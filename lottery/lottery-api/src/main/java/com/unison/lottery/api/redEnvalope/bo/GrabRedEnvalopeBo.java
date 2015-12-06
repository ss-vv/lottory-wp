package com.unison.lottery.api.redEnvalope.bo;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;

public interface GrabRedEnvalopeBo {

	HaveReturnStatusResponse grabRedEnvalope(Long envalopeId, User user);

}
