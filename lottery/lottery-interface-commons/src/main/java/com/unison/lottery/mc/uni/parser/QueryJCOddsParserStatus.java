package com.unison.lottery.mc.uni.parser;

import java.util.LinkedList;
import java.util.List;

import com.xhcms.lottery.dc.data.OddsBase;

/**
 * QueryMatchResultsParser的状态类。
 * @author Yang Bo
 */
public class QueryJCOddsParserStatus extends ParserStatus {
	
	private List<OddsBase> odds = new LinkedList<OddsBase>();
	
	private String type;
	
	/**
	 * @param type 中民比赛查询类型,支持：jczq,jclq,bd
	 */
	public QueryJCOddsParserStatus(String type){
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<OddsBase> getOdds() {
		return odds;
	}

}
