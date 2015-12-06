package com.xhcms.lottery.dc.feed.web.action.matchdata;

import java.util.HashMap;
import java.util.Map;

import com.xhcms.lottery.dc.feed.data.MatchOddsData;
import com.xhcms.lottery.dc.feed.web.action.BaseAction;

/**
 * 赛事指数数据相关 
 */
public class ExponentAction extends BaseAction{
	private static final long serialVersionUID = 4234310687256441374L;
	
	private int oddsType;
	private int cropId;
	private long[] matchId;
	private Map<String,MatchOddsData> data = new HashMap<String, MatchOddsData>(); 
	public String execute(){
		
		return SUCCESS;
	}

	public void setCropId(int cropId) {
		this.cropId = cropId;
	}

	public void setMatchId(long[] matchId) {
		this.matchId = matchId;
	}

	public Map<String, MatchOddsData> getData() {
		return data;
	}

	public void setOddsType(int oddsType) {
		this.oddsType = oddsType;
	}
}
