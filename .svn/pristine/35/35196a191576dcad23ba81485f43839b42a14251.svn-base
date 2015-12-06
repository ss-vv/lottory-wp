package com.davcai.lottery.push.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSession;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.client.BayeuxClient;
import org.cometd.client.transport.ClientTransport;
import org.cometd.client.transport.LongPollingTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.push.common.model.BasketballMatchMessage;
import com.davcai.lottery.push.common.model.FootballMatchMessage;
import com.davcai.lottery.push.common.model.MatchMessage;
import com.davcai.lottery.push.common.model.PushLiveOdds;
import com.davcai.lottery.push.common.model.PushLqLiveOddsModel;
import com.davcai.lottery.push.common.model.PushMessage;
import com.davcai.lottery.push.common.model.PushResult;
import com.davcai.lottery.push.common.model.PushTaskType;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class PushTask implements Callable<PushResult> {

	private static final int _DEFAUL_AWAIT_TIME = 1;

	private int connectionTimeOutInMs = 30000;// 30秒

	private long awaitTimeInSeconds = _DEFAUL_AWAIT_TIME;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private String pushServerUrl;

	private String pushHXServerUrl;

	private PushTaskType type;

	private List<PushMessage> pushMessages;

	private String appId;
	private String appkey;
	private String master;
	private String host;
	
	private static IGtPush push;

	@Autowired
	private org.eclipse.jetty.client.HttpClient jettyHttpClient;

	private static ObjectMapper mapper = null;

	@Override
	public PushResult call() throws Exception {
		PushResult pushResult = new PushResult();
		if (type == PushTaskType.CometD) {// cometd
			pushToCometD(pushResult);
		} else if (type == PushTaskType.GeTui) {// 个推
			if(pusbToGeTui(pushResult)){
				pushResult.setPublishSucc(true);
			}
		}
		if (pushResult.getCountOfSucc() == pushMessages.size()) {
			pushResult.setPublishSucc(true);
		}
		pushResult.setType(type);
		return pushResult;
	}

	private boolean pusbToGeTui(PushResult pushResult) {
		boolean result1 = false;
		String jsonStr = makeJsonStringToPushGeTui(pushMessages);
		String result = pushGeTui(jsonStr);
		if (StringUtils.equals("ok", result)) {
			pushResult.addCountOfSucc();
			result1 = true;
		} else {
			pushResult.addCountOfFail();
		}
		pushResult.setType(PushTaskType.GeTui);
		return result1;
	}

	private String pushGeTui(String jsonStr) {
		if(push==null){
			push = new IGtPush(host, appkey, master);
		}
		try {
			push.connect();
			TransmissionTemplate template = transmissionTemplate(jsonStr);
			AppMessage message = new AppMessage();
			message.setData(template);
			message.setOffline(true);
			message.setOfflineExpireTime(24 * 1000 * 3600);
			List<String> appIdList = new ArrayList<String>();
			appIdList.add(appId);
			message.setAppIdList(appIdList);
			IPushResult ret = push.pushMessageToApp(message);
			push.close();
			logger.debug("推送内容：{}", jsonStr);
			logger.debug("推送结果：{}", ret.getResponse().toString());
			return (String) ret.getResponse().get("result");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public TransmissionTemplate transmissionTemplate(String jsonStr) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		template.setTransmissionType(2);
		template.setTransmissionContent(jsonStr);
		return template;
	}

	private String makeJsonStringToPushGeTui(List<PushMessage> pushMessages) {
		String jsonStr = "";
		if (pushMessages != null && !pushMessages.isEmpty()) {
			if (pushMessages.get(0) instanceof MatchMessage) {
				PushGeTuiDatas pDatas = null;
				List<PushLiveScore> pushDatas = new ArrayList<PushLiveScore>();
				PushLiveScore pushLiveScore = null;
				for (PushMessage pushMessages2 : pushMessages) {
					if (pushMessages2 instanceof MatchMessage) {
						pushLiveScore = new PushLiveScore();
						pushLiveScore.type = pushMessages2.toSubType();
						pushLiveScore.matchType = "1";
						if (StringUtils.equals("0", pushLiveScore.type)) {// 篮球
							pushLiveScore.basketballMatchMessage = (BasketballMatchMessage) pushMessages2;
							pushLiveScore.subType = !StringUtils.equals(
									pushLiveScore.basketballMatchMessage
											.getState(), "-1") ? "0" : "1";
						} else {// 足球
							pushLiveScore.footballMatchMessage = (FootballMatchMessage) pushMessages2;
							pushLiveScore.subType = !StringUtils.equals(
									pushLiveScore.footballMatchMessage
											.getState(), "-1") ? "0" : "1";
						}
						pushDatas.add(pushLiveScore);
					}
				}
				pDatas = new PushGeTuiDatas();
				pDatas.setData(pushDatas);
				jsonStr = makeJsonPushData(pDatas);
			} else if (pushMessages.get(0) instanceof PushLiveOdds) {
				List<PushLiveOdds> pushLiveOdds = new ArrayList<PushLiveOdds>();
				for (PushMessage pushMessage : pushMessages) {
					pushLiveOdds.add((PushLiveOdds) pushMessage);
				}
				PushLqLiveOddsModel lqLiveOddsModel = new PushLqLiveOddsModel();
				lqLiveOddsModel.setOddsList(pushLiveOdds);
				jsonStr = makeJsonPushData(lqLiveOddsModel);
			}

		}
		return jsonStr;
	}

	private String makeJsonPushData(Object object) {
		String jsonStr = null;
		if (mapper == null) {
			mapper = new ObjectMapper();
		}
		try {
			jsonStr = mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 推送到cometD
	 * 
	 * @param pushResult
	 * @return
	 */
	private void pushToCometD(final PushResult pushResult) {
		try {
			jettyHttpClient.start();
			Map<String, Object> options = new HashMap<String, Object>();
			ClientTransport transport = new LongPollingTransport(options,
					jettyHttpClient);
			final ClientSession client = new BayeuxClient(pushServerUrl,
					transport);
			final CountDownLatch messageSendLatch = new CountDownLatch(
					pushMessages.size());
			final CountDownLatch handshakeLatch = new CountDownLatch(1);
			client.handshake(options,
					new ClientSessionChannel.MessageListener() {

						@Override
						public void onMessage(ClientSessionChannel channel,
								Message message) {
							if (message.isSuccessful()) {
								logger.debug("handshake succ!");
								client.batch(new Runnable() {
									@Override
									public void run() {
										for (final PushMessage pushMessage : pushMessages) {
											handleOne(pushMessage, pushResult,
													client, messageSendLatch);
										}
									}
								});
							} else {
								logger.debug("handshake fail!");
							}
							handshakeLatch.countDown();
						}

					});

			handshakeLatch.await(awaitTimeInSeconds, TimeUnit.SECONDS);// 至多等1秒钟
			messageSendLatch.await(awaitTimeInSeconds, TimeUnit.SECONDS);// 至多等1秒钟
			client.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("pushResult=" + pushResult);
	}

	private void handleOne(final PushMessage pushMessage,
			final PushResult pushResult, final ClientSession client,
			final CountDownLatch latch) {
		pushMessage.checkAndSetShouldUnsubscribe();
		Map<String, Object> data = pushMessage.toJsonDataMap();
		final String matchChannel = pushMessage.makeChannelName();
		client.getChannel(matchChannel).publish(data,
				new ClientSessionChannel.MessageListener() {
					public void onMessage(ClientSessionChannel arg0,
							Message message) {
						if (message.isSuccessful()) {
							logger.debug("发送消息 {} 到 {} 成功!", pushMessage,
									matchChannel);
							pushResult.addCountOfSucc();
						} else {
							logger.debug("发送消息{}到{}失败!", pushMessage,
									matchChannel);
							pushResult.addCountOfFail();
						}
						pushResult.setType(PushTaskType.CometD);
						latch.countDown();
					}
				});
	}

	public String getPushServerUrl() {
		return pushServerUrl;
	}

	public void setPushServerUrl(String pushServerUrl) {
		this.pushServerUrl = pushServerUrl;
	}

	public String getPushHXServerUrl() {
		return pushHXServerUrl;
	}

	public void setPushHXServerUrl(String pushHXServerUrl) {
		this.pushHXServerUrl = pushHXServerUrl;
	}

	public List<PushMessage> getPushMessages() {
		return pushMessages;
	}

	public void setPushMessages(List<PushMessage> pushMessages) {
		this.pushMessages = pushMessages;
	}

	public void setType(PushTaskType huanxin) {
		this.type = huanxin;
	}

	public org.eclipse.jetty.client.HttpClient getJettyHttpClient() {
		return jettyHttpClient;
	}

	public void setJettyHttpClient(
			org.eclipse.jetty.client.HttpClient jettyHttpClient) {
		this.jettyHttpClient = jettyHttpClient;
	}

	public int getConnectionTimeOutInMs() {
		return connectionTimeOutInMs;
	}

	public void setConnectionTimeOutInMs(int connectionTimeOutInMs) {
		this.connectionTimeOutInMs = connectionTimeOutInMs;
	}

	public long getAwaitTimeInSeconds() {
		return awaitTimeInSeconds;
	}

	public void setAwaitTimeInSeconds(long awaitTimeInSeconds) {
		this.awaitTimeInSeconds = awaitTimeInSeconds;
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

}
