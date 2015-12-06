package com.unison.lottery.api.protocol.response.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.unison.lottery.api.vGroupMembers.data.GroupUser;

@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class QueryGroupMembersResponse extends HaveReturnStatusResponse {

	private GroupUser groupLeader;//群主信息
	
	private List<GroupUser> userList;//群成员信息
	
	private Integer page;//起始页
	
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public GroupUser getGroupLeader() {
		return groupLeader;
	}

	public void setGroupLeader(GroupUser groupLeader) {
		this.groupLeader = groupLeader;
	}

	public List<GroupUser> getUserList() {
		return userList;
	}

	public void setUserList(List<GroupUser> userList) {
		this.userList = userList;
	}
	
}

