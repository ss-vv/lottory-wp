package com.unison.lottery.weibo.dataservice.commons.model;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TeamContentModel {
	@XmlElement
	public String id;
	@XmlElement
	public String lsID;
	@XmlElement
	public String g;
	@XmlElement
	public String b;
	@XmlElement
	public String e;
	//以下为新接口新增属性
	@XmlElement
	public String Found;  //球队成立日期
	@XmlElement
	public String Area; //所在地
	@XmlElement
	public String gym; //球场
	@XmlElement
	public String Capacity; //球场容量
	@XmlElement
	public String Flag; //队标
	@XmlElement
	public String addr; //地址
	@XmlElement
	public String URL; //球队网址
	@XmlElement
	public String master; //教练
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	
	
	
}
