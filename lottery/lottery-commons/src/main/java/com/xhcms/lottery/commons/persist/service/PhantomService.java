package com.xhcms.lottery.commons.persist.service;

import java.math.BigInteger;
import java.util.List;

import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.lang.OddAndActualOdd;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.TicketPO;
import com.xhcms.lottery.lang.PlayType; 

public interface PhantomService {
	
	/**
	 * 是否能使用“僵尸机制”，并且是否为僵尸用户
	 * @param uid
	 * @param pt
	 * @return true 是僵尸用户并且玩法能使用“僵尸机制”
	 */
	boolean isDoll(long uid, PlayType pt);
	
	long getSUID();
	
	BetSchemePO getDollByShadow(long sid);
	BetSchemePO getShadowByDoll(long did);
	
	/**
	 * 投注时，doll方案要拷贝一个shadow方案。
	 * 要确保先保存了doll方案的tickets，后面 onSendBuyScheme 依赖这个顺序。
	 **/
	BetScheme onBetScheme(BetScheme scheme);
	
	/**
	 * 对于特殊用户发起的方案是否生成可出票方案
	 * @param schemeType	方案类型
	 * @param lotteryId
	 * @return
	 */
	boolean isGeneratePhantomScheme(int schemeType, String lotteryId);

	/**
	 * 交易完成时，如果shadow方案状态为交易成功，执行“拷贝操作”，否则 doll 方案不能出票，自动撤单。 
	 * 对于shadow方案，获取对应doll方案的票，设置他们的状态为对应s-ticket的交易状态，拷贝票的赔率；
	 * 对doll方案执行改方案状态的动作，扣款留给da的deduct task。
	 */
	void onSchemeBuyComplete(BetSchemePO scheme);
	
	/** 
	 * 开奖时，把shadow方案的票信息拷贝到对应的doll方案的票去。
	 * 具体拷贝: 中奖状态
	 **/
	void onSchemePrized(BetSchemePO scheme);
	
	/**
	 * 生成投注方案时，根据不同条件，更新方案状态。
	 */
	void updateSchemeStatus(BetSchemePO scheme);
	
	/**
	 * 更新票的状态。
	 */
	void updateTicketStatus(BetSchemePO scheme);
	
	/**
	 * 更新特殊用户发起的Doll方案的票状态为“出票成功”
	 * @param ticketPO
	 * @param scheme
	 */
	void updateVirtualSchemeTicketStatusSucc(TicketPO ticketPO, BetScheme scheme);
	
	boolean canAutoTicketSuccess(String playId, long sponsorId, int schemeType, String lotteryId);
	
	/**
	 * 更新特殊方案的出票赔率
	 * @param ticketPO
	 * @param scheme
	 */
	void updateOddsOfSpecialScheme(TicketPO ticketPO, BetScheme scheme);
	
	OddAndActualOdd convert(TicketPO ticketPO, BetScheme scheme);
	
	/**
	 * 是否为影子用户，不考虑玩法。
	 * @param userId
	 * @return
	 */
	boolean isShadow(long userId);
	
	List<BigInteger> listUser();
}
