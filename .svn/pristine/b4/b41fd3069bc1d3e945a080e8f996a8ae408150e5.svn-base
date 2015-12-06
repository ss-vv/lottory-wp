package com.xhcms.lottery.commons.util;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.LotteryId;

public final class SchemeStatusTools {

	public static boolean isFinalStatus(int schemeStatus) {
		boolean result = false;
		switch (schemeStatus) {
		case EntityStatus.TICKET_INIT:
		case EntityStatus.TICKET_ALLOW_BUY:
		case EntityStatus.TICKET_REQUIRED:
		case EntityStatus.TICKET_BUY_FAIL:
		case EntityStatus.TICKET_BUY_SUCCESS:
		case EntityStatus.TICKET_NOT_AWARD:
			break;
		case EntityStatus.TICKET_NOT_WIN:
		case EntityStatus.TICKET_AWARDED:
		case EntityStatus.TICKET_SCHEME_FLOW:
		case EntityStatus.TICKET_SCHEME_CANCEL:
			result = true;
			break;
		}
		return result;
	}
	
	/**
	 * 判断是否取到所有比赛的赛果
	 * @param matchs
	 * @return
	 */
	public static boolean isGetAllMatchResult(List<BetMatch> matchs) {
		boolean result = true;
		if(null != matchs && matchs.size() > 0) {
			for(BetMatch betMatch : matchs) {
				PlayMatch playMatch = (PlayMatch) betMatch;
				String matchResult = playMatch.getResult();
				//判断是否获取到赛果
				if(StringUtils.isBlank(matchResult)) {
					result = false;
					break;
				}
				//北单方案的缓存需要等到有开奖赔率之后才能从缓存更新列表中移除
				if(playMatch.getPlayId().indexOf(LotteryId.BJDC.name()) != -1) {
					if(StringUtils.isBlank(playMatch.getResultOdd())) {
						result = false;
						break;
					}
				}
			}
		} else {
			result = false;
		}
		return result;
	}
}