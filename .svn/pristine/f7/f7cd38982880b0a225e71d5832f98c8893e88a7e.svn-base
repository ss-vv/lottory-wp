package com.xhcms.lottery.lang;

public enum LotteryIdForAnRui {

	SF14("10", LotteryId.CTZC),		// 传统足彩，14场胜负
	SFR9("11", LotteryId.CTZC),  		// 传统足彩，胜负任9
	BQ6C("12", LotteryId.CTZC),		// 传统足彩，6场半全
	JQ4C("13", LotteryId.CTZC), 		// 传统足彩，4场进球
	
	CQSS("102", LotteryId.CQSS),		// 重庆时时彩
	SSQ("200", LotteryId.SSQ),			// 双色球
	FC3D("201", LotteryId.FC3D),			// 3D
	
	JCSPF("20", LotteryId.JCZQ),     	// 竞彩足球胜平负
	JCBRQSPF("26", LotteryId.JCZQ),	// 竞彩足球不让球胜平负
	JCBF("21", LotteryId.JCZQ),       // 竞彩足球比分
	JCJQS("22", LotteryId.JCZQ),     // 竞彩足球进球数
	JCBQC("23", LotteryId.JCZQ),     // 竞彩足球半全场
	JCZQFH("25", LotteryId.JCZQ),   // 竞彩足球混合过关
	JCSJBGJ("24", LotteryId.JCZQ),   // 世界杯冠军

	JCSF("30", LotteryId.JCLQ),       // 竞彩篮球胜负
	JCRFSF("31", LotteryId.JCLQ),   // 竞彩篮球让分胜负
	JCFC("32", LotteryId.JCLQ),       // 竞彩篮球胜分差
	JCDXF("33", LotteryId.JCLQ),     // 竞彩篮球大小分
	JCLQFH("35", LotteryId.JCLQ);   // 竞彩篮球混合过关
	
	private String anRuiLotteryId;
	private LotteryId davLottery;
	
	public LotteryId getDavLottery() {
		return davLottery;
	}


	public void setDavLottery(LotteryId davLottery) {
		this.davLottery = davLottery;
	}


	public String getAnRuiLotteryId() {
		return anRuiLotteryId;
	}


	public void setAnRuiLotteryId(String anRuiLotteryId) {
		this.anRuiLotteryId = anRuiLotteryId;
	}
	
    private LotteryIdForAnRui(String anRuiLotteryId, LotteryId lcLottery) {
		this.anRuiLotteryId = anRuiLotteryId;
		this.davLottery = lcLottery;
	}
    
    
    /**
     * @return 本中民彩种ID对应的大V彩彩种ID。
     */
    public static LotteryId getDavLotteryIdByAnRuiLotteryId(String lotteryId){
    	LotteryId result= null;
    	for (LotteryIdForAnRui anRuiLotteryId : LotteryIdForAnRui.values()) {
			if(anRuiLotteryId.getAnRuiLotteryId().equals(lotteryId)){
				result = anRuiLotteryId.getDavLottery();
				break;
			}
		}
    	return result;
    }
    
    

}
