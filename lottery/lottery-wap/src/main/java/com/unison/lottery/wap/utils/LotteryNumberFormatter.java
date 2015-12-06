package com.unison.lottery.wap.utils;

import org.apache.commons.lang3.StringUtils;
import com.xhcms.lottery.commons.data.BetOption;

/**
 * 彩票号码格式化类.
 * 
 * @createTime 2013-2-1
 * @version 1.0
 * 
 * @author zhangdebin
 */
public class LotteryNumberFormatter {

	/**
	 * 提供用于获取从当前时间开始往前的一周日期字符串集合,日期字符串格式:"yyyy-MM-dd"
	 * 
	 * @return
	 */
	public static String format4SSQ(String numberString) {
		String htmlFormatString = "";
		if (StringUtils.isEmpty(numberString)) {
			htmlFormatString = "";
		}
		else {
			String[] lotteryNumbers = numberString.split(BetOption.BAR_REGEXP);
			if (lotteryNumbers != null && lotteryNumbers.length == 2) {
				String redBalls = lotteryNumbers[0];
				String blueBalls = lotteryNumbers[1];

				if (redBalls.indexOf(BetOption.AT_SYMBOL) <= 0) {
					htmlFormatString = "红球:<span style='color:red'>" + redBalls + "</span> 蓝球:<span style='color:blue'>" + blueBalls + "</span>";
				}
				else {
					String[] danTuoNumbers = redBalls.split(BetOption.AT_SYMBOL);

					if (danTuoNumbers != null && danTuoNumbers.length == 2) {
						htmlFormatString = "胆码:<span style='color:red'>" + danTuoNumbers[0] + "</span> 拖码:<span style='color:red'>" + danTuoNumbers[1] + "</span> 蓝球:<span style='color:blue'>" + blueBalls + "</span>";
					}
				}
			}
		}

		return htmlFormatString;
	}
}
