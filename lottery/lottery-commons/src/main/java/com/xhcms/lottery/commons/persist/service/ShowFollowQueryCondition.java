package com.xhcms.lottery.commons.persist.service;

/**
 * @author yonglizhu
 */
public class ShowFollowQueryCondition {
	
	private long userId;
	private String curDate;
	private int status;
	private String orderColumn;
	private boolean isAsc;	
	
	public ShowFollowQueryCondition(){
		
	}
	
	public ShowFollowQueryCondition(long userId,String curDate,int status,String orderColumn,boolean isAsc){
		this.userId = userId;
		this.curDate = curDate;
		this.status = status;
		this.orderColumn = orderColumn;
		this.isAsc = isAsc;
		
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public boolean getIsAsc() {
		return isAsc;
	}

	public void setIsAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}

	public String getCurDate() {
		return curDate;
	}

	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}
}
