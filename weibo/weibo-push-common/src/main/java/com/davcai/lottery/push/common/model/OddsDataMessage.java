package com.davcai.lottery.push.common.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class OddsDataMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2128449114060176796L;
	
	@JsonIgnore
	private String initWinOdds;
	@JsonIgnore
	private String initDrawOdds; 
	@JsonIgnore
	private String initLoseOdds;
	@JsonIgnore
	private String nowWinOdds;
	@JsonIgnore
	private String nowDrawOdds;
	@JsonIgnore
	private String nowLoseOdds;
	
	private String data;
	
	private String time; //距离1970的毫秒数
	public String getInitWinOdds() {
		return initWinOdds;
	}
	public void setInitWinOdds(String initWinOdds) {
		this.initWinOdds = initWinOdds;
	}
	public String getInitDrawOdds() {
		return initDrawOdds;
	}
	public void setInitDrawOdds(String initDrawOdds) {
		this.initDrawOdds = initDrawOdds;
	}
	public String getInitLoseOdds() {
		return initLoseOdds;
	}
	public void setInitLoseOdds(String initLoseOdds) {
		this.initLoseOdds = initLoseOdds;
	}
	public String getNowWinOdds() {
		return nowWinOdds;
	}
	public void setNowWinOdds(String nowWinOdds) {
		this.nowWinOdds = nowWinOdds;
	}
	public String getNowDrawOdds() {
		return nowDrawOdds;
	}
	public void setNowDrawOdds(String nowDrawOdds) {
		this.nowDrawOdds = nowDrawOdds;
	}
	public String getNowLoseOdds() {
		return nowLoseOdds;
	}
	public void setNowLoseOdds(String nowLoseOdds) {
		this.nowLoseOdds = nowLoseOdds;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString(){
		return nowWinOdds+","+nowDrawOdds+","+nowLoseOdds;
	}
	public String getData() {
		String data = this.nowWinOdds+","+this.nowDrawOdds+","+this.nowLoseOdds;
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}
