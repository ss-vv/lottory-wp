package com.xhcms.lottery.commons.utils;

import org.apache.commons.lang3.StringUtils;

import antlr.debug.GuessingEvent;

import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPlayPO;
import com.xhcms.lottery.commons.util.OptionUtils;
import com.xhcms.lottery.lang.Constants;

public class WinOptionUtils {

	public static String makeFBWinOptionByMatchResult(FBMatchPO fbMatchPO,
			FBMatchPlayPO fbMatchPlayPO) {
		if (null != fbMatchPO && null != fbMatchPlayPO
				&& StringUtils.isNotBlank(fbMatchPlayPO.getPlayId())
				&& fbMatchPlayPO.getPlayId().length() >= 2
				&& StringUtils.isNotBlank(fbMatchPO.getHalfScorePreset())
				&&StringUtils.isNotBlank(fbMatchPO.getScorePreset())) {
			int sortPlayId = Integer.parseInt(fbMatchPlayPO.getPlayId().substring(0, 2));
			return OptionUtils.zcWinOption(sortPlayId, fbMatchPO.getConcedePoints(),
					fbMatchPO.getHalfScorePreset(), fbMatchPO.getScorePreset());
		}
		return null;
		
	}

	public static String makeBBWinOptionByMatchResult(BBMatchPO bbMatchPO,
			BBMatchPlayPO bbMatchPlayPO, String concedePoints) {
		if(null!=bbMatchPO&&null!=bbMatchPlayPO){
			String playId = bbMatchPlayPO.getPlayId();
			if(StringUtils.isNotBlank(playId)&&playId.length()>=2){
				int sortPlayId = Integer.parseInt(playId.substring(0,2));
				if(StringUtils.isNotBlank(bbMatchPO.getFinalScorePreset())){
					float letScore=0;//让分
					float guessScore=0;//预设分值
					if(sortPlayId==Constants.PLAY_06){//让分胜负
						letScore=Float.valueOf(concedePoints);
					}
					else if(sortPlayId==Constants.PLAY_09){//大小分
						guessScore=Float.valueOf(concedePoints);
					}
					
					
					return OptionUtils.lcWinOption(sortPlayId, letScore, guessScore, bbMatchPO.getFinalScorePreset());
				}
			}
		}
		return null;
	}

}

