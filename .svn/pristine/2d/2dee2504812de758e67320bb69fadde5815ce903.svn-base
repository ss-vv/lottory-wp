package com.davcai.lottery.platform.client.anruizhiying;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.davcai.lottery.platform.client.AbstractClientSupport;
import com.davcai.lottery.platform.client.AbstractQueryPrizeClient;
import com.davcai.lottery.platform.client.anruizhiying.constants.ParamNames;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingQueryPrizeResponse;
import com.davcai.lottery.platform.client.anruizhiying.util.AnRuiZhiYingResponseAdapter;
import com.davcai.lottery.platform.client.model.QueryPrizeResponse4OneLoop;
import com.xhcms.lottery.commons.data.Ticket;

public class AnRuiZhiYingQueryPrizeClientImpl extends AbstractQueryPrizeClient  {

	@Autowired
	@Qualifier("anRuiZhiYingQueryPrizeClientSupport")
	private AbstractClientSupport clientSupport;
	
	
	
	@Override
	public QueryPrizeResponse4OneLoop queryPrizeForOneLoop(List<Ticket> ts) {
		Map<String, Object> params=makeParams(ts);
		AnRuiZhiYingQueryPrizeResponse response = (AnRuiZhiYingQueryPrizeResponse) clientSupport.doPost(params);
		return AnRuiZhiYingResponseAdapter.toQueryPrizeResponse(response);
	}

	private Map<String, Object> makeParams(List<Ticket> tickets) {
		Map<String, Object> result=null;
		if(null!=tickets&&!tickets.isEmpty()){
			String channelTicketId=null;
			StringBuilder sb=new StringBuilder();
			int size=tickets.size();
			int i=1;
			for(Ticket ticket:tickets){
				if(null!=ticket.getId()){
					sb.append(ticket.getId());
					if(i<size){
						sb.append(",");
					}
				}
			}
			channelTicketId=sb.toString();
			if(StringUtils.isNotBlank(channelTicketId)){
				result=new HashMap<String, Object>();
				result.put(ParamNames.CHANNEL_TICKET_ID, channelTicketId);
			}
		}
		return result;
	}

	protected int getMaxCountForOneLoop() {
		return 50;
	}

	public AbstractClientSupport getClientSupport() {
		return clientSupport;
	}

	public void setClientSupport(AbstractClientSupport clientSupport) {
		this.clientSupport = clientSupport;
	}

}
