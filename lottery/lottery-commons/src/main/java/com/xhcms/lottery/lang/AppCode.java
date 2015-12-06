package com.xhcms.lottery.lang;

public interface AppCode {

    int INVALID_USERNAME = 10000;       // 无效用户名
    int INVALID_PASSWORD = 10001;       // 密码错误
    int INVALID_USER = 10002;           // 无效用户
    int INVALID_ACCOUNT = 10003;        // 无效账户
    
    int INVALID_AMOUNT = 11000;         // 无效金额
    int INVALID_ARGUMENT = 11001;       // 无效参数
    int RELATED_ORDER_NOT_EXIST = 11002;    // 关联流水单不存在
    
    // 投注相关
    int EXCEED_MAX_NOTE = 80000;                // 超过最大注数
    int INVALID_ODDS = 80001;                   // 无效赔率数据
    int UNUSUAL_TICKET_STATUS = 80002;          // 投注方案尚有非成功和失败状态的出票的信息
    int UNUSUAL_SCHEME_TICKET_AMOUNT = 80003;   // 投注方案的投注金额与出票成功和失败金额不符
    int UNUSUAL_SCHEME_PARTNER_AMOUNT = 80004;  // 投注方案的投注金额与合买人投注总金额不符
    int SCHEME_NOT_EXIST  = 80005;              // 投注方案不存在
    int SCHEME_BET_AMOUNT_EXCEED  = 80006;      // 投注金额超过可跟单金额
    int SCHEME_BET_FAIL  = 80007;               // 投注失败：方案不是合买方案或已满员
    int MATCH_NOT_SUPPORT_PLAY  = 80008;        // 赛事不支持玩法
    int INVALID_PLAY  = 80009;                  // 无效玩法
    int INVALID_BET_CODE  = 80010;              // 无效投注内容
    int INVALID_OFFTIME = 80011;                // 投注方案中的赛事中包含已截止赛事
    int SCHEME_NOT_PUBLIC = 80012;              // 方案未公开
    int SCHEME_UNUSUAL_OPERATE = 80013;			// 非法操作
    int SCHEME_UNUSUAL_TICKET_NOTE = 80014;		// 投注方案含有成功或失败状态的出票的信息
    int BET_FAIL_SSO_ERROR = 80015;				// 投注失败，单点登录出现故障。
    int BET_TICKET_EXCEED_20K = 80020;			// 投注失败，无法拆票以满足单票低于2万的限制
    int BET_GROUPBUY_FULL =80021;               //合买满员，投注失败

    // 高频彩
    int INVALID_ISSUE_NUMBER = 80100;	// 无效的高频彩期号
    int INVALID_ISSUE_OFFTIME = 80101;	// 方案中包含已经截止的期
    int CURRENT_SALING_ISSUE_FOR_SHOW_NOT_FOUND = 80102;//无法为页面显示找到当前在售的期信息
    
    // 资金相关
    int ACCOUNT_FROZED = 90000;         // 资金账户被冻结
    int INSUFFICIENT_BALANCE = 90001;   // 资金余额不足
    int FROZEN_INSUFFICIENT_BALANCE = 90002;// 操作金额超过冻结资金
    int UNUSUAL_OPERATE_AMOUNT = 90004;     // 实际操作金额与预期金额不一致
    
    int INVALID_RECHARGE_AMOUNT = 98000;    // 充值金额无效
    int RECHARGE_NOT_EXIST = 98001;         // 充值记录不存在
    int INVALID_RECHARGE_STATUS = 99001;    // 充值状态无效
    
    
    int WITHDRAW_NOT_EXIST = 99000;         // 提现记录不存在
    int INVALID_WITHDRAW_STATUS = 99001;    // 提现状态无效
    
    int PASSWD_MUSTBEEQUAL = 30001;
    int PASSWD_LOGIN_WRONG = 30002;
    int PASSWD_WH_WRONG = 30003;
    int PASSWD_WH_NOTEXIST = 30004;
    
    int CM_CANTSELF = 40001;			//不能定制自己
	
    /** 优惠劵类型不符！ */
    int INVALID_VOUCHER_TYPE = 50000;
    /** 优惠劵状态不符！ */
    int INVALID_VOUCHER_STATUS = 50001;
    /** 用户与优惠劵不匹配！ */
    int INVALID_VOUCHER_USER = 50002;
    /** 优惠劵已过期！ */
    int INVALID_VOUCHER_EXPIRE = 50003;
    /** 优惠劵ID不能为空！ */
    int INVALID_VOUCHER_ID = 50004;
    /** 优惠劵不存在！ */
    int VOUCHER_INEXISTENCE = 50005;
    /** 优惠劵金额不符！ */
    int INVALID_VOUCHER_PRICE = 50006;
    /** 充值优惠劵不存在！ */
    int RECHARGE_VOUCHER_INEXISTENCE= 50007;
    /** 不支持的彩种 */
	int UNSUPPORTED_LOTTERY_ID = 50020;
	/** 优惠劵范围不能为空！ */
    int INVALID_VOUCHER_LIMIT = 50011;
    /** 优惠劵范围不匹配哦！ */
    int INCONFORMITY_VOUCHER_LIMIT = 50012;
}