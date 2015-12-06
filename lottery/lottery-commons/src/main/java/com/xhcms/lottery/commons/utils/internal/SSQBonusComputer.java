package com.xhcms.lottery.commons.utils.internal;

import com.xhcms.lottery.lang.PlayType;

/**
 * 计算双色球的最大奖金。
 * @author zhangdebin
 *
 */
public class SSQBonusComputer {
	
	public enum SSQBonus {
		SSQ_DS(PlayType.SSQ_DS, 5000000),  // 单式：7个号码相符（6个红色球号码和1个蓝色球号码），单注最高限额封顶500万元。
		SSQ_FS(PlayType.SSQ_FS, 5000000),  // 复式：7个号码相符（6个红色球号码和1个蓝色球号码），单注最高限额封顶500万元。
		SSQ_DT(PlayType.SSQ_DT, 5000000)   // 胆拖：7个号码相符（6个红色球号码和1个蓝色球号码），单注最高限额封顶500万元。
		;
		
		private PlayType playType;
		private int bonusPerNote;
		
		/**
		 * @param bonusPerNote 单注奖金
		 */
		SSQBonus(PlayType playType, int bonusPerNote){
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
		
		public static SSQBonus valueOfPlayType(PlayType playType){
			for (SSQBonus bonus : SSQBonus.values()){
				if (bonus.playType == playType){
					return bonus;
				}
			}
			throw new IllegalArgumentException("Unknown play type for SSQBonus: " + playType);
		}
	}

	/**
	 * 计算可能的奖金,不能作为实际的奖金计算.
	 * 
	 * @param playType 玩法类型
	 * @param totalNotes 总注数
	 * @return 奖金，单位元。
	 */
	public static int computeExpectBonus(PlayType playType, int totalNotes) {
		SSQBonus bonus = SSQBonus.valueOfPlayType(playType);
		return bonus.getBonusPerNote() * totalNotes;
	}
}
