package com.unison.lottery.weibo.dataservice.commons.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlRootElement(name = "list")
public class BFXmlByIdModel {

	@XmlElement(name = "match")
	private List<BFXmlByIdContentModel> contentModel;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public List<BFXmlByIdContentModel> getContentModel() {
		return contentModel;
	}

	public void setContentModel(List<BFXmlByIdContentModel> contentModel) {
		this.contentModel = contentModel;
	}
	
	
}