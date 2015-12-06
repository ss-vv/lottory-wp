package com.unison.lottery.weibo.web.action.pc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.Favourite;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.msg.service.FavoriteService;
import com.unison.lottery.weibo.web.action.BaseAction;

/**
 * 网页微博-我的收藏
 * @author Wang Lei
 *
 */
public class FavoriateAction extends BaseAction{

	private static final long serialVersionUID = 6013516655381351353L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private FavoriteService favoriteService;
	private Long postId;
	private PageResult<Favourite> data = new PageResult<Favourite>();
	
	/**
	 * 我的收藏
	 */
	public String execute() {
		try {
		} catch (Exception e) {
			logger.error("用户={}, 查询所有微博出错！", getUserLaicaiWeiboId(),e);
		}
		return SUCCESS;
	}
	
	public String load() {
		try {
			setData(favoriteService.list(getUserLaicaiWeiboId(), pageRequest));
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			data.fail("查询微博出错！");
			logger.error("用户={}, 查询所有微博出错！", getUserLaicaiWeiboId(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 收藏微博
	 * @return
	 */
	public String favoriate(){
		try {
			data.setDesc(favoriteService.add(getUserLaicaiWeiboId(), postId)+"");
		} catch (Exception e) {
			data.fail(Constant.ResultMessage.ERROR);
			logger.error("用户={} , {}", getUserLaicaiWeiboId(), data.getDesc(), e);
		}
		return SUCCESS;
	}
	
	/**
	 * 删除收藏
	 * @return
	 */
	public String delete(){
		try {
			data.setDesc(favoriteService.cancel(getUserLaicaiWeiboId(), postId)+"");
		} catch (Exception e) {
			data.fail(Constant.ResultMessage.DELETE_FAIL);
			logger.error("用户={}, {}", getUserLaicaiWeiboId(), data.getDesc(), e);
		}
		return SUCCESS;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public PageResult<Favourite> getData() {
		return data;
	}

	public void setData(PageResult<Favourite> data) {
		this.data = data;
	}
}
