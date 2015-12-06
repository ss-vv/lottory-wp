package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.mq.MessageSender;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Play;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.data.weibo.RecommendSchemeMessage;
import com.xhcms.lottery.commons.persist.dao.BBMatchDao;
import com.xhcms.lottery.commons.persist.dao.BBMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.BetMatchRecDao;
import com.xhcms.lottery.commons.persist.dao.BetSchemeRecDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchPlayDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.BetMatchRecPO;
import com.xhcms.lottery.commons.persist.entity.BetSchemeRecPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPlayPO;
import com.xhcms.lottery.commons.persist.service.BetSchemeRecService;
import com.xhcms.lottery.commons.persist.service.PlayService;
import com.xhcms.lottery.commons.persist.service.Scheme2ViewService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.MixPlayType;
import com.xhcms.lottery.utils.CombineBetMatchUtil;

public class BetSchemeRecServiceImpl implements BetSchemeRecService {

	@Autowired
	private BetMatchRecDao betMatchDao;
	
	@Autowired
	private BetSchemeRecDao betSchemeDao;

	@Autowired
	private PlayService playService;
	

	@Autowired
	private MessageSender messageSender;
	
	@Autowired
	private FBMatchDao fbMatchDao;
	
    @Autowired
    private FBMatchPlayDao fbMatchPlayDao;
    
    @Autowired
    protected BBMatchDao bbMatchDao;
    
    @Autowired
    protected BBMatchPlayDao bbMatchPlayDao;
    
    @Autowired
    private Scheme2ViewService scheme2ViewService;
    
	@Transactional
	@Override
	public BetScheme getScheme(String playId, String betMatchs,
			String passTypes, int money, int multiple, String bonus) {
		return initScheme(playId, betMatchs, passTypes, money, multiple, bonus);
	}

	protected BetScheme initScheme(String playId, String betMatchs,
			String passTypes, int money, int multiple, String bonus) {
		BetScheme scheme = new BetScheme();
		scheme.setTotalAmount(money);
		scheme.setMultiple(multiple);
		scheme.setMaxBonus(new BigDecimal(bonus));

		Play play = playService.get(playId);
		if (play != null) {
			scheme.setLotteryId(play.getLotteryId());
		}
		scheme.setPlayId(playId);
		scheme.setMultiple(multiple);

		// 过关方式
		List<String> pts = new ArrayList<String>();
		if(StringUtils.isNotBlank(passTypes)) {
			String[] passTypeArr = passTypes.split(",");
			for (int i = 0; i < passTypeArr.length; i++) {
				pts.add(passTypeArr[i]);
			}
			scheme.setPassTypes(pts);
			scheme.setPassTypeIds(passTypes);
		}

		//投注赛事
		if(scheme.getLotteryId().equals(Constants.JCZQ) ||
				scheme.getLotteryId().equals(Constants.JCLQ)||
				scheme.getLotteryId().equals(Constants.BJDC)||
				scheme.getLotteryId().equals(Constants.BDSF)){
			String[] matchArr = betMatchs.split(",");
			Pattern p = Pattern.compile("-");
			List<BetMatch> matchList = new ArrayList<BetMatch>(matchArr.length);
			for (int i = 0; i < matchArr.length; i++) {
				String match = matchArr[i];
				String[] m = p.split(match);
				BetMatch bm = new BetMatch();
				bm.setMatchId(Long.parseLong(m[0]));
				bm.setCode(m[1]);
				// 加胆码
				bm.setSeed(Boolean.parseBoolean(m[2]));
				if (m.length > 3) {
					MixPlayType mp = MixPlayType.valueOfPlayName(m[3]);
					bm.setPlayId(mp.getPlayId());
				}
				matchList.add(bm);
			}
			scheme.setMatchNumber(matchList.size());
			scheme.setMatchs(matchList);
		}

		scheme.setCreatedTime(new Date());
		return scheme;
	}

	@Transactional
	@Override
	public void saveBetScheme(BetScheme scheme, long sponsorId, String content, String annotations) {
		BetSchemeRecPO po = new BetSchemeRecPO();
		BeanUtils.copyProperties(scheme, po);
		betSchemeDao.save(po);
		
		Map<String, String> annotationMap = checkBetMatchAnnotation(annotations);
		long schemeId = po.getId();
		saveBetMatch(schemeId, scheme.getMatchs(), po.getPlayId(), annotationMap);
		
		//同时异步将微博内容发出
		RecommendSchemeMessage recommMessage = new RecommendSchemeMessage();
		recommMessage.setWeiboUserId(sponsorId);
		recommMessage.setSchemeId(schemeId);
		recommMessage.setWeiboContent(content);
		recommMessage.setCreatedTime(new Date());
		recommMessage.setLotteryId(scheme.getLotteryId());
		messageSender.send(recommMessage);
	}
	
	private Map<String, String> checkBetMatchAnnotation(String annotations) {
		Map<String, String> annotationMap = new HashMap<String, String>();
		if(StringUtils.isNotBlank(annotations)) {
			for(String matchAnnotation : annotations.split(",")) {
				String[] annotationSplit = matchAnnotation.split("-");
				if(annotationSplit.length == 2) {
					annotationMap.put(annotationSplit[0], annotationSplit[1]);
				}
			}
		}
		return annotationMap;
	}
	
	protected void saveBetMatch(Long schemeId, List<BetMatch> matchs, String playId, 
			Map<String, String> annotationMap) {
        for(BetMatch bm: matchs){
            BetMatchRecPO po = new BetMatchRecPO();
            po.setMatchId(bm.getMatchId());
            po.setSchemeId(schemeId);
            po.setCode(bm.getCode());
            if(StringUtils.isBlank(bm.getPlayId())) {
            	po.setPlayId(playId);
            } else {
            	po.setPlayId(bm.getPlayId());
            }
            //胆码
            po.setSeed(bm.isSeed());
            po.setAnnotation(annotationMap.get(bm.getMatchId().toString()));
            
            betMatchDao.save(po);
        }
    }

	@Override
	public List<Long> splitBetMatchs(String betMatchs) {
		List<Long> matchIdList = new ArrayList<Long>();
		if(StringUtils.isNotBlank(betMatchs)) {
			String[] matchArr = betMatchs.split(",");
			Pattern p = Pattern.compile("-");
			for (int i = 0; i < matchArr.length; i++) {
				String match = matchArr[i];
				String[] m = p.split(match);
				matchIdList.add(Long.parseLong(m[0]));
			}
		}
		return matchIdList;
	}
	
	@Override
	@Transactional
	public BetScheme viewRecScheme(Long id) {
		Collection<Long> ids = new HashSet<Long>();
		ids.add(id);
		List<BetSchemeRecPO> list = betSchemeDao.findBySchemaIds(ids);
		if(list.size() > 0){
			BetSchemeRecPO betSchemeRecPO = list.get(0);
			if(betSchemeRecPO.getTotalAmount() <= 0){
				betSchemeRecPO.setTotalAmount(2);// 防止BetScheme 调用 getAfterTaxBonusReturnRatio() 被除数为0 bug
			}
			BetScheme bs = new BetScheme();
			BeanUtils.copyProperties(betSchemeRecPO, bs);
			String lotteryId = betSchemeRecPO.getLotteryId();
	        if(Constants.JCLQ.equals(lotteryId) || Constants.JCZQ.equals(lotteryId)){
	            bs.setMatchs(listMatchs(betSchemeRecPO));
	        } else if(Constants.CQSS.equals(lotteryId) || Constants.JX11.equals(lotteryId)) {
	        	// TODO
	        } else if(Constants.CTZC.equals(lotteryId)) {
	        	// TODO
	        } else if(Constants.SSQ.equals(lotteryId)) {
	        	// TODO
	        }
	        
	        bs = scheme2ViewService.scheme2View(bs, false);
	        bs = CombineBetMatchUtil.combine(bs);
	        return bs;
		} else {
			return null;
		}
	}
	
	private List<BetMatch> listMatchs(BetSchemeRecPO betSchemeRecPO){
        List<BetMatchRecPO> recBetMatches = betMatchDao.findBySchemeId(betSchemeRecPO.getId());
        HashMap<String, PlayMatch> playMatchesMap = new HashMap<String, PlayMatch>(recBetMatches.size());
        Set<Long> matchIdsSet = new HashSet<Long>();
        List<BetMatch> betMatches = new ArrayList<BetMatch>(recBetMatches.size());
        for(BetMatchRecPO recBetMatch: recBetMatches){ 
            PlayMatch pm = new PlayMatch();
            pm.setId(recBetMatch.getId());
            pm.setSchemeId(recBetMatch.getSchemeId());
            pm.setId(recBetMatch.getMatchId());
            pm.setMatchId(recBetMatch.getMatchId());
            pm.setBetCode(recBetMatch.getCode().substring(4));
            pm.setBetOptions(recBetMatch.getCode().substring(4));
            pm.setAnnotation(recBetMatch.getAnnotation());
            //加混合功能时添加的代码-start-
            //添加投注的比赛的玩法
            String playId = recBetMatch.getPlayId();
            if (StringUtils.isBlank(playId)) {
            	playId = betSchemeRecPO.getPlayId();
            }
            pm.setPlayId(playId);
            //加混合功能时添加的代码-end-
            matchIdsSet.add(recBetMatch.getMatchId());
            pm.setPlayId(recBetMatch.getPlayId());
            playMatchesMap.put(recBetMatch.getMatchId()+","+recBetMatch.getPlayId(), pm);
            betMatches.add(pm);
        }
        
        if(Constants.JCZQ.equals(betSchemeRecPO.getLotteryId())){
        	// 竞彩足球
        	List<FBMatchPlayPO> fbMatchPlayPOs = null;
        	//混合玩法
        	if(com.xhcms.lottery.lang.Constants.PLAY_05_ZC_2.equals(betSchemeRecPO.getPlayId())
        			|| com.xhcms.lottery.lang.Constants.PLAY_555_ZCFH_2.equals(betSchemeRecPO.getPlayId())
        			|| com.xhcms.lottery.lang.Constants.PLAY_555_ZCFH_1.equals(betSchemeRecPO.getPlayId())){
        		fbMatchPlayPOs = fbMatchPlayDao.findByMatches(playMatchesMap.values());
        	} else {
        		fbMatchPlayPOs = fbMatchPlayDao.find(betSchemeRecPO.getPlayId(), matchIdsSet);
        	}
           
            HashMap<Long, FBMatchPlayPO> fbMatchPlayMap = new HashMap<Long, FBMatchPlayPO>(fbMatchPlayPOs.size());
            for(FBMatchPlayPO f: fbMatchPlayPOs){
                fbMatchPlayMap.put(f.getMatchId(), f);
            } 
            List<FBMatchPO> fbMatchPOs = fbMatchDao.find(matchIdsSet);
            HashMap<String, FBMatchPO> fbmatchesMap = new HashMap<String, FBMatchPO>();
            for (FBMatchPO fbMatchPO : fbMatchPOs) {
            	fbmatchesMap.put(""+fbMatchPO.getId(), fbMatchPO);
			}
            for(String key: playMatchesMap.keySet()){
            	String mid = key.split(",")[0];
            	FBMatchPO m = fbmatchesMap.get(mid);
                PlayMatch pm = playMatchesMap.get(key);
                FBMatchPlayPO p = fbMatchPlayMap.get(m.getId());
                if(pm != null && p != null){
                    toPlayMatch(m, p, pm);
                }
            }
        } else if(Constants.JCLQ.equals(betSchemeRecPO.getLotteryId())) {
            // 竞彩篮球
            List<BBMatchPlayPO> fs = null;
            //混合玩法
            if (com.xhcms.lottery.lang.Constants.PLAY_10_LC_2.equals(betSchemeRecPO.getPlayId())
        			|| com.xhcms.lottery.lang.Constants.PLAY_666_LCFH_2.equals(betSchemeRecPO.getPlayId())
        			|| com.xhcms.lottery.lang.Constants.PLAY_666_LCFH_1.equals(betSchemeRecPO.getPlayId())) {
            	fs = bbMatchPlayDao.findByMatches(playMatchesMap.values());
            } else {
            	fs =  bbMatchPlayDao.find(betSchemeRecPO.getPlayId(),matchIdsSet);
            }
           
            HashMap<Long, BBMatchPlayPO> fMap = new HashMap<Long, BBMatchPlayPO>(fs.size());
            for(BBMatchPlayPO f: fs){
                fMap.put(f.getMatchId(), f);
            }
            List<BBMatchPO> bbMatchPOs = bbMatchDao.find(matchIdsSet);
            HashMap<String, BBMatchPO> bbmatchesMap = new HashMap<String, BBMatchPO>();
            for (BBMatchPO bbMatchPO : bbMatchPOs) {
            	bbmatchesMap.put(""+bbMatchPO.getId(), bbMatchPO);
			}
            for(String key: playMatchesMap.keySet()){
            	String mid = key.split(",")[0];
            	BBMatchPO m = bbmatchesMap.get(mid);
                PlayMatch pm = playMatchesMap.get(key);
                BBMatchPlayPO p = fMap.get(m.getId());
                if(pm != null && p != null){
                    toPlayMatch(m, p, pm);
                }
            }
        }
        
        return betMatches;
    }
	
	private PlayMatch toPlayMatch(FBMatchPO po, FBMatchPlayPO mpo, PlayMatch pm){
		pm.setCnCode(po.getCnCode());
        pm.setCode(po.getCode());
        pm.setName(po.getName());
        pm.setConcedePoints(String.valueOf(po.getConcedePoints()));
        pm.setEntrustDeadline(po.getEntrustDeadline());
        pm.setScore1(po.getHalfScore());
        pm.setPlayingTime(po.getPlayingTime());
        pm.setResult(mpo.getWinOption());
        pm.setScore(po.getScore());
        pm.setStatus(po.getStatus());
        return pm;
	}
	    
    private PlayMatch toPlayMatch(BBMatchPO po, BBMatchPlayPO mpo, PlayMatch pm){
        pm.setCnCode(po.getCnCode());
        pm.setCode(po.getCode());
        pm.setName(po.getName());
        pm.setConcedePoints(String.valueOf(mpo.getDefaultScore()));
        pm.setEntrustDeadline(po.getEntrustDeadline());
        pm.setScore1(po.getQuarter1());
        pm.setScore2(po.getQuarter2());
        pm.setScore3(po.getQuarter3());
        pm.setScore4(po.getQuarter4());
        pm.setPlayingTime(po.getPlayingTime());
        pm.setResult(mpo.getWinOption());
        pm.setScore(po.getFinalScore());
        pm.setStatus(po.getStatus());
        return pm;
    }

    @Transactional
	@Override
	public String getPlayType(Long id) {
    	String playTypes = betSchemeDao.getPlayType(id);
		return playTypes;
	}
}
