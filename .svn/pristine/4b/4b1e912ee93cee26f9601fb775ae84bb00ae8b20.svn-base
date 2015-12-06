package com.unison.lottery.weibo.data.store.lancher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.unison.lottery.weibo.data.service.store.persist.service.DataQueryStoreService;

public class DataInterfaceQueryAndStoreLancher {
	
	private static Logger logger=LoggerFactory.getLogger(DataInterfaceQueryAndStoreLancher.class);
	

	public static void main(String[] args){
		logger.info("DataInterfaceQueryAndStoreLancher starting...");
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[]{"classpath:spring-db.xml","classpath:spring-service.xml"});
		DataQueryStoreService dataQueryStoreService=(DataQueryStoreService) context.getBean("dataQueryStoreService");
		if(null!=args&&args.length>=1){
			String cmd=args[0];
			switch(cmd){
			case "queryQTMatchIdAndStore":{dataQueryStoreService.queryQTMatchIdAndStore();break;}
			case "queryFBLeagueStore":{dataQueryStoreService.queryFBLeagueStore();break;}
			case "qtMatchStore":{dataQueryStoreService.qtMatchStore();break;}
			case "queryFBTeamStore":{dataQueryStoreService.queryFBTeamStore();break;}
			case "queryFBBFDataAndStore":{dataQueryStoreService.queryFBBFDataAndStore();break;}
			case "queryAndStoreFBEuroOdds":{dataQueryStoreService.queryAndStoreFBBjEuropeOdds();break;}
			case "queryBBTeamStore":{dataQueryStoreService.queryBBTeamStore();break;}
			case "queryBBLeagueStore":{dataQueryStoreService.queryBBLeagueStore();break;}
			case "importBBMatchToday":{dataQueryStoreService.importBBMatchToday();break;}
			case "importBBMatchRealtime":{dataQueryStoreService.importBBMatchRealtime();break;}
			case "importBBOdds":{dataQueryStoreService.importBBOdds();break;}
			case "importBBOddsRealtime":{dataQueryStoreService.importBBOddsRealtime();break;}
			case "importBBOddsBjEuro":{dataQueryStoreService.importBBOddsBjEuro();break;}
			default:logger.info("unknow cmd:{}",cmd);
			}
		}
		
	}

}
