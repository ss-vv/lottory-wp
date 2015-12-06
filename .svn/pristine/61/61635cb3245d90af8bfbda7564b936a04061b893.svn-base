package com.unison.lottery.weibo.dataservice.crawler.service.model;

import java.io.Serializable;
import java.util.Date;

import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;

/**
 * 球探赛事赔率模型
 * @author guixiang.cui
 *
 */
public class QtBasketMatchOddsModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4852076802194318160L;
	
	private Integer bsId;//
	private Integer source; //来源：1球探
	private Integer processStatus; //是否已处理
	
	private String corpId;
	private String corpName;
	private String qtOddsId;
	private double HomeWinOdds;//主胜
	private double handicapOrScore;//让球，大小分（只用在亚赔、大小，欧赔无该数据）
	private double GuestWinOdds;//主负
	private Date timestamp;//时间点
	private Qt_fb_match_oddsType oddsType;
	
	private String qtBsId;
	
	private Date createTime;
	private Date updateTime;
	
	QtBasketMatchOddsModel qtBasketInitOddsModel;  //存放初盘内容
	
	public QtBasketMatchOddsModel getQtBasketInitOddsModel() {
		return qtBasketInitOddsModel;
	}
	
	public String getQtBsId() {
		return qtBsId;
	}

	public void setQtBsId(String qtBsId) {
		this.qtBsId = qtBsId;
	}

	public void setQtBasketInitOddsModel(
			QtBasketMatchOddsModel qtBasketInitOddsModel) {
		this.qtBasketInitOddsModel = qtBasketInitOddsModel;
	}
	public String getQtOddsId() {
		return qtOddsId;
	}
	
	public Qt_fb_match_oddsType getOddsType() {
		return oddsType;
	}
	public void setOddsType(Qt_fb_match_oddsType oddsType) {
		this.oddsType = oddsType;
	}
	public void setQtOddsId(String qtOddsId) {
		this.qtOddsId = qtOddsId;
	}
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public Integer getBsId() {
		return bsId;
	}
	public void setBsId(Integer bsId) {
		this.bsId = bsId;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public double getHomeWinOdds() {
		return HomeWinOdds;
	}
	public void setHomeWinOdds(double homeWinOdds) {
		HomeWinOdds = homeWinOdds;
	}
	public double getHandicapOrScore() {
		return handicapOrScore;
	}
	public void setHandicapOrScore(double handicapOrScore) {
		this.handicapOrScore = handicapOrScore;
	}
	public double getGuestWinOdds() {
		return GuestWinOdds;
	}
	public void setGuestWinOdds(double guestWinOdds) {
		GuestWinOdds = guestWinOdds;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
