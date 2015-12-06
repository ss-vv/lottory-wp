package com.unison.lottery.api.login.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.unison.lottery.api.login.hx.comm.Constants;
import com.unison.lottery.api.login.hx.httpclient.apidemo.EasemobChatGroups;
import com.unison.lottery.api.login.hx.httpclient.apidemo.EasemobIMUsers;
import com.unison.lottery.api.login.hx.httpclient.apidemo.HX_sendMassage;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.vGroup.data.Group;
import com.unison.lottery.api.vGroup.data.GroupSecretkey;
import com.xhcms.commons.util.Text;
import com.xhcms.lottery.commons.data.DaVGroup;
import com.xhcms.lottery.commons.data.HX_user;
import com.xhcms.lottery.commons.persist.service.DaVGroupService;
import com.xhcms.lottery.commons.persist.service.HX_userService;
import com.xhcms.lottery.utils.DES;

public class HX_serviceImpl implements HX_service {

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private HX_userService hX_userService;
	
	@Autowired
	private DaVGroupService daVGroupService;
	
	@Override
	@Transactional
	public HX_user createHx_user(User user, Long id, String nickname) {
		HX_user hx_user = hX_userService.getHX_user(id);
		String groupid = validParams(user.getChannel());
		//注册环信账号和密码，并将其入库
		if(StringUtils.isNotBlank(groupid)){
			if(StringUtils.isBlank(hx_user.getUsername()) && StringUtils.isBlank(hx_user.getPassword())){
				hx_user = makeHX_usernameAndHX_password(nickname, String.valueOf(id));
			} 
			//加入特定的群
			if(StringUtils.isNotBlank(hx_user.getUsername()) && StringUtils.isNotBlank(hx_user.getPassword())){
				ObjectNode allMembers = EasemobChatGroups.getAllMemberssByGroupId(groupid);
				JsonNode members = allMembers.get("data");
				List<String> usermenbers = new ArrayList<String>();
				for(int i = 0; i<members.size();i++){
					if(members.get(i).get("member") != null){
						String username = members.get(i).get("member").toString();
						usermenbers.add(username.substring(1, username.length() -1));
					}
				}
				try {
					String davId = DES.decryptDES(user.getChannel(), GroupSecretkey.secretKey, "utf-8").split("[+]")[1];
					if(!usermenbers.contains(hx_user.getUsername()) && !StringUtils.equals(hx_user.getUserId(), davId)){
						if(addUserToGroup(groupid, hx_user)){
							noticeGroup(groupid, nickname);
							logger.info("username:{} 加群成功...", hx_user.getUsername());
						} else {
							logger.info("username:{} 加群失败...", hx_user.getUsername());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
		}
		ObjectNode groups = EasemobChatGroups.getJoinedChatgroupsForIMUser(hx_user.getUsername()); 
		int count = groups.get("data").size();
		String groupId = "";
		ObjectNode  data = null;
		if(!StringUtils.equals(groupid, Constants.COMMON_GROUP)){
			for(int i = 0 ; i<count ; i++){
				groupId = groups.get("data").get(i).get("groupid").textValue();
				if(StringUtils.equals(groupId, groupid)){
					data = EasemobIMUsers.getShieldUsersByusername(hx_user.getUsername(), groupId, false);
					logger.info("非普通群, groupid :{} ,是否屏蔽 ： {}" , groupId, false);
				} else {
					data = EasemobIMUsers.getShieldUsersByusername(hx_user.getUsername(), groupId, true);
					logger.info("非普通群, groupid :{} ,是否屏蔽 ： {}" , groupId, true);
				}
			}
		} else {
			if(groups != null && groups.get("data") != null){
				for(int i = 0 ; i<count ; i++){
					groupId = groups.get("data").get(i).get("groupid").textValue();
					data = EasemobIMUsers.getShieldUsersByusername(hx_user.getUsername(), groupId, false);
					logger.info("普通群, groupid :{} ,是否屏蔽 ： {}" , groupId, false);
				}
			}
		}
		return hx_user;
	}

	/**
	 */
	private String validParams(String channel) {
		String reslut = null;
		try {
			String decryptStr = DES.decryptDES(channel, GroupSecretkey.secretKey, "utf-8");
			if(StringUtils.isNotBlank(decryptStr)){
				String[] strs = decryptStr.split("[+]");
				logger.info("环信groupId: {} , 大V id : {} , clientVersion : {}" , strs[0], strs[1], strs[2]);
				List<DaVGroup> daVGroups = daVGroupService.findDaVGroupByGroupId(strs[0]);
				if(isValid(daVGroups, strs)){
					reslut = strs[0];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reslut;
	}
	
	private boolean isValid(List<DaVGroup> daVGroups, String[] strs) {
		boolean result = false;
		for(DaVGroup daVGroup : daVGroups){
			if(daVGroup != null && StringUtils.equals(strs[1], daVGroup.getVid()) && StringUtils.equals(strs[2], daVGroup.getClientVersion())){
				result = true;
			}
		}
		return result;
	}
	
	private boolean addUserToGroup(String groupid, HX_user hx_user){
		boolean reslut = false;
		ObjectNode addUserToGroupNode = EasemobChatGroups.addUserToGroup(groupid, hx_user.getUsername());
		if(!(addUserToGroupNode.get("data") != null && StringUtils.equals(addUserToGroupNode.get("data").get("result").toString(), "true"))){
	    	return reslut;
	    }
	    reslut = true;
	    return reslut;
	}
	
	/**
     * 注册环信账号和密码（账号：昵称的MD5, 密码： 昵称+定长字符串的MD5）
     * @param nickname
     * @param userId 用户的id
     * @return
     */
	private HX_user makeHX_usernameAndHX_password(String nickname, String userId) {
		HX_user hx_user = null;
		//注册请求
		 ObjectNode datanode = JsonNodeFactory.instance.objectNode();
	        datanode.put("username", Text.MD5Encode(userId + Constants.APPKEY));
	        datanode.put("password", Text.MD5Encode(userId + VONames.HX_USE_STRINE));
	        datanode.put("nickname", nickname);
	        ObjectNode createNewIMUserSingleNode = EasemobIMUsers.createNewIMUserSingle(datanode);
	        if(null != createNewIMUserSingleNode && createNewIMUserSingleNode.get("error") != null && StringUtils.isNotBlank(createNewIMUserSingleNode.get("error").toString())){
	        	logger.info("注册IM用户[单个], 已经存在username : {}" ,Text.MD5Encode(nickname));
	        	hx_user = new HX_user();
	        	hx_user.setUsername(Text.MD5Encode(userId + Constants.APPKEY));
        		hx_user.setPassword(Text.MD5Encode(userId + VONames.HX_USE_STRINE));
        		//成功后保存入库
        		hX_userService.saveOrUpdateHX_user(hx_user, userId, nickname);
        		return hx_user;
	        }
	        if (null != createNewIMUserSingleNode && !createNewIMUserSingleNode.has("message")) {
	        	logger.info("注册IM用户[单个]: " + createNewIMUserSingleNode.toString());
	        	if(StringUtils.equals(createNewIMUserSingleNode.get("statusCode").toString() , "200")){
	        		hx_user = new HX_user();
	        		hx_user.setUsername(Text.MD5Encode(userId + Constants.APPKEY));
	        		hx_user.setPassword(Text.MD5Encode(userId + VONames.HX_USE_STRINE));
	        		//成功后保存入库
	        		hX_userService.saveOrUpdateHX_user(hx_user, userId, nickname);
	        	}
	        }
		return hx_user;
	}
	
	/**
	 * 通知群里谁加入了
	 * @param groupid
	 * @param nickName
	 */ 
    private void noticeGroup(String groupid, String nickName) {
    	String jsonStr = "{ \"type\" : \"6\" , \"nickName\" :\"" + nickName + "\"}";
    	HX_sendMassage hx_sendMassage = new HX_sendMassage();
		if(hx_sendMassage.sendMsg2Group(groupid, jsonStr)){
			logger.info("通知群成功，nickName= {}", nickName);
		} else {
			logger.info("通知群失败，nickName= {}", nickName);
		}
	}

	@Override
	@Transactional
	public HX_user registeHx_user(String nickname, String id) {
		HX_user hx_user = hX_userService.getHX_user(Long.valueOf(id));
		String username = Text.MD5Encode(id + Constants.APPKEY);
		String password = Text.MD5Encode(id + VONames.HX_USE_STRINE);
		if(StringUtils.isBlank(hx_user.getUsername()) && StringUtils.isBlank(hx_user.getPassword())){
			//注册请求
			ObjectNode datanode = JsonNodeFactory.instance.objectNode();
			datanode.put("username", username);
			datanode.put("password", password);
			datanode.put("nickname", nickname);
			ObjectNode createNewIMUserSingleNode = EasemobIMUsers.createNewIMUserSingle(datanode);
			if (null != createNewIMUserSingleNode && !createNewIMUserSingleNode.has("message")) {
				logger.info("注册IM用户[单个]: " + createNewIMUserSingleNode.toString());
				if(StringUtils.equals(createNewIMUserSingleNode.get("statusCode").toString() , "200")){
					hx_user.setUsername(username);
					hx_user.setPassword(password);
					//成功后保存入库
					hX_userService.saveOrUpdateHX_user(hx_user, id, nickname);
				}
			}
		} else {
			hx_user.setNickName(nickname);
			hx_user.setUsername(username);
			hx_user.setPassword(password);
			hX_userService.updateHX_user(hx_user);
		}
		return hx_user;
	}
}
