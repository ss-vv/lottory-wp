package com.xhcms.lottery.dc.feed.persist.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.ctfb.CTMatchInfo;
import com.xhcms.lottery.commons.persist.dao.CTFBMatchDao;
import com.xhcms.lottery.commons.persist.dao.MatchColorDao;
import com.xhcms.lottery.commons.persist.entity.CTFBMatchPO;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.dc.feed.persist.service.CTMatchService;
import com.xhcms.lottery.utils.POUtils;
import com.xhcms.lottery.lang.PlayType;;

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
	
	@Override
	@Transactional(readOnly=true)
	public List<CTFBMatch> listCTFB(String playId,String issueNumber) {
		//获取赛程信息列表
		List<CTFBMatchPO> matchsPO = cTFBMatchDao.findByIssueNoAndPlayId(issueNumber,playId);
		List<CTFBMatch> matchs = null;
		
		if(matchsPO !=null && !matchsPO.isEmpty()){
			matchs = POUtils.copyBeans(matchsPO, CTFBMatch.class);
			//得到联赛名称集合
			Map<String,String> matchMP = new HashMap<String, String>();
			for(CTFBMatch m:matchs){
				matchMP.put(m.getLeagueName(), null);
			}
			//查询赛事对应颜色列表
			Map<String, String> colors = matchColorDao.listColorsByLeagueNames(matchMP.keySet());
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

		targetCTFBMatchs = targetIssueInfo==null?null:this.listCTFB(playId,targetIssueInfo.getIssueNumber());
		
		ctMatchInfo.setIssueInfo(targetIssueInfo);
		ctMatchInfo.setIssueInfos(onSaleIssueInfos);
		ctMatchInfo.setOldIssueInfos(oldIssueInfos);
		ctMatchInfo.setCtFBMatchs(targetCTFBMatchs);
		return ctMatchInfo;
	}
}
