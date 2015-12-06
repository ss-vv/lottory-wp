package com.xhcms.lottery.commons.persist.service;

import org.apache.commons.lang.StringUtils;

/**
 * @author yonglizhu
 */
public class ShowSchemeQueryCondition {
	boolean isRecommend;
	String lotteryId;
	String playId;
	YesNoAll followedRatio;
	String orderColumn;
	boolean isAsc;
	String userName;
	
	/**
	 * @param isRecommend true 限推荐方案；false 所有晒单方案
	 * @param userName 用户名 空位不限
	 * @param lotteryId 彩种id 空为不限
	 * @param playId 玩法id 空为不限
	 * @param followedRatio YES 查有佣金的；NO 查无佣金的；ALL 全部的
	 * @param orderColumn 排序字段
	 */
	public ShowSchemeQueryCondition(boolean isRecommend, String userName, 
			String lotteryId, String playId, YesNoAll followedRatio, 
			String orderColumn, boolean isAsc) {
		this.isRecommend = isRecommend;
		this.lotteryId = lotteryId;
		this.playId = playId;
		this.followedRatio = followedRatio;
		this.orderColumn = orderColumn;
		this.isAsc = isAsc;
		this.userName = userName;
	}
	
	/**
	 * 用默认值创建查找条件对象。<br/>
	 * isRecommend为true，lotteryId为空，playId为空，<br/>
	 * followedRatio为ALL，orderColum为NONE，userName为空。
	 */
	public ShowSchemeQueryCondition(){
		this.isRecommend = true;
		this.lotteryId = StringUtils.EMPTY;
		this.playId = StringUtils.EMPTY;
		this.followedRatio = YesNoAll.ALL;
		this.orderColumn = StringUtils.EMPTY;
		this.isAsc = false;
		this.userName = StringUtils.EMPTY;
	}
	
	public boolean isRecommend() {
		return isRecommend;
	}
	
	public void setRecommend(boolean isRecommend) {
		this.isRecommend = isRecommend;
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
	
	public YesNoAll getCommission() {
		return followedRatio;
	}
	
	public void setCommission(YesNoAll commission) {
		this.followedRatio = commission;
	}
	
	public boolean isAsc() {
		return isAsc;
	}
	
	public void setAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}
}
