package com.unison.lottery.weibo.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.unison.lottery.weibo.data.WeiboMsgVO;

public class Tool {
	
	/**
	 * 计算用户id集合
	 * @param msgs
	 * @return
	 */
	public static Set<Long> findUserIds(List<WeiboMsgVO> msgs){
		Set<Long> userIds = new HashSet<Long>();
		for(WeiboMsgVO msg : msgs){
			if(msg.getOwnerId() > 0)
				userIds.add(msg.getOwnerId());
			if(msg.getSourceWeiboMsg() != null && msg.getSourceWeiboMsg().getOwnerId() > 0){
				userIds.add(msg.getSourceWeiboMsg().getOwnerId());
			}
		}
		return userIds;
	}
	
	public static Set<Long> findUserIds(WeiboMsgVO msgs){
		List<WeiboMsgVO> list = new ArrayList<WeiboMsgVO>();
		list.add(msgs);
		return findUserIds(list);
	}

}
