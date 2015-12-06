package com.unison.lottery.weibo.data.service.crawler;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.JishiBifenDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl.JishiBifenDataStoreDaoImpl;
import com.unison.lottery.weibo.data.crawler.service.store.pushOdds.PushOddsDataImpl;
import com.unison.lottery.weibo.data.crawler.service.store.pushOdds.PushOddsDataService;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;

/**
 * @author baoxing.peng@davcai.com
 *
 */
public class PushOddsServiceTest {
	PushOddsDataService pushOddsDataService;
	JishiBifenDataStoreDao jishiBifenDataStoreDao;
	@Before
	public void init(){
		jishiBifenDataStoreDao = new JishiBifenDataStoreDaoImpl();
		pushOddsDataService =  new PushOddsDataImpl(jishiBifenDataStoreDao);
	}
	
	@Test
	public void testPushBbOddsMessage() throws ParseException{
		a("周一003","2015-03-31 08:10:00",Qt_fb_match_oddsType.asia);
		a("周四302","2015-03-20 00:40:00",Qt_fb_match_oddsType.asia);
		a("周四303","2015-03-20 01:40:00",Qt_fb_match_oddsType.euro);
		a("周四304","2015-03-20 02:00:00",Qt_fb_match_oddsType.ou);
	}
	private void a(String code,String ptime,Qt_fb_match_oddsType oddsType)throws ParseException{
		DecimalFormat df = new DecimalFormat("#.00");
		Map<String, BasketBallMatchPO> basMap = new HashMap<String, BasketBallMatchPO>();
		BasketBallMatchPO basketMatchModel = new BasketBallMatchPO();
		basketMatchModel.setJingcaiId(code);
		basMap.put("189685", basketMatchModel);
		basketMatchModel.setMatchTime(DateUtils.parseDate(ptime, new String[]{"yyyy-MM-dd HH:mm:ss"}));
		List<QtBasketMatchOddsModel> qtBasketMatchOddsModels = new ArrayList<QtBasketMatchOddsModel>();
		QtBasketMatchOddsModel qtBasketMatchOddsModel = new QtBasketMatchOddsModel();
		qtBasketMatchOddsModel.setCorpId("1");
		qtBasketMatchOddsModel.setQtBsId("189685");
		qtBasketMatchOddsModel.setTimestamp(DateUtils.parseDate("2015-03-19 06:32:10", new String[]{"yyyy-MM-dd HH:mm:ss"}));
		qtBasketMatchOddsModel.setHomeWinOdds(Double.valueOf(df.format(Math.random()*2+(new Date().getSeconds())/60)));
		qtBasketMatchOddsModel.setHandicapOrScore(Double.valueOf(df.format(-Math.random()*2+(new Date().getSeconds())/60)));
		qtBasketMatchOddsModel.setGuestWinOdds(Double.valueOf(df.format(Math.random()*2+(new Date().getSeconds())/60)));
		QtBasketMatchOddsModel qtBasketMatchOddsModel1 = new QtBasketMatchOddsModel();
		qtBasketMatchOddsModel1.setCorpId("1");
		qtBasketMatchOddsModel1.setQtBsId("189685");
		qtBasketMatchOddsModel.setOddsType(Qt_fb_match_oddsType.euro);
		qtBasketMatchOddsModel1.setOddsType(Qt_fb_match_oddsType.euro);
		qtBasketMatchOddsModel1.setTimestamp(DateUtils.parseDate("2015-03-19 06:33:30", new String[]{"yyyy-MM-dd HH:mm:ss"}));
		qtBasketMatchOddsModel1.setHomeWinOdds(Double.valueOf(df.format(Math.random()*2+(new Date().getSeconds())/60)));
		qtBasketMatchOddsModel1.setHandicapOrScore(Double.valueOf(df.format(Math.random()*2+(new Date().getSeconds())/60)));
		qtBasketMatchOddsModel1.setGuestWinOdds(Double.valueOf(df.format(Math.random()*2+(new Date().getSeconds())/60)));
		qtBasketMatchOddsModels.add(qtBasketMatchOddsModel);
		qtBasketMatchOddsModels.add(qtBasketMatchOddsModel1);
		pushOddsDataService.pushBbOddsMessage(qtBasketMatchOddsModels,basMap);
	}
	@Test
	public void testPushFbOddsMessage() throws ParseException{
		fbOdds(Qt_fb_match_oddsType.ou);
	}

	private void fbOdds(Qt_fb_match_oddsType odd) throws ParseException {
		DecimalFormat df = new DecimalFormat("#.00");
		List<QtMatchOpOddsModel> qtMatchOpOddsModels = new ArrayList<QtMatchOpOddsModel>();
		QtMatchOpOddsModel qtMatchOpOddsModel = new QtMatchOpOddsModel();
		qtMatchOpOddsModel.setCorpId("1");
		qtMatchOpOddsModel.setQtBsId("996264");
		qtMatchOpOddsModel.setTimestamp(DateUtils.parseDate("2015-04-02 15:50:10", new String[]{"yyyy-MM-dd HH:mm:ss"}));
		//Double.valueOf(df.format(Math.random()*2+(new Date().getSeconds())/60))
		qtMatchOpOddsModel.setHomeWinOdds(1.25);
		qtMatchOpOddsModel.setHandicap(0.5);
		qtMatchOpOddsModel.setDrawOdds(0.25);
		//Double.valueOf(df.format(Math.random()*2+(new Date().getSeconds())/60))
		qtMatchOpOddsModel.setGuestWinOdds(0.65);
		QtMatchOpOddsModel qtBasketMatchOddsModel1 = new QtMatchOpOddsModel();
		qtBasketMatchOddsModel1.setCorpId("4");
		qtBasketMatchOddsModel1.setQtBsId("996264");
		qtBasketMatchOddsModel1.setTimestamp(DateUtils.parseDate("2015-03-19 06:33:30", new String[]{"yyyy-MM-dd HH:mm:ss"}));
		qtBasketMatchOddsModel1.setHomeWinOdds(Double.valueOf(df.format(Math.random()*2+(new Date().getSeconds())/60)));
		qtBasketMatchOddsModel1.setHandicap(Double.valueOf(df.format(Math.random()*2+(new Date().getSeconds())/60)));
		qtBasketMatchOddsModel1.setGuestWinOdds(Double.valueOf(df.format(Math.random()*2+(new Date().getSeconds())/60)));
		qtMatchOpOddsModels.add(qtMatchOpOddsModel);
//		qtMatchOpOddsModels.add(qtBasketMatchOddsModel1);
		pushOddsDataService.pushFbOddsMessage(odd, qtMatchOpOddsModels);
	}
}
