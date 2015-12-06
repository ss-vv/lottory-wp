package com.xhcms.lottery.commons.data;

import java.util.List;
import com.xhcms.lottery.lang.PlayType;

/**
 * 投注请求基础类。
 * 当用户确认购买订单时，从action传递给service的对象。
 * 
 * @author Yang Bo
 */
public class BetRequest {

	// 用户id
	private long userId;
	
	// 彩种
	private String lotteryId;
	
	// 玩法
	private PlayType playType;
	
	// 期号
	private String issue;
	
	// 投注的内容列表. 注意，前一的手选也和其他任选一样，放在一条记录中，用逗号分隔而已。
	private List<String> betContents;
	
	// 倍数
	private int multiple;

	// 总注数，是单倍注数乘以倍数后的结果. 
	private int totalNotesCount;
	
	// 总钱数, 单位是元
	private int money;
	
	//注数
	private int betNote;
	
	//投注类型：代购，晒单，合买
	private int betType;

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public List<String> getBetContents() {
		return betContents;
	}

	public void setBetContents(List<String> betContents) {
		this.betContents = betContents;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public int getTotalNotesCount() {
		return totalNotesCount;
	}

	public void setTotalNotesCount(int totalNotesCount) {
		this.totalNotesCount = totalNotesCount;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public int getBetNote() {
		return betNote;
	}

	public void setBetNote(int betNote) {
		this.betNote = betNote;
	}

	public int getBetType() {
		return betType;
	}

	public void setBetType(int betType) {
		this.betType = betType;
	}
}
