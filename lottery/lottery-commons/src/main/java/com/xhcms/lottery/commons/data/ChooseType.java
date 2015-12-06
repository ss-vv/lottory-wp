package com.xhcms.lottery.commons.data;

/**
 * 投注的产生方式：机选、手选。
 * 
 * @author Yang Bo
 */
public enum ChooseType {
	MANUAL(0),		// 手选=复式 
	DAN(1),			// 胆拖
	MACHINE(2),		// 机选=单式
	UNKNOWN(-1)		// 未定义
	;
	
	private int index;
	
	ChooseType(int index) {
		this.index = index;
	}

	public int getIndex(){
		return index;
	}
	
	public static ChooseType valueOfIndex(int index){
		for (ChooseType type : ChooseType.values()){
			if (type.index == index){
				return type;
			}
		}
		throw new IllegalArgumentException("Unknown ChooseType index: " + index);
	}
}
