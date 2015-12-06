package com.xhcms.lottery.commons.client;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
 
import com.xhcms.lottery.commons.client.translate.Odds;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PlayType;

public class TranslatorTest {

	@Test
	public void testTranslateTicketCode() throws TranslateException{
		String code = "70203-70211-70220-70231-70250-70261-70270-70290";
		Ticket ticket = new Ticket();
		ticket.setPlayId(Constants.PLAY_01_ZC_2);
		ticket.setCode(code);
		String zmCode = Translator.translateTicketCodeToPFormat(ticket);
		System.out.println("code: " + code);
		System.out.println("zmCode: " + zmCode);
		assertEquals("7-020:[胜]/7-021:[平]/7-022:[负]/7-023:[平]/7-025:[负]/7-026:[平]/7-027:[负]/7-029:[负]", zmCode);

		code = "73021-73052";
		ticket.setPlayId(Constants.PLAY_09_LC_2);
		ticket.setCode(code);
		zmCode = Translator.translateTicketCodeToPFormat(ticket);
		System.out.println("code: " + code);
		System.out.println("zmCode: " + zmCode);
		assertEquals("7-302:[大]/7-305:[小]", zmCode);
		
		code = "630101-630211";
		ticket.setPlayId(Constants.PLAY_08_LC_2);
		ticket.setCode(code);
		zmCode = Translator.translateTicketCodeToPFormat(ticket);
		System.out.println("code: " + code);
		System.out.println("zmCode: " + zmCode);
		assertEquals("6-301:[胜1-5]/6-302:[负1-5]", zmCode);
		
		code = "601913-602010";
		ticket.setPlayId(Constants.PLAY_04_ZC_2);
		ticket.setCode(code);
		zmCode = Translator.translateTicketCodeToPFormat(ticket);
		System.out.println("code: " + code);
		System.out.println("zmCode: " + zmCode);
		assertEquals("6-019:[平-胜]/6-020:[平-负]", zmCode);
		
		code = "63011-63031-63041-63051-63062";
		ticket.setPlayId(Constants.PLAY_06_LC_2);
		ticket.setCode(code);
		zmCode = Translator.translateTicketCodeToPFormat(ticket);
		System.out.println("code: " + code);
		System.out.println("zmCode: " + zmCode);
		assertEquals("6-301:[主胜]/6-303:[主胜]/6-304:[主胜]/6-305:[主胜]/6-306:[客胜]", zmCode);
		
		code = "600412-600531-600612";
		ticket.setPlayId(Constants.PLAY_02_ZC_2);
		ticket.setCode(code);
		zmCode = Translator.translateTicketCodeToPFormat(ticket);
		System.out.println("code: " + code);
		System.out.println("zmCode: " + zmCode);
		assertEquals("6-004:[1:2]/6-005:[3:1]/6-006:[1:2]", zmCode);
		
		code = "60050-60087";
		ticket.setPlayId(Constants.PLAY_03_ZC_2);
		ticket.setCode(code);
		zmCode = Translator.translateTicketCodeToPFormat(ticket);
		System.out.println("code: " + code);
		System.out.println("zmCode: " + zmCode);
		assertEquals("6-005:[0]/6-008:[7+]", zmCode);
		
		code = "23022-23031-23041-23052-23062-23072";
		ticket.setPlayId(Constants.PLAY_07_LC_2);
		ticket.setCode(code);
		zmCode = Translator.translateTicketCodeToPFormat(ticket);
		System.out.println("code: " + code);
		System.out.println("zmCode: " + zmCode);
		assertEquals("2-302:[客胜]/2-303:[主胜]/2-304:[主胜]/2-305:[客胜]/2-306:[客胜]/2-307:[客胜]", zmCode);
	
		//高频彩江西11选5
		code="(01)02,03,04";
		ticket.setPlayId(Constants.PLAY_14_J5_R4);
		ticket.setCode(code);
		zmCode=Translator.translateTicketCodeToPFormat(ticket);
		System.out.println("code: " + code);
		System.out.println("zmCode: " + zmCode);
		assertEquals("(01)02,03,04", zmCode);
		
		code="3,3,3,3,3,3,3,3,3,-,-,-,-,-";
		ticket.setPlayId(Constants.PLAY_25_ZC_R9);
		ticket.setCode(code);
		zmCode=Translator.translateTicketCodeToPFormat(ticket);
		System.out.println("code: " + code);
		System.out.println("zmCode: " + zmCode);
	}
	
//	@Test
//	public void testCTZC() throws TranslateException{
//		String code="3,3,3,3,3,3,3,3,3,-,-,-,-,-";
//		Ticket ticket=new Ticket();
//		ticket.setPlayId(Constants.PLAY_25_ZC_R9);
//		ticket.setCode(code);
//		String zmCode = Translator.translateTicketCodeToPFormat(ticket);
//		System.out.println("code: " + code);
//		System.out.println("zmCode: " + zmCode);
//	}
	
	@Test
	public void testSSQPlayIdToZMLotteryId() throws TranslateException {		
		String[] lcPlayTypeIds = {
				Constants.PLAY_SSQ_DS,    // 双色球单式
				Constants.PLAY_SSQ_FS,    // 双色球复式
				Constants.PLAY_SSQ_DT,    // 双色球胆拖
		};
		
		String[] cqSSC_ZMBetTypes = {
				Constants.ZM_BETTYPE_SSQ_DS, // 双色球单式
				Constants.ZM_BETTYPE_SSQ_FS, // 双色球复式
				Constants.ZM_BETTYPE_SSQ_DT, // 双色球胆拖
		};

		String zmLotteryId, zmPlayTypeId;
		for (int i = 0; i < lcPlayTypeIds.length; i++) {
			zmLotteryId = Translator.lcPlayIdToZMLotteryId(lcPlayTypeIds[i]);
			assertEquals(Constants.ZM_LOTTERYID_SSQ, zmLotteryId);
			
			zmPlayTypeId = Translator.lcHfPlayIdToZMBetType(lcPlayTypeIds[i], "");
			assertEquals(cqSSC_ZMBetTypes[i], zmPlayTypeId);

			System.out.println("lotteryId:" + zmLotteryId + " -- PlayType(大V彩):" + lcPlayTypeIds[i] + "\t-- ZM_BETTYPE(中民):" + zmPlayTypeId);
		}
	}
	
	@Test
	public void testCQSSCPlayIdToZMLotteryId() throws TranslateException {		
		String[] lcPlayTypeIds = {
				Constants.PLAY_CQSS_DXDS,     // 重庆时时彩 - 大小单双
				Constants.PLAY_CQSS_1X_DS,    // 重庆时时彩 - 一星直选单式
				Constants.PLAY_CQSS_2X_DS,    // 重庆时时彩 - 二星直选单式
				Constants.PLAY_CQSS_2X_FS,    // 重庆时时彩 - 二星直选复式
				Constants.PLAY_CQSS_2X_ZH,    // 重庆时时彩 - 二星组合
				Constants.PLAY_CQSS_2X_HZ,    // 重庆时时彩 - 二星直选和值
				Constants.PLAY_CQSS_2X_ZX_DS, // 重庆时时彩 - 二星组选单式
				Constants.PLAY_CQSS_2X_ZX_ZH, // 重庆时时彩 - 二星组选组合
				Constants.PLAY_CQSS_2X_ZX_FZ, // 重庆时时彩 - 二星组选分组
				Constants.PLAY_CQSS_2X_ZX_HZ, // 重庆时时彩 - 二星组选和值
				Constants.PLAY_CQSS_2X_ZX_BD, // 重庆时时彩 - 二星组选包胆
				Constants.PLAY_CQSS_3X_DS,    // 重庆时时彩 - 三星直选单式
				Constants.PLAY_CQSS_3X_FS,    // 重庆时时彩 - 三星直选复式
				Constants.PLAY_CQSS_3X_ZH,    // 重庆时时彩 - 三星组合
				Constants.PLAY_CQSS_3X_ZH_FS, // 重庆时时彩 - 三星组合复式
				Constants.PLAY_CQSS_3X_HZ,    // 重庆时时彩 - 三星直选和值
				Constants.PLAY_CQSS_3X_Z3_DS, // 重庆时时彩 - 三星组三单式
				Constants.PLAY_CQSS_3X_Z3_FS, // 重庆时时彩 - 三星组三复式
				Constants.PLAY_CQSS_3X_Z6_DS, // 重庆时时彩 - 三星组六单式
				Constants.PLAY_CQSS_3X_Z6_FS, // 重庆时时彩 - 三星组六复式
				Constants.PLAY_CQSS_3X_ZX_HZ, // 重庆时时彩 - 三星组选合值
				Constants.PLAY_CQSS_3X_ZX_BD, // 重庆时时彩 - 三星组选包胆
				Constants.PLAY_CQSS_5X_TX,    // 重庆时时彩 - 五星通选
				Constants.PLAY_CQSS_5X_DS,    // 重庆时时彩 - 五星直选单式
				Constants.PLAY_CQSS_5X_FS,    // 重庆时时彩 - 五星直选复式
				Constants.PLAY_CQSS_5X_ZH,    // 重庆时时彩 - 五星组合
		};
		
		String[] cqSSC_ZMBetTypes = {
				Constants.ZM_BETTYPE_CQSS_DXDS,     // 重庆时时彩 - 大小单双
				Constants.ZM_BETTYPE_CQSS_1X_DS,    // 重庆时时彩 - 一星直选单式
				Constants.ZM_BETTYPE_CQSS_2X_DS,    // 重庆时时彩 - 二星直选单式
				Constants.ZM_BETTYPE_CQSS_2X_FS,    // 重庆时时彩 - 二星直选复式
				Constants.ZM_BETTYPE_CQSS_2X_ZH,    // 重庆时时彩 - 二星组合
				Constants.ZM_BETTYPE_CQSS_2X_HZ,    // 重庆时时彩 - 二星直选和值
				Constants.ZM_BETTYPE_CQSS_2X_ZX_DS, // 重庆时时彩 - 二星组选单式
				Constants.ZM_BETTYPE_CQSS_2X_ZX_ZH, // 重庆时时彩 - 二星组选组合
				Constants.ZM_BETTYPE_CQSS_2X_ZX_FZ, // 重庆时时彩 - 二星组选分组
				Constants.ZM_BETTYPE_CQSS_2X_ZX_HZ, // 重庆时时彩 - 二星组选和值
				Constants.ZM_BETTYPE_CQSS_2X_ZX_BD, // 重庆时时彩 - 二星组选包胆
				Constants.ZM_BETTYPE_CQSS_3X_DS,    // 重庆时时彩 - 三星直选单式
				Constants.ZM_BETTYPE_CQSS_3X_FS,    // 重庆时时彩 - 三星直选复式
				Constants.ZM_BETTYPE_CQSS_3X_ZH,    // 重庆时时彩 - 三星组合
				Constants.ZM_BETTYPE_CQSS_3X_ZH_FS, // 重庆时时彩 - 三星组合复式
				Constants.ZM_BETTYPE_CQSS_3X_HZ,    // 重庆时时彩 - 三星直选和值
				Constants.ZM_BETTYPE_CQSS_3X_Z3_DS, // 重庆时时彩 - 三星组三单式
				Constants.ZM_BETTYPE_CQSS_3X_Z3_FS, // 重庆时时彩 - 三星组三复式
				Constants.ZM_BETTYPE_CQSS_3X_Z6_DS, // 重庆时时彩 - 三星组六单式
				Constants.ZM_BETTYPE_CQSS_3X_Z6_FS, // 重庆时时彩 - 三星组六复式
				Constants.ZM_BETTYPE_CQSS_3X_ZX_HZ, // 重庆时时彩 - 三星组选合值
				Constants.ZM_BETTYPE_CQSS_3X_ZX_BD, // 重庆时时彩 - 三星组选包胆
				Constants.ZM_BETTYPE_CQSS_5X_TX,    // 重庆时时彩 - 五星通选
				Constants.ZM_BETTYPE_CQSS_5X_DS,    // 重庆时时彩 - 五星直选单式
				Constants.ZM_BETTYPE_CQSS_5X_FS,    // 重庆时时彩 - 五星直选复式
				Constants.ZM_BETTYPE_CQSS_5X_ZH,    // 重庆时时彩 - 五星组合
		};

		String zmLotteryId, zmPlayTypeId;
		for (int i = 0; i < lcPlayTypeIds.length; i++) {
			zmLotteryId = Translator.lcPlayIdToZMLotteryId(lcPlayTypeIds[i]);
			assertEquals(Constants.ZM_LOTTERYID_CQSSC, zmLotteryId);
			
			zmPlayTypeId = Translator.lcHfPlayIdToZMBetType(lcPlayTypeIds[i], "");
			assertEquals(cqSSC_ZMBetTypes[i], zmPlayTypeId);

			System.out.println("lotteryId:" + zmLotteryId + " -- PlayType(大V彩):" + lcPlayTypeIds[i] + "\t-- ZM_BETTYPE(中民):" + zmPlayTypeId);
		}
	}
	
	@Test
	public void testJcPlayIdToZMLotteryId() throws TranslateException {
		String lotteryId = Translator.lcPlayIdToZMLotteryId(Constants.PLAY_01_ZC_1);
		assertEquals(Constants.ZM_LOTTERY_ZQ_JCSPF, lotteryId);
		lotteryId = Translator.lcPlayIdToZMLotteryId(Constants.PLAY_01_ZC_2);
		assertEquals(Constants.ZM_LOTTERY_ZQ_JCSPF, lotteryId);
		
		lotteryId = Translator.lcPlayIdToZMLotteryId(Constants.PLAY_02_ZC_1);
		assertEquals(Constants.ZM_LOTTERY_ZQ_JCBF, lotteryId);
		lotteryId = Translator.lcPlayIdToZMLotteryId(Constants.PLAY_02_ZC_2);
		assertEquals(Constants.ZM_LOTTERY_ZQ_JCBF, lotteryId);
		
		lotteryId = Translator.lcPlayIdToZMLotteryId(Constants.PLAY_03_ZC_1);
		assertEquals(Constants.ZM_LOTTERY_ZQ_JCJQS, lotteryId);
		lotteryId = Translator.lcPlayIdToZMLotteryId(Constants.PLAY_03_ZC_2);
		assertEquals(Constants.ZM_LOTTERY_ZQ_JCJQS, lotteryId);
		
		lotteryId = Translator.lcPlayIdToZMLotteryId(Constants.PLAY_04_ZC_1);
		assertEquals(Constants.ZM_LOTTERY_ZQ_JCBQC, lotteryId);
		lotteryId = Translator.lcPlayIdToZMLotteryId(Constants.PLAY_04_ZC_2);
		assertEquals(Constants.ZM_LOTTERY_ZQ_JCBQC, lotteryId);
		
		lotteryId = Translator.lcPlayIdToZMLotteryId(Constants.PLAY_06_LC_1);
		assertEquals(Constants.ZM_LOTTERY_LQ_JCRFSF, lotteryId);
		lotteryId = Translator.lcPlayIdToZMLotteryId(Constants.PLAY_06_LC_2);
		assertEquals(Constants.ZM_LOTTERY_LQ_JCRFSF, lotteryId);
		
		lotteryId = Translator.lcPlayIdToZMLotteryId(Constants.PLAY_07_LC_1);
		assertEquals(Constants.ZM_LOTTERY_LQ_JCSF, lotteryId);
		lotteryId = Translator.lcPlayIdToZMLotteryId(Constants.PLAY_07_LC_2);
		assertEquals(Constants.ZM_LOTTERY_LQ_JCSF, lotteryId);
		
		lotteryId = Translator.lcPlayIdToZMLotteryId(Constants.PLAY_08_LC_1);
		assertEquals(Constants.ZM_LOTTERY_LQ_JCFC, lotteryId);
		lotteryId = Translator.lcPlayIdToZMLotteryId(Constants.PLAY_08_LC_2);
		assertEquals(Constants.ZM_LOTTERY_LQ_JCFC, lotteryId);
		
		lotteryId = Translator.lcPlayIdToZMLotteryId(Constants.PLAY_09_LC_1);
		assertEquals(Constants.ZM_LOTTERY_LQ_JCDXF, lotteryId);
		
		lotteryId = Translator.lcPlayIdToZMLotteryId(Constants.PLAY_09_LC_2);
		assertEquals(Constants.ZM_LOTTERY_LQ_JCDXF, lotteryId);
		
		lotteryId = Translator.lcPlayIdToZMLotteryId(Constants.PLAY_13_J5_R3);
		assertEquals(Constants.ZM_LOTTERYID_JX11X5, lotteryId);
		
		
	}

	@Test(expected=IllegalArgumentException.class)
	public void testJcPlayIdToZMLotteryIdException() throws TranslateException{
		Translator.lcPlayIdToZMLotteryId(null);
	}
	
	@Test(expected=TranslateException.class)
	public void testJcPlayIdToZMLotteryIdTranslatorException() throws TranslateException {
		Translator.lcPlayIdToZMLotteryId("abc");
	}
	
	@Test
	public void testLcPlayIdToZMLotteryId() throws TranslateException{
		String zmLotteryId = Translator.lcPlayIdToZMLotteryId(Constants.PLAY_02_ZC_1);
		System.out.println("zmLotteryId: " + zmLotteryId);
		assertEquals(Constants.ZM_LOTTERY_ZQ_JCBF, zmLotteryId);
	}
	
	@Test
	public void testlcHfPlayIdToZMBetType() throws TranslateException{
		String zmLotteryId = Translator.lcHfPlayIdToZMBetType("13_J5_R3", "");
		System.out.println("zmLotteryId: " + zmLotteryId);
		assertEquals(Constants.ZM_BETTYPE_11_RX_3, zmLotteryId);
	}
	
	/**
	 * 测试混合过关模式
	 */
	@Test
	public void testHHPlayType() throws TranslateException {
		Ticket ticket = new Ticket();
		ticket.setCode("230131:spf-230111:bqc");
		ticket.setPlayId(PlayType.JCZQ_HH.getPlayIdWithPass(false));
		String code = Translator.translateTicketCodeToPFormat(ticket);
		System.out.println("混合过关的中民出票内容: " + code);
		assertEquals("SPF@2-301:[胜,平]/BQC@2-301:[平-平]", code);
	}
	
	@Test 
	public void testHHZMOddsToLC() throws TranslateException{
		String zmOdds = "JQS@5-027[0=11]/BQC@6-001[胜-胜=5.5,负-负=3.1]";
		String lcBetContent = "50270:JQS-60013300:BQC";
		String lcPlayId = "05_ZC_2";
		String code = Translator.translateOddsToLCFormat(zmOdds, lcBetContent, lcPlayId);
		assertEquals("JQS@5-027[0=11]/BQC@6-001[胜-胜=5.5,负-负=3.1]", code);
	}
	
	@Test
	public void testBQC() throws TranslateException{
		String zmOdds = "5-027:[胜胜=5.5,负-负=3.1]/6-001:[胜-胜=5.5,负-负=3.1]";
		String lcBetContent = "50273300-60013300";
		String lcPlayId = "04_ZC_2";
		String code = Translator.translateOddsToLCFormat(zmOdds, lcBetContent, lcPlayId);
		assertEquals("5.5A3.1-5.5A3.1", code);
	}
	
	@Test
	public void testBQCHH() throws TranslateException{
		String zmOdds = "BQC@5-027:[胜胜=5.5,负-负=3.1]/BQC@3-003:[负胜=24]";
		Odds o = Odds.parseZMOdds(zmOdds);
		String odd = o.getMatchOdds().get(1).getOptionOddsMap().get("负胜");
		assertEquals("24", odd);
	}
	
	@Test
	public void testSFC() throws TranslateException{
		// FC@2-302[负16-20=18.5,胜16-20=12.5]
		String zmOdds = "5-027:[负16-20=18.5,胜16-20=12.5]/6-001:[负16-20=18.5,胜16-20=12.5]";
		String lcBetContent = "50271404-60011404";
		String lcPlayId = "08_LC_2";
		String code = Translator.translateOddsToLCFormat(zmOdds, lcBetContent, lcPlayId);
		assertEquals("18.5A12.5-18.5A12.5", code);
	}
	
	@Test
	public void testTranslateAnRuiOddsToDavFormat() throws TranslateException{
		//足球混合  05_ZC_1
		String hhOdd = "18020:周一001(-1):[让球胜@5.10+让球负@6.84+让球平@3.30]/18021:周一002(0):[胜@2.30+负@1.90+平@2.19]/18020:周一001(0):[胜胜@5.10+平负@6.84]/18021:周一002(0):[负平@1.90]/001(0):[10@5.10+31@6.84]/18021:周一002(0):[02@1.90+52@2.19]/001(0):[1@5.10+4@6.84]/18021:周一002(0):[6@1.90+7@2.19]/18021:周一002(0):[6@1.90+平其他@2.19]";
		
		String hhOdd2 = "18021:周一002(0):[平其他@2.19]";
		
		//足球让球胜平负 01_ZC_01
		String rqsOdd = "18020:周一001(-1):[胜@5.10+负@6.84+平@3.30]/18021:周一002(+1):[胜@2.30]";
		//足球胜平负 01_ZC_02
		String BRQSPF_odd = "18020:周一001(0):[胜@5.10+负@6.84+平@3.30]/18021:周一002(0):[胜@2.30]";
		//半全场 04_ZC_01
		String bqc_odd = "8020:周一001(0):[胜胜@5.10+平负@6.84]/18021:周一002(0):[负平@1.90]/18020:周一001(0):[胜胜@5.10+平负@6.84]";
		//进球数 03_ZC_01
		String jqs_odd = "18020:周一001(0):[1@5.10+4@6.84]/18021:周一002(0):[6@1.90+7@2.19]";
		//比分
		String bf_odd = "18020:周一001(0):[10@5.10+31@6.84]/18021:周一002(0):[02@1.90+52@2.18]";
		//篮球混合 10_LC_2
		String lqhh_odd = "18020:周一301(0):[主胜@5.10+主负@6.84]/18021:周一302(6.5):[让分主胜@1.90+让分主负@2.19]/18021:周一302(0):[主胜21-25@1.90+负胜26@2.19]/18021:周一302(200.5):[大@1.90+小@2.19]";
		String lqhh_odd2 = "63461:周五301(-9.50):[让分主胜@1.70]/63462:周五302(0):[客胜26@29.00]/63463:周五303(+150.50):[小@1.77]";
		//篮球胜负
		String lqsf_odd= "18020:周一301(0):[主胜@5.10+主负@6.84]/18021:周一302(0):[主胜@1.90+主负@2.19]";
		//篮球大小分
		String lqdxf_odd= "63613:周六301(+195.50):[大@1.70+小@1.79]/63614:周六302(+191.50):[大@1.65]";
		//篮球让分胜负
		String rfsf_odd= "18021:周一302(+6.5):[让分主胜@1.90+让分主胜@2.19]/18020:周一302(-16.5):[让分主胜@1.90]";
		//篮球胜分差

		String lqsfc_odd="18021:周一302(0):[主胜21-25@1.90+负胜26@2.19]/18021:周一302(0):[主胜1-5@1.90+负胜11-15@2.19]";

		String temp="63399:周四001(0):[11@7.00+22@10.50+12@8.00+13@18.00]/63412:周四002(0):[平平@4.00+平负@3.60+负平@15.00]/63400:周四003(0):[2@3.30+3@3.20+4@4.60]/63401:周四004(0):[胜平@15.00+平胜@4.00+平平@6.00]";
		
		//篮球
		String code = Translator.translateAnRuiOddsToDavFormat(lqdxf_odd,"09_LC_2");
		System.out.println(code);
//		code = Translator.translateAnRuiOddsToDavFormat(temp,"05_ZC_2");
		
		
		String test = "63406:周二301(+6.5):[让分主负@1.68)+让分主胜@1.81)]/63407:周二302(0):[主负@2.36+主胜@1.38]/63408:周二303(0):[主胜6-10@4.10+负胜6-10@4.25]/63409:周二304(+193.5):[大@1.75+小@1.75]";
	}
	
}
