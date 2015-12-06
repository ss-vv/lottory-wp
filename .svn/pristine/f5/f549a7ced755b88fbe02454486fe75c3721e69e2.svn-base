package com.unison.lottery.weibo.data.service.store.data;

/**
 * @desc 积分榜类型枚举，与数据库值对应
 * @author lei.li@unison.net.cn
 * @createTime 2014-2-20
 * @version 1.0
 */
public enum ScoreTypeEnum {
	
	//积分榜类型: 0 总积分榜，1主场积分，2客场积分，3半场积分榜，4半场主场积分，5半场客场积分
	
	TOTAL_SCORE(0),
	HOME_SCORE(1),
	GUEST_SCORE(2),
	HALF_SCORE(3),
	HALF_HOME_SCORE(4),
	HALF_GUEST_SCORE(5);
	
	int type;
	
	private ScoreTypeEnum(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
