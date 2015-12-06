package com.unison.lottery.mc.uni.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.event.BuyTicketMessage;
import com.xhcms.lottery.mc.service.BuyTicketService;

/**
 * 投注消息处理器。
 * @author Yang Bo
 *
 */
public class OrderTicketMQHandler implements MessageHandler<BuyTicketMessage> {

    private static final long serialVersionUID = 2828216191231267394L;
    
    private static final Logger logger = LoggerFactory.getLogger(OrderTicketMQHandler.class);
    
    @Autowired
    private BuyTicketService buyTicketService;
    
    @Override
    public void handle(BuyTicketMessage tm) {
    	logger.debug("receive BuyTicketMessage:{}",tm);
    	try {
    		for(Long schemeId: tm.getSchemes()){
                buyTicketService.buyTicket(schemeId);
            }
		} catch (Exception e) {
			logger.error("",e);
		}
    }
}
