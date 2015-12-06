package com.xhcms.lottery.utils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.xhcms.lottery.lang.Constants;

public final class ResultUtils {
    private ResultUtils(){}
    
	/**
	 * 查询投注选项的赔率
	 * @param playId	玩法
	 * @param opts		投注选项
	 * @param options	赛事选项
	 * @param odds		赛事赔率
	 * @return	投注投注现象的赔率
	 */
	public static String resolveOdds(String playId, String opts, String[] options, String[] odds) {
		if(StringUtils.isEmpty(opts)){
			return "0.00";
		}
		int step = 1;
		if (playId.startsWith(Constants.PLAY_02_ZC) || playId.startsWith(Constants.PLAY_04_ZC)
				|| playId.startsWith(Constants.PLAY_08_LC)) {
			step = 2;
		}
		
		if(playId.startsWith(Constants.PLAY_03_BD_SXDS)||playId.startsWith(Constants.PLAY_04_BD_BF)
				||playId.startsWith(Constants.PLAY_05_BD_BQC)){
			
			step = 2;
		}

		StringBuilder buf = new StringBuilder(200);
		for (int i = 0; i < opts.length(); i += step) {
			buf.append(odds[ArrayUtils.indexOf(options, opts.substring(i, i + step))]).append(',');
		}
		return buf.deleteCharAt(buf.length() - 1).toString();
	}
	
}
