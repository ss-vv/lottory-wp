package com.xhcms.lottery.commons.utils.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.commons.persist.service.BetException;

/**
 * 出票内容格式验证器。确保投注内容格式满足出票平台约束。
 * @author Yang Bo
 */
public class CQSSBetCodeValidator extends BetCodeValidator{
	
	private static java.util.Map<PlayType, Pattern> playTypePatterns = new java.util.HashMap<PlayType, Pattern>();
	static {
		playTypePatterns.put(PlayType.CQSS_DXDS,     Pattern.compile("^([1-4]{2}(;|;?$))+")); // 大小单双
		playTypePatterns.put(PlayType.CQSS_1X_DS,    Pattern.compile("^(\\d{1}(;|;?$))+"));   // 一星直选单式
		playTypePatterns.put(PlayType.CQSS_2X_DS,    Pattern.compile("^(\\d{2}(;|;?$))+"));   // 二星直选单式
		playTypePatterns.put(PlayType.CQSS_3X_DS,    Pattern.compile("^(\\d{3}(;|;?$))+"));   // 三星直选单式
		playTypePatterns.put(PlayType.CQSS_5X_DS,    Pattern.compile("^(\\d{5}(;|;?$))+"));   // 五星直选单式
		playTypePatterns.put(PlayType.CQSS_5X_TX,    Pattern.compile("^(\\d{5}(;|;?$))+"));   // 五星通选
		
		playTypePatterns.put(PlayType.CQSS_2X_FS,    Pattern.compile("^(\\d{1,10},){1}\\d{1,10}$")); // 二星直选复式
		playTypePatterns.put(PlayType.CQSS_3X_FS,    Pattern.compile("^(\\d{1,10},){2}\\d{1,10}$")); // 三星直选复式
		playTypePatterns.put(PlayType.CQSS_5X_FS,    Pattern.compile("^(\\d{1,10},){4}\\d{1,10}$")); // 五星直选复式
		
		playTypePatterns.put(PlayType.CQSS_2X_HZ,    Pattern.compile("^([0-9]|[1][0-8]){1}(;([0-9]|[1][0-8])){0,18}")); // 二星直选和值
		playTypePatterns.put(PlayType.CQSS_2X_ZX_HZ, Pattern.compile("^([0-9]|[1][0-8]){1}(;([0-9]|[1][0-8])){0,18}")); // 二星组选和值
		playTypePatterns.put(PlayType.CQSS_3X_HZ,    Pattern.compile("^([0-9]|[1][0-9]|[2][0-7]){1}(;([0-9]|[1][0-9]|[2][0-7])){0,27}")); // 三星直选和值
		playTypePatterns.put(PlayType.CQSS_3X_ZX_HZ, Pattern.compile("^([0-9]|[1][0-9]|[2][0-7]){1}(;([0-9]|[1][0-9]|[2][0-7])){0,27}")); // 三星组选合值
		
		playTypePatterns.put(PlayType.CQSS_2X_ZH,    Pattern.compile("\\d{2,10}")); // 二星组合(二星复选)
		playTypePatterns.put(PlayType.CQSS_3X_ZH,    Pattern.compile("\\d{3,10}")); // 三星组合(三星复选)
		playTypePatterns.put(PlayType.CQSS_5X_ZH,    Pattern.compile("\\d{5,10}")); // 五星组合(五星复选)
		
		playTypePatterns.put(PlayType.CQSS_2X_ZX_DS, Pattern.compile("^(\\d{2}(;|;?$))+")); // 二星组选单式
		playTypePatterns.put(PlayType.CQSS_2X_ZX_ZH, Pattern.compile("\\d{2,10}")); // 二星组选组合(二星组选复式)
		playTypePatterns.put(PlayType.CQSS_2X_ZX_FZ, Pattern.compile("")); // 二星组选分组
		playTypePatterns.put(PlayType.CQSS_2X_ZX_BD, Pattern.compile("")); // 二星组选包胆
		playTypePatterns.put(PlayType.CQSS_3X_ZH_FS, Pattern.compile("")); // 三星组合复式
		playTypePatterns.put(PlayType.CQSS_3X_Z3_DS, Pattern.compile("\\d{3}"));    // 三星组三单式
		playTypePatterns.put(PlayType.CQSS_3X_Z6_DS, Pattern.compile("\\d{3}"));    // 三星组六单式
		playTypePatterns.put(PlayType.CQSS_3X_Z3_FS, Pattern.compile("\\d{2,10}")); // 三星组三复式
		playTypePatterns.put(PlayType.CQSS_3X_Z6_FS, Pattern.compile("\\d{3,10}")); // 三星组六复式
		playTypePatterns.put(PlayType.CQSS_3X_ZX_BD, Pattern.compile("")); // 三星组选包胆
	}
	
	/**
	 * 对投注的号码进行格式验证。
	 * 
	 * @param playType, 投注玩法
	 * @param betCode, 投注号码
	 * @throws BetException, 投注格式不符合要求。
	 * @return
	 */
	@Override
	public void valid(String betCode, PlayType playType) throws BetException {
		boolean checkResult = false;
		
		Pattern pattern = playTypePatterns.get(playType);
		if (pattern != null) {
			Matcher matcher = pattern.matcher(betCode);
			checkResult = matcher.matches();
			
			if (checkResult) {
				switch (playType) {
					case CQSS_DXDS: // 大小单双
						break;
					case CQSS_1X_DS: // 一星单式. 完成
						break;
					case CQSS_2X_DS: // 二星直选单式. 完成
						break;
					case CQSS_2X_FS: // 二星直选复式. 完成
						break;
					case CQSS_2X_ZH: // 二星组合
					case CQSS_2X_HZ: // 二星直选和值
					case CQSS_2X_ZX_DS: // 二星组选单式
					case CQSS_2X_ZX_ZH: // 二星组选组合
					case CQSS_2X_ZX_FZ: // 二星组选分组
					case CQSS_2X_ZX_HZ: // 二星组选和值
					case CQSS_2X_ZX_BD: // 二星组选包胆
						break;
					case CQSS_3X_DS: // 三星单式
						break;
					case CQSS_3X_FS: // 三星复式. 完成
						break;
					case CQSS_3X_ZH: // 三星组合
					case CQSS_3X_ZH_FS: // 三星组合复式
						break;
					case CQSS_3X_HZ: // 三星直选和值
						break;
					case CQSS_3X_Z3_DS: // 三星组三单式
						break;
					case CQSS_3X_Z3_FS: // 三星组三复式
						break;
					case CQSS_3X_Z6_FS: // 三星组六复式
						break;
					case CQSS_3X_ZX_HZ: // 三星组选合值
					case CQSS_3X_ZX_BD: // 三星组选包胆
					case CQSS_3X_Z6_DS: // 三星组六单式
						break;
					case CQSS_5X_TX: // 五星通选
						break;
					case CQSS_5X_DS: // 五星单式
						break;
					case CQSS_5X_FS: // 五星复式
						break;
					case CQSS_5X_ZH: // 五星组合
						break;
				}
			}
		}

		if (!checkResult) {
			throw new BetException("Invalid bet content: " + betCode + " || pattern: " + pattern.pattern());
		}
	}
}
