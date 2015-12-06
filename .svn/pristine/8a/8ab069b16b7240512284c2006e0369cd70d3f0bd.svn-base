package com.davcai.lottery.platform.client;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.davcai.lottery.platform.client.ILotteryPlatformQueryPrizeClient;
import com.davcai.lottery.platform.client.model.QueryPrizeResponse;
import com.davcai.lottery.platform.client.model.QueryPrizeResponse4OneLoop;
import com.xhcms.lottery.commons.data.Ticket;

public abstract class AbstractQueryPrizeClient implements ILotteryPlatformQueryPrizeClient{

	protected Logger logger=LoggerFactory.getLogger(getClass());

	@Override
	public QueryPrizeResponse queryPrize(List<Ticket> tickets) {
		QueryPrizeResponse response=null;
		logger.debug("处理票列表，总共有{}个",null==tickets?0:tickets.size());
		if(null!=tickets&&!tickets.isEmpty()){
			response=new QueryPrizeResponse();
			List<QueryPrizeResponse4OneLoop> queryPrizeResponse4OneLoopList=new ArrayList<QueryPrizeResponse4OneLoop>();
			int counter=0;
			List<Ticket> ts=new ArrayList<Ticket>();
			for(Ticket t : tickets){
		        // 查询票张数不超过指定的张数
		        if(counter == getMaxCountForOneLoop()){
		        	handleOneLoop(queryPrizeResponse4OneLoopList, ts);
		            ts.clear();
		            counter = 0;
		        }
		        ts.add(t);
		        counter++;
		    }
		    if(ts.size() > 0){
		    	
		    	handleOneLoop(queryPrizeResponse4OneLoopList, ts);
		    }
		    response.setResponse4Loops(queryPrizeResponse4OneLoopList);
		}
		return response;
	}

	protected abstract int getMaxCountForOneLoop() ;

	private void handleOneLoop(List<QueryPrizeResponse4OneLoop> queryPrizeResponse4OneLoopList, List<Ticket> ts) {
		QueryPrizeResponse4OneLoop queryPrizeResponse4OneLoop=queryPrizeForOneLoop(ts);
		logger.debug("queryPrizeResponse4OneLoop={}",queryPrizeResponse4OneLoop);
		queryPrizeResponse4OneLoopList.add(queryPrizeResponse4OneLoop);
		
	}

	@Override
	public  abstract QueryPrizeResponse4OneLoop queryPrizeForOneLoop(List<Ticket> ts) ;

}