package com.davcai.lottery.platform.client.chooser;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.davcai.lottery.platform.client.ILotteryPlatformClient;
import com.davcai.lottery.platform.client.ILotteryPlatformOrderTicketClient;
import com.davcai.lottery.platform.client.anruizhiying.AnRuiZhiYingOrderTicketClientImpl;
import com.davcai.lottery.platform.client.chooser.ILotteryPlatformChooser;
import com.davcai.lottery.platform.client.chooser.LotteryPlatformChooserImpl;
import com.davcai.lottery.platform.client.zunao.ZunAoOrderTicketClientImpl;
import com.davcai.lottery.platform.constants.LotteryInterfaceName;
import com.xhcms.lottery.lang.LotteryPlatformId;

public class LotteryPlatformChooserTest {
	
	@Test
	public void testChooseRightClientByPlatformId(){
		ILotteryPlatformChooser chooser=new LotteryPlatformChooserImpl();
		Map<String, ILotteryPlatformClient> map4Clients=new HashMap<String, ILotteryPlatformClient>();
		map4Clients.put(LotteryPlatformId.AN_RUI_ZHI_YING+"_"+LotteryInterfaceName.orderTicket.toString(), new AnRuiZhiYingOrderTicketClientImpl());
		map4Clients.put(LotteryPlatformId.ZUN_AO+"_"+LotteryInterfaceName.orderTicket.toString(), new ZunAoOrderTicketClientImpl());
		((LotteryPlatformChooserImpl)chooser).setMap4Clients(map4Clients);
		
		
		
		
		String platformId = LotteryPlatformId.AN_RUI_ZHI_YING;
		ILotteryPlatformClient lotteryPlatformClient=chooser.chooseOnePlatformClient(platformId, LotteryInterfaceName.orderTicket);
		assertTrue(lotteryPlatformClient instanceof ILotteryPlatformOrderTicketClient &&lotteryPlatformClient instanceof AnRuiZhiYingOrderTicketClientImpl);
		
		platformId = LotteryPlatformId.ZUN_AO;
		lotteryPlatformClient=chooser.chooseOnePlatformClient(platformId, LotteryInterfaceName.orderTicket);
		assertTrue(lotteryPlatformClient instanceof ILotteryPlatformOrderTicketClient &&lotteryPlatformClient instanceof ZunAoOrderTicketClientImpl);
	}

}
