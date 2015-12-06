package com.unison.lottery.weibo.data.service.store.data;

import java.math.BigDecimal;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBLotteryCorpPO;

/**
 * 篮球欧赔指数, 值对象
 * 
 * @desc
 * @author lei.li@unison.net.cn
 * @createTime 2014-2-13
 * @version 1.0
 */
public class BBEuropeOddVO extends BBLotteryCorpPO {

	private int index;

	private BigDecimal homeWinOdds; // 主胜赔率

	private BigDecimal guestWinOdds; // 客胜赔率

	private BigDecimal homeWinPer; // 主胜率

	private BigDecimal guestWinPer; // 客胜率

	private String kellyHomeWin; // 主胜凯利指数

	private String kellyGuestWin; // 客胜凯利指数

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public BigDecimal getHomeWinOdds() {
		return homeWinOdds;
	}

	public void setHomeWinOdds(BigDecimal homeWinOdds) {
		this.homeWinOdds = homeWinOdds;
	}

	public BigDecimal getGuestWinOdds() {
		return guestWinOdds;
	}

	public void setGuestWinOdds(BigDecimal guestWinOdds) {
		this.guestWinOdds = guestWinOdds;
	}

	public BigDecimal getHomeWinPer() {
		return homeWinPer;
	}

	public void setHomeWinPer(BigDecimal homeWinPer) {
		this.homeWinPer = homeWinPer;
	}

	public BigDecimal getGuestWinPer() {
		return guestWinPer;
	}

	public void setGuestWinPer(BigDecimal guestWinPer) {
		this.guestWinPer = guestWinPer;
	}

	public String getKellyHomeWin() {
		return kellyHomeWin;
	}

	public void setKellyHomeWin(String kellyHomeWin) {
		this.kellyHomeWin = kellyHomeWin;
	}

	public String getKellyGuestWin() {
		return kellyGuestWin;
	}

	public void setKellyGuestWin(String kellyGuestWin) {
		this.kellyGuestWin = kellyGuestWin;
	}
}