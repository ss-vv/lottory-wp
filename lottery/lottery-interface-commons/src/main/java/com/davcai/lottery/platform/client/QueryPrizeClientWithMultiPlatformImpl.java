package com.davcai.lottery.platform.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.platform.client.chooser.ILotteryPlatformChooser;
import com.davcai.lottery.platform.client.model.QueryPrizeResponse;
import com.davcai.lottery.platform.constants.LotteryInterfaceName;
import com.xhcms.lottery.commons.data.Ticket;

public class QueryPrizeClientWithMultiPlatformImpl implements
		IQueryPrizeClientWithMultiPlatform {
	
	@Autowired
	private ILotteryPlatformChooser chooser;

	@Override
	public QueryPrizeResponse queryPrize(String lotteryPlatformId,
			List<Ticket> tickets) {
		ILotteryPlatformClient client = chooser.chooseOnePlatformClient(lotteryPlatformId, LotteryInterfaceName.queryPrize);
		ILotteryPlatformQueryPrizeClient queryPrizeClient=(ILotteryPlatformQueryPrizeClient) client;
		return queryPrizeClient.queryPrize(tickets);
	}

	public ILotteryPlatformChooser getChooser() {
		return chooser;
	}

	public void setChooser(ILotteryPlatformChooser chooser) {
		this.chooser = chooser;
	}

}
