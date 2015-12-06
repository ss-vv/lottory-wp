package com.unison.lottery.mc.uni.parser;

import java.util.LinkedList;
import java.util.List;
import com.xhcms.lottery.commons.data.CTFBMatch;

/**
 * QueryMatchesCTZCParserStatus的状态类。
 * @author Wang Lei
 */
public class QueryMatchesCTZCParserStatus extends ParserStatus {
	private List<CTFBMatch> matches = new LinkedList<CTFBMatch>();
	private String type;
	private String issueNumber;
	private String playId;
	
//	public enum ZQPlayType {
//		SF14("14CSF"),//14场胜负
//		SFR9("SFR9"),  //胜负任9
//		BQ6C("6CBQ"),//6场半全
//		JQ4C("4CJQ"), //4场进球
//		;
//		
//		private String name;
//	    private ZQPlayType(String name) {
//			this.name = name;
//		}
//	    
//	    public static ZQPlayType valueOfName(String name){
//	    	for (ZQPlayType playType : ZQPlayType.values()) {
//	    		if (playType.name.equalsIgnoreCase(name)) {
//	    			return playType;
//	    		}
//	    	}
//	    	throw new IllegalArgumentException("Unknown name for ZQPlayType: " + name);
//	    }
//	}
	
	/**
	 * @param type 中民比赛查询类型,支持：jczq,jclq,jcgj,jcgy
	 */
	public QueryMatchesCTZCParserStatus(String type){
		this.type = type;
	}
	
	public List<CTFBMatch> getMatches() {
		return matches;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
//	public ZQPlayType getEnumType(){
//		
//		return ZQPlayType.valueOf(this.type);
//	}

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
