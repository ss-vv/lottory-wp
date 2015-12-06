package com.unison.lottery.weibo.data.service.store.data;

import com.unison.lottery.weibo.data.service.store.persist.entity.LotteryCorpPO;

/**
 * 足球欧赔指数值对象
 * @desc
 * @author lei.li@unison.net.cn
 * @createTime 2014-2-13
 * @version 1.0
 */
public class FBEuropeOddVO extends LotteryCorpPO {

	private int index;
	
	/** 欧赔胜平负 */
	private String europeOddWin;
	private String europeOddFlat;
	private String europeOddNegative;

	/** 凯利指数胜平负 */
	private String kellyOddWin;
	private String kellyOddFlat;
	private String kellyOddNegative;

	public String getEuropeOddWin() {
		return europeOddWin;
	}

	public void setEuropeOddWin(String europeOddWin) {
		this.europeOddWin = europeOddWin;
	}

	public String getEuropeOddFlat() {
		return europeOddFlat;
	}

	public void setEuropeOddFlat(String europeOddFlat) {
		this.europeOddFlat = europeOddFlat;
	}

	public String getEuropeOddNegative() {
		return europeOddNegative;
	}

	public void setEuropeOddNegative(String europeOddNegative) {
		this.europeOddNegative = europeOddNegative;
	}

	public String getKellyOddWin() {
		return kellyOddWin;
	}

	public void setKellyOddWin(String kellyOddWin) {
		this.kellyOddWin = kellyOddWin;
	}

	public String getKellyOddFlat() {
		return kellyOddFlat;
	}

	public void setKellyOddFlat(String kellyOddFlat) {
		this.kellyOddFlat = kellyOddFlat;
	}

	public String getKellyOddNegative() {
		return kellyOddNegative;
	}

	public void setKellyOddNegative(String kellyOddNegative) {
		this.kellyOddNegative = kellyOddNegative;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}