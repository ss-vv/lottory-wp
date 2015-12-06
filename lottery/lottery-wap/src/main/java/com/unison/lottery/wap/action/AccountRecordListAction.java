package com.unison.lottery.wap.action;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.BetRecord;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.AccountQueryService;
import com.xhcms.lottery.commons.persist.service.AccountService;

public class AccountRecordListAction extends BaseListAction {
	
	private static final long serialVersionUID = 2922822526796265909L;

	@Autowired
	private AccountQueryService accountQueryService;
	@Autowired
	private AccountService accountService;
	private Account account;
	private String lotteryId;
	private Integer duration;
	private List<BetRecord> records = new ArrayList<BetRecord>();
	
	private List<BetRecord> betHistory = new ArrayList<BetRecord>();

	public List<BetRecord> getRecords() {
		return records;
	}

	public void setRecords(List<BetRecord> records) {
		this.records = records;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String execute() {
		init();
		User user = getSelf();
		if (user != null) {
			account = accountService.getAccount(user.getId());
			if (lotteryId != null && duration != null && user != null) {
				String[] id = lotteryId.split(",");
				List<BetRecord> betRecords = accountQueryService.listBetRecord(user.getId(),
						id, duration, -1, paging, -1, -1);
				betHistory.addAll(betRecords);
			}
		} else {
			return LOGIN;
		}
		return SUCCESS;
	}

	public List<BetRecord> getBetHistory() {
		return betHistory;
	}

	public void setBetHistory(List<BetRecord> betHistory) {
		this.betHistory = betHistory;
	}
}