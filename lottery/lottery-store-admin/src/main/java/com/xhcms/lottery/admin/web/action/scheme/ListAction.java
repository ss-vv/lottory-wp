package com.xhcms.lottery.admin.web.action.scheme;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.lang.EntityStatus;

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
	private static final long serialVersionUID = 5235943196152554334L;
	
	@Autowired
	private BetSchemeService betSchemeService;
	private String sponsor;
	private Long schemeId;
	private String lotteryId;
	
	/**
	 * 未派奖
	 */
	@Override
	public String execute() {
	    return query(EntityStatus.TICKET_NOT_AWARD);
	}
	
	/**
	 * 未兑奖
	 * @return
	 */
	public String bought() {
	    return query(EntityStatus.TICKET_BUY_SUCCESS);
	}
	
	/**
	 * 已派奖
	 * @return
	 */
	public String awarded() {
	    return query(EntityStatus.TICKET_AWARDED);
	}
	
	/**
	 * 未中奖
	 * @return
	 */
	public String notWin() {
	    return query(EntityStatus.TICKET_NOT_WIN);
	}
	
	private String query(int status){
	    init();
	    if(StringUtils.isBlank(lotteryId)){
	        lotteryId = null;
	    }
        betSchemeService.listBetScheme(paging, from, to, status, lotteryId, schemeId, sponsor, null, null);
        return SUCCESS;
	}

    public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}
	
}
