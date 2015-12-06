package com.davcai.lottery.platform.client;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import com.davcai.lottery.platform.client.model.QueryPrizeResponse;
import com.davcai.lottery.platform.client.model.QueryPrizeResponse4OneLoop;
import com.davcai.lottery.platform.client.zunao.ZunAoQueryPrizeClientImpl;
import com.unison.lottery.mc.uni.InterfaceConfig;
import com.unison.lottery.mc.uni.client.QueryPrizeClient;
import com.unison.lottery.mc.uni.parser.QueryPrizeParser;
import com.xhcms.lottery.commons.data.Ticket;

public class ZunAoQueryPrizeClientImplTest {
	
	private ZunAoQueryPrizeClientImpl client;
	
	@Before
	public void setUp(){
		client=new ZunAoQueryPrizeClientImpl();
		QueryPrizeClient queryPrizeClient=new QueryPrizeClient();
		String key = "pwd123456";
		String partnerId="349139";
		String version="1.0";
		queryPrizeClient.setKey(key);
		queryPrizeClient.setPartnerId(partnerId);
		queryPrizeClient.setUrl("http://121.12.168.101:661/ticketinterface.aspx");
		queryPrizeClient.setTemplate("/tpl/zm_query_prize.xml");
		QueryPrizeParser parser = new QueryPrizeParser();
		InterfaceConfig config=new InterfaceConfig();
		config.setKey(key);
		config.setPartnerId(partnerId);
		config.setVersion(version);
		parser.setConfig(config);
		queryPrizeClient.setParser(parser);
		client.setClient(queryPrizeClient);
		
		
		
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
	public void testQueryOneTicketPrize() throws ParseException{
		List<Ticket> tickets = new ArrayList<Ticket>();
		Ticket ticket=new Ticket();
		ticket.setId(1684525l);
		ticket.setPlayId("70_SSQ_DS");
		ticket.setIssue("2015012");
		ticket.setCreatedTime(DateUtils.parseDate("2015-01-27 11:25:41", "yyyy-MM-dd HH:mm:ss"));
		tickets.add(ticket);
		QueryPrizeResponse4OneLoop response = client.queryPrizeForOneLoop(tickets);
		System.out.println(response);
//		assertTrue(StringUtils.equals(response.getDesc(), "投注成功")&&response.getStatus()==0);
//		assertTrue(null!=response.getSuccTickets()&&response.getSuccTickets().size()==tickets.size());
	}
	
	@Test
	public void testQueryManyTicketsPrize() throws ParseException{
		List<Ticket> tickets = new ArrayList<Ticket>();
		Ticket ticket=new Ticket();
		ticket.setId(1684525l);
		ticket.setPlayId("70_SSQ_DS");
		ticket.setIssue("2015012");
		Ticket ticket2=new Ticket();
		ticket2.setId(1684541l);
		ticket2.setPlayId("80_ZC_2");
		ticket2.setIssue(null);
		ticket2.setCreatedTime(DateUtils.parseDate("2015-01-27 12:07:05", "yyyy-MM-dd HH:mm:ss"));
		tickets.add(ticket);
		tickets.add(ticket2);
		QueryPrizeResponse response = client.queryPrize(tickets);
		System.out.println(response);
	}
	
	
	

}
