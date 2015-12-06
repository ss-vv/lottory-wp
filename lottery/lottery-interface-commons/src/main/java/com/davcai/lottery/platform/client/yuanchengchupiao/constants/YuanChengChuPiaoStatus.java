package com.davcai.lottery.platform.client.yuanchengchupiao.constants;

public class YuanChengChuPiaoStatus {
	/**回应成功*/
	public static int Success = 0;
	/**出票成功*/
	public static int Ticket_Success = 1;
	/**无效的方法*/
	public static int InvalidMethod = -110001;
	/**无效的订单*/
	public static int InvalidOrder = -110002;
	/**出票中*/
	public static int Wait = -110003;
	/**出票失败(未扣款)*/
	public static int Failure = -110004;
	/**出票失败(已扣款)*/
	public static int Faliure_2 = -110005;
	/**无效的API_ID*/
	public static int InvalidAPI_ID = -110006;
	/**无效的消息ID*/
	public static int InvalidMessageID = -110007;
	/**校验失败（校验码错误）*/
	public static int ValidateFailure = -110008;
	/**请求被拒绝*/
	public static int ReqRefused = -110009;
	/**彩票内容错误*/
	public static int ContentWrong = -110010;
	/**余额不足*/
	public static int InsufficientBalance = -110011;
	/**未知错误*/
	public static int UnknowError = -999999;
}
