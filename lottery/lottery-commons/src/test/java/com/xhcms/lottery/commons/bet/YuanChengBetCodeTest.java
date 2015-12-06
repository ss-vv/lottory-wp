package com.xhcms.lottery.commons.bet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.commons.client.translate.BetContent;
import com.xhcms.lottery.commons.client.translate.JCBetContent;
import com.xhcms.lottery.commons.client.translate.PlatformBetContent;
import com.xhcms.lottery.commons.client.translate.YuanChengMatchBetContent;
import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.utils.internal.PlatformBetCodeCreator;
import com.xhcms.lottery.commons.utils.internal.impl.ARZYBetCodeCreatorImpl;
import com.xhcms.lottery.lang.LotteryPlatformId;
import com.xhcms.lottery.lang.LotteryPlatformIdEnum;
import com.xhcms.lottery.lang.PlayType;

/**
 * 远程投注
 * @author haoxiang.jiang@davcai.com
 * @date 2015年6月16日 下午6:45:42
 */
public class YuanChengBetCodeTest {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * @throws TranslateException 
	 * 
	 */
	@Test
	public void testYuanChengCode() throws TranslateException {
		//足彩胜平负	51
		//比分	52
		//总进球	53
		//半全场	54
		//足彩让球胜平负	56
		Map<String, String> r = testYuanCheng("20033:SPF-20043:BRQSPF-2005109903:BF","05_ZC_2");
		System.out.println(r.get(LotteryPlatformId.YUAN_CHENG_CHU_PIAO));
		Assert.assertEquals(r.get(LotteryPlatformId.YUAN_CHENG_CHU_PIAO), "7003563^7004513^700552109903^");
		
		Assert.assertEquals(3, testCalMatchCount("19", r.get(LotteryPlatformId.YUAN_CHENG_CHU_PIAO)));
		r = testYuanCheng("200807:JQS-200900:BQC","05_ZC_2");
		Assert.assertEquals(r.get(LotteryPlatformId.YUAN_CHENG_CHU_PIAO), "53|2|008|0,7|54|2|009|00");
		Assert.assertEquals(2, testCalMatchCount("19", r.get(LotteryPlatformId.YUAN_CHENG_CHU_PIAO)));
		r = testYuanCheng("20033-200431","01_ZC_2");
		Assert.assertEquals(r.get(LotteryPlatformId.YUAN_CHENG_CHU_PIAO), "2|003|3|2|004|3,1");
		Assert.assertEquals(2, testCalMatchCount("12", r.get(LotteryPlatformId.YUAN_CHENG_CHU_PIAO)));
		r = testYuanCheng("20053199-2006303390","02_ZC_2");
		Assert.assertEquals(r.get(LotteryPlatformId.YUAN_CHENG_CHU_PIAO), "2|005|31,99|2|006|30,33,90");
		r = testYuanCheng("200807-20097","03_ZC_2");
		Assert.assertEquals(r.get(LotteryPlatformId.YUAN_CHENG_CHU_PIAO), "2|008|0,7|2|009|7");
		r = testYuanCheng("20081103-20091133","04_ZC_2");
		Assert.assertEquals(r.get(LotteryPlatformId.YUAN_CHENG_CHU_PIAO), "2|008|11,03|2|009|11,33");
		
		//篮球 胜负 让球胜负
		//Map<String, String> l = testYuanCheng("43052-43061-43072-43082","06_LC_2");
		//胜分差
		Map<String, String> l = testYuanCheng("33010102-33020203-43011112","08_LC_1");
		Assert.assertEquals(l.get(LotteryPlatformId.YUAN_CHENG_CHU_PIAO), "7003563^7004513^700552109903^");
		System.out.println(l.get(LotteryPlatformId.YUAN_CHENG_CHU_PIAO));
		
	}
	private Map<String, String> testYuanCheng(String betContent,String lcPlayId) throws TranslateException{
		Map<String, String> result = new HashMap<String, String>();
		String[] matches = betContent.split("-");
		BetContent yuanchengResult = new JCBetContent();
		for (int i=0; i<matches.length; i++) {
			String matchBet = matches[i];
			PlatformBetContent yuanChengMatchBetContent = 
					 YuanChengMatchBetContent.parseMatchBetContentInLaiCaiFormat(matchBet, lcPlayId);
			yuanchengResult.getMatchBetContents().add(yuanChengMatchBetContent);
		}
		result.put(LotteryPlatformId.YUAN_CHENG_CHU_PIAO, yuanchengResult.toPlatformBetContent(
				LotteryPlatformIdEnum.YUAN_CHENG_CHU_PIAO).toString());
		return result;
	}
	
	public int testCalMatchCount(String gameType,String content){
		if("19".equals(gameType)){
			return content.split("\\|").length/4;
		} else {
			return content.split("\\|").length/3;
		}
	}
}
