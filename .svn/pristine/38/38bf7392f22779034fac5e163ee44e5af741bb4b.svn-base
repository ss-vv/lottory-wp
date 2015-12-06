package com.xhcms.lottery.commons.data;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * 数字彩投注票内容 Data Transfer Object.
 * 一个 HFBetContent 对象对应一条用户的投注选项。
 * 
 * @author yangbo
 */
public class DigitalBetContent implements Serializable {
	private static final long serialVersionUID = 6057776860107084190L;
	private long id;
	private String lotteryId;
	private String playId;
	private long schemeId;
	private long issueId;
	private String issueNumber; // 期号
	private String code;		// 投注选项
	private int chooseType;		// 投注的选择方式, 手选0，胆拖1，机选2
	private int note;
	private int multiple;
	private int money;
	private String bonusCode;	//开奖号
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(long schemeId) {
		this.schemeId = schemeId;
	}
	public long getIssueId() {
		return issueId;
	}
	public void setIssueId(long issueId) {
		this.issueId = issueId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getPlayId() {
		return playId;
	}
	public void setPlayId(String playId) {
		this.playId = playId;
	}
	public String getIssueNumber() {
		return issueNumber;
	}
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	public int getChooseType() {
		return chooseType;
	}
	public void setChooseType(int chooseType) {
		this.chooseType = chooseType;
	}
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	public int getNote() {
		return note;
	}
	public void setNote(int note) {
		this.note = note;
	}
	public int getMultiple() {
		return multiple;
	}
	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getBonusCode() {
		return bonusCode;
	}
	public void setBonusCode(String bonusCode) {
		this.bonusCode = bonusCode;
	}
}
