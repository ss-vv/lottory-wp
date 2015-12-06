package com.xhcms.lottery.commons.data;

import java.math.BigDecimal;

public class SsqInfo {
	private Long id;
	private String lotteryId;
	private String issueNumber;
    private BigDecimal totalSales;
    private BigDecimal jackpot;
    private int usedBallNum;
    private BigDecimal ydjBouns; 
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public BigDecimal getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}

	public BigDecimal getJackpot() {
		return jackpot;
	}

	public void setJackpot(BigDecimal jackpot) {
		this.jackpot = jackpot;
	}

	public int getUsedBallNum() {
		return usedBallNum;
	}

	public void setUsedBallNum(int usedBallNum) {
		this.usedBallNum = usedBallNum;
	}

	public BigDecimal getYdjBouns() {
		return ydjBouns;
	}

	public void setYdjBouns(BigDecimal ydjBouns) {
		this.ydjBouns = ydjBouns;
	}
}
