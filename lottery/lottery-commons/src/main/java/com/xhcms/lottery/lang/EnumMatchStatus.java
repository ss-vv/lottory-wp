package com.xhcms.lottery.lang;

import java.util.HashMap;
import java.util.Map;


/**
 * @author xulang
 */
public class EnumMatchStatus {
	private static Map<String, Integer> statusMap = new HashMap<String, Integer>();
	
	static {
		statusMap.put("停售", EntityStatus.MATCH_STOP_SELLING );
		statusMap.put("在售", EntityStatus.MATCH_ON_SALE);
		statusMap.put("待售", EntityStatus.MATCH_WAIT_SALE);
		statusMap.put("进行中", EntityStatus.MATCH_PLAYING);
		statusMap.put("取消", EntityStatus.MATCH_CANCEL);
		statusMap.put("已完成", EntityStatus.MATCH_OVER);
	}
	
	public static Integer getStatus(String key) {
		return statusMap.get(key);
	}
	
}