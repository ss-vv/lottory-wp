package com.unison.lottery.pm.lang;

import java.math.BigDecimal;

public class WinGrant {

	public static class promotion{
		/**2012.6竞彩足彩活动ID*/
		public static final long jczc_2012_6 = 1;
		//2012.6竞彩篮彩活动ID
		public static final long jclc_2012_6 = 3;
		//2012.7奥运竞彩足彩活动ID
		public static final long jczc_olympic = 5;
		//2012.7奥运竞彩篮彩活动ID
		public static final long jclc_olympic = 6;
		//2012.8竞彩足彩活动ID
		public static final long jczc_2012_8 = 7;
		//2012.8竞彩篮彩活动ID
		public static final long jclc_2012_8= 8;
		/** 竞彩足彩2@1未中奖赠款活动 */
		public static final long jczc_2012_10_11_2C1_notWin= 11;
		/** 竞彩篮彩2@1未中奖赠款活动 */
		public static final long jclc_2012_11_2C1_notWin= 12;
		/** 2012.11竞彩足彩2@1中奖加奖赠款 */
		public static final long jczc_2012_11_2C1_Win= 16;
		/** 2012.11竞彩篮彩2@1中奖加奖赠款 */
		public static final long jclc_2012_11_2C1_Win= 17;
		
		/** 竞彩足彩2@1未中奖赠款12月活动 */
		public static final long jczc_2012_12_2C1_notWin= 21;
		/** 竞彩篮彩2@1未中奖赠款12月活动 */
		public static final long jclc_2012_12_2C1_notWin= 22;
		

	}


	//赠款金额
	public static final BigDecimal grantAmount = new BigDecimal(0.05);
	//发起人ID
	public static final int operatorId = 0;

	public static class submitStatus{
		/**未提交*/
		public static final int unSubmit = 0;//未提交
		/**已提交*/
		public static final int submited = 1;//已提交
		/** 已提交 超次数 */
		public static final int submited_out_count = 2;//已提交 超次数
	}
	
	public static class GrantStatus{
		/**未赠送*/
		public static final int unGrant = 0;
		/**已赠送*/
		public static final int suGranted = 1;
		/** 超过最大赠送次数 */
		public static final int granted_out_count = 2;
		/**赠送发现用户ID对应同一个手机号*/
		public static final int granted_more_userId = 3;
	}
	
	public static class promotionStatus{
		/**无效活动*/
		public static final int invalid = 0;//无效
		/**有效活动*/
		public static final int valid  = 1;//有效
	}
}