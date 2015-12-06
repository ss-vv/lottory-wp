package com.unison.lottery.api.callBack;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.unison.lottery.api.callBack.model.ActivityOrNotice;
import com.unison.lottery.api.login.hx.httpclient.apidemo.EasemobChatGroups;
import com.unison.lottery.api.login.hx.httpclient.apidemo.EasemobMessages;
import com.unison.lottery.api.login.hx.httpclient.exception.HX_CallBackException;
import com.unison.lottery.api.login.hx.httpclient.exception.HX_CallBackNoGroupidsException;
 
@WebServlet("/syncSendAllGroupMsg")
public class SendAllGroupMsgServlet extends HttpServlet{
	//活动、公告、视频直播 推送

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static JsonNodeFactory factory = new JsonNodeFactory(false);
	
	Logger logger = LoggerFactory.getLogger(LiveScoresServlet.class);
	
	protected ConfigurableWebApplicationContext ctx;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ctx = (ConfigurableWebApplicationContext) WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Map<String, String> map = makeParamsToMap(req); 
		String type = map.get("type");
		ActivityOrNotice activityOrNotice = new ActivityOrNotice();
		activityOrNotice.type = type;
		if(StringUtils.equals(type, "1")){//活动
			activityOrNotice.content = map.get("content");
			activityOrNotice.activityId = map.get("activityId");
			activityOrNotice.activityUrl = map.get("activityUrl");
			activityOrNotice.imageUrl = map.get("imageUrl");
			activityOrNotice.endTime = map.get("endTime");
		} else if (StringUtils.equals(type, "2")) {//公告
			activityOrNotice.content = map.get("content");
		} else if (StringUtils.equals(type, "5")) {//视频直播
			activityOrNotice.host = map.get("host");
			activityOrNotice.guest = map.get("guest");
			activityOrNotice.url = map.get("url");
			activityOrNotice.info = map.get("info");
		}
		//发所有的群
		ObjectNode groupids = EasemobChatGroups.getAllChatgroupids();
		JsonNode datas = groupids.get("data");
		JsonNode error = groupids.get("error");
		PrintWriter writer = null;
		String msg = null;
		try {
			if (groupids != null && error != null) {
				throw new HX_CallBackException();
			}
			if(groupids != null && datas == null){
				throw new HX_CallBackNoGroupidsException();
			}
			String from = "";
			String targetTypeus = "chatgroups";
			ArrayNode targetgroupids = factory.arrayNode();
			ObjectNode ext = factory.objectNode();
			for (int i = 0; i < datas.size(); i++) {
				targetgroupids.add(datas.get(i).get("groupid"));
			}
			//sendMsg
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(activityOrNotice);
			
			ObjectNode txtmsg = factory.objectNode();
			txtmsg.put("msg", jsonString);
			txtmsg.put("type", "txt");
			
			ObjectNode sendTxtMessageusernode = EasemobMessages.sendMessages(
					targetTypeus, targetgroupids, txtmsg, from, ext);
			writer =resp.getWriter();
			if (null != sendTxtMessageusernode && sendTxtMessageusernode.get("message") == null) {
				logger.info("给所有的群发一条文本消息: " + sendTxtMessageusernode.toString());
				msg="ok";
			} else {
				msg = "error";
			}
		} catch (HX_CallBackException e) {
			logger.info("活动或者公告推送,获取所有的群的groupid失败...");
			logger.info("活动或者公告推送失败,环信返回信息为：{}", groupids.toString());
			msg = "error";
			e.printStackTrace();
		} catch (HX_CallBackNoGroupidsException e) {
			logger.info("活动或者公告推送成功,但是没有群的相关信息，返回信息为：{}", groupids.toString());
			msg = "error";
			e.printStackTrace();
		}
		resp.setContentType("text/html;charset=utf8");
		writer.write(msg);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doPost(req, resp);
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> makeParamsToMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> e = (Enumeration<String>) request.getParameterNames();
		while (e.hasMoreElements()) {
			String parName = (String) e.nextElement();
			String value = request.getParameter(parName);
			map.put(parName, value);
		}
		return map;
	}
	
}
