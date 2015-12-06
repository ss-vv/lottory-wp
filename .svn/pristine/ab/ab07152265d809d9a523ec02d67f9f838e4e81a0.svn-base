package com.xhcms.lottery.pb.action;

import java.util.List;

import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.exception.JXRuntimeException;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.pb.exception.IssueException;
import com.xhcms.lottery.pb.exception.PartnerBetException;
import com.xhcms.lottery.pb.model.Constants;
import com.xhcms.lottery.pb.model.MESSAGE_TYPE;
import com.xhcms.lottery.pb.service.PartnerBetService;
import com.xhcms.lottery.pb.xml.model.BetReq;
import com.xhcms.lottery.pb.xml.model.BetResp;
import com.xhcms.lottery.pb.xml.model.Msg;

public class BetAction extends BaseAction {
	private static final long serialVersionUID = -87038092983472325L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private List<BetReq> betReqs;
	private List<BetResp> betResps;
	@Autowired
	private PartnerBetService partnerBetService;
	
	public String execute(){
		init();
		if(betReqs.size() < 1){
			return this.errorMsg(this.partnerId, "404", "消息类型不正确");
		}
		BetReq betReq = betReqs.get(0);
		betReq.setPartnerId(this.partnerId);
		try {
			if(LotteryId.SSQ.name().equals(betReq.getLotteryType())){
				betResps = partnerBetService.betSSQ(betReq);
			} else if (LotteryId.FC3D.name().equals(betReq.getLotteryType())){
				betResps = partnerBetService.betFC3D(betReq); 
			} else {
				return this.errorMsg(this.partnerId, "405", "彩种类型不正确");
			}
		} catch (Throwable e) {
			betResps = partnerBetService.errorResult(betReq, exceptionProc(e));
		}
		response.setHeader(Constants.PARTNER_ID, partnerId);
		response.setHeader(Constants.MSG_TYPE,MESSAGE_TYPE.BET_RESP.getCode());
		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("partnerId", this.partnerId);
		velocityContext.put("version", this.version);
		velocityContext.put("time", this.time);
		velocityContext.put("betResps", this.betResps);
		response.setHeader(Constants.SIGNATURE, this.getSignatureMD5(velocityContext, "../vm/bet-result.xml"));
		return SUCCESS;
	}

	private void init() {
		Msg msg = (Msg)request.getAttribute(Constants.REQUESR_MSG);
		this.betReqs = msg.getBody().getBetReq();
		this.partnerId = request.getHeader(Constants.PARTNER_ID);
		this.reqTime = msg.getHead().getTime();
		logger.info("投注请求：partnerId={},reqTime={},"
				+ "betReq={}",new Object[]{
					this.partnerId,this.reqTime,this.betReqs
				});
	}
	
	/**
	 * 投注错误处理
	 * 
	 * @param e
	 * @return
	 */
	protected int exceptionProc(Throwable e) {
		e.printStackTrace();
		int code = -1;
		if (e instanceof XHRuntimeException) {
			code = ((XHRuntimeException) e).getCode();
			logger.error("XHRuntimeException,错误码：{}", code);
			if(AppCode.INSUFFICIENT_BALANCE == code){
				return Constants.BET_BALANCE_NOT_ENOUGH;
			}
			return Constants.BET_SYS_ERROR;
		}
		if (e instanceof JXRuntimeException) {
			code = ((JXRuntimeException) e).getErrorCode();
			logger.error("JXRuntimeException,错误码：{}", code);
			return Constants.BET_SYS_ERROR;
		}
		if (e instanceof BetException) {
			code = ((BetException) e).getErrorCode();
			logger.error("BetException,错误码：{}", code);
			if(AppCode.INSUFFICIENT_BALANCE==code){
				return Constants.BET_BALANCE_NOT_ENOUGH;
			} else {
				return Constants.BET_SYS_ERROR;
			}
		}
		if (e instanceof IssueException) {
			logger.error("IssueException,错误码：{}", code);
			return Constants.BET_ISSUE_ERROR;
		}
		if (e instanceof PartnerBetException) {
			code = ((PartnerBetException) e).getErrorCode();
			logger.error("PartnerBetException,错误码：{}", code);
			if(Constants.BET_MONEY_ERROR == code){
				return Constants.BET_MONEY_ERROR;
			}
			return Constants.BET_SYS_ERROR;
		}
		return Constants.BET_SYS_ERROR;
	}
	
	public List<BetResp> getBetResps() {
		return betResps;
	}
}
