package com.unison.lottery.api.protocol.response.xml.parser.methodparse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Item;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.Response;
import com.unison.lottery.api.protocol.response.model.UserDetailResponse;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.Recharge;
import com.xhcms.lottery.commons.data.Win;
import com.xhcms.lottery.commons.data.Withdraw;

public class UserDetailMethodResponseParser extends
		AbstractMethodResponseParser {

	private String datePattern="yyyy-MM-dd HH:mm:ss";

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.USER_DETAIL_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		UserDetailResponse userDetailResponse=(UserDetailResponse) responseFromHttpRequest;
		if(null!=userDetailResponse){
			if(null!=userDetailResponse.getAccount()){
				resultResponse.free=userDetailResponse.getAccount().getFree();
				resultResponse.grant=userDetailResponse.getAccount().getGrant();
				resultResponse.fund=userDetailResponse.getAccount().getFund();
				resultResponse.frozenFund=userDetailResponse.getAccount().getFrozenFund();
				resultResponse.frozenGrant=userDetailResponse.getAccount().getFrozenGrant();
				resultResponse.totalRecharge=userDetailResponse.getAccount().getTotalRecharge();
				resultResponse.totalWithdraw=userDetailResponse.getAccount().getTotalWithdraw();
				resultResponse.totalBet=userDetailResponse.getAccount().getTotalBet();
				resultResponse.totalAward=userDetailResponse.getAccount().getTotalAward();
				resultResponse.totalCommission=userDetailResponse.getAccount().getTotalCommission();
			}
			if(null!=userDetailResponse.getRechargeList()
					&&!userDetailResponse.getRechargeList().isEmpty()){
				handleRechargeList(userDetailResponse.getRechargeList(),resultResponse,userDetailResponse.getAccount());
			}
			
			if(null!=userDetailResponse.getWinList()){
				handleWinList(userDetailResponse.getWinList(),resultResponse);
			}
			if(null!=userDetailResponse.getWithdrawList()){
				handleWithdrawList(userDetailResponse.getWithdrawList(),resultResponse);
			}
				
			
		}
		

	}

	private void handleWithdrawList(List<Withdraw> withdrawList,
			Response resultResponse) {
		
		if(null!=withdrawList&&!withdrawList.isEmpty()){
			resultResponse.result=new Result();
			resultResponse.result.item = new ArrayList<Item>();
			for(Withdraw withdraw:withdrawList){
				Item resultItem = new Item();
				resultItem.date=toDateString(withdraw.getCreatedTime());
				resultItem.withdrawAmount=withdraw.getAmount();
				resultItem.serviceCharge=withdraw.getTotalFee();
				resultItem.statusDesc=makeWithdrawStatus(withdraw.getStatus());
				resultResponse.result.item.add(resultItem);
			}
		}
		
	}

	/**
	 *  int WITHDRAW_INIT = 0;      // 待审核
     *	int WITHDRAW_AUDIT = 1;     // 已审核，待付款
     *	int WITHDRAW_PAYED = 2;     // 已付款，待确认
     *	int WITHDRAW_FINISH = 90;   // 已完成
     *	int WITHDRAW_REJECT = 98;   // 驳回
     *	int WITHDRAW_FAIL = 99;     // 提现失败
	 * @param status
	 * @return
	 */
	private String makeWithdrawStatus(int status) {
		String result=null;
		switch(status){
			case 0:{result="待审核";break;}
			case 1:{result="已审核,待付款";break;}
			case 2:{result="已付款，待确认";break;}
			case 90:{result="已完成";break;}
			case 98:{result="驳回";break;}
			case 99:{result="提现失败";break;}
			default:{result=null;}
		}
		return result;
	}

	private void handleWinList(List<Win> winList, Response resultResponse) {
		
		if(null!=winList&&!winList.isEmpty()){
			resultResponse.result=new Result();
			resultResponse.result.item = new ArrayList<Item>();
			for(Win win:winList){
				Item resultItem = new Item();
				resultItem.date=toDateString(win.getCreatedTime());
				resultItem.schemeId=win.getSchemeId();
				resultItem.betAmount=win.getAmount();
				resultItem.bounus=win.getBonus();
				resultResponse.result.item.add(resultItem);
			}
		}
		
	}

	private void handleRechargeList(List<Recharge> rechargeList, 
			Response resultResponse, Account account) {
		
		if(null!=rechargeList&&!rechargeList.isEmpty()){
			resultResponse.result=new Result();
			resultResponse.result.item = new ArrayList<Item>();
			for(Recharge recharge:rechargeList){
				Item resultItem = new Item();
				resultItem.date=toDateString(recharge.getCreatedTime());
				resultItem.chargeAmount=recharge.getAmount();
				resultItem.serviceCharge=recharge.getTotalFee();
				resultItem.statusDesc=makeRechargeStatus(recharge.getStatus());
				resultItem.note=recharge.getNote();
				resultResponse.result.item.add(resultItem);
			}
		}
		
	}

	private String toDateString(Date createdTime) {
		SimpleDateFormat sdf=new SimpleDateFormat(datePattern);
		return sdf.format(createdTime);
	}

	/**
	 *  int RECHARGE_NOT_PAY = 0;       // 未付款
     *  int RECHARGE_NOT_AUDIT = 1;     // 已付款,待审核
     *  int RECHARGE_NOT_CONFIRM = 2;   // 已付款,待确认
     *  int RECHARGE_FINISH = 90;       // 充值已完成
    *   int RECHARGE_FAIL = 99;         // 充值失败
	 * @param status
	 * @return
	 */
	private String makeRechargeStatus(int status) {
		String result=null;
		switch(status){
			case 0:{result="未付款";break;}
			case 1:{result="已付款，待审核";break;}
			case 2:{result="已付款，待确认";break;}
			case 90:{result="充值已完成";break;}
			case 99:{result="充值失败";break;}
			default:{result=null;}
		}
		return result;
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.UserDetail.FAIL;
	}

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

}
