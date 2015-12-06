package com.unison.lottery.weibo.data;

/**
 * Recent Date Type enum.<br/>
 * 定义了一些常用时间长度，单位是毫秒。
 * @author lei.li@unison.net.cn
 */
public enum RecentDateType {
	FIVE_MINUTES("FIVE_MINUTES", 5 * 60 * 1000L),//5分钟
	HALF_AN_HOUR("HALF_AN_HOUR", 30 * 60 * 1000L),//半小时
	DAY("DAY", 24 * 60 * 60 * 1000L),	//最近24小时
	TWO_DAY("TWO_DAY", 2 * 24 * 60 * 60 * 1000L),	//最近48小时
	WEEK("WEEK", 7 * 24 * 60 * 60 * 1000L),	//最近7天
	MONTH("MONTH", 30 * 24 * 60 * 60 * 1000L),	//最近30天
	YEAR("YEAR", 365 * 24 * 60 * 60 * 1000L);	//最近一年
	
	private String name;
	
	private long time;

	private RecentDateType(String name, long time) {
		this.name = name;
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	/**
	 * 之前的时间。<br/>
	 * @return 比如枚举值是MONTH，那么返回的就是当前时间减去30天（固定）的时间。
	 */
	public long ago(){
		return System.currentTimeMillis() - time;
	}
}