package com.unison.lottery.weibo.dataservice.commons.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="c")
public class BBOddsRealtimeModel {

	@XmlElement(name="a")
	public Concede concede;

	@XmlElement(name="o")
	public Euro euro;
	
	@XmlElement(name="d")
	public BigSmall bigSmall;
	
	public static class Concede{
		@XmlElement(name="h")
		public List<String> odds;
	}
	
	public static class Euro{
		@XmlElement(name="h")
		public List<String> odds;
	}
	
	public static class BigSmall{
		@XmlElement(name="h")
		public List<String> odds;
	}
}
