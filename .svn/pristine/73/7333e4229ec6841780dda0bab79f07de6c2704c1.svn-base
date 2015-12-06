package com.davcai.lottery.push.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.push.common.model.PushMessage;
import com.davcai.lottery.push.common.model.PushResult;
import com.davcai.lottery.push.common.model.PushTaskType;

public class PushClientCometdImpl implements PushClient {

	

	private static final int _DEFAUL_AWAIT_TIME = 1;


	private Logger logger=LoggerFactory.getLogger(getClass());
	
	private String pushServerUrl;
	
	private String pushHXServerUrl; 
	
	private String appId;
	private String appkey;
	private String master;
	private String host;
	
	
	private PushTask pushTask;
	
	@Autowired
	private NewPushMessageHandler newPushMessageHandler;
	
	
	private long awaitTimeInSeconds=_DEFAUL_AWAIT_TIME;


	public PushResult pushMessages(final List<PushMessage> pushMessages) throws PushClientException{
		PushResponse pushResponse=new PushResponse();
		check(pushMessages);
		Map<PushTaskType,List<PushMessage>> pushMessageList = newPushMessageHandler.checkPushMessagesIfNew(pushMessages);
		if(pushMessageList != null && !pushMessageList.isEmpty()){
			
			List<PushMessage> pushMessageToCometd = pushMessageList.get(PushTaskType.CometD);
			List<PushMessage> pushMessageToGTui = pushMessageList.get(PushTaskType.GeTui);
			int oldCometdCount=pushMessages.size()-pushMessageToCometd.size();
			int oldGeTuiCount = pushMessages.size()-pushMessageToGTui.size();
			
			ExecutorService executorService=Executors.newFixedThreadPool(2);
        	List<PushTask> tasks = new ArrayList<PushTask>();
        	PushTask pushTask = null;
			if(pushMessageToCometd!=null&&!pushMessageToCometd.isEmpty()){
				newPushMessageHandler.saveNews(pushMessageToCometd);
				pushTask = createPushTask();
				pushTask.setPushMessages(pushMessageToCometd);
				pushTask.setType(PushTaskType.CometD);//cometd
				tasks.add(pushTask);
			}
			if(pushMessageToGTui!=null&&!pushMessageToGTui.isEmpty()){
				pushTask = createPushTask();
				pushTask.setPushMessages(pushMessageToGTui);
				pushTask.setType(PushTaskType.GeTui);//个推
				tasks.add(pushTask);
			}
			try {
				List<Future<PushResult>> resultList = executorService.invokeAll(tasks);
				logger.debug("多平台发起推送请求响应:{}", resultList);
				try {
					pushResponse = makePushResponse(pushResponse, resultList,oldCometdCount,oldGeTuiCount);
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
		else{
			logger.info("无需发送推送消息");
		}
		return pushResponse;
	}
	
	public PushTask createPushTask() {
		PushTask result=null;
		if(null!=this.pushTask){
			result=new PushTask();
			result.setAwaitTimeInSeconds(this.pushTask.getAwaitTimeInSeconds());
			result.setConnectionTimeOutInMs(this.pushTask.getConnectionTimeOutInMs());
//			result.setHttpClient(this.pushTask.getHttpClient());
			result.setJettyHttpClient(this.pushTask.getJettyHttpClient());
//			result.setPushHXServerUrl(this.pushTask.getPushHXServerUrl());
			result.setPushServerUrl(this.pushTask.getPushServerUrl());
			result.setAppId(this.pushTask.getAppId());
			result.setAppkey(this.pushTask.getAppkey());
			result.setHost(this.pushTask.getHost());
			result.setMaster(this.pushTask.getMaster());
		}
		return result;
	}

	private PushResponse makePushResponse(PushResponse pushResponse,
			List<Future<PushResult>> resultList, int oldCometdCount, int oldGeTuiCount) throws InterruptedException, ExecutionException {
		pushResponse.pushToHX = new PushToHX();
		pushResponse.pushToComted = new PushToComted();
		pushResponse.pushToGeTui = new PushToGeTui();
		for (Future<PushResult> pFuture : resultList) {
			PushResult puResult = pFuture.get();
			if (PushTaskType.GeTui==puResult.getType()) {
				if(puResult.isPublishSucc()){
					pushResponse.pushToGeTui.setCountOfSucc(puResult.getCountOfSucc());
					pushResponse.pushToGeTui.setPublishSucc(puResult.isPublishSucc());
					pushResponse.pushToGeTui.setCountOfOld(oldGeTuiCount);
				}else{
					pushResponse.pushToGeTui.setPublishSucc(false);
					pushResponse.pushToGeTui.setCountOfOld(oldGeTuiCount);
				}
			} else {
				if(puResult.isPublishSucc()){
					pushResponse.pushToComted.setCountOfSucc(puResult.getCountOfSucc());
					pushResponse.pushToComted.setPublishSucc(puResult.isPublishSucc());
					pushResponse.pushToComted.setCountOfOld(oldCometdCount);
				}else{
					pushResponse.pushToComted.setPublishSucc(false);
					pushResponse.pushToComted.setCountOfOld(oldCometdCount);
				}
			}
		}
		return pushResponse;
	}
	
	@Override
	public PushResult pushOddsMessages(final List<PushMessage> pushMessages,Integer oldCount)throws PushClientException{
		PushResponse pushResponse=new PushResponse();
		check(pushMessages);
		if(pushMessages != null && !pushMessages.isEmpty()){
			//newPushMessageHandler.saveNews(pushMessageList);
			ExecutorService executorService=Executors.newFixedThreadPool(2);
        	List<PushTask> tasks = new ArrayList<PushTask>();
        	
//        	for(int i=0;i<2;i++){
				PushTask pushTask = createPushTask();
//				if(i==0){
//					pushTask.setType(PushTaskType.CometD);//cometd
//				}else{
					pushTask.setType(PushTaskType.GeTui);
//				}
				pushTask.setPushMessages(pushMessages);
				tasks.add(pushTask);
//        	}
			try {
				List<Future<PushResult>> resultList = executorService.invokeAll(tasks);
				logger.debug("即时赔率推送请求响应:{}", resultList);
				try {
					pushResponse = makePushResponse(pushResponse, resultList,oldCount,oldCount);
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
		else{
			logger.debug("无需发送推送消息");
		}
		return pushResponse;
	}
//	public PushResult pushMessage(final PushMessage pushMessage) throws PushClientException {
//		check(pushMessage);
//		final PushResult pushResult=new PushResult();
//
////		httpClient.setMaxConnectionsPerDestination(2);
//		try {
//			httpClient.start();
//			Map<String, Object> options = new HashMap<String, Object>();
//			ClientTransport transport = new LongPollingTransport(options, httpClient);
//			final ClientSession  client = new BayeuxClient(pushServerUrl, transport);
//			final CountDownLatch messageSendLatch = new CountDownLatch(1);
//			final CountDownLatch handshakeLatch = new CountDownLatch(1);
//			client.handshake(options, new ClientSessionChannel.MessageListener(){
//
//				@Override
//				public void onMessage(ClientSessionChannel channel,
//						Message message) {
//					if (message.isSuccessful()){
//						logger.debug("handshake succ!");
//			            handleOne(pushMessage, pushResult, client, messageSendLatch);
//					}
//					else{
//						logger.debug("handshake fail!");
//						
//					}
//					handshakeLatch.countDown();
//				}
//
//			});
//			
//			handshakeLatch.await(awaitTimeInSeconds, TimeUnit.SECONDS);//至多等1秒钟
//			messageSendLatch.await(awaitTimeInSeconds, TimeUnit.SECONDS);//至多等1秒钟
//			if(pushResult.getCountOfSucc()==1){
//				 pushResult.setPublishSucc(true);
//			}	
//			client.disconnect();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		logger.debug("pushResult="+pushResult);
//		return pushResult;
//	}
	
//	private void handleOne(final PushMessage pushMessage,
//			final PushResult pushResult,
//			final ClientSession client, final CountDownLatch latch) {
//		boolean checkIfNew = newPushMessageHandler.checkIfNew(pushMessage);
//		if(checkIfNew){
//			pushMessage.checkAndSetShouldUnsubscribe();
//			Map<String, Object> data = pushMessage.toJsonDataMap();
//			final String matchChannel = pushMessage.makeChannelName();
//			
//			client.getChannel(matchChannel).publish(data, new ClientSessionChannel.MessageListener()
//			 {
//				public void onMessage(ClientSessionChannel arg0,
//						Message message) {
//					 if (message.isSuccessful())
//			         {
//			        	 logger.debug("发送消息 {} 到 {} 成功!",pushMessage,matchChannel);
//			        	 pushResult.addCountOfSucc();
//			        	 newPushMessageHandler.saveNew(pushMessage);
//			        	 
//			         }
//			         else{
//			        	 logger.debug("发送消息{}到{}失败!",pushMessage,matchChannel);
//			        	 pushResult.addCountOfFail();
//			         }
//					 latch.countDown();
//				}
//			 });
//		}
//		else{
//			pushResult.addCountOfOld();
//			latch.countDown();
//		}
//		
//		
//	}

	public String getPushServerUrl() {
		return pushServerUrl;
	}

	
	public void setPushServerUrl(String pushServerUrl) {
		this.pushServerUrl = pushServerUrl;
	}


	public long getAwaitTimeInSeconds() {
		return awaitTimeInSeconds;
	}

	public void setAwaitTimeInSeconds(long awaitTimeInSeconds) {
		this.awaitTimeInSeconds = awaitTimeInSeconds;
	}

//	@Override
//	public PushResult pushMessages(final List<PushMessage> messageList)
//			throws PushClientException {
//		check(messageList);
//		
//		final PushResult pushResult=new PushResult();
//		HttpClient httpClient = new HttpClient();
////		httpClient.setMaxConnectionsPerDestination(2);
//		try {
//			httpClient.start();
//			Map<String, Object> options = new HashMap<String, Object>();
//			ClientTransport transport = new LongPollingTransport(options, httpClient);
//			final ClientSession  client = new BayeuxClient(pushServerUrl, transport);
//			final CountDownLatch messageSendLatch = new CountDownLatch(messageList.size());
//			final CountDownLatch handshakeLatch = new CountDownLatch(1);
//			client.handshake(options, new ClientSessionChannel.MessageListener(){
//				@Override
//				public void onMessage(ClientSessionChannel channel,
//						Message message) {
//					if (message.isSuccessful()){
//						logger.debug("handshake succ!");
//						client.batch(new Runnable(){
//							@Override
//							public void run() {
//								for(final PushMessage pushMessage:messageList){
//									handleOne(pushMessage, pushResult, client, messageSendLatch);
//								}
//							}
//						});
//					}
//					else{
//						logger.debug("handshake fail!");
//					}
//					handshakeLatch.countDown();
//				}
//			});
//			
//			messageSendLatch.await(awaitTimeInSeconds*messageList.size(), TimeUnit.SECONDS);//至多等1秒钟*待发送消息数
//			handshakeLatch.await(awaitTimeInSeconds, TimeUnit.SECONDS);//至多等1秒钟
//			if(pushResult.getCountOfSucc()==messageList.size()){
//				pushResult.setPublishSucc(true);
//			}
//			client.disconnect();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		logger.debug("pushResult={}",pushResult);
//		return pushResult;
//		
//	}

	private void check(List<PushMessage> messageList) throws PushClientException {
		if(null==messageList){
			throw new PushClientException("messageList为空! ");
		}
		for(PushMessage message:messageList){
			check(message);
		}
	}

	private void check(PushMessage message) throws PushClientException {
		if(null==message){
			throw new PushClientException("pushMessage为null! ");
		}
		if(null==message.getType()){
			throw new PushClientException("pushMessage type为null! ");
		}
	}

	
	public void setNewPushMessageHandler(
			NewPushMessageHandler newPushMessageHandler) {
		this.newPushMessageHandler=newPushMessageHandler;
		
	}

	public NewPushMessageHandler getNewPushMessageHandler() {
		return newPushMessageHandler;
	}

	@Override
	public void initPushServerUrl(String pushServerUrl) {
//		if (StringUtils.isNotBlank(pushServerUrl)) {
//			setPushServerUrl(pushServerUrl);
//		}
//		if (StringUtils.isNotBlank(pushHXServerUrl)) {
//			setPushHXServerUrl(pushHXServerUrl);
//		}
	}
	public String getPushHXServerUrl() {
		return pushHXServerUrl;
	}
	public void setPushHXServerUrl(String pushHXServerUrl) {
		this.pushHXServerUrl = pushHXServerUrl;
	}


	@Override
	public PushResult pushMessage(PushMessage message)
			throws PushClientException {
		// TODO Auto-generated method stub
		return null;
	}

	public PushTask getPushTask() {
		return pushTask;
	}

	public void setPushTask(PushTask pushTask) {
		this.pushTask = pushTask;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
