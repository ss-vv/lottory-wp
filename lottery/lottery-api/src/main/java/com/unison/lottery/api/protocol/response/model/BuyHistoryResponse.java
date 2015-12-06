package com.unison.lottery.api.protocol.response.model;

import java.util.List;

import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.BetRecord;

public class BuyHistoryResponse extends HaveReturnStatusResponse{
	
	List<BetRecord> results;
	
	private Account account;

	public List<BetRecord> getResults() {
		return results;
	}

	public void setResults(List<BetRecord> results) {
		this.results = results;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
