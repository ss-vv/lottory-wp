package com.unison.lottery.api.bet.bo;

import java.util.Map;

import com.unison.lottery.api.protocol.response.model.BetSchemeResponse;



public interface BetSchemeBO {
	
	
    /**
     * 代购投注
     * @param userId    用户id
     * @param partner  合作方
     * @param betScheme 投注方案
     * @param tickets   方案拆分的彩票
     * 
     * @return 0：投注成功，可出票；其他值认购失败
     */
	BetSchemeResponse bet(Long userId, Map<String,String> paramMap, String partner);
	
	
}
