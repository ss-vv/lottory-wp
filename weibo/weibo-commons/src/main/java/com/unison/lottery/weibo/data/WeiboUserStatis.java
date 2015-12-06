package com.unison.lottery.weibo.data;

import java.util.Date;

public class WeiboUserStatis extends WeiboUser{
	private static final long serialVersionUID = 1L;
	private int commetsCount;
	private int discussCount;
	
	private int realWeiboCount; 
	private int realWeibo7Count; 
	private int realWeiboWinCount; 
	private int realWeibo7WinCount;
	private int realWeibo7NotWinCount;
	private int realWeibo7GainCount;
	private int realWeibo7NotOpenCount;
	private int realWeibo7OpenCount;
	
	private int recMatchCount; 
	private int recMatch7Count; 
	private int recMatchWinCount; 
	private int recMatch7WinCount;
	private int recMatch7NotWinCount;
	private int recMatch7NotOpenCount;
	private int recMatch7OpenCount;
	
	private double totalBonus;
	private int guodanlv;
	
	private int weiboCount;
	
	private Date updateDate;
	
	//存redis数据使用-----------
	private String nickName;
	private Long id;
	private Long weiboUserId;
	//存redis数据使用-----------
	
	public int getRealWeiboCount() {
		return realWeiboCount;
	}
	public void setRealWeiboCount(int realWeiboCount) {
		this.realWeiboCount = realWeiboCount;
	}
	public int getRealWeibo7Count() {
		return realWeibo7Count;
	}
	public void setRealWeibo7Count(int realWeibo7Count) {
		this.realWeibo7Count = realWeibo7Count;
	}
	public int getRealWeiboWinCount() {
		return realWeiboWinCount;
	}
	public void setRealWeiboWinCount(int realWeiboWinCount) {
		this.realWeiboWinCount = realWeiboWinCount;
	}
	public int getRealWeibo7WinCount() {
		return realWeibo7WinCount;
	}
	public void setRealWeibo7WinCount(int realWeibo7WinCount) {
		this.realWeibo7WinCount = realWeibo7WinCount;
	}
	public int getRecMatchCount() {
		return recMatchCount;
	}
	public void setRecMatchCount(int recMatchCount) {
		this.recMatchCount = recMatchCount;
	}
	public int getRecMatch7Count() {
		return recMatch7Count;
	}
	public void setRecMatch7Count(int recMatch7Count) {
		this.recMatch7Count = recMatch7Count;
	}
	public int getRecMatchWinCount() {
		return recMatchWinCount;
	}
	public void setRecMatchWinCount(int recMatchWinCount) {
		this.recMatchWinCount = recMatchWinCount;
	}
	public int getRecMatch7WinCount() {
		return recMatch7WinCount;
	}
	public void setRecMatch7WinCount(int recMatch7WinCount) {
		this.recMatch7WinCount = recMatch7WinCount;
	}
	public int getRealWeibo7NotWinCount() {
		return realWeibo7NotWinCount;
	}
	public void setRealWeibo7NotWinCount(int realWeibo7NotWinCount) {
		this.realWeibo7NotWinCount = realWeibo7NotWinCount;
	}
	public int getRecMatch7NotWinCount() {
		return recMatch7NotWinCount;
	}
	public void setRecMatch7NotWinCount(int recMatch7NotWinCount) {
		this.recMatch7NotWinCount = recMatch7NotWinCount;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public int getRealWeibo7NotOpenCount() {
		return realWeibo7NotOpenCount;
	}
	public void setRealWeibo7NotOpenCount(int realWeibo7NotOpenCount) {
		this.realWeibo7NotOpenCount = realWeibo7NotOpenCount;
	}
	public int getRecMatch7NotOpenCount() {
		return recMatch7NotOpenCount;
	}
	public void setRecMatch7NotOpenCount(int recMatch7NotOpenCount) {
		this.recMatch7NotOpenCount = recMatch7NotOpenCount;
	}
	public int getCommetsCount() {
		return commetsCount;
	}
	public void setCommetsCount(int commetsCount) {
		this.commetsCount = commetsCount;
	}
	public int getDiscussCount() {
		return discussCount;
	}
	public void setDiscussCount(int discussCount) {
		this.discussCount = discussCount;
	}
	public int getRealWeibo7GainCount() {
		return realWeibo7GainCount;
	}
	public void setRealWeibo7GainCount(int realWeibo7GainCount) {
		this.realWeibo7GainCount = realWeibo7GainCount;
	}
	public int getRealWeibo7OpenCount() {
		return realWeibo7OpenCount;
	}
	public void setRealWeibo7OpenCount(int realWeibo7OpenCount) {
		this.realWeibo7OpenCount = realWeibo7OpenCount;
	}
	public int getRecMatch7OpenCount() {
		return recMatch7OpenCount;
	}
	public void setRecMatch7OpenCount(int recMatch7OpenCount) {
		this.recMatch7OpenCount = recMatch7OpenCount;
	}
	public double getTotalBonus() {
		return totalBonus;
	}
	public void setTotalBonus(double totalBonus) {
		this.totalBonus = totalBonus;
	}
	public int getGuodanlv() {
		return guodanlv;
	}
	public void setGuodanlv(int guodanlv) {
		this.guodanlv = guodanlv;
	}
	public int getWeiboCount() {
		return weiboCount;
	}
	public void setWeiboCount(int weiboCount) {
		this.weiboCount = weiboCount;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getWeiboUserId() {
		return weiboUserId;
	}
	public void setWeiboUserId(Long weiboUserId) {
		this.weiboUserId = weiboUserId;
	}
}
