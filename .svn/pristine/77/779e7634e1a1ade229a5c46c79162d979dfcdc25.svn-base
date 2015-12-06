package com.xhcms.lottery.mc.jc.handler;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xhcms.lottery.mc.jc.client.JCTicketClient;
import com.xhcms.lottery.mc.persist.service.TicketService;

/**
 * 查询老接口中奖信息。在切换新接口时为了处理遗留中奖信息而开发。
 * @author Yang Bo
 */
public class JCBonusCheckerForOldPlatform {
	
	private static Logger logger = LoggerFactory.getLogger(JCBonusCheckerForOldPlatform.class);
	
	// 历史接口客户端
	private JCTicketClient historyClient;
	
	@Autowired
	private TicketService ticketService;
	
	public static void main(String[] args) throws ParseException {
		if (args.length != 1){
			System.out.println("Usage: JCBonusCheckerForOldPlatform <end time>");
			return;
		}
		logger.info("=> Loading spring context ...");
		// 不要装载  message queue spring context. 
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[]{"spring-service.xml","spring-db.xml"});
		JCBonusCheckerForOldPlatform checker = context.getBean(JCBonusCheckerForOldPlatform.class);
		String endTime = args[0];
		logger.info("=> Start checking bonus on old platform before '{}'...", endTime);
		checker.checkBonus(endTime);
		logger.info("=> Finished");
	}

	/**
	 * 检查在 endTime 之前生成的方案是否中奖.
	 * @param endTime 要检查方案的截止时间。
	 * @throws ParseException 
	 */
	private void checkBonus(String endTime) throws ParseException {
		Date end = DateUtils.parseDate(endTime, new String[]{"yyyy-MM-dd HH:mm:ss"});
		List<Long> ticketIds = ticketService.listNotPrizeTicketBefore(end);
		logger.info("=> post ticket ids to old platform: \n\t{}", ticketIds);
		boolean ret = historyClient.post(ticketIds);
		logger.info("post result: {}", ret);
	}

	public JCTicketClient getHistoryClient() {
		return historyClient;
	}

	public void setHistoryClient(JCTicketClient historyClient) {
		this.historyClient = historyClient;
	}

}
