package com.davcai.lottery.platform.client.zunao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davcai.lottery.platform.client.ILotteryPlatformQueryMatcheOddsClient;
import com.davcai.lottery.platform.client.zunao.model.ZunAoJCMatcheOddsResponse;
import com.unison.lottery.mc.uni.client.QueryJCOddsClient;
import com.unison.lottery.mc.uni.parser.QueryJCOddsParserStatus;
import com.xhcms.lottery.dc.data.OddsBase;

@Service
public class ZunAoQueryMatchOddsClientImpl implements ILotteryPlatformQueryMatcheOddsClient{
	@Autowired
	private QueryJCOddsClient queryJCOddsClient;
	
	@Override
	public ZunAoJCMatcheOddsResponse getOddsByLotteryId(String lotteryId) {
		String type = lotteryId.toLowerCase();
		String dg = type+"dg";
		String gg = type+"gg";
		QueryJCOddsParserStatus dgstatus = new QueryJCOddsParserStatus(dg);
		QueryJCOddsParserStatus ggstatus = new QueryJCOddsParserStatus(gg);
		queryJCOddsClient.postWithStatus(dg, dgstatus);
		queryJCOddsClient.postWithStatus(gg, ggstatus);
		List<OddsBase> dgodds = dgstatus.getOdds();
		List<OddsBase> ggodds = ggstatus.getOdds();
		ggodds.addAll(dgodds);
		ZunAoJCMatcheOddsResponse z = new ZunAoJCMatcheOddsResponse();
		z.setOdds(ggodds);
		return z;
	}
}
