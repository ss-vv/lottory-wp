package com.unison.lottery.api.framework.request;

import javax.servlet.http.HttpServletRequest;

public interface IRequestParser {

	boolean parseRequestToObject(HttpServletRequest httpRequest);
    
}
