package com.unison.lottery.api.protocol.request.xml.utils;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.request.json.model.JsonMethodRequest;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;



public interface IUserParser {

	boolean parseUser(MethodRequest methodRequest,
			HttpServletRequest httpRequest);
	
	boolean parseUser(JsonMethodRequest methodRequest,
			HttpServletRequest httpRequest);

}
