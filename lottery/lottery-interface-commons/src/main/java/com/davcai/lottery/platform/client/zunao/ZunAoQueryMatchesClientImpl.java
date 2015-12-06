package com.davcai.lottery.platform.client.zunao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davcai.lottery.platform.client.ILotteryPlatformQueryMatchesClient;
import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.davcai.lottery.platform.client.zunao.model.ZunAoJCMatchesResponse;
import com.unison.lottery.mc.uni.client.QueryMatchesClient;
import com.unison.lottery.mc.uni.parser.QueryMatchesParserStatus;
import com.xhcms.lottery.dc.data.Match;

@Service
public class ZunAoQueryMatchesClientImpl implements ILotteryPlatformQueryMatchesClient{
	@Autowired
	private QueryMatchesClient queryMatchesClient;
	@Override
	public LotteryPlatformBaseResponse postByMatchType(String matchType) {
		String type = matchType.toLowerCase();
		ZunAoJCMatchesResponse zunAoJCMatchesResponse = new ZunAoJCMatchesResponse();
		QueryMatchesParserStatus status = new QueryMatchesParserStatus(type);
		queryMatchesClient.postWithStatus(type, status);
		List<Match> matches = status.getMatches();
		zunAoJCMatchesResponse.setMatches(matches);
		return zunAoJCMatchesResponse;
	}
}
