package com.xhcms.lottery.commons.persist.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.data.CTFBMatchPreset;
import com.xhcms.lottery.commons.data.FBMatch;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.OpenSaleTime;
import com.xhcms.lottery.commons.data.ctfb.CTMatchInfo;
import com.xhcms.lottery.commons.persist.dao.CTFBMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.dao.LotteryOpenSaleDao;
import com.xhcms.lottery.commons.persist.dao.MatchColorDao;
import com.xhcms.lottery.commons.persist.dao.SystemConfDao;
import com.xhcms.lottery.commons.persist.entity.CTFBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.persist.entity.LotteryOpenSalePO;
import com.xhcms.lottery.commons.persist.service.CTMatchService;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.commons.util.OpenSaleTimeUtil;
import com.xhcms.lottery.conf.SystemConf;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.utils.POUtils;

/**
 * 传统足彩赛事
 * @author Wang Lei
 *
 */
public class CTMatchServiceImpl implements CTMatchService {

	@Autowired
	CTFBMatchDao cTFBMatchDao;
	@Autowired
	MatchColorDao matchColorDao;
	@Autowired
	private IssueService issueService;
	@Autowired
	private SystemConfDao systemConfDao;
	@Autowired
	private LotteryOpenSaleDao lotteryOpenSaleDao;
	@Autowired
	private FBMatchDao fbMatchDao;
	@Override
	@Transactional(readOnly=true)
	public List<CTFBMatch> listCTFB(String playId,String issueNumber, Paging paging) {
		//获取赛程信息列表
		List<CTFBMatchPO> matchsPO = cTFBMatchDao.findByIssueNoAndPlayId(issueNumber,playId, paging);
		List<CTFBMatch> matchs = null;
		
		if(matchsPO !=null && !matchsPO.isEmpty()){
			matchs = POUtils.copyBeans(matchsPO, CTFBMatch.class);
			//得到联赛名称集合
			Map<String,String> matchMP = new HashMap<String, String>();
			for(CTFBMatch m:matchs){
				matchMP.put(m.getLeagueName(), null);
			}
			//查询赛事对应颜色列表
			Map<String, String> colors = matchColorDao.listColorsByLeagueShortNames(matchMP.keySet());
			//获取投注选项
			String options = PlayType.getOptionByPlayId(playId);
			//将颜色信息与投注选项信息加入赛事
			for(CTFBMatch m:matchs){
				m.setColor(colors.get(m.getLeagueName()));
				m.setOptions(options);
				m.setOdds(options);
				m.setCode(m.getMatchId().toString());
				m.setName(m.getHomeTeamName() + "VS" + m.getGuestTeamName());
			}
		}
		return matchs;
	}

	@Override
	@Transactional(readOnly=true)
	public CTMatchInfo getCTMatchInfo(String playId, String targetIssueNumber) {
		CTMatchInfo ctMatchInfo = new CTMatchInfo();
		if(StringUtils.isBlank(playId)){
			ctMatchInfo.setCode(1);
			ctMatchInfo.setMessage("玩法不能为空！");
			return ctMatchInfo;
		}
		Calendar beginStopTime=Calendar.getInstance();
		Date now = new Date();
        beginStopTime.setTime(now);
        beginStopTime.add(Calendar.MONTH, -1);
        
		IssueInfo targetIssueInfo = null;
		List<IssueInfo> oldIssueInfos = new ArrayList<IssueInfo>();
		List<IssueInfo> onSaleIssueInfos = new ArrayList<IssueInfo>();
		List<CTFBMatch> targetCTFBMatchs =null;
		List<IssueInfo> allIssueInfos = issueService.findIssuesBetweenStopTimeForUser(playId,beginStopTime.getTime(),null);
		if(null==allIssueInfos || allIssueInfos.isEmpty() ){
			return ctMatchInfo;
		}
		for(IssueInfo issueInfo:allIssueInfos){
			if(issueInfo.getStopTimeForUser().after(now)){
				onSaleIssueInfos.add(issueInfo);
			}else{
				oldIssueInfos.add(issueInfo);
			}
			if(StringUtils.isNotBlank(targetIssueNumber) &&  issueInfo.getIssueNumber().equals(targetIssueNumber)){
				targetIssueInfo = issueInfo;
			}
		}
		if(targetIssueInfo == null){
			targetIssueInfo = onSaleIssueInfos.isEmpty()?oldIssueInfos.get(oldIssueInfos.size()-1):onSaleIssueInfos.get(0);
		}

		targetCTFBMatchs = targetIssueInfo==null?null:this.listCTFB(playId,targetIssueInfo.getIssueNumber(), null);
		
		ctMatchInfo.setIssueInfo(targetIssueInfo);
		ctMatchInfo.setIssueInfos(onSaleIssueInfos);
		ctMatchInfo.setOldIssueInfos(oldIssueInfos);
		ctMatchInfo.setCtFBMatchs(targetCTFBMatchs);
		return ctMatchInfo;
	}

	@Override
	@Transactional
	public CTMatchInfo getCTIssueInfo(String playId, String targetIssueNumber) {
		CTMatchInfo ctMatchInfo = new CTMatchInfo();
		if(StringUtils.isBlank(playId)){
			ctMatchInfo.setCode(1);
			ctMatchInfo.setMessage("玩法不能为空！");
			return ctMatchInfo;
		}
		Calendar beginStopTime=Calendar.getInstance();
		Date now = new Date();
        beginStopTime.setTime(now);
        beginStopTime.add(Calendar.MONTH, -1);
        
		IssueInfo targetIssueInfo = null;
		List<IssueInfo> oldIssueInfos = new ArrayList<IssueInfo>();
		List<IssueInfo> onSaleIssueInfos = new ArrayList<IssueInfo>();
		List<IssueInfo> allIssueInfos = issueService.findIssuesBetweenStopTimeForUser(playId,beginStopTime.getTime(),null);
		if(null==allIssueInfos || allIssueInfos.isEmpty() ){
			return ctMatchInfo;
		}
		for(IssueInfo issueInfo:allIssueInfos){
			if(issueInfo.getStopTimeForUser().after(now)){
				onSaleIssueInfos.add(issueInfo);
			}else{
				oldIssueInfos.add(issueInfo);
			}
			if(StringUtils.isNotBlank(targetIssueNumber) &&  issueInfo.getIssueNumber().equals(targetIssueNumber)){
				targetIssueInfo = issueInfo;
			}
		}
		if(targetIssueInfo == null){
			targetIssueInfo = onSaleIssueInfos.isEmpty()?oldIssueInfos.get(oldIssueInfos.size()-1):onSaleIssueInfos.get(0);
		}
		//修改当前期的截止时间
		
		Integer beforeCloseMinute=systemConfDao.findIntValueByKey(SystemConf.KEY_BEFORE_CLOSE_MINUTE);
		
//		now=DateUtils.addMinutes(now, beforeCloseMinute);
		if(null!=targetIssueInfo){
			Date closeTime = targetIssueInfo.getCloseTime();
			List<LotteryOpenSalePO> openAndEndTimes=this.lotteryOpenSaleDao.findOpenAndEndTime();
			OpenSaleTime openSaleTime=OpenSaleTimeUtil.transferToOpenSaleTime(openAndEndTimes,now);
			if(null!=openSaleTime){
				Date newCloseTime=DateUtils.addMinutes(closeTime, -1*beforeCloseMinute);
				if(newCloseTime.compareTo(openSaleTime.getTodayOpenDateTime())<=0){//当前期结束时间提前一小时后，仍然早于今日投注开始时间
					targetIssueInfo=null;//当前期无法投注
				}
				else if(newCloseTime.compareTo(openSaleTime.getTodayOpenDateTime())>0&&newCloseTime.compareTo(openSaleTime.getTodayEndDateTime())<0){
					targetIssueInfo.setStopTimeForUser(newCloseTime);//当前期的截止时间要提前beforeCloseMinute分钟
					if(now.compareTo(targetIssueInfo.getStopTimeForUser())>=0){//当前时间已经晚于期的新截止时间
						targetIssueInfo=null;//当前期无法投注
					}
				}
				else if(newCloseTime.compareTo(openSaleTime.getTodayEndDateTime())>=0&&newCloseTime.compareTo(openSaleTime.getTomorrowOpenDateTime())<0){
					targetIssueInfo.setStopTimeForUser(openSaleTime.getTodayEndDateTime());//当前期的截止时间是今日投注结束时间
					if(now.compareTo(targetIssueInfo.getStopTimeForUser())>=0){//当前时间已经晚于今日投注结束时间
						targetIssueInfo=null;//当前期无法投注
					}
					
					
				}
				else if(newCloseTime.compareTo(openSaleTime.getTomorrowOpenDateTime())>=0){//当前期的截止时间提前一小时后，晚于明日投注开始时间
					targetIssueInfo.setStopTimeForUser(newCloseTime);//当前期的截止时间要提前beforeCloseMinute分钟
					
				}
			}
			if(null!=targetIssueInfo){
				if(now.compareTo(targetIssueInfo.getStopTimeForUser())<0){
					ctMatchInfo.setIssueInfo(targetIssueInfo);
				}
				
			}
		}
		
		
		ctMatchInfo.setIssueInfos(onSaleIssueInfos);
		ctMatchInfo.setOldIssueInfos(oldIssueInfos);
		return ctMatchInfo;
	}

	@Override
	@Transactional
	public List<CTFBMatchPreset>  getCTMatchByIssue(String issue,String playId) {
		List<CTFBMatchPO> ctfbList=cTFBMatchDao.findCTFBMatchByIssue(issue,playId);
		List<CTFBMatchPreset> prsetList=new ArrayList<CTFBMatchPreset>();
		Map<String,CTFBMatchPreset> presetMap=new HashMap<String,CTFBMatchPreset>();
		if(!ctfbList.isEmpty() && ctfbList.size()>0){
			for(CTFBMatchPO p:ctfbList){
				CTFBMatchPreset ctfb=new CTFBMatchPreset();
				BeanUtils.copyProperties(p, ctfb);
				/*CTFBMatch ctfb=new CTFBMatch();
				BeanUtils.copyProperties(p, ctfb);
				list.add(ctfb);*/
				setFBMatch(ctfb);
				presetMap.put(ctfb.getId(), ctfb);
			}
		}
		//return list;
		List<Object> obj=cTFBMatchDao.findCTFBMatchByIssue_(issue, playId);
	    if(obj!=null && obj.size()>0){
	    	for(int i=0;i<obj.size();i++){
	    		Object o[]=(Object[])obj.get(i);
	    		if(presetMap.size()>0){
	    			String id=o[0].toString();
	    			CTFBMatchPreset match=presetMap.get(id);
	    			if(match!=null){
	    				match.setFbHalfScore(o[1]!=null?o[1].toString():"");
	    				match.setFbScore(o[2]!=null?o[2].toString():"");
	    				match.setFbStatus(Integer.parseInt(o[3].toString()));
	    			}
	    		}
	    	}
	    }
	    List<Map.Entry<String, CTFBMatchPreset>> temp=new ArrayList<Map.Entry<String, CTFBMatchPreset>>(presetMap.entrySet());
	    Collections.sort(temp, new Comparator<Map.Entry<String, CTFBMatchPreset>>(){
			@Override
			public int compare(Entry<String, CTFBMatchPreset> arg0,
					Entry<String, CTFBMatchPreset> arg1) {
				return (int)(arg0.getValue().getMatchId()-arg1.getValue().getMatchId());
			}
	    });
	    for(Map.Entry<String, CTFBMatchPreset> mapping:temp){
	    	prsetList.add(mapping.getValue());
	    }
		return prsetList;
	}
	@Override
	@Transactional
	public void updateCTMatchScore(String id, String halfScore, String score,String status) {
		CTFBMatchPO ctfb=cTFBMatchDao.findCTFBMatchById(id);
		if(ctfb!=null){
			/*CTFBMatch ct=new CTFBMatch();
			BeanUtils.copyProperties(ctfb, ct);*/
			ctfb.setHalfScore(halfScore);
			ctfb.setScore(score);
			if(StringUtils.isNotBlank(status)){
				ctfb.setStatus(Integer.parseInt(status));
			}
			cTFBMatchDao.updateCTFBMatchScore(ctfb);
		}
	}
	private void setFBMatch(CTFBMatchPreset ctfb){
		List<FBMatchPO> fbpo=fbMatchDao.getFBMatchByPlayTime_(ctfb.getOfftime(),
				com.xhcms.lottery.utils.DateUtils.getWeekDayWithTime(ctfb.getOfftime()));
		if(fbpo!=null && fbpo.size()>0){
			List<FBMatch> fbMatchs=new ArrayList<FBMatch>();
			for(FBMatchPO fb:fbpo){
				FBMatch m=new FBMatch();
				BeanUtils.copyProperties(fb, m);
				fbMatchs.add(m);
			}
			ctfb.setFbMatchs(fbMatchs);
		}
	}
}
