package com.xhcms.lottery.commons.persist.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.unison.lottery.api.redEnvalope.model.RedEnvalope;
import com.unison.lottery.weibo.data.service.store.persist.entity.RedEnvalopePO;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.Recharge;

public interface AccountService {
    
    /**
     * 查询账户列表
     * @param paging
     * @param username
     */
    void listAccount(Paging paging, String username);
    
	/**
	 * 添加账户信息，注册成功时调用
	 * @param account
	 */
	void addAccount(Long userId, String username);

	/**
	 * 添加银行信息
	 * @param account
	 */
	void addBankInfo(Long userId, String province, String city, String bank, String accountNumber, String password);

	/**
	 * 修改银行信息
	 * @param account
	 */
	void modifyBankInfo(Long userId, String province, String city, String bank, String accountNumber);
	
    /**
     * 用户使用易宝申请充值
     * 
     * @param userId 用户ID
     * @param amount 用户充值金额（未扣手续费）
     * @param fee 总手续费
     * @param channelFee 渠道手续费
     * @param channelCode 支付通道编码
     * @param ip 用户IP
     * @return 充值申请单ID
     */
    Long applyForRecharge(Long userId, BigDecimal amount, BigDecimal fee, BigDecimal channelFee, String channelCode, String ip);

    /**
     * 用户使用支付宝网银申请充值
     * 
     * @param userId 用户ID
     * @param amount 用户充值金额（未扣手续费）
     * @param fee 总手续费
     * @param channelFee 渠道手续费
     * @param channelCode 支付通道编码
     * @param ip 用户IP
     * @return 充值申请单ID
     */
    Long alipayApplyForRecharge(Long userId, BigDecimal amount, BigDecimal fee, BigDecimal channelFee, String channelCode, String ip);

    /**
     * 用户使用支付宝WAP申请充值
     * @param userId 用户id
     * @param amount 用户充值金额（未扣手续费）
     * @param fee 总手续费
     * @param channelFee 渠道手续费
     * @param channelCode 支付通道编码
     * @param ip 用户ip
     * @return 充值申请单id
     */
    Long wapAlipayForRecharge(Long userId, BigDecimal amount, BigDecimal fee, BigDecimal channelFee, String channelCode, String ip);
    

    /**
     * 用户使用WAP手机卡通过易宝申请充值
     * @param userId
     * @param amount
     * @param fee
     * @param channelFee
     * @param channelCode
     * @param ip
     * @return
     */
    Long yeepayCardForRecharge(Long userId, BigDecimal amount, BigDecimal fee, BigDecimal channelFee, String channelCode, String ip);
    
    /**
     * 用户使用客户端支付宝申请充值
     * @param userId 用户id
     * @param amount 用户充值金额（未扣手续费）
     * @param fee 总手续费
     * @param channelFee 渠道手续费
     * @param channelCode 支付通道编码
     * @param ip 用户ip
     * @return 充值申请单id
     */
    Long clientAlipayForRecharge(Long userId, BigDecimal amount, BigDecimal fee, BigDecimal channelFee, String channelCode, String ip);
    

    /**
     * 用户使用客户端手机卡通过易宝申请充值
     * @param userId
     * @param amount
     * @param fee
     * @param channelFee
     * @param channelCode
     * @param ip
     * @return
     */
    Long clientYeepayCardForRecharge(Long userId, BigDecimal amount, BigDecimal fee, BigDecimal channelFee, String channelCode, String ip);
    
    /**
     * 确认充值成功。
     * 通过易宝充值成功时，调用该接口直接将充值金额打入用户账户
     * @param operator
     * @param userId
     * @param amount 易宝账户收到金额(必须不超过充值金额)
     * @param rechargeId 充值申请记录ID 
     * @param trxId 易宝交易流水号
     */
    void comfirmRecharge(Integer operator, Long userId, BigDecimal amount, Long rechargeId, String trxId);
 
    /**
     * 判断支付宝交易是否已经处理
     * @param rechargeId 充值记录id
     * @return 已处理：true 未处理:false
     */
    boolean isDealWithTransactionResult(Long rechargeId);
    
    /**
     * 派奖
     * @param operator
     * @param userId
     * @param amount 派奖金额（已扣个税）
     * @param betPartnerId 投注合买人记录ID
     */
    void devide(Integer operator, Long userId, BigDecimal amount, Long betPartnerId, String note);
    
    /**
     * 赠款
     * @param operator
     * @param userId
     * @param amount 赠款数额
     * @param note
     */
    void grant(Integer operator, Long userId, BigDecimal amount, String note);
   
    /**
     * 给用户佣金
     * @param operator 操作管理员id
     * @param givedUserId 收到佣金的用户id
     * @param commission 佣金
     * @param givingBetPartnerId lt_bet_partner 购买记录id
     */
    void giveCommission(Integer operator, Long givedUserId, BigDecimal commission, Long givingBetPartnerId);
    
    /**
     * 投注冻结。冻结投注所需资金
     * @param userId
     * @param amount 冻结金额
     * @param betPartnerId 投注合买人记录ID
     */
    void betFrozen(Long userId, BigDecimal amount, Long betPartnerId);
    
    /**
     * 发起合买保底资金冻结。冻结投注所需资金
     * @param userId
     * @param amount 冻结金额
     * @param schemeId 方案ID
     */
    void betFloorFrozen(Long userId, BigDecimal amount, Long schemeId);
    
    /**
     * 投注扣款。扣除投注成功所对应的冻结资金
     * @param userId
     * @param deductAmount 扣除金额
     * @param returnAmount 返还金额
     * @param schemeId 投注合买记录ID 
     */
    void betDeductAndReturn(Long userId, BigDecimal deductAmount, BigDecimal returnAmount, Long schemeId);
    
    /**
     * 合买保底金额扣款。扣除投注成功所对应的保底资金，返还投注失败所对应的保底资金
     * @param userId
     * @param deductAmount 扣除金额
     * @param returnAmount 返还金额
     * @param schemeId 投注方案ID 
     */
    void betFloorDeductAndReturn(Long userId, BigDecimal deductAmount, BigDecimal returnAmount, Long schemeId);
    
    /**
     * 提现返款。用户提现失败返回被冻结的提现资金
     * @param operator
     * @param userId
     * @param amount 返款金额
     * @param withdrawId 提现记录ID
     * @param note
     */
    void withdrawReturn(Integer operator, Long userId, BigDecimal amount, Long withdrawId, String note);
    
    /**
     * 提现冻结
     * @param userId
     * @param amount 提现金额（不含费用）
     * @param fee 提现费用
     * @param withdrawId 提现记录ID
     * 
     * @return 返回订单ID
     */
    Long withdrawFrozen(Long userId, BigDecimal amount, BigDecimal fee, Long withdrawId);
    
    /**
     * 提现扣款
     * @param operator
     * @param userId
     * @param amount 扣除金额
     * @param withdrawId 提现记录ID
     * @param note
     */
    void withdrawDeduct(Integer operator, Long userId, BigDecimal amount, Long withdrawId, String note);
    
    /**
     * 获取用户账户信息
     * @param userId
     * @return
     */
    Account getAccount(Long userId);
    
    /**
     * 验证提现密码
     * @param userId
     * @param passwd
     * @return true 正确
     */
    boolean checkPasswd(long userId, String passwd);
    
    /**
     * 修改提现密码
     * @param userId
     * @param passwd
     */
    void updatePasswd(long userId, String passwd);
    
    /**
     * 重置提现密码为用户的登陆密码。<br/>
     * 需求来源：田利耕 2012-5-30<br/>
     * 实现：杨波<br/>
     */
    void resetWithdrawPassword(long userId);
    
    /**
     * 根据id取得用户的充值信息
     * @param id
     * @return
     */
    Recharge getRecharge(long id);
    
    /**
     * 根据id更新用户充值记录
     * @param rechargeId
     */
    void updateRecharge(long rechargeId, String note);

    
	

	/**
     * 先验证旧提现密码，再更新新提现密码
     * @param userId
     * @param oldPasswd
     * @param newPasswd
     * 
     * @author 陈岩
     */
	void checkAndUpdatePasswd(long userId, String oldPasswd, String newPasswd);
	   
    /**
     * 充值成功
     * @param rechargeId
     */
    void rechargeSuccess(long rechargeId);

	List<Long> listAllId();

	/**
	 * 发红包冻结资金
	 * @param userId 
	 * @param frozenFund 冻结金额
	 * @param fee 费用
	 * @param envalopeId
	 * @return 
	 */
	Map<Integer, BigDecimal> withRedFrozen(Long userId, BigDecimal redEnvalopeAmountBigDecimal,
			BigDecimal fee, long envalopeId);

	/**
	 * 抢红包扣款，并增加款项到抢到的用户账户中
	 * @param userId 抢红包的用户id
	 * @param smallRedEnvalopeAmount 抢到的红包金额(以分为单位)
	 * @param redEnvalope 红包信息
	 * @return 抢红包扣款之后的可用余额和赠款余额
	 */
	Map<Integer, BigDecimal> withdGrabRedDeduct(Long userId, Long smallRedEnvalopeAmount, RedEnvalope redEnvalope);

	/**
	 * 用户发红包冻结返款
	 * @param leaveAmountDecimal
	 * @param redEnvalopePO
	 */
	void withSendRedReturn(BigDecimal leaveAmountDecimal, RedEnvalopePO redEnvalopePO);

}
