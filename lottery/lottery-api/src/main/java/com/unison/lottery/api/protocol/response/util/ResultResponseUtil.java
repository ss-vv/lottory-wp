package com.unison.lottery.api.protocol.response.util;

import com.unison.lottery.api.protocol.response.model.Response;
import com.xhcms.lottery.commons.data.Account;

public class ResultResponseUtil {

	public static void bindAccountInfoToResultResponse(Response resultResponse,
			Account account) {
		if(null != account && account.getId() != null && account.getId() > 0&&null!=resultResponse) {
			resultResponse.free = account.getFree();
			resultResponse.fund = account.getFund();
			resultResponse.grant = account.getGrant();
			resultResponse.frozenFund = account.getFrozenFund();
			resultResponse.frozenGrant = account.getFrozenGrant();
			resultResponse.totalRecharge = account.getTotalRecharge();
			resultResponse.totalWithdraw = account.getTotalWithdraw();
			resultResponse.totalBet = account.getTotalBet();
			resultResponse.totalAward = account.getTotalAward();
			resultResponse.totalCommission = account.getTotalCommission();
			resultResponse.accountBank = account.getBank();
			resultResponse.bankID = account.getAccountNumber();
		}
		
	}

}
