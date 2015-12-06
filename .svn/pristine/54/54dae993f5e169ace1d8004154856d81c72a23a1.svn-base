package com.davcai.lottery.platform.client.yuanchengchupiao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.mapping.Array;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.davcai.lottery.platform.client.AbstractQueryPrizeClient;
import com.davcai.lottery.platform.client.model.QueryPrizeResponse4OneLoop;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.GetBonusListResponse4OneLoop;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.OrderInfo;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoResponse;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.dao.TicketRemoteDao;
import com.xhcms.lottery.commons.persist.entity.TicketRemotePO;
/**
 * 查询奖金列表  不用了
 * @author Next
 *
 */
public class YuanChengChuPiaoGetBonusListClientImpl  extends AbstractQueryPrizeClient{

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private TicketRemoteDao ticketRemoteDao;
	
	@Autowired
	@Qualifier("yuanChengChuPiaoGetBonusListClientSupport")
	private AbstractYuanChengChuPiaoClientSupport clientSupport;
	
	
	/**
	 * 参数
	 * @param startTime
	 * @param stopTime
	 * @return
	 */
	private Map<String, Object> makeParams(int startTime, int stopTime) {
		Map<String, Object> params=new HashMap<String,Object>();
		
		params.put("Start_Time", startTime+"");
		params.put("End_Time", stopTime+"");
		
		return params;
	}

	
	@Override
	protected int getMaxCountForOneLoop() {
		//查询时通过时间，和票的数量无关，默认应该最大
		return 1000;
	}

	@Override
	public QueryPrizeResponse4OneLoop queryPrizeForOneLoop(List<Ticket> ts) {
		//查询中奖金额列表 不需要票，只需要 查询起止时间
		YuanChengChuPiaoResponse resp = new YuanChengChuPiaoResponse();
		
		
		int stopTime = (int) (System.currentTimeMillis() / 1000);
		//时间段为 之前10分钟 
		int startTime = stopTime - 600;
		
		Map<String,Object> params = makeParams(startTime , stopTime);
		
		resp=(YuanChengChuPiaoResponse)clientSupport.doPostWithRetry(params);
		
		GetBonusListResponse4OneLoop getBonus = (GetBonusListResponse4OneLoop) resp;
		
		//查询到的订单信息   订单号，中奖金额
		List<OrderInfo> orderInfos = getBonus.getOrderInfo();
		
		//组装数据 GetBonusListResponse4OneLoop --> QueryPrizeResponse4OneLoop
		QueryPrizeResponse4OneLoop queryPrizeResponse4OneLoop = new QueryPrizeResponse4OneLoop();
		
		//ticketid ， Ticket对象
		Map<Long, Ticket> ticketsMap = new HashMap<>();
		
		for(OrderInfo o : orderInfos)
		{
			List<String> orderstr = new ArrayList<>();
			orderstr.add(o.getOrderId());
			List<TicketRemotePO> listTicketRemotePO = ticketRemoteDao.findTicketByOrderIds(orderstr);
			TicketRemotePO ticket = listTicketRemotePO.get(0);
			Ticket t = new Ticket();
			//设置参数
			t.setId(ticket.getId());
			//状态和信息 有疑问 不是单张票的状态 是总体状态
			t.setStatus(Integer.parseInt(getBonus.getError()));
			t.setMessage(getBonus.getMessage());
			t.setAfterTaxBonus(BigDecimal.valueOf(o.getAmount()));
			//组装Map
			ticketsMap.put(ticket.getId(), t);
		}
		
		queryPrizeResponse4OneLoop.setTicketsMap(ticketsMap);
		
		return queryPrizeResponse4OneLoop;
	}

	
	
}
