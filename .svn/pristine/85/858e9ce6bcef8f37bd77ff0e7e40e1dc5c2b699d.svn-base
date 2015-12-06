package com.unison.lottery.api.protocol.response.xml.parser.methodparse;



import javax.servlet.http.HttpServletRequest;










import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.LoginResponse;
import com.unison.lottery.api.protocol.response.model.Response;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.HX_user;

public class LoginMethodResponseParser extends
		AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.LOGIN_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		LoginResponse loginResponse=(LoginResponse) responseFromHttpRequest;
		if(null!=loginResponse&&null!=loginResponse.getReturnStatus()){
			
			User user=loginResponse.getUser();
			Account account =loginResponse.getAccount();
			HX_user hx_user = loginResponse.getHx_user(); 
			if(null!=user&&null!=account&&null != hx_user){
				resultResponse.result=new Result();
				resultResponse.result.validId=user.getValidId();
				resultResponse.result.fund=account.getFund();
				resultResponse.result.free=account.getFree();
				resultResponse.result.grant=account.getGrant();
				resultResponse.result.frozenFund=account.getFrozenFund();
				resultResponse.result.frozenGrant=account.getFrozenGrant();
				resultResponse.result.totalRecharge=account.getTotalRecharge();
				resultResponse.result.totalWithdraw=account.getTotalWithdraw();
				resultResponse.result.totalBet=account.getTotalBet();
				resultResponse.result.totalAward=account.getTotalAward();
				resultResponse.result.totalCommission=account.getTotalCommission();
				resultResponse.result.accountBank=account.getBank();
				resultResponse.result.provinceOfBank=account.getProvince();
				resultResponse.result.cityOfBank=account.getCity();
				resultResponse.result.bankID=account.getAccountNumber();
				resultResponse.result.accountUser=account.getAccountName();
				resultResponse.result.isBindMobile=user.isVerifyPhoneNumber();
				resultResponse.result.realName=user.getRealName();
				resultResponse.result.IDCard=user.getIDCard();
				resultResponse.result.phoneNumber=user.getPhoneNumber();
				resultResponse.result.hx_username=hx_user.getUsername();
				resultResponse.result.hx_password=hx_user.getPassword();
				resultResponse.result.nickname=loginResponse.getNickname();
				resultResponse.result.imageUrl=loginResponse.getImageUrl();
			}
			
			
				
			
		}
		

	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.Login.FAIL;
	}

}
