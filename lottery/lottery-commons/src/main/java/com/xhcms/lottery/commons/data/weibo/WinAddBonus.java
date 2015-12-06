package com.xhcms.lottery.commons.data.weibo;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xhcms.commons.mq.XHMessage;

public class WinAddBonus implements XHMessage {

	private static final long serialVersionUID = 1L;

	private long userId;
	private long schemeId;
	private BigDecimal bonus;
	
	public WinAddBonus() {
	}
	
	public WinAddBonus(long userId, long schemeId, BigDecimal bonus) {
		this.userId = userId;
		this.schemeId = schemeId;
		this.bonus = bonus;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	@Override
	public int getPriority() {
		
		return 0;
	}

	@Override
	public String getType() {
		return WinAddBonus.class.getSimpleName();
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public BigDecimal getBonus() {
		return bonus;
	}

	public void setBonus(BigDecimal bonus) {
		this.bonus = bonus;
	}
}
