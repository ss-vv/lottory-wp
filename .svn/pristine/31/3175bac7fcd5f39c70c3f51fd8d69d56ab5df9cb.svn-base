package com.xhcms.lottery.commons.data.weibo;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xhcms.commons.mq.XHMessage;

/**
 * 投注消息
 * @author lei.li@davcai.com
 */
public class BetMessage implements XHMessage {

	private static final long serialVersionUID = 3614372232630846109L;
	
	private long userId;

	private long schemeId;
	private long betRecordId;

	private String lotteryId;
	
	private int displayMode;

	private long schemeCreateTime;
	
	public BetMessage() {
	}

	public BetMessage(long id) {
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public int getDisplayMode() {
		return displayMode;
	}

	public void setDisplayMode(int displayMode) {
		this.displayMode = displayMode;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public long getSchemeCreateTime() {
		return schemeCreateTime;
	}

	public void setSchemeCreateTime(long schemeCreateTime) {
		this.schemeCreateTime = schemeCreateTime;
	}

	public long getBetRecordId() {
		return betRecordId;
	}

	public void setBetRecordId(long betRecordId) {
		this.betRecordId = betRecordId;
	}
}