package com.xhcms.lottery.lang;

/**
 * 混合过关方式.
 * @author Yang Bo
 */
public enum MixPlayType {
	SF("SF", PlayType.JCLQ_SF),
	RFSF("RFSF", PlayType.JCLQ_RFSF),
	FC("FC", PlayType.JCLQ_SFC),
	DXF("DXF", PlayType.JCLQ_DXF),
	
	BRQSPF("BRQSPF", PlayType.JCZQ_BRQSPF),
	SPF("SPF", PlayType.JCZQ_SPF),
	BF("BF", PlayType.JCZQ_BF),
	JQS("JQS", PlayType.JCZQ_ZJQS),
	BQC("BQC", PlayType.JCZQ_BQC)
	;
	private String name;
	private PlayType playType;
	
	private MixPlayType(String name, PlayType playType){
		this.name = name;
		this.playType = playType;
	}
	public String getPlayId(){
		return this.playType.getPlayIdWithPass(false);
	}
	
	/**
	 * 从小写字母缩写取对应的枚举对象。
	 * @param lowerCaseName 小写缩写，如 SF.
	 */
	public static MixPlayType valueOfPlayName(String playName){
		return MixPlayType.valueOf(playName.toUpperCase());
	}
	
	/**
	 * 从大V彩玩法类型获取对应的混合过关玩法类型。
	 * @param playType 大V彩玩法类型
	 * @return 混合过关玩法类型
	 */
	public static MixPlayType valueOfPlayType(PlayType playType){
		for (MixPlayType mp : MixPlayType.values()) {
			if (mp.getPlayType() == playType){
				return mp;
			}
		}
		throw new IllegalArgumentException("Unknown PlayType for MixPlayType: " + playType);
	}
	/**
	 * @return 混合过关方式的小写缩写名称，如：spf
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PlayType getPlayType() {
		return playType;
	}
	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}
}
