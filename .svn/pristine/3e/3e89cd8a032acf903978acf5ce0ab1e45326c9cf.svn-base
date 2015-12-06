package com.xhcms.lottery.lang;

/**
 * @desc 追号计划停止原因的类型
 * @createTime 2013-8-5
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public enum RepeatPlanStopReasonType {
	
	NO_STOP(0, "noStoop"),			//未被停止
	PRIZED_STOP(1, "prizedStop"),	//中奖即停
	BONUS_FOR_STOP(2, "bonusForStop"),//盈利达标标准
	USER_STOP(3, "userStop");		//用户手动停止
	
	private int type;
	
	private String name;
	
	private RepeatPlanStopReasonType(int type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public static String getName(int type) {
		RepeatPlanStopReasonType[] status = RepeatPlanStopReasonType.values();
		String name = null;
		for(RepeatPlanStopReasonType s : status) {
			if(s.getType() == type) {
				name = s.getName();break;
			}
		}
		return name;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
