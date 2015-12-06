package com.unison.lottery.api.bet.parse;

import java.util.ArrayList;
import java.util.List;
import com.unison.lottery.api.protocol.common.Constants;

/**
 * @desc 高频投注内容解析 
 * @createTime 2013-1-5
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public final class HFBetContentParse {
	
	/**
	 * 对于高频的多玩法投注时，对应投注内容解析为List
	 * @param betContents 格式：按★进行分割
	 * @return
	 */
	public static List<String> getBetContentList(String betContents) {
		List<String> list = new ArrayList<String>();
		if(null != betContents) {
			String[] betContentArr = betContents.split(Constants.CLIENT_CONTENT_SPLIT);
			for(String bet : betContentArr) {
				list.add(bet);
			}
		}
		return list;
	}
}