package com.unison.lottery.weibo.web.action.pc.ajax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.common.service.AnnounceService;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.xhcms.commons.lang.Data;

/**
 * @desc 用于异步获取公告内容
 * @author lei.li@unison.net.cn
 * @createTime 2014-1-6
 * @version 1.0
 */
public class AnnounceAction extends BaseAction{
	
	private static final long serialVersionUID = -7819873078787379995L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private Data data = Data.success(null);
	
	@Autowired
	private AnnounceService announceService;
	
	public String execute(){
		try {
			pageRequest.setPageSize(5);
			PageResult<WeiboMsg> result = announceService.query(pageRequest);
			data.setData(result);
		} catch (Exception e) {
			data = Data.failure("查询公告列表错误");
			logger.error("查询公告列表错误：{}", e);
		}
		return SUCCESS;
	}

	public Data getData() {
		return data;
	}
}
