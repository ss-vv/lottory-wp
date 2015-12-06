package com.xhcms.lottery.commons.utils.internal;

import com.xhcms.lottery.lang.PlayType;

/**
 * 计算福彩3D的最大奖金。
 */
public class FC3DBonusComputer {
	
	public enum FC3DBonus {
		//直选奖：定位中三码
		FC3D_ZXDS(PlayType.FC3D_ZXDS, 1040),
		FC3D_ZXFS(PlayType.FC3D_ZXFS, 1040),
		FC3D_ZXHZ(PlayType.FC3D_ZXHZ, 1040),
		
		//组选三奖:不定位中三码
		FC3D_ZX_DS(PlayType.FC3D_ZX_DS, 346),
		FC3D_Z3FS(PlayType.FC3D_Z3FS, 346),
		FC3D_Z3HZ(PlayType.FC3D_Z3HZ, 346),
		
		//组选六奖:不定位中三码
		FC3D_Z6FS(PlayType.FC3D_Z6FS, 173),
		FC3D_Z6HZ(PlayType.FC3D_Z6HZ, 173),
		
		//单选包号
		FC3D_DXBH(PlayType.FC3D_DXBH, 173),
		;
		
		private PlayType playType;
		private int bonusPerNote;
		
		/**
		 * @param bonusPerNote 单注奖金
		 */
		FC3DBonus(PlayType playType, int bonusPerNote){
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
		
		public static FC3DBonus valueOfPlayType(PlayType playType){
			for (FC3DBonus bonus : FC3DBonus.values()){
				if (bonus.playType == playType){
					return bonus;
				}
			}
			throw new IllegalArgumentException("Unknown play type for FC3DBonus: " + playType);
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
		FC3DBonus bonus = FC3DBonus.valueOfPlayType(playType);
		return bonus.getBonusPerNote();
	}
}
