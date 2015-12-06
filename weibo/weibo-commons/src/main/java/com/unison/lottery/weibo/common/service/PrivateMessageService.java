package com.unison.lottery.weibo.common.service;

import java.util.List;

import com.unison.lottery.weibo.data.PrivateMessage;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.vo.PrivateMessageVO;

/**
 * @desc 私信的接口服务
 * @author lei.li@unison.net.cn
 * @createTime 2013-10-24
 * @version 1.0
 */
public interface PrivateMessageService {

	/**
	 * 判断是否能给某人发私信；条件：1.必须是互粉
	 * 
	 * @param userId
	 *            发信人
	 * @param peerId
	 *            收信人
	 * @return 可以返回true，否则false
	 */
	public boolean isCanSeed(long userId, long peerId);

	/**
	 * 私信某人
	 * 
	 * @param letter
	 *            私信对象
	 * @return 成功true，否则false
	 */
	public boolean sendPrivateMessage(PrivateMessage letter);

	/**
	 * 回复某人的私信
	 * 
	 * @param userId
	 *            回复人ID
	 * @param responseId
	 *            被回复人ID
	 * @param privateLetterId
	 *            要回复的私信ID
	 * @param replyCont
	 *            回复内容
	 * @return
	 */
	public boolean replyPrivateMessage(long userId, long responseId,
			String replyCont);

	/**
	 * 查看个人私信列表，默认给出的是和每个人最后一次私信对话信息
	 * 
	 * @param userId
	 *            用户ID
	 */
	public PageResult<PrivateMessage> showSelfPrivateMessageList(long userId,
			PageRequest pageRequest);

	/**
	 * 查看和某个人的私信会话内容
	 * 
	 * @param userId
	 * @param peerId
	 */
	public PageResult<PrivateMessage> showSessionWithUser(long userId, long peerId,
			PageRequest pageRequest);

	/**
	 * 删除和某个人的全部会话
	 * 
	 * @param userId
	 * @param peerId
	 * @return
	 */
	public int deleteAllSession(long userId, long peerId);

	/**
	 * 根据私信ID，删除会话中某一条私信
	 * 
	 * @param privateLetterId
	 * @return
	 */
	public boolean deleteById(long userId, long peerId, long privateLetterId);

	/**
	 * 计算和某个人私信对话的条数
	 * 
	 * @param userId
	 * @param peerId
	 * @return
	 */
	public long calPrivateCountWithUser(long userId, long peerId);
	
	public void pushPrivateMsgToAllUsers(String privateMsgId);

	public void sendPrivateMsg(PrivateMessage privateMsg, String recUserId);
	
	long sendSystemPrivateMsg(PrivateMessage privateMsg,List<String> receivers);

	public PageResult<PrivateMessage> showMyPostedPrivateMsg(String ownerId, PageRequest pageRequest);

	public PageResult<PrivateMessageVO> showMyReceivedPrivateMsg(String weiboUserId, PageRequest pageRequest);
}
