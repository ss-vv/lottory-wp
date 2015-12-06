package com.davcai.lottery.platform.client.anruizhiying.model;

public class AnRuiZhiYingParams {

	private String lotteryId ;
	private String wareId ;
	private String wareIssue ;
	
	public AnRuiZhiYingParams(String lotteryId,String wareId,String wareIssue) {
		this.lotteryId=lotteryId;
		this.wareId=wareId;
		this.wareIssue=wareIssue;
	}
	public String getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getWareId() {
		return wareId;
	}
	public void setWareId(String wareId) {
		this.wareId = wareId;
	}
	public String getWareIssue() {
		return wareIssue;
	}
	public void setWareIssue(String wareIssue) {
		this.wareIssue = wareIssue;
	}
}
