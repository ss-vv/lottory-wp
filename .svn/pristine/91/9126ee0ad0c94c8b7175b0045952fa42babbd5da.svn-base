package com.xhcms.lottery.utils;

import java.io.Serializable;

import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.PlayType;

public class AdminStatusTool implements Serializable {

    private static final long serialVersionUID = -9140060444830830857L;

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
                return "未付款";
            case EntityStatus.RECHARGE_NOT_AUDIT:
                return "已付款,待审核";
            case EntityStatus.RECHARGE_NOT_CONFIRM:
                return "已付款,待确认";
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
                return "待审核";
            case EntityStatus.WITHDRAW_AUDIT:
                return "已审核，待付款";
            case EntityStatus.WITHDRAW_PAYED:
                return "已付款，待确认";
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
                return "未出票";
            case EntityStatus.TICKET_ALLOW_BUY:
                return "可出票";
            case EntityStatus.TICKET_REQUIRED:
                return "已请求出票";
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
     * 投注方案销售状态
     * @param v 状态
     * @return 状态说明
     */
    public String sale(int v){
        switch(v){
            case EntityStatus.SCHEME_ON_SALE:
                return "可跟单";
            case EntityStatus.SCHEME_STOP:
                return "满员未扣款";
            case EntityStatus.SCHEME_SETTLEMENT:
                return "满员已扣款";
            default:
                return "未知状态";
        }
    }
    
    /**
     * 将期次状态码转换成期次名称
     * @param status 期次状态码
     * @return
     */
    public String issueName(int status) {
    	 switch(status) {
    	 	case 0:
    	 		return "未开售";
    	 	case 1:
    	 		return "销售中";
    	 	case 2:
    	 		return "已截止";
    	 	case 3:
    	 		return "已开奖";
    	 	case 4:
    	 		return "已派奖";
    	 }
    	 return "未知期状态";
    }
    
    /**
     * 玩法中文名称
     * @param play
     * @return
     */
    public String playName(String play){
    	PlayType pt = PlayType.valueOfLcPlayId(play);
    	if (pt==null){
    		return "未知玩法";
    	}else{
    		return pt.getPlayName();
    	}
    }
    
    /**
     * 赛事状态
     * @param v 状态
     * @return 状态说明
     */
    public String match(int v){
        switch(v){
            case EntityStatus.MATCH_STOP_SELLING:
                return "停售";
            case EntityStatus.MATCH_ON_SALE:
                return "在售";
            case EntityStatus.MATCH_WAIT_SALE:
                return "待售";
            case EntityStatus.MATCH_PLAYING:
                return "进行中";
            case EntityStatus.MATCH_CANCEL:
                return "取消";
            case EntityStatus.MATCH_OVER:
                return "已完成";
            default:
                return "未知状态";
        }
    }
    
    /**
     * 传统足彩赛事状态
     * @param v 状态
     * @return 状态说明
     */
    public String ctzcmatch(int v){
        switch(v){
            case Constants.ISSUE_STATUS_NOTSALE:
                return "未开售";
            case Constants.ISSUE_STATUS_SALE:
                return "销售中";
            case Constants.ISSUE_STATUS_END:
                return "已截止";
            case Constants.ISSUE_STATUS_SOLD:
                return "已开奖";
            case Constants.ISSUE_STATUS_AWARD:
                return "已派奖";
            default:
                return "未知状态";
        }
    }
    
    /**
     * 资金变更类型
     * @param v 类型
     * @return 类型说明
     */
    public String order(int v){
        switch(v){
            case EntityType.ORDER_RECHARGE:
                return "充值";
            case EntityType.ORDER_GRANT:
                return "赠款";
            case EntityType.ORDER_DEVIDE:
                return "派奖";
            case EntityType.ORDER_WITHDRAW_DEDUCT:
                return "提现扣款";
            case EntityType.ORDER_WITHDRAW_FROZEN:
                return "提现冻结";
            case EntityType.ORDER_WITHDRAW_RETURN:
                return "提现返款";
            case EntityType.ORDER_BET_FLOOR_DEDUCT:
                return "合买保底扣款";
            case EntityType.ORDER_BET_FLOOR_FROZEN:
                return "合买保底冻结";
            case EntityType.ORDER_BET_FLOOR_RETURN:
                return "合买保底返款";
            case EntityType.ORDER_BET_DEDUCT:
                return "投注扣款";
            case EntityType.ORDER_BET_FROZEN:
                return "投注冻结";
            case EntityType.ORDER_BET_RETURN:
                return "投注返款";
            case EntityType.ORDER_COMMISSION_ADD:
            	return "加佣金";
            case EntityType.SEND_REDENVALOPE_FROZEN:
            	return "发红包冻结";
            case EntityType.GRAB_REDENVALOPE_TRANSFER:
            	return "抢红包转账";
            case EntityType.SEND_REDENVALOPE_FROZEN_RETURN:
            	return "红包失效返款";
            case 0:
            	return "全部";
            default:
                return "未知类型";
        }
    }
    
    /**
     * 高频彩选择方式中文名称
     * @param type
     * @return
     */
    public String chooseTypeName(int type) {
    	switch (type) {
		case EntityType.HAND_PICK:
			return "手选";
		case EntityType.DAN_TUO:
			return "胆拖";
		case EntityType.MACHINE_CHOOSE:
			return "机选";
		default:
			return "未知选择方式";
		}
    }
    
}
