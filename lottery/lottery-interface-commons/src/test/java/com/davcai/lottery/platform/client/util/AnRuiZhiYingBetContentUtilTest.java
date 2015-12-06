package com.davcai.lottery.platform.client.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.davcai.lottery.platform.client.anruizhiying.util.AnRuiZhiYingBetContentUtil;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.LotteryPlatformId;

public class AnRuiZhiYingBetContentUtilTest {
	
	@Test
	public void testMakeBetContent(){
		List<Ticket> tickets = new ArrayList<Ticket>();
		Ticket ticket=new Ticket();
		ticket.setId(1683663l);
		ticket.setPlayId("08_LC_2");
		ticket.setPassTypeId("2@1");
		ticket.setCode("330211-330311");
		ticket.setActualCode("10848~[客胜1-5]~0/1084~[客胜1-5]~0");
		ticket.setNote(1);
		ticket.setMultiple(1);
		ticket.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		
		Ticket ticket2=new Ticket();
		ticket2.setId(1683664l);
		ticket2.setPlayId("08_LC_2");
		ticket2.setPassTypeId("2@1");
		ticket2.setCode("330211-330311");
		ticket2.setActualCode("10848~[客胜1-5]~0/1084~[客胜1-5]~0");
		ticket2.setNote(1);
		ticket2.setMultiple(1);
		ticket2.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		tickets.add(ticket);
		tickets.add(ticket2);
		String betContent=AnRuiZhiYingBetContentUtil.makeBetContent(tickets);
		System.out.println(betContent);
		assertTrue(StringUtils.equals(betContent, "10848~[客胜1-5]~0/1084~[客胜1-5]~0$2X1~1~1@1683663|10848~[客胜1-5]~0/1084~[客胜1-5]~0$2X1~1~1@1683664"));
	}

}
