package com.unison.lottery.weibo.dataservice.parse.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class League {
	
	private String id;
	private String type;//1：联赛，2：杯赛
	private String color;
	private String nameInGB;//国语名
	private String nameInBig;//繁体名
	private String nameInEng;//英文名
	private String doucmentPath;//资料库路径
	private String importantMatch;//是否重要賽事 0/1表示，0：次要赛事，1：重要赛事
	private String isAllOrSimple;//是否精简版( 0:完全版、1:精简)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getNameInGB() {
		return nameInGB;
	}
	public void setNameInGB(String nameInGB) {
		this.nameInGB = nameInGB;
	}
	public String getNameInBig() {
		return nameInBig;
	}
	public void setNameInBig(String nameInBig) {
		this.nameInBig = nameInBig;
	}
	public String getNameInEng() {
		return nameInEng;
	}
	public void setNameInEng(String nameInEng) {
		this.nameInEng = nameInEng;
	}
	public String getDoucmentPath() {
		return doucmentPath;
	}
	public void setDoucmentPath(String doucmentPath) {
		this.doucmentPath = doucmentPath;
	}
	public String getImportantMatch() {
		return importantMatch;
	}
	public void setImportantMatch(String importantMatch) {
		this.importantMatch = importantMatch;
	}
	public String getIsAllOrSimple() {
		return isAllOrSimple;
	}
	public void setIsAllOrSimple(String isAllOrSimple) {
		this.isAllOrSimple = isAllOrSimple;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
