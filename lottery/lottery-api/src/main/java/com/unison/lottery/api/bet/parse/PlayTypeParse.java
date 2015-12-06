package com.unison.lottery.api.bet.parse;

import java.util.ArrayList;
import java.util.List;
import com.unison.lottery.api.protocol.common.Constants;
import com.xhcms.lottery.lang.PlayType;

/**
 * @desc 玩法解析
 * @createTime 2013-1-5
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public final class PlayTypeParse {
	
	/**
	 * 根据彩种解析投注玩法
	 * @param lotteryId 彩种
	 * @param playId 格式：
	 * 		重庆时时投注时支持多玩法，玩法id★玩法id;按★进行分割；
	 * 		江西十一选五投注时只有一个玩法
	 * @return
	 */
	public static List<PlayType> getPlayTypeList(String lotteryId, String playIdStr) {
		List<PlayType> list = new ArrayList<PlayType>();
		if(null != playIdStr) {
			String[] playIdArr = playIdStr.split(Constants.CLIENT_CONTENT_SPLIT);
			for(String playId : playIdArr) {
				if(com.xhcms.lottery.lang.Constants.CQSS.equals(lotteryId)
						|| com.xhcms.lottery.lang.Constants.JX11.equals(lotteryId)) {
					PlayType playType = PlayType.valueOf(playId);
					list.add(playType);
				}
			}
		}
		return list;
	}
}