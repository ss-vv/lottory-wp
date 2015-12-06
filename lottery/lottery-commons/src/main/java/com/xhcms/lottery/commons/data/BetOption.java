package com.xhcms.lottery.commons.data;

import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PlayType;

/**
 * 投注选项基础类。
 * 
 * @author Yang Bo
 */
public abstract class BetOption {
	public static final String COMMA = ",";
	public static final String SEMICOLON = ";";
	public static final String VERTICALBAR = "|";
	public static final String BAR_REGEXP = "\\|";
	public static final String BLANK = "";
	
	/**
	 * 胆拖号码之间的分隔符
	 */
	public static final String AT_SYMBOL  = "@";

	protected PlayType playType;
	protected ChooseType chooseType;
	protected String option;
	protected int notes;
	protected int money;
	
	protected String betType;	// 中民接口中用到的 betType
	protected String dan;		// 胆
	
	public BetOption(PlayType playType, ChooseType chooseType, String option, String dan) throws BetException {
		this.playType = playType;
		this.chooseType = chooseType;
		this.option = option;
		this.dan = dan;
		computeNotes();
		this.money = notes * Constants.MONEY_PER_NOTE; // 2元一注
		computeBetType();
	}
	
	public BetOption(PlayType playType, ChooseType chooseType, String option) throws BetException {
		this(playType, chooseType, option, "");
	}

	
	/**
	 * 计算本投注选项代表的 betType。
	 */
	protected abstract void computeBetType();

	public int getMoney() {
		return money;
	}

	public int getNotes() {
		return notes;
	}

	public String getBetType(){
		return betType;
	}
	
	abstract protected void computeNotes() throws BetException;
}
