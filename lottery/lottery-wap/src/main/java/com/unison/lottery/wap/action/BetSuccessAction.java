package com.unison.lottery.wap.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.commons.persist.service.DigitalBetService;
import com.xhcms.ucenter.action.BaseAction;

public class BetSuccessAction extends BaseAction{
	private static final long serialVersionUID = 7861315462015477126L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String issue; 		// 期号
	private String lotteryId;
	private Integer playId;
	private Integer selectNum;
	private String playName;
	private Integer selectType;
	private Integer times;
	private String 	content;
	private Integer betCount;	// 注数
	private List<String> resultList = new ArrayList<String>();
	private BetScheme scheme;
	private IssueInfo issueInfo;	



	@Autowired
 	private IssueService issueService;

	public BetScheme getScheme() {
		return scheme;
	}

	public void setScheme(BetScheme scheme) {
		this.scheme = scheme;
	}

	public Integer getBetCount() {
		return betCount;
	}

	public void setBetCount(Integer betCount) {
		this.betCount = betCount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Integer getPlayId() {
		return playId;
	}

	public void setPlayId(Integer playId) {
		this.playId = playId;
	}

	public Integer getSelectNum() {
		return selectNum;
	}

	public void setSelectNum(Integer selectNum) {
		this.selectNum = selectNum;
	}

	public String getPlayName() {
		return playName;
	}

	public void setPlayName(String playName) {
		this.playName = playName;
	}

	public Integer getSelectType() {
		return selectType;
	}

	public void setSelectType(Integer selectType) {
		this.selectType = selectType;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}
	
 	public IssueInfo getIssueInfo() {
		return issueInfo;
	}

	public void setIssueInfo(IssueInfo issueInfo) {
		this.issueInfo = issueInfo;
	}

	@Override
	public String execute() {
		try {
			issueInfo = issueService.getCurrentSalingIssueForShow("JX11", new Date());
			issue=(String) request.getAttribute("issueNumber");
		} catch (BetException e) {
			log.info("取得当前在售期出错！");
		}
		
		return INPUT;
	}


	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public List<String> getResultList() {
		return resultList;
	}

	public void setResultList(List<String> resultList) {
		this.resultList = resultList;
	}
}

