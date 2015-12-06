package com.unison.lottery.weibo.lang;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.xhcms.lottery.lang.LotteryId;

public class LotteryIdParser {

	/**
	 * 返回彩种串对应的彩种
	 * @param matchId 彩种赛事串ID；格式：JZ1203061001
	 * @return
	 */
	public static List<LotteryId> parse(String matchId) {
		List<LotteryId> list = new ArrayList<LotteryId>();
		if(StringUtils.isNotBlank(matchId)) {
			if(matchId.startsWith(MatchIdPrefix.JZ)) {
				list.add(LotteryId.JCZQ);
			} else if(matchId.startsWith(MatchIdPrefix.JL)) {
				list.add(LotteryId.JCLQ);
			} else if(matchId.startsWith(MatchIdPrefix.CT)) {
				list.add(LotteryId.CTZC);
			}
		}
		return list;
	}
	
}