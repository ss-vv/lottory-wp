package com.unison.lottery.mc.uni.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.platform.client.QueryRemoteTicketResultClient;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoOrderTicketResultResponse;
import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.data.RemoteTicket;
import com.xhcms.lottery.commons.event.RemoteTicketMessage;
import com.xhcms.lottery.commons.persist.entity.TicketRemotePO;
import com.xhcms.lottery.mc.persist.service.RemoteTicketService;
import com.xhcms.lottery.mc.persist.service.TicketService;

public class QueryRemoteOrderResultMQHandler implements MessageHandler<RemoteTicketMessage> {

	private static final long serialVersionUID = -5761631457237569464L;
	private Logger log = LoggerFactory.getLogger(getClass());

	private QueryRemoteTicketResultClient queryRemoteTicketResultClient;
	@Autowired
	private RemoteTicketService remoteTicketService;
	@Autowired
	private TicketService ticketService;
    private static final int MAX_TICKETS_PER_REQUEST = 50;
	@Override
	public void handle(RemoteTicketMessage rtm) {
		List<String> tickets = new ArrayList<String>(MAX_TICKETS_PER_REQUEST);

        int counter = 0;
        for(String t: rtm.getRemoteTicket()){
            if(counter == MAX_TICKETS_PER_REQUEST){
                postAndProcess(tickets);
                counter = 0;
                tickets.clear();
            }
            if(log.isDebugEnabled()){
                log.debug("check ticket order: " + t);
            }
            tickets.add(t);
            counter++;
        }
        if(counter > 0){
            postAndProcess(tickets);
        }
	}
	
    private void postAndProcess(List<String> tickets){
    	YuanChengChuPiaoOrderTicketResultResponse response=queryRemoteTicketResultClient.queryTicketResult(tickets);
    	if(response!=null && response.getOrderInfo()!=null && response.getOrderInfo()!=null){
    		List<RemoteTicket> ts=response.getOrderInfo();//获取票状态响应对象
    		remoteTicketService.updateRemoteTicketStatus(ts);//更新lt_ticket_remote 表 status message
    		Map<Long,TicketRemotePO> tMap=remoteTicketService.findRemoteTicketWithOrderIds(ts);//ticket_id , RemoteTicketPO
    		ticketService.checkYuanChengChuPiao(tMap);
    		ticketService.updateOdds(ts);
    	}
    }

	public QueryRemoteTicketResultClient getQueryRemoteTicketResultClient() {
		return queryRemoteTicketResultClient;
	}

	public void setQueryRemoteTicketResultClient(
			QueryRemoteTicketResultClient queryRemoteTicketResultClient) {
		this.queryRemoteTicketResultClient = queryRemoteTicketResultClient;
	}

	public RemoteTicketService getRemoteTicketService() {
		return remoteTicketService;
	}

	public void setRemoteTicketService(RemoteTicketService remoteTicketService) {
		this.remoteTicketService = remoteTicketService;
	}

	public TicketService getTicketService() {
		return ticketService;
	}

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}
    

}
