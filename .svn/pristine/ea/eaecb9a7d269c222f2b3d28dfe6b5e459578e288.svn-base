package com.unison.lottery.wap.action;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.WinService;

public class WinRecordListAction extends BaseListAction{
	private static final long serialVersionUID = 7861315462015477126L;
	
    @Autowired
    private AccountService accountService;
    
	@Autowired
	private WinService winService;

	private Account account;

	private BetScheme scheme;
	
	private Integer duration=0;
	
	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public BetScheme getScheme() {
		return scheme;
	}

	public void setScheme(BetScheme scheme) {
		this.scheme = scheme;
	}


	@Override
	public String execute() {
		init();
		User user = getSelf();
		if(user!=null){
			account = accountService.getAccount(user.getId());
			if (duration != null && user!=null) {
		    	Calendar beginCalendar = Calendar.getInstance();
		    	Calendar endCalendar = Calendar.getInstance();
				Date begin = null;
		    	Date end = null;
		    	switch (duration) {
					case 0:
						beginCalendar.set(beginCalendar.get(Calendar.YEAR), beginCalendar.get(Calendar.MONTH), beginCalendar.get(Calendar.DATE), 0, 0, 0);
						endCalendar.set(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DATE), 23, 59, 59);
						break;
			
					case 1:
						beginCalendar.add(Calendar.DAY_OF_YEAR, -7);
						endCalendar.set(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DATE), 23, 59, 59);
						break;
					
					case 2:
						beginCalendar.add(Calendar.DAY_OF_YEAR, -30);
						endCalendar.set(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DATE), 23, 59, 59);
						break;
						
					default:
						break;
				}
				begin = beginCalendar.getTime();
		    	end =endCalendar.getTime();
				winService.listWinByLotteryId("JX11",paging, user.getId(), begin, end);
			}
		}else{
			return LOGIN;
		}
		return SUCCESS;
		
	}


}

