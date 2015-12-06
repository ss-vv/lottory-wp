package com.xhcms.lottery.account.web.action.bet;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.xhcms.lottery.account.web.action.BaseListAction;
import com.xhcms.lottery.commons.persist.service.AccountQueryService;

/**
 * 
 * <p>Title: 查询投注记录 </p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author wanged
 * @version 1.0.0
 */
public class ListAction extends BaseListAction {
    private static final long serialVersionUID = -1895085157670872281L;

    @Autowired
	private AccountQueryService accountQueryService;

	private String lotteryId;
	
	private int  type=-1;
	
	private int showScheme=-1;
	
	@Autowired
    private UserAccountService userAccountService;
	private WeiboUser weiboUser;
	
	@Override
	public String execute() {
	    init();
	    weiboUser = userAccountService.findWeiboUserByLotteryUid(getUserId() + "");
	    if(StringUtils.isBlank(lotteryId)){
	        lotteryId = null;
	    }
		accountQueryService.listBetHistory(lotteryId, getUserId(), from, to, -1, paging,type,showScheme);
		return SUCCESS;
	}

    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getShowScheme() {
		return showScheme;
	}

	public void setShowScheme(int showScheme) {
		this.showScheme = showScheme;
	}

	public WeiboUser getWeiboUser() {
		return weiboUser;
	}

	public void setWeiboUser(WeiboUser weiboUser) {
		this.weiboUser = weiboUser;
	}
}
