package com.davcai.lottery.platform.client.anruizhiying;

public class CheckResult {
	
 	private  boolean lotteryPlatformIdIsRight;
 	private boolean playIdAndIssueIsSame;
 	private boolean playIdNotBlank;
	public boolean isLotteryPlatformIdIsRight() {
		return lotteryPlatformIdIsRight;
	}
	public void setLotteryPlatformIdIsRight(boolean lotteryPlatformIdIsRight) {
		this.lotteryPlatformIdIsRight = lotteryPlatformIdIsRight;
	}
	public boolean isPlayIdAndIssueIsSame() {
		return playIdAndIssueIsSame;
	}
	public void setPlayIdAndIssueIsSame(boolean playIdAndIssueIsSame) {
		this.playIdAndIssueIsSame = playIdAndIssueIsSame;
	}
	public boolean isPlayIdNotBlank() {
		return playIdNotBlank;
	}
	public void setPlayIdNotBlank(boolean playIdNotBlank) {
		this.playIdNotBlank = playIdNotBlank;
	}

}
