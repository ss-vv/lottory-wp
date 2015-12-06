package com.xhcms.lottery.admin;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.PrintableFile;
import com.xhcms.lottery.mc.persist.service.TicketService;

public class ListTicketFileTest {

	public TicketService ticketService;
	@Before
	public void init(){
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:spring-service.xml",
				"classpath:spring-db.xml","classpath:spring-activemq.xml"});
		ticketService = (TicketService) context.getBean("ticketService");
	}
	
	@Test
	public void testFindPrintableTicketFile(){ 
		Long ticketId = 994L;
		Paging paging = new Paging(1, 12);
		List<PrintableFile> printableFiles = ticketService.listHighSpeedPrintFileByTicketId(paging, null, null, ticketId, "changchunshitidian");
	}
}
