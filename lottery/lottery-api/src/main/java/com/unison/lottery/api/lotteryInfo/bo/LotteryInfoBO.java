package com.unison.lottery.api.lotteryInfo.bo;

import java.util.Map;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.response.model.BonusResultResponse;
import com.unison.lottery.api.protocol.response.model.LotteryResultResponse;

/**
 * @desc 开奖信息查询接口
 * @createTime 2012-11-30
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public interface LotteryInfoBO {

	/**
	 * 查询开奖信息
	 * @param uesr
	 * @return
	 */
	BonusResultResponse queryLotteryInfo(User user);
	
	/**
	 * 彩种开奖信息
	 * @param user
	 * @param m
	 * @return
	 */
	LotteryResultResponse lotteryResult(User user, Map<String, String> m);
}