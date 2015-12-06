package com.unison.lottery.weibo.common.service;

import java.util.List;
import com.unison.lottery.weibo.data.MatchIdInfo;
import com.unison.lottery.weibo.data.WeiboMsg;

/**
 * @desc 微博内容中赛事串的解析服务
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-25
 * @version 1.0
 */
public interface WeiboMatchIdService {
	
	/**
	 * 反解并校验赛事串集合;赛事串格式详见文档
	 * @param weiboMsg
	 * @return	合法并在数据库中存在的球探比赛ID集合
	 */
	List<MatchIdInfo> reversionAndCheckMatchId(WeiboMsg weiboMsg);
	
	/**
	 * 解析微博对象中出现的赛事串集合
	 * @param weiboMsg
	 * @return
	 */
	List<String> analyzeMatchIdBaseWeiboMsg(WeiboMsg weiboMsg);
}