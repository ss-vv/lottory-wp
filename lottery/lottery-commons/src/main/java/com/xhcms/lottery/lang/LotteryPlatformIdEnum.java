package com.xhcms.lottery.lang;

public enum LotteryPlatformIdEnum { 
	CHANGCHUN_SHI_TI_DIAN("changchunshitidian"),
	CHANGCHUN_SHI_TI_DIAN2("changchunshitidian2"),
	CHANGCHUN_60005("changchun60005"),
	SHANXI_00001("shanxi00001"),
	CHANGCHUN_02857("changchun02857"),
	KUAI_SU_CHU_PIAO("kuaisuchupiao"),
	YUAN_CHENG_CHU_PIAO("yuanchengchupiao");
	
	private String lotteryPlatformId;
	
	LotteryPlatformIdEnum(String lotteryPlatformId){
		this.lotteryPlatformId = lotteryPlatformId;
	}
	
	public String getLotteryPlatformId(){
		return this.lotteryPlatformId;
	}
	
	public static LotteryPlatformIdEnum getlotteryPlatformIdEnumById(String lotteryPlatformId){
		for (LotteryPlatformIdEnum a : LotteryPlatformIdEnum.values()) {
			if(a.getLotteryPlatformId().equals(lotteryPlatformId)){
				return a;
			}
		}
		return null;
	}
}
