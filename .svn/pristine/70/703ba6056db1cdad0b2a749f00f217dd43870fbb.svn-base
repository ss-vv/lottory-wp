package com.unison.lottery.weibo.web.action.pc.ajax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.web.action.BaseAction;
/**
 * @Description:获取赛事微博数据 
 * @author haoxiang.jiang@unison.net.cn 
 * @date 2014-2-18 下午4:58:49 
 * @version V1.0
 */
public class LoadMatchPostAction extends BaseAction{
	
	private static final long serialVersionUID = -7819873078787379995L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String timeLine;
	private PageResult<WeiboMsgVO> data = new PageResult<WeiboMsgVO>();
	@Autowired
	private MessageService messageService;
	private String matchId;
	
	public String execute(){
		try {
			setData(messageService.findAllMatchPost(getUserLaicaiWeiboId(), matchId ,timeLine));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			data.fail("查询赛事相关微博出错！");
			logger.error("{}",data.getDesc(),e);
		}
		return SUCCESS;
	}
	
	public String load(){
		try {
			setData(messageService.findAllMatchPost(getUserLaicaiWeiboId(), matchId ,pageRequest));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			data.fail("查询赛事相关微博出错！");
			logger.error("{}",data.getDesc(),e);
		}
		return SUCCESS;
	}
	public String loadMatchsDiscuss(){
		try {
			setData(messageService.findAllMatchDiscussPost(getUserLaicaiWeiboId(), matchId ,pageRequest));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			data.fail("查询赛事讨论相关微博出错！");
			logger.error("{}",data.getDesc(),e);
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
