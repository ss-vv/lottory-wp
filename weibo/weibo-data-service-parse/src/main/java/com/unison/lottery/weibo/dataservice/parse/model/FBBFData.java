package com.unison.lottery.weibo.dataservice.parse.model;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FBBFData {
	
	//比赛赛程数组
	private List<MatchAgenda> matchAgendaList;
	//赛事类型数组
	private List<MatchType> matchTypeList;
	//新增数据
	private List<CountryDetail> countryDetailList;
	//比赛场数
	private Double matchcount;
	//赛事类型（联赛/杯赛）个数
	private String sclasscount;
	//日期
	private String matchdate;
	public List<MatchAgenda> getMatchAgendaList() {
		return matchAgendaList;
	}
	public void setMatchAgendaList(List<MatchAgenda> matchAgendaList) {
		this.matchAgendaList = matchAgendaList;
	}
	
	public List<MatchType> getMatchTypeList() {
		return matchTypeList;
	}
	public void setMatchTypeList(List<MatchType> matchTypeList) {
		this.matchTypeList = matchTypeList;
	}
	public Double getMatchcount() {
		return matchcount;
	}
	public void setMatchcount(Double matchcount) {
		this.matchcount = matchcount;
	}
	public String getSclasscount() {
		return sclasscount;
	}
	public void setSclasscount(String sclasscount) {
		this.sclasscount = sclasscount;
	}
	public String getMatchdate() {
		return matchdate;
	}
	public void setMatchdate(String matchdate) {
		this.matchdate = matchdate;
	}
	public String toString(){
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	public List<CountryDetail> getCountryDetailList() {
		return countryDetailList;
	}
	public void setCountryDetailList(List<CountryDetail> countryDetailList) {
		this.countryDetailList = countryDetailList;
	}
	
	
	
}
