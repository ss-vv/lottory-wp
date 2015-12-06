package com.xhcms.lottery.dc.data;




/**
 * 篮彩赔率
 * 
 * @author langhsu
 */
public class LCOdds extends OddsBase {
	private float defaultScore; // 单场让分及预设总分

	/**
	 * 构造 LCOdds 时, 玩法Id, 和 选项 作为必传参数
	 * 
	 * @param playId
	 * @param options
	 */
	public LCOdds(String playId, String options) {
		this.playId = playId;
		this.options = options;
	}

	public float getDefaultScore() {
		return defaultScore;
	}

	public void setDefaultScore(float defaultScore) {
		this.defaultScore = defaultScore;
	}
}
