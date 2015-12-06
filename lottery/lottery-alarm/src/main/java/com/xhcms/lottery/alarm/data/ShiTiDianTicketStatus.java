package com.xhcms.lottery.alarm.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class ShiTiDianTicketStatus {
	
	private BigInteger total;
	private BigDecimal totalMoney;
	private Date fbMinEntrustDeadLine;//票中竞彩足球比赛最早截止期
	private Date bbMinEntrustDeadLine;//票中竞彩篮球比赛最早截止期
	private Date ctfbMinEntrustDeadLine;//票中传统足球比赛最早截止期
	public Date getFbMinEntrustDeadLine() {
		return fbMinEntrustDeadLine;
	}
	public void setFbMinEntrustDeadLine(Date fbMinEntrustDeadLine) {
		this.fbMinEntrustDeadLine = fbMinEntrustDeadLine;
	}
	public Date getBbMinEntrustDeadLine() {
		return bbMinEntrustDeadLine;
	}
	public void setBbMinEntrustDeadLine(Date bbMinEntrustDeadLine) {
		this.bbMinEntrustDeadLine = bbMinEntrustDeadLine;
	}
	public Date getCtfbMinEntrustDeadLine() {
		return ctfbMinEntrustDeadLine;
	}
	public void setCtfbMinEntrustDeadLine(Date ctfbMinEntrustDeadLine) {
		this.ctfbMinEntrustDeadLine = ctfbMinEntrustDeadLine;
	}
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}
	public BigInteger getTotal() {
		return total;
	}
	public void setTotal(BigInteger total) {
		this.total = total;
	}
	

}
