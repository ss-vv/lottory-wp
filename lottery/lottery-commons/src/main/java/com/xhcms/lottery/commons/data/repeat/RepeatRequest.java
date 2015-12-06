package com.xhcms.lottery.commons.data.repeat;


/**
 * @desc 用户追号请求封装
 * @createTime 2013-8-5
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class RepeatRequest {

	private String lotteryId;
	
	private String playId;
	
	private long sponsorId;
	
	private int status;
	
	private int planStopType;
	
	private int bonusForStop;
	
	private int bonusForStopMeal;
	
	private int privacy;
	
	private int mealPrivacy;
	
	private int suite;

	private int stopedReason;
	
	private int repeatType;
	
	private String betNoteList;
	
	private String stopType;
	
	private String stopTypeMeal;
	
	private String issueNumber;
	
	public int getStopedReason() {
		return stopedReason;
	}

	public void setStopedReason(int stopedReason) {
		this.stopedReason = stopedReason;
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

	public long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public int getPlanStopType() {
		return planStopType;
	}

	public void setPlanStopType(int planStopType) {
		this.planStopType = planStopType;
	}

	public int getBonusForStop() {
		return bonusForStop;
	}

	public void setBonusForStop(int bonusForStop) {
		this.bonusForStop = bonusForStop;
	}

	public int getPrivacy() {
		return privacy;
	}

	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}

	public int getSuite() {
		return suite;
	}

	public void setSuite(int suite) {
		this.suite = suite;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the repeatType
	 */
	public int getRepeatType() {
		return repeatType;
	}

	/**
	 * @param repeatType the repeatType to set
	 */
	public void setRepeatType(int repeatType) {
		this.repeatType = repeatType;
	}

	/**
	 * @return the betNoteList
	 */
	public String getBetNoteList() {
		return betNoteList;
	}

	/**
	 * @param betNoteList the betNoteList to set
	 */
	public void setBetNoteList(String betNoteList) {
		this.betNoteList = betNoteList;
	}

	/**
	 * @return the stopType
	 */
	public String getStopType() {
		return stopType;
	}

	/**
	 * @param stopType the stopType to set
	 */
	public void setStopType(String stopType) {
		this.stopType = stopType;
	}

	/**
	 * @return the mealPrivacy
	 */
	public int getMealPrivacy() {
		return mealPrivacy;
	}

	/**
	 * @param mealPrivacy the mealPrivacy to set
	 */
	public void setMealPrivacy(int mealPrivacy) {
		this.mealPrivacy = mealPrivacy;
	}

	/**
	 * @return the stopTypeMeal
	 */
	public String getStopTypeMeal() {
		return stopTypeMeal;
	}

	/**
	 * @param stopTypeMeal the stopTypeMeal to set
	 */
	public void setStopTypeMeal(String stopTypeMeal) {
		this.stopTypeMeal = stopTypeMeal;
	}

	/**
	 * @return the bonusForStopMeal
	 */
	public int getBonusForStopMeal() {
		return bonusForStopMeal;
	}

	/**
	 * @param bonusForStopMeal the bonusForStopMeal to set
	 */
	public void setBonusForStopMeal(int bonusForStopMeal) {
		this.bonusForStopMeal = bonusForStopMeal;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
}
