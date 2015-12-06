package com.xhcms.lottery.mc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.davcai.lottery.platform.client.model.OrderTicketResponse;
import com.davcai.lottery.platform.util.ILotteryPlatformAllocator;
import com.davcai.orderticket.task.OrderTicketTask;
import com.xhcms.lottery.commons.client.translate.MultiPlatformBetCodeUtil;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.TicketPO;
import com.xhcms.lottery.commons.persist.service.PhantomService;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.PlayType;

public class BuyTicketServiceImpl implements BuyTicketService{
	
	@Autowired
	private TicketDao ticketDao;

	@Autowired
	private BetSchemeDao betSchemeDao;
	
	@Autowired
	private PhantomService pservice;
	
	private static final Logger log = LoggerFactory .getLogger(BuyTicketServiceImpl.class);
	
	@Autowired
	private ILotteryPlatformAllocator<Ticket> lotteryPlatformAllocator;
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public void buyTicket(Long schemeId) {
		// 修改出票状态
		BetSchemePO scheme = betSchemeDao.get(schemeId);
		if (scheme.getStatus() != EntityStatus.TICKET_ALLOW_BUY &&
		// 方案状态“已请求出票”是为处理未成功接票的情况准备的
				scheme.getStatus() != EntityStatus.TICKET_REQUIRED) {
			return ;
		}

		boolean isGroupBuy = (EntityType.BET_TYPE_PARTNER == scheme.getType());
		PlayType pt = PlayType.valueOfLcPlayId(scheme.getPlayId());
		// 只是预防万一
		if (pservice.isDoll(scheme.getSponsorId(), pt) && !isGroupBuy) {
			log.error("Doll scheme want to buyTicket, Not Allowed! sid={}",
					scheme.getId());
			return ;
		}
		//将可出票改成已请求出票状态
		if(EntityStatus.TICKET_ALLOW_BUY == scheme.getStatus()){
			scheme.setStatus(EntityStatus.TICKET_REQUIRED);
		}
		List<TicketPO> tickets = ticketDao.findBySchemeWithLock(schemeId,
				EntityStatus.TICKET_INIT);
		List<Ticket> ts = new ArrayList<Ticket>(tickets.size());
		for (TicketPO ticket : tickets) {
			/**
			 * 取消在此更新票状态，交由分票任务处理票之后再更新
			 * @see com.davcai.lottery.persist.OrderTicketServiceImpl.makeStatus(Ticket)
			 */
			//ticket.setStatus(EntityStatus.TICKET_REQUIRED); 
			Ticket t = new Ticket();
			BeanUtils.copyProperties(ticket, t);
			Map<String, String> betCode4LotteryPlatformMap = MultiPlatformBetCodeUtil
					.parse(ticket.getPlatformBetCode());
			t.setPlatformBetCodeMap(betCode4LotteryPlatformMap);
			ts.add(t);
		}
		orderTicketsWithMultiPlatforms(ts);//多平台出票
	}
	
	private void orderTicketsWithMultiPlatforms(List<Ticket> tickets) {
		//按各彩票平台权重对票进行分组
        Map<String,List<Ticket>> ticketsGroupByLotteryPlatformId=lotteryPlatformAllocator.allocateByPriority(tickets);
        //并发调用，同时对每个彩票平台发起请求
        if(null!=ticketsGroupByLotteryPlatformId&&!ticketsGroupByLotteryPlatformId.isEmpty()){
        	int size=ticketsGroupByLotteryPlatformId.size();
        	ExecutorService executorService=Executors.newFixedThreadPool(size);
        	List<OrderTicketTask> tasks = new ArrayList<OrderTicketTask>();
        	for(Entry<String, List<Ticket>> ticketsGroupByLotteryPlatformIdEntry:ticketsGroupByLotteryPlatformId.entrySet()){
        		log.debug("将{}张票分配给{}平台",ticketsGroupByLotteryPlatformIdEntry.getValue().size(),ticketsGroupByLotteryPlatformIdEntry.getKey());
        		OrderTicketTask task=createTask();
        		task.setLotteryPlatformId(ticketsGroupByLotteryPlatformIdEntry.getKey());
        		task.setTickets(ticketsGroupByLotteryPlatformIdEntry.getValue());
        		tasks.add(task);
        	}
			try {
				List<Future<OrderTicketResponse>> resultList = executorService.invokeAll(tasks);
				log.debug("多平台发起投注请求响应:{}",resultList);
				executorService.shutdown();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
	}
	
	public OrderTicketTask createTask() {
		return new OrderTicketTask();
	}
}
