package com.xhcms.lottery.commons.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.xhcms.lottery.lang.PlayType;

/**
 * 猜冠军玩法投注请求。
 */
public class CGJBetRequest extends BetRequest {

	public List<BetMatch> makeBetMatch(String playId, String code, String odds) {
		List<BetMatch> codes = null;
		BetMatch betMatch = new BetMatch();
		
		betMatch.setPlayId(playId);
		if(StringUtils.isNotBlank(code) 
				&& StringUtils.isNotBlank(odds)
				&& PlayType.JCSJBGJ.getShortPlayStr().equals(playId)) {
			codes = new ArrayList<BetMatch>();
			String[] codeOptions = code.split(",");
			String[] oddsOptions = odds.split(",");
			if(codeOptions.length == oddsOptions.length) {
				betMatch.setCode(code);
				betMatch.setOdds(odds);
				int year = Calendar.getInstance().get(Calendar.YEAR);
				betMatch.setMatchId(Long.valueOf(year));
				codes.add(betMatch);
			}
		}
		return codes;
	}
}