package com.xhcms.lottery.lang;

import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 彩票玩法种类枚举。
 * 
 * @author Yang Bo
 */
public enum PlayType {
	
	// 竞彩足球胜平负
	JCZQ_SPF(1, "01_ZC","让球胜平负", LotteryId.JCZQ),
	JCZQ_BRQSPF(80, "80_ZC","胜平负", LotteryId.JCZQ),
	// 竞彩足球比分
	JCZQ_BF(2, "02_ZC","比分", LotteryId.JCZQ),	
	// 竞彩足球总进球数
	JCZQ_ZJQS(3, "03_ZC","总进球数", LotteryId.JCZQ),
	// 竞彩足球半全场
	JCZQ_BQC(4, "04_ZC","半全场", LotteryId.JCZQ),
	// 竞彩混合过关
	JCZQ_HH(5, "05_ZC","混合过关", LotteryId.JCZQ),
	
	// 竞彩篮球让分胜负
	JCLQ_RFSF(6, "06_LC","让分胜负", LotteryId.JCLQ),
	// 竞彩篮球胜负
	JCLQ_SF(7, "07_LC","胜负", LotteryId.JCLQ),
	// 竞彩篮球胜分差
	JCLQ_SFC(8, "08_LC","胜分差", LotteryId.JCLQ),
	// 竞彩篮球大小分
	JCLQ_DXF(9, "09_LC","大小分", LotteryId.JCLQ),
	// 竞彩混合过关
	JCLQ_HH(10, "10_LC","混合过关", LotteryId.JCLQ),
	
	JCZQ_FH(555, "555_ZCFH","混合过关", LotteryId.JCZQ),
	JCLQ_FH(666, "666_LCFH","混合过关", LotteryId.JCLQ),
	
	//江西11选5任选1
	JX11_R1(11,"11_J5_R1","任选一", LotteryId.JX11),
	//江西11选5任选2
	JX11_R2(12,"12_J5_R2","任选二", LotteryId.JX11),
	//江西11选5任选3
	JX11_R3(13,"13_J5_R3","任选三", LotteryId.JX11),
	//江西11选5任选4
	JX11_R4(14,"14_J5_R4","任选四", LotteryId.JX11),
	//江西11选5任选5
	JX11_R5(15,"15_J5_R5","任选五", LotteryId.JX11),
	//江西11选5任选6
	JX11_R6(16,"16_J5_R6","任选六", LotteryId.JX11),
	//江西11选5任选7
	JX11_R7(17,"17_J5_R7","任选七", LotteryId.JX11),
	//江西11选5任选8
	JX11_R8(18,"18_J5_R8","任选八", LotteryId.JX11),
	//江西11选5直选前2
	JX11_D2(19,"19_J5_D2","前二直选",LotteryId.JX11),
	//江西11选5直选前3
	JX11_D3(20,"20_J5_D3","前三直选",LotteryId.JX11),
	//江西11选5组选前2
	JX11_G2(21,"21_J5_G2","前二组选",LotteryId.JX11),
	//江西11选5组选前3
	JX11_G3(22,"22_J5_G3","前三组选",LotteryId.JX11),
	
	CTZC_14(24, "24_ZC_14", "14场胜负", LotteryId.CTZC),
	CTZC_R9(25, "25_ZC_R9", "胜负任九", LotteryId.CTZC),
	CTZC_BQ(26, "26_ZC_BQ", "6场半全", LotteryId.CTZC),
	CTZC_JQ(27, "27_ZC_JQ", "4场进球", LotteryId.CTZC),
	
	CQSS_5X_TX(30, "30_CQSS_5X_TX", "五星通选", LotteryId.CQSS), 
	CQSS_5X_DS(31, "31_CQSS_5X_DS", "五星单式", LotteryId.CQSS), 
	CQSS_5X_FS(32, "32_CQSS_5X_FS", "五星复式", LotteryId.CQSS), 
	CQSS_5X_ZH(33, "33_CQSS_5X_ZH", "五星组合", LotteryId.CQSS), 
	CQSS_3X_DS(34, "34_CQSS_3X_DS", "三星单式", LotteryId.CQSS), 
	CQSS_3X_FS(35, "35_CQSS_3X_FS", "三星复式", LotteryId.CQSS), 
	CQSS_3X_ZH(36, "36_CQSS_3X_ZH", "三星组合", LotteryId.CQSS), 
	CQSS_3X_ZH_FS(37, "37_CQSS_3X_ZH_FS", "三星组合复式", LotteryId.CQSS), 
	CQSS_3X_HZ(38, "38_CQSS_3X_HZ", "三星直选和值", LotteryId.CQSS), 
	CQSS_3X_Z3_FS(39, "39_CQSS_3X_Z3_FS", "三星组三复式", LotteryId.CQSS), 
	CQSS_3X_Z6_FS(40, "40_CQSS_3X_Z6_FS", "三星组六复式", LotteryId.CQSS), 
	CQSS_3X_ZX_HZ(41, "41_CQSS_3X_ZX_HZ", "三星组选合值", LotteryId.CQSS), 
	CQSS_3X_ZX_BD(42, "42_CQSS_3X_ZX_BD", "三星组选包胆", LotteryId.CQSS), 
	CQSS_2X_DS(43, "43_CQSS_2X_DS", "二星直选单式", LotteryId.CQSS), 
	CQSS_2X_FS(44, "44_CQSS_2X_FS", "二星直选复式", LotteryId.CQSS), 
	CQSS_2X_ZH(45, "45_CQSS_2X_ZH", "二星组合", LotteryId.CQSS), 
	CQSS_2X_HZ(46, "46_CQSS_2X_HZ", "二星直选和值", LotteryId.CQSS), 
	CQSS_2X_ZX_DS(47, "47_CQSS_2X_ZX_DS", "二星组选单式", LotteryId.CQSS), 
	CQSS_2X_ZX_ZH(48, "48_CQSS_2X_ZX_ZH", "二星组选组合", LotteryId.CQSS), 
	CQSS_2X_ZX_FZ(49, "49_CQSS_2X_ZX_FZ", "二星组选分组", LotteryId.CQSS), 
	CQSS_2X_ZX_HZ(50, "50_CQSS_2X_ZX_HZ", "二星组选和值", LotteryId.CQSS), 
	CQSS_2X_ZX_BD(51, "51_CQSS_2X_ZX_BD", "二星组选包胆", LotteryId.CQSS), 
	CQSS_1X_DS(52, "52_CQSS_1X_DS", "一星单式", LotteryId.CQSS), 
	CQSS_DXDS(53, "53_CQSS_DXDS", "大小单双", LotteryId.CQSS), 
	CQSS_3X_Z3_DS(54, "54_CQSS_3X_Z3_DS", "三星组三单式", LotteryId.CQSS), 
	CQSS_3X_Z6_DS(55, "55_CQSS_3X_Z6_DS", "三星组六单式", LotteryId.CQSS), 
	
	SSQ_DS(70, "70_SSQ_DS", "单式", LotteryId.SSQ),
	SSQ_FS(71, "71_SSQ_FS", "复式", LotteryId.SSQ),
	SSQ_DT(72, "72_SSQ_DT", "胆拖", LotteryId.SSQ),
	
	JCSJBGJ(90, "jcsjbgj", "世界杯冠军", LotteryId.JCZQ),
	
	//北单玩法类型
	BJDC_SPF(91, "91_BJDC_SPF","让球胜平负", LotteryId.BJDC),
	BJDC_JQS(92, "92_BJDC_JQS","进球数", LotteryId.BJDC),
	BJDC_SXDS(93, "93_BJDC_SXDS","上下单双", LotteryId.BJDC),
	BJDC_BF(94, "94_BJDC_BF","比分", LotteryId.BJDC),
	BJDC_BQC(95, "95_BJDC_BQC","半全场", LotteryId.BJDC),
	BJDC_SF(96, "96_BJDC_SF","胜负过关", LotteryId.BDSF),
	
	FC3D_ZXDS(97, "97_FC3D_ZXDS", "直选单式", LotteryId.FC3D),
	FC3D_ZXFS(98, "98_FC3D_ZXFS", "直选复式", LotteryId.FC3D),
	FC3D_ZXHZ(99, "99_FC3D_ZXHZ", "直选和值", LotteryId.FC3D),
	FC3D_ZX_DS(100, "100_FC3D_ZX_DS", "组选单式", LotteryId.FC3D),
	FC3D_Z3FS(101, "101_FC3D_Z3FS", "组三复式", LotteryId.FC3D),
	FC3D_Z3HZ(102, "102_FC3D_Z3HZ", "组三和值", LotteryId.FC3D),
	FC3D_Z6FS(103, "103_FC3D_Z6FS", "组六复式", LotteryId.FC3D),
	FC3D_Z6HZ(104, "104_FC3D_Z6HZ", "组六和值", LotteryId.FC3D),
	FC3D_DXBH(105, "105_FC3D_DXBH", "单选包号", LotteryId.FC3D),
	FC3D_1DDS(106, "106_FC3D_1DDS", "1D单式", LotteryId.FC3D),
	FC3D_1DWX(107, "107_FC3D_1DWX", "1D位选", LotteryId.FC3D),
	FC3D_2DDS(108, "108_FC3D_2DDS", "2D单式", LotteryId.FC3D),
	FC3D_2DWX(109, "109_FC3D_2DWX", "2D位选", LotteryId.FC3D),
	FC3D_HS(110, "110_FC3D_HS", "和数", LotteryId.FC3D),
	FC3D_TX(111, "111_FC3D_TX", "通选", LotteryId.FC3D),
	
	
	UNKNOWN(999, "999_UNKOWN", "未知", LotteryId.UNKNOWN),
	;
	
	private final int id;				// 玩法唯一序号
	private final String shortPlayStr;	// 短玩法标识串
	private final String playName;		// 玩法中文名称
	private final LotteryId lotteryId;	// 所属彩种ID
	private static Map<Integer, String> playIdPlayNameMap = new Hashtable<Integer, String>();
	private static Map<String, PlayType> shortLcPlayIdPlayTypeMap = new Hashtable<String, PlayType>();
	private static Map<String,String> longPlayIdNameMap = new Hashtable<String, String>();
	
	public static final String JCGJ = "JCGJ";
	public static final String JCYJ = "JCYJ";
	
	
	static{
		for(PlayType type:PlayType.values()){
			playIdPlayNameMap.put(type.toInt(),type.getPlayName());
		}
	}
	
	static {
		for (PlayType type : PlayType.values()){
			shortLcPlayIdPlayTypeMap.put(type.getShortPlayStr(), type);
		}
	}
	
	static {
		for (PlayType type : PlayType.values()){
			longPlayIdNameMap.put(type.shortPlayStr, type.getPlayName());
		}
	}
	
	/**
	 * 构造一个 PlayType
	 * @param id
	 * @param shortPlayStr
	 * @param playName 玩法名，不包含彩种名
	 */
	PlayType(int id, String shortPlayStr,String playName, LotteryId lotteryId){
		this.id = id;
		this.shortPlayStr = shortPlayStr;
		this.playName = playName;
		this.lotteryId = lotteryId;
	}
	
	/**
	 * 玩法id对应的int值
	 * @return
	 */
	public int toInt(){
		return id;
	}
	
	/**
	 * 是否为混合过关方式。
	 * @return true 混合过关；false 不是。
	 */
	public boolean isHH(){
		return this == PlayType.JCZQ_HH || this == PlayType.JCLQ_HH ||
				this ==  PlayType.JCZQ_FH || this == PlayType.JCLQ_FH;
	}
	
	/**
	 * 是否为高频彩.
	 */
	public boolean isHighFrequency(){
		return this.getLotteryId() == LotteryId.JX11 ||
		       this.getLotteryId() == LotteryId.CQSS;
	}
	
	/**
	 * 是否为数字彩
	 */
	public boolean isDigitalLottery(){
		return this.getLotteryId() == LotteryId.JX11 ||
			   this.getLotteryId() == LotteryId.CQSS ||
			   this.getLotteryId() == LotteryId.SSQ;
	}
	
	/**
	 * 短玩法标识串
	 * @return
	 */
	public String getShortPlayStr(){
		return shortPlayStr;
	}
	
	public String getPlayName(){
		return playName;
	}
	
	public LotteryId getLotteryId(){
		return lotteryId;
	}
	
	/**
	 * 获取与过关方式对应的玩法id标识串。只对竞彩有效，但要包括竞彩混合过关。
	 * @param isDG true 单关；false 过关
	 */
	public String getPlayIdWithPass(boolean isDG){
		if (id >= 11 && id != 80 && id != 555 && id != 666){
			throw new IllegalStateException("getPlayIdWithPass() just support JC!");
		}
		String dgStr = "_1";
		String ggStr = "_2";
		if (isDG){
			return shortPlayStr + dgStr;
		}else{
			return shortPlayStr + ggStr;
		}
	}
	
	/**
	 * 获取玩法id。不支持竞彩，因为缺少过关类型。
	 * @return playId
	 */
	public String getPlayId(){
		if (id<=9||id==80 || id == 555 || id == 666){
			throw new IllegalStateException("getPlayId() does not support JC!");
		}
		return shortPlayStr;
	}
	
	/**
	 * 用大V彩id构造对应的PlayType enum 对象.
	 * @param lcPlayId 大V彩玩法id
	 * @return 玩法类型枚举对象。
	 */
	public static PlayType valueOfLcPlayId(String lcPlayId){
		if(StringUtils.isBlank(lcPlayId)){
			return null;
		}
		String[] parts = lcPlayId.split("_");
		PlayType playType = null;
		if(parts.length>1){
			playType = shortLcPlayIdPlayTypeMap.get(parts[0]+"_"+parts[1]);
		}
		if (playType == null){
			playType = shortLcPlayIdPlayTypeMap.get(lcPlayId);
		}
		return playType;
	}
	
	/**
	 * 用大V彩玩法id获取玩法名字
	 * 
	 * @param id
	 * @return
	 */
	public static String valueOfPlayName(Integer id){
		return playIdPlayNameMap.get(id);
	}
	
	/**
	 * 用大V彩玩法long id 获取玩法名称
	 * 
	 * @param longId
	 * @return
	 */
	public static String getPlayNameByLongId(String longId){
		return longPlayIdNameMap.get(longId);
	}
	
	/**
	 * 获取 PlayType 序号对应的玩法枚举。
	 * @param id 玩法序号
	 * @return 玩法枚举
	 */
	public static PlayType valueOfPlayId(Integer id) {
		for(PlayType type:PlayType.values()){
			if (type.id == id){
				return type;
			}
		}
		throw new IllegalArgumentException("Unknown PlayType id: " + id);
	}

	/**
	 * @return true 如果玩法属于江西11选5彩种。
	 */
	public boolean isJX11() {
		if (this.id >= 11 && this.id <= 22) {
			return true;
		}
		return false;
	}
	
	/** 是否双色球 */
	public boolean isSSQ() {
		if (getLotteryId() == LotteryId.SSQ){
			return true;
		}
		return false;
	}
	
	/** 是否3D */
	public boolean is3D() {
		if (getLotteryId() == LotteryId.FC3D){
			return true;
		}
		return false;
	}
	
	/** 是否重庆时时彩 */
	public boolean isCQSS() {
		if (getLotteryId() == LotteryId.CQSS){
			return true;
		}
		return false;
	}
	
	/**
	 * @return true 为江西11选5的任选玩法，从任选2到任选8，不包含“前1”。
	 */
	public boolean isJX11AnyChoose() {
		if (id>=12 && id<=18) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据玩法id获取玩法选项
	 * @param playId
	 * @return
	 */
	public static String getOptionByPlayId(String playId){
		String options="";
		if(StringUtils.isBlank(playId)){
			return options;
		}
		if(playId.equals(CTZC_14.getPlayId())){
			options = "3,1,0";
		}else if(playId.equals(CTZC_R9.getPlayId())){
			options = "3,1,0";
		}else if(playId.equals(CTZC_BQ.getPlayId())){
			options = "3,1,0";
		}else if(playId.equals(CTZC_JQ.getPlayId())){
			options = "0,1,2,3";
		}else if(playId.equals(JCLQ_RFSF.shortPlayStr)){
			options = "2,1";
		}else if(playId.equals(JCLQ_SF.shortPlayStr)){
			options = "2,1";
		}else if(playId.equals(JCLQ_SFC.shortPlayStr)){
			options = "11,01,12,02,13,03,14,04,15,05,16,06";
		}else if(playId.equals(JCLQ_DXF.shortPlayStr)){
			options = "1,2";
		}else if(playId.equals(JCZQ_BF.shortPlayStr)){
			options = "10,20,21,30,31,32,40,41,42,50,51,52,90,00,11,22,33,99,01,02,12,03,13,23,04,14,24,05,15,25,09";
		}else if(playId.equals(JCZQ_BQC.shortPlayStr)){
			options = "33,31,30,13,11,10,03,01,00";
		}else if(playId.equals(JCZQ_SPF.shortPlayStr) || playId.equals(JCZQ_BRQSPF.shortPlayStr)){
			options = "3,1,0";
		}else if(playId.equals(JCZQ_ZJQS.shortPlayStr)){
			options = "0,1,2,3,4,5,6,7";
		}
		return options;
	}
	
	/**
	 * 根据玩法id获取彩种id
	 * @param playId
	 * @return
	 */
	public static String getLotteryIdByPlayId(String playId){
		PlayType playType = valueOfLcPlayId(playId);
		return playType.getLotteryId().toString();
	}
	/**
	 * 根据玩法获取彩种中文名字
	 * @param playId
	 * @return
	 */
	public static String getPlayNameByPlayId(String playId){
		PlayType playType = valueOfLcPlayId(playId);
		return playType.getPlayName();
	}
	
	/**
	 * 判断是否包含一场赛事多玩法混合过关投注
	 * @param playId
	 * @return
	 */
	public static boolean isOneMatchMutiPlayMixBet(String playId) {
		if(StringUtils.isNotBlank(playId)) {
			if(playId.equals(PlayType.JCZQ_FH.getPlayIdWithPass(false)) ||
					playId.equals(PlayType.JCLQ_FH.getPlayIdWithPass(false))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断当前投注内容是否为单玩法混投
	 * @param playId
	 * @return
	 */
	public static boolean isOnePlayMixBet(String playId) {
		boolean result = false;
		if(StringUtils.isNotBlank(playId)) {
			if(playId.equals(PlayType.JCZQ_FH.getPlayIdWithPass(true)) ||
				playId.equals(PlayType.JCLQ_FH.getPlayIdWithPass(true))) {
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * 是否是复合投注
	 * @param playId
	 * @return
	 */
	public static boolean isFHMixBet(String playId) {
		if(StringUtils.isNotBlank(playId)) {
			return isOneMatchMutiPlayMixBet(playId) || 
					isOnePlayMixBet(playId);
		}
		return false;
	}
}
