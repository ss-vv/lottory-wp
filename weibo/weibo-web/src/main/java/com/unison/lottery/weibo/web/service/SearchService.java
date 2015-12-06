package com.unison.lottery.weibo.web.service;

import java.util.List;

import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.data.vo.PromptMatchVO;

/**
 * @Description:搜索服务 
 * @author 江浩翔   
 * @date 2013-12-12 上午11:00:04 
 * @version V1.0
 */
public interface SearchService {
	List<WeiboUser> querryUser(String keyString,PageRequest pageRequest);
	List<WeiboMsgVO> querryWeiboMsg(String keyString,PageRequest pageRequest,
			String weiboType);
	/**
	 * 搜索赛事(所有彩种)
	 * @param keyString
	 * @param pageRequest
	 * @return
	 */
	List<PromptMatchVO> querryPromptMatch(String keyString,PageRequest pageRequest);
	boolean indexUser(WeiboUser weiboUser);
	boolean indexWeiboContent(WeiboMsg weiboMsg);
}
