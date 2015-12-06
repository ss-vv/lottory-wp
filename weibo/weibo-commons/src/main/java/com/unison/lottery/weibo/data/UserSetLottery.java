package com.unison.lottery.weibo.data;

/**
 * 用户设置的彩种
 * @desc
 * @author lei.li@unison.net.cn
 * @createTime 2013-11-26
 * @version 1.0
 */
public class UserSetLottery {

	//彩种名称
	private String name;
	
	//顺序值
	private Double score;
	
	//显示的彩种中文名称
	private String viewName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
}
