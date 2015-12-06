package com.unison.lottery.weibo.web.action.pc.ajax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.web.action.BaseAction;

/**
 * 加载各类新微博消息
 * @author haoxiang.jiang@unison.net.cn
 * @time 2014-3-26 下午12:21:57
 */

public class LoadNewPostAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String timeLine;
	private PageResult<WeiboMsgVO> data = new PageResult<WeiboMsgVO>();
	@Autowired
	private MessageService messageService;
	private String matchId;
 	
	public String posts(){
		try {
			setData(messageService.findNewPost(getUserLaicaiWeiboId(), Long.parseLong(timeLine)));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			data.fail("查询新的微博出错！");
			logger.error("用户={}, 查询新的微博出错！", getUserLaicaiWeiboId(),e);
		}
		return SUCCESS;
	}
	
	public String recommends(){
		try {
			setData(messageService.findNewRecommends(getUserLaicaiWeiboId(), Long.parseLong(timeLine)));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			data.fail("查询新的推荐出错！");
			logger.error("用户={}, 查询新的推荐出错！", getUserLaicaiWeiboId(),e);
		}
		return SUCCESS;
	}
	
	public String matchs(){
		try {
			setData(messageService.findNewMatchs(getUserLaicaiWeiboId(), Long.parseLong(timeLine)));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			data.fail("查询新的赛事微博出错！");
			logger.error("用户={}, 查询新的赛事微博出错！", getUserLaicaiWeiboId(),e);
		}
		return SUCCESS;
	}
	
	public String news(){
		try {
			setData(messageService.findNewNews(getUserLaicaiWeiboId(), Long.parseLong(timeLine)));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			data.fail("查询新的新闻微博出错！");
			logger.error("用户={}, 查询新的新闻微博出错！", getUserLaicaiWeiboId(),e);
		}
		return SUCCESS;
	}
	
	public String discuss(){
		try {
			setData(messageService.findNewDiscuss(getUserLaicaiWeiboId(), Long.parseLong(timeLine)));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			data.fail("查询新的新闻微博出错！");
			logger.error("用户={}, 查询新的新闻微博出错！", getUserLaicaiWeiboId(),e);
		}
		return SUCCESS;
	}
	
	public String loadNewMatchsNews(){
		try {
			setData(messageService.findNewMatchsNews(getUserLaicaiWeiboId(),matchId, Long.parseLong(timeLine)));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			data.fail("查询新的赛事新闻微博出错！");
			logger.error("查询新的赛事新闻微博出错！",e);
		}
		return SUCCESS;
	}
	public String loadNewMatchsPosts(){
		try {
			setData(messageService.findNewWeiboByKey(getUserLaicaiWeiboId(),
					matchId, Long.parseLong(timeLine),Keys.matchDataKey(matchId)));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			logger.error("",e);
		}
		return SUCCESS;
	}
	public String loadNewMatchsReal(){
		try {
			setData(messageService.findNewWeiboByKey(getUserLaicaiWeiboId(),
					matchId, Long.parseLong(timeLine),Keys.matchRealDataKey(matchId)));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			logger.error("",e);
		}
		return SUCCESS;
	}
	public String loadNewMatchsDiscuss(){
		try {
			setData(messageService.findNewWeiboByKey(getUserLaicaiWeiboId(),
					matchId, Long.parseLong(timeLine),Keys.matchDiscussKey(matchId)));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			logger.error("",e);
		}
		return SUCCESS;
	}
	
	public String getTimeLine() {
		return timeLine;
	}
	public void setTimeLine(String timeLine) {
		this.timeLine = timeLine;
	}
	public PageResult<WeiboMsgVO> getData() {
		return data;
	}
	public void setData(PageResult<WeiboMsgVO> data) {
		this.data = data;
	}
	public String getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
}
