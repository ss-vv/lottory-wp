package com.unison.lottery.weibo.dataservice.commons.parse;

import java.util.Hashtable;
import java.util.Map;

/**
 * 文本字段。
 * <p>一个“文本字段”可以是一个简单的“bean属性名”，也可以是包含子文档的复合字段。
 * <p>"文本记录"是文本格式定义基础类，使用 Composite 设计模式。
 * 
 * @author Yang Bo
 */
public class TextField {

	// 属性名，如果是子文档，属性名 为 null
	protected String propertyName;
	
	// 子文档分隔符
	protected String delimiter;
	
	// 子文档的下标定义，从0开始，没有子文档就为null
	protected Map<Integer, TextField> recordsIndex = new Hashtable<>();

	public TextField(){
	}
	
	public TextField(String property) {
		this.propertyName = property;
	}

	public boolean hasSubRecords(){
		return recordsIndex != null && recordsIndex.size() > 0;
	}
	
	/**
	 * 添加一个简单“字段”。
	 * @param index 字段索引
	 * @param property bean的属性名
	 */
	public void addField(int index, String property){
		TextField field = new TextField(property);
		recordsIndex.put(index, field);
	}
	
	public void addField(int index, TextField field){
		recordsIndex.put(index, field);
	}
	
	
	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Map<Integer, TextField> getRecordsIndex() {
		return recordsIndex;
	}

	public void setRecordsIndex(Map<Integer, TextField> recordsIndex) {
		this.recordsIndex = recordsIndex;
	}
}
