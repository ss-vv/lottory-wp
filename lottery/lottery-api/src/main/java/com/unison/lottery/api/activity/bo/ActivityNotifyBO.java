package com.unison.lottery.api.activity.bo;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.response.model.ActivityNotifyResponse;

public interface ActivityNotifyBO {

	ActivityNotifyResponse listActivityNotify(String firstResult);

}
