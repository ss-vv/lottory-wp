package com.xhcms.lottery.utils;

import java.io.Serializable;

import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.EnumLotteryType;
import com.xhcms.lottery.lang.PlayType; 

public class StatusTool implements Serializable {

    private static final long serialVersionUID = -9140060444830830857L;

    /**
     * 方案类型
     * @param v 状态
     * @return 状态说明
     */
    public String type(int v){
    	switch(v){
    	case EntityType.BETTING_ALONE:
    		return "代购";
    	case EntityType.BETTING_PARTNER:
    		return "合买";
    	case EntityType.BETTING_FOLLOW:
    		return "跟单";
    	default:
    		return "未知";
    	}
    }
    
    /**
     * 方案保密状态
     * @param v 状态
     * @return 状态说明
     */
    public static String privacy(int v){
    	switch(v){
    	case EntityStatus.PRIVACY_PUBLIC:
    		return "公开";
    	case EntityStatus.PRIVACY_SOLD:
    		return "开奖后公开";
    	case EntityStatus.PRIVACY_FOLLOW_PUBLIC:
    		return "仅对跟单者公开（跟单后即公开）";
    	case EntityStatus.PRIVACY_FOLLOW:
    		return "仅对跟单者公开（销售截止后）";
    	case EntityStatus.PRIVACY_SECRET:
    		return "保密";
    	default:
    		return "未知状态";
    	}
    }
    
    /**
     * 充值状态
     * @param v 状态
     * @return 状态说明
     */
    public String recharge(int v){
        switch(v){
            case EntityStatus.RECHARGE_FINISH:
                return "充值成功";
            case EntityStatus.RECHARGE_NOT_PAY:
                return "用户未付款";
            case EntityStatus.RECHARGE_NOT_AUDIT:
            case EntityStatus.RECHARGE_NOT_CONFIRM:
                return "充值中";
            case EntityStatus.RECHARGE_FAIL:
                return "充值失败";
            default:
                return "未知状态";
        }
    }
    
    /**
     * 提现状态
     * @param v 状态
     * @return 状态说明
     */
    public String withdraw(int v){
        switch(v){
            case EntityStatus.WITHDRAW_INIT:
            case EntityStatus.WITHDRAW_AUDIT:
            case EntityStatus.WITHDRAW_PAYED:
                return "提现中";
            case EntityStatus.WITHDRAW_FINISH:
                return "已完成";
            case EntityStatus.WITHDRAW_REJECT:
                return "驳回";
            case EntityStatus.WITHDRAW_FAIL:
                return "提现失败";
            default:
                return "未知状态";
        }
    }
    
    /**
     * 投注方案状态
     * @param v 状态
     * @return 状态说明
     */
    public String bet(int v){
        switch(v){
            case EntityStatus.TICKET_INIT:
            case EntityStatus.TICKET_ALLOW_BUY:
            case EntityStatus.TICKET_REQUIRED:
                return "未出票";
            case EntityStatus.TICKET_BUY_SUCCESS:
                return "出票成功";
            case EntityStatus.TICKET_BUY_FAIL:
                return "出票失败";
            case EntityStatus.TICKET_NOT_WIN:
                return "未中奖";
            case EntityStatus.TICKET_NOT_AWARD:
                return "中奖未派奖";
            case EntityStatus.TICKET_AWARDED:
                return "已派奖";
            case EntityStatus.TICKET_SCHEME_FLOW:
                return "流标";
            case EntityStatus.TICKET_SCHEME_CANCEL:
                return "撤单";
            case EntityStatus.SCHEME_FAIL_TO_SEND:
            	return "未成功接收";
            case EntityStatus.TICKET_READY_FOR_HANDWORK:
            	return "等待人工处理出票";
            case EntityStatus.TICKET_EXPORTED:
            	return "已导出文件";
            default:
                return "未知状态";
        }
    }
    
    /**
     * 合买方案销售状态
     * @param v 状态
     * @return 状态说明
     */
    public String sale(int v){
        switch(v){
            case EntityStatus.SCHEME_ON_SALE:
                return "可参与";
            case EntityStatus.SCHEME_STOP:
            case EntityStatus.SCHEME_SETTLEMENT:
                return "已满员";
            default:
                return "未知状态";
        }
    }
    
    /**
     * 通过id取得玩法中文名称
     * @param play
     * @return
     */
    public String jx11PlayName(Integer playId){
    	 return PlayType.valueOfPlayName(playId);
    }
  
    
    /**
     * 通过long id 取得玩法名称
     * @param longPlayId
     * @return
     */
    public static String getPlayName(String longPlayId){
    	PlayType playType = PlayType.valueOfLcPlayId(longPlayId);
    	if(null == playType){
    		return "";
    	}
    	String playId = playType.getShortPlayStr();
    	return PlayType.getPlayNameByLongId(playId);
    }
    
    /**
     * 获取彩种中文名
     * 
     * @param lotteryId
     * @return
     */
    public String lotteryName(String lotteryId){
    	
    	return EnumLotteryType.getLotteryName(lotteryId);
    }
    
    
}
