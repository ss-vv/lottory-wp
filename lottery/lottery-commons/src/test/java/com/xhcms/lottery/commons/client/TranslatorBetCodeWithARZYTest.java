package com.xhcms.lottery.commons.client;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.LotteryPlatformId;

/**
 * 主要功能：
 * 	1.大V彩投注格式到“安瑞智赢出票平台”投注格式转换的测试
 * @author lei.li@davcai.com
 */
public class TranslatorBetCodeWithARZYTest {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void testTranslateTicketCode() throws TranslateException{
		
		/**普通玩法**/
		String code = "(66081)70203-(66082)70211";
		Ticket ticket = new Ticket();
		ticket.setPlayId(Constants.PLAY_01_ZC_2);
		ticket.setCode(code);
		ticket.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		String toPlatformCode = Translator.translateTicketCodeToPFormat(ticket);
		
		showLog(ticket, toPlatformCode);
		String expected = "66081~[让球胜]~0/66082~[让球平]~0";
		assertEquals(expected, toPlatformCode);
		
		/**混合玩法**/
		code = "(66081)40013:SPF-(66082)40021:BRQSPF";
		ticket.setCode(code);
		String playId = Constants.PLAY_05_ZC_2;
		ticket.setPlayId(playId);
		toPlatformCode = Translator.translateTicketCodeToPFormat(ticket);
		
		showLog(ticket, toPlatformCode);
		expected = "66081~[让球胜]~0/66082~[平]~0";
		assertEquals(expected, toPlatformCode);
		
	}
	
	private void showLog(Ticket ticket, String toPlatformCode) {
		System.out.println("platformId=" + ticket.getLotteryPlatformId());
		System.out.println("code=" + ticket.getCode());
		System.out.println("platformCode=" + toPlatformCode);
		System.out.println("------------------------------->");
	}
}