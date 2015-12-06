/**
 * 
 */
package com.xhcms.lottery.commons.persist.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map; 
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetResult;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.MixPlayType;
import com.xhcms.lottery.utils.ResultTool;

/**
 * @author Bean.Long
 *
 */
public interface BetSchemeBaseService {
	
	/**
	 * 更新方案为已发晒单
	 * @param schemeId
	 */
	boolean updateBetSchemePublishShow(long schemeId);
	
	/**
	 * 判断方案能否被晒单
	 * @param showScheme
	 * @param isPublishShow
	 * @return
	 */
	boolean isCanSendShowScheme(long schemeId);
	
	/**
	 * 对给定的方案ID，发起晒单;条件
	 * @param loginUserId
	 * @param schemeId
	 * @return
	 */
	boolean publishShowScheme(BetSchemePO po, Long loginUserId);
	/**
	 * 对给定的方案ID，发起晒单;条件
	 * <p>1）本身不是晒单方案</p>
	 * <p>2）方案未被晒过</p>
	 * @param loginUserId
	 * @param schemeId
	 */
	void publishShowScheme(BetScheme betScheme, Long loginUserId,String followWeiboContent);
	
	/**
	 * 发布合买微博
	 * @param schemeId
	 * @param betRecordId 投注记录ID
	 * @param loginUserId
	 * @param buyAmount 购买金额
	 * @param weiboContent
	 * @param sponsorId
	 */
	void publishGroupBuyWeibo(long schemeId, long betRecordId,Long loginUserId, int buyAmount, String weiboContent, long sponsorId);
	
	/**
	 * 代购、合买发微博
	 * @param scheme
	 */
	void postWeibo(BetScheme scheme);
	
	/**
	 * 返回方案的PO对象
	 * @param schemeId
	 * @return
	 */
	BetSchemePO getSchemePOById(Long schemeId);
	
	/**
	 * 查询单个方案信息
	 * @param schemeId
	 * @return
	 */
	BetScheme getSchemeById(Long schemeId);
	
	
    
    int checkMatchAndFillScheme(BetScheme betScheme, String playId);
    
    /**
     * 代购投注
     * @param userId    用户id
     * @param betScheme 投注方案
     * @param tickets   方案拆分的彩票
     * 
     * @return 0：投注成功，可出票；其他值认购失败
     */
    int bet(Long userId, BetScheme betScheme, List<Ticket> tickets);
    
    /**
     * 跟单投注
     * @param userId    用户id
     * @param betScheme 投注方案
     * @param tickets   方案拆分的彩票
     * 
     * @return 0：投注成功，可出票；其他值认购失败
     */
    int betFollow(Long userId, BetScheme betScheme, List<Ticket> tickets);
    
    /**
     * 发起合买
     * @param userId    用户id
     * @param betScheme 投注方案
     * @param tickets   方案拆分的彩票
     * 
     * @return BetResult中appCode含义：0：发起成功，不可出票；-1：发起成功，可出票；其他值认购失败
     */
    BetResult promoteBet(Long userId, BetScheme betScheme, List<Ticket> tickets);

    /**
     * 合买跟单
     * @param userId    用户id
     * @param schemeId  方案id
     * @param buyAmount 认购金额
     * 
     * @return BetResult中appCode含义：0：认购成功，不可出票；-1：认购成功，可出票；其他值认购失败
     */
    BetResult purchase(Long userId, Long schemeId, int buyAmount);
    
    /**
     * 拷贝一个方案到某用户名下去投注。
     * 如: EntityStatus.TICKET_ALLOW_BUY
     * @return 新的方案
     */
    public BetScheme copySchemeToUser( Long userId, BetScheme scheme, int multiple );
    
    /**
     * 查询特殊用户发起的指定彩种方案Id
     * @param lotteryId
     * @return
     */
    List<Object> findProfessionalSchemeTicketSuccess(String lotteryId);
    
    /**
     * 查询竞彩足球可以进行预兑奖的方案的赛事Id集合
     * @param schemeIds
     * @return
     */
    Map<Long, List<Long>> findFBPresetMatchIds(List<Object> schemeIds);
    
    /**
     * 查询竞彩篮球可以进行预兑奖的方案的赛事Id集合
     * @param schemeIds
     * @return
     */
    Map<Long, List<Long>> findBBPresetMatchIds(List<Object> schemeIds);
    
    /**
     * 查询指定彩种符合“预兑奖”条件的方案对象
     * 条件：
     * 	<pre>1）方案状态“出票成功”，且方案已经截止</pre>
     * 	<pre>2）方案包含的所有赛事状态为“停售”或“已完成”</pre>
     * 	<pre>3）方案包含的所有票都已经出票成功</pre>
     * 	<pre>4）方案包含的所有赛事都已经设置了预兑奖的“半场比分”和“全场比分”（对于篮球要设置预兑奖的最终比分）</pre>
     *	 
     * @param lotteryId
     * @return	
     *	<pre>key：方案Id</pre>
     * 	<pre>value：方案对应投注赛事ID集合</pre>
     */
    Map<Long, List<Long>> queryPresetSchemeList(String lotteryId);
    
    void sendBetMessage(BetScheme betScheme, long loginUserId);

    /**
     * 检查某渠道某彩种是否可以投注
     * @param channel
     * @param lotteryId
     * @return
     */
	boolean canBet(String channel, String lotteryId);


}
