/**
 * 
 */
package com.xhcms.lottery.commons.util;

import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EnumMap;

/**
 * @author langhsu
 * 
 */
public class OptionUtils {
	/**
	 * 
	 * @param playId
	 * @param concedePoints
	 * @param halfScore
	 * @param score
	 * @return
	 */
	public static String zcWinOption(int playId, int concedePoints, String halfScore, String score) {
		if(("取消").equals(halfScore) || ("取消").equals(score)) {
			return "";
		}
		String[] arr = score.split(":");
		int home = Integer.parseInt(arr[0]);
		int guest = Integer.parseInt(arr[1]);
		
		String opt = "";
		
		switch (playId) {
			case Constants.PLAY_01: //胜平负
					int r = home + concedePoints - guest;
					opt = r > 0 ? "3" : (r < 0 ? "0" : "1");
				break;
			case Constants.PLAY_02: //比分
				opt= EnumMap.getZCOptKey(score);
				if (opt == null || opt.length() < 1) {
					opt = (home > guest) ? "90" : ((home < guest) ? "09" : "99");
				}
				break;
			case Constants.PLAY_03: //总进球
				int t = home + guest;
				if (t >= 7) {
					opt = "7";
				} else {
					opt= String.valueOf(t);
				}
				break;
			case Constants.PLAY_04: //胜负半全
				String[] halfScoreArr = halfScore.split(":");
				int halfHome = Integer.parseInt(halfScoreArr[0]);
				int halfGuest = Integer.parseInt(halfScoreArr[1]);
				
				opt = score2Result(halfHome, halfGuest);
				opt += score2Result(home, guest);
				break;
			case Constants.PLAY_80: //不让球胜平负
				r = home - guest;
				opt = r > 0 ? "3" : (r < 0 ? "0" : "1");
				break;
			
		}
		return opt;
	}
	//北京单场
	public static String bdWinOption(int playId, int concedePoints, String halfScore, String score){
		if(("取消").equals(halfScore) || ("取消").equals(score)) {
			return "";
		}
		String[] arr = score.split(":");
		int home = Integer.parseInt(arr[0]);
		int guest = Integer.parseInt(arr[1]);
		
		String opt = "";
		switch(playId){
		     case Constants.PLAY_100:  //胜平负
		    	    int r = home + concedePoints - guest;
					opt = r > 0 ? "3" : (r < 0 ? "0" : "1");
		    	  break;
		     case Constants.PLAY_101:  //进球数
		    	    int t = home + guest;
					if (t >= 7) {
						opt = "7";
					} else {
						opt= String.valueOf(t);
					}
		    	 break;
		     case Constants.PLAY_102:  //上下单双 
		    	    int total=home+guest;
		    	    if(total>=3){
		    	    	if(total%2==0){
		    	    		
		    	    		opt="12";//上双
		    	    	}else{
		    	    		
		    	    		opt="11";//上单
		    	    	}
		    	    	
		    	    }else{
                        if(total%2==0){
		    	    		
		    	    		opt="02";//下双
		    	    	}else{
		    	    		
		    	    		opt="01";//下单
		    	    	}
		    	    	
		    	    }
		    	  
		    	 break;
		     case Constants.PLAY_103:  //比分
		    	    opt= EnumMap.getZCOptKey(score);
					if (opt == null || opt.length() < 1) {
						opt = (home > guest) ? "90" : ((home < guest) ? "09" : "99");
					}
		    	 break;
		     case Constants.PLAY_104:  //半全场
		    	    String[] halfScoreArr = halfScore.split(":");
					int halfHome = Integer.parseInt(halfScoreArr[0]);
					int halfGuest = Integer.parseInt(halfScoreArr[1]);
					
					opt = score2Result(halfHome, halfGuest);
					opt += score2Result(home, guest);
		    	 break;
		     
		
		
		}
		return opt;
	}
	//北单胜负
	public static String bdSFWinOption(int playId,String result){
		
		String opt="";
		
		switch(playId){
		   case Constants.PLAY_105:
			    if("胜".equals(result)){
			    	
			    	opt="3";
			    }else if("负".equals(result)){
			    	
			    	opt="0";
			    }
			   break;
		   
		}
		
		return opt;
	}
	
	public static String lcWinOption(int playId, float letScore, float guessScore, String score) {
		String[] arr = score.split(":");
		float home = Float.parseFloat(arr[1]);
		float guest = Float.parseFloat(arr[0]);
		String opt = "";
		
		switch(playId) {
			case Constants.PLAY_06: //让分胜负
				opt = home + letScore > guest ? "1" : "2";
				break;
			case Constants.PLAY_07: //胜负
				opt = home > guest ? "1" : "2";
				break;
			case Constants.PLAY_08: // 胜分差
				opt = EnumMap.getLCOptKey(opt08((int)(home - guest)));
				break;
			case Constants.PLAY_09: // 大小分
				opt = (home + guest > guessScore) ? "1" : "2";
				break;
		}
		return opt;
	}
	
	private static String score2Result(int home, int guest) {
		return home > guest ? "3" : (home < guest ? "0" : "1");
	}
	
	private static String opt08(int diff) {
		String opt = "主胜";
		if(diff < 0) {
			opt = "客胜";
		}
		float d = Math.abs(diff);
		if(d >= 26) {
			return opt + "26+";
		}
		int times = (int) Math.ceil(d/5.0);
		return opt + (5*times - 4) + "-" + 5*times;
	}
	
//	public static void main(String[] args) {
//		System.out.println(sfcOption(5));
//		System.out.println(sfcOption(6));
//		System.out.println(sfcOption(22));
//		System.out.println(sfcOption(26));
//		System.out.println(sfcOption(27));
//		
//		System.out.println(sfcOption(-5));
//		System.out.println(sfcOption(-6));
//		System.out.println(sfcOption(-11));
//		System.out.println(sfcOption(-22));
//		System.out.println(sfcOption(-26));
//		System.out.println(sfcOption(-27));
//		
//	}
}
