package com.unison.lottery.api.withdraw.bo;

import java.math.BigDecimal;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.response.model.WithdrawResponse;

public interface WithdrawBO {

	WithdrawResponse withdrawForUser(
			String withdrawPassword, BigDecimal amount, String realIP, User user);

}
