package com.unison.lottery.api.modifypassword.bo;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.response.model.ModifyPasswordResponse;

public interface ModifyPasswordBO {

	ModifyPasswordResponse modifyPasswordForUser(String oldPassword,
			String newPassword, User user, String type);

}
