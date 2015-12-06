package com.davcai.lottery.platform.client.qiutan;

import java.util.ArrayList;
import java.util.List;

import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;

public class QiutanJCMatchInfo extends LotteryPlatformBaseResponse{
	List<QiutanJCMatch> matches = new ArrayList<QiutanJCMatch>();

	public List<QiutanJCMatch> getMatches() {
		return matches;
	}

	public void setMatches(List<QiutanJCMatch> matches) {
		this.matches = matches;
	}
}
