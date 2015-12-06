package com.xhcms.lottery.commons.data.cqss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.laicai.util.ComputerUtils;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.commons.data.BetOption;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.utils.internal.Number3XHezhiUtils;
import com.xhcms.lottery.commons.utils.internal.Number2XHezhiUtils;

/**
 * 重庆时时彩投注选项，主要是用来计算注数。
 * 
 * @author 张得斌, Yang Bo
 */
public class CQSSBetOption extends BetOption {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 构造一个重庆时时彩投注选项。
	 */
	public CQSSBetOption(PlayType playType, String option) throws BetException {
		super(playType, ChooseType.UNKNOWN, option);
	}

	/**
	 * 因为大V彩玩法定义同中明玩法，所以不需要额外处理。
	 */
	@Override
	protected void computeBetType() {
	}

	@Override
	protected void computeNotes() throws BetException {
		int noteCount = 0;
		switch (playType) {
			case CQSS_1X_DS:    // 一星单式
			case CQSS_DXDS:     // 大小单双
			case CQSS_2X_HZ:    // 二星直选和值
			case CQSS_2X_ZX_HZ: // 二星组选和值
			case CQSS_3X_HZ:    // 三星直选和值
			case CQSS_5X_TX:    // 五星通选

			case CQSS_2X_DS:    // 二星直选单式
			case CQSS_2X_ZX_DS: // 二星组选单式
			case CQSS_3X_DS:    // 三星直选单式
			case CQSS_3X_Z3_DS: // 三星组三单式
			case CQSS_3X_Z6_DS: // 三星组六单式
			case CQSS_5X_DS:    // 五星直选单式
				String[] notes = this.option.split(SEMICOLON);
				
				switch (playType) {
					case CQSS_1X_DS:
					case CQSS_DXDS:     // 大小单双
					case CQSS_5X_TX:    // 五星通选

					case CQSS_2X_DS:    // 二星直选单式
					case CQSS_2X_ZX_DS: // 二星组选单式
					case CQSS_3X_DS:    // 三星直选单式
					case CQSS_3X_Z3_DS: // 三星组三单式
					case CQSS_3X_Z6_DS: // 三星组六单式
					case CQSS_5X_DS:    // 五星直选单式
						noteCount = notes.length;
						break;
						
					case CQSS_2X_HZ:    // 二星直选和值
						for (String zhixuan2He : notes) {
							noteCount += Number2XHezhiUtils.getZhiXuanStakeCount(zhixuan2He);
						}
						break;
						
					case CQSS_2X_ZX_HZ: // 二星组选和值
						for (String zuxuan2He : notes) {
							noteCount += Number2XHezhiUtils.getZuXuanStakeCount(zuxuan2He);
						}
						break;
						
					case CQSS_3X_HZ:    // 三星直选和值
						for (String tmpZhixuan3He : notes) {
							noteCount += Number3XHezhiUtils.getZhiXuanStakeCount(tmpZhixuan3He);
						}
						break;
				}
				break;
				
			default:
				String[] digits = this.option.split(COMMA);

				switch (playType) {
					case CQSS_2X_FS:    // 二星直选复式
					case CQSS_3X_FS:    // 三星直选复式
					case CQSS_5X_FS:    // 五星直选复式
						noteCount = 1;
						for (int i = 0; i < digits.length; i++) {
							noteCount *= digits[i].length();
						}
						break;
						
					case CQSS_2X_ZX_ZH: // 二星组选组合(二星组选复式)
						noteCount = (int)ComputerUtils.combination(this.option.length(), 2);
						break;

					case CQSS_3X_Z3_FS: // 三星组三复式
						noteCount = this.option.length() * (this.option.length() - 1);
						break;

					case CQSS_3X_Z6_FS: // 三星组六复式
						noteCount = (int)ComputerUtils.combination(this.option.length(), 3);
						break;
						
					case CQSS_2X_ZX_FZ: // 二星组选分组
					case CQSS_2X_ZX_BD: // 二星组选包胆
					case CQSS_3X_ZH_FS: // 三星组合复式
						break;

					case CQSS_3X_ZX_HZ: // 三星组选和值
					case CQSS_3X_ZX_BD: // 三星组选包胆
						break;

					case CQSS_2X_ZH:    // 二星组合(二星复选)
					case CQSS_3X_ZH:    // 三星组合(五星复选)
					case CQSS_5X_ZH:    // 五星组合(五星复选)
						break;

					default:
						logger.error("Unknown playType {} for computing notes.", playType);
						break;
				}
				break;
		}
		this.notes = noteCount;
	}

}
