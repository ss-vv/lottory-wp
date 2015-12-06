package com.unison.lottery.weibo.web.action.pc;

import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.common.service.NotificationService;
import com.unison.lottery.weibo.data.WeiboNotice;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.xhcms.commons.lang.Data;

/**
 * @desc 微博通知
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-6
 * @version 1.0
 */
public class NotificationAction extends BaseAction {

	private static final long serialVersionUID = 3962117088728540350L;

	private Data data = Data.success(null);
	
	@Autowired
	private NotificationService notificationService;
	
	@Override
	public String execute() {
		Long uid = getUserLaicaiWeiboId();
		try {
			WeiboNotice weiboNotice = notificationService.lookStatus(uid);
			data.setData(weiboNotice);
		} catch (Exception e) {
			String title = "查询微博通知时出现异常";
			data = Data.failure(title);
			log.error(title, e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public Data getData() {
		return data;
	}
}