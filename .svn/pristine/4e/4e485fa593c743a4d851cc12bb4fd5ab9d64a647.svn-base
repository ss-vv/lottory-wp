package com.unison.lottery.api.protocol.response.xml.parser.methodparse;

import java.util.ArrayList;


import javax.servlet.http.HttpServletRequest;


import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;

import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Item;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.response.model.ActivityNotifyResponse;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;

import com.unison.lottery.api.protocol.response.model.Response;
import com.xhcms.lottery.commons.persist.entity.ActivityNotifyPO;

public class ActivityNotifyMethodResponseParser extends
		AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.ACTIVITY_NOTIFY_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		ActivityNotifyResponse activityNotifyResponse=(ActivityNotifyResponse) responseFromHttpRequest;
		if(null!=activityNotifyResponse&&null!=activityNotifyResponse.getReturnStatus()){
			resultResponse.result=new Result();
			resultResponse.result.item = new ArrayList<Item>();
			if(null!=activityNotifyResponse.getNotifies()){
				for(ActivityNotifyPO notify:activityNotifyResponse.getNotifies()){
					Item resultItem = new Item();
					resultItem.title=notify.getActivityName();
					resultItem.content=notify.getActivityDesc();
					resultResponse.result.item.add(resultItem);
				}
			}
			
			
				
			
		}
		

	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.ActivityNotify.FAIL;
	}

}
