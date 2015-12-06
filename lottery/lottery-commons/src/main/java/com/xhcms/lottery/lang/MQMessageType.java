package com.xhcms.lottery.lang;

/**
 * @desc 消息类型
 * @author lei.li@unison.net.cn
 * @createTime 2014-6-19
 * @version 1.0
 */
public enum MQMessageType {

	NORMAL("普通私信", 0), 
	SYSTEM_MESSAGE("系统消息", 1), 
	WITHDRAW("提款", 2),
	PRIZE("派奖", 3),
	WINNING("喜报",4),
	ANALYSIS_EXPERT("分析达人通知推送",5);

	private MQMessageType(String name, int type) {
		this.name = name;
		this.type = type;
	}

	public static String getByValue(int value) {
		String name = null;
		for(MQMessageType letteryType : MQMessageType.values()) {
			if(letteryType.getType() == value) {
				name = letteryType.getName();
				break;
			}
		}
		return name;
	}
	
	private String name;

	private int type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}