package com.unison.lottery.api.odds.bo;

import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;

/**
 *
 * @author baoxing.peng
 * @since 2015年3月26日上午9:44:03
 */
public interface QueryImmediateIndexDetailsBO {

	HaveReturnStatusResponse queryImmediateIndexDetails(Long matchId,
			String corpId, String oddsType, String matchType);

}
