package com.xhcms.lottery.commons.persist.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unison.lottery.wap.persist.service.impl.BetServiceTestBase;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.CTBetRequest;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.CTBetService;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

/**
 * 传统足彩服务测试。
 * 
 * @author Yang Bo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-bet-service-spring.xml")
public class CTBetServiceImplTest extends BetServiceTestBase {

	@Autowired
	private CTBetService ctBetService;

	/** 准备投注方案应该计算好注数、钱数 */
	@Test
	public void prepareSchemeShouldComputeNotesAndMoney() throws BetException {
		CTBetRequest betRequest = mockBetRequtest(
				LotteryId.CTZC, 
				PlayType.CTZC_14, 
				1,
				new String[]{"12139;310,3,3,3,3,3,3,3,3,3,3,1,0,0"}
		);
		BetScheme scheme = ctBetService.prepareScheme(betRequest);
		assertNotesMultipleMoney(scheme, 3, 1, 6);
	}

//	@Test
//	public void schemeShouldUsePassTypeAsBetType(){
//		CTBetRequest betRequest = mockBetRequtest(
//				LotteryId.CTZC, 
//				PlayType.CTZC_14, 
//				1,
//				new String[]{"12139;310,3,3,3,3,3,3,3,3,3,3,1,0,0"}
//		);
//		BetScheme scheme = ctBetService.prepareScheme(betRequest);
//		tickets = 
//		assertEquals(Constants.ZM_BETTYPE_CTZC_FS);
//	}
//
	
	/** 创建测试用的投注请求对象 */
	private CTBetRequest mockBetRequtest(LotteryId lotteryId, PlayType playType, 
			int multiple, String[] betContents) {
		CTBetRequest req = new CTBetRequest();
		req.setLotteryId(lotteryId.toString());
		req.setPlayType(playType);
		req.setBetContents(Arrays.asList(betContents));
		req.setChooseType(ChooseType.MANUAL);
		req.setIssue("12139");
		req.setMultiple(multiple);
		return req;
	}

	/** 检查注数、倍数、钱数都符合预期 */
	private void assertNotesMultipleMoney(BetScheme scheme, int notes, int multiple, int money) {
		assertEquals("expected notes is (" + notes + ") but is (" + scheme.getBetNote() + ").",
				notes, scheme.getBetNote());
		assertEquals("expected multiple is (" + multiple + ") but is (" + scheme.getMultiple() + ").",
				multiple, scheme.getMultiple());
		assertEquals("expected money is (" + money + ") but is (" + scheme.getTotalAmount() + ").",
				money, scheme.getTotalAmount());
	}
}
