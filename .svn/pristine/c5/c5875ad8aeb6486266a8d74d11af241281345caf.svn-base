package com.xhcms.lottery.commons.data.fc3d;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.laicai.util.ComputerUtils;
import com.xhcms.lottery.commons.data.BetOption;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.exception.JXRuntimeException;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.lang.PlayType;

/**
 * 投注选项，主要是用来计算注数。
 */
public class FC3DBetOption extends BetOption {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 构造一个投注选项。
	 */
	public FC3DBetOption(PlayType playType, String option) throws BetException {
		super(playType, ChooseType.UNKNOWN, option);
	}

	/**
	 * 因为来彩玩法定义同中明玩法，所以不需要额外处理。
	 */
	@Override
	protected void computeBetType() {
	}

	@Override
	protected void computeNotes() throws BetException {
		int noteCount = 0;
		switch (playType) {
		case FC3D_ZXDS: // 福彩3D直选单式
		case FC3D_ZX_DS: // 福彩3D组选单式
			String[] notes = this.option.split(SEMICOLON);
			if (notes != null) {
				noteCount = notes.length;
			}
			break;
		case FC3D_ZXFS: // 福彩3D直选复式
			String[] noteNumbers = this.option.split(COMMA);
			if (noteNumbers.length > 0) {
				int calc = 1;
				for (int i = 0; i < noteNumbers.length; i++) {
					calc = calc * noteNumbers[i].length();
				}
				noteCount = calc;
			}
			break;
		case FC3D_ZXHZ: // 福彩3D直选和值
		case FC3D_Z3HZ: // 福彩3D组三和值
		case FC3D_Z6HZ: // 福彩3D组六和值
			notes = this.option.split(SEMICOLON);
			noteCount = 0;
			if (notes != null && notes.length > 0) {
				for (int i = 0; i < notes.length; i++) {
					noteCount += calcZXHZNotes(playType, notes[i]);
				}
			}
			break;
		case FC3D_Z3FS: // 福彩3D组三复式
		case FC3D_Z6FS: // 福彩3D组六复式
			notes = this.option.split(SEMICOLON);
			noteCount = 0;
			if (notes != null && notes.length > 0) {
				for (int i = 0; i < notes.length; i++) {
					String[] code = notes[i].split(COMMA);
					if (playType == PlayType.FC3D_Z3FS) {
						noteCount += (int) (ComputerUtils.combination(
								code.length, 2) * 2);
					} else if (playType == PlayType.FC3D_Z6FS) {
						noteCount += (int) ComputerUtils.combination(
								code.length, 3);
					}
				}
			}
			break;
		case FC3D_DXBH: // 单选包号
			notes = this.option.split(SEMICOLON);
			int betNotes = 0;
			for (int i = 0; i < notes.length; i++) {
				int len = notes[i].split(COMMA).length;
				betNotes += (len * len * len);
			}
			noteCount = betNotes;
			break;
		default:
			logger.error("Unknown playType {} for computing notes.", playType);
			break;
		}
		this.notes = noteCount;
	}

	/**
	 * 计算和值玩法选项对应的注数 
	 * @return
	 */
	private int calcZXHZNotes(PlayType playType, String value) {
		Integer notes = 0;
		if (playType == PlayType.FC3D_ZXHZ) {
			int option = Integer.parseInt(value);
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					for (int k = 0; k < 10; k++) {
						if (i + j + k == option) {
							notes++;
						}
					}
				}
			}
		} else if (playType == PlayType.FC3D_Z3HZ) {
			int option = Integer.parseInt(value);
			for (int i = 0; i < 10; i++) {
				for (int j = i + 1; j < 10; j++) {
					//保证组3和值，有两个数是一样
					if (i * 2 + j == option || i + 2 * j == option) {
						notes++;
					}
				}
			}
		} else if (playType == PlayType.FC3D_Z6HZ) {
			int option = Integer.parseInt(value);
			//遍历所有情况，保证组六和值，三个数都不同
			for (int i = 0; i < 10; i++) {
				for (int j = i + 1; j < 10; j++) {
					for (int k = j + 1; k < 10; k++) {
						if (i + j + k == option) {
							notes++;
						}
					}
				}
			}
		}
		if (0 == notes) {
			throw new JXRuntimeException(
					"Not calculate Bet Option notes error! PlayType="
							+ playType + ", code=" + value);
		}
		return notes;
	}

}
