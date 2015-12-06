package com.unison.lottery.api.protocol.common.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;



public class ResultList {
	
	@XmlAttribute
	public String name;
	
	@XmlAttribute
	public Integer page;
	
	@XmlAttribute
	public Integer pages;
	
	@XmlAttribute
	public Integer perPage;
	
	@XmlAttribute
	public Integer total;
	
	@XmlAttribute
	public Integer count;
	
	@XmlAttribute
	public String backgroundTrackId;
	
	@XmlAttribute
	public String contentImgUrl;
	
	@XmlAttribute
	public String trackUrl;
	
	@XmlAttribute
	public String trackDuration;
	
	@XmlElement
	public List<Item> item;
	
	@XmlElement
	public List<ResultItem> resultItem;

	@XmlAttribute
	public Integer sumOfPages;

	@XmlAttribute
	public String backgroundTrackName;

	@XmlAttribute
	public String backgroundArtistName;

	@XmlAttribute
	public String title;

	@XmlAttribute
	public Integer currentPhase;

	@XmlAttribute
	public Integer playListVersion;

	@XmlAttribute
	public Integer currentPageNo;
	
	@XmlElement
	public List<Result> result;

	@XmlElement
	public String banner;

	@XmlAttribute
	public String matchTime;

	@XmlAttribute
	public long matchId;

	@XmlAttribute
	public String homeTeamName;

	@XmlAttribute
	public String guestTeamName;

	@XmlAttribute
	public String leagueShortName;

	@XmlAttribute
	public String code;
	

}
