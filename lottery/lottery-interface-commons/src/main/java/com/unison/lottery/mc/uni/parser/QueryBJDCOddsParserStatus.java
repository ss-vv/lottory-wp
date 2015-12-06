package com.unison.lottery.mc.uni.parser;

import java.util.LinkedList;
import java.util.List;
import com.xhcms.lottery.dc.data.BDOdds;

public class QueryBJDCOddsParserStatus extends ParserStatus {
	
	private List<BDOdds> odds = new LinkedList<BDOdds>();
	
	private String type;
	
	/**
	 * @param lotteryId -  SPF,BF,JQS,BQC,SXDS 同接口文档定义。
	 */
	private String lotteryId;
	
	public QueryBJDCOddsParserStatus(String type){
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<BDOdds> getOdds() {
		return odds;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
}