package com.xhcms.lottery.commons.data;

public class PresetResultPerPlayId {
	private boolean succ;
	private int canAwardSchemes;
	private int skipSchemes;
	private int notAwardSchemes;
	private int totalSchemes;
	private String message;
	private String playId;
	
	public boolean isSucc() {
		return succ;
	}
	public void setSucc(boolean succ) {
		this.succ = succ;
	}
	public int getCanAwardSchemes() {
		return canAwardSchemes;
	}
	public void setCanAwardSchemes(int canAwardSchemes) {
		this.canAwardSchemes = canAwardSchemes;
	}
	public int getSkipSchemes() {
		return skipSchemes;
	}
	public void setSkipSchemes(int skipSchemes) {
		this.skipSchemes = skipSchemes;
	}
	public int getNotAwardSchemes() {
		return notAwardSchemes;
	}
	public void setNotAwardSchemes(int notAwardSchemes) {
		this.notAwardSchemes = notAwardSchemes;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getPlayId() {
		return playId;
	}
	public void setPlayId(String playId) {
		this.playId = playId;
	}
	public int getTotalSchemes() {
		return totalSchemes;
	}
	public void setTotalSchemes(int totalSchemes) {
		this.totalSchemes = totalSchemes;
	}
	public void addSkipScheme() {
		this.skipSchemes++;
		
	}
	public void addCanAwardScheme() {
		this.canAwardSchemes++;
		
	}
	public void addNotAwardScheme() {
		this.notAwardSchemes++;
		
	}

}
