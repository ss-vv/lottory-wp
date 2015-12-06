package com.davcai.lottery.platform.client.anruizhiying.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AnRuiZhiYingJCPlayOddsModel {
	private String lotteryId;
	private String ggRq;
	private String dgRq;
	private String ggPv;
	private String dgPv;
	private String ggSaleStatus;
	private String dgSaleStatus;
	private String dgGdSaleStatus;
	public String getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getGgRq() {
		return ggRq;
	}
	public void setGgRq(String ggRq) {
		this.ggRq = ggRq;
	}
	public String getDgRq() {
		return dgRq;
	}
	public void setDgRq(String dgRq) {
		this.dgRq = dgRq;
	}
	public String getGgPv() {
		return ggPv;
	}
	public void setGgPv(String ggPv) {
		this.ggPv = ggPv;
	}
	public String getDgPv() {
		return dgPv;
	}
	public void setDgPv(String dgPv) {
		this.dgPv = dgPv;
	}
	public String getGgSaleStatus() {
		return ggSaleStatus;
	}
	public void setGgSaleStatus(String ggSaleStatus) {
		this.ggSaleStatus = ggSaleStatus;
	}
	public String getDgSaleStatus() {
		return dgSaleStatus;
	}
	public void setDgSaleStatus(String dgSaleStatus) {
		this.dgSaleStatus = dgSaleStatus;
	}
	public String getDgGdSaleStatus() {
		return dgGdSaleStatus;
	}
	public void setDgGdSaleStatus(String dgGdSaleStatus) {
		this.dgGdSaleStatus = dgGdSaleStatus;
	}
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
