package com.unison.lottery.api.protocol.request.xml.methodparser;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;
import com.xhcms.lottery.commons.data.Account;

public class BindBankMethodRequestParser extends
		AbstractRequestParserWithUserParser {

	@Override
	protected boolean parseParamList(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		return false;
	}

	@Override
	protected boolean parseExtendRequestParams(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		boolean result = false;
		Account account = createAccount(methodRequest);
		httpRequest.setAttribute(VONames.BIND_BANK_REQUEST_VO_NAME, account);
		result = true;
		return result;
	}

	private Account createAccount(MethodRequest methodRequest) {
		Account account = new Account();
		account.setBank(methodRequest.accountBank);
		account.setCity(methodRequest.city);
		account.setPassword(methodRequest.withdrawPassword);
		account.setAccountNumber(methodRequest.bankID);
		account.setProvince(methodRequest.province);
		account.setIdCard(methodRequest.IDCard);
		account.setRealName(methodRequest.realName);
		return account;
	}

	@Override
	protected boolean decideShouldParseParamList() {
		return false;
	}

	@Override
	protected boolean decideShouldParseExtendRequestParams() {
		return true;
	}

	@Override
	protected boolean decideShouldParseUser() {
		return true;
	}
}