package com.unison.lottery.weibo.dataservice.commons.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 篮球赛程、赛果
 * 
 * @author Yang Bo
 */
@XmlRootElement(name = "c")
public class BBMatchInfoDocument {

	@XmlElement(name="m")
	public M m;
	
	@XmlElement(name = "h")
	public List<String> contentStringList;
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public static class M {
		@XmlElement(name = "h")
		public List<String> contentStringList;
	}
}
