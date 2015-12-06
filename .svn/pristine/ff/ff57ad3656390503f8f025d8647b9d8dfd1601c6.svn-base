package com.xhcms.ucenter.service.verify;

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
	 */
	void verify(long uid, String code);

	/**
	 * 检验验证码有效性 不更新状态
	 * 
	 * @param uid
	 * @param code
	 * @return 
	 */
	void verifyNotUpdate(long uid, String code);
	
}
