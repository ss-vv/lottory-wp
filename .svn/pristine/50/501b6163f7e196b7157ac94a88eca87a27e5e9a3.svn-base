package com.unison.lottery.weibo.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @desc 中奖喜报对象
 * @author lei.li@unison.net.cn
 * @createTime 2014-1-7
 * @version 1.0
 */
public class WinningNew implements Serializable {

	private static final long serialVersionUID = 4442743671945283738L;

	private long id;

	/** 微博的ID */
	private long weiboId;

	/** 中奖微博用户ID */
	private long userId;

	/** 彩种ID */
	private String lotteryId;

	/** 中奖金额 */
	private BigDecimal bonus;

	/** 中奖方案ID */
	private long schemeId;

	/** 喜报创建时间 */
	private Date createTime;

	/**
	 * 晒单微博ID
	 */
	private long showWeiboId;
	/**
	 * 玩法中文名字
	 */
	private String playName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(long weiboId) {
		this.weiboId = weiboId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public BigDecimal getBonus() {
		return bonus;
	}

	public void setBonus(BigDecimal bonus) {
		this.bonus = bonus;
	}

	public long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(long schemeId) {
		this.schemeId = schemeId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getShowWeiboId() {
		return showWeiboId;
	}

	public void setShowWeiboId(long showWeiboId) {
		this.showWeiboId = showWeiboId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, 
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getPlayName() {
		return playName;
	}

	public void setPlayName(String playName) {
		this.playName = playName;
	}
	
}