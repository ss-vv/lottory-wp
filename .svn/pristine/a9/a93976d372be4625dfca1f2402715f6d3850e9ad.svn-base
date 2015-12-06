package com.xhcms.lottery.pb.action;

import java.util.Date;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.pb.model.Constants;
import com.xhcms.lottery.pb.model.MESSAGE_TYPE;
import com.xhcms.lottery.pb.util.DateTimeUtil;
import com.xhcms.lottery.pb.xml.model.IssueInfoReq;
import com.xhcms.lottery.pb.xml.model.Msg;

public class IssueInfoAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String lotteryType;
	private String startTime;
	private String deadline;
	private String currentIssueNumber;
	
	@Autowired
	private IssueService issueService;
	
	public String execute(){
		IssueInfo issueInfo = null;
		try{
			init();
			if(!LotteryId.SSQ.name().equals(lotteryType)
				&& !LotteryId.FC3D.name().equals(lotteryType)){
				return this.errorMsg(this.partnerId, "405", "彩种类型不正确");
			}
			issueInfo = issueService.findCurrentOnSaling(LotteryId.get(lotteryType).name(), new Date());
		} catch (Throwable t){
			logger.error("期查询出错:",t);
		}
		if(null == issueInfo){
			this.deadline = DateTimeUtil.getTimeString(new Date());
			this.currentIssueNumber = "-1";//没有可投注的期号
			this.startTime = DateTimeUtil.getTimeString(new Date());
		} else {
			this.deadline = DateTimeUtil.getTimeString(issueInfo.getStopTime());
			this.currentIssueNumber = issueInfo.getIssueNumber();
			this.startTime = DateTimeUtil.getTimeString(issueInfo.getStartTime());
		}
		response.setHeader(Constants.PARTNER_ID, partnerId);
		response.setHeader(Constants.MSG_TYPE,MESSAGE_TYPE.ISSUE_INFO_RESP.getCode());
		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("partnerId", this.partnerId);
		velocityContext.put("version", this.version);
		velocityContext.put("time", this.time);
		velocityContext.put("startTime", this.startTime);
		velocityContext.put("deadline", this.deadline);
		velocityContext.put("currentIssueNumber", this.currentIssueNumber);
		velocityContext.put("lotteryType", this.lotteryType);
		response.setHeader(Constants.SIGNATURE, this.getSignatureMD5(velocityContext,"../vm/issue-info.xml"));
	
		return SUCCESS;
	}
	private void init(){
		Msg msg = (Msg)request.getAttribute(Constants.REQUESR_MSG);
		IssueInfoReq issueInfoReq = msg.getBody().getIssueInfoReq();
		this.lotteryType = issueInfoReq.getLotteryType();
		this.partnerId = request.getHeader(Constants.PARTNER_ID);
		this.reqTime = msg.getHead().getTime();
		logger.info("期信息请求：partnerId={},lotteryType={},"
				+ "reqTime={}",new Object[]{
					this.partnerId,this.lotteryType,this.reqTime
				});
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public String getDeadline() {
		return deadline;
	}

	public String getCurrentIssueNumber() {
		return currentIssueNumber;
	}

	public String getStartTime() {
		return startTime;
	}
}