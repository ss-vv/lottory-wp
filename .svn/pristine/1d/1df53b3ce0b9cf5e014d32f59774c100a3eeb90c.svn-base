package com.xhcms.lottery.commons.data.weibo;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xhcms.commons.mq.XHMessage;

/**
 * 方案消息传送对象
 * 
 * @author Wang Lei
 * 
 */
public class SchemeHandle implements XHMessage {

	private static final long serialVersionUID = 3614372232630846109L;

	private long sponsorId;	//方案发起人ID
	private long schemeId;
	private long betRecordId;//投注记录ID

	private long loginUserId;

	private String weiboContent;
	
	/** 是否是投注记录晒单 */
	private boolean isBetRecordShowScheme;

	private String showSchemeSlogan;	//晒单宣言
	
	/** 方案是否已经被晒过*/
	private boolean alreadyShow;
	private int buyAmount;
	
	public SchemeHandle() {
	}

	public SchemeHandle(long id) {
		this.setSchemeId(id);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}

	@Override
	public void setPriority(int arg0) {

	}

	public long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(long schemeId) {
		this.schemeId = schemeId;
	}

	public boolean isBetRecordShowScheme() {
		return isBetRecordShowScheme;
	}

	public void setBetRecordShowScheme(boolean isBetRecordShowScheme) {
		this.isBetRecordShowScheme = isBetRecordShowScheme;
	}

	public long getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getWeiboContent() {
		return weiboContent;
	}

	public void setWeiboContent(String weiboContent) {
		this.weiboContent = weiboContent;
	}

	public String getShowSchemeSlogan() {
		return showSchemeSlogan;
	}

	public void setShowSchemeSlogan(String showSchemeSlogan) {
		this.showSchemeSlogan = showSchemeSlogan;
	}

	public boolean isAlreadyShow() {
		return alreadyShow;
	}

	public void setAlreadyShow(boolean alreadyShow) {
		this.alreadyShow = alreadyShow;
	}

	public int getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(int buyAmount) {
		this.buyAmount = buyAmount;
	}

	public long getBetRecordId() {
		return betRecordId;
	}

	public long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public void setBetRecordId(long betRecordId) {
		this.betRecordId = betRecordId;
	}
}
