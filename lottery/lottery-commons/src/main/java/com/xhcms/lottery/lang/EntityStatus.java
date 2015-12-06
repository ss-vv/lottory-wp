package com.xhcms.lottery.lang;

public interface EntityStatus {

    // 资金流水单的状态
    int ORDER_PLACE = 1;        // 已下单
    int ORDER_WAITING = 2;      // 处理中
    int ORDER_FINISH = 4;       // 处理完成（成功）
    int ORDER_PART_FINISH = 8;  // 处理完成（部分成功）
    int ORDER_FAIL = 16;        // 处理失败
    
    // 提现状态
    int WITHDRAW_INIT = 0;      // 待审核
    int WITHDRAW_AUDIT = 1;     // 已审核，待付款
    int WITHDRAW_PAYED = 2;     // 已付款，待确认
    int WITHDRAW_FINISH = 90;   // 已完成
    int WITHDRAW_REJECT = 98;   // 驳回
    int WITHDRAW_FAIL = 99;     // 提现失败
    
    int RECHARGE_NOT_PAY = 0;       // 未付款
    int RECHARGE_NOT_AUDIT = 1;     // 已付款,待审核
    int RECHARGE_NOT_CONFIRM = 2;   // 已付款,待确认
    int RECHARGE_FINISH = 90;       // 充值已完成
    int RECHARGE_FAIL = 99;         // 充值失败
    
    // 通用状态
    int NORMAL = 0; // 正常
    int LOCKED = 1; // 锁定
    
    //用户验证状态
    int VERIFY_EMAIL = 1;   //邮箱
    int VERIFY_MOBILE = 2;  //手机
    int VERIFY_EMAIL_MOBILE = 3;  //邮箱加手机
    
    //用户是否有效
    int USER_VALID=0;//有效
    int USER_INVALID=1;//无效
    
    // 方案保密状态
    int PRIVACY_PUBLIC = 0;         // 公开
    int PRIVACY_SOLD = 1;           // 开奖后公开
    int PRIVACY_FOLLOW_PUBLIC = 2;  // 仅对跟单者公开（跟单后即公开）
    int PRIVACY_FOLLOW = 3;         // 仅对跟单者公开（销售截至后）
    int PRIVACY_SECRET = 4;         // 保密
    
    // 方案投注状态
    /**0 可参与合买*/
    int SCHEME_ON_SALE = 0;     // 可参与
    /**1 方案已停止认购，未扣冻结款*/
    int SCHEME_STOP = 1;        // 方案已停止认购，未扣冻结款
    /**2 方案已停止认购，已扣冻结款*/
    int SCHEME_SETTLEMENT = 2;  // 方案已停止认购，已扣冻结款
    
    // 出票状态
    /**未出票*/
    int TICKET_INIT = 0;            // 不可出票，未满足出票条件，比如合买
    /**可出票*/
    int TICKET_ALLOW_BUY = 1;       // 可出票
    /**已请求出票*/
    int TICKET_REQUIRED = 2;        // 已请求出票
    /**出票成功*/
    int TICKET_BUY_SUCCESS = 5100;  // 出票成功
    /**出票失败*/
    int TICKET_BUY_FAIL = 5101;     // 出票失败
    /**未中奖*/
    int TICKET_NOT_WIN = 5202;      // 未中奖
    /**中奖未派奖*/
    int TICKET_NOT_AWARD = 5203;    // 中奖未派奖
    /**已派奖*/
    int TICKET_AWARDED = 12;        // 已派奖
    /**流标*/
    int TICKET_SCHEME_FLOW = 99;    // 流标
    /**撤单*/
    int TICKET_SCHEME_CANCEL = 100; // 撤单
    
    int TICKET_READY_FOR_HANDWORK=3;//等待人工处理出票
    int TICKET_EXPORTED = 4;//已导出到高速打印文件中
    
    int SCHEME_SALE = 60;//销售中
    
    // admin 中查询使用
    int SCHEME_FAIL_TO_SEND = 21;	// 方案状态为已出票（required）但是有“未成功接收”的ticket
    
    int TICKET_ACTUAL_STATUS_SUCCESS = 0;				// 实际接口返回值，0-成功接收
    int TICKET_ACTUAL_STATUS_ORDER_NO_RESPONSE = 9;		// 未能取得接口的“接票响应”，绝大部分情况是网络、接口服务中断造成的。
    
    /**正在出票中*/
    int TICKET_BUYING = 5102;       // 正在出票中
    /**未兑奖*/
    int TICKET_NOT_PRIZE = 5201;    // 未兑奖
    /**彩票不存在*/
    int TICKET_NOT_EXIST = 2004;    // 彩票不存在
    /**已接收出票请求*/
    int TICKET_ACCEPT = 101;        // 已接收出票请求
    
    
    
    int MATCH_STOP_SELLING = 0; // 停售
    int MATCH_ON_SALE = 1;      // 在售
    int MATCH_WAIT_SALE = 2;    // 待售
    int MATCH_PLAYING = 3;      // 进行中
    int MATCH_CANCEL = 4;       // 已取消
    int MATCH_OVER = 5;         // 已结束
    
    int GRANT_INIT = 0; //未审核
    int GRANT_AUDIT = 1; //已审核
    int GRANT_REJECT = 2; //已驳回
    
    int ID_CARD_INIT = 0;   //未审核
    int ID_CARD_PASS = 1;   //审核通过
    int ID_CARD_UNPASS = 2; //审核未通过
    
    //方案定制状态
    int CUSTOMMADE_STATUS_NO = 0; //未定制
    int CUSTOMMADE_STATUS_YES = 1; //已经定制
    
    int BETSCHEME_TYPE_BUY = 0; //方案类型-代购
    int BETSCHEME_TYPE_GROUPBUY = 1; //方案类型-合买
    int BETSCHEME_TYPE_FOLLOW  = 2; //方案类型-跟单    
    //远程出票
	String REMOTE_SUCCESS ="0"; //响应成功
	String REMOTE_WAIT="-110003";//出票中的
	String REMOTE_FAILURE="-110004";//出票失败(未扣款)
	String REMOTE_FALIURE_2="-110005";//出票失败(已扣款)
	String REMOTE_TICKET_SUCCESS="1";//成功出票
	
}
