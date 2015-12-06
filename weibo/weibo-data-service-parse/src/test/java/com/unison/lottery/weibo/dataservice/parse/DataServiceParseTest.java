package com.unison.lottery.weibo.dataservice.parse;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.unison.lottery.weibo.dataservice.parse.model.BBBjEuropeOddsData;
import com.unison.lottery.weibo.dataservice.parse.model.BBCurrentDayOddsData;
import com.unison.lottery.weibo.dataservice.parse.model.BBLeagueData;
import com.unison.lottery.weibo.dataservice.parse.model.BBMatchInfoData;
import com.unison.lottery.weibo.dataservice.parse.model.BBOdds;
import com.unison.lottery.weibo.dataservice.parse.model.BBTeamData;
import com.unison.lottery.weibo.dataservice.parse.model.CurrentDayOddsData;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFChangeData;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFData;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFDetail;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFResultData;
import com.unison.lottery.weibo.dataservice.parse.model.FBBjEuropeOddsData;
import com.unison.lottery.weibo.dataservice.parse.model.FBEvent;
import com.unison.lottery.weibo.dataservice.parse.model.FBLeagueData;
import com.unison.lottery.weibo.dataservice.parse.model.FBMatchidData;
import com.unison.lottery.weibo.dataservice.parse.model.FBTeamData;
import com.unison.lottery.weibo.dataservice.parse.model.FBTechnicData;


/**
 * Unit test for simple App.
 */
public class DataServiceParseTest 
   
{
	private ApplicationContext ctx;
	
	private DataParseService dataParseService;
	
    @Test
    public void testParseOdds(){
    	CurrentDayOddsData result= this.dataParseService.getCurrentDayOdds();
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
    
    @Before
	public void setup() throws Exception {
		ctx = new ClassPathXmlApplicationContext(
				"dataServiceParseTest.xml");
		dataParseService=(DataParseService) ctx.getBean("dataParseService");
		
	}

	@After
	public void tearDown() throws Exception {
		ctx = null;
		dataParseService=null;
	}
	
	
	/**
	 * 联赛杯赛
	 */
	@Test
	public void testGetFBLeagueAndSaveFile(){
		FBLeagueData result= this.dataParseService.getFBLeagueAndSaveFile();
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	@Test
	public void testGetFBLeague(){
		FBLeagueData result= this.dataParseService.getFBLeague();
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	
	
	/**
	 * 球队资料
	 */
	@Test
	public void testGetFBTeamAndSaveFile(){
		FBTeamData result= this.dataParseService.getFBTeamAndSaveFile();
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	
	@Test
	public void testGetFBTeam(){
		FBTeamData result= this.dataParseService.getFBTeam();
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
//		File file = new File("c:\\ddd.txt");
//		OutputStream os = new FileOutputStream(file);
//		try {
//			os.write(result.toString().getBytes());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	
	/**
	 * 彩票赛事与球探网的关联表
	 */
	@Test
	public void testGetFBMatchidAndSaveFile(){
		FBMatchidData result= this.dataParseService.getFBMatchidAndSaveFile();
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	@Test
	public void testGetFBMatchid(){
		FBMatchidData result= this.dataParseService.getFBMatchid();
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	
	/**
	 * 比赛详细事件接口（历史数据）
	 * http://dxbf.bet007.com/Event_XML.aspx?date=yyyy-mm-dd
	 */
	@Test
	public void testGetFBEventAndSaveFile(){
		FBEvent result= this.dataParseService.getFBEventAndSaveFile(new Date());
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	@Test
	public void testGetFBEvent(){
		FBEvent result= this.dataParseService.getFBEvent(new Date());
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	
	/**
	 * 20秒内变化数据 (实时更新) 
		
	 */
	@Test
	public void testGetFBBF20SecChangeAndSaveFile(){
		FBBFChangeData result= this.dataParseService.getFBBF20SecChangeAndSaveFile();
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	
	@Test
	public void testGetFBBF20SecChange(){
		FBBFChangeData result= this.dataParseService.getFBBF20SecChange();
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	
	/**
	 * 150秒内变化数据，
	 */
	@Test
	public void testGetFBBF150SecChangeAndSaveFile(){
		FBBFChangeData result= this.dataParseService.getFBBF150SecChangeAndSaveFile();
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	
	@Test
	public void testGetFBBF150SecChange(){
		FBBFChangeData result= this.dataParseService.getFBBF150SecChange();
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	
	/**
	 * 按赛程ID查该比赛的数据
	 * http://dxbf.bet007.com/BF_XMLByID.aspx?id=比赛ID集
	 */
	@Test
	public void testGetFBBFResultByIDAndSaveFile(){
		FBBFResultData result= this.dataParseService.getFBBFResultByIDAndSaveFile("948843");
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	@Test
	public void testGetFBBFResultByID(){
		FBBFResultData result= this.dataParseService.getFBBFResultByID("1063354");
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	
	
	/**
	 * 当天比分数据(js解析)
	 * 
	 */
	@Test
	public void testGetFBBFDataAndSaveFile(){
		 FBBFData result = this.dataParseService.getFBBFDataAndSaveFile();
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	@Test
	public void testGetFBBFData(){
		FBBFData result = this.dataParseService.getFBBFData();
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	
	/**
	 * 按历史盘口的数据
	 * http://dxbf.bet007.com/BF_XMLByID.aspx?id=比赛ID集
	 */
	@Test
	public void testGetFBChangeOddsHistoryAndSaveFile(){
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date myDate2 = sdf.parse("2014-02-13");
			CurrentDayOddsData result = this.dataParseService.getFBChangeOddsHistoryAndSaveFile(myDate2);
			System.out.println(result);
	    	assertTrue(null!=result);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@Test
	public void testGetFBChangeOddsHistory(){
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date myDate2 = sdf.parse("2014-10-21");
			CurrentDayOddsData result = this.dataParseService.getFBChangeOddsHistory(new Date());
			System.out.println(result);
	    	assertTrue(null!=result);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	
    }
	
	/**
	 * 当天比赛的入球、红黄牌事件(js解析)
	 * 
	 */
	@Test
	public void testGetFBBFDetailAndSaveFile(){
		FBBFDetail result = this.dataParseService.getFBBFDetailAndSaveFile();
		//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	@Test
	public void testGetFBBFDetail(){
		 FBBFDetail result = this.dataParseService.getFBBFDetail();
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	
	
	/**
	 * 获取英超本赛季的赛程

	 * http://bf.bet007.com/BF_XML.aspx?sclassid=36
	 */
	@Test
	public void testGetFBBfYingChaoResultAndSaveFile(){
		String sclassid="36";
		 FBBFResultData result = this.dataParseService.getFBBfYingChaoAndSaveFile(sclassid);
		//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	@Test
	public void testGetFBBfYingChaoResult(){
		String sclassid="36";
		 FBBFResultData result = this.dataParseService.getFBBfYingChao(sclassid);
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	
	
	
	/**
	 * 一天内比赛的技术统计
	 */
	@Test
	public void testGetFBTechnicAndSaveFile(){
		  FBTechnicData result = this.dataParseService.getFBTechnicAndSaveFile();
		//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	@Test
	public void testGetFBTechnic(){
		  FBTechnicData result = this.dataParseService.getFBTechnic();
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	
	/**
	 * 赛程赛果
	 * http://dxbf.bet007.com/BF_XML.aspx?date=yyyy-MM-dd (带时间)
	 */
	@Test
	public void testGetFBBfResultAndSaveFile(){
		   FBBFResultData result = this.dataParseService.getFBBfResultAndSaveFile(new Date());
		//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
    }
	@Test
	public void testGetFBBfResult(){
		try{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date myDate2 = sdf.parse("2014-02-13");
		   FBBFResultData result = this.dataParseService.getFBBfResult(myDate2);
    	//result= this.dataParseService.getCurrentDayOddsAndSaveFile();
    	System.out.println(result);
    	assertTrue(null!=result);
		}catch(Exception e){
			e.printStackTrace();
		}
    }
	
	
	
	
	
	/**
	 * 获取足球赔率
	 */
	@Test
	public void testGetCurrentDayOddsAndSaveFile() {
		  CurrentDayOddsData result = this.dataParseService.getCurrentDayOddsAndSaveFile();
		// result= this.dataParseService.getCurrentDayOddsAndSaveFile();
		System.out.println(result);
		assertTrue(null != result);
	}

	@Test
	public void testGetCurrentDayOdds() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date myDate2 = sdf.parse("2014-02-13");
			  CurrentDayOddsData result = this.dataParseService.getCurrentDayOdds();
			// result= this.dataParseService.getCurrentDayOddsAndSaveFile();
			System.out.println(result);
			assertTrue(null != result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	/**
	 * 足球赔率30秒变化接口
	 */

	
	@Test
	public void testGetFBChangeOdds30SecAndSaveFile() {
		 CurrentDayOddsData result = this.dataParseService.getFBChangeOdds30SecAndSaveFile();
		// result= this.dataParseService.getCurrentDayOddsAndSaveFile();
		System.out.println(result);
		assertTrue(null != result);
	}

	@Test
	public void testGetFBChangeOdds() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date myDate2 = sdf.parse("2014-02-13");
			 CurrentDayOddsData result = this.dataParseService.getFBChangeOdds();
			// result= this.dataParseService.getCurrentDayOddsAndSaveFile();
			System.out.println(result);
			assertTrue(null != result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 百家欧赔接口
	 */
	@Test
	public void testGetFBBjEuropeOddsAndSaveFile() {
		try{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate2 = sdf.parse("2014-02-17");
		FBBjEuropeOddsData result = this.dataParseService
				.getFBBjEuropeOddsAndSaveFile("", myDate2, "");
		// result= this.dataParseService.getCurrentDayOddsAndSaveFile();
		System.out.println(result);
		assertTrue(null != result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void testGetFBBjEuropeOdds() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date myDate2 = sdf.parse("2014-02-17");
			FBBjEuropeOddsData result = this.dataParseService
					.getFBBjEuropeOdds("", myDate2, "");
			// result= this.dataParseService.getCurrentDayOddsAndSaveFile();
			System.out.println(result);
			assertTrue(null != result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 篮球当天赔率
	 */
	@Test
	public void testGetBBCurrentDayOdds() {
		try {
			BBCurrentDayOddsData result = this.dataParseService.getBBCurrentDayOdds();
			// result= this.dataParseService.getCurrentDayOddsAndSaveFile();
			System.out.println(result);
			assertTrue(null != result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 篮球历史盘口，保存文件
	 */
	@Test
	public void testGetBBChangeOddsHistory() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date myDate2 = sdf.parse("2014-03-03");
			BBCurrentDayOddsData result = this.dataParseService.getBBChangeOddsHistory(myDate2);
			// result= this.dataParseService.getCurrentDayOddsAndSaveFile();
			System.out.println(result);
			assertTrue(null != result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetBBOddsHistory(){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date myDate2 = sdf.parse("2014-03-03");
			BBOdds result = this.dataParseService.getBBOddsHistory(myDate2);
			
			// result= this.dataParseService.getCurrentDayOddsAndSaveFile();
			System.out.println(result);
			assertTrue(null != result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 篮球30秒内的变化赔率
	 */
	@Test
	public void testGetBBChangeOdds30Sec() {
		try {
			BBCurrentDayOddsData result = this.dataParseService.getBBChangeOdds30Sec();
			// result= this.dataParseService.getCurrentDayOddsAndSaveFile();
			System.out.println(result);
			assertTrue(null != result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 篮球百家欧赔接口，5分钟数据
	 */
	@Test
	public void testGetBBBjEuropeOdds() {
		try {
			BBBjEuropeOddsData result = this.dataParseService.getBBBjEuropeOdds(null,null,"5");
			// result= this.dataParseService.getCurrentDayOddsAndSaveFile();
			System.out.println(result);
			assertTrue(null != result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 篮球百家欧赔接口,指定某天
	 */
	@Test
	public void testGetBBBjEuropeOddsOnTargetDate() {
		try {
			Date date = DateUtils.parseDate("2014-09-23", "yyyy-MM-dd");
			BBBjEuropeOddsData result = this.dataParseService.getBBBjEuropeOdds(null,date,null);
			// result= this.dataParseService.getCurrentDayOddsAndSaveFile();
			System.out.println(result);
			assertTrue(null != result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 篮球比分接口：当天比赛数据
	 * 
	 */
	@Test
	public void testGetBBMatchInfoToday(){
		try {
			List<BBMatchInfoData> result = this.dataParseService.getBBMatchInfoToday();
			
			// result= this.dataParseService.getCurrentDayOddsAndSaveFile();
			System.out.println(result);
			assertTrue(null != result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 篮球赛程赛果接口：指定日期数据
	 */
	@Test
	public void testGetBBMatchInfoForDate(){
		Date date;
		try {
			date = DateUtils.parseDate("2014-10-23", "yyyy-MM-dd");
			List<BBMatchInfoData> result = this.dataParseService.getBBMatchInfo(date);
			System.out.println(result);
			assertTrue(null != result);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	
	}
	
	/**
	 * 篮球即时变化的数据接口
	 */
	@Test
	public void testGetBBMatchInfoRealTime(){
		List<BBMatchInfoData> result=this.dataParseService.getBBMatchInfoRealtime();
		System.out.println(result);
		assertTrue(null != result);
	}
	
	/**
	 * 篮球联赛，赛事类型
	 */
	@Test
	public void testGetBBLeague(){
		BBLeagueData leagues = dataParseService.getBBLeague();
		System.out.println(leagues);
		assertTrue(null!=leagues);
	}
	
	/**
	 * 篮球球队
	 */
	@Test
	public void testGetBBTeam(){
		BBTeamData teamData = dataParseService.getBBTeam();
		System.out.println(teamData);
		assertTrue(null!=teamData);
	}
	
	/**
	 * 篮球赔率接口
	 * 
	 */
	@Test
	public void testGetBBOdds(){
		BBOdds odds = dataParseService.getBBOdds();
		System.out.println(odds);
		assertTrue(null!=odds);
	}
	
	
	/**
	 * 篮球即时赔率接口
	 */
	@Test
	public void testGetBBOddsRealtime(){
		BBOdds odds = dataParseService.getBBOddsRealtime();
		System.out.println(odds);
		assertTrue(null!=odds);
	}
	
	@Test
	public void testConvertGMT8ToBeijingTime(){
		Date date = dataParseService.convertGMT8ToBeijingTime("1414079940000");
		System.out.println("date="+date);
	}
	
	@Test
	public void testConvertGMT0ToBeijingTime(){
		Date date = dataParseService.convertGMT0ToBeijingTime("1414079940000");
		System.out.println("date="+date);
	}
}
