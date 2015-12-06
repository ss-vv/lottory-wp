package com.unison.lottery.api.query.bo;

import java.util.Map;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.response.model.QueryCTZCMatchResponse;
import com.unison.lottery.api.protocol.response.model.QueryJCLQMatchResponse;
import com.unison.lottery.api.protocol.response.model.QueryJCZQMatchResponse;
import com.unison.lottery.api.protocol.response.model.QueryOnSaleLotteryResponse;

public interface QueryBO {

	QueryOnSaleLotteryResponse queryOnSaleLottery(String benner, User user);

	QueryJCZQMatchResponse queryJCZQMatch(Map<String, String> map, User user);

	QueryJCLQMatchResponse queryJCLQMatch(Map<String, String> paramMap, User user);

	/**
	 * 查询可投注的传统足彩赛事：如果玩法为空则默认玩法为“任九”
	 * @param paramMap
	 * @param user 
	 * @return
	 */
	QueryCTZCMatchResponse queryCTZCMatch(Map<String, String> paramMap, User user);
}
