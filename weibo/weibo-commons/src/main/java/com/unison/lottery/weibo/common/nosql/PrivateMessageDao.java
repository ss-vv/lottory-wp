package com.unison.lottery.weibo.common.nosql;

import java.util.Date;
import java.util.List;
import java.util.Set;
import com.unison.lottery.weibo.common.redis.RedisException;
import com.unison.lottery.weibo.data.PrivateMessage;

/**
 * 私信dao
 * 
 * @author lei.li
 * 
 */
public interface PrivateMessageDao  extends BaseDao<PrivateMessage> {

	/**
	 * 保存私信内容
	 * 
	 * @param letter
	 * @return 返回私信ID
	 * @throws RedisException
	 */
	long savePrivateMessage(PrivateMessage letter);

	/**
	 * 保存和指定用户用户私信ID集合
	 * @param letter 需要指定私信ID，私信发送人，私信接收人，私信创建时间
	 */
	long savePrivateMessageWithUser(PrivateMessage letter);

	/**
	 * 保存私信会话的用户集合
	 * 
	 * @param letter
	 *            需要指定私信ID，私信发送人，私信接收人
	 */
	long saveSessionUserSet(PrivateMessage letter);
	
	/**
	 * 
	 * @param peerId 私信接收人ID
	 * @param privateMsgId	私信ID
	 * @param createTime	被投递信件的创建时间
	 * @return
	 */
	long deliverToReceiver(long peerId, long privateMsgId, Date createTime);

	/**
	 * 查找用户的私信用户ID集合
	 * 
	 * @param userId
	 */
	Set<String> findSelfPrivateMessageUserId(long userId, int startIndex,
			int endIndex);

	/**
	 * 查找和指定用户ID的私信对话ID集合
	 * 
	 * @param userId
	 * @param peer
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	Set<String> findPrivateMessageMsgId(long userId, long peer, int startIndex,
			int endIndex);

	/**
	 * 查看指定用户的私信用户个数
	 * 
	 * @param userId
	 * @return
	 */
	long countPrivateMessageUser(long userId);

	/**
	 * 查看和某个人的私信会话信息
	 * 
	 * @param msgIdSet
	 *            私信回话消息ID
	 * @param startIndex
	 *            开始索引
	 * @param endIndex
	 *            结束索引
	 * @return
	 */
	List<PrivateMessage> findSessionInfoWithUser(Set<String> msgIdSet, int startIndex,
			int endIndex);

	/**
	 * 查看和某个人最近一次的私信会话信息
	 * 
	 * @param msgId
	 *            对话消息ID
	 * @return
	 */
	PrivateMessage findLastestSessionInfoWithUser(String msgId);

	/**
	 * 查看和某个人会话条数
	 * 
	 * @param userId
	 * @param peerId
	 * @return
	 */
	long sessionCountWithUserId(long userId, long peerId);

	/**
	 * 删除和某个人的指定会话私信
	 * 
	 * @param userId
	 * @param peerId
	 * @param privateLetterId
	 *            私信ID
	 * @return
	 */
	Long deleteById(final long userId, final long peerId,
			final long privateLetterId);

	/**
	 * 删除和某个人的全部私信内容
	 * 
	 * @param userId
	 * @param peerId
	 * @return
	 */
	int deleteSession(long userId, long peerId);
	
	PrivateMessage findPrivateMessageById(final long msgId);
}
