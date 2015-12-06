package com.xhcms.lottery.commons.utils.internal;

import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.commons.persist.service.BetException;

/**
 * 出票内容格式验证器。确保投注内容格式满足出票平台约束。
 * @author Yang Bo
 */
public abstract class BetCodeValidator {	
	/**
	 * 对投注的号码进行格式验证。
	 * 
	 * @param playType, 投注玩法
	 * @param betCode, 投注号码
	 * @throws BetException, 投注格式不符合要求。
	 * @return
	 */
	public abstract void valid(String betCode, PlayType playType) throws BetException;
	
	/**
	 * 对投注的号码进行格式验证。
	 * 
	 * @param playType, 投注玩法
	 * @param betCodes, 投注号码
	 * @throws BetException, 投注格式不符合要求。
	 * @return
	 */
	public void valid(String[] betCodes, PlayType playType) throws BetException {
		if (playType != null && betCodes != null) {
			for (String tmpBetCode : betCodes) {
				this.valid(tmpBetCode, playType);
			}
		}
	}

	/**
	 * 对投注的号码进行格式验证。
	 * 
	 * @param playType, 投注玩法
	 * @param betCodes, 投注号码
	 * @throws BetException, 投注格式不符合要求。
	 * @return
	 */
	public void valid(PlayType playType, java.util.List<String> betCodes) throws BetException {
		if (playType != null && betCodes != null) {
			for (String tmpBetCode : betCodes) {
				this.valid(tmpBetCode, playType);
			}
		}
	}
}
