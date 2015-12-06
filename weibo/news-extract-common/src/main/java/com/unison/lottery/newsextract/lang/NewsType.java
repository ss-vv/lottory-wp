package com.unison.lottery.newsextract.lang;

public enum NewsType {
	
	FootBall,BasketBall;
	
	public static NewsType getWithName(String newsType){
		if(newsType != null){
			if(newsType.endsWith(NewsType.BasketBall.name())){
				return NewsType.BasketBall;
			} else if(newsType.endsWith(NewsType.FootBall.name())){
				return NewsType.FootBall;
			} else {
				return null ;
			}
		} 
		return null;
	}
}
