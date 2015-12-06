package com.xhcms.lottery.commons.data;

import java.util.List;

import javax.persistence.Column;

/**
 * 传统足球预设比分赛事
 * @author haohao
 *
 */
public class CTFBMatchPreset extends CTFBMatch {

	private static final long serialVersionUID = -2746823203198018195L;
	private String fbHalfScore;
	private String fbScore;
	private int fbStatus;
	private List<FBMatch> fbMatchs;
	public String getFbHalfScore() {
		return fbHalfScore;
	}
	public void setFbHalfScore(String fbHalfScore) {
		this.fbHalfScore = fbHalfScore;
	}
	public String getFbScore() {
		return fbScore;
	}
	public void setFbScore(String fbScore) {
		this.fbScore = fbScore;
	}
	public int getFbStatus() {
		return fbStatus;
	}
	public void setFbStatus(int fbStatus) {
		this.fbStatus = fbStatus;
	}
	public List<FBMatch> getFbMatchs() {
		return fbMatchs;
	}
	public void setFbMatchs(List<FBMatch> fbMatchs) {
		this.fbMatchs = fbMatchs;
	}
}
