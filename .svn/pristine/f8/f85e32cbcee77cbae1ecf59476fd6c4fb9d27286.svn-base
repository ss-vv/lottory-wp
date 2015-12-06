package com.unison.lottery.weibo.data.service.store;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.unison.lottery.weibo.dataservice.crawler.service.JishiDataParseServiceImpl;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;

public class JishiDataParseServiceImplTest {

	@Test
	public void testSendFB() throws IOException, ParseException{
		JishiDataParseServiceImpl jishiDataParseService=new JishiDataParseServiceImpl();
		List<QtMatchBaseModel> matchBaseInfos = new ArrayList<QtMatchBaseModel>();
		QtMatchBaseModel qtMatchBaseModel=new QtMatchBaseModel();
		qtMatchBaseModel.setJingcaiId("周四004");
		qtMatchBaseModel.setMatchTime(DateUtils.parseDate("2015-02-13 18:00:00", "yyyy-MM-dd HH:mm:ss"));
		qtMatchBaseModel.setHalfStartTime(DateUtils.parseDate("2015-02-13 18:55:00", "yyyy-MM-dd HH:mm:ss"));
		qtMatchBaseModel.setMatchStatus(3);
		qtMatchBaseModel.setHomeTeamScore(6);
		qtMatchBaseModel.setGuestTeamScore(3);
		qtMatchBaseModel.setHomeTeamId("拉普拉塔大学生 ");
		qtMatchBaseModel.setGuestTeamId("德尔瓦耶独立");
		qtMatchBaseModel.setGuestTeamHalfScore(2);
		qtMatchBaseModel.setHomeTeamHalfScore(5);
		qtMatchBaseModel.setGuestTeamRed(0);
		qtMatchBaseModel.setHomeTeamRed(0);
		qtMatchBaseModel.setGuestTeamYellow(0);
		qtMatchBaseModel.setHomeTeamYellow(0);
		qtMatchBaseModel.setSource(1);
		qtMatchBaseModel.setProcessStatus(0);
		qtMatchBaseModel.setHandiCap(0.0);
		qtMatchBaseModel.setHomeTeamPosition(0);
		qtMatchBaseModel.setGuestTeamPosition(0);
		
		QtMatchBaseModel qtMatchBaseModel2=new QtMatchBaseModel();
		qtMatchBaseModel2.setJingcaiId("周五003");
		qtMatchBaseModel2.setMatchTime(DateUtils.parseDate("2015-02-14 17:10:00", "yyyy-MM-dd HH:mm:ss"));
		qtMatchBaseModel.setHalfStartTime(DateUtils.parseDate("2015-02-14 18:05:00", "yyyy-MM-dd HH:mm:ss"));
		qtMatchBaseModel2.setMatchStatus(3);
		qtMatchBaseModel2.setHomeTeamScore(2);
		qtMatchBaseModel2.setGuestTeamScore(2);
		qtMatchBaseModel2.setHomeTeamId("杜塞尔多夫  ");
		qtMatchBaseModel2.setGuestTeamId("奥厄");
		qtMatchBaseModel2.setHomeTeamHalfScore(2);
		qtMatchBaseModel2.setGuestTeamHalfScore(2);
		qtMatchBaseModel2.setGuestTeamRed(0);
		qtMatchBaseModel2.setHomeTeamRed(0);
		qtMatchBaseModel2.setGuestTeamYellow(0);
		qtMatchBaseModel2.setHomeTeamYellow(0);
		qtMatchBaseModel2.setSource(1);
		qtMatchBaseModel2.setProcessStatus(0);
		qtMatchBaseModel2.setHandiCap(0.0);
		qtMatchBaseModel2.setHomeTeamPosition(0);
		qtMatchBaseModel2.setGuestTeamPosition(0);
		
		matchBaseInfos.add(qtMatchBaseModel);
		matchBaseInfos.add(qtMatchBaseModel2);
		jishiDataParseService.sendZqJishiBifenData(matchBaseInfos);
	}
	
	@Test
	public void testSendBB() throws IOException, ParseException{
		JishiDataParseServiceImpl jishiDataParseService=new JishiDataParseServiceImpl();
		
		List<BasketBallMatchModel> ballMatchModels=new ArrayList<BasketBallMatchModel>();
		BasketBallMatchModel basketBallMatchModel=new BasketBallMatchModel();
		basketBallMatchModel.setJingcaiId("周四301");
		basketBallMatchModel.setMatchTime(DateUtils.parseDate("2015-02-13 00:30:00", "yyyy-MM-dd HH:mm:ss"));
		basketBallMatchModel.setMatchState(3);
		basketBallMatchModel.setGuestOne(10);
		basketBallMatchModel.setGuestTwo(14);
		basketBallMatchModel.setGuestThree(9);
		basketBallMatchModel.setGuestFour(0);
		basketBallMatchModel.setGuestScore(33);
		basketBallMatchModel.setGuestTeam("奥林匹亚克斯");
		basketBallMatchModel.setHomeOne(10);
		basketBallMatchModel.setHomeTwo(16);
		basketBallMatchModel.setHomeThree(4);
		basketBallMatchModel.setHomeFour(0);
		basketBallMatchModel.setHomeScore(30);
		basketBallMatchModel.setHomeTeam("下洛夫哥罗德");
		basketBallMatchModel.setHomeAddTime1(0);
		basketBallMatchModel.setHomeAddTime2(0);
		basketBallMatchModel.setHomeAddTime3(0);
		basketBallMatchModel.setGuestAddTime1(0);
		basketBallMatchModel.setGuestAddTime2(0);
		basketBallMatchModel.setGuestAddTime3(0);
		basketBallMatchModel.setSource(1);
		basketBallMatchModel.setProcessStatus(0);
		basketBallMatchModel.setLetBallNum(0);
		basketBallMatchModel.setBsId("");
		
		BasketBallMatchModel basketBallMatchModel2=new BasketBallMatchModel();
		basketBallMatchModel2.setJingcaiId("周四305");
		basketBallMatchModel2.setMatchTime(DateUtils.parseDate("2015-02-13 09:00:00", "yyyy-MM-dd HH:mm:ss"));
		basketBallMatchModel2.setMatchState(1);
		basketBallMatchModel2.setGuestOne(10);
		basketBallMatchModel2.setGuestTwo(14);
		basketBallMatchModel2.setGuestThree(0);
		basketBallMatchModel2.setGuestFour(0);
		basketBallMatchModel2.setGuestScore(10);
		basketBallMatchModel2.setGuestTeam("克里夫兰骑士");
		basketBallMatchModel2.setHomeOne(6);
		basketBallMatchModel2.setHomeTwo(0);
		basketBallMatchModel2.setHomeThree(0);
		basketBallMatchModel2.setHomeFour(0);
		basketBallMatchModel2.setHomeScore(6);
		basketBallMatchModel2.setHomeTeam("芝加哥公牛");
		basketBallMatchModel2.setHomeAddTime1(0);
		basketBallMatchModel2.setHomeAddTime2(0);
		basketBallMatchModel2.setHomeAddTime3(0);
		basketBallMatchModel2.setGuestAddTime1(0);
		basketBallMatchModel2.setGuestAddTime2(0);
		basketBallMatchModel2.setGuestAddTime3(0);
		basketBallMatchModel2.setSource(1);
		basketBallMatchModel2.setProcessStatus(0);
		basketBallMatchModel2.setLetBallNum(0);
		basketBallMatchModel2.setBsId("");
		ballMatchModels.add(basketBallMatchModel);
		ballMatchModels.add(basketBallMatchModel2);
		jishiDataParseService.sendLqjingcaiJishi(ballMatchModels);
	}

}
