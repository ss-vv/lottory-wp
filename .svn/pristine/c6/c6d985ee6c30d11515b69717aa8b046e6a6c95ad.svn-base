package com.xhcms.lottery.dc.data;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.time.DateFormatUtils;

public class LCResult {
	private long matchId;
	private String code; // 赛事编号
	private Date offtime; // 预计截止停售时间
	private float concedeScore; //胜负让分
	private float guessScore; //大小分预设总分
	private String score; // 总分
	private double sfSp;
	private double yfsfSp;
	private double sfcSp;
	private double dxfSp;
	private int status;
	
	public long getMatchId() {
		if (matchId <= 0) {
			matchId = Long.parseLong(DateFormatUtils.format(offtime, "yyyyMMdd"))*10000 + Long.parseLong(code);
		}
		return matchId;
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

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public double getSfSp() {
		return sfSp;
	}

	public void setSfSp(double sfSp) {
		this.sfSp = sfSp;
	}

	public double getYfsfSp() {
		return yfsfSp;
	}

	public void setYfsfSp(double yfsfSp) {
		this.yfsfSp = yfsfSp;
	}

	public double getSfcSp() {
		return sfcSp;
	}

	public void setSfcSp(double sfcSp) {
		this.sfcSp = sfcSp;
	}

	public double getDxfSp() {
		return dxfSp;
	}

	public void setDxfSp(double dxfSp) {
		this.dxfSp = dxfSp;
	}

	public float getConcedeScore() {
		return concedeScore;
	}

	public void setConcedeScore(float concedeScore) {
		this.concedeScore = concedeScore;
	}

	public float getGuessScore() {
		return guessScore;
	}

	public void setGuessScore(float guessScore) {
		this.guessScore = guessScore;
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
}