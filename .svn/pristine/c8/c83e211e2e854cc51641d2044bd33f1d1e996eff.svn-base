package com.unison.lottery.api.protocol.response.model;

import java.util.List;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.unison.lottery.api.odds.model.JCOdds;
import com.unison.lottery.api.protocol.common.model.Item;
import com.xhcms.lottery.dc.data.OddsBase;

/**
 *
 * @author baoxing.peng
 * @since 2015年3月24日下午3:02:35
 */
@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class QueryImmediateIndexInfoResponse extends HaveReturnStatusResponse {
	private String name;
	private Integer matchType;
	private Set<String> leagueShortName;
	private List<JCOdds> resultList;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMatchType() {
		return matchType;
	}
	public void setMatchType(Integer matchType) {
		this.matchType = matchType;
	}
	public Set<String> getLeagueShortName() {
		return leagueShortName;
	}
	public void setLeagueShortName(Set<String> leagueNameList) {
		this.leagueShortName = leagueNameList;
	}
	public List<JCOdds> getResultList() {
		return resultList;
	}
	public void setResultList(List<JCOdds> matchList) {
		this.resultList = matchList;
	}
	
	
}
