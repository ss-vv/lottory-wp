package com.xhcms.lottery.service;

import java.util.List;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.ActivityNotify;
import com.xhcms.lottery.commons.persist.entity.ActivityNotifyPO;

/**
 * @desc 活动公告服务接口
 * @createTime 2012-12-5
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public interface ActivityNotifyService {
	
	/**
	 * 查询所有活动公告
	 * @param paging
	 * @param activityName
	 * @return
	 */
	List<ActivityNotifyPO> listActivity(Paging paging, String activityName);
	
	/**
	 * 新增活动公告
	 * @param notify
	 */
    void add(ActivityNotifyPO notify);
    
    /**
	 * 修改活动公告
	 * @param notify
	 */
    void modify(ActivityNotify notify);

    /**
     * 删除活动公告
     * @param activityNotifyId
     */
    void remove(Long activityNotifyId);
	
}