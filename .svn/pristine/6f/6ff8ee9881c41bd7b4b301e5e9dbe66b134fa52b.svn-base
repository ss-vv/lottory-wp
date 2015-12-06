package com.xhcms.lottery.dc.task.ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.job.Job;
import com.xhcms.commons.mq.MessageSender;
import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.commons.client.Translator;
import com.xhcms.lottery.commons.event.BuyTicketMessage;
import com.xhcms.lottery.commons.persist.entity.BetSchemeWithIssueInfoPO;
import com.xhcms.lottery.dc.persist.service.TicketService;
import com.xhcms.lottery.lang.PlayType;

/**
 * 
 * <p>Title: 检查高频彩可出票方案，发送出票请求</p>
 * <p>Description: 检查合买方案是否符合出票条件，如果符合则出票。目前支持江西11选5、重庆时时彩</p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author 陈岩, Yang Bo
 * @version 1.0.0
 */
public class BuyHFTicketTask extends Job {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private MessageSender messageSender;
    
    @Override
    protected void execute() {
        // 按照 playId 合并的方案列表。
        Map<String, List<BetSchemeWithIssueInfoPO>> playIdSchemePOsMap = ticketService.listHFAllowBuySchemesGroupByPlayId();        
        for(Entry<String, List<BetSchemeWithIssueInfoPO>> playIdSchemePOs: playIdSchemePOsMap.entrySet()) {
        	String playId = playIdSchemePOs.getKey();
        	List<BetSchemeWithIssueInfoPO> schemePOs = playIdSchemePOs.getValue();
        	sendBuyTicketMessage(playId, schemePOs);
        }
    }

    private void sendBuyTicketMessage(String playId, List<BetSchemeWithIssueInfoPO> schemePOs) {
    	try{
        	String lcLotteryId = PlayType.getLotteryIdByPlayId(playId);
        	log.debug("lcLotteryId from playId '{}' is: {}", playId, lcLotteryId);
        	List<Long> schemeIds=getCanSendSchemeIds(lcLotteryId,schemePOs);
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
     * 返回可以发送的方案id列表
     * @param lcLotteryId
     * @param schemePOs
     * @return
     */
	private List<Long> getCanSendSchemeIds(String lcLotteryId,
			List<BetSchemeWithIssueInfoPO> schemePOs) {
		List<Long> result=new ArrayList<Long>();
		if(null!=schemePOs&&!schemePOs.isEmpty()){
			for (BetSchemeWithIssueInfoPO po : schemePOs) {

				result.add(po.getId());

			}
		}
		return result;
	}

	@Override
    public String toString(){
        return "BuyTicketTask";
    }
}
