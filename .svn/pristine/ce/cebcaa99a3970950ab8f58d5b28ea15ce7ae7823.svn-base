package com.xhcms.lottery.commons.persist.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lt_ssq_info")
public class SsqInfoPO {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
    @Column(name = "lottery_id", nullable = false)
	private String lotteryId; // 彩种id “SSQ”
    
    @Column(name = "issue_number", nullable = false)
	private String issueNumber; // 期号
    
    @Column(name = "total_sales", nullable = false)
    private BigDecimal totalSales; // 总销售额
    
    @Column(name = "jackpot", nullable = false)
    private BigDecimal jackpot; // 滚存奖池总额
    
    @Column(name = "used_ball_num", nullable = false)
    private int usedBallNum; // 使用的第几套球

    @Column(name = "ydj_bouns", nullable = false)
    private BigDecimal ydjBouns;  // 一等奖 奖金
    
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
