package com.unison.lottery.api.vGroup.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.unison.lottery.api.login.hx.comm.Constants;
import com.unison.lottery.api.login.hx.httpclient.apidemo.EasemobChatGroups;
import com.unison.lottery.api.login.hx.httpclient.apidemo.EasemobIMUsers;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.QueryGroupInfoResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.unison.lottery.api.util.AllowReturnResultService;
import com.unison.lottery.api.vGroup.data.Group;
import com.unison.lottery.api.vGroup.data.GroupSecretkey;
import com.xhcms.lottery.commons.data.DaVGroup;
import com.xhcms.lottery.commons.data.HX_user;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.DaVGroupService;
import com.xhcms.lottery.commons.persist.service.HX_userService;
import com.xhcms.lottery.utils.DES;
import com.xhcms.ucenter.persistent.service.IUserService;

public class QueryGroupInfoBoImpl implements QueryGroupInfoBo{

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DaVGroupService daVGroupService;
	
	@Autowired
	private IStatusRepository statusRepository;
	
	@Autowired
	private HX_userService hx_userService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private AllowReturnResultService allowReturnResultService;
	
	@Override
	public QueryGroupInfoResponse makeQueryGroupInfoResponse(String channel,
			String groupid, com.unison.lottery.api.model.User user) {
		QueryGroupInfoResponse queryGroupInfoResponse = new QueryGroupInfoResponse();
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QUERY_GROUP_INFO_SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QUERY_GROUP_INFO_FAIL);
		queryGroupInfoResponse.setReturnStatus(failStatus);
		logger.info("验证请求参数开始...");
		if(allowReturnResultService.allow(user)){
			HX_user hxUser = hx_userService.getHX_user(Long.parseLong(user.getId()));
			doMakeQueryGroupInfoResponse(channel, groupid,
					queryGroupInfoResponse, succStatus, hxUser);
		} else {
			queryGroupInfoResponse.setReturnStatus(succStatus);
		}
		
		return queryGroupInfoResponse;
	}

	private void doMakeQueryGroupInfoResponse(String channel, String groupid,
			QueryGroupInfoResponse queryGroupInfoResponse,
			ReturnStatus succStatus, HX_user hxUser) {
		if(StringUtils.isNotBlank(groupid) && validParams(channel, groupid)){
			ObjectNode groups = EasemobChatGroups.getAllChatgroupids(); 
			List<Group> groupList = new ArrayList<Group>();
			int count = groups.get("data").size();
			String groupId = "";
			ObjectNode data = null;
			if(!StringUtils.equals(groupid, Constants.COMMON_GROUP)){
				String[] chatgroupIDs = {groupid};
				makeGroups(chatgroupIDs, groupList);
				for(int i = 0 ; i<count ; i++){
					groupId = groups.get("data").get(i).get("groupid").textValue();
					if(StringUtils.equals(groupId, groupid)){
						data = EasemobIMUsers.getShieldUsersByusername(hxUser.getUsername(), groupId, false);
					} else {
						data = EasemobIMUsers.getShieldUsersByusername(hxUser.getUsername(), groupId, true);
					}
					logger.info("common_ groupId : {} , callback : {} " , groupId , data.toString());
				}
			} else{
				if(groups != null && groups.get("data") != null){
					for(int i = 0 ; i<count ; i++){
						groupId = groups.get("data").get(i).get("groupid").textValue();
						String[] chatgroupIDs = {groupId};
						makeGroups(chatgroupIDs, groupList);
						data = EasemobIMUsers.getShieldUsersByusername(hxUser.getUsername(), groupId, false);
						logger.info("groupId : {} , callback : {} " , groupId , data.toString());
					}
				}
			}
			queryGroupInfoResponse.setGroupList(groupList);
			queryGroupInfoResponse.setReturnStatus(succStatus);
		}
	}

	private List<Group> makeGroups(String[] chatgroupIDs, List<Group> groupList) {
		ObjectNode groupDetail = EasemobChatGroups.getGroupDetailsByChatgroupid(chatgroupIDs);
		logger.info("groupid: {} 群组的详情 : {} ", chatgroupIDs[0], groupDetail.toString());
		if(groupDetail.get("message") == null){
			Group group = new Group();
			JsonNode data = groupDetail.get("data").get(0);
			JsonNode members = data.get("affiliations");
			String name = "";
			for(int i =0 ; i<members.size();i++){
				if(members.get(i).get("owner") != null){
					name = members.get(i).get("owner").toString();
				}
			}
			if(StringUtils.isNotBlank(name)){
				HX_user hxUser = hx_userService.findHX_userByUsername(name.substring(1, name.length() - 1));
				if(hxUser != null && StringUtils.isNotBlank(hxUser.getUsername())){
					User user = userService.getUser(Long.valueOf(hxUser.getUserId()));
					if(user !=null){
						group.setGroupImageUrl(user.getHeadImageURL());
					}
				}
			}
			String groupName=data.get("name").toString();
			group.setGroupName(groupName.substring(1, groupName.length()-1));
			group.setGroupSize(data.get("affiliations_count").toString());
			group.setGroupid(chatgroupIDs[0]);
			groupList.add(group);
		}
		return groupList;
	}
	/**
	 * 验证请求参数是否合法
	 * @param channel
	 * @param groupid
	 * @return
	 */
	private boolean validParams(String channel, String groupid) {
		boolean reslut = false;
		try {
			String decryptStr = DES.decryptDES(channel, GroupSecretkey.secretKey, "utf-8");
			if(StringUtils.isNotBlank(decryptStr)){
				String[] strs = decryptStr.split("[+]");
				logger.info("环信groupId: {} , 大V id : {} , clientVersion : {}" , strs[0], strs[1], strs[2]);
				List<DaVGroup> daVGroups = daVGroupService.findDaVGroupByGroupId(strs[0]);
				if(isValid(daVGroups, strs)){
					reslut = true;
				} else {
					logger.info("验证参数失败，groupId: {} , 大V id : {} , clientVersion : {}" , strs[0], strs[1], strs[2]);
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

}
