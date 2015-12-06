package com.unison.lottery.weibo.dataservice.commons.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
@XmlRootElement(name="list")
public class FBBFDataModel {
	
	//比赛赛程数组
	@XmlElement(name="match")
	public List<MatchAgendaModel> matchAgendaList;
	//赛事类型数组
	private List<MatchTypeModel> matchTypeList;
	//新增数据
	private List<CountryDetailModel> countryDetailList;
	//比赛场数
	private Double matchcount;
	//赛事类型（联赛/杯赛）个数
	private String sclasscount;
	//日期
	private String matchdate;
	
	public List<MatchTypeModel> getMatchTypeList() {
		return matchTypeList;
	}
	public void setMatchTypeList(List<MatchTypeModel> matchTypeList) {
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
	public List<CountryDetailModel> getCountryDetailList() {
		return countryDetailList;
	}
	public void setCountryDetailList(List<CountryDetailModel> countryDetailList) {
		this.countryDetailList = countryDetailList;
	}
	
	
	
}
