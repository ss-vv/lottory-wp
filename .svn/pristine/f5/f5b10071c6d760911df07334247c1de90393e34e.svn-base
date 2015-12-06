package com.unison.lottery.weibo.web.action.pc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.service.DiscussService;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.web.action.BaseAction;

/**
 * 网页微博首页
 * 
 * @author Wang Lei
 * 
 */
public class HomeAction extends BaseAction {

	private static final long serialVersionUID = -8700565177834871349L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private int page =1;
	@Autowired
    private DiscussService discuessService;
	public HomeAction(){
		pageRequest.setPageSize(5);
	}
	public String execute() {
		pageRequest.setPageIndex(page);
		try {
			PageResult<WeiboMsgVO> result=discuessService.findHotDiscussByDate(getUserLaicaiWeiboId(), RecentDateType.DAY, pageRequest);
			pageResult = result;
			pageResult.setUserId(getUserLaicaiWeiboId());
			logger.debug("进入HOME_ACTION");
		} catch (Exception e) {
			logger.error("用户={}, 转到首页出错！", getUserLaicaiWeiboId(),e);
		}
		return SUCCESS; 
	}
}
