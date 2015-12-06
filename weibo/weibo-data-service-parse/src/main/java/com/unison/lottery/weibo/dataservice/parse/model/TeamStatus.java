package com.unison.lottery.weibo.dataservice.parse.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TeamStatus {
	
	private Team team;
	private int position;	//排名
	private int score;		//得分
	private int red;		//红牌数
	private int yellow;		//黄牌数
	
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getRed() {
		return red;
	}
	public void setRed(int red) {
		this.red = red;
	}
	public int getYellow() {
		return yellow;
	}
	public void setYellow(int yellow) {
		this.yellow = yellow;
	}
	public String toString(){
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
