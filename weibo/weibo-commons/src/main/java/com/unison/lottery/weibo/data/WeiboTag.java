package com.unison.lottery.weibo.data;

import java.io.Serializable;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @desc 微博标签对象
 * @author lei.li@unison.net.cn
 * @createTime 2014-5-5
 * @version 1.0
 */
public class WeiboTag implements Serializable {

	private static final long serialVersionUID = -1808865018713673292L;

	private long id;

	private String name;

	private String bgcolor;

	private String color;

	/** 是否为动态标签 */
	private boolean isDynamic;

	/** 方案ID */
	private long schemeId;
	
	/** 投注记录ID */
	private long betRecordId;

	/** 是否为最终状态 */
	private boolean isFinalStatus;

	/** 动态标签计算出的内容 */
	private String value;

	/** 索引 */
	private int index;

	private long createTime;
	
	private long recSchemeId;
	
	private String weiboType;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isDynamic() {
		return isDynamic;
	}

	public void setDynamic(boolean isDynamic) {
		this.isDynamic = isDynamic;
	}

	public long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(long schemeId) {
		this.schemeId = schemeId;
	}

	public boolean isFinalStatus() {
		return isFinalStatus;
	}

	public void setFinalStatus(boolean isFinalStatus) {
		this.isFinalStatus = isFinalStatus;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getRecSchemeId() {
		return recSchemeId;
	}

	public void setRecSchemeId(long recSchemeId) {
		this.recSchemeId = recSchemeId;
	}

	public String getWeiboType() {
		return weiboType;
	}

	public void setWeiboType(String weiboType) {
		this.weiboType = weiboType;
	}

	public long getBetRecordId() {
		return betRecordId;
	}

	public void setBetRecordId(long betRecordId) {
		this.betRecordId = betRecordId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}