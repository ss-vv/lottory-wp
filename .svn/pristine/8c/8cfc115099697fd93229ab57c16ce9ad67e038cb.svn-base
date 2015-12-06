package com.unison.lottery.mc.uni.parser;

import java.util.LinkedList;
import java.util.List;

import com.xhcms.lottery.dc.data.Match;

/**
 * QueryMatchesParser的状态类。
 * @author Yang Bo
 */
public class QueryMatchesParserStatus extends ParserStatus {
	private List<Match> matches = new LinkedList<Match>();
	private String type;
	
	public enum Type {
		jczq,
		jclq,
		jcgj,
		jcgy,
		jcsjbgj,
		bjdc
	}
	
	/**
	 * @param type 中民比赛查询类型,支持：jczq,jclq,jcgj,jcgy,jcsjbgj
	 */
	public QueryMatchesParserStatus(String type){
		this.type = type;
	}
	
	public List<Match> getMatches() {
		return matches;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Type getEnumType(){
		return Type.valueOf(this.type);
	}
}
