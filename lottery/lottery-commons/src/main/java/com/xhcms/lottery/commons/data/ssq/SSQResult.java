package com.xhcms.lottery.commons.data.ssq;

import java.math.BigDecimal;

/**
 * 双色球的开奖信息。
 * 
 * @author Yang Bo
 */
public class SSQResult {

	private String shortIssueNum; // 期号简写，后3位

	private String issueNum; // 完整期号

	private String redBalls; // 空格间隔，如："01 02 03 04 05 06"

	private String blueBall; // 蓝球

	private String bigSmall; // 大小比值，如："5:1" 5个大数，一个小数，01-16 是小数，17-33 是大数

	private String sum; // 和值，所有红球之和

	private BigDecimal jackpot; // 奖池滚存

	public String getRedBalls() {
		return redBalls;
	}

	public void setRedBalls(String redBalls) {
		this.redBalls = redBalls;
	}

	public String getBlueBall() {
		return blueBall;
	}

	public void setBlueBall(String blueBall) {
		this.blueBall = blueBall;
	}

	public String getBigSmall() {
		return bigSmall;
	}

	public void setBigSmall(String bigSmall) {
		this.bigSmall = bigSmall;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getShortIssueNum() {
		return shortIssueNum;
	}

	public void setShortIssueNum(String shortIssueNum) {
		this.shortIssueNum = shortIssueNum;
	}

	public String getIssueNum() {
		return issueNum;
	}

	public void setIssueNum(String issueNum) {
		this.issueNum = issueNum;
	}

	public BigDecimal getJackpot() {
		return jackpot;
	}

	public void setJackpot(BigDecimal jackpot) {
		this.jackpot = jackpot;
	}
}
