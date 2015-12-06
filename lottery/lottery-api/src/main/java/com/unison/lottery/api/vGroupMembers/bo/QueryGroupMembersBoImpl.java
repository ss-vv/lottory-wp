package com.unison.lottery.api.vGroupMembers.bo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.unison.lottery.api.login.hx.httpclient.apidemo.EasemobChatGroups;
import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.QueryGroupMembersResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.unison.lottery.api.vGroup.data.GroupSecretkey;
import com.unison.lottery.api.vGroupMembers.data.GroupUser;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.DaVGroup;
import com.xhcms.lottery.commons.data.HX_user;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.DaVGroupService;
import com.xhcms.lottery.commons.persist.service.HX_userService;
import com.xhcms.lottery.utils.DES;
import com.xhcms.ucenter.persistent.service.IUserService;

public class QueryGroupMembersBoImpl implements QueryGroupMembersBo{

	private Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	private IStatusRepository statusRepository;
	
	@Autowired
	private DaVGroupService daVGroupService;
	
	@Autowired
	private HX_userService hx_userService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@Override
	public QueryGroupMembersResponse makeQueryGroupMembersResponse(
			String groupid, String pages) {
		QueryGroupMembersResponse queryGroupMembersResponse = new QueryGroupMembersResponse();
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QUERY_GROUP_INFO_SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QUERY_GROUP_INFO_FAIL);
		queryGroupMembersResponse.setReturnStatus(failStatus);
		if(StringUtils.isNotBlank(groupid)){
			try {
				groupid = DES.decryptDES(groupid, GroupSecretkey.secretKey, "utf-8");
				List<DaVGroup> vGroup = daVGroupService.findDaVGroupByGroupId(groupid);
				if(vGroup != null && !vGroup.isEmpty()){
					logger.info("groupid:{},获取群成员开始..." ,groupid);
					ObjectNode allMembers = EasemobChatGroups.getAllMemberssByGroupId(groupid);
					JsonNode members = allMembers.get("data");
					List<String> userMembers = new ArrayList<String>(Constants.TOTAL_VGROUP);
					String owerName = null;
					for(int i = Integer.valueOf(pages); i<members.size();i++){
						if(members.get(i).get("owner") != null){
							owerName = members.get(i).get("owner").textValue();
						} else if(members.get(i).get("member") != null){
							if(userMembers.size() < Constants.TOTAL_VGROUP){
								userMembers.add(members.get(i).get("member").textValue());
							}
						}
					}
					//加上群主
					if(members != null && owerName != null){
						GroupUser groupLeader = new GroupUser();
						HX_user hx_user = hx_userService.findHX_userByUsername(owerName);
						if(StringUtils.isNotBlank(hx_user.getUserId())){
							User user = userService.getUser(Long.valueOf(hx_user.getUserId()));
							Account account = accountService.getAccount(Long.valueOf(hx_user.getUserId()));
							if(account != null){
								groupLeader.setTotalAward(account.getTotalAward());
							}
							groupLeader.setUserId(Long.valueOf(hx_user.getUserId()));
							groupLeader.setNickName(hx_user.getNickName());
							groupLeader.setImageUrl(user.getHeadImageURL());
						}
						queryGroupMembersResponse.setGroupLeader(groupLeader);
						logger.info("群主用户名：{},群主已经获取，并装配完成",owerName);
					}
					//加上群成员
					if(userMembers != null && userMembers.size() > 0){
						List<GroupUser> userList = new ArrayList<GroupUser>(Constants.TOTAL_VGROUP);
						GroupUser groupUser = null;
						Object[] object = null;
						for(String useranme : userMembers){
							groupUser = new GroupUser();
							object = hx_userService.findUserInfoByUsername(useranme);
							if(object != null){
								groupUser.setUserId((Long)object[2]);
								groupUser.setNickName((String)object[0]);
								groupUser.setImageUrl((String)object[1]);
								userList.add(groupUser);
							}
						}
						Collections.sort(userList, new MyComparator());
						queryGroupMembersResponse.setUserList(userList);
						logger.info("群主成员用户名集：{},群成员已经获取，并装配完成", makeUsernames(userMembers));
					}
					if(StringUtils.equals("0", pages)){
						queryGroupMembersResponse.setPage(Integer.valueOf(pages) + Constants.TOTAL_VGROUP + 1);
					}else{
						queryGroupMembersResponse.setPage(Integer.valueOf(pages) + Constants.TOTAL_VGROUP);
					}
					if(userMembers != null &&  (userMembers.size() < Constants.TOTAL_VGROUP || userMembers.isEmpty())){
						queryGroupMembersResponse.setPage(-1);
					}
					queryGroupMembersResponse.setReturnStatus(succStatus);
				} else {
					return queryGroupMembersResponse;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				queryGroupMembersResponse.setReturnStatus(failStatus);
			}
		}
		return queryGroupMembersResponse;
	}

	private String makeUsernames(List<String> userMembers) {
		StringBuffer sBuffer = new StringBuffer();
		for(String username : userMembers ){
			sBuffer.append(username).append(",");
		}
		String resultStr = sBuffer.toString();
		resultStr = resultStr.substring(0, resultStr.length() - 1);
		return resultStr;
	}
	

}
