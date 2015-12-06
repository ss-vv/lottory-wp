package com.xhcms.lottery.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.xhcms.lottery.lang.EntityType;

public class ConvertUserScore {

	private static Logger logger = LoggerFactory.getLogger(ConvertUserScore.class);
	
	public static String convertScore(long score,int scoreType){
		//type 1:晒单  2：合买  3：合买流标
		if(score<=0 || scoreType<=0){
			return null;
		}else if(score>999){
			if(scoreType==EntityType.SHOW_SCORE){
				return "k9.png#d9.png#s9.png#";
			}else if(scoreType==EntityType.GROUP_SCORE){
				return "bk9.png#bd9.png#bs9.png#";
			}else{
				return null;
			}
		}
		StringBuffer scorePic = new StringBuffer();
		int crown = new Long(score).intValue()/100;
		int diamond = new Long(score).intValue()%100/10;
		int star = new Long(score).intValue()%100%10;
		if(scoreType==EntityType.SHOW_SCORE){
			if(crown>0){
				scorePic.append("k"+crown+".png#");
			}
			if(diamond>0){
				scorePic.append("d"+diamond+".png#");
			}
			if(star>0){
				scorePic.append("s"+star+".png#");
			}
		}else if(scoreType==EntityType.GROUP_SCORE){
			if(crown>0){
				scorePic.append("bk"+crown+".png#");
			}
			if(diamond>0){
				scorePic.append("bd"+diamond+".png#");
			}
			if(star>0){
				scorePic.append("bs"+star+".png#");
			}
		}else{
			logger.error("不支持的类型--");
			return null;
		}

		return scorePic.toString();
	}
}
