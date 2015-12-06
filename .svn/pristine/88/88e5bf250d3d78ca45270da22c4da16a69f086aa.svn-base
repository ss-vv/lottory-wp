package com.xhcms.ucenter.persistent.service;



public interface IUserValidIdManager {

	String createValidIdByLoginName(String loginName);
	
	String createValidIdByPhoneNumber(String phoneNumber);

	/**
	 * 查看当前的validID是否有效，若有效，返回对应的用户id
	 * @param validId
	 * @return
	 */
	String findUserIdByValidIdAndCurrentTime(String validId);

	void updateExpiredTime(String validId, String userId);

	String createValidIdByUidAndType(String uid, String platType);

	
	
	

}
