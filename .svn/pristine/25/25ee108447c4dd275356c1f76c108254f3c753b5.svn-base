package com.unison.lottery.weibo.lang;


/**
 * @desc 微博特殊标签
 * @author lei.li@unison.net.cn
 * @createTime 2014-5-7
 * @version 1.0
 */
public enum SpecialTag {
	
	/**彩种类型的标签*/
	JCZQ("竞足", "#83C150"), 
	JCLQ("竞篮", "#F1C411"),
	CTZC("足彩", "#D8A137"),
	BJDC("北单", "#56B39C"),
	
	/**微博类型的标签*/
	SHOW("晒单", "#FC8619"),
	GROUP("合买", "#FF2D81"),
	RECOMMEND("推荐", "#328EF1"),
	FOLLOW("跟单", "#994CD5"),
	
	/**方案状态类型的标签*/
	SALING("销售中", "#A89297"),
	FULL("已满员", "#A89297"),
	PROGRESS("进度", "#A89297"),	//合买进度
	END("已截止", "#A89297"),
	NOT_WIN("未中奖", "#A89297"),
	AWARDED("奖金", "#D54C6C"),
	FLOW("流标", "#A89297"),
	CANCEL("撤单", "#A89297"),
	REC_HIT_RATE("命中率", "#D54C6C")
	;

	private SpecialTag(String tagName, String bgcolor) {
		this.tagName = tagName;
		this.bgcolor = bgcolor;
	}
	
	private String tagName;

	private String bgcolor;
	
	public static String COLOR = "#FFFFFF";

	public String getTagName() {
		return tagName;
	}
	
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	public String getBgcolor() {
		return bgcolor;
	}
	
	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}
}