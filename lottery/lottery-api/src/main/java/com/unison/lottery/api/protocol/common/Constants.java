package com.unison.lottery.api.protocol.common;

import java.math.BigDecimal;

public class Constants {
	
	public static final String X_UP_CALLING_LINE_ID = "x-up-calling-line-id";

	public static final String ORDER_DEF_NAME = "orderDef";
	public static final String RETURN_STATUS_NAME = "ReturnStatus";
	public static final String SET_USER_INVALID_STATUS = "userInvalidStatus";
	public static final String METHOD_REQUEST_NAME = "methodName";
	
	//手机号长度 
	public static final int PHONE_NO_LEN = 11;
	public static final String SPONSOR_NAME = "中国移动";
	public static final int CRBT_FEE = 600;
	public static final String INIT_INFO_NAME = "initInfo";
	public static final String QUERY_USER_INFO_NAME = "queryUserInfo";
	public static final String SEARCH_RESULT_LIST_NAME = "searchResultList";
	public static final String FRIEND_TONE_INFO_LIST_NAME = "friendToneInfoList"; 
	//普通铃音
	public static final String SPECIAL_TYPE_DEFAULT_TONE = "1";
	//特定主机铃音
	public static final String SPECIAL_TYPE_SPECIAL_TONE = "2";
	public static final String FRIEND_TONE_INFO_NAME = "friendToneInfo";
	public static final String SCORE_INFO_LIST_NAME = "scoreInfoList";
	public static final String FRIEND_GROUP_NAME = "friendGroup";
	public static final String FRIEND_PHONE_NUMBER = "friendPhoneNumber";
	public static final String ADD_STATUS = "addStatus";
	public static final String STATUS = "status";
	public static final String RECOMMEND_LIST = "recommendList";
	public static final int DEFAULT_RESULT_COUNT_PER_PAGE = 5;
	public static final String CONNECTION_EXCEPTION_NAME = "connetionException";
	public static final String CLIENT_VERSION_NAME = "clientVersion";
	public static final String TONE_TYPE_GENERALTONE_WITH_NUM = "1";
	public static final String VALIDID_NAME = "validId";
	public static final String USER_PHONE_NAME = "userPhone";
	public static final String USER_AGENT_NAME = "userAgent";
	public static final String CLIENT_VERSION_UNKNOWN = "N/A";
	public static final String CHANNEL_NAME = "channel";
	public static final String SHOW_TYPE_POP = "0";
	public static final String SHOW_TYPE_TEXT = "1";
	public static final String PROVINCE_CODE_NAME = "provinceCode";
	public static final String CITY_CODE_NAME = "cityCode";
	public static final String IS_NEW_USER_NAME = "isNewUser";
	public static final String UNKNOW_USER_PHONE = "N/A";
	public static final String WIND_WANE_RESULT_LIST_NAME = "toneList";
	public static final String INVITE_ID_NAME = "inviteId";
	public static final String CDATA_PREFIX = "<![CDATA[";
	public static final String CDATA_SUFFIX = "]]";
	public static final String ORDER_DESC = "订购";
	public static final String GIVE_DESC = "赠送";
	public static final String SEARCH_BY_CATEGORY_LIST_NAME = "typeSearchList";
	public static final String SEARCH_BY_CATEGORY_TONE_LIST_NAME = "typeSearchToneList";
	public static final String SEARCH_BY_CATEGORY_LIST_ITEM_NAME = "typeSearch";
	public static final String PARAM_LIST_NAME = "paramList";
	public static final String TONE_ID_NAME = "toneId";
	public static final String MODULE_NAME = "moduleName";
	public static final String TONE_TYPE_REINGBOX_WITH_NUM = "2";
	public static final int RINGBOX_CODE_LENGTH = 12;
	public static final int GENERALTONE_CODE_LENGTH = 18;
	public static final String CYB_PRODUCT_TYPE = "cyb";
	public static final String CLT_PRODUCT_TYPE = "clt";
	
	public static final String METHOD_RESPONSE_NAME_KEY = "responseName";
	public static final String DETAIL_LOG_NAME = "detailLog";
	public static final String CONNECTION_EXCEPTION_DESC = "网络异常";
	public static final String CONNECTION_EXCEPTION_STATUS_CODE = "9999999";
	public static final String REQUEST_GROUP_BY_PROVINCE = "省";
	public static final String GROUP_BY_PROVINCE = "province";
	public static final String REQUEST_GROUP_BY_CITY = "市";
	public static final String GROUP_BY_CITY = "city";
	public static final String SONG_LIST_NAME="songList";
	public static final String ALBUM_LIST_NAME="albumList";
	public static final String CHART_NAME_LIST_NAME="chartNameList";
	public static final String CHART_NAME="chartName";
	public static final String SONG_NAME="song";
	public static final String ALBUM_NAME="album";

	public static final String SEED_PARAMETER_NAME = "seed";

	public static final int PROTOCOL_DATA_EXCEPTION = 442;//通信协议加密或解密出现异常的状态码
	
	
	//分页时，每页最大结果数
	public static final int PAGING_MAX_RESULT = 20;
	
	//分页时，从第几条数据开始
	public static final String FIRST_RESULT = "first_result";
	
	//充值金额
	public static final String RECHARE_AMOUNT = "rechargeAmount";
	
	//彩种ID
	public static final String LOTTERY_ID ="lottery_id";

	//比赛数量
	public static final String MATCH_COUNT = "match_count";
	
	//期号
	public static final String ISSUE_NUMBER ="issue_number";
	
	//倒计时（用于高频彩）
	public static final String COUNT_DOWN_TIME = "count_down_time";
	
	//最近一次开奖期号
	public static final String LATEST_BALLOT_ISSUE_NUMBER = "LATEST_BALLOT_ISSUE_NUMBER";
	
	//最近一次开奖号码
	public static final String LATEST_BALLOT_LOTTERY_NUMBER = "LATEST_BALLOT_LOTTERY_NUMBER";
	
	public static final String LEAGUES= "leagues";
	
	public static final String COMMA_SEPARATOR = ",";
	
	//玩法id
	public static final String PLAY_ID="play_id";
	
	//每页记录数
	public static final String RESULT_COUNT_PER_PAGE="resultCountPerPage";
	
	//联赛简称
	public static final String LEAGUE_SHORT_NAME = "league_short_name";
	
	//比赛code 例如：周二001
	public static final String MATCH_CODE = "match_code";
	
	//联赛颜色
	public static final String LEAGUE_COLOR = "league_color";
	
	//比赛投注截止时间
	public static final String MATCH_OFF_TIME = "match_off_time"; 
	
	//大V彩-比赛投注截止时间
	public static final String ENTRUST_DEADLINE = "entrust_deadline"; 
		
	//主队名字
	public static final String HOST_NAME ="host_name";
	
	//客队名字
	public static final String GUEST_NAME = "guest_name";
	
	//赔率
	public static final String ODDS = "odds";
	
	//玩法选项
	public static final String OPTIONS = "options";

	//让分
	public static final String CONCEDE_POINTS = "concede_points";
	
	//大小分 
	public static final String CONCEDE_POINTS_DXF = "concede_points_dxf";
	
	//方案id
	public static final String SCHEME_ID = "scheme_id";
	
	//方案
	public static final String SCHEME = "scheme";

	//倍数
	public static final String MUTIPLE = "multiple";

	//注数
	public static final String BET_NOTE = "bet_NOTE";

	//过关方式
	public static final String PASS_TYPE = "pass_type";
	
	//投注的产生方式
	public static final String CHOOSE_TYPE = "chooseType";

	//是否晒单
	public static final String IS_SHOW = "is_show";

	//晒单类型
	public static final String SHOW_TYPE = "show_type";

	//赛事ID
	public static final String MATCH_ID = "match_id";

	//晒单提成
	public static final String FOLLOWED_RATIO = "followed_ratio";

	//投注内容
	public static final String BET_CONTENT ="bet_content";

	//投注类型
	public static final String BET_TYPE = "bet_type";

	//账户
	public static final String ACCOUNT = "account";

	//票
	public static final String TICKET = "ticket";
	
	//赛事数据
	public static final String MATCH_DATA = "matchData";
	
	//根据票信息拆分好的赛事数据
	public static final String SPLIT_MATCH_DATA = "split_match_data";

	//方案显示模式
	public static final String DISPLAY_MODE = "display_mode";
	
	//客户端投注时,多玩法之间以及多个投注内容之间的分割符
	public static final String CLIENT_CONTENT_SPLIT = "★";
	
	public static BigDecimal ZERO = new BigDecimal("0.00");
	
	public static final String BONUS_CODE = "bonus_code";
	
	//能被投注的期列表
	public static final String ISSUE_NUMBER_QUEUE = "issue_number_queue";
	
	//时间间隔队列
	public static final String TIME_INTERVAL_QUEUE = "timeIntervalQueue";
	
	public static final int TIME_10 = 10;
	
	public static final int TIME_22 = 22;
	
	public static final int TIME_2 = 2;
	
	//CQSS五分钟一期的间隔秒数
	public static final int CQSS_TIME_INTERVAL_FIVE_MINUTE = 300;
	
	//CQSS十分钟一期的间隔秒数
	public static final int CQSS_TIME_INTERVAL_TEN_MINUTE = 600;
	
	//江西十一选五每一期的间隔秒数
	public static final int JX11_TIME_INTERVAL_TEN_MINUTE = 600;
	
	//
	public static final String SPONSOR_AWARD = "sponsor_award";
	public static final String SUM_BET_AMOUNT = "sum_bet_amount";
	public static final String SUM_BONUS = "sum_bonus";

	
	public static final String SCHEME_FILTER = "schemeFilter";

	public static final String SPONSOR_COMMISSION = "sponsor_commission";

	public static final String MATCHES = "matches";
	
	
	/** 赛事颜色为空时，默认赛事颜色 */
	public static final String DEFAULT_MATCH_COLOR = "#339933";
	/** 赛事名称过长时，截取的最大长度 */
	public static final int	MAX_LEAGUE_SIZE = 5;
	
	//截止时间
	public static final String OFFTIME = "offtime";
	
	public static final String CTFB_SCHEMEDETAIL = "ctfb_schemeDetail";
	
	//方案金额
	public static final String TOTAL_AMOUNT = "total_amount";
	
	//JCZQ,JCLQ用户投注关联的所有赛事
	public static final String JC_BET_MATCH = "jc_bet_match";
	
	//数字彩的投注内容
	public static final String DIGITAL_BET_CONTENT = "digital_bet_content";
	/**
	 * 优惠劵类型
	 */
	public static final String VOUCHER_TYPE = "voucherType";
	/**
	 * 每页数据个数
	 */
	public static final String PERPAGE_COUNT = "perpageCount";
	/**
	 * 页码
	 */
	public static final String PAGE = "page";
	/**
	 * 用户优惠劵id
	 */
	public static final String VOUCHER_USER_ID = "voucherUserId";
	/**
	 * 客户端v群成员显示的个数
	 */
	public static final Integer TOTAL_VGROUP = 20;
	
	public static final Integer MAX_PAGES = 5;

	public static final String USER_ID = "userId";

	public static final String BUY_AMOUNT = "buyAmount";
	public static final String FLOOR_AMOUNT = "floorAmount";
	
}