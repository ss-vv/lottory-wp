package com.unison.lottery.weibo.web.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.service.store.data.QTMatchVO;
import com.unison.lottery.weibo.data.service.store.persist.dao.QTMatchDao;
import com.unison.lottery.weibo.web.service.ScoreService;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.Constants;

/**
 * 微博比分直播
 * @author Wang Lei
 *
 */
@Service
public class ScoreServiceImpl implements ScoreService {
	@Autowired
	private QTMatchDao qTMatchDao;
	@Autowired
	private IssueService issueService;
	
	@Override
	@Transactional(readOnly=true)
	public PageResult<QTMatchVO> listJCZQscore(Date date){
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		startTime.setTime(date);
		endTime.setTime(date);
		
		startTime.set(Calendar.HOUR_OF_DAY, 0);
		startTime.set(Calendar.MINUTE, 0);
		startTime.set(Calendar.SECOND, 0);
		
		endTime.set(Calendar.HOUR_OF_DAY, 23);
		endTime.set(Calendar.MINUTE, 59);
		endTime.set(Calendar.SECOND, 59);
		return new PageResult<QTMatchVO>(new PageRequest(), qTMatchDao.findQTMatchByDate(startTime.getTime(), endTime.getTime()));
	}
	
	@Override
	@Transactional(readOnly=true)
	public PageResult<QTMatchVO> listCTZCscore(String issueNumber){
		if(StringUtils.isBlank(issueNumber)){
			List<IssueInfo> issueInfos = issueService.findIssuesBetweenStopTimeForUser(Constants.PLAY_24_ZC_14, null, null);
			if(issueInfos == null || issueInfos.isEmpty()){
				return new PageResult<QTMatchVO>(new PageRequest(), new ArrayList<QTMatchVO>());
			}
			issueNumber =  "20" + issueInfos.get(0).getIssueNumber();
		}else{
			issueNumber = "20" + issueNumber;
		}
		return new PageResult<QTMatchVO>(new PageRequest(),qTMatchDao.findQTMatchByIssueNum(issueNumber));
	}
	

}
