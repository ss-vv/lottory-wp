package com.unison.lottery.api.redEnvalope.bo;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.response.model.SendRedEnvalopeResponse;

public interface SendRedEnvalopeBo {
	/**
	 * 生成红包
	 * @param redEnvalopeAmount 红包金额
	 * @param envalopeNum 红包数量
	 * @param user
	 * @param groupId 要发到的群id
	 * @param hxUserName 环信用户名称
	 * @param greetings 祝福语
	 * @return
	 */
	SendRedEnvalopeResponse sendRedEnvalope(Long redEnvalopeAmount, Integer envalopeNum, User user, String groupId, String hxUserName,String greetings);
}
