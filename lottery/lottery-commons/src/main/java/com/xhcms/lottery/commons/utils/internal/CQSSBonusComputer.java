package com.xhcms.lottery.commons.utils.internal;

import com.xhcms.lottery.lang.PlayType;

/**
 * 计算重庆时时彩的最大奖金。
 * @author zhangdebin, Yang Bo
 *
 */
public class CQSSBonusComputer {
	
	public enum CQSSBonus {
		DXDS(PlayType.CQSS_DXDS, 4),              // 大小单双：竞猜开奖号码最后两位的大小单双，分别选择十位、个位投注，全部猜中则中奖，奖金4元
		X1_DS(PlayType.CQSS_1X_DS, 10),           // 一星单式：竞猜开奖号个位，单注选号与开奖号码个位一致即中奖，单注奖金10元
		X2_DS(PlayType.CQSS_2X_DS, 100),          // 二星直选单式：竞猜开奖号码的最后两位，分别选择十位和个位的1个或多个号码投注，单注奖金100元
		X2_FS(PlayType.CQSS_2X_FS, 100),          // 二星直选复式：竞猜开奖号码的最后两位，分别选择十位和个位的1个或多个号码投注，单注奖金100元
		X2_HZ(PlayType.CQSS_2X_HZ, 100),          // 二星直选和值：竞猜开奖号码最后两位的数字相加之和，选择1个或多个和值号码投注，奖金100元
		X2_ZX_DS(PlayType.CQSS_2X_ZX_DS, 50),     // 二星组选单式：竞猜开奖号码的最后两位，选择2个或以上号码投注，奖金50元(开出对子不算中奖)
		X2_ZX_ZH(PlayType.CQSS_2X_ZX_ZH, 50),     // 二星组选组合(二星组选复式)：竞猜开奖号码的最后两位，选择2个或以上号码投注，奖金50元(开出对子不算中奖)
		X2_ZX_FZ(PlayType.CQSS_2X_ZX_FZ, 100),    // 二星组选分组：竞猜开奖号码的最后两位，分别选择十位和个位的1个或多个号码投注，单注奖金100元
		X2_ZX_HZ(PlayType.CQSS_2X_ZX_HZ, 50),     // 二星组选和值：选择1个和值投注，所选和值与开奖号码后两位和值一致即中奖50元！（对子号算直选，奖金100元）
		X2_ZX_BD(PlayType.CQSS_2X_ZX_BD, 100),    // 二星组选包胆：竞猜开奖号码的后二位的胆码，任意一位开出即中奖，开奖号码为对子奖金100元;非对子奖金50元
		X3_DS(PlayType.CQSS_3X_DS, 1000),         // 三星直选单式：竞猜开奖号码的后三位，分别选择百位、十位和个位的1个或多个号码投注，单注奖金1000元
		X3_FS(PlayType.CQSS_3X_FS, 1000),         // 三星直选复式：竞猜开奖号码的后三位，分别选择百位、十位和个位的1个或多个号码投注，单注奖金1000元
		X3_ZH(PlayType.CQSS_3X_ZH, 1110),         // 三星组合(三星复选)：相当于同时购买三星，二星，一星 一个单式号码 注数为3
		X3_ZH_FS(PlayType.CQSS_3X_ZH_FS, 1000),   // 三星组合复式(三星直选组合)：竞猜开奖号码的后三位，分别选择百位、十位和个位的1个或多个号码投注，单注奖金1000元
		X3_HZ(PlayType.CQSS_3X_HZ, 1000),         // 三星直选和值：竞猜开奖号码后面三位的数字相加之和，选择1个或多个和值号码投注，奖金1000元
		X3_Z3_DS(PlayType.CQSS_3X_Z3_DS, 320),    // 三星组三单式：选择2个或以上号码投注,与当期开奖号码的后三位(有对子)全部相同(顺序不限)即中奖,奖金320元
		X3_Z3_FS(PlayType.CQSS_3X_Z3_FS, 320),    // 三星组三复式：选择2个或以上号码投注,与当期开奖号码的后三位(有对子)全部相同(顺序不限)即中奖,奖金320元
		X3_Z6_DS(PlayType.CQSS_3X_Z6_DS, 160),    // 三星组六单式：选择3个或以上号码投注,与当期开奖号码的后三位(无对子)全部相同(顺序不限)即中奖,奖金160元
		X3_Z6_FS(PlayType.CQSS_3X_Z6_FS, 160),    // 三星组六单式：选择3个或以上号码投注,与当期开奖号码的后三位(无对子)全部相同(顺序不限)即中奖,奖金160元
		X3_ZX_HZ(PlayType.CQSS_3X_ZX_HZ, 1000),   // 三星组选和值：竞猜开奖号码后面三位的数字相加之和，选择1个或多个和值号码投注，奖金1000元
		X3_ZX_BD(PlayType.CQSS_3X_ZX_BD, 1000),   // 三星组选包胆：竞猜开奖号码后三位之和，开奖号码为豹子奖金1000元;组3奖金320元;组6奖金160元
		X5_DS(PlayType.CQSS_5X_DS, 100000),       // 五星直选单式：万、千、百、十、个位至少各选1个号码，单注选号与开奖号码按位一致即中奖，奖金10万元
		X5_FS(PlayType.CQSS_5X_FS, 100000),       // 五星直选复式：万、千、百、十、个位至少各选1个号码，单注选号与开奖号码按位一致即中奖，奖金10万元
		X5_TX(PlayType.CQSS_5X_TX, 20440),        // 五星通选：每行至少选1号码，三个奖级通吃，五次中奖机会，大奖20,440元
		X5_ZH(PlayType.CQSS_5X_ZH, 101110)        // 五星组合(五星复选)：相当于同时购买五星，三星，二星，一星 一个单式号码 注数为4
		;
		
		private PlayType playType;
		private int bonusPerNote;
		
		/**
		 * @param bonusPerNote 单注奖金
		 */
		CQSSBonus(PlayType playType, int bonusPerNote){
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
		
		public static CQSSBonus valueOfPlayType(PlayType playType){
			for (CQSSBonus bonus : CQSSBonus.values()){
				if (bonus.playType == playType){
					return bonus;
				}
			}
			throw new IllegalArgumentException("Unknown play type for CQSSBonus: " + playType);
		}
	}

	/**
	 * 计算可能的奖金
	 * 
	 * @param playType 玩法类型
	 * @param totalNotes 总注数
	 * @return 奖金，单位元。
	 */
	public static int computeExpectBonus(PlayType playType, int totalNotes) {
		CQSSBonus bonus = CQSSBonus.valueOfPlayType(playType);
		return bonus.getBonusPerNote() * totalNotes;
	}
}
