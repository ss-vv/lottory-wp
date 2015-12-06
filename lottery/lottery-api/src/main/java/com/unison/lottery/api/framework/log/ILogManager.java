package com.unison.lottery.api.framework.log;

import javax.servlet.http.HttpServletRequest;





public interface ILogManager {

	void recordActivityDetailLog(HttpServletRequest httpRequest );

}
