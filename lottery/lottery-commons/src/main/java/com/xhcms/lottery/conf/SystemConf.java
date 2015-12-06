package com.xhcms.lottery.conf;

public interface SystemConf {

	String KEY_INTERVAL_IN_MINUTE_4_ZM_CLOSETIME = "interval_minute_for_zm_close_time";
	String KEY_INTERVAL_IN_MINUTE_4_LC_STOPTIME = "interval_minute_for_lc_stop_time";
	String KEY_BEFORE_CLOSE_MINUTE ="before_close_minute";
	String KEY_BET_TOTAL_AMOUNT_LIMIT = "bet_total_amount_limit";
	
	class FETCH_MATCH {
		/**校对尊奥与球探赛事通过开赛时间关联不到的问题**/
		public static final String INTERVAL_FOR_QT_MATCH_JOIN = "interval_for_qt_match_join";
	}
	
	/**接口提前关闭时间 单位秒*/
	class CLOSETIME{
		public static final String BET_FOR_PASSTYPE_CLOSE_TIME = "bet_for_passtype_close_time";
		
		/**传统足彩  大V彩提前截止时间*/
		public static final String CTZC_LC_AHEAD_SECOND= "ctzc_lc_ahead_second";
		
		/**传统足彩  大V彩提前截止时间*/
		public static final String SSQ_LC_AHEAD_SECOND= "ctzc_lc_ahead_second";
		/**
		 * 北京单场 大V彩提前截止时间
		 */
		public static final String BJDC_BEFORE_CLOSE_SECOND="bjdc_before_close_second";
		/**
		 * 通过彩客获取的传统足球期信息设置stop_time的时间间隔
		 */
		public static final String CTZC_310WIN_STOP_TIME_SECOND="ctzc_310win_stop_time_second";
		/**
		 * 设置本地入库close_time 相对彩客传统足球close_time的时间间隔
		 */
		public static final String CZTC_310WIN_CLOSE_TIME_SECOND="ctzc_310win_close_time_second";
	}
	

	/**出票*/
	class ISSUE{
		/**延长出票时间*/
		public static final String EXTEND_SECOND = "issue_extend_second";
	}
	
	/**下单提前截至时间*/
	class BETTIME{
		/**合买全部玩法默认提前截至时间*/
		public static final String GROUPBUY_DEFAULT_AHEAD_SECOND = "groupbuy_default_ahead_second";
	}
	//传统足彩 获取源
    class CTZC_PLATFORM{
    	public static final String GET_CTZC_DATA_PLATFORM="get_ctzc_data_platform";
    }
	

	Integer getIntegerValueByKey(String key);
	
	Long getLongValueByKey(String key);

	public String findValueByKey(String key);
	
	/**
	 * 是否允许自行注册
	 * @return
	 */
	boolean isAllowSelfRegiste();

	/**
	 * 当前是否可以发送监控邮件
	 * @return
	 */
	boolean canSendMonitorEmail();

	/**
	 * 是否需要对大额票进行特殊分配
	 * @return
	 */
	boolean shouldArrangeBigTicket();

	/**
	 * 大额票投注额阙值
	 * @return
	 */
	Integer getBigTicketMoneyThreshold();

	/**
	 * 需要升级的实体店
	 * @return
	 */
	String getShouldUpgradeShiTiDians();
	/**
	 * 更新配置Int值
	 * @param key
	 * @param value
	 */
	void updateLongValueByKey(String key,String value);
	
	/**
	 * 更新配置String值
	 * @param key
	 * @param value
	 */
	void updateStrinfValueByKey(String key,String value);

}
