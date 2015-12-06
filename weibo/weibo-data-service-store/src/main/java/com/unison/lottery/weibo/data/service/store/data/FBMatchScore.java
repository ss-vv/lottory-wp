package com.unison.lottery.weibo.data.service.store.data;

import com.unison.lottery.weibo.data.service.store.persist.entity.QTMatchPO;

public class FBMatchScore extends QTMatchPO {

	private static final long serialVersionUID = 1L;

	/** 是否完场:true表示完场 */
	private boolean finish;
	
	/**主队logo*/
	private String homeTeamLogoUrl;
	
	/**客队logo*/
	private String guestTeamLogoUrl;
	
	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean isFinish) {
		this.finish = isFinish;
	}

	public String getHomeTeamLogoUrl() {
		return homeTeamLogoUrl;
	}

	public void setHomeTeamLogoUrl(String homeTeamLogoUrl) {
		this.homeTeamLogoUrl = homeTeamLogoUrl;
	}

	public String getGuestTeamLogoUrl() {
		return guestTeamLogoUrl;
	}

	public void setGuestTeamLogoUrl(String guestTeamLogoUrl) {
		this.guestTeamLogoUrl = guestTeamLogoUrl;
	}
}