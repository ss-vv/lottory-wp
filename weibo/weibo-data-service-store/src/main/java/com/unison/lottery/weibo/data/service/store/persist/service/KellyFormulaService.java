package com.unison.lottery.weibo.data.service.store.persist.service;

import java.math.BigDecimal;
import com.unison.lottery.weibo.data.service.store.data.KellyFormula;

/**
 * @desc 凯利指数的计算
 * @author lei.li@unison.net.cn
 * @createTime 2014-2-17
 * @version 1.0
 */
public interface KellyFormulaService {

	/**
	 * 根据球探赛事ID，算出足彩对应的平均胜率、平率、负率
	 * @param qtMatchId
	 * @return
	 */
	KellyFormula calFBArgRatio(long qtMatchId);
	
	KellyFormula calBBArgRatio(long qtMatchId);
	
	/**
	 * 计算返还率
	 * @param odds
	 * @return
	 */
	BigDecimal lossRatio(BigDecimal ... odds);
}