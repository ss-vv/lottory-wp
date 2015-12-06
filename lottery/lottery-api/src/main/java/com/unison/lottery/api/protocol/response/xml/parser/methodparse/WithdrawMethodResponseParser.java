package com.unison.lottery.api.protocol.response.xml.parser.methodparse;



import javax.servlet.http.HttpServletRequest;










import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;

import com.unison.lottery.api.protocol.response.model.BindIDCardResponse;
import com.unison.lottery.api.protocol.response.model.LoginResponse;
import com.unison.lottery.api.protocol.response.model.RegisteResponse;
import com.unison.lottery.api.protocol.response.model.Response;
import com.unison.lottery.api.protocol.response.model.WithdrawResponse;
import com.xhcms.lottery.commons.data.Account;


public class WithdrawMethodResponseParser extends
		AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.WITHDRAW_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		
		WithdrawResponse WithdrawResponse=(WithdrawResponse) responseFromHttpRequest;
		Account account =WithdrawResponse.getAccount();
		if(null!=account){
			
			resultResponse.fund=account.getFund();
			resultResponse.free=account.getFree();
			resultResponse.grant=account.getGrant();
			resultResponse.frozenFund=account.getFrozenFund();
			resultResponse.frozenGrant=account.getFrozenGrant();
			resultResponse.totalRecharge=account.getTotalRecharge();
			resultResponse.totalWithdraw=account.getTotalWithdraw();
			resultResponse.totalBet=account.getTotalBet();
			resultResponse.totalAward=account.getTotalAward();
			resultResponse.totalCommission=account.getTotalCommission();
			
		}

	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.Withdraw.FAIL;
	}

}
