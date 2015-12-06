package com.xhcms.lottery.commons.client.translate;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.lang.AssertUtils;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.MixPlayType;
import com.xhcms.lottery.lang.PlayType;

/**
 * 一次比赛的投注内容。
 * @author Yang Bo
 *
 */
public class ZMMatchBetContent extends PlatformBetContent {
	
	public static final Map<String, String>[] resultMaps;
	
	static {
		resultMaps = MatchResultMap.getZmResultMaps();
	}
	
	@Override
	public Map<String, String>[] getResultMaps() {
		return resultMaps;
	}
	
	private ZMMatchBetContent(){}
	
	public static PlatformBetContent parseMatchBetContentInLaiCaiFormat(String matchBet, String lcPlayId){
		AssertUtils.assertNotBlank(matchBet, "matchBet");
		AssertUtils.assertNotBlank(lcPlayId, "lcPlayId");
		
		PlayType lcPlayType = PlayType.valueOfLcPlayId(lcPlayId);
		ZMMatchBetContent matchBetObj = new ZMMatchBetContent();
		if (matchBet.length()<=4&&!(LotteryId.BJDC.equals(lcPlayType.getLotteryId())||LotteryId.BDSF.equals(lcPlayType.getLotteryId()))){
			
			throw new IllegalArgumentException("matchBet length < 4 : " + matchBet);
		}else if(matchBet.length()<4&&LotteryId.BJDC.equals(lcPlayType.getLotteryId())){
			
			throw new IllegalArgumentException("matchBet length < 4 : " + matchBet);
		}
		// 是否 lcPlayId 为混合彩，是就解析出各自比赛对应的玩法
		PlayType playType = PlayType.valueOfLcPlayId(lcPlayId);
		String playId = lcPlayId;
		String bet = matchBet;
		if (playType.isHH()){
			String[] sa = matchBet.split(":");
			bet = sa[0];
			playId = MixPlayType.valueOfPlayName(sa[1]).getPlayId();
		}
		matchBetObj.setLcPlayId(playId);
		matchBetObj.setTicketPlayId(lcPlayId);
		//-----------判断玩法 北京单场场次格式 
		
		
		if(LotteryId.BJDC.equals(lcPlayType.getLotteryId())||LotteryId.BDSF.equals(lcPlayType.getLotteryId())){
			
			//matchBetObj.setDayInWeek(bet.substring(0, 1));
			matchBetObj.setSequence(String.valueOf(Integer.parseInt(bet.substring(0,3))));
			matchBetObj.setExpectedResult(bet.substring(3));
			
		}else{
			
			matchBetObj.setDayInWeek(bet.substring(0, 1));
			matchBetObj.setSequence(bet.substring(1,4));
			matchBetObj.setExpectedResult(bet.substring(4));
			
		}
		 
	
		return matchBetObj;
	}
	
	public String toPlatformMatchBetContent() throws TranslateException{
		String zmExpectedResult = translatePlatformExpectedResult();
		String code = "";
		//判断玩法----bjdc  lcPlayId
	      
		PlayType lcPlayType = PlayType.valueOfLcPlayId(lcPlayId);
		if(LotteryId.BJDC.equals(lcPlayType.getLotteryId())||LotteryId.BDSF.equals(lcPlayType.getLotteryId())){
			
			code = String.format("%s:[%s]",sequence, zmExpectedResult);
		}else{
			
			code = String.format("%s-%s:[%s]", dayInWeek, sequence, zmExpectedResult);
		}
		
		// 从票的玩法id可以知道是否为混合过关玩法
		PlayType pt = PlayType.valueOfLcPlayId(this.ticketPlayId);
		if (pt.isHH()){
			// 从比赛的玩法可以推出“缩写的混合过关”串。
			// 如：SF@1-001:[主胜,客胜]/RFSF@1-002:[主胜]/FC@1-003:[胜1-5]/DXF@1-004:[大,小]
			MixPlayType mpt = MixPlayType.valueOfPlayType(PlayType.valueOfLcPlayId(this.lcPlayId));
			code = String.format("%s@%s-%s:[%s]", mpt.getName(), dayInWeek, sequence, zmExpectedResult);
		}
		return code;
	}

	@Override
	public String translatePlatformExpectedResult() throws TranslateException {
		// lcPlayId: 01_ZC_1
		String[] parts = lcPlayId.split("_");
		int playType = Integer.parseInt(parts[0], 10);
		List<String> options = splitOptionsByPlayType(playType);
		return optionsToResult(options, playType);
	}

	/**
	 * 从大V彩选项到中民选项的转换。
	 * @param option 选项 大V彩的选项格式，如：33
	 * @param playType PlayType 的下标
	 * @return 中民选项，如：胜-平
	 * @throws TranslateException
	 */
	protected String lcOptionToPlatformOption(String option, int playType) throws TranslateException{
		Map<String, String> resultMap = resultMaps[playType];
		if (resultMap==null){
			logger.error("Unknown playType: " + playType);
			throw new TranslateException("Unknown playType: " + playType);
		}
		String zmOption = resultMap.get(option);
		if (StringUtils.isBlank(zmOption)){
			throw new TranslateException("lcOption '" + option + "' can't map to zmOption. playType="+playType);
		}
		return zmOption;
	}
	
	/**
	 * 按照不同的玩法拆分出中民新接口形式的选项串，按相同顺序返回对应的中民新接口格式内容。
	 * @return 形如：胜-胜,胜-平
	 */
	@Override
	public List<String> splitOptionsInPlatformFormat() throws TranslateException{
		List<String> zmOptions = new LinkedList<String>();
		String[] parts = lcPlayId.split("_");
		int playType = Integer.parseInt(parts[0], 10);
		for (String lcOption : splitOptionsByPlayType(playType) ) {
			zmOptions.add(lcOptionToPlatformOption(lcOption, playType));
		}
		return zmOptions;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public String getLcPlayId() {
		return lcPlayId;
	}

	public void setLcPlayId(String lcPlayId) {
		this.lcPlayId = lcPlayId;
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

	public String getTicketPlayId() {
		return ticketPlayId;
	}

	public void setTicketPlayId(String ticketPlayId) {
		this.ticketPlayId = ticketPlayId;
	}
	
	/**
	 * 如果是混合过关方式，返回的是本场比赛的玩法，否则返回的是票的玩法。
	 */
	public PlayType getMatchOrTicketPlayType(){
		String playId = this.lcPlayId;
		if(StringUtils.isBlank(playId)){
			playId = this.ticketPlayId;
		}
		return PlayType.valueOfLcPlayId(playId);
	}
}
