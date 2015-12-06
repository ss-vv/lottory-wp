package com.davcai.lottery.platform.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.davcai.lottery.platform.client.model.OrderTicketResponse;
import com.davcai.lottery.platform.client.model.OrderTicketResponse4OneLoop;
import com.xhcms.lottery.commons.data.Ticket;

public abstract class AbstractLotteryPlatformOrderTicketClient implements
		ILotteryPlatformOrderTicketClient {

	private Logger logger=LoggerFactory.getLogger(getClass());
	@Override
	public OrderTicketResponse orderTicketByLoops(List<Ticket> tickets) {
		OrderTicketResponse response=null;
		if(null!=tickets&&!tickets.isEmpty()){
			response=new OrderTicketResponse();
			//先分组
			Map<String, List<Ticket>> ticketsGroupByPlayIdAndIssueId=groupTickets(tickets);
			//每一组再分轮发
			if(null!=ticketsGroupByPlayIdAndIssueId&&!ticketsGroupByPlayIdAndIssueId.isEmpty()){
				List<OrderTicketResponse4OneLoop> orderTicketResponse4OneLoopList=new ArrayList<OrderTicketResponse4OneLoop>();
				for( Entry<String, List<Ticket>> ticketsGroupEntry:ticketsGroupByPlayIdAndIssueId.entrySet()){
					handleOneGroup(orderTicketResponse4OneLoopList,ticketsGroupEntry);
				}
				response.setResponse4Loops(orderTicketResponse4OneLoopList);
			}
		}
		return response;
	}

	private void handleOneGroup(
			List<OrderTicketResponse4OneLoop> orderTicketResponse4OneLoopList,
			Entry<String, List<Ticket>> ticketsGroupEntry) {
		String key=ticketsGroupEntry.getKey();
		List<Ticket> ticketsGroup = ticketsGroupEntry.getValue();
		logger.debug("处理{}对应的票列表，总共有{}个",key,null==ticketsGroup?0:ticketsGroup.size());
		if(null!=ticketsGroup&&!ticketsGroup.isEmpty()){
			int counter=0;
			List<Ticket> ts=new ArrayList<Ticket>();
			for(Ticket t : ticketsGroup){
				preProcess(t);
		        // 出票张数不超过指定的张数
		        if(counter == getMaxCountForOneLoop()){
		        	handleOneLoop(orderTicketResponse4OneLoopList, ts);
		            //ts.clear();不可以使用ts.clear()，应该使用new，
		        	//因为orderTicketResponse4OneLoopList元素会持有ts引用，ts.clear()会改变orderTicketResponse4OneLoopList元素的内容
		        	ts=new ArrayList<Ticket>();
		            counter = 0;
		        }
		        ts.add(t);
		        counter++;
		    }
		    if(ts.size() > 0){
		    	handleOneLoop(orderTicketResponse4OneLoopList, ts);
		    }
		}
	}

	private void handleOneLoop(
			List<OrderTicketResponse4OneLoop> orderTicketResponse4OneLoopList,
			List<Ticket> ts) {
		OrderTicketResponse4OneLoop orderTicketResponse4OneLoop=orderTicketForOneLoop(ts);
		logger.debug("orderTicketResponse4OneLoop={}",orderTicketResponse4OneLoop);
		orderTicketResponse4OneLoopList.add(orderTicketResponse4OneLoop);
		
	}

	protected abstract int getMaxCountForOneLoop();

	/**
	 * 在发起投注调用之前，对ticket做预处理
	 * @param t
	 */
	protected abstract void preProcess(Ticket t) ;

	/**
	 * 对票进行分组
	 * @param tickets
	 * @return
	 */
	protected abstract Map<String, List<Ticket>> groupTickets(List<Ticket> tickets) ;
	
	@Override
	public  abstract OrderTicketResponse4OneLoop orderTicketForOneLoop(
			List<Ticket> tickets) ;

}
