package com.unison.lottery.weibo.common.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.common.service.LCIssueService;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.ctfb.CTMatchInfo;
import com.xhcms.lottery.commons.persist.service.IssueService;

@Service
public class LCIssueServiceImpl implements LCIssueService {
	@Autowired
	private IssueService issueService;
	
	@Override
	public String[][] getCTIssueInfo(String playId, String targetIssueNumber) {
		CTMatchInfo ctMatchInfo = new CTMatchInfo();
		if(StringUtils.isBlank(playId)){
			ctMatchInfo.setCode(1);
			ctMatchInfo.setMessage("玩法不能为空！");
			return null;
		}
		Calendar beginStopTime=Calendar.getInstance();
		Date now = new Date();
        beginStopTime.setTime(now);
        beginStopTime.add(Calendar.MONTH, -1);
        
		IssueInfo targetIssueInfo = null;
		List<IssueInfo> oldIssueInfos = new ArrayList<IssueInfo>();
		List<IssueInfo> onSaleIssueInfos = new ArrayList<IssueInfo>();
		List<IssueInfo> allIssueInfos = issueService.findIssuesBetweenStopTimeForUser(playId,beginStopTime.getTime(),null);
		Set<String> setResult = new HashSet<String>();
		if(null==allIssueInfos || allIssueInfos.isEmpty() ){
			return null;
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
			setResult.add(issueInfo.getIssueNumber());
		}
		if(targetIssueInfo == null){
			targetIssueInfo = onSaleIssueInfos.isEmpty()?oldIssueInfos.get(oldIssueInfos.size()-1):onSaleIssueInfos.get(0);
		}
		ctMatchInfo.setIssueInfo(targetIssueInfo);
		ctMatchInfo.setIssueInfos(onSaleIssueInfos);
		ctMatchInfo.setOldIssueInfos(oldIssueInfos);
		
		// 只取期号，并排序
		String[][] result = new String[2][setResult.size()];
		String[] issueInfos = setResult.toArray(new String[setResult.size()]);
		Arrays.sort(issueInfos);
		// 最近一期
		result[0]=new String[]{targetIssueInfo.getIssueNumber()};
		// 所有期
		result[1]=issueInfos;
		return result;
	}
}
