package com.unison.lottery.weibo.web.action.mobile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.web.action.BaseAction;

/**
 * 发布手机微博消息
 * @author Wang Lei
 *
 */
public class PublishAction extends BaseAction {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 3577708384263535007L;
	@Autowired
	private MessageService messageService;
	private WeiboMsg weiboMsg;
	private long postId;
	private PageResult<WeiboMsg> data = new PageResult<WeiboMsg>();
	
	/** 准备发微博 */
	public String toPublish(){
		try {
			
		} catch (Exception e) {
			data.fail(Constant.ResultMessage.ERROR);
			logger.error("{}", data.getDesc(), e);
		}
		return SUCCESS;
	}
	
	/** 准备转发微博 */
	public String toForward(){
		try {
			setWeiboMsg(messageService.getWeiboById(postId));
			if(weiboMsg == null){
				data.fail(Constant.ResultMessage.FORWARD_FAIL_POST);
				return SUCCESS;
			}
		} catch (Exception e) {
			data.fail(Constant.ResultMessage.ERROR);
			logger.error("{}",data.getDesc(), e);
		}
		return SUCCESS;
	}
	
	/** 发微博 */
	public String publish(){
		try {
			long uid = getUserLaicaiWeiboId();
			if(uid < 1){
				data.fail(Constant.ResultMessage.PUBLISH_FAIL_LOGIN);
				logger.info(data.getDesc());
				return SUCCESS;
			}
			if(weiboMsg == null){
				data.fail(Constant.ResultMessage.PUBLISH_FAIL);
				logger.info(data.getDesc());
				return SUCCESS;
			}
			weiboMsg.setOwnerId(uid);
			messageService.publish(weiboMsg);
		} catch (Exception e) {
			data.fail(Constant.ResultMessage.PUBLISH_FAIL);
			logger.error("{}", data.getDesc(),e);
		}
		return SUCCESS;
	}
	
	public WeiboMsg getWeiboMsg() {
		return weiboMsg;
	}

	public void setWeiboMsg(WeiboMsg weiboMsg) {
		this.weiboMsg = weiboMsg;
	}

	public PageResult<WeiboMsg> getData() {
		return data;
	}

	public void setData(PageResult<WeiboMsg> data) {
		this.data = data;
	}
}
