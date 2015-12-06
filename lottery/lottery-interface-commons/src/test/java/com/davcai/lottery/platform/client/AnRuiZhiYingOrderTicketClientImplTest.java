package com.davcai.lottery.platform.client;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.davcai.lottery.platform.client.anruizhiying.AbstractAnRuiZhiYingClientSupport;
import com.davcai.lottery.platform.client.anruizhiying.AnRuiZhiYingOrderTicketClientImpl;
import com.davcai.lottery.platform.client.anruizhiying.AnRuiZhiYingOrderTicketClientSupport;
import com.davcai.lottery.platform.client.anruizhiying.constants.ParamNames;
import com.davcai.lottery.platform.client.model.OrderTicketResponse;
import com.davcai.lottery.platform.client.model.OrderTicketResponse4OneLoop;
import com.xhcms.lottery.commons.client.translate.MultiPlatformBetCodeUtil;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.LotteryPlatformId;

public class AnRuiZhiYingOrderTicketClientImplTest {
	
	private AnRuiZhiYingOrderTicketClientImpl client;
	
	@Before
	public void setUp(){
		client=new AnRuiZhiYingOrderTicketClientImpl();
		AbstractAnRuiZhiYingClientSupport clientSupport=new AnRuiZhiYingOrderTicketClientSupport();
		List<String> paramNamesShouldSign=new ArrayList<String>();
		paramNamesShouldSign.add(ParamNames.CHANNEL_ID);
		paramNamesShouldSign.add(ParamNames.LOTTERY_ID);
		paramNamesShouldSign.add(ParamNames.WARE_ID);
		paramNamesShouldSign.add(ParamNames.BET_AMOUNT);
		paramNamesShouldSign.add(ParamNames.BET_CONTENT);
		((AnRuiZhiYingOrderTicketClientSupport) clientSupport).setParamNamesShouldSign(paramNamesShouldSign);
		clientSupport.setInterfaceUrl("http://124.254.63.10:7001/trade.aspx");
		clientSupport.setChannelId("802");
		clientSupport.setKey("NP9WRYE85R");
		clientSupport.setPostEncoding("utf-8");
		clientSupport.setResponseEncoding("gb2312");
		client.setClientSupport(clientSupport);
		
		
		
	}
	
	@Test
	public void testEmptyTickets(){
		List<Ticket> tickets = new ArrayList<Ticket>();
		OrderTicketResponse4OneLoop response=client.orderTicketForOneLoop( tickets);
		System.out.println(response);
//		assertTrue(StringUtils.equals(response.getDesc(), "票列表为空")&&response.getStatus()==-9999);
//		assertTrue(null!=response.getFailTickets()&&response.getFailTickets().size()==tickets.size());
	}
	
	@Test
	public void testOrderOneTicket(){
		List<Ticket> tickets = new ArrayList<Ticket>();
		Ticket ticket=new Ticket();
		ticket.setId(1683664l);
		ticket.setPlayId("08_LC_2");
		ticket.setPassTypeId("2@1");
		ticket.setCode("330211-330311");
		ticket.setActualCode("62606~[客胜1-5]~0/62607~[客胜1-5]~0");
		ticket.setNote(1);
		ticket.setMultiple(1);
		ticket.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		tickets.add(ticket);
		OrderTicketResponse4OneLoop response=client.orderTicketForOneLoop( tickets);
		System.out.println(response);
//		assertTrue(StringUtils.equals(response.getDesc(), "投注成功")&&response.getStatus()==0);
//		assertTrue(null!=response.getSuccTickets()&&response.getSuccTickets().size()==tickets.size());
	}
	
	@Test
	public void testOrderManyTickets(){
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
		OrderTicketResponse4OneLoop response=client.orderTicketForOneLoop( tickets);
		System.out.println(response);
//		assertTrue(StringUtils.equals(response.getDesc(), "投注成功")&&response.getStatus()==0);
//		assertTrue(null!=response.getSuccTickets()&&response.getSuccTickets().size()==tickets.size());
	}
	
	@Test
	public void testWrongLotteryPlatformId(){
		List<Ticket> tickets = new ArrayList<Ticket>();
		Ticket ticket=new Ticket();
		ticket.setId(1683663l);
		ticket.setPlayId("08_LC_2");
		ticket.setPassTypeId("2@1");
		ticket.setCode("330211-330311");
		ticket.setActualCode("10848~[客胜1-5]~0/1084~[客胜1-5]~0");
		ticket.setNote(1);
		ticket.setMultiple(1);
		ticket.setLotteryPlatformId(LotteryPlatformId.ZUN_AO);
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
		OrderTicketResponse4OneLoop response=client.orderTicketForOneLoop( tickets);
		System.out.println(response);
//		assertTrue(StringUtils.equals(response.getDesc(), "彩票平台id错误，不允许出票")&&response.getStatus()==-9998);
//		assertTrue(null!=response.getFailTickets()&&response.getFailTickets().size()==tickets.size());
	}
	
	@Test
	public void testNotSamePlayIdAndIssue(){
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
		ticket2.setPlayId("07_LC_2");
		ticket2.setPassTypeId("2@1");
		ticket2.setCode("330211-330311");
		ticket2.setActualCode("10848~[客胜1-5]~0/1084~[客胜1-5]~0");
		ticket2.setNote(1);
		ticket2.setMultiple(1);
		ticket2.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		tickets.add(ticket);
		tickets.add(ticket2);
		OrderTicketResponse4OneLoop response=client.orderTicketForOneLoop( tickets);
		System.out.println(response);
//		assertTrue(StringUtils.equals(response.getDesc(), "票列表中playId与期id不一致，不允许出票")&&response.getStatus()==-9997);
//		assertTrue(null!=response.getFailTickets()&&response.getFailTickets().size()==tickets.size());
	}
	
	@Test
	public void testPlayIdBlank(){
		List<Ticket> tickets = new ArrayList<Ticket>();
		Ticket ticket=new Ticket();
		ticket.setId(1683663l);
		ticket.setPlayId("");
		ticket.setPassTypeId("2@1");
		ticket.setCode("330211-330311");
		ticket.setActualCode("10848~[客胜1-5]~0/1084~[客胜1-5]~0");
		ticket.setNote(1);
		ticket.setMultiple(1);
		ticket.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		Ticket ticket2=new Ticket();
		ticket2.setId(1683664l);
		ticket2.setPlayId("07_LC_2");
		ticket2.setPassTypeId("2@1");
		ticket2.setCode("330211-330311");
		ticket2.setActualCode("10848~[客胜1-5]~0/1084~[客胜1-5]~0");
		ticket2.setNote(1);
		ticket2.setMultiple(1);
		ticket2.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		tickets.add(ticket);
		tickets.add(ticket2);
		OrderTicketResponse4OneLoop response=client.orderTicketForOneLoop( tickets);
		System.out.println(response);
//		assertTrue(StringUtils.equals(response.getDesc(), "票列表中playId为空，不允许出票")&&response.getStatus()==-9996);
//		assertTrue(null!=response.getFailTickets()&&response.getFailTickets().size()==tickets.size());
	}
	
	@Test
	public void testOrderTicketByLoop(){
		List<Ticket> tickets = new ArrayList<Ticket>();
		for(int i=1;i<=50;i++){
			Ticket ticket=new Ticket();
			ticket.setId(1684053l+i);
			ticket.setPlayId("05_ZC_2");
			ticket.setPassTypeId("2@1");
			ticket.setCode("SPF@2-001:[让球胜]/JQS@2-002:[0,1]");
//			ticket.setActualCode("10848~[客胜1-5]~0/1084~[客胜1-5]~0");
			ticket.setNote(1);
			ticket.setMultiple(1);
			
			ticket.setPlatformBetCodeMap(MultiPlatformBetCodeUtil.parse("zunao>SPF@2-001:[让球胜]/JQS@2-002:[0,1]#anruizhiying>62887~[让球胜]~0/62888~[0,1]~0"));
//			ticket.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
			tickets.add(ticket);
		}
		
		for(int i=1;i<=51;i++){
			Ticket ticket=new Ticket();
			ticket.setId(1694053l+i);
			ticket.setPlayId("80_ZC_2");
			ticket.setPassTypeId("2@1");
			ticket.setCode("20013-20023");
//			ticket.setActualCode("10848~[平]~0/1084~[平]~0");
			ticket.setNote(1);
			ticket.setMultiple(1);
			ticket.setPlatformBetCodeMap(MultiPlatformBetCodeUtil.parse("zunao>2-001:[胜]/2-002:[胜]#anruizhiying>62887~[胜]~0/62888~[胜]~0"));
//			ticket.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
			tickets.add(ticket);
		}
		

		OrderTicketResponse response = client.orderTicketByLoops(tickets);
		System.out.println(response);
	}

}
