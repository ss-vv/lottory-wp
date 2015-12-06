package com.unison.lottery.weibo.dataservice.commons.parse;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BeanForTest {

	private int intProp;
	private String strProp;
	private BigDecimal decimalProp;
	private boolean booleanProp;
	private Boolean booleanObjProp;
	private float floatProp;
	private long longProp;
	private Long longObjProp;
	private Date dateProp;
	
	private String subProp1;
	private long subProp2;
	private boolean subProp3;
	
	private ChildBean child = new ChildBean();
	
	public int getIntProp() {
		return intProp;
	}
	public void setIntProp(int intProp) {
		this.intProp = intProp;
	}
	public String getStrProp() {
		return strProp;
	}
	public void setStrProp(String strProp) {
		this.strProp = strProp;
	}
	public BigDecimal getDecimalProp() {
		return decimalProp;
	}
	public void setDecimalProp(BigDecimal decimalProp) {
		this.decimalProp = decimalProp;
	}
	public boolean isBooleanProp() {
		return booleanProp;
	}
	public void setBooleanProp(boolean booleanProp) {
		this.booleanProp = booleanProp;
	}
	public Boolean getBooleanObjProp() {
		return booleanObjProp;
	}
	public void setBooleanObjProp(Boolean booleanObjProp) {
		this.booleanObjProp = booleanObjProp;
	}
	public float getFloatProp() {
		return floatProp;
	}
	public void setFloatProp(float floatProp) {
		this.floatProp = floatProp;
	}
	public long getLongProp() {
		return longProp;
	}
	public void setLongProp(long longProp) {
		this.longProp = longProp;
	}
	public Long getLongObjProp() {
		return longObjProp;
	}
	public void setLongObjProp(Long longObjProp) {
		this.longObjProp = longObjProp;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	public Date getDateProp() {
		return dateProp;
	}
	public void setDateProp(Date dateProp) {
		this.dateProp = dateProp;
	}
	public String getSubProp1() {
		return subProp1;
	}
	public void setSubProp1(String subProp1) {
		this.subProp1 = subProp1;
	}
	public long getSubProp2() {
		return subProp2;
	}
	public void setSubProp2(long subProp2) {
		this.subProp2 = subProp2;
	}
	public boolean isSubProp3() {
		return subProp3;
	}
	public void setSubProp3(boolean subProp3) {
		this.subProp3 = subProp3;
	}
	public ChildBean getChild() {
		return child;
	}
	public void setChild(ChildBean child) {
		this.child = child;
	}
}
