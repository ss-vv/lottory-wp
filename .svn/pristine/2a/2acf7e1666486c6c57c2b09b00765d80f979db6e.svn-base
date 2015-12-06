package com.xhcms.lottery.commons.client.translate;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.lang.PlayType;

/**
 * 平台投注内容抽象
 * 
 * @author lei.li@davcai.com
 */
public abstract class PlatformBetContent {

	protected static Logger logger = LoggerFactory
			.getLogger(ARZYMatchBetContent.class);

	public static final int MAX_PLAY_TYPE = 100;// 80+1; // 因为添加了80不让球胜平负。
	protected String lcPlayId; // 比赛的玩法
	protected String ticketPlayId; // 投注内容所属票的玩法
	protected String dayInWeek; // 星期, 1-7
	protected String sequence; // 序号, 000-999
	protected String expectedResult; // 选择的赛果
	protected String seed = "0"; // 是否包含胆码
	private String jcOfficialMatchId = "";
	protected static final Pattern pattern = Pattern.compile("(\\d+{5,30})");
	
	public abstract Map<String, String>[] getResultMaps();
	
	public static PlatformBetContent parseMatchBetContentInLaiCaiFormat(
			String matchBet, String lcPlayId) {
		return null;
	}

	/**
	 * 转换为对应平台的投注内容。
	 * 
	 * @throws TranslateException
	 */
	public abstract String toPlatformMatchBetContent()
			throws TranslateException;

	public abstract List<String> splitOptionsInPlatformFormat()
			throws TranslateException;

	public abstract PlayType getMatchOrTicketPlayType();

	/**
	 * 从大V彩选项到平台选项的转换。
	 * 
	 * @param option
	 *            选项 大V彩的选项格式，如：33
	 * @param playType
	 *            PlayType 的下标
	 * @return 平台选项，如：胜-平
	 * @throws TranslateException
	 */
	protected String lcOptionToPlatformOption(String option, int playType)
			throws TranslateException {
		Map<String, String> resultMap = getResultMaps()[playType];
		if (resultMap == null) {
			logger.error("Unknown playType: " + playType);
			throw new TranslateException("Unknown playType: " + playType);
		}
		String zmOption = resultMap.get(option);
		if (StringUtils.isBlank(zmOption)) {
			throw new TranslateException("lcOption '" + option
					+ "' can't map to zmOption. playType=" + playType);
		}
		return zmOption;
	}

	public abstract String translatePlatformExpectedResult()
			throws TranslateException;

	protected List<String> splitOptionsByPlayType(int playType)
			throws TranslateException {
		List<String> options = new LinkedList<String>();
		switch (playType) {
		case 1:
		case 80:
		case 3:
		case 6:
		case 7:
		case 9:
		case 91: // 北单胜负平
		case 92: // 北单进球数
		case 96: // 北单胜负
			// 单字符投注选项，如:3,1,0
			for (int i = 0; i < expectedResult.length(); i++) {
				options.add(expectedResult.substring(i, i + 1));
			}
			return options;
		case 2:
		case 4:
		case 8:
		case 93: // 北单上下双
		case 94: // 北单比分
		case 95: // 北单半全场
			// 双字符投注选项，如：23,10
			for (int i = 0; i < expectedResult.length(); i += 2) {
				options.add(expectedResult.substring(i, i + 2));
			}
			return options;
		default:
			logger.error("Unknown playId: " + lcPlayId);
			throw new TranslateException("Unknown playId: " + lcPlayId);
		}
	}

	protected String optionsToResult(List<String> options, int playType)
			throws TranslateException {
		StringBuilder builder = new StringBuilder();
		for (String option : options) {
			appendResult(option, playType, builder);
		}
		return builder.toString();
	}

	protected void appendResult(String option, int playType,
			StringBuilder builder) throws TranslateException {
		appendComma(builder);
		builder.append(lcOptionToPlatformOption(option, playType));
	}

	protected void appendComma(StringBuilder builder) {
		if (builder.length() > 0) {
			builder.append(",");
		}
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getLcPlayId() {
		return lcPlayId;
	}

	public void setLcPlayId(String lcPlayId) {
		this.lcPlayId = lcPlayId;
	}

	public String getTicketPlayId() {
		return ticketPlayId;
	}

	public void setTicketPlayId(String ticketPlayId) {
		this.ticketPlayId = ticketPlayId;
	}

	public String getDayInWeek() {
		return dayInWeek;
	}

	public void setDayInWeek(String dayInWeek) {
		this.dayInWeek = dayInWeek;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getExpectedResult() {
		return expectedResult;
	}

	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}

	public String getJcOfficialMatchId() {
		return jcOfficialMatchId;
	}

	public void setJcOfficialMatchId(String jcOfficialMatchId) {
		this.jcOfficialMatchId = jcOfficialMatchId;
	}
}