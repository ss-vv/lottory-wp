package com.xhcms.lottery.dc.data;

import java.math.BigDecimal;

/**
 * 北京单场赛事信息
 */
public class BDMatch extends Match {

	public BDMatch() {
	}

	@Override
	public long getMatchId() {
		if (matchId <= 0) {
			matchId = Long.parseLong(issueNumber) * 1000 + Long.parseLong(code);
		}
		return matchId;
	}

	public String issueNumber;

	public int status;
	
	private BigDecimal concedePoints;
	
	public BigDecimal getConcedeBalls() {
		return concedePoints;
	}

	public void setConcedePoints(BigDecimal concedePoints) {
		this.concedePoints = concedePoints;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}