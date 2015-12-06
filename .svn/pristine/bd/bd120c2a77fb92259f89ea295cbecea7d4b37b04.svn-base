package com.xhcms.lottery.account.web.action.commission;

import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.lottery.account.service.BetSchemeService;
import com.xhcms.lottery.account.web.action.BaseListAction;

/**
 * 
 * <p>Title: 查询佣金收入记录 </p>
 * @author wang lei
 */
public class ListInAction extends BaseListAction {
    private static final long serialVersionUID = -1895085157670872281L;

    @Autowired
	private BetSchemeService betSchemeService;

	@Override
	public String execute() {
	    init();
	    betSchemeService.listCommissionInByUserId(getUser().getId(), from, to, paging);
		return SUCCESS;
	}
}
