package com.xhcms.lottery.commons.data.ssq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.laicai.util.ComputerUtils;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.commons.data.BetOption;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.persist.service.BetException;

/**
 * 双色球投注选项，主要是用来计算注数。
 * 
 * @author zhangdebin
 */
public class SSQBetOption extends BetOption {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 构造一个双色球投注选项。
	 */
	public SSQBetOption(PlayType playType, String option) throws BetException {
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
			case SSQ_DS: // 单式
				String[] notes = this.option.split(SEMICOLON);
				if (notes != null) {
					noteCount = notes.length;
				}
				break;

			case SSQ_FS: // 复式
			case SSQ_DT: // 胆拖
				int redCombCount = 0;  // 红球组合数：复式(m选6);胆拖;
				int blueCombCount = 0; // 蓝球组合数：复式(m选1);胆拖(m选1);

				String[] noteNumbers = this.option.split(BAR_REGEXP);
				String[] noteRedNumbers;
				String[] noteBlueNumbers;
				String noteRedNumber = noteNumbers[0];
				String noteBlueNumber = noteNumbers[1];
				switch (playType) {
					case SSQ_FS: // 复式
						noteRedNumbers = noteRedNumber.split(COMMA);
						noteBlueNumbers = noteBlueNumber.split(COMMA);
						redCombCount = (int)ComputerUtils.combination(noteRedNumbers.length, 6);
						blueCombCount = noteBlueNumbers.length;
						noteCount = redCombCount * blueCombCount;
						break;

					case SSQ_DT: // 胆拖
						noteRedNumbers = noteRedNumber.split("\\),"); // 中民卓彩的出票格式: (01,02,03,04,05),06,07,08|01
						String tmpDanNumber = noteRedNumbers[0];
						String tmpTuoNumber = noteRedNumbers[1];

						int tmpDanMaximum = 6 - tmpDanNumber.split(COMMA).length;
						redCombCount = (int)ComputerUtils.combination(tmpTuoNumber.split(COMMA).length, tmpDanMaximum);
						blueCombCount = noteBlueNumber.split(COMMA).length;
						noteCount = redCombCount * blueCombCount;
						break;
				}
				break;
				
			default:
				logger.error("Unknown playType {} for computing notes.", playType);
				break;
		}
		this.notes = noteCount;
	}
}
