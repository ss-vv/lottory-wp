package com.xhcms.lottery.lang;

import java.util.HashMap;
import java.util.Map;

public enum EnumLotteryType {
	JCZQ("JCZQ","竞彩足球"),
	JCLQ("JCLQ","竞彩篮球"),
	CTZC("CTZC","传统足彩"),
	SSQ("SSQ","双色球"),
	JX11("JX11","江西11选5"),
	CQSS("CQSS","重庆时时彩");
	
	private String id;
	private String name;
	public static Map<String,String> lotteryNameMap = new HashMap<String,String>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	EnumLotteryType(String id,String name){
		this.id = id;
		this.name = name;
	}

	static{
		for (EnumLotteryType lottery : EnumLotteryType.values()) {
			
			lotteryNameMap.put(lottery.getId(), lottery.getName());
		}
	}

	
	public static String getLotteryName(String id){
		return lotteryNameMap.get(id);
	}
}
