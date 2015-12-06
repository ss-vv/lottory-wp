package com.unison.lottery.weibo.dataservice.parse.model;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FBEvent {

	private List<FBEventContentData> fbEventContentDataList;

	public List<FBEventContentData> getFbEventContentDataList() {
		return fbEventContentDataList;
	}

	public void setFbEventContentDataList(
			List<FBEventContentData> fbEventContentDataList) {
		this.fbEventContentDataList = fbEventContentDataList;
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
