package com.xhcms.lottery.commons.client.translate;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.AssertUtils;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.LotteryPlatformId;
import com.xhcms.lottery.lang.LotteryPlatformIdEnum;
import com.xhcms.lottery.lang.PlayType;

/**
 * 投注内容解析、转换工厂类。
 * 
 * @author 陈岩，Yang Bo，lei.li@davcai.com
 *
 */
public class BetContentFactory {

	private static Logger logger = LoggerFactory.getLogger(BetContentFactory.class);
	
	/**
	 * 将投注内容转换成多平台的投注格式
	 * @param ticket	大V彩平台定义的一张票对象
	 * @return
	 */
	public static Map<String,String> parseBetToPlatformBetCode(Ticket ticket) {
		logger.debug("将投注内容转换为多平台的投注格式:betContent={}, lcPlayId={}", 
				new Object[]{ticket.getCode(), ticket.getPlayId()});
		String betContent = ticket.getCode();
		String lcPlayId = ticket.getPlayId();
		AssertUtils.assertNotBlank(betContent, "betContent");
		AssertUtils.assertNotBlank(lcPlayId, "lcPlayId");
		
		BetContent arzyResult = new JCBetContent();
		BetContent zmResult = new JCBetContent();
		BetContent yuanchengResult = new JCBetContent();
		Map<String, String> result = new HashMap<String, String>();
		
		if (!PlayType.valueOfLcPlayId(lcPlayId).isDigitalLottery()) {
			String[] matches = betContent.split("-");
			for (int i=0; i<matches.length; i++) {
				String matchBet = matches[i];
				
				//转换安瑞智赢
				if(null != ticket.getMatches() && ticket.getMatches().size() > 0) {
					Long jcOfficialMatchId = ticket.getMatches().get(i).getJcOfficialMatchId();
					if(null != jcOfficialMatchId && jcOfficialMatchId > 0) {
						String bet = "(" + jcOfficialMatchId + ")" + matchBet;
						PlatformBetContent arzyMatchBetContent = ARZYMatchBetContent
								.parseMatchBetContentInLaiCaiFormat(bet, lcPlayId);
						arzyResult.matchBetContents.add(arzyMatchBetContent);
					} else {
						logger.warn("投注内容:{}, 找不到竞彩官网ID", matchBet);
					}
				}
				//转换中民
				PlatformBetContent zmMatchBetContent = ZMMatchBetContent
						.parseMatchBetContentInLaiCaiFormat(matchBet, lcPlayId);
				zmResult.matchBetContents.add(zmMatchBetContent);

				//远程出票目前仅支持足球
				if(LotteryId.JCZQ.toString().equals(PlayType.valueOfLcPlayId(lcPlayId).getLotteryId().toString())){
					//转换远程出票
					PlatformBetContent yuanChengMatchBetContent = 
							 YuanChengMatchBetContent.parseMatchBetContentInLaiCaiFormat(matchBet, lcPlayId);
					yuanchengResult.matchBetContents.add(yuanChengMatchBetContent);
				}
			}
		}
		try {
			result.put(LotteryPlatformId.ZUN_AO, zmResult.toPlatformBetContent().toString());
			result.put(LotteryPlatformId.AN_RUI_ZHI_YING, arzyResult.toPlatformBetContent().toString());
			//远程出票目前仅支持足球
			if(LotteryId.JCZQ.toString().equals(PlayType.valueOfLcPlayId(lcPlayId).getLotteryId().toString())){
				result.put(LotteryPlatformId.YUAN_CHENG_CHU_PIAO, yuanchengResult.toPlatformBetContent(
					LotteryPlatformIdEnum.YUAN_CHENG_CHU_PIAO).toString());
			}
		} catch (TranslateException e) {
			logger.info("将投注内容转换成多平台的投注格式异常:{}", e);
		}
		
		return result;
	}

	/**
	 * 解析大V彩内部格式的投注内容。
	 * 
	 * @param betContent
	 *            形如：30011-30023-30031-30040-30053-30060 <br/>
	 *            对于混合过关方式，格式是：30011:spf-30023:bqc
	 * @param lcPlayId
	 *            大V彩格式的玩法id
	 * @return 投注内容对象
	 */
	public static BetContent parseBetContentInLaiCaiFormat(String betContent,
			String lcPlayId, String lotteryPlatformId) {
		BetContent result = null;
		AssertUtils.assertNotBlank(betContent, "betContent");
		AssertUtils.assertNotBlank(lcPlayId, "lcPlayId");
		if (PlayType.valueOfLcPlayId(lcPlayId).isDigitalLottery()) {
			result = new HFLotteryBetContent(betContent);
		} else {
			String[] matches = betContent.split("-");
			result = new JCBetContent();
			for (String matchBet : matches) {
				if (LotteryPlatformId.AN_RUI_ZHI_YING.equals(lotteryPlatformId)) {
					PlatformBetContent matchBetContent = ARZYMatchBetContent
							.parseMatchBetContentInLaiCaiFormat(matchBet,
									lcPlayId);
					result.matchBetContents.add(matchBetContent);
				} else {
					PlatformBetContent matchBetContent = ZMMatchBetContent
							.parseMatchBetContentInLaiCaiFormat(matchBet,
									lcPlayId);
					result.matchBetContents.add(matchBetContent);
				}
			}
		}
		return result;
	}
}