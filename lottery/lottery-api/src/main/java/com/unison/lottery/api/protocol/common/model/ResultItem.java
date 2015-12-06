package com.unison.lottery.api.protocol.common.model;



import javax.xml.bind.annotation.XmlAttribute;






public class ResultItem {

	@XmlAttribute
	public String name;
	
	@XmlAttribute
	public String type;
	
	@XmlAttribute
	public String operationDesc;
	
	@XmlAttribute
	public String playListName;

	@XmlAttribute
	public String playListId;

	@XmlAttribute
	public Integer playListContentVersion;

	@XmlAttribute
	public Integer trackCountOfPlayList;
	


	@XmlAttribute
	public String clientTransitationId;

	@XmlAttribute
	public String corpId;

	@XmlAttribute
	public String europeChange;

	@XmlAttribute
	public String europeOddsData;

	@XmlAttribute
	public String asianOddsData;

	@XmlAttribute
	public String asianChange;

	@XmlAttribute
	public String bigsmallChange;

	@XmlAttribute
	public String bigsmallOddsData;
	
	

}
