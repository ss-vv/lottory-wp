package com.xhcms.lottery.commons.client.translate;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;

import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.lang.AssertUtils;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.MixPlayType;
import com.xhcms.lottery.lang.PlayType;

/**
 * 对应“安瑞智赢”接口一次比赛的投注内容。
 */
public class ARZYMatchBetContent extends PlatformBetContent {

	public static final Map<String, String>[] resultMaps;
	
	static {
		resultMaps = MatchResultMap.getArzyResultMaps();
	}

	@Override
	public Map<String, String>[] getResultMaps() {
		return resultMaps;
	}
	
	private ARZYMatchBetContent() {
	}

	private static String findJcOfficialMatchId(String bet) {
		Matcher m = pattern.matcher(bet);
		if(m.find()) {
			String jcOfficaalMatchId = m.group(1);
			return jcOfficaalMatchId;
		}
		return "";
	}
	
	public static PlatformBetContent parseMatchBetContentInLaiCaiFormat(
			String matchBet, String lcPlayId) {
		AssertUtils.assertNotBlank(matchBet, "matchBet");
		AssertUtils.assertNotBlank(lcPlayId, "lcPlayId");

		PlayType lcPlayType = PlayType.valueOfLcPlayId(lcPlayId);
		ARZYMatchBetContent matchBetObj = new ARZYMatchBetContent();
		// 是否 lcPlayId 为混合彩，是就解析出各自比赛对应的玩法
		PlayType playType = PlayType.valueOfLcPlayId(lcPlayId);
		String playId = lcPlayId;
		String bet = matchBet;
		if (playType.isHH()) {
			String[] sa = matchBet.split(":");
			bet = sa[0];
			playId = MixPlayType.valueOfPlayName(sa[1]).getPlayId();
		}
		matchBetObj.setLcPlayId(playId);
		matchBetObj.setTicketPlayId(lcPlayId);
		if (LotteryId.BJDC.equals(lcPlayType.getLotteryId())
				|| LotteryId.BDSF.equals(lcPlayType.getLotteryId())) {
			
		} else {
//			matchBetObj.setDayInWeek(bet.substring(0, 1));
//			matchBetObj.setSequence(bet.substring(1,4));
//			matchBetObj.setExpectedResult(bet.substring(4));
			
			String jcOfficialMatchId = findJcOfficialMatchId(bet);
			matchBetObj.setJcOfficialMatchId(jcOfficialMatchId);
			bet = bet.substring(jcOfficialMatchId.length() + 2);
			matchBetObj.setExpectedResult(bet.substring(4));
		}
		return matchBetObj;
	}

	@Override
	public String toPlatformMatchBetContent() throws TranslateException {
		String platformExpectedResult = translatePlatformExpectedResult();
		String code = "";
		if(StringUtils.isBlank(getJcOfficialMatchId())) {
			return code;
		}
		
		PlayType lcPlayType = PlayType.valueOfLcPlayId(lcPlayId);
		if (LotteryId.BJDC.equals(lcPlayType.getLotteryId())
				|| LotteryId.BDSF.equals(lcPlayType.getLotteryId())) {
			
		} else {
			code = String.format("%s~[%s]~0", getJcOfficialMatchId(),
					platformExpectedResult);
		}

		// 从票的玩法id可以知道是否为混合过关玩法
		PlayType pt = PlayType.valueOfLcPlayId(this.ticketPlayId);
		if (pt.isHH()) {
			code = String.format("%s~[%s]~0", getJcOfficialMatchId(),
					platformExpectedResult);
		}
		return code;
	}

	/**
	 * 按照不同的玩法拆分出安瑞智赢新接口形式的选项串，按相同顺序返回对应的安瑞智赢新接口格式内容。
	 * 
	 * @return 形如：胜-胜,胜-平
	 */
	@Override
	public List<String> splitOptionsInPlatformFormat() throws TranslateException {
		List<String> zmOptions = new LinkedList<String>();
		String[] parts = lcPlayId.split("_");
		int playType = Integer.parseInt(parts[0], 10);
		for (String lcOption : splitOptionsByPlayType(playType)) {
			zmOptions.add(lcOptionToPlatformOption(lcOption, playType));
		}
		return zmOptions;
	}

	/**
	 * 如果是混合过关方式，返回的是本场比赛的玩法，否则返回的是票的玩法。
	 */
	@Override
	public PlayType getMatchOrTicketPlayType() {
		String playId = this.lcPlayId;
		if (StringUtils.isBlank(playId)) {
			playId = this.ticketPlayId;
		}
		return PlayType.valueOfLcPlayId(playId);
	}
	
	@Override
	public String translatePlatformExpectedResult() throws TranslateException {
		// lcPlayId: 01_ZC_1
		String[] parts = lcPlayId.split("_");
		int playType = Integer.parseInt(parts[0], 10);
		List<String> options = splitOptionsByPlayType(playType);
		return optionsToResult(options, playType);
	}
}
