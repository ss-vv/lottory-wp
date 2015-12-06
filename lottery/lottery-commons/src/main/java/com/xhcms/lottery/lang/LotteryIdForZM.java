package com.xhcms.lottery.lang;

import org.apache.commons.lang.StringUtils;

/**
 * 中民彩票ID
 * 
 * @author Wang Lei, Yang Bo
 */
public enum LotteryIdForZM {
	SF14("14CSF", LotteryId.CTZC),		// 传统足彩，14场胜负
	SFR9("SFR9", LotteryId.CTZC),  		// 传统足彩，胜负任9
	BQ6C("6CBQ", LotteryId.CTZC),		// 传统足彩，6场半全
	JQ4C("4CJQ", LotteryId.CTZC), 		// 传统足彩，4场进球
	
	CQSS("ZQSSC", LotteryId.CQSS),		// 重庆时时彩
	JX11("JX11X5", LotteryId.JX11),		// 江西11选5
	SSQ("SSQ", LotteryId.SSQ),			// 双色球
	FC3D("3D", LotteryId.FC3D),			// 3D
	
	JCSPF("JCSPF", LotteryId.JCZQ),     	// 竟彩足球胜平负
	JCBRQSPF("JCBRQSPF", LotteryId.JCZQ),	// 竟彩足球不让球胜平负
	JCBF("JCBF", LotteryId.JCZQ),       // 竟彩足球比分
	JCJQS("JCJQS", LotteryId.JCZQ),     // 竟彩足球进球数
	JCBQC("JCBQC", LotteryId.JCZQ),     // 竟彩足球半全场
	JCZQFH("JCZQFH", LotteryId.JCZQ),   // 竟彩足球混合过关
	JCSJBGJ("jcsjbgj", LotteryId.JCZQ),   // 世界杯冠军

	JCSF("JCSF", LotteryId.JCLQ),       // 竟彩篮球胜负
	JCRFSF("JCRFSF", LotteryId.JCLQ),   // 竟彩篮球让分胜负
	JCFC("JCFC", LotteryId.JCLQ),       // 竟彩篮球胜分差
	JCDXF("JCDXF", LotteryId.JCLQ),     // 竟彩篮球大小分
	JCLQFH("JCLQFH", LotteryId.JCLQ),   // 竟彩篮球混合过关
	//北单
	BDSFP("SPF",LotteryId.BJDC),
	BDBF("BF",LotteryId.BJDC),
	BDSXDS("SXDS",LotteryId.BJDC),
	BDJQS("JQS",LotteryId.BJDC),
	BDBQC("BQC",LotteryId.BJDC),
	//胜负
	BDSF("SF",LotteryId.BDSF),
	;
	
	private String zmLotteryId;			// 中民彩种id串，见《中民接口文档》中“彩种代码及限倍列表”的定义。
	private LotteryId lcLottery; 		// 大V彩彩种id
	
    private LotteryIdForZM(String zmLotteryId, LotteryId lcLottery) {
		this.zmLotteryId = zmLotteryId;
		this.lcLottery = lcLottery;
	}
    
    public static LotteryIdForZM valueOfName(String name){
    	for (LotteryIdForZM playType : LotteryIdForZM.values()) {
    		if (playType.zmLotteryId.equalsIgnoreCase(name)) {
    			return playType;
    		}
    	}
    	throw new IllegalArgumentException("Unknown name for ZQPlayType: " + name);
    }
	
    /**
     * @return 本中民彩种ID对应的大V彩彩种ID。
     */
    public LotteryId getLcLotteryId(){
    	return this.lcLottery;
    }
    
    /**
     * 中民lotteryId是否是传统足彩lotteryid
     * @param name
     * @return
     */
    public static boolean isCTZCLotteryId(String name){
    	return isInLottery(name, LotteryId.CTZC);
    }
    
    /**
     * 北京单场
     * @param name
     * @return
     */
    public static boolean isBJDCLotteryId(String name){
    	return isInLottery(name, LotteryId.BJDC);
    }
    
    /**
     * 北京单场
     * @param name
     * @return
     */
    public static boolean isBDSFLotteryId(String name){
    	return isInLottery(name, LotteryId.BDSF);
    }
    
    /**
     * 中民lotteryId是否是重庆时时彩lotteryid
     * @param name
     * @return
     */
    public static boolean isCQSSLotteryId(String name){
    	return isInLottery(name, LotteryId.CQSS);
    }
    
    /**
     * 中民lotteryId是否是江西11选5lotteryid
     * @param name
     * @return
     */
    public static boolean isJX11LotteryId(String name){
    	return isInLottery(name, LotteryId.JX11);
    }
    
    /**
     * 中民lotteryId是否是双色球lotteryid
     * @param name
     * @return
     */
    public static boolean isSSQLotteryId(String name){
    	return isInLottery(name, LotteryId.SSQ);
    }
    
    public static boolean isFC3DLotteryId(String name){
    	return isInLottery(name, LotteryId.FC3D);
    }
    
    private static boolean isInLottery(String name, LotteryId lcLottery){
    	for(LotteryIdForZM zmLotteryId : LotteryIdForZM.values()){
    		if (zmLotteryId.lcLottery.equals(lcLottery) &&  zmLotteryId.zmLotteryId.equalsIgnoreCase(name)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * 从中民lotteryId转换大V彩playId.
     * 只有部分彩种支持，部分彩种需要给定玩法代码才能对应到大V彩playId.
     * 
     * @param platformLotteryId
     * @return
     */
    public static PlayType getLcPlayTypeFromZmLotteryId(String platformLotteryId){
    	if(StringUtils.isBlank(platformLotteryId)){
    		return null;
    	}
    	if(platformLotteryId.equals(SF14.getName())){
			return PlayType.CTZC_14;
		}
		if(platformLotteryId.equals(SFR9.getName())){
			return PlayType.CTZC_R9;
		}
		if(platformLotteryId.equals(BQ6C.getName())){
			return PlayType.CTZC_BQ;
		}
		if(platformLotteryId.equals(JQ4C.getName())){
			return PlayType.CTZC_JQ;
		}
		//北单
		if(platformLotteryId.equals(BDSFP.getName())){
			return PlayType.BJDC_SPF;
		}
		//胜负
		if(platformLotteryId.equals(BDSF.getName())){
			return PlayType.BJDC_SF;
		}
		
		/*if(platformLotteryId.equals(JQ4C.getName())){
			return PlayType.CTZC_JQ;
		}
		if(platformLotteryId.equals(JQ4C.getName())){
			return PlayType.CTZC_JQ;
		}
		if(platformLotteryId.equals(JQ4C.getName())){
			return PlayType.CTZC_JQ;
		}
		if(platformLotteryId.equals(JQ4C.getName())){
			return PlayType.CTZC_JQ;
		}
		if(platformLotteryId.equals(JQ4C.getName())){
			return PlayType.CTZC_JQ;
		}*/
		
		return null;
    }

    /**
     * 从大V彩玩法id映射得到中民出票平台的彩种id。
     * 
     * @param lcPlayId 大V彩玩法id
     * @return 中民彩种id
     * @throws LotteryIdException 转换异常
     */
    public static LotteryIdForZM getZMLotteryIdFromLcPlayId(String lcPlayId) throws LotteryIdException {
    	PlayType lcPlayType = PlayType.valueOfLcPlayId(lcPlayId);
    	// 重庆时时彩
    	if ( lcPlayType.isCQSS() ) {
    		return LotteryIdForZM.CQSS;
    	}
    	// 江西11选5
    	if ( lcPlayType.isJX11() ) {
    		return LotteryIdForZM.JX11;
    	}
    	// 双色球
    	if ( lcPlayType.isSSQ() ) {
    		return LotteryIdForZM.SSQ;
    	}
    	
    	//FC3d
    	if ( lcPlayType.is3D() ) {
    		return LotteryIdForZM.FC3D;
    	}
    	
    	switch(lcPlayType) {
	    	case CTZC_14: return SF14;			// 传统足彩，14场胜负
	    	case CTZC_R9: return SFR9;			// 传统足彩，胜负任9
	    	case CTZC_BQ: return BQ6C;			// 传统足彩，6场半全
	    	case CTZC_JQ: return JQ4C;			// 传统足彩，4场进球
	
	    	case JCZQ_SPF: return JCSPF;			// 竟彩足球胜平负
	    	case JCZQ_BRQSPF: return JCBRQSPF;		// 竟彩足球不让球胜平负
	    	case JCZQ_BF:  return JCBF;			// 竟彩足球比分
	    	case JCZQ_ZJQS: return JCJQS;		// 竟彩足球进球数
	    	case JCZQ_BQC: return JCBQC;		// 竟彩足球半全场
	    	case JCZQ_HH: return JCZQFH;		// 竟彩足球混合过关
	    	case JCZQ_FH: return JCZQFH;		// 竟彩足球混合过关
	    	case JCSJBGJ: return JCSJBGJ;		// 世界杯冠军
	
	    	case JCLQ_SF: return JCSF;			// 竟彩篮球胜负
	    	case JCLQ_RFSF: return JCRFSF;		// 竟彩篮球让分胜负
	    	case JCLQ_SFC: return JCFC;			// 竟彩篮球胜分差
	    	case JCLQ_DXF: return JCDXF;		// 竟彩篮球大小分
	    	case JCLQ_HH: return JCLQFH;		// 竟彩篮球混合过关
	    	case JCLQ_FH: return JCLQFH;		// 竟彩篮球混合过关
	    	
	    	case BJDC_SPF: return BDSFP;		//北单胜平负
	    	case BJDC_JQS: return BDJQS;		//北单进球数
	    	case BJDC_SXDS: return BDSXDS;		//北单上下单双
	    	case BJDC_BF: return BDBF;			//北单比分
	    	case BJDC_BQC: return BDBQC;		//北单半全场
	    	case BJDC_SF: return BDSF;			//北单胜负过关
	    	
	    	default:
	    		throw new LotteryIdException("Unsupported lcPlayId: " + lcPlayId);
    	}
    }
    
	public String getName() {
		return zmLotteryId;
	}
}
