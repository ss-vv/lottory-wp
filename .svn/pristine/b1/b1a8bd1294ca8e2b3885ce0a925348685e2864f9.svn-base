package com.xhcms.lottery.commons.util;

import com.xhcms.lottery.lang.PlayType;

public final class BJDCPlayIdConvert {

	public static String convertQueryPlayId(String playId) {
		String queryPlayId = null;
		if (PlayType.BJDC_SF.getShortPlayStr().equals(playId)) {
			queryPlayId = PlayType.BJDC_SF.getShortPlayStr();
		} else {
			queryPlayId = PlayType.BJDC_SPF.getShortPlayStr();
		}
		return queryPlayId;
	}
}