package com.xhcms.ucenter.persistent.service;

import com.xhcms.lottery.commons.persist.service.VerifyException;

public interface IVerifyService {
	
	/**
	 * 生成验证码
	 * 
	 * @param uid
	 * @param target : email mobile
	 * @return
	 */
	String generateCode(long uid, String target);
	
	/**
	 * 检验验证码有效性
	 * 
	 * @param uid
	 * @param code
	 * @return 
	 * @throws VerifyException 
	 */
	void verify(long uid, String code) throws VerifyException;
	
	
	/**
	 * 解除手机绑定
	 * 
	 * @param uid
	 */
	void unbindMobile(long uid,String code);

	/**
	 * 找到与验证码配对的手机号
	 * @param verifyCode
	 * @return
	 */
	String getPhoneNumberOfVerifyCode(String verifyCode);

	boolean haveVerifyCode(String verifyCode);
	
	String haveVerifyCodeByPhoneNumber(String phoneNumber);
}
