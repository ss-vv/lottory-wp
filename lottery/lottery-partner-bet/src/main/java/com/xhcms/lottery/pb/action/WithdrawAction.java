package com.xhcms.lottery.pb.action;

import java.util.List;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.pb.model.Constants;
import com.xhcms.lottery.pb.model.MESSAGE_TYPE;
import com.xhcms.lottery.pb.service.PartnerWithdrawService;
import com.xhcms.lottery.pb.xml.model.Msg;
import com.xhcms.lottery.pb.xml.model.WithdrawReq;
import com.xhcms.lottery.pb.xml.model.WithdrawResp;

public class WithdrawAction extends BaseAction {
	private static final long serialVersionUID = 6290933563183298278L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private List<WithdrawReq> withdrawReqs;
	private WithdrawResp withdrawResp;
	@Autowired
	private PartnerWithdrawService partnerWithdrawService;
	public String execute(){
		init();
		WithdrawReq withdrawReq = new WithdrawReq();
		if(this.withdrawReqs.size() > 0){
			withdrawReq = withdrawReqs.get(0);
			withdrawReq.setPartnerId(this.partnerId);
			withdrawReq.setIpAddress(this.getIPaddress());
			withdrawResp = partnerWithdrawService.withdraw(withdrawReq);
		} else {
			withdrawResp = partnerWithdrawService.errorProc(withdrawReq);
		}
		response.setHeader(Constants.PARTNER_ID, partnerId);
		response.setHeader(Constants.MSG_TYPE,MESSAGE_TYPE.WITHDRAW_RESP.getCode());
		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("partnerId", this.partnerId);
		velocityContext.put("version", this.version);
		velocityContext.put("time", this.time);
		velocityContext.put("withdrawResp", this.withdrawResp);
		response.setHeader(Constants.SIGNATURE, this.getSignatureMD5(velocityContext, "../vm/withdraw-resp.xml"));
		return SUCCESS;
	}
	
	private void init(){
		Msg msg = (Msg)request.getAttribute(Constants.REQUESR_MSG);
		this.withdrawReqs = msg.getBody().getWithdrawReq();
		this.partnerId = request.getHeader(Constants.PARTNER_ID);
		this.reqTime = msg.getHead().getTime();
		logger.info("提现请求：partnerId={},reqTime={}",new Object[]{
					this.partnerId,this.reqTime
				});
		logger.info("请求参数：{}",withdrawReqs);
	}

	public WithdrawResp getWithdrawResp() {
		return withdrawResp;
	}
}
