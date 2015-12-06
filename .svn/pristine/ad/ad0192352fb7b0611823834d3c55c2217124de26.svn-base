package com.xhcms.lottery.commons.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @desc 赛事筛选器
 * @createTime 2014-6-9
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class MatchSelector {

	private String playId;
	
	/**联赛列表*/
	private List<String> leagueList;
	
	/**开赛时间*/
	private Date playingTime;
	
	/**是否显示停售赛事*/
	private boolean isShowStopSell;
	
	private String issueNum;
	
	public MatchSelector() {
		leagueList = new ArrayList<String>();
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, 
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public List<String> getLeagueList() {
		return leagueList;
	}

	public void addLeague(String league) {
		leagueList.add(league);
	}
	
	public void addLeagueList(List<String> leagueList) {
		leagueList.addAll(leagueList);
	}

	public Date getPlayingTime() {
		return playingTime;
	}
	
	public void setPlayingTime(Date playingTime) {
		this.playingTime = playingTime;
	}
	
	public boolean isShowStopSell() {
		return isShowStopSell;
	}

	public void setShowStopSell(boolean isShowStopSell) {
		this.isShowStopSell = isShowStopSell;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public String getIssueNum() {
		return issueNum;
	}

	public void setIssueNum(String issueNum) {
		this.issueNum = issueNum;
	}
}