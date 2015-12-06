package com.unison.lottery.pm.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.exception.XHRuntimeException;

public class LevelUser {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private String users;
	private List<Long> userList;
	
	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		logger.info("injection users={}", users);
		this.users = users;
	}
	
	public List<Long> getUserList() {
		if(StringUtils.isNotBlank(users)) {
			String[] userArray = users.split(",");
			userList = new ArrayList<Long>();
			for(String u : userArray) {
				if(StringUtils.isNotBlank(u)) {
					userList.add(Long.valueOf(u));
				}
			}
		}
		logger.info("解析出活动参与的高优先级用户列表:{}", userList);
		if(null == userList || userList.size() == 0) {
			throw new XHRuntimeException("查找不到活动的高优先级用户！");
		}
		return userList;
	}
}