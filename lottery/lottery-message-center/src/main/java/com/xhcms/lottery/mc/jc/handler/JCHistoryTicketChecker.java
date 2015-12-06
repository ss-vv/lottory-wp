package com.xhcms.lottery.mc.jc.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.mc.jc.client.JCTicketClient;
import com.xhcms.lottery.mc.persist.service.TicketService;

/**
 * 检查历史方案的ticket，并按照正常流程更新方案、合买人表。
 * 运营时使用，用来处理那些错过了检查周期的老方案。比如7月12日的问题方案:9468,10344,24607
 * @author Yang Bo
 */
public class JCHistoryTicketChecker {
	
	private static Logger logger = LoggerFactory.getLogger(JCHistoryTicketChecker.class);
	
	// 历史接口客户端
	private JCTicketClient historyClient;
	
	@Autowired
	private TicketService ticketService;
	
	public static void main(String[] args) {
		if (args.length != 1){
			System.out.println("Usage：JCHistoryTicketChecker <scheme id>");
		}
		long schemeId = Long.parseLong(args[0]);
		logger.info("=> Loading spring context ...");
		// 不要装载  message queue spring context. 
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[]{"spring-service.xml","spring-db.xml"});
		JCHistoryTicketChecker checker = context.getBean(JCHistoryTicketChecker.class);
		logger.info("=> Start checking scheme: " + schemeId);
		checker.checkScheme(schemeId);
		logger.info("=> Finished");
	}

	public void checkScheme(Long schemeId){
		List<Ticket> ticketsOfScheme = ticketService.listTicketsOfScheme(schemeId);
		logger.info("=> find {} tickets of scheme.", ticketsOfScheme.size());
		List<Long> ticketIds = new ArrayList<Long>(100);
		for (Ticket ticket : ticketsOfScheme){
			ticketIds.add(ticket.getId());
		}
		logger.info("=> post ticket ids to lottery service interface.");
		historyClient.post(ticketIds);
	}

	public JCTicketClient getHistoryClient() {
		return historyClient;
	}

	public void setHistoryClient(JCTicketClient historyClient) {
		this.historyClient = historyClient;
	}

}
