package com.unison.lottery.weibo.web.action.pc.marrow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.common.service.DiscussService;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.lang.WeiboType;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.service.BetSchemeRecService;
import com.xhcms.lottery.commons.persist.service.GetPresetSchemeService;

/**
 * @desc 热门讨论
 * @author lei.li@unison.net.cn
 * @createTime 2013-12-25
 * @version 1.0
 */
public class HotDiscussAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	Logger log = LoggerFactory.getLogger(getClass());

	private int page = 1;

	@Autowired
	private DiscussService discussService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private GetPresetSchemeService getPresetSchemeService;
	@Autowired
	BetSchemeRecService betSchemeRecService;
	
	public HotDiscussAction() {
		pageRequest.setPageSize(5);
	}

	public String show() {
		return SUCCESS;
	}

	@Override
	public String execute() {
		try {
			pageRequest.setPageIndex(page);
			
			PageResult<WeiboMsgVO> result =discussService.findHotDiscussByDate(getUserLaicaiWeiboId(), RecentDateType.DAY, pageRequest); 
			//messageService.addMyFollowingData(getUserLaicaiWeiboId(), result);
			
			pageResult = result;
			pageResult.setUserId(getUserLaicaiWeiboId());
			for (Object o : pageResult.getResults()) {
				WeiboMsgVO weiboMsgVO = (WeiboMsgVO)o;
				weiboMsgVO.setLikeUsers(messageService.findLikeWeiboUser(""+weiboMsgVO.getId()));
				messageService.loadWeiboSchemeInfo(weiboMsgVO);
				messageService.loadSourceWeiboSchemeInfo(weiboMsgVO);
			}
			
		} catch (Exception e) {
			log.error("查询热门讨论error:", e);
		}
		return SUCCESS;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
}