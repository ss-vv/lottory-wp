package com.unison.lottery.weibo.data.service.store.data;

import com.unison.lottery.weibo.lang.MatchResultNameEnum;

/**
 * @desc 赛果统计数据对象
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-21
 * @version 1.0
 */
public class MatchResultStats {

	private String teamName;	//队名
	private int shengNum;		//胜数量
	private int fuNum;			//负数量
	private int size;			//比赛场次数
	protected String info;		//赛果字符串打印
	
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public int getShengNum() {
		return shengNum;
	}

	public void setShengNum(int shengNum) {
		this.shengNum = shengNum;
	}

	public int getFuNum() {
		return fuNum;
	}

	public void setFuNum(int fuNum) {
		this.fuNum = fuNum;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public String getInfo() {
		String pattern = "%s%s%s%s%s";
		Object[] param = new Object[]{
			teamName, shengNum, MatchResultNameEnum.SHENG.getName(),
			fuNum, MatchResultNameEnum.FU.getName()
		};
		return String.format(pattern, param);
	}
	
	@Override
	public String toString() {
		return getInfo();
	}
}
