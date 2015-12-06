package com.xhcms.lottery.commons.utils.internal;

import com.xhcms.lottery.lang.PlayType;

/**
 * 江西 11选 5 奖金计算器。
 * 
 * @author Yang Bo
 */
public class JX11BonusComputer {
	public enum JX11Bonus {
		R1(PlayType.JX11_R1, 13),
		R2(PlayType.JX11_R2, 6),
		R3(PlayType.JX11_R3, 19),
		R4(PlayType.JX11_R4, 78),
		R5(PlayType.JX11_R5, 540),
		R6(PlayType.JX11_R6, 90),
		R7(PlayType.JX11_R7, 26),
		R8(PlayType.JX11_R8, 9),
		D2(PlayType.JX11_D2, 130),
		G2(PlayType.JX11_G2, 65),
		D3(PlayType.JX11_D3, 1170),
		G3(PlayType.JX11_G3, 195)
		;
		
		private PlayType playType;
		private int bonusPerNote;
		
		/**
		 * @param bonusPerNote 单注奖金
		 */
		JX11Bonus(PlayType playType, int bonusPerNote){
			this.playType = playType;
			this.bonusPerNote = bonusPerNote;
		}
		
		public int getBonusPerNote(){
			return bonusPerNote;
		}
		
		/**
		 * @return 赔率
		 */
		public float getOdds() {
			return bonusPerNote / 2.0f;
		}
		
		public static JX11Bonus valueOfPlayType(PlayType playType){
			for (JX11Bonus bonus : JX11Bonus.values()){
				if (bonus.playType == playType){
					return bonus;
				}
			}
			throw new IllegalArgumentException("Unknown play type for JX11Bonus: " + playType);
		}
	}

	/**
	 * 计算可能的奖金
	 * @param playType 玩法类型
	 * @param totalNotes 总注数
	 * @return 奖金，单位元。
	 */
	public static int computeExpectBonus(PlayType playType, int totalNotes) {
		JX11Bonus bonus = JX11Bonus.valueOfPlayType(playType);
		return bonus.getBonusPerNote() * totalNotes;
	}
}
