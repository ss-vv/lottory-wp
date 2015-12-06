package com.unison.lottery.mc.uni.parser;

import java.util.LinkedList;
import java.util.List;

/**
 * QueryMatchResultsParser的状态类。
 * @author Yang Bo
 */
@SuppressWarnings("rawtypes")
public class QueryMatchResultsParserStatus extends ParserStatus {
	private List matchResults = new LinkedList();
	private String type;
	
	/**
	 * @param type 中民比赛查询类型,支持：jczq,jclq,bd
	 */
	public QueryMatchResultsParserStatus(String type){
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List getMatchResults() {
		return matchResults;
	}

}
