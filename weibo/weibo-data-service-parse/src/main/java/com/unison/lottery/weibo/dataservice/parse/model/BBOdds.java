package com.unison.lottery.weibo.dataservice.parse.model;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 篮球赔率总对象
 * 
 * @author Yang Bo
 */
public class BBOdds {

	// 第一部分：联赛资料
	private String leagueInfo;
	
	// 第二部分，赛程资料
	private BBMatchInfoData bbMatchInfo;
	
	// 第三部分，让分赔率
	private List<BBOddsConcedeData> bbOddsConcede = new LinkedList<>();
	
	// 第四部分，欧赔（标准盘） 
	private List<BBOddsEuroData> bbOddsEuro = new LinkedList<>();
	
	// 第五部分：大小球
	private List<BBOddsBigData> bbOddsBig = new LinkedList<>();
	
	// 第六部分：日期
	private String date;

	public String getLeagueInfo() {
		return leagueInfo;
	}

	public void setLeagueInfo(String leagueInfo) {
		this.leagueInfo = leagueInfo;
	}

	public BBMatchInfoData getBbMatchInfo() {
		return bbMatchInfo;
	}

	public void setBbMatchInfo(BBMatchInfoData bbMatchInfo) {
		this.bbMatchInfo = bbMatchInfo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public List<BBOddsEuroData> getBbOddsEuro() {
		return bbOddsEuro;
	}

	public void setBbOddsEuro(List<BBOddsEuroData> bbOddsEuro) {
		this.bbOddsEuro = bbOddsEuro;
	}

	public void setBbOddsBig(List<BBOddsBigData> bbOddsBig) {
		this.bbOddsBig = bbOddsBig;
	}

	public List<BBOddsBigData> getBbOddsBig() {
		return bbOddsBig;
	}

	public List<BBOddsConcedeData> getBbOddsConcede() {
		return bbOddsConcede;
	}

	public void setBbOddsConcede(List<BBOddsConcedeData> bbOddsConcede) {
		this.bbOddsConcede = bbOddsConcede;
	}
}
