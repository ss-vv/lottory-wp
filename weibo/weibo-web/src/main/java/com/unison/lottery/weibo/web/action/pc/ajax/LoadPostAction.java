package com.unison.lottery.weibo.web.action.pc.ajax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.web.action.BaseAction;

/**
 * ajax获取微博，使用timeLine
 * @author Wang Lei
 *
 */
public class LoadPostAction extends BaseAction{
	
	private static final long serialVersionUID = -7819873078787379995L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String timeLine;
	private PageResult<WeiboMsgVO> data = new PageResult<WeiboMsgVO>();
	@Autowired
	private MessageService messageService;
	private String matchId;
	
	public String execute(){
		try {
			setData(messageService.findAllPost(getUserLaicaiWeiboId(), timeLine));
		} catch (Exception e) {
			data.fail("查询微博出错！");
			logger.error("{}",data.getDesc(),e);
		}
		return SUCCESS;
	}
	
	public String load(){
		try {
			setData(messageService.findAllPost(getUserLaicaiWeiboId(), pageRequest));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			data.fail("查询微博出错！");
			logger.error("用户={}, 查询所有微博出错！", getUserLaicaiWeiboId(),e);
		}
		return SUCCESS;
	}
	
	/**  查询用户微博推荐列表 */
	public String loadRecommends(){
		try {
			setData(messageService.findAllRecommendsPost(getUserLaicaiWeiboId(), pageRequest));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			data.fail("查询微博出错！");
			logger.error("用户={}, 查询所有微博出错！", getUserLaicaiWeiboId(),e);
		}
		return SUCCESS;
	}
	
	/**  查询用户微博赛事列表 */
	public String loadMatch(){
		try {
			setData(messageService.findAllMatchsPost(getUserLaicaiWeiboId(), pageRequest));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			data.fail("查询微博出错！");
			logger.error("用户={}, 查询所有微博出错！", getUserLaicaiWeiboId(),e);
		}
		return SUCCESS;
	}
	
	/**  查询用户微博新闻列表 */
	public String loadNews(){
		try {
			setData(messageService.findAllNewsPost(getUserLaicaiWeiboId(), pageRequest));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			data.fail("查询微博出错！");
			logger.error("用户={}, 查询所有微博出错！", getUserLaicaiWeiboId(),e);
		}
		return SUCCESS;
	}
	/**  查询用户微博讨论列表 */
	public String loadDiscussWeibo(){
		try {
			setData(messageService.findAllDiscussPost(getUserLaicaiWeiboId(), pageRequest));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			data.fail("查询讨论微博出错！");
			logger.error("用户={}, 查询所有讨论微博出错！", getUserLaicaiWeiboId(),e);
		}
		return SUCCESS;
	}
	
	/**  查询赛事微博新闻列表 */
	public String loadMatchsNews(){
		try {
			setData(messageService.findAllMatchNewsPost(getUserLaicaiWeiboId(),matchId, pageRequest));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			data.fail("查询微博出错！");
			logger.error("用户={}, 查询所有微博出错！", getUserLaicaiWeiboId(),e);
		}
		return SUCCESS;
	}
	/**  查询赛事讨论微博列表 */
	public String loadMatchDiscussPost(){
		try {
			setData(messageService.findMatchDiscussPost(getUserLaicaiWeiboId(),matchId, pageRequest));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			data.fail("查询微博出错！");
			logger.error("用户={}, 查询所有微博出错！", getUserLaicaiWeiboId(),e);
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
