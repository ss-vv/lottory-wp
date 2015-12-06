package com.unison.lottery.weibo.data.vo;

import java.util.Date;

/**
 * @Description:发微博时，$查询赛事的VO 
 * @author 江浩翔   
 * @date 2014-1-22 上午11:52:50 
 * @version V1.0
 */
public class PromptMatchVO {
	/** 赛事id 对应相关赛事表的id*/
	private String id;
	
	/** 赛事名称 ，eg: 皇马 VS 巴萨 */
	private String matchName;
	
	/** 赛事cnCode或期号 （如果是传统足彩 ，该域为 期号，否则为cnCode）*/
	private String matchCode;
	
	/** 赛事截至时间（大V彩截至投注时间）*/
	private Date entrustDeadline;
	
	/** 表示该赛事类型，值为："JCZQ"、"JCLQ"、"CTZC" , 参考class ：Constant.SolrConfig.SEARCH_TYPE_CTZC_MATCH 等*/
	private String lotteryType;
	
	/** 传统足彩有该域，其他类型该值为null*/
	private String matchId;
	
	private Date matchTime;
	
	private Float score;
	
	/** 是否在售*/
	private boolean isSale;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMatchName() {
		return matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

	public String getMatchCode() {
		return matchCode;
	}

	public void setMatchCode(String matchCode) {
		this.matchCode = matchCode;
	}

	public Date getEntrustDeadline() {
		return entrustDeadline;
	}

	public void setEntrustDeadline(Date entrustDeadline) {
		this.entrustDeadline = entrustDeadline;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public boolean isSale() {
		return isSale;
	}

	public void setSale(boolean isSale) {
		this.isSale = isSale;
	}

	public Date getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}
}