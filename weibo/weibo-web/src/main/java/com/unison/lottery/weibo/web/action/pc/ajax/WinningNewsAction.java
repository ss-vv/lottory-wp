package com.unison.lottery.weibo.web.action.pc.ajax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.common.service.WinningNewService;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.vo.WinningNewVO;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.msg.service.WinningWeiboService;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.xhcms.commons.lang.Data;

/**
 * @desc 用于异步获取中奖喜报内容
 * @author lei.li@unison.net.cn
 * @createTime 2014-1-6
 * @version 1.0
 */
public class WinningNewsAction extends BaseAction {

	private static final long serialVersionUID = -7819873078787379995L;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WinningNewService winningNewService;

	@Autowired
	private WinningWeiboService winningWeiboService;
	@Autowired
	private MessageService messageService;
	
	//private Data data = Data.success(null);

	private int page = 1;
	PageResult<WinningNewVO> data=new PageResult<WinningNewVO>();
	public WinningNewsAction() {
		pageRequest.setPageSize(10);
	}

	public String show() {
		return SUCCESS;
	}

	public String execute() {
		try {
			PageResult<WinningNewVO> result = winningNewService.queryByTime(
					pageRequest, RecentDateType.MONTH);
			data.setResults(result.getResults());

			
		} catch (Exception e) {
			logger.error("查询喜报列表错误：{}", e);
		}
		return SUCCESS;
	}

	/**
	 * 喜报微博列表
	 * 
	 * @return
	 */
	public String winningList() {
		try {
			pageRequest.setPageIndex(page);
			PageResult<WeiboMsgVO> result = winningWeiboService.queryWinWeibo(
					pageRequest, RecentDateType.YEAR);
			result.setUserId(getUserLaicaiWeiboId());
			
			//messageService.addMyFollowingData(getUserLaicaiWeiboId(), result);
			
			pageResult = result;
			for (Object o : pageResult.getResults()) {
				WeiboMsgVO weiboMsgVO = (WeiboMsgVO)o;
				weiboMsgVO.setLikeUsers(messageService.findLikeWeiboUser(""+weiboMsgVO.getId()));
				messageService.loadWeiboSchemeInfo(weiboMsgVO);
				messageService.loadSourceWeiboSchemeInfo(weiboMsgVO);
			}
		} catch (Exception e) {
			logger.error("查询喜报列表错误：{}", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 查看我关注的人他们的中奖喜报
	 * @return
	 */
	public String myFollowingWinningList() {
		pageRequest.setPageIndex(page);
		long weiboUserId = getUserLaicaiWeiboId();
		try {
			if(weiboUserId > 0) {
				PageResult<WeiboMsgVO> result = winningWeiboService.
						queryMyFollowingWinnings(weiboUserId, pageRequest);
				if(null != result) {
					result.setUserId(weiboUserId);
				}
				pageResult = result;
				for (Object o : pageResult.getResults()) {
					WeiboMsgVO weiboMsgVO = (WeiboMsgVO)o;
					weiboMsgVO.setLikeUsers(messageService.findLikeWeiboUser(""+weiboMsgVO.getId()));
					messageService.loadWeiboSchemeInfo(weiboMsgVO);
					messageService.loadSourceWeiboSchemeInfo(weiboMsgVO);
				}
			}
		} catch (Exception e) {
			log.error("查询我关注人的中奖喜报error={}", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}


	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public PageResult<WinningNewVO> getData() {
		return data;
	}

	public void setData(PageResult<WinningNewVO> data) {
		this.data = data;
	}

	
}
