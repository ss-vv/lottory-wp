package com.unison.lottery.weibo.msg.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.common.nosql.AtMeDao;
import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.nosql.MessageDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.redis.RedisException;
import com.unison.lottery.weibo.common.service.NotificationService;
import com.unison.lottery.weibo.common.service.impl.BaseServiceImpl;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.msg.service.AtUserService;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.uc.service.UserAccountService;

/**
 * 发布微博时@用户服务
 * @author Wang Lei
 *
 */
@Service
public class AtUserServiceImpl extends BaseServiceImpl implements AtUserService {

	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private AtMeDao atMeDao;
	
	@Autowired
	private NotificationService notificationService;
	
	@SuppressWarnings("unchecked")
	@Override
	public PageResult<WeiboMsgVO> findAtMeList(final long uid, PageRequest pageRequest){
		PageResult<WeiboMsgVO> pageResult = new PageResult<>();
		try {
			PageResult<String> result = (PageResult<String>) descListSortedSetByPageRequest(Keys.atMeKey(""+uid), pageRequest);
			List<String> postids = result.getResults() == null ? new ArrayList<String>() : result.getResults();
			String[] keys = new String[postids.size()];
			for(int i = 0 ; i < postids.size() ; i++){
				keys[i] = Keys.postKey(postids.get(i));
			}
			pageResult = messageService.listPost(postids.toArray(new String[postids.size()]), pageRequest);
		} catch (RedisException e) {
			log.error("list RedisError ! uid =  {}", uid, e);
		} catch (Exception e) {
			log.error("list Error ! uid = {} ", uid, e);
		}
		return messageService.checkFavoriateAndLike(uid, pageResult);
	}
	
	@Override
	public long atUserByPost(WeiboMsg weiboMsg){
		long count =0;
		if(weiboMsg == null){
			log.error("error ! post is null!");
			return count;
		}
		try {
			String content = weiboMsg.getContent();
			// 计算@用户数
			Set<String> users = findAtUsers(content);
			WeiboUser weiboUser = userAccountService.findWeiboUserByWeiboUid(weiboMsg.getOwnerId()+"");
	        String userNickName = weiboUser == null ? null : weiboUser.getNickName();
	        if(users != null && StringUtils.isNotBlank(userNickName)){
	        	users.remove(userNickName);
	        }
			//@用户
			count = at(users, weiboMsg.getId());
			
			//加入到对方未读“提到我的”时间线上去
			if(null != users) {
				String[] uids = userAccountService.findWeiboUserIdsByNickNames(users);
				notificationService.addUnreadMetions(uids, weiboMsg.getId());
			}
		} catch (Exception e) {
			log.error("atUserByPostId Error ! pid = {}", weiboMsg.getId(), e);
		}
		return count;
	}

	@Override
	public long atUserByPostId(long pid) {
		if(pid < 1){
			log.error("error ! postId is wrong ! pid = {}", pid);
			return 0;
		}
		return atUserByPost(messageDao.get(""+pid));
	}

	private long at(Set<String> users, final long pid) {
		long count = 0;
		try {
			if (null != users && !users.isEmpty()) {
				log.info("@users = {}", users.toString());
				// 取得用户id集合
				String[] uids = userAccountService.findWeiboUserIdsByNickNames(users);
				// @用户
				count = atMeDao.atUserByPostId(uids, ""+pid);
			}
		} catch (Exception e) {
			log.error("atUserByPId Error ! pid = {}", pid, e);
		}
		return count;
	}

	@Override
	public Set<String> findAtUsers(String content) {
		if(StringUtils.isNotBlank(content) && content.indexOf("@") == -1){
			return null;
		}
		content = content.replaceAll("&nbsp;", " ");
		content = content.substring(content.indexOf("@"),content.length());
		String[] users = content.split("@");
		Set<String> result = new HashSet<String>();
		for (String user : users) {
			String userName = null;
			if (StringUtils.isBlank(user)) {
				continue;
			}	
			if(user.indexOf(" ") == -1){
				userName = user;
			}else{
				userName = user.substring(0, user.indexOf(" "));
			}
			if(userName.length() < 17){
				result.add(userName);
			}
		}
		return result;
	}

	@Override
	protected BaseDao<?> getBaseDao() {
		return atMeDao;
	}

}
