package com.unison.lottery.weibo.utils;

import org.apache.commons.lang.StringUtils;
import com.unison.lottery.weibo.lang.MatchIdPrefix;
import com.xhcms.lottery.lang.LotteryId;

public class ConverterMatchId {

	public  static String converterMatchId(String lottery,Long matchId){
		
		if(StringUtils.isNotBlank(lottery) && StringUtils.isNotBlank(matchId+"")){
			
			if(LotteryId.JCZQ.name().equals(lottery)){
				String key=matchId.toString().substring(2);
				return MatchIdPrefix.JZ+key;	
			}else if(LotteryId.JCLQ.name().equals(lottery)){
				String key=matchId.toString().substring(2);
				return MatchIdPrefix.JL+key;	
			}else if(LotteryId.BJDC.name().equals(lottery)){
				return MatchIdPrefix.BD+matchId;
			}else{
				return "";
			}
		}
		return "";
		
	}
}
