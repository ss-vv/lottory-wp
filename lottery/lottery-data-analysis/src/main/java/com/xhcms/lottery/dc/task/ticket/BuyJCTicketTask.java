package com.xhcms.lottery.dc.task.ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.job.Job;
import com.xhcms.commons.mq.MessageSender;
import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.commons.client.Translator;
import com.xhcms.lottery.commons.event.BuyTicketMessage;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.dc.persist.service.TicketService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PlayType;

/**
 * 
 * <p>Title: 检查竞彩可出票方案，发送出票请求</p>
 * <p>Description: 检查合买方案是否符合出票条件，如果符合则出票</p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author wanged, Yang Bo
 * @version 1.0.0
 */
public class BuyJCTicketTask extends Job {

    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private MessageSender messageSender;
    
    @Autowired
    private OpenSaleIntercessor openSaleIntercessor;

	
    
    @Override
    protected void execute() {
    	log.debug("BuyJCTicketTask begin run");
        // 按照 playId 合并的方案列表。
        Map<String, List<BetSchemePO>> playIdSchemePOsMap = ticketService.listJCAllowBuySchemesGroupByPlayId();
        
        for(Map.Entry<String, List<BetSchemePO>> playIdSchemePOs: playIdSchemePOsMap.entrySet()) {
        	String playId = playIdSchemePOs.getKey();
        	List<BetSchemePO> schemePOs = playIdSchemePOs.getValue();
        	sendBuyTicketMessage(playId, schemePOs);
        }
        log.debug("BuyJCTicketTask finish run");
    }

    private void sendBuyTicketMessage(String playId, List<BetSchemePO> schemePOs) {
        
    	try{
        	String lcLotteryId = PlayType.getLotteryIdByPlayId(playId);
        	log.debug("lcLotteryId from playId '{}' is: {}", playId, lcLotteryId);
        	List<Long> schemeIds=getCanSendSchemeIds4JC(lcLotteryId,schemePOs);
            if( null!=schemeIds) {
                BuyTicketMessage tm = new BuyTicketMessage();
                tm.setLotteryId(Translator.lcPlayIdToZMLotteryId(playId));
                tm.setSchemes(schemeIds);
                log.debug("Sending BuyTicketMessage: {}", tm);
                messageSender.send(tm);
            }
    	}catch(TranslateException te){
    		log.error("can not send buy ticket message for playId: {}, schemes: {}", 
    				new Object[]{playId, schemePOs, te});
    	}
	}

   

	/**
	 * 返回可以发送的竞彩方案id列表
	 * @param lcLotteryId
	 * @param schemePOs
	 * @return
	 */
	private List<Long> getCanSendSchemeIds4JC(String lcLotteryId,
			List<BetSchemePO> schemePOs) {
		List<Long> result=null;
		boolean fbAllow = openSaleIntercessor.isAllowable(Constants.JCZQ);
        boolean bbAllow = openSaleIntercessor.isAllowable(Constants.JCLQ);
        if(fbAllow && Constants.JCZQ.equals(lcLotteryId) || 
    		    bbAllow && Constants.JCLQ.equals(lcLotteryId)){
        	result=new ArrayList<Long>();
        	for(BetSchemePO po:schemePOs){
        		result.add(po.getId());
        	}
        }
		return result;
	}
	
	

	

	

	

	@Override
    public String toString(){
        return "BuyJCTicketTask";
    }

	
}
