package com.unison.lottery.api.framework.log.model;

import javax.xml.bind.annotation.XmlElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.unison.lottery.api.protocol.response.model.Response;


@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseContent {
	@XmlElement
	public Response response;
}
