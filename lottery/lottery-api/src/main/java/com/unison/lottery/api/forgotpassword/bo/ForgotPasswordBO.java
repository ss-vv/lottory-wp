package com.unison.lottery.api.forgotpassword.bo;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.response.model.ForgotPasswordResponse;

public interface ForgotPasswordBO {

	ForgotPasswordResponse resetPassword(String verifyCode, User user);

}
