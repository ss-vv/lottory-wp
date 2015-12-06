package com.xhcms.lottery.pb.model;

import java.util.HashMap;
import java.util.Map;

public final class Constants {
	public static final String API_VERSION = "1.0";
	public static final String CHANNEL = "Partner";
	
	public static final String MSG_TYPE = "msg-type";
	public static final String PARTNER_ID = "partner-id";
	public static final String SIGNATURE = "signature";
	
	public static final String BODY_CONTENT = "BODY_CONTENT";
	public static final String REQUESR_MSG = "REQUESR_MSG";
	
	public static Map<String,String> PARTNER_KEYS = new HashMap<String, String>();
	public static Map<String,Map<String,String>> PARTNER_URLS = new HashMap<String, Map<String,String>>();
	
	static {
		PARTNER_KEYS.put("youyuanwang", "!@8W6#2E52$43DS4%1^");
		
		Map<String,String> youyuanwangUrlMap = new HashMap<String, String>();
		youyuanwangUrlMap.put(MESSAGE_TYPE.DRAW_TICKET_REQ.getCode(), "http://baidu.com");
		youyuanwangUrlMap.put(MESSAGE_TYPE.WIN_INFO_REQ.getCode(), "http://baidu.com");
		youyuanwangUrlMap.put(MESSAGE_TYPE.WITHDRAW_RESULT_REQ.getCode(), "http://baidu.com");
		PARTNER_URLS.put("youyuanwang", youyuanwangUrlMap);
	}
	
	//商户投注结果
	public static final int BET_SUCCESS = 0;
	public static final int BET_BALANCE_NOT_ENOUGH = 1;
	public static final int BET_ISSUE_ERROR = 2;
	public static final int BET_SYS_ERROR = 3;
	public static final int BET_MONEY_ERROR = 4;
	public static final int BET_UUID_ERROR = 5;
	public static final int BET_OTHER_ERROR = 9;
	
	//推送通知的最大条数
	public static final int RESULT_MAX_SIZE = 10;

	//出票状态
	public static final int TICKET_DRAW_SUCCESS = 1;
	public static final int TICKET_DRAW_FAILURE = 0;
	
	//出票通知接收状态
	/**出票通知接收成功*/
	public static final int TICKET_DRAW_NOTIFY_REV_SUCCESS = 1;
	/**出票通知接收失败*/
	public static final int TICKET_DRAW_NOTIFY_REV_FAIL = 0;
	
	//中奖状态
	public static final int TICKET_WIN = 1;
	public static final int TICKET_NOT_WIN = 0;
	
	//中奖通知接收状态
	public static final int TICKET_WIN_NOTIFY_REV_SUCCESS = 1;
	public static final int TICKET_WIN_NOTIFY_REV_FAIL = 0;
	
	//提现请求接收状态
	public static final int WITHDRAW_REQ_RCV_SUCCESS = 1;
	public static final int WITHDRAW_REQ_RCV__FAIL = 0;

	//提现结果请求接收状态
	public static final int WITHDRAW_RESULT_REQ_RCV_SUCCESS = 1;
	public static final int WITHDRAW_RESULT_REQ_RCV__FAIL = 0;
	
	//提现状态
	public static final int WITHDRAW_SUCCESS = 1;
	public static final int WITHDRAW_FAIL = 0;
	

	//商户投注状态：0 等待出票交易结果通知；1 出票通知成功；2 开奖通知成功
	/**等待出票通知*/
	public static final int WAIT_DRAW_TICKET_INFORM = 0;
	/**出票已通知*/
	public static final int DRAW_TICKET_HAVE_INFORMED = 1;
	/**开奖已通知*/
	public static final int WIN_TICKET_HAVE_INFORMED = 2;
	
	//商户提现状态
	/**0 提现结果待通知*/
	public static final int WAIT_WITHDRAW_RESULT_INFORM = 0;
	/**1 提现结果通知成功*/
	public static final int WITHDRAW_RESULT_HAVE_INFORMED = 1;
}
