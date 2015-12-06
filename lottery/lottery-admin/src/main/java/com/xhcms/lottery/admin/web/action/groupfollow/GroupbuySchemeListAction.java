package com.xhcms.lottery.admin.web.action.groupfollow;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.lang.Constants;

public class GroupbuySchemeListAction extends BaseListAction {

	private static final long serialVersionUID = 4833958017189345340L;

	@Autowired
	private BetSchemeService betSchemeService;
	
	private String tab;
	private boolean isOnSale = true;

    private String lotteryId;
    
    private String playId;
    
    private String passType;
    
    private String sponsor;
    
    private Long schemeId;
    
    private int state = -1;
    
	@Override
	public String execute() throws Exception {
		paging = wrapPaging();
		
		if ( StringUtils.isBlank(tab) || tab.equals("REC_SCHEME") ){
			return listRecommendedScheme();
		}
		
		if (tab.equals(Constants.JCLQ) || tab.equals(Constants.JCZQ)
				|| tab.equals(Constants.CTZC)) {
			lotteryId = tab;
			return listOnSaleGroupbuyBetScheme(tab);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 列出在售的晒单方案。
	 * @param lotteryId 彩票ID
	 */
	private String listOnSaleGroupbuyBetScheme(String lotteryId){
		String resultName = "groupbuy";
		
		if ( StringUtils.isBlank(lotteryId) ){
			addActionError("lotteryId不能为空。");
			return resultName;
		}
		
		betSchemeService.listOnSaleGroupbuyBetScheme(paging, state, lotteryId, 
				schemeId, sponsor, playId, passType);
		
		return resultName;
	}
	
	private String listRecommendedScheme(){
		betSchemeService.listRecommendedBetScheme(paging, isOnSale, Constants.TYPE_GROUP);
		return SUCCESS;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public String getPassType() {
		return passType;
	}

	public void setPassType(String passType) {
		this.passType = passType;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public boolean isOnSale() {
		return isOnSale;
	}

	public void setOnSale(boolean isOnSale) {
		this.isOnSale = isOnSale;
	}
}
