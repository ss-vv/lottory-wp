package com.davcai.lottery.push.client;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.junit.Before;
import org.junit.Test;

import com.davcai.lottery.push.common.model.BasketballMatchMessage;
import com.davcai.lottery.push.common.model.FootballMatchMessage;
import com.davcai.lottery.push.common.model.PushMessage;
import com.davcai.lottery.push.common.model.PushResult;
import com.davcai.lottery.push.common.model.PushTaskType;

public class PushTaskTest {
	
	private org.eclipse.jetty.client.HttpClient jettyHttpClient;
	private HttpClient httpClient;
	private HttpParams httpParameters;
	private PoolingClientConnectionManager connectionManager;  
	private String pushHXServerUrl="http://182.92.191.193:28080/lottery-api/syncLiveScores";
	private String pushServerUrl="http://182.92.191.193:18080/push-server/comet";
	
	@Before
	public void setUp(){
		jettyHttpClient=new org.eclipse.jetty.client.HttpClient();
		httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
		HttpConnectionParams.setSoTimeout(httpParameters, 30000);
		connectionManager = new PoolingClientConnectionManager();
		httpClient = new DefaultHttpClient(connectionManager,httpParameters);
	}

	@Test
	public void testPush() throws ParseException{
		
		
		PushTask pushTask = createPushTaskByType(PushTaskType.HuanXin);
		
		PushTask pushTask2 = createPushTaskByType(PushTaskType.CometD);
		
		ExecutorService executorService=Executors.newFixedThreadPool(2);
    	List<PushTask> tasks = new ArrayList<PushTask>();
		tasks.add(pushTask);
		tasks.add(pushTask2);
		try {
			List<Future<PushResult>> resultList = executorService.invokeAll(tasks);
			System.out.println("推送请求响应:");
			for(Future<PushResult> result:resultList){
				PushResult pushResult = result.get();
				System.out.println("推送请求响应pushResult:"+pushResult);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private PushTask createPushTaskByType(PushTaskType type) throws ParseException {
		PushTask pushTask=new PushTask();
//		pushTask.setHttpClient(httpClient);
		pushTask.setJettyHttpClient(jettyHttpClient);
		pushTask.setPushHXServerUrl(pushHXServerUrl);
		pushTask.setPushServerUrl(pushServerUrl);
		pushTask.setType(type);
		
		List<PushMessage> messageList=new ArrayList<PushMessage>();
    	FootballMatchMessage match = new FootballMatchMessage();
    	match.setMatchId("201502031004");
    	match.setHomeTeamName("赛事A7");
    	match.setGuestTeamName("赛事A7");
    	match.setHomeScore(6);
    	match.setGuestScore(5);
    	match.setState("2");
    	match.setGuestTeamHalfScore(1);
    	match.setHomeTeamHalfScore(1);
    	match.setGuestTeamPosition("1");
    	match.setGuestTeamRed(0);
    	match.setGuestTeamYellow(1);
    	match.setHalfStartTime(DateUtils.parseDate("2014-11-25 18:00:00", "yyyy-MM-dd HH:mm:ss"));
    	match.setHomeTeamPosition("2");
    	match.setHomeTeamRed(0);
    	match.setHomeTeamYellow(0);
    	match.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
    	
    	FootballMatchMessage match2 = new FootballMatchMessage();
    	match2.setMatchId("201501164303");
     	match2.setHomeTeamName("赛事B7");
     	match2.setGuestTeamName("赛事B7");
     	match2.setHomeScore(4);
     	match2.setGuestScore(14);
     	match2.setState("1");
    	match2.setGuestTeamHalfScore(0);
    	match2.setHomeTeamHalfScore(1);
    	match2.setGuestTeamPosition("1");
    	match2.setGuestTeamRed(0);
    	match2.setGuestTeamYellow(1);
    	match2.setHalfStartTime(DateUtils.parseDate("2014-11-25 18:00:00", "yyyy-MM-dd HH:mm:ss"));
    	match2.setHomeTeamPosition("6");
    	match2.setHomeTeamRed(0);
    	match2.setHomeTeamYellow(0);
    	match2.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:30:00", "yyyy-MM-dd HH:mm:ss"));
     	
    	BasketballMatchMessage match3 = new BasketballMatchMessage();
     	match3.setMatchId("201501201008");
     	match3.setHomeTeamName("篮球赛事P7");
     	match3.setGuestTeamName("篮球赛事P7");
     	match3.setHomeScore(54);
     	match3.setGuestScore(58);
     	match3.setGuestTeamHalfScore(44);
     	match3.setHomeTeamHalfScore(40);
     	match3.setGuestTeamPosition("东1");
     	match3.setHomeOne(20);
     	match3.setGuestOne(23);
     	match3.setHomeTwo(25);
     	match3.setGuestTwo(25);
     	match3.setRemainTime("1:00");
     	match3.setHomeTeamPosition("东2");
     	match3.setScheduleMatchTime(DateUtils.parseDate("2014-11-25 17:35:00", "yyyy-MM-dd HH:mm:ss"));
     	match3.setState("2");
     	
     	messageList.add(match); 
     	messageList.add(match2);
     	messageList.add(match3);
		pushTask.setPushMessages(messageList);
		
		return pushTask;
	}

}
