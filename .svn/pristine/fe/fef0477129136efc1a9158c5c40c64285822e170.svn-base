package com.unison.lottery.weibo.web.action.pc.ajax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.web.action.BaseAction;

/**
 * 检查myallpost新微博
 * @author Wang Lei
 *
 */
public class NewPostTimerAction extends BaseAction{

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
			data.setResultCode(messageService.getNewPostCount(getUserLaicaiWeiboId(), timeLine, timeLineOffset)+"");
		} catch (Exception e) {
			data.fail("查询新微博失败！");
			logger.error("newPostTimer error ! {}", data.getDesc(), e);
		}
		return SUCCESS;
	}
	
	public String newRecTimer(){
		try {
			data.setResultCode(messageService.getNewRecPostCount(getUserLaicaiWeiboId(), timeLine, timeLineOffset)+"");
		} catch (Exception e) {
			data.fail("查询新推荐微博失败！");
			logger.error("newRecTimer error ! {}", data.getDesc(), e);
		}
		return SUCCESS;
	}
	
	public String newMatchTimer(){
		try {
			data.setResultCode(messageService.getNewMatchPostCount(getUserLaicaiWeiboId(), timeLine, timeLineOffset)+"");
		} catch (Exception e) {
			data.fail("查询新赛事微博失败！");
			logger.error("newMatchTimer error ! {}", data.getDesc(), e);
		}
		return SUCCESS;
	}
	
	public String newNewsTimer(){
		try {
			data.setResultCode(messageService.getNewNewsPostCount(getUserLaicaiWeiboId(), timeLine, timeLineOffset)+"");
		} catch (Exception e) {
			data.fail("查询新新闻微博失败！");
			logger.error("newNewsTimer error ! {}", data.getDesc(), e);
		}
		return SUCCESS;
	}
	public String newDiscussTimer(){
		try {
			data.setResultCode(messageService.getNewDiscussPostCount(getUserLaicaiWeiboId(), timeLine, timeLineOffset)+"");
		} catch (Exception e) {
			data.fail("查询新讨论微博失败！");
			logger.error("newNewsTimer error ! {}", data.getDesc(), e);
		}
		return SUCCESS;
	}
	
	public String newRealMatchPostTimer(){
		try {
			data.setResultCode(messageService.getNewRealMatchPostTimer(matchId, timeLine, timeLineOffset)+"");
		} catch (Exception e) {
			data.fail("查询新赛事实单微博失败！");
			logger.error("newNewsTimer error ! {}", data.getDesc(), e);
		}
		return SUCCESS;
	}
	public String newMatchDiscussTimer(){
		try {
			data.setResultCode(messageService.getNewMatchDiscussTimer(matchId, timeLine, timeLineOffset)+"");
		} catch (Exception e) {
			data.fail("查询新赛事实单微博失败！");
			logger.error("newNewsTimer error ! {}", data.getDesc(), e);
		}
		return SUCCESS;
	}
	
	/** 赛事新闻 */
	public String newMatchNewsTimer(){
		try {
			data.setResultCode(messageService.getNewMatchNewsPostCount(matchId, timeLine, timeLineOffset)+"");
		} catch (Exception e) {
			data.fail("查询新赛事新闻微博失败！");
			logger.error("newMatchNewsTimer error ! {}", data.getDesc(), e);
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

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
}
