package com.unison.lottery.weibo.dataservice.commons.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 篮球即时赛程、赛果
 * 
 * @author Yang Bo
 */
@XmlRootElement(name = "c")
public class BBMatchInfoRealtimeDocument {

	@XmlElement(name = "h")
	public List<String> contents;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
