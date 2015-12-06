package com.unison.lottery.weibo.web.action.pc.ajax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.web.action.BaseAction;

/**
 * @Description:赛事数据页面 - 计算新的赛事相关微博数量
 * @author haoxiang.jiang@unison.net.cn 
 * @date 2014-2-18 下午4:48:59 
 * @version V1.0
 */
public class NewMatchPostTimerAction extends BaseAction{

	private static final long serialVersionUID = -8973593472922384465L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String timeLine;
	private int timeLineOffset;
	private PageResult<WeiboMsg> data = new PageResult<WeiboMsg>();
	@Autowired
	private MessageService messageService;
	
	private String matchId;
	
	public String execute(){
		try {
			data.setResultCode(messageService.getNewMatchPostCount(matchId, getUserLaicaiWeiboId(), timeLine, timeLineOffset)+"");
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			data.fail("查询新赛事微博数量失败！");
			logger.error("newMatchPostTimer error ! {}", data.getDesc(), e);
		}
		return SUCCESS;
	}
	
	public PageResult<WeiboMsg> getData() {
		return data;
	}

	public void setData(PageResult<WeiboMsg> data) {
		this.data = data;
	}

	public String getTimeLine() {
		return timeLine;
	}

	public void setTimeLine(String timeLine) {
		this.timeLine = timeLine;
	}

	public int getTimeLineOffset() {
		return timeLineOffset;
	}

	public void setTimeLineOffset(int timeLineOffset) {
		this.timeLineOffset = timeLineOffset;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
}
