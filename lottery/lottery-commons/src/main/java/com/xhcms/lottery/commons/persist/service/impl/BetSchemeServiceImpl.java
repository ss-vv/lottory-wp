package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap; 
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.common.persist.QTMatchidDao;
import com.xhcms.commons.lang.Assert;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetPartner;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.data.Commission;
import com.xhcms.lottery.commons.data.DigitalBetContent;
import com.xhcms.lottery.commons.data.Play;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.data.UserScore;
import com.xhcms.lottery.commons.data.ctfb.CTBetContent;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.PlayDao;
import com.xhcms.lottery.commons.persist.dao.SystemConfDao;
import com.xhcms.lottery.commons.persist.dao.TicketPresetDao;
import com.xhcms.lottery.commons.persist.dao.WinDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPO;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.BetMatchPO;
import com.xhcms.lottery.commons.persist.entity.BetPartnerPO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePresetPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.OrderPO;
import com.xhcms.lottery.commons.persist.entity.PlayPO;
import com.xhcms.lottery.commons.persist.entity.RecommendUserPO;
import com.xhcms.lottery.commons.persist.entity.TicketPO;
import com.xhcms.lottery.commons.persist.entity.UserScorePO;
import com.xhcms.lottery.commons.persist.entity.WinPO;
import com.xhcms.lottery.commons.persist.service.AccountQueryService;
import com.xhcms.lottery.commons.persist.service.AwardNoticeService;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.commons.persist.service.GetPresetSchemeService;
import com.xhcms.lottery.commons.persist.service.PhantomService;
import com.xhcms.lottery.commons.persist.service.PlayMatchModifyService;
import com.xhcms.lottery.commons.persist.service.SchemeCacheUpdateNoticeService;
import com.xhcms.lottery.commons.persist.service.UserScoreService;
import com.xhcms.lottery.commons.persist.service.WinListService;
import com.xhcms.lottery.commons.persist.service.WinningNoticeService;
import com.xhcms.lottery.commons.util.PresetSchemeTool;
import com.xhcms.lottery.commons.util.bonus.MaxAndMinBonusModel;
import com.xhcms.lottery.commons.util.bonus.MaxAndMinBonusUtil;
import com.xhcms.lottery.conf.SystemConf;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.utils.ConvertUserScore;
import com.xhcms.lottery.utils.NumberUtils;
import com.xhcms.lottery.utils.POUtils;
import com.xhcms.lottery.utils.PagingUtils;

public class BetSchemeServiceImpl extends BetSchemeBaseServiceImpl implements
		BetSchemeService {

	private static final Logger log = LoggerFactory.getLogger(BetSchemeServiceImpl.class);
	
	@Autowired
	private WinDao winDao;
	@Autowired
	private UserScoreService userScoreService;

	@Autowired
	private WinListService winListService;

	@Autowired
	private PlayDao playDao;
	
	@Autowired
	private WinningNoticeService winningNoticeService;

	@Autowired
	private AwardNoticeService awardNoticeService;
	
	@Autowired
	private SchemeCacheUpdateNoticeService schemeCacheUpdateNotice;
	
	@Autowired
	private GetPresetSchemeService getPresetSchemeService;
	
	@Autowired
	private QTMatchidDao qtMatchidDao;
	@Autowired
	private BJDCMatchPlayDao bjdcMatchPlayDao;
	@Autowired
	private TicketPresetDao ticketPresetDao;
	
	@Autowired
    private AccountQueryService accountQueryService;
	
	@Autowired
	private PlayMatchModifyService playMatchModifyService;
	
	@Autowired
	private SystemConfDao systemConfDao;
	
	@Autowired
	private MaxAndMinBonusUtil maxAndMinBonusUtil;
	
	@Override
    @Transactional(readOnly = true)
    public BetScheme viewScheme(Long schemeId, int displayMode) {
		log.debug("查看方案ID={}, 显示模式={}", schemeId, displayMode);
        BetSchemePO spo = betSchemeDao.get(schemeId);//getPresetSchemeService.getRightSchemeById(schemeId);
        log.debug("获取的方案对象={}", spo);
        
        Assert.notNull(spo, AppCode.SCHEME_NOT_EXIST);
        switch(displayMode){
        	case EntityType.DISPLAY_GROUPBUY:
        		Assert.eq(spo.getType(), EntityType.BETTING_PARTNER, AppCode.SCHEME_UNUSUAL_OPERATE);
        		break;
        	case EntityType.DISPLAY_SHOW:
        		if(spo.getType()==EntityType.BETTING_ALONE ||spo.getType()==EntityType.BETTING_PARTNER){
        			Assert.eq(spo.getShowScheme(), EntityType.SHOW_SCHEME, AppCode.SCHEME_UNUSUAL_OPERATE);
        		}else{
        			Assert.notNull(null, AppCode.SCHEME_UNUSUAL_OPERATE);
        		}
        		break;
        	case EntityType.DISPLAY_ALONE :
        		if(spo.getType()==EntityType.BETTING_ALONE){
        			if(spo.getIsPublishShow() == EntityType.DONT_PUBLISH_SHOW) {
        				Assert.eq(spo.getShowScheme(), EntityType.DONT_SHOW_SCHEME, 
        						AppCode.SCHEME_UNUSUAL_OPERATE);
        			}
        		}else{
        			Assert.eq(spo.getType(), EntityType.BETTING_FOLLOW, AppCode.SCHEME_UNUSUAL_OPERATE);
        		}
        		break;
            default:
            	Assert.notNull(null, AppCode.SCHEME_UNUSUAL_OPERATE);
            	break;
        }
        BetScheme bs = new BetScheme();
        
        //查找用户对应的彩种战绩
        UserScorePO userScorePO = userScoreDao.getUserScoreByUserIdLottoryId(spo.getSponsorId(), spo.getLotteryId());
        if(userScorePO!=null){
        	UserScore userScore=new UserScore();
        	BeanUtils.copyProperties(userScorePO,userScore);
        	LinkedList<UserScore> userScoreList = new LinkedList<UserScore>();
        	userScoreList.add(userScore);
        	bs.setUserScores(userScoreList);
        	//转换积分图标
        	if(displayMode==EntityType.DISPLAY_GROUPBUY){
        		userScore.setGroupPic(ConvertUserScore.convertScore(userScore.getGroupScore(), EntityType.GROUP_SCORE));
        	}else{
        		userScore.setShowPic(ConvertUserScore.convertScore(userScore.getShowScore(), EntityType.SHOW_SCORE));
        	}
        }
        
        if(displayMode!=EntityType.DISPLAY_GROUPBUY){
        	//如果是代购(晒单)模式
        	List<BetSchemePO>  followSchemePOs = new ArrayList<BetSchemePO>();
        	if(spo.getFollowingCount()>0){//跟单人数大于0
        		followSchemePOs = getPresetSchemeService.findFollowSchemes(spo);
        	}
        	bs.setFollowSchemes(POUtils.copyBeans(followSchemePOs, BetScheme.class));
        }else{
        	//合买模式
        	List<BetPartnerPO> betPartnerPOs = betPartnerDao.findBySchemeId(spo.getId());
        	if(null != betPartnerPOs && betPartnerPOs.size() > 0) {
        		List<BetPartner> groupbuyPartners = new ArrayList<BetPartner>();
        		for(BetPartnerPO po : betPartnerPOs) {
        			if(po.getScheme() != null && po.getScheme().getId() > 0) {
        				BetPartner partner = new BetPartner();
        				BeanUtils.copyProperties(po, partner);
        				partner.setSchemeId(po.getScheme().getId());
        				groupbuyPartners.add(partner);
        			}
        		}
        		if(groupbuyPartners.size() > 0) {
        			bs.setGroupbuyPartners(groupbuyPartners);
        		}
        	}
        }
        BeanUtils.copyProperties(spo, bs);
        List<CTBetContent> ctBetContents=null ;
        String issueNumber = null;
        if(spo.getLotteryId().equals(Constants.CTZC)){
        	 ctBetContents = POUtils.copyBeans(cTBetContentDao.findCtBetContent(schemeId), CTBetContent.class);
        	 issueNumber=ctBetContents.get(0).getIssueNumber();
        	 bs.setIssueNumber(issueNumber);
        }
    	if(spo.getLotteryId().equals(Constants.CTZC)){
    		bs.setCtBetContents(ctBetContents);
    		List<CTFBMatch> ctFBMatchs = POUtils.copyBeans(
    				cTFBMatchDao.findByIssueNoAndPlayId(issueNumber, spo.getPlayId()),CTFBMatch.class);
    		bs.setCtFBMatchs(ctFBMatchs);
    	}else if(spo.getLotteryId().equals(Constants.JCLQ) || spo.getLotteryId().equals(Constants.JCZQ)){
    		if(!PlayType.JCSJBGJ.getShortPlayStr().equals(spo.getPlayId())) {
    			bs.setMatchs(listMatchs(spo));
    		}
    	}else if(spo.getLotteryId().equals(Constants.SSQ) || spo.getLotteryId().equals(Constants.CQSS) || spo.getLotteryId().equals(Constants.JX11)){
			List<DigitalBetContent> hfBetContent = POUtils.copyBeans(
					hfBetContentDao.findHfBetContent(schemeId),
					DigitalBetContent.class);
    		bs.setDigitalBetContents(hfBetContent);
    	} else if(spo.getLotteryId().equals(Constants.BJDC)) {
    		bs.setMatchs(listMatchs(spo));
    	} else{
    		log.error("unknown LotteryId :{}",spo.getLotteryId());
    	}
        return bs;
    }
    
	@Override
    public BetScheme setBetCodeWithPrivacy(BetScheme betScheme, long userId, int displayMode) {
    	if(!PlayType.JCSJBGJ.getShortPlayStr().equals(betScheme.getPlayId())) {
    		BetSchemePO spo = new BetSchemePO();
    		BeanUtils.copyProperties(betScheme, spo);
	    	//检查方案公开时间以及是否需要保密
	    	if(needKeepSecret(userId, displayMode, spo)){
	    		for (BetMatch m : betScheme.getMatchs()) {
					PlayMatch p = (PlayMatch) m;
					p.setBetCode("");
				}
	    	}
        }
    	return betScheme;
    }

	private boolean needKeepSecret(long userId, int displayMode, BetSchemePO spo) {
		return checkUser(spo, userId,displayMode) == false && !checkBetSchemePOPublicTime(spo);
	}
	
	@Transactional(readOnly = true)
	public boolean needKeepSecret(long userId, int displayMode, BetScheme betScheme){
		return checkUser(betScheme, userId,displayMode) == false && !checkBetSchemePOPublicTime(betScheme);
	}
    
    private boolean checkBetSchemePOPublicTime(BetScheme betScheme) {
    	if(new Date().getTime() < betScheme.getPublicTime().getTime()){
    		return false;
    	} else {
    		return true;
    	}
	}

	private boolean checkUser(BetScheme betScheme, long userId, int displayMode) {
		// 不晒单
		if (betScheme.getShowScheme() == EntityType.DONT_SHOW_SCHEME
				&& betScheme.getType() != EntityType.BETTING_FOLLOW
				&& displayMode != EntityType.DISPLAY_GROUPBUY) {
			return (betScheme.getSponsorId()==userId);
		}
		// 晒单
		long sponsorId;
		long schemeId;
		int privacy = -1;
		BetSchemePO spo = new BetSchemePO();

		if (betScheme.getType() == EntityType.BETTING_FOLLOW) {// 跟单时，取主单发起人和权限
			spo = betSchemeDao.get(betScheme.getFollowedSchemeId());
			sponsorId = spo.getSponsorId();
			privacy = betScheme.getFollowSchemePrivacy();// 取跟单权限
			schemeId = betScheme.getFollowedSchemeId();
		} else {// 代购方案并且不是本人的，或者是合买方案
			if (betScheme.getType() == EntityType.BETTING_PARTNER
					&& displayMode == EntityType.DISPLAY_GROUPBUY) {// 合买 晒单
				privacy = betScheme.getPrivacy();
			} else {// 代购、晒单
				privacy = betScheme.getFollowSchemePrivacy();
			}
			schemeId = betScheme.getId();
			spo.setId(betScheme.getId());
			spo.setStatus(betScheme.getStatus());
			spo.setOfftime(betScheme.getOfftime());
			
			sponsorId = betScheme.getSponsorId();
		}
		switch (privacy) {
			case EntityStatus.PRIVACY_PUBLIC:
				return true;
			case EntityStatus.PRIVACY_SOLD:
				return userId == sponsorId
						|| spo.getStatus() == EntityStatus.TICKET_NOT_WIN
						|| spo.getStatus() == EntityStatus.TICKET_NOT_AWARD
						|| spo.getStatus() == EntityStatus.TICKET_AWARDED;
			case EntityStatus.PRIVACY_FOLLOW_PUBLIC:
				return userId == sponsorId
						|| (null != betPartnerDao.get(spo.getId(), userId))
						|| (null != betSchemeDao.findFollowSchemesByUser(schemeId,
								userId));
			case EntityStatus.PRIVACY_FOLLOW:
				return userId == sponsorId
						|| (spo.getOfftime().before(new Date()) && null != betPartnerDao
								.get(spo.getId(), userId))
						|| (spo.getOfftime().before(new Date()) && (null != betSchemeDao
								.findFollowSchemesByUser(schemeId, userId)));
			case EntityStatus.PRIVACY_SECRET:
				return (userId == sponsorId);
			default:
				return false;
		}
	}

	private boolean checkBetSchemePOPublicTime(BetSchemePO spo_follow){
    	if(new Date().getTime() < spo_follow.getPublicTime().getTime()){
    		return false;
    	} else {
    		return true;
    	}
    }
    
	@Override
	@Transactional(readOnly = true)
	public void confirmScheme(BetScheme scheme) {
		List<BetMatch> matchs = scheme.getMatchs();
		List<BetMatch> ms = new ArrayList<BetMatch>();
		Map<Long, PlayMatch> maps = new HashMap<Long, PlayMatch>();
		for (BetMatch m : matchs) {
			PlayMatch pm = new PlayMatch();
			BeanUtils.copyProperties(m, pm);
			pm.setBetCode(m.getCode().substring(4));
			pm.setOdds(m.getOdds());
			maps.put(m.getMatchId(), pm);
			ms.add(pm);
		}

		Set<Long> ids = new HashSet<Long>(matchs.size());
		for (BetMatch match : matchs) {
			ids.add(match.getMatchId());
		}
		if (Constants.JCZQ.equals(scheme.getLotteryId())) {
			// 竞彩足球
			Map<Long, FBMatchPlayPO> fps = new HashMap<Long, FBMatchPlayPO>();
			for (FBMatchPlayPO po : fbMatchPlayDao
					.find(scheme.getPlayId(), ids)) {
				fps.put(po.getMatchId(), po);
			}
			for (FBMatchPO po : fbMatchDao.find(ids)) {
				PlayMatch p = maps.get(po.getId());
				if (p != null) {
					p.setName(po.getName());
					p.setCnCode(po.getCnCode());
					p.setConcedePoints(String.valueOf(po.getConcedePoints()));
				}
			}

		} else {
			// 竞彩篮球
			Map<Long, BBMatchPlayPO> bps = new HashMap<Long, BBMatchPlayPO>();
			for (BBMatchPlayPO po : bbMatchPlayDao
					.find(scheme.getPlayId(), ids)) {
				bps.put(po.getMatchId(), po);
			}

			for (BBMatchPO po : bbMatchDao.find(ids)) {
				PlayMatch p = maps.get(po.getId());
				BBMatchPlayPO ppo = bps.get(po.getId());
				if (p != null && ppo != null) {
					p.setName(po.getName());
					p.setCnCode(po.getCnCode());
					p.setConcedePoints(String.valueOf(ppo.getDefaultScore()));
				}
			}
		}
		scheme.setMatchs(ms);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Ticket> listTicket(long schemeId, long userId, int displayMode) {
		BetSchemePO spo = betSchemeDao.get(schemeId);
		Assert.notNull(spo, AppCode.SCHEME_NOT_EXIST);
		List<Ticket> rets = null;
		if (checkUser(spo, userId, displayMode)) {
			List<TicketPO> list = ticketDao.findByScheme(schemeId, -1);
			rets = new ArrayList<Ticket>();
			Ticket tk = null;
			for (TicketPO po : list) {
				tk = new Ticket();
				BeanUtils.copyProperties(po, tk);
				rets.add(tk);
			}
		}
		return rets;
	}

	private List<BetMatch> listMatchs(BetSchemePO spo) {
		List<BetMatchPO> betMatchPOList = betMatchDao.findBySchemeId(spo.getId());
		HashMap<Long, PlayMatch> matchs = new HashMap<Long, PlayMatch>(betMatchPOList.size());
		HashMap<Long, List<PlayMatch>> jcmatchs = new HashMap<Long, List<PlayMatch>>(betMatchPOList.size());
		List<PlayMatch> playMatchList = new ArrayList<PlayMatch>();
		
		List<BetMatch> betMatchList = new ArrayList<BetMatch>(betMatchPOList.size());
		init(spo, betMatchPOList, matchs, jcmatchs, playMatchList, betMatchList);

		if (Constants.JCZQ.equals(spo.getLotteryId())) {
			handleJCZQ(spo, matchs, jcmatchs, playMatchList);
		} else if (Constants.JCLQ.equals(spo.getLotteryId())) {
			handleJCLQ(spo, matchs, jcmatchs, playMatchList);
		} else if (Constants.BJDC.equals(spo.getLotteryId())) {
			handleBJDC(matchs);
		}

		return betMatchList;
	}

	private void handleBJDC(HashMap<Long, PlayMatch> matchs) {
		List<BJDCMatchPlayPO> mp = bjdcMatchPlayDao.findByMatches(matchs.values());
		HashMap<Long, BJDCMatchPlayPO> fMap = new HashMap<Long, BJDCMatchPlayPO>(mp.size());
		for (BJDCMatchPlayPO f : mp) {
			fMap.put(f.getMatchId(), f);
		}
		
		for (BJDCMatchPO m : bjdcMatchDao.find(matchs.keySet())) {
			PlayMatch pm = matchs.get(m.getId());
			BJDCMatchPlayPO p = fMap.get(m.getId());
			if (pm != null && p != null) {
				modifyPlayMatchWithBD(m, p, pm);
			}
		}
	}

	private void handleJCLQ(BetSchemePO spo, HashMap<Long, PlayMatch> matchs,
			HashMap<Long, List<PlayMatch>> jcmatchs,
			List<PlayMatch> playMatchList) {
		List<BBMatchPlayPO> bbMatchPlayPOList = queryBBMatchPlayPOList(spo,
				matchs, playMatchList);

		HashMap<Long, BBMatchPlayPO> bbMatchPlayPOMap = makeBBMatchPlayPOMap(bbMatchPlayPOList);

		for (BBMatchPO bbMatchPO : bbMatchDao.find(matchs.keySet())) {
			List<PlayMatch> list = new ArrayList<PlayMatch>();
			if(PlayType.isFHMixBet(spo.getPlayId())) {
		    	list = jcmatchs.get(bbMatchPO.getId());
		    } else {
		    	PlayMatch playMatch = matchs.get(bbMatchPO.getId());
		    	list.add(playMatch);
		    }
			Iterator<PlayMatch> iter = list.iterator();
			while(iter.hasNext()) {
				PlayMatch playMatch = iter.next();
				BBMatchPlayPO p = bbMatchPlayPOMap.get(bbMatchPO.getId());
				if (playMatch != null && p != null) {
					this.playMatchModifyService.modifyBBPlayMatch(bbMatchPO, p, playMatch);
				}
			}
		}
	}

	private HashMap<Long, BBMatchPlayPO> makeBBMatchPlayPOMap(
			List<BBMatchPlayPO> bbMatchPlayPOList) {
		HashMap<Long, BBMatchPlayPO> bbMatchPlayPOMap = new HashMap<Long, BBMatchPlayPO>(
				bbMatchPlayPOList.size());
		for (BBMatchPlayPO f : bbMatchPlayPOList) {
			bbMatchPlayPOMap.put(f.getMatchId(), f);
		}
		return bbMatchPlayPOMap;
	}

	private List<BBMatchPlayPO> queryBBMatchPlayPOList(BetSchemePO spo,
			HashMap<Long, PlayMatch> matchs, List<PlayMatch> playMatchList) {
		// 竞彩篮球
		List<BBMatchPlayPO> bbMatchPlayPOList = null;
		// 混合玩法
		if(PlayType.isFHMixBet(spo.getPlayId())) {
			bbMatchPlayPOList = bbMatchPlayDao.findByMatches(playMatchList);
		} else if (com.xhcms.lottery.lang.Constants.PLAY_10_LC_2.equals(spo
				.getPlayId())) {
			bbMatchPlayPOList = bbMatchPlayDao.findByMatches(matchs.values());
		} else {
			bbMatchPlayPOList = bbMatchPlayDao.find(spo.getPlayId(), matchs.keySet());
		}
		return bbMatchPlayPOList;
	}

	private void handleJCZQ(BetSchemePO spo, HashMap<Long, PlayMatch> matchs,
			HashMap<Long, List<PlayMatch>> jcmatchs,
			List<PlayMatch> playMatchList) {
		List<FBMatchPlayPO> fbMatchPlayPOList = queryFBMatchPlayPOList(spo,
				matchs, playMatchList);

		HashMap<String, FBMatchPlayPO> fbMatchPlayPOMap = makeFBMatchPlayPOMap(fbMatchPlayPOList);

		for (FBMatchPO fbMatchPO : fbMatchDao.find(matchs.keySet())) {
			List<PlayMatch> list = new ArrayList<PlayMatch>();
			if(PlayType.isFHMixBet(spo.getPlayId())) {
		    	list = jcmatchs.get(fbMatchPO.getId());
		    } else {
		    	PlayMatch playMatch = matchs.get(fbMatchPO.getId());
		    	list.add(playMatch);
		    }
			Iterator<PlayMatch> iter = list.iterator();
			while(iter.hasNext()) {
				PlayMatch playMatch = iter.next();
				String key=fbMatchPO.getId()+playMatch.getPlayId();
				FBMatchPlayPO fbMatchPlayPO = fbMatchPlayPOMap.get(key);
				if (playMatch != null && fbMatchPlayPO != null) {
					this.playMatchModifyService.modifyFBPlayMatch(fbMatchPO, fbMatchPlayPO, playMatch);
				}
			}
		}
	}

	/**
	 * 根据lt_bet_match初始化playMatch以及其他map和list
	 * @param spo
	 * @param betMatchPOList
	 * @param matchs
	 * @param jcmatchs
	 * @param playMatchList
	 * @param betMatchList
	 */
	private void init(BetSchemePO spo, List<BetMatchPO> betMatchPOList,
			HashMap<Long, PlayMatch> matchs,
			HashMap<Long, List<PlayMatch>> jcmatchs,
			List<PlayMatch> playMatchList, List<BetMatch> betMatchList) {
		for (BetMatchPO betMatchPO : betMatchPOList) {
			PlayMatch playMatch = new PlayMatch();
			playMatch.setId(betMatchPO.getId());
			playMatch.setSchemeId(betMatchPO.getSchemeId());
			playMatch.setMatchId(betMatchPO.getMatchId());
			playMatch.setOdds(betMatchPO.getOdds());
			if(spo.getLotteryId().equals(Constants.BJDC)){
				playMatch.setBetCode(betMatchPO.getCode().substring(3));
			}else{
				playMatch.setBetCode(betMatchPO.getCode().substring(4));
			}
			
			// 加混合功能时添加的代码-start-
			// 添加投注的比赛的玩法
			String playId = betMatchPO.getPlayId();
			if (StringUtils.isBlank(playId)) {
				playId = spo.getPlayId();
			}
			playMatch.setPlayId(playId);
			// 加混合功能时添加的代码-end-

			playMatch.setDefaultScore(betMatchPO.getDefaultScore());
			playMatch.setConcedePoints("" + betMatchPO.getDefaultScore());
			
			//胆码
        	playMatch.setSeed(betMatchPO.isSeed());
        	playMatch.setAnnotation(betMatchPO.getAnnotation());
        	
			matchs.put(betMatchPO.getMatchId(), playMatch);
			
			if(!PlayType.isFHMixBet(spo.getPlayId())) {
            	matchs.put(betMatchPO.getMatchId(), playMatch);
            } else {
            	if(null == jcmatchs.get(betMatchPO.getMatchId()) || jcmatchs.get(betMatchPO.getMatchId()).size() <= 0) {
            		List<PlayMatch> playML = new ArrayList<PlayMatch>();
            		playML.add(playMatch);
            		jcmatchs.put(betMatchPO.getMatchId(), playML);
            	} else {
            		jcmatchs.get(betMatchPO.getMatchId()).add(playMatch);
            	}
            }
			
			playMatchList.add(playMatch);
			betMatchList.add(playMatch);
		}
	}

	private HashMap<String, FBMatchPlayPO> makeFBMatchPlayPOMap(
			List<FBMatchPlayPO> fbMatchPlayPOList) {
		HashMap<String, FBMatchPlayPO> fbMatchPlayPOMap = new HashMap<String, FBMatchPlayPO>(
				fbMatchPlayPOList.size());
		for (FBMatchPlayPO f : fbMatchPlayPOList) {
			fbMatchPlayPOMap.put(f.getId(), f);
		}
		return fbMatchPlayPOMap;
	}

	private List<FBMatchPlayPO> queryFBMatchPlayPOList(BetSchemePO spo,
			HashMap<Long, PlayMatch> matchs, List<PlayMatch> playMatchList) {
		// 竞彩足球
		List<FBMatchPlayPO> fbMatchPlayPOList = null;
		// 混合玩法
		if(PlayType.isFHMixBet(spo.getPlayId())) {
			fbMatchPlayPOList = fbMatchPlayDao.findByMatches(playMatchList);
		} else if (com.xhcms.lottery.lang.Constants.PLAY_05_ZC_2.equals(spo
				.getPlayId())) {
			fbMatchPlayPOList = fbMatchPlayDao.findByMatches(matchs.values());				
		} else {
			fbMatchPlayPOList = fbMatchPlayDao.find(spo.getPlayId(), matchs.keySet());
		}
		return fbMatchPlayPOList;
	}
	
	/**
	 * 北单彩种投注内容获取同时绑定比赛的比分、赛果等信息
	 * 得到投注内容的如下信息：
	 * 1.比分（半场比分，全场比分）
	 * 2.赛果
	 * 3.投注赛事的状态
	 * 4.赛事的让球数
	 * @param po	北单赛程记录PO对象
	 * @param mpo	北单赛程对应玩法PO对象
	 * @param pm	投注内容（BetMatch对象）
	 * @return
	 */
	private PlayMatch modifyPlayMatchWithBD(BJDCMatchPO po, BJDCMatchPlayPO mpo, PlayMatch pm) {
		pm.setCnCode(po.getCnCode());
		pm.setCode(po.getCode());
		pm.setName(po.getName());
//		pm.setConcedePoints(String.valueOf(mpo.getConcedePoints()));
		pm.setEntrustDeadline(po.getEntrustDeadline());
		pm.setScore1(po.getHalfScore());
		pm.setPlayingTime(po.getPlayingTime());
		pm.setResult(mpo.getWinOption());
		pm.setScore(po.getScore());
		pm.setStatus(po.getStatus());
		try{
			String finalOdds = "";
			if(null != mpo.getFinalOdds()){
				finalOdds = mpo.getFinalOdds();
				finalOdds = finalOdds.substring(0, Math.min(finalOdds.length(),4));
			}
			pm.setResultOdd(finalOdds);
		} catch (Exception e){
			pm.setResultOdd(mpo.getFinalOdds());
			log.error("最终赔率FinalOdds={}",mpo.getFinalOdds(),e);
		}
		return pm;
	}
	


	private boolean checkUser(BetSchemePO spo_follow, Long userId,
			int displayMode) {
		// 不晒单
		if (spo_follow.getShowScheme() == EntityType.DONT_SHOW_SCHEME
				&& spo_follow.getType() != EntityType.BETTING_FOLLOW
				&& displayMode != EntityType.DISPLAY_GROUPBUY) {
			return (spo_follow.getSponsorId().equals(userId));
		}
		// 晒单
		long sponsorId;
		long schemeId;
		int privacy = -1;
		BetSchemePO spo = new BetSchemePO();

		if (spo_follow.getType() == EntityType.BETTING_FOLLOW) {// 跟单时，取主单发起人和权限
			spo = betSchemeDao.get(spo_follow.getFollowedSchemeId());
			sponsorId = spo.getSponsorId();
			privacy = spo_follow.getFollowSchemePrivacy();// 取跟单权限
			schemeId = spo_follow.getFollowedSchemeId();
		} else {// 代购方案并且不是本人的，或者是合买方案
			if (spo_follow.getType() == EntityType.BETTING_PARTNER
					&& displayMode == EntityType.DISPLAY_GROUPBUY) {// 合买 晒单
				privacy = spo_follow.getPrivacy();
			} else {// 代购、晒单
				privacy = spo_follow.getFollowSchemePrivacy();
			}
			schemeId = spo_follow.getId();
			spo = spo_follow;
			sponsorId = spo_follow.getSponsorId();
		}
		switch (privacy) {
		case EntityStatus.PRIVACY_PUBLIC:
			return true;
		case EntityStatus.PRIVACY_SOLD:
			return userId == sponsorId
					|| spo.getStatus() == EntityStatus.TICKET_NOT_WIN
					|| spo.getStatus() == EntityStatus.TICKET_NOT_AWARD
					|| spo.getStatus() == EntityStatus.TICKET_AWARDED;
		case EntityStatus.PRIVACY_FOLLOW_PUBLIC:
			return userId == sponsorId
					|| (null != betPartnerDao.get(spo.getId(), userId))
					|| (null != betSchemeDao.findFollowSchemesByUser(schemeId,
							userId));
		case EntityStatus.PRIVACY_FOLLOW:
			return userId == sponsorId
					|| (spo.getOfftime().before(new Date()) && null != betPartnerDao
							.get(spo.getId(), userId))
					|| (spo.getOfftime().before(new Date()) && (null != betSchemeDao
							.findFollowSchemesByUser(schemeId, userId)));
		case EntityStatus.PRIVACY_SECRET:
			return (userId == sponsorId);
		default:
			return false;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isRecommendUser(BetScheme scheme) {
		boolean isRecommend = false;
		List<RecommendUserPO> reUser = recommendUserDao
				.getRecommendUser(scheme);
		if (reUser != null && reUser.size() > 0) {
			isRecommend = true;
		}
		return isRecommend;
	}

	@Override
	@Transactional
	public void findMyJoinFollowList(Paging paging, int status,
			String lotteryId, Long schemeId, long sponsorid, String playId,
			String passType, Date from, Date to) {
		if (status == 0) {
			status = -1;
		}
		List<BetSchemePO> betSchemePOs = betSchemeDao.findMyJoinFollowScheme(
				paging, status, lotteryId, schemeId, sponsorid, playId,
				passType, from, to);
		List<BetScheme> betSchemes = new ArrayList<BetScheme>();
		BetScheme betScheme;
		if (betSchemePOs != null)
			for (BetSchemePO betSchemePO : betSchemePOs) {
				betScheme = new BetScheme();
				BeanUtils.copyProperties(betSchemePO, betScheme);
				betSchemes.add(betScheme);
			}
		paging.setResults(betSchemes);
	}

	@Override
	@Transactional
	public void findMyLaunchFollowList(Paging paging, int status,
			String lotteryId, Long schemeId, long sponsorid, String playId,
			String passType, Date from, Date to) {
		if (status == 0) {
			status = -1;
		}
		List<BetSchemePO> betSchemePOs = betSchemeDao.findLaunchShowingScheme(
				paging, status, lotteryId, schemeId, sponsorid, playId,
				passType, from, to);
		PagingUtils.makeCopyAndSetPaging(betSchemePOs, paging, BetScheme.class);
	}

	@Override
	@Transactional(readOnly = true)
	public void listCommissionOutByUserId(Long userId, Date startDate,
			Date endDate, Paging paging) {
		List<Object[]> objects = betSchemeDao.findCommissionOutList(paging,
				userId, startDate, endDate);
		if (objects == null)
			return;
		Map<Long, Object[]> ids = new HashMap<Long, Object[]>();
		for (Object[] object : objects) {
			ids.put(Long.parseLong(object[1].toString()), object);
		}
		List<OrderPO> orderPOs = orderDao.getByRelatedIds(
				EntityType.ORDER_COMMISSION_ADD, ids.keySet());
		if (orderPOs == null) {
			paging.setTotalCount(0);
			return;
		}
		List<Commission> commissions = new ArrayList<Commission>();
		Commission c;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (OrderPO op : orderPOs) {
			c = new Commission();
			Object[] o = ids.get(op.getRelatedId());
			if (o != null) {
				c.setCommission(op.getAmount());
				c.setSchemeId(Long.parseLong(o[0].toString()));
				c.setSponsor(op.getUsername());
				try {
					c.setCreatedTime(df.parse(o[2].toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				c.setFollowSchemeId(Long.parseLong(o[3].toString()));
				commissions.add(c);
			}
		}
		paging.setResults(commissions);
	}

	@Override
	@Transactional(readOnly = true)
	public void listCommissionInByUserId(Long userId, Date startDate,
			Date endDate, Paging paging) {
		List<OrderPO> orderPOs = orderDao.listCommission(paging, userId,
				startDate, endDate);
		if (orderPOs == null)
			return;
		Map<Long, OrderPO> ids = new HashMap<Long, OrderPO>();
		for (OrderPO op : orderPOs) {
			ids.put(op.getRelatedId(), op);
		}
		List<Object[]> betSchemePOs = betSchemeDao
				.findBetSchemeListByPartnerIds(ids.keySet());
		List<Commission> commissions = new ArrayList<Commission>();
		Commission c;
		for (Object[] bs : betSchemePOs) {
			c = new Commission();
			OrderPO op = ids.get(bs[2]);
			if (op != null) {
				c.setCommission(op.getAmount());
				c.setCreatedTime(op.getCreatedTime());
				c.setSponsor(bs[0].toString());
				c.setSchemeId(Long.parseLong(bs[3].toString()));
				c.setFollowSchemeId(Long.parseLong(bs[1].toString()));
				commissions.add(c);
			}
		}
		paging.setResults(commissions);
	}

	@Override
	@Transactional(readOnly = true)
	public void findMyJoinGroupbuyList(Paging paging, int status,
			String lotteryId, Long sponsorId, String playId, String passType,
			Date from, Date to) {
		if (status == 0) {
			status = -1;
		}
		List<BetSchemePO> betSchemePOs = betSchemeDao.findMyJoinGroupbuyScheme(
				paging, status, lotteryId, sponsorId, playId, passType, from,
				to);
		PagingUtils.makeCopyAndSetPaging(betSchemePOs, paging, BetScheme.class);
	}

	@Override
	@Transactional(readOnly = true)
	public void findMyLaunchGroupbuyList(Paging paging, int status,
			String lotteryId, long sponsorid, String playId, String passType,
			Date from, Date to) {
		if (status == 0) {
			status = -1;
		}
		List<BetSchemePO> betSchemePOs = betSchemeDao.findLaunchGroupbuyScheme(
				paging, status, lotteryId, sponsorid, playId, passType, from,
				to);
		PagingUtils.makeCopyAndSetPaging(betSchemePOs, paging, BetScheme.class);
	}

	@Transactional
	@Override
	public void award(int operatorId, List<Long> betSchemeIdList) {
		Date date = new Date();
		List<BetSchemePO> pos = betSchemeDao.find(betSchemeIdList);
		List<Long> schemeList = new ArrayList<Long>();
		
		Set<Long> sid = new HashSet<Long>();
		for (BetSchemePO betSchemePo : pos) {
			Map<Long, BigDecimal> awardMap = new HashMap<Long, BigDecimal>();

			// 验证状态
			if (EntityStatus.TICKET_NOT_AWARD != betSchemePo.getStatus()) {
				continue;
			}
			if(PresetSchemeTool.isPresetScheme(betSchemePo)) {
				BetSchemePresetPO bsPresetPO = betSchemePresetDao.get(betSchemePo.getId());
				if(null != bsPresetPO && bsPresetPO.getId() > 0) {
					bsPresetPO.setStatus(EntityStatus.TICKET_AWARDED);
					bsPresetPO.setAwardTime(new Date());
					List<Long> sids = new ArrayList<Long>();
					sids.add(bsPresetPO.getId());
					ticketPresetDao.updateStatusByScheme(sids, 
							EntityStatus.TICKET_NOT_AWARD, EntityStatus.TICKET_AWARDED, "派奖");
					
				}
			}
			
			// 发起人分成
			BigDecimal totalBonus = betSchemePo.getAfterTaxBonus();
			// 合买
			if (betSchemePo.getType() == EntityType.BETTING_PARTNER) {
				BigDecimal share = totalBonus.multiply(
						NumberUtils.percent(betSchemePo.getShareRatio()))
						.setScale(2, RoundingMode.DOWN);
				if (share.compareTo(BigDecimal.ZERO) == 1) {
					BetPartnerPO partnerPo = betPartnerDao.get(
							betSchemePo.getId(), betSchemePo.getSponsorId());
					accountService.giveCommission(operatorId,
							betSchemePo.getSponsorId(), share,
							partnerPo.getId());
				}
			}
			// 跟单
			if (betSchemePo.getType() == EntityType.BETTING_FOLLOW) {
				giveCommission(operatorId, betSchemePo);
			}

			// 给合买人派奖
			HashMap<Long, WinPO> wins = new HashMap<Long, WinPO>();
			for (BetPartnerPO ppo : betPartnerDao.findBySchemeId(betSchemePo
					.getId())) {
				WinPO win = wins.get(ppo.getId());

				if (win == null) {
					win = new WinPO();
					win.setUserId(ppo.getUserId());
					win.setSchemeId(betSchemePo.getId());
					win.setAmount(new BigDecimal(ppo.getBetAmount()));
					win.setBonus(ppo.getWinAmount());
					win.setCreatedTime(date);
				} else {
					win.setAmount(win.getAmount().add(
							new BigDecimal(ppo.getBetAmount())));
					win.setBonus(win.getBonus().add(ppo.getWinAmount()));
				}
				wins.put(ppo.getId(), win);
				accountService.devide(operatorId, ppo.getUserId(),
						ppo.getWinAmount(), ppo.getId(), "派奖");
			}
			for (WinPO wpo : wins.values()) {
				winDao.save(wpo);
				awardMap.put(wpo.getUserId(), wpo.getBonus());
			}
			if (winningNoticeService.checkWinningScheme(betSchemePo)) {
				schemeList.add(betSchemePo.getId());
			}

			// 修改状态
			betSchemePo.setStatus(EntityStatus.TICKET_AWARDED);
			betSchemePo.setAwardTime(new Date());
			sid.add(betSchemePo.getId());

			// 计算用户战绩
			if (betSchemePo.getType() == EntityType.BETTING_PARTNER
					|| betSchemePo.getShowScheme() == EntityType.SHOW_SCHEME) {
				userScoreService.countUserScore(betSchemePo);
			}
			// 计算用户晒单跟单中奖榜
			if (betSchemePo.getShowScheme() == EntityType.SHOW_SCHEME
					|| betSchemePo.getType() == EntityType.BETTING_FOLLOW) {
				winListService.countWinList(betSchemePo);
			}

			awardNoticeService.sendAwardNotic(awardMap,
					betSchemePo.getLotteryId());
			
			schemeCacheUpdateNotice.buildMsgAndSend(betSchemePo);
		}
		ticketDao.updateStatusByScheme(sid, EntityStatus.TICKET_NOT_AWARD,
				EntityStatus.TICKET_AWARDED, "派奖");

		winningNoticeService.sendWinningNew(schemeList);
	}

	/**
	 * 给佣金
	 * 
	 * @param operatorId
	 *            管理员id
	 * @param schemePo
	 *            被处理的方案
	 */
	private void giveCommission(Integer operatorId, BetSchemePO schemePo) {
		BigDecimal totalBonus = schemePo.getAfterTaxBonus();
		// 当税后奖金大于本金时才给晒单人佣金
		if (totalBonus.compareTo(new BigDecimal(schemePo.getTotalAmount())) == 1) {
			BetSchemePO showScheme = betSchemeDao.get(schemePo
					.getFollowedSchemeId());
			BigDecimal ratio = NumberUtils.percent(showScheme
					.getFollowedRatio());
			BigDecimal commission = totalBonus.multiply(ratio).setScale(2,
					RoundingMode.DOWN);
			// 计算后的佣金大于0就派发佣金
			if (commission.compareTo(BigDecimal.ZERO) == 1) {
				BetPartnerPO partnerPo = betPartnerDao.get(schemePo.getId(),
						schemePo.getSponsorId());
				accountService.giveCommission(operatorId,
						showScheme.getSponsorId(), commission,
						partnerPo.getId());
			}
		}
	}

	@Autowired
	private PhantomService phantomService;

	@Override
	@Transactional(readOnly = true)
	public List<BetScheme> findNewestWinScheme(String lotteryId, int maxResults) {
		List<BetScheme> betSchemes = new ArrayList<BetScheme>();
		List<BetSchemePO> betSchemePOs = betSchemeDao.findNewestWinScheme(
				lotteryId, EntityStatus.TICKET_AWARDED, maxResults);
		if (betSchemePOs != null && betSchemePOs.size() > 0) {
			for (BetSchemePO spo : betSchemePOs) {
				if (phantomService.isShadow(spo.getSponsorId())) {
					continue;
				}
				BetScheme bs = new BetScheme();
				BeanUtils.copyProperties(spo, bs);

				Play play = new Play();
				PlayPO playpo = playDao.get(bs.getPlayId());
				BeanUtils.copyProperties(playpo, play);
				bs.setPlay(play);

				betSchemes.add(bs);
			}
		}
		return betSchemes;
	}

	@Override
	@Transactional(readOnly = true)
	public List<BetSchemePO> queryShowingScheme(String schemeType,
			Integer firstResult, Integer pageMaxResult) {
		List<BetSchemePO> list = betSchemeDao.queryShowingScheme(schemeType,
				firstResult, pageMaxResult);
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<BetSchemePO> queryShowingSchemeAll(String schemeType,
			Integer firstResult, Integer pageMaxResult) {
		List<BetSchemePO> list = betSchemeDao.queryShowingSchemeAll(schemeType,
				firstResult, pageMaxResult);
		return list;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Object[]> findBetRecord(String lotteryId, long userId, int count) {
		if(StringUtils.isBlank(lotteryId) ||
				userId <= 0 || count <= 0) {
			return null;
		}
		return betSchemeDao.findBetRecord(lotteryId, userId, count);
	}

	@Override
	public List<Long> queryShowingSchemeBy(String fromDate, String followCountSort, String buyAmountSort,
			String rateOfReturnSort, String timeSort,String lottery) {
		List<Object[]> l = betSchemeDao.queryShowingSchemeBy(fromDate, followCountSort, buyAmountSort, rateOfReturnSort, timeSort,lottery);
		if(null == l || l.size() < 1){
			return null;
		} else {
			List<Long> ids = new ArrayList<Long>();
			for (Object[] o : l) {
				ids.add(((BigInteger)o[0]).longValue());
			}
			return ids;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<BetSchemePO> queryShowingGroupbuy(String schemeType,
			int parseInt, int pagingMaxResult) {
		List<BetSchemePO> list = betSchemeDao.queryShowingGroupbuy(schemeType,
				parseInt, pagingMaxResult);
		return list;
	}

	@Override
	@Transactional(readOnly=true)
	public int findTotalAmountLimit() {
		return systemConfDao.findIntValueByKey(SystemConf.KEY_BET_TOTAL_AMOUNT_LIMIT);
	}

	@Override
	@Transactional(readOnly=true)
	public List<BetSchemePO> queryShowingGroupbuyWithoutPage(String schemeType) {
		return betSchemeDao.queryShowingGroupbuyWithoutPage(schemeType);
	}

	@Override
	public void computeMinAndMaxBonus(BetScheme scheme, Bet bet) {
		 if(Constants.JCZQ.equals(scheme.getLotteryId())||Constants.JCLQ.equals(scheme.getLotteryId())){
	        	MaxAndMinBonusModel maxAndMinBonusModel = maxAndMinBonusUtil.maxAndMinBonusForJC(scheme.getMatchs(), scheme.getLotteryId(), scheme.getMatchScoreMap(),scheme.getPassTypeIds());
	            if(null!=maxAndMinBonusModel){
	            	scheme.setMaxBonus(new BigDecimal(maxAndMinBonusModel.getMaxBonus()* scheme.getMultiple()));
	            	scheme.setMinBonus(new BigDecimal(maxAndMinBonusModel.getMinBonus()*scheme.getMultiple()));
	            }
	        }
	        else{
	        	 scheme.setMaxBonus(new BigDecimal(bet.getMaxBonus()));
	        	 scheme.setMinBonus(BigDecimal.ZERO);
	        }
		
	}
}
