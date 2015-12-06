package com.xhcms.lottery.commons.persist.service;

import java.util.List;

import com.xhcms.lottery.commons.data.DaVGroup;
import com.xhcms.lottery.commons.data.User;

public interface DaVGroupService {

	 /**
     * 查询大v群列表
     * @param firstResult 分页开始
     * @param pageMaxResult 一页的最大值
     */
    List<DaVGroup> listGroup(Integer firstResult,Integer pageMaxResult);
    
    /**
     * 根据群号查询
     * @param groupid
     * @return
     */
    List<DaVGroup> findDaVGroupByGroupId(String groupid);

	List<User> listAll();
	
}
