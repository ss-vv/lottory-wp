package com.unison.lottery.weibo.dataservice.commons.model;

import java.io.File;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;



public class DataInterfaceResponse {
	
	private boolean succ;
	private String responseStr;//数据接口返回的响应字符串
	
	private File responseFile;//数据接口返回的响应内容保存到的文件
	
	public boolean isSucc() {
		return succ;
	}

	public void setSucc(boolean succ) {
		this.succ = succ;
	}

	public String getResponseStr() {
		return responseStr;
	}

	public void setResponseStr(String responseStr) {
		this.responseStr = responseStr;
	}

	public File getResponseFile() {
		return responseFile;
	}

	public void setResponseFile(File responseFile) {
		this.responseFile = responseFile;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
