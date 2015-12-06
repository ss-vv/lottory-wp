package com.unison.lottery.mc.uni.client;

import com.unison.lottery.mc.uni.parser.QueryPrizeParserStatus;

/**
 * 中奖消息处理器
 * @author Yang Bo
 *
 */
public interface IPrizeProcessor {
	void process(QueryPrizeParserStatus prizeStatus);
}
