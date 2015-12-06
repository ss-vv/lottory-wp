package com.unison.lottery.api.registe.bo;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.response.model.RegisteResponse;

public interface RegisteBO {

	RegisteResponse regist(User user);

}
