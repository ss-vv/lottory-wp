package com.unison.lottery.weibo.common.persist.impl;

import com.unison.lottery.weibo.common.redis.ObjectId;
import com.unison.lottery.weibo.common.redis.ObjectKey;

/**
 * 测试用的 model.
 * 
 * @author Yang Bo
 */
@ObjectKey(key="test:user:%s", nextIdKey="global:nextId:testuser")
public class TestUser {

	@ObjectId
	private Long idNotNormal;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getIdNotNormal() {
		return idNotNormal;
	}

	public void setIdNotNormal(Long idNotNormal) {
		this.idNotNormal = idNotNormal;
	}
}
