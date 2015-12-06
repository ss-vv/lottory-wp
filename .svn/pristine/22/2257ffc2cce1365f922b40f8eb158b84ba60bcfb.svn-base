package com.unison.lottery.weibo.web.util;

import java.util.Comparator;
import com.xhcms.lottery.commons.data.HotAndRecommendMatch;
import com.xhcms.lottery.lang.LotteryId;

public class MatchWeiboComparator implements Comparator<HotAndRecommendMatch>{

	@Override
	public int compare(HotAndRecommendMatch o1, HotAndRecommendMatch o2) {
		 
	       if( Integer.parseInt(o1.getRecommendCount())>Integer.parseInt(o2.getRecommendCount())){
	    	   
	    	   return -1;
	       }else if(Integer.parseInt(o1.getRecommendCount())==Integer.parseInt(o2.getRecommendCount())){
	    	   if(o1.getLottery().equals(LotteryId.JCZQ.name())){
	    		   
	    		   return -1;
	    	   }else if(o1.getLottery().equals(LotteryId.JCLQ.name())){
	    		   
	    		   return -1;
	    	   }else {
	    		   
	    		   return 0;
	    	   }
	    	   
	       }else{
	    	   return 1;
	       }
		   
	}



}
