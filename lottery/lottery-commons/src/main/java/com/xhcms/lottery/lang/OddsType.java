package com.xhcms.lottery.lang;


/**
 * @desc 赔率类型
 */
public enum OddsType {
	
	EUROPE_ODDS(1, "亚赔"),
	ASIA_ODDS(2,"欧赔"),
	BIGORSMALL_ODDS(3,"大小分");
	
	private int type;
	private String name;
	
	private OddsType(int type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public static OddsType get(int type) {
		OddsType[] arr = OddsType.values();
		for(OddsType t : arr) {
			if(type == t.type) {
				return t;
			}
		}
		return null;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
