package com.xhcms.lottery.lang;

public interface EntityType {

    // 资金流水单的交易类型
    int ORDER_RECHARGE = 100;   // 充值
    int ORDER_GRANT = 101;      // 赠款
    int ORDER_DEVIDE = 102;     // 派奖
    
    int ORDER_WITHDRAW_RETURN = 200;     // 提现返款
    int ORDER_WITHDRAW_FROZEN = 210;     // 提现冻结
    int ORDER_WITHDRAW_DEDUCT = 220;     // 提现扣款
    int SEND_REDENVALOPE_FROZEN = 230;   //发红包冻结
	int SEND_REDENVALOPE_FROZEN_RETURN = 240; //红包失效返款
    int GRAB_REDENVALOPE_TRANSFER = 250; //抢红包成功转账
    int ORDER_BET_RETURN = 300;         // 投注返款
    int ORDER_BET_FLOOR_RETURN = 301;   // 合买保底资金返款
    int ORDER_BET_FROZEN = 310;         // 投注冻结
    int ORDER_BET_FLOOR_FROZEN = 311;   // 合买保底资金冻结
    int ORDER_BET_DEDUCT = 320;         // 投注扣款
    int ORDER_BET_FLOOR_DEDUCT = 321;   // 合买保底资金扣款
    int ORDER_COMMISSION_ADD = 500;		// 累计得到的佣金
    
    //方案类型
    /** 0 代购*/
    int BETTING_ALONE = 0;
    /** 1 合买*/
    int BETTING_PARTNER = 1;
    /** 2 跟单*/
    int BETTING_FOLLOW = 2;
    
    /** 1 晒单 */
    int SHOW_SCHEME = 1;
    /** 0 不晒单*/
    int DONT_SHOW_SCHEME = 0;
    
    /**方案已被晒过*/
    int PUBLISH_SHOW = 1;
    /**方案未被晒过*/
    int DONT_PUBLISH_SHOW = 0;
    
    
    /** 0 晒单显示模式 */
    int DISPLAY_SHOW = 0;
    /** 1 代购不晒单显示模式*/
    int DISPLAY_ALONE = 1;
    /** 2 跟单显示模式*/
    int DISPLAY_FOLLOW = 2;
    /** 3 合买显示模式*/
    int DISPLAY_GROUPBUY = 3;
    
    int ID_CARD_REALNAME = 0;   //真实姓名
    int ID_CARD_IDNUMBER = 1;   //身份证号
    int ID_CARD_PASSWORD = 2;   //找回提款密码
    
    //下注类型
    /** 代购or晒单下注*/
    int BET_TYPE_ALONE = 0;
    /** 合买下注*/
    int BET_TYPE_PARTNER = 1;
    /** 跟单下注*/
    int BET_TYPE_FOLLOW = 2;
    /** 参与合买下注*/
    int BET_TYPE_JION_PARTNER = 3;
    
    //用户晒单战绩
    int SHOW_SCORE = 1;
    //合买战绩
    int GROUP_SCORE = 2;
    //合买流标战绩
    int GROUP_FAILURE_SCORE = 3;
    
    int TOP_USER_SCORE = 20;
    
    //选择方式
    /**手选**/
    int HAND_PICK = 0;
    /**胆拖**/
    int DAN_TUO = 1;
    /**机选**/
    int MACHINE_CHOOSE = 2;
    
    //充值返还彩金活动未提交
    int RECHARGE_REDEEMED_UNSUBMIT = 0;
    //充值返还彩金活动已提交
    int RECHARGE_REDEEMED_SUBMITED = 1;
}
