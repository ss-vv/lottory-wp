package com.xhcms.lottery.account.web.action.win;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.account.service.WinService;
import com.xhcms.lottery.account.web.action.BaseListAction;

/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author xulang
 * @version 1.0.0
 */
public class ListAction extends BaseListAction {
	private static final long serialVersionUID = 3250963577960908665L;
	@Autowired
	private WinService winService;
    
	@Override
	public String execute() {
		init();
        winService.listWin(paging, getUserId(), from, to);
		return SUCCESS;
	}

}
