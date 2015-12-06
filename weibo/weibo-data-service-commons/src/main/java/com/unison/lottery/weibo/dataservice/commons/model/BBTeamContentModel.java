package com.unison.lottery.weibo.dataservice.commons.model;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BBTeamContentModel {
	@XmlElement
	public String id;
	@XmlElement
	public String lsID;
	@XmlElement(name="short")
	public String shortName;
	@XmlElement
	public String gb;
	@XmlElement
	public String big5;
	@XmlElement
	public String en;
	@XmlElement
	public String logo;
	@XmlElement
	public String url;
	@XmlElement
	public String LocationID;
	@XmlElement
	public String MatchAddrID;
	@XmlElement
	public String City;
	@XmlElement
	public String Gymnasium;
	@XmlElement
	public String Capacity;
	@XmlElement
	public String JoinYear;
	@XmlElement
	public String FirstTime;
	@XmlElement
	public String Drillmaster;
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	
	
	
}
