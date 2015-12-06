package com.xhcms.lottery.commons.persist.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.persist.dao.BBMatchDao;
import com.xhcms.lottery.commons.persist.dao.BBMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchDao;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchPlayDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPO;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPlayPO;
import com.xhcms.lottery.commons.persist.service.CheckMatchService;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.utils.ResultUtils;

public class CheckMatchServiceImpl implements CheckMatchService {
	
	@Autowired
    protected FBMatchPlayDao fbMatchPlayDao;
	
	@Autowired
	protected FBMatchDao fbMatchDao;
	
	@Autowired
    protected BBMatchPlayDao bbMatchPlayDao;
	
	@Autowired
    protected BBMatchDao bbMatchDao;
	
	@Autowired
	protected BJDCMatchPlayDao bjdcMatchPlayDao;
	
	@Autowired
	protected BJDCMatchDao bjdcMatchDao;
	
	
	
	// XXX:增加新玩法需要增加对应的解析模式
    private Pattern getCodePattern(String playId){
        //让球胜平负、不让球胜平负
        if( playId.startsWith(Constants.PLAY_01_ZC) || 
        		playId.startsWith(Constants.PLAY_80_ZC) ){
            return Pattern.compile("\\d{4}([310]{1,3})");
        }
        //比分
        if(playId.startsWith(Constants.PLAY_02_ZC)){
            return Pattern.compile("\\d{4}(\\d{2,62})");
        }
        //总进球数
        if(playId.startsWith(Constants.PLAY_03_ZC)){
            return Pattern.compile("\\d{4}([01234567]{1,8})");
        }
        //半场胜平负
        if(playId.startsWith(Constants.PLAY_04_ZC)){
            return Pattern.compile("\\d{4}([310]{2,18})");
        }
        // 篮球
        // 让分胜负、胜负、大小分
        if (playId.startsWith(Constants.PLAY_06_LC) || playId.startsWith(Constants.PLAY_07_LC)|| playId.startsWith(Constants.PLAY_09_LC)) {
            return Pattern.compile("\\d{4}([12]{1,2})");
        }
        // 胜分差
        if (playId.startsWith(Constants.PLAY_08_LC)) {
            return Pattern.compile("\\d{4}([0-6]{2,24})");
        }
        //北京单场
        //胜平负
        if(playId.startsWith(Constants.PLAY_01_BD_SPF)){
        	
        	 return Pattern.compile("\\d{3}([310]{1,3})");
        }
        //进球数
        if(playId.startsWith(Constants.PLAY_02_BD_JQS)){
        	
        	 return Pattern.compile("\\d{3}([012345678]{1,8})");
        }
        //上下单双--
        if(playId.startsWith(Constants.PLAY_03_BD_SXDS)){
        	 //01 02 11 12   "\\d{3}(01?02?11?12?)"  \\d{3}([01|02|11|12]{1,4})
        	return Pattern.compile("\\d{3}([01|02|11|12]{1,8})");
        }
        //比分
        if(playId.startsWith(Constants.PLAY_04_BD_BF)){
        	
        	  return Pattern.compile("\\d{3}(\\d{2,62})");
        }
        //半全场
        if(playId.startsWith(Constants.PLAY_05_BD_BQC)){
        	
        	 return Pattern.compile("\\d{3}([310]{2,18})");
        }
        
        //胜负
        if(playId.startsWith(Constants.PLAY_06_BD_SF)){
        	
        	return Pattern.compile("\\d{3}([30]{1,2})");
        }

        return null;
    }
    
	
	 /**
     * 查找所有的 MatchPlay, 对混合过关模式要特殊处理。
     * @param playId 玩法id
     * @param matchs 比赛
     * @return 所有的 MatchPlay 对象。
     */
    private List<FBMatchPlayPO> findFBMatchPlay(String playId,
			List<BetMatch> matchs, Set<Long> idSet) {
    	PlayType playType = PlayType.valueOfLcPlayId(playId);
    	if (playType == PlayType.JCZQ_HH || playType == PlayType.JCZQ_FH) {
    		Set<FBMatchPlayPO> fps = new HashSet<FBMatchPlayPO>();
    		for (BetMatch m : matchs) {
    			HashSet<Long> ids = new HashSet<Long>();
    			ids.add(m.getMatchId());
    			fps.addAll(fbMatchPlayDao.find(m.getPlayId(), ids));
    		}
    		ArrayList<FBMatchPlayPO> list = new ArrayList<FBMatchPlayPO>();
    		list.addAll(fps);
    		return list;
    	}else{
    		return fbMatchPlayDao.find(playId, idSet);
    	}
	}

	@Override
	@Transactional(readOnly = true)
	public int checkMatch(String playId, List<BetMatch> matchs) {
		 Set<Long> idSet = new HashSet<Long>(matchs.size());
	        for(BetMatch m: matchs){
	            idSet.add(m.getMatchId());
	        }
	        if(playId.indexOf("ZC") > 0) {
	            List<FBMatchPlayPO> mps = findFBMatchPlay(playId, matchs, idSet);
	            List<FBMatchPO> ms = fbMatchDao.find(idSet);
	            if(mps.size() != idSet.size() && !PlayType.isFHMixBet(playId)){
	                return AppCode.MATCH_NOT_SUPPORT_PLAY;
	            }
	            HashMap<String, FBMatchPlayPO> mpMap = new HashMap<String, FBMatchPlayPO>(idSet.size());
	            HashMap<Long, FBMatchPO> mMap = new HashMap<Long, FBMatchPO>(idSet.size());
	            for(FBMatchPlayPO fbmp: mps){
	            	String fbPlayId = StringUtils.isBlank(fbmp.getPlayId()) ? "" : fbmp.getPlayId();
	                mpMap.put(fbmp.getMatchId() + fbPlayId, fbmp);
	            }
	            for(FBMatchPO fbm: ms){
	                mMap.put(fbm.getId(), fbm);
	            }
	            Pattern spliter = Pattern.compile(",");
	            
	            for(BetMatch bm: matchs){
	            	String bmPlayId = bm.getPlayId();
	            	if (StringUtils.isBlank(bmPlayId)) {
	            		bmPlayId = playId;
	            	}
	                Pattern p = getCodePattern(bmPlayId);
	                if(p == null){
	                    return AppCode.INVALID_PLAY;
	                }
	                FBMatchPlayPO po = mpMap.get(bm.getMatchId() + bmPlayId);
	                if(po == null){
	                    return AppCode.MATCH_NOT_SUPPORT_PLAY;
	                }
	                Matcher m = p.matcher(bm.getCode());
	                if(!m.find()){
	                    return AppCode.INVALID_BET_CODE;
	                }
	                bm.setEntrustDeadline(mMap.get(bm.getMatchId()).getEntrustDeadline());
	                bm.setPlayingTime(mMap.get(bm.getMatchId()).getPlayingTime());
	                
	                bm.setOdds(ResultUtils.resolveOdds(bmPlayId, m.group(1), spliter.split(po.getOptions()), spliter.split(po.getOdds())));
	                FBMatchPO match = mMap.get(bm.getMatchId());
	                bm.setDefaultScore(match.getConcedePoints());
	            }
	        } else if(playId.indexOf("LC") > 0){
	            List<BBMatchPlayPO> mps = findBBMatchPlay(playId, matchs, idSet);
	            List<BBMatchPO> ms = bbMatchDao.find(idSet);
	            if(mps.size() != idSet.size() && !PlayType.isFHMixBet(playId)){
	                return AppCode.MATCH_NOT_SUPPORT_PLAY;
	            }
	            HashMap<String, BBMatchPlayPO> mpMap = new HashMap<String, BBMatchPlayPO>(idSet.size());
	            HashMap<Long, BBMatchPO> mMap = new HashMap<Long, BBMatchPO>(idSet.size());
	            for(BBMatchPlayPO bbmp: mps){
	                mpMap.put(bbmp.getMatchId()+bbmp.getPlayId(), bbmp);
	            }
	            for(BBMatchPO bbm: ms){
	                mMap.put(bbm.getId(), bbm);
	            }
	            Pattern spliter = Pattern.compile(",");
	            
	            for(BetMatch bm: matchs){
	            	String bmPlayId = bm.getPlayId();
	            	if (StringUtils.isBlank(bmPlayId)) {
	            		bmPlayId = playId;
	            	}
	            	Pattern p = getCodePattern(bmPlayId);
	            	if(p == null){
	            		return AppCode.INVALID_PLAY;
	            	}
	                BBMatchPlayPO po = mpMap.get(bm.getMatchId() + bmPlayId);
	                if(po == null){
	                    return AppCode.MATCH_NOT_SUPPORT_PLAY;
	                }
	                Matcher m = p.matcher(bm.getCode());
	                if(!m.find()){
	                    return AppCode.INVALID_BET_CODE;
	                }
	                bm.setEntrustDeadline(mMap.get(bm.getMatchId()).getEntrustDeadline());
	                bm.setPlayingTime(mMap.get(bm.getMatchId()).getPlayingTime());
	                bm.setOdds(ResultUtils.resolveOdds(bmPlayId, m.group(1), spliter.split(po.getOptions()), spliter.split(po.getOdds())));
	                bm.setDefaultScore(po.getDefaultScore());
	            }
	        } else if(playId.indexOf("BJDC") > 0) {	//北京单场
	        	 List<BJDCMatchPlayPO> mps = findBJDCMatchPlay(playId, idSet);
	        	 List<BJDCMatchPO> ms=bjdcMatchDao.find(idSet);
	        	  if(mps.size() != idSet.size()){
	                  return AppCode.MATCH_NOT_SUPPORT_PLAY;
	              }
	              HashMap<Long, BJDCMatchPlayPO> mpMap = new HashMap<Long, BJDCMatchPlayPO>(idSet.size());
	              HashMap<Long, BJDCMatchPO> mMap = new HashMap<Long, BJDCMatchPO>(idSet.size());
	              for(BJDCMatchPlayPO mp: mps){
	                  mpMap.put(mp.getMatchId(), mp);
	              }
	              for(BJDCMatchPO mp: ms){
	                  mMap.put(mp.getId(), mp);
	              }
	              Pattern spliter = Pattern.compile(",");
	              
	              for(BetMatch bm: matchs){
	              	String pid = bm.getPlayId();
	              	if (StringUtils.isBlank(pid)) {
	              		pid = playId;
	              	}
	              	Pattern p = getCodePattern(pid);
	              	if(p == null){
	              		return AppCode.INVALID_PLAY;
	              	}
	              	BJDCMatchPlayPO po = mpMap.get(bm.getMatchId());
	                  if(po == null){
	                      return AppCode.MATCH_NOT_SUPPORT_PLAY;
	                  }
	                  Matcher m = p.matcher(bm.getCode());
	                  if(!m.find()){
	                      return AppCode.INVALID_BET_CODE;
	                  }
	                  bm.setEntrustDeadline(mMap.get(bm.getMatchId()).getEntrustDeadline());
	                  bm.setPlayingTime(mMap.get(bm.getMatchId()).getPlayingTime());
	                  bm.setOdds(ResultUtils.resolveOdds(pid, m.group(1), spliter.split(po.getOptions()), spliter.split(po.getOdds())));
	              }
	        }
	        return 0;
	}


	private List<BJDCMatchPlayPO> findBJDCMatchPlay(String playId,
			Set<Long> idSet) {
		return bjdcMatchPlayDao.find(playId, idSet);
	}


	private List<BBMatchPlayPO> findBBMatchPlay(String playId,
			List<BetMatch> matchs, Set<Long> idSet) {
		PlayType playType = PlayType.valueOfLcPlayId(playId);
    	if (playType == PlayType.JCLQ_HH || playType == PlayType.JCLQ_FH){
    		List<BBMatchPlayPO> fps = new LinkedList<BBMatchPlayPO>();
    		for (BetMatch m : matchs){
    			HashSet<Long> ids = new HashSet<Long>();
    			ids.add(m.getMatchId());
    			fps.addAll(bbMatchPlayDao.find(m.getPlayId(), ids));
    		}
    		return fps;
    	}else{
    		return bbMatchPlayDao.find(playId, idSet);
    	}
	}

}
