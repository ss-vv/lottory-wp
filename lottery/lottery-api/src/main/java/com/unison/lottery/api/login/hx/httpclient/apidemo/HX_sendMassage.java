package com.unison.lottery.api.login.hx.httpclient.apidemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.unison.lottery.api.vGroup.data.GroupSecretkey;
import com.xhcms.lottery.utils.DES;

 
public class HX_sendMassage {

	private static Logger LOGGER = LoggerFactory.getLogger(HX_sendMassage.class);
	
	private static JsonNodeFactory factory = new JsonNodeFactory(false);

	/**
	 * 发文本到特定的群里
	 * @param groupid DES密文
	 * @param jsonStr
	 * @return
	 */
	public boolean sendMassage2Group(String groupidEN, String jsonStr){
		try {
			String from = "";
			String groupid = DES.decryptDES(groupidEN, GroupSecretkey.secretKey, "utf-8");
			String targetTypeus = "chatgroups";
			ArrayNode targetgroupids = factory.arrayNode();
			ObjectNode ext = factory.objectNode();
			targetgroupids.add(groupid);
			ObjectNode txtmsg = factory.objectNode();
			txtmsg.put("msg", jsonStr);
			txtmsg.put("type", "txt");
			ObjectNode sendTxtMessageusernode = EasemobMessages.sendMessages(
					targetTypeus, targetgroupids, txtmsg, from, ext);
			LOGGER.info("已成功推送消息给groupid:{} 发了消息到环信的内容为：{}" , groupid, jsonStr);
			if (null != sendTxtMessageusernode && sendTxtMessageusernode.get("message") == null) {
				LOGGER.info("已成功推送消息给groupid:{} 发了消息为：{}" , groupid, sendTxtMessageusernode.toString());
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param groupid 没有加密的groupid
	 * @param jsonStr
	 * @return
	 */
	public boolean sendMsg2Group(String groupid, String jsonStr){
		try {
			String from = "";
			String targetTypeus = "chatgroups";
			ArrayNode targetgroupids = factory.arrayNode();
			ObjectNode ext = factory.objectNode();
			targetgroupids.add(groupid);
			ObjectNode txtmsg = factory.objectNode();
			txtmsg.put("msg", jsonStr);
			txtmsg.put("type", "txt");
			ObjectNode sendTxtMessageusernode = EasemobMessages.sendMessages(
					targetTypeus, targetgroupids, txtmsg, from, ext);
			LOGGER.info("已成功推送消息给groupid:{} 发了消息到环信的内容为：{}" , groupid, jsonStr);
			if (null != sendTxtMessageusernode && sendTxtMessageusernode.get("message") == null) {
				LOGGER.info("已成功推送消息给groupid:{} 发了消息为：{}" , groupid, sendTxtMessageusernode.toString());
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean sendMassage2Group(String groupidEN, String jsonStr,
			String target_type) {
		try {
			String targetTypeus = "chatgroups";
			//String groupid = DES.decryptDES(groupidEN, GroupSecretkey.secretKey, "utf-8");
			String groupid = groupidEN;
			ArrayNode targetgroupids = factory.arrayNode();
			targetgroupids.add(groupid);
			ObjectNode sendTxtMessageusernode = EasemobMessages.sendMessages(
					targetTypeus,
					targetgroupids,jsonStr,target_type);
			LOGGER.info("已成功推送消息给groupid:{} 发了消息到环信的内容为：{}" , groupid, jsonStr);
			if (null != sendTxtMessageusernode && sendTxtMessageusernode.get("message") == null) {
				LOGGER.info("已成功推送消息给groupid:{} 发了消息为：{}" , groupid, sendTxtMessageusernode.toString());
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
