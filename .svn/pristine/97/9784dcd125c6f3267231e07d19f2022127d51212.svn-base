package com.unison.lottery.mc.uni.parser.util;

import com.xhcms.lottery.lang.EntityStatus;

public final class ZMStatusMapper {

	/**
	 * 北单，接口赛程状态到大V彩自定义赛程状态的转换
	 * @param matchStatus
	 * @return
	 */
	public static int convertBjdcStatus(int matchStatus) {
		int result = EntityStatus.MATCH_STOP_SELLING;
		switch (matchStatus) {
		case 0:
			result = EntityStatus.MATCH_ON_SALE;
			break;
		case 1:
		case 2:
			result = EntityStatus.MATCH_STOP_SELLING;
			break;
		case 3:
			result = EntityStatus.MATCH_CANCEL;
			break;
		case 4:
			result = EntityStatus.MATCH_OVER;
			break;
		}
		return result;
	}
}