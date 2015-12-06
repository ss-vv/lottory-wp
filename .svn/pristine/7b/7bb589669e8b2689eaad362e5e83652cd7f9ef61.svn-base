package com.unison.lottery.weibo.data.service.store.data;

import java.util.ArrayList;
import java.util.List;
import com.unison.lottery.weibo.data.service.store.persist.entity.QTMatchPO;

/**
 * 竞彩足球赛事数据
 * 
 * @desc
 * @author lei.li@unison.net.cn
 * @createTime 2014-2-11
 * @version 1.0
 */
public class FBMatchData {

	private long homeTeamId;
	private long guestTeamId;
	private String homeTeamName;
	private String guestTeamName;

	/** 主队最近赛事胜平负结果 */
	private List<Integer> homeMatchResult = new ArrayList<Integer>();

	/** 客队最近赛事胜平负结果 */
	private List<Integer> guestMatchResult = new ArrayList<Integer>();

	/** 主队最近胜平负比分集合 */
	private List<QTMatchPO> homeChartList = new ArrayList<QTMatchPO>();

	/** 客队最近胜平负比分集合 */
	private List<QTMatchPO> guestChartList = new ArrayList<QTMatchPO>();

	/** 主队不输的概率 */
	private String homeNotLosePercent;

	/** 客队队不输的概率 */
	private String guestNotLosePercent;

	public String getHomeNotLosePercent() {
		return homeNotLosePercent;
	}

	public void setHomeNotLosePercent(String homeNotLosePercent) {
		this.homeNotLosePercent = homeNotLosePercent;
	}

	public String getGuestNotLosePercent() {
		return guestNotLosePercent;
	}

	public void setGuestNotLosePercent(String guestNotLosePercent) {
		this.guestNotLosePercent = guestNotLosePercent;
	}

	public List<QTMatchPO> getHomeChartList() {
		return homeChartList;
	}

	public void setHomeChartList(List<QTMatchPO> homeChartList) {
		this.homeChartList = homeChartList;
	}

	public List<QTMatchPO> getGuestChartList() {
		return guestChartList;
	}

	public void setGuestChartList(List<QTMatchPO> guestChartList) {
		this.guestChartList = guestChartList;
	}

	public List<Integer> getHomeMatchResult() {
		return homeMatchResult;
	}

	public void setHomeMatchResult(List<Integer> homeMatchResult) {
		this.homeMatchResult = homeMatchResult;
	}

	public List<Integer> getGuestMatchResult() {
		return guestMatchResult;
	}

	public void setGuestMatchResult(List<Integer> guestMatchResult) {
		this.guestMatchResult = guestMatchResult;
	}

	public long getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(long homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public long getGuestTeamId() {
		return guestTeamId;
	}

	public void setGuestTeamId(long guestTeamId) {
		this.guestTeamId = guestTeamId;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	public String getGuestTeamName() {
		return guestTeamName;
	}

	public void setGuestTeamName(String guestTeamName) {
		this.guestTeamName = guestTeamName;
	}
}