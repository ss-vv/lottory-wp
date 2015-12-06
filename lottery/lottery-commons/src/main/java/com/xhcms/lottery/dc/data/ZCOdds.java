package com.xhcms.lottery.dc.data;




/**
 * 足彩赔率
 * 
 * @author xulang
 */
public class ZCOdds extends OddsBase {
	private int concedePoints; // 让球

	/**
	 * 构造 ZCOdds 时, 玩法Id, 和 选项 作为必传参数
	 * 
	 * @param playId
	 * @param options
	 */
	public ZCOdds(String playId, String options) {
		this.playId = playId;
		this.options = options;
	}

	public int getConcedePoints() {
		return concedePoints;
	}

	public void setConcedePoints(int concedePoints) {
		this.concedePoints = concedePoints;
	}

}
