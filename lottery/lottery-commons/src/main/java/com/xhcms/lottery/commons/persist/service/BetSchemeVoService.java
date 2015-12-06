package com.xhcms.lottery.commons.persist.service;

import com.xhcms.lottery.commons.data.BetScheme;
/*
 * 转换BetScheme
 */
public interface BetSchemeVoService {

	/**
	 * 将BetScheme转换为字节数组
	 * @param bet
	 * @return
	 */
	public byte[] getBetSchemeByte(BetScheme bet);
	/**
	 * 将字节数组转换为BetScheme
	 * @param b
	 * @return
	 */
	public BetScheme getBetScheme(byte b[]);
}
