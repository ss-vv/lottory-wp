package com.davcai.lottery.platform.client;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.davcai.lottery.platform.client.anruizhiying.AnRuiZhiYingQueryPrizeClientImpl;
import com.davcai.lottery.platform.client.anruizhiying.AnRuiZhiYingQueryPrizeClientSupport;
import com.davcai.lottery.platform.client.anruizhiying.constants.ParamNames;
import com.davcai.lottery.platform.client.anruizhiying.parser.AnRuiZhiYingQueryPrizeParser;
import com.davcai.lottery.platform.client.anruizhiying.parser.IAnRuiZhiYingRespParser;
import com.davcai.lottery.platform.client.model.QueryPrizeResponse4OneLoop;
import com.xhcms.lottery.commons.data.Ticket;

public class AnRuiZhiYingQueryPrizeClientImplTest {
	
	private AnRuiZhiYingQueryPrizeClientImpl client;
	
	@Before
	public void setUp(){
		client=new AnRuiZhiYingQueryPrizeClientImpl();
		AnRuiZhiYingQueryPrizeClientSupport clientSupport=new AnRuiZhiYingQueryPrizeClientSupport();
		List<String> paramNamesShouldSign=new ArrayList<String>();
		paramNamesShouldSign.add(ParamNames.CHANNEL_ID);
		paramNamesShouldSign.add(ParamNames.CHANNEL_TICKET_ID);
		((AnRuiZhiYingQueryPrizeClientSupport) clientSupport).setParamNamesShouldSign(paramNamesShouldSign);
		IAnRuiZhiYingRespParser anRuiZhiYingQueryPrizeParser=new AnRuiZhiYingQueryPrizeParser();
		((AnRuiZhiYingQueryPrizeClientSupport) clientSupport).setAnRuiZhiYingQueryPrizeParser(anRuiZhiYingQueryPrizeParser);
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
		QueryPrizeResponse4OneLoop response = client.queryPrizeForOneLoop(tickets);
		System.out.println(response);
//		assertTrue(StringUtils.equals(response.getDesc(), "票列表为空")&&response.getStatus()==-9999);
		assertTrue(null==response);
	}
	
	@Test
	public void testQueryOneTicketPrize(){
		List<Ticket> tickets = new ArrayList<Ticket>();
		Ticket ticket=new Ticket();
		ticket.setId(1683664l);
		tickets.add(ticket);
		QueryPrizeResponse4OneLoop response = client.queryPrizeForOneLoop( tickets);
		System.out.println(response);
//		assertTrue(StringUtils.equals(response.getDesc(), "投注成功")&&response.getStatus()==0);
//		assertTrue(null!=response.getSuccTickets()&&response.getSuccTickets().size()==tickets.size());
	}
	
	@Test
	public void testQueryManyTicketsPrize(){
		List<Ticket> tickets = new ArrayList<Ticket>();
		Ticket ticket=new Ticket();
		ticket.setId(1683663l);
		Ticket ticket2=new Ticket();
		ticket2.setId(1683664l);
		tickets.add(ticket);
		tickets.add(ticket2);
		QueryPrizeResponse4OneLoop response = client.queryPrizeForOneLoop( tickets);
		System.out.println(response);
	}
	
	


}
