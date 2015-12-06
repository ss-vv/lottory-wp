package com.unison.lottery.weibo.data.service.store.data;

import java.math.BigDecimal;
import com.unison.lottery.weibo.data.service.store.persist.entity.LotteryCorpPO;

/**
 * 足球亚盘指数数据对象
 * 
 * @desc
 * @author lei.li@unison.net.cn
 * @createTime 2014-2-13
 * @version 1.0
 */
public class FBAsianOddVO extends LotteryCorpPO {

	private int index;

	/** 亚盘初盘水位 */
	private BigDecimal initHandicap; // 初盘盘口

	private BigDecimal initHomeOdds; // 初盘主队赔率

	private BigDecimal initGuestOdds; // 初盘客队赔率

	/** 亚盘即时盘水位 */
	private BigDecimal curHandicap; // 即时盘盘口

	private BigDecimal curHomeOdds; // 即时盘主队赔率

	private BigDecimal curGuestOdds; // 即时盘客队赔率

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public BigDecimal getInitHandicap() {
		return initHandicap;
	}

	public void setInitHandicap(BigDecimal initHandicap) {
		this.initHandicap = initHandicap;
	}

	public BigDecimal getInitHomeOdds() {
		return initHomeOdds;
	}

	public void setInitHomeOdds(BigDecimal initHomeOdds) {
		this.initHomeOdds = initHomeOdds;
	}

	public BigDecimal getInitGuestOdds() {
		return initGuestOdds;
	}

	public void setInitGuestOdds(BigDecimal initGuestOdds) {
		this.initGuestOdds = initGuestOdds;
	}

	public BigDecimal getCurHandicap() {
		return curHandicap;
	}

	public void setCurHandicap(BigDecimal curHandicap) {
		this.curHandicap = curHandicap;
	}

	public BigDecimal getCurHomeOdds() {
		return curHomeOdds;
	}

	public void setCurHomeOdds(BigDecimal curHomeOdds) {
		this.curHomeOdds = curHomeOdds;
	}

	public BigDecimal getCurGuestOdds() {
		return curGuestOdds;
	}

	public void setCurGuestOdds(BigDecimal curGuestOdds) {
		this.curGuestOdds = curGuestOdds;
	}
}