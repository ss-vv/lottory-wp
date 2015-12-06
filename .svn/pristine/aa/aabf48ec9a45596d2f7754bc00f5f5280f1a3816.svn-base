package com.xhcms.lottery.commons.persist.service;

import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.lang.OddAndActualOdd;
import com.xhcms.lottery.commons.persist.entity.TicketPO;

/**
 * 用于转换用户投注赔率内容
 * @author lei.li@davcai.com
 */
public interface BetOddsService {
 
	/**
	 * 根据投注内容和玩法类型，转换出尊傲出票成功赔率内容
	 * @param ticketPO	投注内容，请参考lt_ticket的code字段内容格式
	 * @param scheme
	 * @return
	 */
	OddAndActualOdd convert(TicketPO ticketPO, BetScheme scheme);
	
}