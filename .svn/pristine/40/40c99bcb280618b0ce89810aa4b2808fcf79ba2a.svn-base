package com.unison.lottery.api.protocol.request.xml.methodparser;

import javax.servlet.http.HttpServletRequest;



import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;
import com.unison.lottery.api.protocol.request.xml.utils.IUserParser;

public abstract class AbstractRequestParserWithUserParser extends
		AbstractRequestParserForMethod {

	private IUserParser userParser;

	@Override
	protected boolean parseUser(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		boolean result=false;
		if(null!=this.userParser){
			result=this.userParser.parseUser(methodRequest,httpRequest);
		}
		return result;
	}

	/**
	 * @return the userParser
	 */
	public IUserParser getUserParser() {
		return userParser;
	}

	/**
	 * @param userParser the userParser to set
	 */
	public void setUserParser(IUserParser userParser) {
		this.userParser = userParser;
	}

	

}
