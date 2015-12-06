package com.xhcms.lottery.lang;

import java.math.BigDecimal;

/**
 * @author xulang
 */
public interface Constants { 
	// 大V彩 lottery id
    String JCZQ = "JCZQ";   // 竞彩足球
    String JCLQ = "JCLQ";   // 竞彩篮球
    String JX11 = "JX11";   // 江西11选5
    String CTZC = "CTZC"; 	// 传统足彩
    String CQSS = "CQSS"; 	// 重庆时时彩
    String SSQ  = "SSQ"; 	// 双色球
    String FC3D  = "FC3D"; 	// 双色球
    String ZCZ = "-1"; 		//所有彩种
    String BJDC = "BJDC"; 	//北京单场
    String BDSF ="BDSF";    //北单胜负
    
    
    String BET_IN_SESSION = "bet.key";
    
    // 足球胜平负玩法
    String PLAY_01_ZC = "01_ZC";
    String PLAY_80_ZC = "80_ZC";
    String PLAY_02_ZC = "02_ZC";
    String PLAY_03_ZC = "03_ZC";
    String PLAY_04_ZC = "04_ZC";
    String PLAY_05_ZC = "05_ZC";
    String PLAY_555_ZCFH = "555_ZCFH";
    String PLAY_06_LC = "06_LC";
    String PLAY_07_LC = "07_LC";
    String PLAY_08_LC = "08_LC";
    String PLAY_09_LC = "09_LC";
    String PLAY_10_LC = "10_LC";
    String PLAY_666_LCFH = "666_LCFH";
	String PLAY_01_ZC_1 = "01_ZC_1";  // 胜平负单关
	String PLAY_01_ZC_2 = "01_ZC_2";  // 胜平负过关
	String PLAY_02_ZC_1 = "02_ZC_1";  // 比分单关
	String PLAY_02_ZC_2 = "02_ZC_2";  // 比分过关
	String PLAY_03_ZC_1 = "03_ZC_1";  // 总进球数单关
	String PLAY_03_ZC_2 = "03_ZC_2";  // 总计球数过关
	String PLAY_04_ZC_1 = "04_ZC_1";  // 胜负半全单关
	String PLAY_04_ZC_2 = "04_ZC_2";  // 胜负半全过关
	String PLAY_05_ZC_2 = "05_ZC_2";  // 混合过关
	String PLAY_555_ZCFH_2 = "555_ZCFH_2";  // 混合过关
	String PLAY_555_ZCFH_1 = "555_ZCFH_1";  // 混合过关
	String PLAY_80_ZC_1 = "80_ZC_1";  // 不让球胜平负单关
	String PLAY_80_ZC_2 = "80_ZC_2";  // 不让球胜平负过关
	
	
	String PLAY_06_LC_1 = "06_LC_1";  // 让分胜负单关
	String PLAY_06_LC_2 = "06_LC_2";  // 让分胜负过关
	String PLAY_07_LC_1 = "07_LC_1";  // 胜负单关
	String PLAY_07_LC_2 = "07_LC_2";  // 胜负过关
	String PLAY_08_LC_1 = "08_LC_1";  // 胜分差单关
	String PLAY_08_LC_2 = "08_LC_2";  // 胜分差过关
	String PLAY_09_LC_1 = "09_LC_1";  // 大小分单关
	String PLAY_09_LC_2 = "09_LC_2";  // 大小分过关
	String PLAY_10_LC_2 = "10_LC_2";  // 混合过关
	String PLAY_666_LCFH_2 = "666_LCFH_2";  // 混合过关
	String PLAY_666_LCFH_1 = "666_LCFH_1";  // 混合过关
	

	//北京单场
	String PLAY_01_BD_SPF = "91_BJDC_SPF";  // 胜平负单关

	String PLAY_02_BD_JQS = "92_BJDC_JQS";  // 进球数单关

	String PLAY_03_BD_SXDS = "93_BJDC_SXDS";  // 上下单双单关

	String PLAY_04_BD_BF = "94_BJDC_BF";  // 比分单关

	String PLAY_05_BD_BQC = "95_BJDC_BQC";  // 半全场单关

	//胜负
	String PLAY_06_BD_SF = "96_BJDC_SF";  // 胜负单关
	
	
	// 双色球玩法
	String PLAY_SSQ_DS  = "70_SSQ_DS"; // 双色球单式投注
	String PLAY_SSQ_FS  = "71_SSQ_FS"; // 双色球复式投注
	String PLAY_SSQ_DT  = "72_SSQ_DT"; // 双色球胆拖投注
	
	// 福彩3D玩法
	String PLAY_FC3D_ZXDS  = "97_FC3D_ZXDS";	//直选单式
	String PLAY_FC3D_ZXFS  = "98_FC3D_ZXFS";	//直选复式
	String PLAY_FC3D_ZXHZ  = "99_FC3D_ZXHZ";	//直选和值
	String PLAY_FC3D_ZX_DS  = "100_FC3D_ZX_DS";	//组选单式
	String PLAY_FC3D_Z3FS  = "101_FC3D_Z3FS";	//组三复式
	String PLAY_FC3D_Z3HZ  = "102_FC3D_Z3HZ";	//组三和值
	String PLAY_FC3D_Z6FS  = "103_FC3D_Z6FS";	//组六复式
	String PLAY_FC3D_Z6HZ  = "104_FC3D_Z6HZ";	//组六和值
	String PLAY_FC3D_DXBH  = "105_FC3D_DXBH";	//单选包号
	
	//高频彩玩法
	String PLAY_11_J5_R1 = "11_J5_R1";//江西11选5前选1玩法
	String PLAY_12_J5_R2 = "12_J5_R2";//江西11选5任选2玩法
	String PLAY_13_J5_R3 = "13_J5_R3";//江西11选5任选3玩法
	String PLAY_14_J5_R4 = "14_J5_R4";//江西11选5任选4玩法
	String PLAY_15_J5_R5 = "15_J5_R5";//江西11选5任选5玩法
	String PLAY_16_J5_R6 = "16_J5_R6";//江西11选5任选6玩法
	String PLAY_17_J5_R7 = "17_J5_R7";//江西11选5任选7玩法
	String PLAY_18_J5_R8 = "18_J5_R8";//江西11选5任选8玩法
	String PLAY_19_J5_D2 = "19_J5_D2";//江西11选5前二直选玩法
	String PLAY_20_J5_D3 = "20_J5_D3";//江西11选5前三直选玩法
	String PLAY_21_J5_G2 = "21_J5_G2";//江西11选5前二组选玩法
	String PLAY_22_J5_G3 = "22_J5_G3";//江西11选5前三组选玩法

	// 大V彩重庆时时彩玩法编码
	String PLAY_CQSS_DXDS     = "53_CQSS_DXDS";     // 重庆时时彩 - 大小单双
	String PLAY_CQSS_1X_DS    = "52_CQSS_1X_DS";    // 重庆时时彩 - 一星直选单式
	String PLAY_CQSS_2X_DS    = "43_CQSS_2X_DS";    // 重庆时时彩 - 二星直选单式
	String PLAY_CQSS_2X_FS    = "44_CQSS_2X_FS";    // 重庆时时彩 - 二星直选复式
	String PLAY_CQSS_2X_ZH    = "45_CQSS_2X_ZH";    // 重庆时时彩 - 二星组合
	String PLAY_CQSS_2X_HZ    = "46_CQSS_2X_HZ";    // 重庆时时彩 - 二星直选和值
	String PLAY_CQSS_2X_ZX_DS = "47_CQSS_2X_ZX_DS"; // 重庆时时彩 - 二星组选单式
	String PLAY_CQSS_2X_ZX_ZH = "48_CQSS_2X_ZX_ZH"; // 重庆时时彩 - 二星组选组合
	String PLAY_CQSS_2X_ZX_FZ = "49_CQSS_2X_ZX_FZ"; // 重庆时时彩 - 二星组选分组
	String PLAY_CQSS_2X_ZX_HZ = "50_CQSS_2X_ZX_HZ"; // 重庆时时彩 - 二星组选和值
	String PLAY_CQSS_2X_ZX_BD = "51_CQSS_2X_ZX_BD"; // 重庆时时彩 - 二星组选包胆
	String PLAY_CQSS_3X_DS    = "34_CQSS_3X_DS";    // 重庆时时彩 - 三星直选单式
	String PLAY_CQSS_3X_FS    = "35_CQSS_3X_FS";    // 重庆时时彩 - 三星直选复式
	String PLAY_CQSS_3X_ZH    = "36_CQSS_3X_ZH";    // 重庆时时彩 - 三星组合
	String PLAY_CQSS_3X_ZH_FS = "37_CQSS_3X_ZH_FS"; // 重庆时时彩 - 三星组合复式
	String PLAY_CQSS_3X_HZ    = "38_CQSS_3X_HZ";    // 重庆时时彩 - 三星直选和值
	String PLAY_CQSS_3X_Z3_DS = "54_CQSS_3X_Z3_DS"; // 重庆时时彩 - 三星组三单式
	String PLAY_CQSS_3X_Z3_FS = "39_CQSS_3X_Z3_FS"; // 重庆时时彩 - 三星组三复式
	String PLAY_CQSS_3X_Z6_DS = "55_CQSS_3X_Z6_DS"; // 重庆时时彩 - 三星组六单式
	String PLAY_CQSS_3X_Z6_FS = "40_CQSS_3X_Z6_FS"; // 重庆时时彩 - 三星组六复式
	String PLAY_CQSS_3X_ZX_HZ = "41_CQSS_3X_ZX_HZ"; // 重庆时时彩 - 三星组选合值
	String PLAY_CQSS_3X_ZX_BD = "42_CQSS_3X_ZX_BD"; // 重庆时时彩 - 三星组选包胆
	String PLAY_CQSS_5X_TX    = "30_CQSS_5X_TX";    // 重庆时时彩 - 五星通选
	String PLAY_CQSS_5X_DS    = "31_CQSS_5X_DS";    // 重庆时时彩 - 五星直选单式
	String PLAY_CQSS_5X_FS    = "32_CQSS_5X_FS";    // 重庆时时彩 - 五星直选复式
	String PLAY_CQSS_5X_ZH    = "33_CQSS_5X_ZH";    // 重庆时时彩 - 五星组合
	
	
	String SHORT_PLAYID_4_JX11X5 = "J5";
	
	//传统足彩玩法
	String PLAY_24_ZC_14 = "24_ZC_14";//胜平负14场
	String PLAY_25_ZC_R9 = "25_ZC_R9";//胜负任9
	String PLAY_26_ZC_BQ= "26_ZC_BQ";//半全6场
	String PLAY_27_ZC_JQ = "27_ZC_JQ";//进球4场
	
	
	int PLAY_01 = 1;
	int PLAY_02 = 2;
	int PLAY_03 = 3;
	int PLAY_04 = 4;
	
	int PLAY_06 = 6;
	int PLAY_07 = 7;
	int PLAY_08 = 8;
	int PLAY_09 = 9;
	int PLAY_80 = 80;	// 玩法竞彩足球不让分胜平负
	//北京单场
	int PLAY_100=1;   //北单胜平负
	int PLAY_101=2;   //北单进球数
	int PLAY_102=3;   //北单上下单双
	int PLAY_103=4;   //北单比分
	int PLAY_104=5;   //北单半全场
	int PLAY_105=6;   //北单胜负
	
	String WORKDAY_STARTTIME = "9:00:00";
	String WORKDAY_ENDTIME = "22:55:00";
	String WEEKEND_STARTTIME = "9:00:00";
	String WEEKEND_ENDTIME = "0:55:00";
	
	BigDecimal ZERO = new BigDecimal("0.00");
	
	// 一周对应的毫秒数
	long ONE_WEEK = 86400000L * 7;
	
	int FUND = 10;
	int GRANT = 12;
	int FROZEN_FUND = 20;
	int FROZEN_GRANT = 22;
	int FREE = 30;
	int TOTAL_BET = 41;
	int TOTAL_AWARD = 42;
	int TOTAL_WITHDRAW = 43;
	int TOTAL_RECHARGE = 44;
	int TOTAL_COMMISSION = 45;
	
	// 晒单跟单
	int RECOMMEND = 1;
	int NOT_RECOMMEND = 0;
	
	int SHOW_ALL = -1;		// 所有
	/**晒单*/
	int SHOW_SCHEME = 1;
	/**不晒单*/
	int NOT_SHOW_SCHEME = 0;
	
	// 中民新接口定义的交易状态，交易结果状态
	final int ZM_TICKET_NOT_EXIST = 0;		// 订单不存在 
	final int ZM_TICKET_WAIT = 1;			// 等待交易 
	final int ZM_TICKET_IN_TRANSACTION = 2;	// 交易中
	final int ZM_TICKET_SUCCESS = 3;		// 交易成功
	final int ZM_TICKET_FAIL = 4;			// 交易失败
	
	// 中民新接口，中奖状态
	final int ZM_PRIZE_CANCEL  = 0;			// 撤单
	final int ZM_PRIZE_NOT_YET = 1;			// 未开奖
	final int ZM_PRIZE_WIN     = 2;			// 已中奖
	final int ZM_PRIZE_NOT_WIN = 3;			// 未中奖
	
	
	//TODO 需要重构和EntityType重复
	int TYPE_ALL = -1; // 所有
	int TYPE_BUY = 0;    //代购
	int TYPE_GROUP = 1;  //合买
	int TYPE_FOLLOW = 2; //跟单
	
	//User Accout locked Status
	int ISLOCKED = 1;		//用户被锁定状态值
	int UNLOCKED = 0;		//标志用户未被锁定
	
	// 中民新接口，竞彩lotteryId
	final String ZM_LOTTERY_ZQ_JCSPF = "JCSPF";		// 竞彩足球胜平负
	final String ZM_LOTTERY_ZQ_JCBF = "JCBF";		// 竞彩足球比分
	final String ZM_LOTTERY_ZQ_JCJQS = "JCJQS";		// 竟彩足球进数
	final String ZM_LOTTERY_ZQ_JCBQC = "JCBQC";		// 竟彩 足球半全场
	final String ZM_LOTTERY_ZQ_JCHH = "JCZQFH";		// 竟彩 足球混合过关
	final String ZM_LOTTERY_LQ_JCSF = "JCSF";		// 竟彩篮球胜负
	final String ZM_LOTTERY_LQ_JCRFSF = "JCRFSF";	// 竟彩篮球让分胜负
	final String ZM_LOTTERY_LQ_JCFC = "JCFC";		// 竟彩篮球胜分差
	final String ZM_LOTTERY_LQ_JCDXF = "JCDXF";		// 竟彩篮球大小分
	final String ZM_LOTTERY_LQ_JCHH = "JCLQFH";		// 竟彩篮球混合过关
	
	
	//中民新接口，江西11选5的lotteryId
	String ZM_LOTTERYID_JX11X5 = LotteryIdForZM.JX11.getName();
	//中民新接口，重庆时时彩的lotteryId
	String ZM_LOTTERYID_CQSSC = "ZQSSC";
	
	//中民新接口，双色球的lotteryId
	String ZM_LOTTERYID_SSQ = "SSQ";
	
	// 中民新接口，传统足彩 lotteryId
	String ZM_LOTTERYID_14CSF = LotteryIdForZM.SF14.getName();
	String ZM_LOTTERYID_SFR9  = LotteryIdForZM.SFR9.getName();
	String ZM_LOTTERYID_6CBQ  = LotteryIdForZM.BQ6C.getName();
	String ZM_LOTTERYID_4CJQ  = LotteryIdForZM.JQ4C.getName();
	
	//中民接口，双色球玩法id
	String ZM_BETTYPE_SSQ_DS = "DS"; // 双色球单式投注
	String ZM_BETTYPE_SSQ_FS = "FS"; // 双色球复式投注
	String ZM_BETTYPE_SSQ_DT = "DT"; // 双色球胆拖投注
	
	//中民接口，福彩3D玩法id
	String ZM_BETTYPE_FC3D_ZXDS = "ZXDS";
	String ZM_BETTYPE_FC3D_ZXFS = "ZXFS";
	String ZM_BETTYPE_FC3D_ZXHZ = "ZXHZ";
	String ZM_BETTYPE_FC3D_ZX_DS = "ZX_DS";
	String ZM_BETTYPE_FC3D_Z3FS = "Z3FS";
	String ZM_BETTYPE_FC3D_Z3HZ = "Z3HZ";
	String ZM_BETTYPE_FC3D_Z6FS = "Z6FS";
	String ZM_BETTYPE_FC3D_Z6HZ = "Z6HZ";
	String ZM_BETTYPE_FC3D_DXBH = "DXBH";
	
	//中民新接口，江西11选5玩法id
	String ZM_BETTYPE_11_RX_1 = "11_RX1";//11前选1
	String ZM_BETTYPE_11_RX_2 = "11_RX2";//11任选2
	String ZM_BETTYPE_11_RX_3 = "11_RX3";//11任选3
	String ZM_BETTYPE_11_RX_4 = "11_RX4";//11任选4
	String ZM_BETTYPE_11_RX_5 = "11_RX5";//11任选5
	String ZM_BETTYPE_11_RX_6 = "11_RX6";//11任选6
	String ZM_BETTYPE_11_RX_7 = "11_RX7";//11任选7
	String ZM_BETTYPE_11_RX_8 = "11_RX8";//11任选8
	String ZM_BETTYPE_11_ZHIXQ2 = "11_ZXQ2";//11直选前2
	String ZM_BETTYPE_11_ZHIXQ3 = "11_ZXQ3";//11直选前3
	String ZM_BETTYPE_11_ZUXQ2 = "11_ZXQ2";//11组选前2
	String ZM_BETTYPE_11_ZUXQ3 = "11_ZXQ3";//11组选前3


	// 中民新接口 - 重庆时时彩玩法编码
	String ZM_BETTYPE_CQSS_DXDS     = "ZQSSC_DXDS";     // 重庆时时彩 - 大小单双
	String ZM_BETTYPE_CQSS_1X_DS    = "ZQSSC_1X_DS";    // 重庆时时彩 - 一星直选单式
	String ZM_BETTYPE_CQSS_2X_DS    = "ZQSSC_2X_DS";    // 重庆时时彩 - 二星直选单式
	String ZM_BETTYPE_CQSS_2X_FS    = "ZQSSC_2X_FS";    // 重庆时时彩 - 二星直选复式
	String ZM_BETTYPE_CQSS_2X_ZH    = "ZQSSC_2XZH";     // 重庆时时彩 - 二星组合
	String ZM_BETTYPE_CQSS_2X_HZ    = "ZQSSC_2XHZ";     // 重庆时时彩 - 二星直选和值
	String ZM_BETTYPE_CQSS_2X_ZX_DS = "ZQSSC_2XZX_DS";  // 重庆时时彩 - 二星组选单式
	String ZM_BETTYPE_CQSS_2X_ZX_ZH = "ZQSSC_2XZXZH";   // 重庆时时彩 - 二星组选组合
	String ZM_BETTYPE_CQSS_2X_ZX_FZ = "ZQSSC_2XZXFZ";   // 重庆时时彩 - 二星组选分组
	String ZM_BETTYPE_CQSS_2X_ZX_HZ = "ZQSSC_2XZXHZ";   // 重庆时时彩 - 二星组选和值
	String ZM_BETTYPE_CQSS_2X_ZX_BD = "ZQSSC_2XZX_BD";  // 重庆时时彩 - 二星组选包胆
	String ZM_BETTYPE_CQSS_3X_DS    = "ZQSSC_3X_DS";    // 重庆时时彩 - 三星直选单式
	String ZM_BETTYPE_CQSS_3X_FS    = "ZQSSC_3X_FS";    // 重庆时时彩 - 三星直选复式
	String ZM_BETTYPE_CQSS_3X_ZH    = "ZQSSC_3XZH";     // 重庆时时彩 - 三星组合
	String ZM_BETTYPE_CQSS_3X_ZH_FS = "ZQSSC_3XZH_FS";  // 重庆时时彩 - 三星组合复式
	String ZM_BETTYPE_CQSS_3X_HZ    = "ZQSSC_3XHZ";     // 重庆时时彩 - 三星直选和值
	String ZM_BETTYPE_CQSS_3X_Z3_DS = "ZQSSC_3XZ3_DS";  // 重庆时时彩 - 三星组三单式
	String ZM_BETTYPE_CQSS_3X_Z3_FS = "ZQSSC_3XZ3_FS";  // 重庆时时彩 - 三星组三复式
	String ZM_BETTYPE_CQSS_3X_Z6_DS = "ZQSSC_3XZ6_DS";  // 重庆时时彩 - 三星组六单式
	String ZM_BETTYPE_CQSS_3X_Z6_FS = "ZQSSC_3XZ6_FS";  // 重庆时时彩 - 三星组六复式
	String ZM_BETTYPE_CQSS_3X_ZX_HZ = "ZQSSC_3XZXHZ";   // 重庆时时彩 - 三星组选和值
	String ZM_BETTYPE_CQSS_3X_ZX_BD = "ZQSSC_3XZX_BD";  // 重庆时时彩 - 三星组选包胆
	String ZM_BETTYPE_CQSS_5X_TX    = "ZQSSC_5XTX";     // 重庆时时彩 - 五星通选
	String ZM_BETTYPE_CQSS_5X_DS    = "ZQSSC_5X_DS";    // 重庆时时彩 - 五星直选单式
	String ZM_BETTYPE_CQSS_5X_FS    = "ZQSSC_5X_FS";    // 重庆时时彩 - 五星直选复式
	String ZM_BETTYPE_CQSS_5X_ZH    = "ZQSSC_5XZH";     // 重庆时时彩 - 五星组合
	
	// 中民传统足彩玩法id
	String ZM_BETTYPE_CTZC_DS = "DS";	// 单式
	String ZM_BETTYPE_CTZC_FS = "FS";	// 复式
	
	final int MONEY_PER_NOTE = 2;	// 一注2元
	
	// 传统足彩期状态
	int ISSUE_STATUS_NOTSALE = 0; // 未开售
	int ISSUE_STATUS_SALE = 1; // 销售中
	int ISSUE_STATUS_END = 2; // 已截止
	int ISSUE_STATUS_SOLD = 3; // 已开奖
	int ISSUE_STATUS_AWARD = 4; // 已派奖
	
	// 每票最大钱数
	int MAX_MONEY_PER_TICKET = 20000;
	
	// 活动计算次数
	/** 不计次数 */
	int  NOT_COMPUTE = 0;
	/** 每天 */
	int  EVERYDAY = 1;
	/** 每月 */
	int  EVERYMONTH = 2;
	
	/** 赛事颜色为空时，默认赛事颜色 */
	public static final String DEFAULT_MATCH_COLOR = "#339933";
	
	//奖金明细
	public static final String BONUS_DETAIL = "bonusDetail";
	
	final Long SHOW_FOLLOW_PROMOTION_ID = 39L;
	
	final String DEFAULT_HEAD_IMG = "http://www.davcai.com/images/default_face.jpg";
	String MATCH_ID = "match_id";
	String MATCH_CODE = "match_code";
	String LEAGUE_SHORT_NAME = "league_short_name";
	String LEAGUE_COLOR = "league_color";
	String MATCH_OFF_TIME = "match_off_time";
	String ENTRUST_DEADLINE = "entrust_deadline";
	String HOST_NAME = "host_name";
	String GUEST_NAME = "guest_name";
	String CONCEDE_POINTS = "concede_points";
	String ODDS = "odds";
	String OPTIONS = "options";
	String ALL_PLAY_ID = "all";
}
