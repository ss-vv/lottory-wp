package com.unison.lottery.weibo.dataservice.parse.model;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FBTechnicData {
	
	private List<FBTechnicContentData> fbTechicContentDataList;

	public List<FBTechnicContentData> getFbTechicContentDataList() {
		return fbTechicContentDataList;
	}

	public void setFbTechicContentDataList(
			List<FBTechnicContentData> fbTechicContentDataList) {
		this.fbTechicContentDataList = fbTechicContentDataList;
	}

	
	public String toString(){
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
