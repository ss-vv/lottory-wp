package com.unison.lottery.weibo.dataservice.crawler.service.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class OddsBaseModel {
	private String corpId;
	private String oddsId;
	private String corpName;
	
	private String bsId;
	private Long id;
	public String getCorpId() {
		return corpId;
	}
	
	public String getBsId() {
		return bsId;
	}

	public void setBsId(String bsId) {
		this.bsId = bsId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getOddsId() {
		return oddsId;
	}
	public void setOddsId(String oddsId) {
		this.oddsId = oddsId;
	}
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE );
	}
	
	
	
}
