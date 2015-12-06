package com.unison.lottery.weibo.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.weibo.common.nosql.HotRecommendDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.persist.QTMatchidDao;
import com.unison.lottery.weibo.common.service.LotteryService;
import com.unison.lottery.weibo.web.service.HotRecommendMatchService;
import com.unison.lottery.weibo.web.util.MatchWeiboComparator;
import com.xhcms.lottery.commons.data.HotAndRecommendMatch;
import com.xhcms.lottery.commons.persist.dao.BBMatchDao;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchDao;
import com.xhcms.lottery.commons.persist.dao.BetMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.utils.DateUtils;

public class HotRecommendMatchServiceImpl implements HotRecommendMatchService {

	@Autowired
	private BetMatchDao betMatchDao;
	@Autowired
	private QTMatchidDao qtMatchIdDao;
	@Autowired
	private FBMatchDao fbMatchDao;
	@Autowired
	private BBMatchDao bbMatchDao;
	@Autowired
	private BJDCMatchDao bjdcMatchDao;
	@Autowired
	private HotRecommendDao hotRecommendDao;
	@Autowired
	private LotteryService lotteryService;
	@Override
	@Transactional
	public List<HotAndRecommendMatch> getTop5HotRecommend() {
		 List<HotAndRecommendMatch> matchs=new ArrayList<HotAndRecommendMatch>();
		List<Object[]> matchList=betMatchDao.findTop5Match(DateUtils.todayBeginTime());
		if(matchList!=null&&matchList.size()>0){
			for(int i=0;i<matchList.size();i++){
				HotAndRecommendMatch hot=new HotAndRecommendMatch();
				Object[] obj=matchList.get(i);
				hot.setMatchId(Long.parseLong(obj[0].toString()));
				String lottery=obj[1].toString();
				hot.setLottery(lottery);
				hot.setRecommendCount(obj[2].toString());
				matchs.add(hot);
			}
			
		}
        for(int i=0;i<matchs.size();i++){
        	HotAndRecommendMatch hot=matchs.get(i);
        	getTeamPosition(hot);
        	getTeamInfo(hot);
        }
		return matchs;
	}
	
	private void getTeamPosition(HotAndRecommendMatch match){
		List<Object[]> positions=qtMatchIdDao.findTeamPosition(match.getMatchId());
		if(positions!=null && positions.size()>0){
			Object[] p=positions.get(0);
			if(p[0]!=null){
				match.setHostTeamPosition(p[0].toString());
			}
			if(p[1]!=null){
				match.setGuestTeamPostion(p[1].toString());
			}
			
		}
		
	}
	private void getTeamInfo(HotAndRecommendMatch match){
		String lottery=match.getLottery();
		if(lottery.equals(LotteryId.JCZQ.name())){
			FBMatchPO fb=fbMatchDao.findFBMatch(match.getMatchId());
			match.setHostTeamName(fb.getHomeTeamName());
			match.setGuestTeamName(fb.getGuestTeamName());
			match.setLeagueName(fb.getLeagueName());
		}else if(lottery.equals(LotteryId.JCLQ.name())){
	        BBMatchPO bb=bbMatchDao.findBBMatch(match.getMatchId());
	        match.setHostTeamName(bb.getHomeTeamName());
	        match.setGuestTeamName(bb.getGuestTeamName());
	        match.setLeagueName(bb.getLeagueName());
		}else if(lottery.equals(LotteryId.BJDC.name())){
			BJDCMatchPO bjdc=bjdcMatchDao.findMatch(match.getMatchId());
			match.setHostTeamName(bjdc.getHomeTeamName());
			match.setGuestTeamName(bjdc.getGuestTeamName());
			match.setLeagueName(bjdc.getLeagueName());
		}
		
	}

	@Override
	@Transactional
	public List<HotAndRecommendMatch> getTop5ShowWeiboRecommend() {
		List<HotAndRecommendMatch> hot=new ArrayList<HotAndRecommendMatch>();
		Date beginTime=DateUtils.todayBeginTime();
		List<FBMatchPO> fbMatchs=fbMatchDao.findFBMatchByPlayTime(beginTime);
		List<BBMatchPO> bbMatchs=bbMatchDao.findBBMatchByPlayTime(beginTime);
		List<BJDCMatchPO> bjdcMatchs=bjdcMatchDao.findBJDCMatchByPlayTime(beginTime);
		List<HotAndRecommendMatch> hotRecommend=new ArrayList<HotAndRecommendMatch>();
		getFBWeiboMatchCount(fbMatchs,hotRecommend);
		getBBWeiboMatchCount(bbMatchs,hotRecommend);
		getBJDCWeiboMatchCount(bjdcMatchs,hotRecommend);
	    Collections.sort(hotRecommend, new MatchWeiboComparator());
	    int count=0;
	    if(hotRecommend.size()>5){
           count=5;
	    }else{
	    	count=hotRecommend.size();
	    }
	    for(int i=0;i<count;i++){
	    	HotAndRecommendMatch hrm=hotRecommend.get(i);
	    	getTeamPosition(hrm);
	    	hot.add(hrm);
	    }
	    
		
		return hot;
	}
	private void getFBWeiboMatchCount(List<FBMatchPO> fb,List<HotAndRecommendMatch> hr){
		if(fb!=null &&fb.size()>0){
			
			for(FBMatchPO fbpo:fb){
				 HotAndRecommendMatch hrMatch=new HotAndRecommendMatch();
				 //
				 //String key=ConverterMatchId.converterMatchId(LotteryId.JCZQ.name(), fbpo.getId());
				 String key=lotteryService.getMatchContent(fbpo.getId().toString(),LotteryId.JCZQ.name());
				 String weiboKey=Keys.matchAllContentKey(key);
				 String tuiJianShiDanKey=Keys.matchRealDataKey(key);
				 Long count=hotRecommendDao.zcard(tuiJianShiDanKey)+hotRecommendDao.zcard(weiboKey);
				if(count>0){
					hrMatch.setHostTeamName(fbpo.getHomeTeamName());
					hrMatch.setGuestTeamName(fbpo.getGuestTeamName());
					hrMatch.setLeagueName(fbpo.getLeagueName());
					hrMatch.setMatchId(fbpo.getId());
					hrMatch.setRecommendCount(count+"");
					hrMatch.setLottery(LotteryId.JCZQ.name());
					hr.add(hrMatch);
				}
			}
			
		}
		
	}

     private void getBBWeiboMatchCount(List<BBMatchPO> bb,List<HotAndRecommendMatch> hr){
		if(bb!=null&& bb.size()>0){
			for(BBMatchPO bbpo:bb){
				HotAndRecommendMatch hrMatch=new HotAndRecommendMatch();
				//String key=ConverterMatchId.converterMatchId(LotteryId.JCLQ.name(), bbpo.getId());
				String key=lotteryService.getMatchContent(bbpo.getId().toString(),LotteryId.JCLQ.name());
				String weiboKey=Keys.matchAllContentKey(key);
				String tuiJianShiDan=Keys.matchRealDataKey(key);
				Long count=hotRecommendDao.zcard(tuiJianShiDan)+hotRecommendDao.zcard(weiboKey);
				if(count>0){
					hrMatch.setHostTeamName(bbpo.getHomeTeamName());
					hrMatch.setGuestTeamName(bbpo.getGuestTeamName());
					hrMatch.setLeagueName(bbpo.getLeagueName());
					hrMatch.setMatchId(bbpo.getId());
					hrMatch.setRecommendCount(count+"");
					hrMatch.setLottery(LotteryId.JCLQ.name());
					hr.add(hrMatch);
				}
			}
				
			}
			
		}
		
	
     private void getBJDCWeiboMatchCount(List<BJDCMatchPO> bjdc,List<HotAndRecommendMatch> hr){
    	 if(bjdc!=null&& bjdc.size()>0){
 			for(BJDCMatchPO bjdcpo:bjdc){
 				HotAndRecommendMatch hrMatch=new HotAndRecommendMatch();
 				//String key=ConverterMatchId.converterMatchId(LotteryId.BJDC.name(), bjdcpo.getId());
 				String key=lotteryService.getMatchContent(bjdcpo.getId().toString(),LotteryId.BJDC.name());
 				String weiboKey=Keys.matchAllContentKey(key);
 			    String tuiJianShiDanKey=Keys.matchRealDataKey(key);
 				Long count=hotRecommendDao.zcard(tuiJianShiDanKey)+hotRecommendDao.zcard(weiboKey);
 				if(count>0){
 					
 					hrMatch.setHostTeamName(bjdcpo.getHomeTeamName());
 					hrMatch.setGuestTeamName(bjdcpo.getGuestTeamName());
 					hrMatch.setLeagueName(bjdcpo.getLeagueName());
 					hrMatch.setMatchId(bjdcpo.getId());
 					hrMatch.setRecommendCount(count+"");
 					hrMatch.setLottery(LotteryId.BJDC.name());
 					hr.add(hrMatch);
 				}
 			}
 				
 			}
 		
 		
 	}
	

}
