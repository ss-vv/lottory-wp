package com.unison.lottery.weibo.lang;

/**
 * 
 * @desc 微博类型枚举
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-24
 * @version 1.0
 */
public enum WeiboType {

	NORMAL("普通", "0"), 
	SHOW_SCHEME("晒单", "1"), 
	RECOMMEND("推荐", "2"),
	GROUP("合买", "3"),
	WINNING("喜报", "4"),
	FOLLOW("跟单", "5"),
	NEWS("新闻", "6");

	private WeiboType(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public static String getByValue(String value) {
		String name = null;
		for(WeiboType type : WeiboType.values()) {
			if(type.getType().equals(value)) {
				name = type.getName();
				break;
			}
		}
		return name;
	}
	
	private String name;

	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}