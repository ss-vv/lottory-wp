package com.unison.lottery.weibo.web.action.pc.ajax;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.UserStatisService;

public class LastWinWeibo extends BaseAction{

	private static final long serialVersionUID = 7662328707308136034L;
	
	private List<WeiboMsgVO> weiboMsgVOs;
	
	@Autowired
	private UserStatisService userStatisService;
	
	public String execute(){
		weiboMsgVOs = userStatisService.getLastWinWeibos();
		return SUCCESS;
	}

	public List<WeiboMsgVO> getWeiboMsgVOs() {
		return weiboMsgVOs;
	}
}
