package com.unison.lottery.mc.uni.parser;

import java.util.LinkedList;
import java.util.List;

import com.xhcms.lottery.dc.data.BDMatch;

public class QueryMatchesBJDCParserStatus extends ParserStatus {
	private List<BDMatch> matches = new LinkedList<BDMatch>();
	private String type;
	private String issueNumber;
	private String playId;
	
	/**
	 * @param type 中民比赛查询类型,支持：
	 */
	public QueryMatchesBJDCParserStatus(String type){
		this.type = type;
	}
	
	public List<BDMatch> getMatches() {
		return matches;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}
}
