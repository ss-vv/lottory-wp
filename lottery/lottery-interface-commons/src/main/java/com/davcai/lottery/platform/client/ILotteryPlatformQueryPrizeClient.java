package com.davcai.lottery.platform.client;

import java.util.List;

import com.davcai.lottery.platform.client.model.QueryPrizeResponse;
import com.davcai.lottery.platform.client.model.QueryPrizeResponse4OneLoop;
import com.xhcms.lottery.commons.data.Ticket;

public interface ILotteryPlatformQueryPrizeClient extends ILotteryPlatformClient{

	QueryPrizeResponse queryPrize(List<Ticket> tickets);

	public abstract QueryPrizeResponse4OneLoop queryPrizeForOneLoop(List<Ticket> ts);

}
