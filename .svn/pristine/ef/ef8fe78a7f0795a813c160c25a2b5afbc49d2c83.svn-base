package com.xhcms.lottery.dc.data;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.time.DateFormatUtils;


/**
 * @author xulang
 */
public class ZCResult {
	private long matchId;
	private String code; // 赛事编号
	private Date offtime; // 预计截止停售时间
	private String halfScore; // 半场比分
	private String score; // 全场比分
	private double sfpSp;
	private double bfSp;
	private double zjqSp;
	private double bqcSp;
	private double brqspfSp;	// 不让球胜平负SP
	private int concedePoints; 	//让球
	private int status;
	
	public long getMatchId() {
		if (matchId <= 0) {
			matchId = Long.parseLong(DateFormatUtils.format(offtime, "yyyyMMdd"))*10000 + Long.parseLong(code);
		}
		return matchId;
	}
	
	public String getHalfScore() {
		return halfScore;
	}

	public void setHalfScore(String halfScore) {
		this.halfScore = halfScore;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getOfftime() {
		return offtime;
	}

	public void setOfftime(Date offtime) {
		this.offtime = offtime;
	}

	public double getSfpSp() {
		return sfpSp;
	}

	public void setSfpSp(double sfpSp) {
		this.sfpSp = sfpSp;
	}

	public double getBfSp() {
		return bfSp;
	}

	public void setBfSp(double bfSp) {
		this.bfSp = bfSp;
	}

	public double getZjqSp() {
		return zjqSp;
	}

	public void setZjqSp(double zjqSp) {
		this.zjqSp = zjqSp;
	}

	public double getBqcSp() {
		return bqcSp;
	}

	public void setBqcSp(double bqcSp) {
		this.bqcSp = bqcSp;
	}

	public int getConcedePoints() {
		return concedePoints;
	}

	public void setConcedePoints(int concedePoints) {
		this.concedePoints = concedePoints;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public double getBrqspfSp() {
		return brqspfSp;
	}

	public void setBrqspfSp(double brqspfSp) {
		this.brqspfSp = brqspfSp;
	}
}
