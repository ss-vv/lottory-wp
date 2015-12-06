package com.unison.lottery.api.bindbank.bo;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.response.model.BindBankResponse;
import com.xhcms.lottery.commons.data.Account;

public interface BindBankBO {

	BindBankResponse bindBankForUser(Account account, User user);

}
