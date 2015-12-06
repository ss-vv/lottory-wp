package com.unison.lottery.api.protocol.response.model;

import java.util.List;

import com.xhcms.lottery.commons.persist.entity.ActivityNotifyPO;

public class ActivityNotifyResponse extends HaveReturnStatusResponse{
	private List<ActivityNotifyPO> notifies;

	public List<ActivityNotifyPO> getNotifies() {
		return notifies;
	}

	public void setNotifies(List<ActivityNotifyPO> notifies) {
		this.notifies = notifies;
	}

}
