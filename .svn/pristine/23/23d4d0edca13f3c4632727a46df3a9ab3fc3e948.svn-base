package com.xhcms.lottery.commons.persist.service;

import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Message;
import com.xhcms.lottery.commons.data.SysMessage;
import com.xhcms.lottery.commons.persist.entity.SysMessagePO;

/**
 * @desc 站内信息服务接口
 * @author yonglizhu
 */
public interface MessageService {
	
	/**
	 * 查询系统站内信息
	 * @param paging
	 * @param subject
	 * @return
	 */
	void listSysMessage(Paging paging, String subject);
	
	/**
	 * 新增系统站内信息
	 * @param message
	 */
    void add(SysMessagePO sysMessagePO);
    
    /**
	 * 修改系统站内信息
	 * @param notify
	 */
    void modify(SysMessage sysMessage);

    /**
     * 删除系统站内信息
     * @param sysMessageId
     */
    void remove(Long sysMessageId);
    
    /**
     * 同步系统信息并查询用户个人站内信
     * @param paging
     * @param userId
     * @param username
     */
    void syncAndListMyMessages(Paging paging, Long userId, String username);
    
    /**
     * 根据用户id查询用户站内信
     * @param paging
     * @param userId
     * @param username
     */
    void listMyMessages(Paging paging, Long userId);
    
    /**
     * 阅读站内信
     * @param messageId
     */
    Message read(Long messageId);
	
    /**
     * 删除用户个人站内信
     */
    void removeMyMessage(List<Long> ids);
    
    /**
     * 取得未读信息的数量
     * @param userId
     * @return
     */
    int getUnreadCount(Long userId);
    
}