package com.unison.lottery.api.odds.bo;

import com.unison.lottery.api.protocol.response.model.QueryImmediateIndexInfoResponse;

public interface QueryImmediateIndexInfoBO {

	QueryImmediateIndexInfoResponse queryOddsDataByMatchType(Integer matchType, String time, Long matchId, String leagueShortName);

}
