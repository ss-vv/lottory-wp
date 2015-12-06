package com.unison.weibo.admin.action.app.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Notice {

	public String type; // 1(活动),2(公告),4(更新群方案)
	
	public String content; //公告内容
	
}
