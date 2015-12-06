package com.unison.lottery.weibo.dataservice.parse;

import com.unison.lottery.weibo.dataservice.commons.parse.ExtractEngine;
import com.unison.lottery.weibo.dataservice.commons.parse.TextDocument;
import com.unison.lottery.weibo.dataservice.commons.parse.TextField;
import com.unison.lottery.weibo.dataservice.parse.model.BBMatchInfoData;

public class ExtractEngineFactory {

	public static ExtractEngine<BBMatchInfoData> configTodayBBMatchEngine() {
		TextDocument format = new TextDocument();
		format.setDelimiter("\\^");
		
		format.addField(0,"id");              // 赛事ID
		format.addField(1,"cupLeagueId");     // 赛事类型ID（联赛/杯赛ID）
		format.addField(2,"cupLeague");       // 类型： 1联赛，2杯赛
		
		// 3联赛名，例如 NBA，WNBA，简体名与繁体名
		TextField nameFields = new TextField();
		nameFields.setDelimiter(",");
		nameFields.addField(0,"name");        // 简体联赛名
		nameFields.addField(1,"nameF");       // 繁体联赛名		
		format.addField(3, nameFields);
		
		format.addField(4,"sclassType");      // 分几节进行，2:上下半场，4：分4小节
		format.addField(5,"color");           // 颜色值
		format.addField(6,"matchTimeStr");    // 开赛时间, 2014/10/23 7:00:00
		format.addField(7,"matchState");      // 状态:0:未开赛,1:一节,2:二节,5:1'OT，以此类推，-1:完场, -2:待定,-3:中断,-4:取消,-5:推迟,50中场
		format.addField(8,"remainTime");      // 小节剩余时间
		format.addField(9,"homeTeamId");      // 主队ID
		
		// 10 hometeam , hometeam_F , hometeam_E 主队名，简繁
		TextField homeTeamNameFields = new TextField();
		homeTeamNameFields.setDelimiter(",");
		homeTeamNameFields.addField(0,"homeTeam");        // 主队名，简体
		homeTeamNameFields.addField(1,"homeTeamF");       // 主队名，繁体
		//homeTeamNameFields.addField(2,"homeTeamE");       // 主队名，英文
		format.addField(10, homeTeamNameFields);
		
		format.addField(11,"guestTeamId");     // 客队ID

		// 12 guestteam , guestteam_F , guestteam_E 客队名，简繁
		TextField guestTeamNameFields = new TextField();
		guestTeamNameFields.setDelimiter(",");
		guestTeamNameFields.addField(0,"guestTeam");       // 客队名，简体
		guestTeamNameFields.addField(1,"guestTeamF");      // 客队名，繁体
		//guestTeamNameFields.addField(2,"guestTeamE");      // 客队名，英文
		format.addField(12, guestTeamNameFields);
		
		format.addField(13,"homeTeamRank");       // 主队排名
		format.addField(14,"guestTeamRank");      // 客队排名
		format.addField(15,"homeScore");       // 主队得分
		format.addField(16,"guestScore");      // 客队得分
		format.addField(17,"homeOne");         // 主队一节得分(上半场)
		format.addField(18,"guestOne");        // 客队一节得分（上半场）
		format.addField(19,"homeTwo");         // 主队二节得分
		format.addField(20,"guestTwo");        // 客队二节得分
		format.addField(21,"homeThree");       // 主队三节得分(下半场）
		format.addField(22,"guestThree");      // 客队三节得分(下半场）
		format.addField(23,"homeFour");        // 主队四节得分
		format.addField(24,"guestFour");       // 客队四节得分
		format.addField(25,"addTime");         // 加时数 ，即几个加时
		format.addField(26,"homeAddTime1");    // 主队1'ot得分
		format.addField(27,"guestAddTime1");   // 客队1'ot得分
		format.addField(28,"homeAddTime2");    // 主队2'ot得分
		format.addField(29,"guestAddTime2");   // 客队2'ot得分
		format.addField(30,"homeAddTime3");    // 主队3'ot得分
		format.addField(31,"guestAddTime3");   // 客队3'ot得分
		format.addField(32,"addTechnic");      // 是否有技术统计
		format.addField(33,"tv");              // 电视直播
		format.addField(34,"explainContent");         // 备注，直播内容
		format.addField(35,"middleMatchState");        // 中立场：1表示中立场
		
		// 33,34 不用管

		// 35 主胜赔率，客胜赔率
//		TextField oddsFields = new TextField();
//		oddsFields.setDelimiter(",");
//		oddsFields.addField(0,"homeWinOdds");     // 主胜赔率
//		oddsFields.addField(1,"guestWinOdds");    // 客胜赔率
//		format.addField(35, oddsFields);
		
		

		return new ExtractEngine<BBMatchInfoData>(format, BBMatchInfoData.class, 
				new BBMatchInfoTransformer());
	}

	public static ExtractEngine<BBMatchInfoData> configBBMatchInfo4ScheduleEngine() {
		TextDocument format = new TextDocument();
		format.setDelimiter("\\^");
		
		format.addField(0,"id");              // 赛事ID
		format.addField(1,"cupLeagueId");     // 赛事类型ID（联赛/杯赛ID）
		format.addField(2,"cupLeague");       // 类型： 1联赛，2杯赛
		
		// 3联赛名，例如 NBA，WNBA，简体名与繁体名
		TextField nameFields = new TextField();
		nameFields.setDelimiter(",");
		nameFields.addField(0,"name");        // 简体联赛名
		nameFields.addField(1,"nameF");       // 繁体联赛名		
		format.addField(3, nameFields);
		
		format.addField(4,"sclassType");      // 分几节进行，2:上下半场，4：分4小节
		format.addField(5,"color");           // 颜色值
		format.addField(6,"matchTimeStr");    // 开赛时间, 2014/10/23 7:00:00
		format.addField(7,"matchState");      // 状态:0:未开赛,1:一节,2:二节,5:1'OT，以此类推，-1:完场, -2:待定,-3:中断,-4:取消,-5:推迟,50中场
		format.addField(8,"remainTime");      // 小节剩余时间
		format.addField(9,"homeTeamId");      // 主队ID
		
		// 10 hometeam , hometeam_F , hometeam_E 主队名，简繁
		TextField homeTeamNameFields = new TextField();
		homeTeamNameFields.setDelimiter(",");
		homeTeamNameFields.addField(0,"homeTeam");        // 主队名，简体
		homeTeamNameFields.addField(1,"homeTeamF");       // 主队名，繁体
		//homeTeamNameFields.addField(2,"homeTeamE");       // 主队名，英文
		format.addField(10, homeTeamNameFields);
		
		format.addField(11,"guestTeamId");     // 客队ID

		// 12 guestteam , guestteam_F , guestteam_E 客队名，简繁
		TextField guestTeamNameFields = new TextField();
		guestTeamNameFields.setDelimiter(",");
		guestTeamNameFields.addField(0,"guestTeam");       // 客队名，简体
		guestTeamNameFields.addField(1,"guestTeamF");      // 客队名，繁体
		//guestTeamNameFields.addField(2,"guestTeamE");      // 客队名，英文
		format.addField(12, guestTeamNameFields);
		
		format.addField(13,"homeTeamRank");       // 主队排名
		format.addField(14,"guestTeamRank");      // 客队排名
		format.addField(15,"homeScore");       // 主队得分
		format.addField(16,"guestScore");      // 客队得分
		format.addField(17,"homeOne");         // 主队一节得分(上半场)
		format.addField(18,"guestOne");        // 客队一节得分（上半场）
		format.addField(19,"homeTwo");         // 主队二节得分
		format.addField(20,"guestTwo");        // 客队二节得分
		format.addField(21,"homeThree");       // 主队三节得分(下半场）
		format.addField(22,"guestThree");      // 客队三节得分(下半场）
		format.addField(23,"homeFour");        // 主队四节得分
		format.addField(24,"guestFour");       // 客队四节得分
		format.addField(25,"addTime");         // 加时数 ，即几个加时
		format.addField(26,"homeAddTime1");    // 主队1'ot得分
		format.addField(27,"guestAddTime1");   // 客队1'ot得分
		format.addField(28,"homeAddTime2");    // 主队2'ot得分
		format.addField(29,"guestAddTime2");   // 客队2'ot得分
		format.addField(30,"homeAddTime3");    // 主队3'ot得分
		format.addField(31,"guestAddTime3");   // 客队3'ot得分
		format.addField(32,"addTechnic");      // 是否有技术统计
		format.addField(33,"tv");              // 电视直播
		format.addField(34,"explainContent");         // 备注，直播内容
		format.addField(35,"middleMatchState");        // 中立场：1表示中立场
		format.addField(36,"season");//赛季
		format.addField(37,"matchType");       // 类型，如季前赛，常规赛，季后赛
		// 38, 比赛场所，不用管
		
		format.addField(39,"matchClass");      // 比赛分类，例如第一圈，只有杯赛或季后赛才有数据
		format.addField(40,"matchSubClass");   // 比赛子分类，例如A组，只有杯赛才有数据
//		format.addField(42,"year");            // 年份
//		format.addField(43,"isNeutral");       // 是否中立场
		
		
		return new ExtractEngine<BBMatchInfoData>(format, BBMatchInfoData.class, 
				new BBMatchInfoTransformer());
	}

	public static ExtractEngine<BBMatchInfoData> configRealtimeBBMatchEngine() {
		TextDocument format = new TextDocument();
		format.setDelimiter("\\^");
		
		format.addField(0,"id");              // 赛事ID
		
		format.addField(1, "matchState"); // 状态:0:未开赛,1:一节,2:二节,5:1'OT，以此类推，-1:完场,
											// -2:待定,-3:中断,-4:取消,-5:推迟,50中场
		format.addField(2,"remainTime");      // 小节剩余时间
		format.addField(3,"homeScore");       // 主队得分
		format.addField(4,"guestScore");      // 客队得分
		
		format.addField(5,"homeOne");         // 主队一节得分(上半场)
		format.addField(6,"guestOne");        // 客队一节得分（上半场）
		format.addField(7,"homeTwo");         // 主队二节得分
		format.addField(8,"guestTwo");        // 客队二节得分
		format.addField(9,"homeThree");       // 主队三节得分(下半场）
		format.addField(10,"guestThree");      // 客队三节得分(下半场）
		format.addField(11,"homeFour");        // 主队四节得分
		format.addField(12,"guestFour");       // 客队四节得分
		format.addField(13,"addTime");         // 加时数 ，即几个加时
		format.addField(14,"explainContent");  // 备注，直播内容
		format.addField(15,"sclassType");      // 分几节进行，2:上下半场，4：分4小节
		
		format.addField(16,"homeAddTime1");    // 主队1'ot得分
		format.addField(17,"guestAddTime1");   // 客队1'ot得分
		format.addField(18,"homeAddTime2");    // 主队2'ot得分
		format.addField(19,"guestAddTime2");   // 客队2'ot得分
		format.addField(20,"homeAddTime3");    // 主队3'ot得分
		format.addField(21,"guestAddTime3");   // 客队3'ot得分
		
		format.addField(22,"explain2");        // 赔率信息,可不理
		format.addField(23,"addTechnic");      // 是否有技术统计
		
		// 24 主胜赔率，客胜赔率
		TextField oddsFields = new TextField();
		oddsFields.setDelimiter(",");
		oddsFields.addField(0,"homeWinOdds");     // 主胜赔率
		oddsFields.addField(1,"guestWinOdds");    // 客胜赔率
		format.addField(24, oddsFields);
		
		return new ExtractEngine<BBMatchInfoData>(format,
				BBMatchInfoData.class, new BBMatchInfoTransformer());
	}

}
