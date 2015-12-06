package com.xhcms.lottery.dc.feed.web.action.ssq;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.dc.feed.data.JsonIssueInfo;
import com.xhcms.lottery.lang.LotteryId;

/**
 * 返回当前在售的双色球期信息.
 * 
 * @author Yang Bo
 */
public class AjaxIssueAction extends ActionSupport{
	private static final long serialVersionUID = 250556592293697546L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
 	@Autowired
 	private IssueService issueService;
	private JsonIssueInfo data;
	
	public String execute(){
		try {
			IssueInfo issueInfo = this.issueService.getCurrentSalingIssueOfWF(LotteryId.SSQ.name(), new java.util.Date());
			if (issueInfo.getIssueNumber() == null){
				data = new JsonIssueInfo();
				data.setSuccess(false);
				return SUCCESS;
			}
			long countDown = computeCountDown(issueInfo);
			this.data = new JsonIssueInfo(issueInfo, countDown);
			data.setSuccess(true);
		} catch (BetException exception) {
			logger.error("不能获取期信息。", exception);
			addActionError(getText("error." + exception.getErrorCode()));
		}
		return SUCCESS;
	}

	private long computeCountDown(IssueInfo issueInfo) {
		if (issueInfo.getStopTimeForUser()==null){
			return 0;
		}
		Date stopTime = issueInfo.getStopTimeForUser();
		Date now = new Date();
		return (stopTime.getTime() - now.getTime())/1000;
	}

	public JsonIssueInfo getData() {
		return data;
	}
}
