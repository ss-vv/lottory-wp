package com.unison.lottery.api.util;

import com.unison.lottery.api.model.User;

public interface AllowReturnResultService {

	boolean allow(User user);

	boolean shouldDecideAllowReturnResult(String clientVersion);

}
