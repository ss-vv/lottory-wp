package com.unison.lottery.weibo.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unison.lottery.weibo.common.nosql.PrivateMessageDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.redis.RedisException;
import com.unison.lottery.weibo.common.service.IdGenerator;
import com.unison.lottery.weibo.common.service.NotificationService;
import com.unison.lottery.weibo.common.service.PrivateMessageService;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.PrivateMessage;
import com.unison.lottery.weibo.data.vo.PrivateMessageVO;
import com.xhcms.commons.mq.MessageSender;
import com.xhcms.lottery.commons.data.weibo.PushPrivateMsgToAllHandle;
import com.xhcms.lottery.lang.MQMessageType;

@Service
public class PrivateMessageServiceImpl implements PrivateMessageService {

	private static final Logger logger = LoggerFactory
			.getLogger(PrivateMessageServiceImpl.class);

	@Autowired
	private PrivateMessageDao privateDao;
	@Autowired
	private IdGenerator idGenerator;
	@Autowired
	private MessageSender messageSender;
	
	@Autowired
	private NotificationService notificationService;
	
	@Override
	public boolean isCanSeed(long userId, long peerId) {
		// TODO
		
		return false;
	}

	@Override
	public boolean sendPrivateMessage(PrivateMessage privateMsg) throws RedisException {
		if(validPrivateMsgFiled(privateMsg)) {
			privateMsg.setCreateTime(new Date());
			
			privateDao.savePrivateMessage(privateMsg);			//保存私信对象
			privateDao.saveSessionUserSet(privateMsg);			//保存会话用户ID
			
			privateDao.savePrivateMessageWithUser(privateMsg);	//保存和对方的私信集合
			PrivateMessage changePrivate = new PrivateMessage();
			BeanUtils.copyProperties(privateMsg, changePrivate);
			changePrivate.setOwnerId(privateMsg.getPeer());
			changePrivate.setPeer(privateMsg.getOwnerId());
			privateDao.savePrivateMessageWithUser(changePrivate);
			
			privateDao.deliverToReceiver(privateMsg.getPeer(), 	//将信件投递到接收者
					privateMsg.getId(), privateMsg.getCreateTime());
			
			logger.info("给用户ID={},发送私信成功:{}", privateMsg.getPeer(), privateMsg);
			
			deliverUnreadPrivateMsg(privateMsg.getPeer(), privateMsg.getId());
		}
		return true;
	}

	private boolean validPrivateMsgFiled(PrivateMessage privateMsg) {
		boolean result = true;
		if (null == privateMsg) {
			result = false;
			logger.info("无效私信对象，对象不能为空.");
		}
		if(privateMsg.getOwnerId() <= 0 || privateMsg.getPeer() <= 0) {
			result = false;
			logger.info("私信的发件人或者收信人ID不能为空！ownerId={}, peerId={}", 
					privateMsg.getOwnerId(), privateMsg.getPeer());
		}
		if(StringUtils.isBlank(privateMsg.getContent())) {
			result = false;
			logger.info("私信内容不能为空.");
		}
		if(StringUtils.isBlank(MQMessageType.getByValue(privateMsg.getType()))) {
			result = false;
			logger.info("私信对象类型非法，类型不包含在：{}之中。", MQMessageType.values());
		}
		return result;
	}
	
	/**
	 * 分发到私信接收者的未读时间线
	 * @param weiboReceiverId
	 * @param privateMsgId
	 */
	private void deliverUnreadPrivateMsg(long weiboReceiverId, long privateMsgId) {
		notificationService.addUnreadPrivateMsgs(weiboReceiverId, privateMsgId);
	}
	
	@Override
	public boolean replyPrivateMessage(long userId, long responseId,
			String replyCont) {
		if (StringUtils.isBlank(replyCont) || userId < 0 || responseId < 0) {
			return false;
		}
		PrivateMessage letter = new PrivateMessage();
		letter.setOwnerId(userId);
		letter.setPeer(responseId);
		letter.setContent(replyCont);
		letter.setCreateTime(new Date());

		privateDao.savePrivateMessage(letter);
		privateDao.savePrivateMessageWithUser(letter);
		privateDao.saveSessionUserSet(letter);

		PrivateMessage let = new PrivateMessage();
		BeanUtils.copyProperties(letter, let);
		privateDao.savePrivateMessage(let);

		let.setOwnerId(responseId);
		let.setPeer(userId);
		privateDao.savePrivateMessageWithUser(let);

		privateDao.saveSessionUserSet(let);

		return true;
	}

	@Override
	public PageResult<PrivateMessage> showSelfPrivateMessageList(long userId,
			PageRequest pageRequest) {
		if (null == pageRequest) {
			pageRequest = new PageRequest();
		}

		// TODO 提出公共返回方法
		int startIndex = pageRequest.getOffset();
		int endIndex = pageRequest.getPageIndex() * pageRequest.getPageSize() - 1;

		Set<String> peerIdSet = privateDao.findSelfPrivateMessageUserId(userId,
				startIndex, endIndex);
		List<PrivateMessage> lastestSessionList = new ArrayList<PrivateMessage>();
		if (null != peerIdSet) {
			Iterator<String> iter = peerIdSet.iterator();
			while (iter.hasNext()) {
				String peerId = iter.next();
				Set<String> msgIdSet = privateDao.findPrivateMessageMsgId(
						userId, Long.parseLong(peerId), 0, 1);
				if (null != msgIdSet && msgIdSet.size() > 0) {
					String privateId = msgIdSet.toArray()[0].toString();
					PrivateMessage letter = privateDao
							.findLastestSessionInfoWithUser(privateId);
					if (null != letter) {
						lastestSessionList.add(letter);
					}
				}
			}
		}
		PageResult<PrivateMessage> result = new PageResult<PrivateMessage>();
		result.setResults(lastestSessionList);
		return result;
	}

	@Override
	public PageResult<PrivateMessage> showSessionWithUser(long userId, long peerId,
			PageRequest pageRequest) {
		if (null == pageRequest) {
			pageRequest = new PageRequest();
		}
		
		int startIndex = pageRequest.getOffset();
		int endIndex = pageRequest.getPageIndex() * pageRequest.getPageSize() - 1;

		Set<String> msgIdSet = privateDao.findPrivateMessageMsgId(userId,
				peerId, startIndex, endIndex);
		List<PrivateMessage> list = privateDao.findSessionInfoWithUser(msgIdSet,
				startIndex, endIndex);
		PageResult<PrivateMessage> result = new PageResult<PrivateMessage>();
		result.setResults(list);
		return result;
	}

	@Override
	public int deleteAllSession(long userId, long peerId) {
		int ret = privateDao.deleteSession(userId, peerId);
		return ret;
	}

	@Override
	public boolean deleteById(long userId, long peerId, long privateLetterId) {
		Long rs = privateDao.deleteById(userId, peerId, privateLetterId);
		return rs.intValue() > 0 ? true : false;
	}

	@Override
	public long calPrivateCountWithUser(long userId, long peerId) {
		return privateDao.sessionCountWithUserId(userId, peerId);
	}

	public void setPrivateDao(PrivateMessageDao privateDao) {
		this.privateDao = privateDao;
	}

	@Override
	public void pushPrivateMsgToAllUsers(String privateMsgId) {
		try {
			Long pMid = Long.parseLong(privateMsgId);
			PrivateMessage privateMsg = privateDao.findPrivateMessageById(pMid);
			if(null != privateMsg && privateMsg.getType() == MQMessageType.SYSTEM_MESSAGE.getType()){
				logger.info("starting push PrivateMsg privateMsgId={} to all users...",privateMsgId);
				doPushPrivateMsg(privateMsg);
				logger.info("push PrivateMsg privateMsgId={} to all users finish!",privateMsgId);
			} else {
				logger.info("do push PrivateMsg letter error,illegal argument :" +
						" privateMsgId={} ,type={} ",privateMsgId,null == privateMsg?"null":privateMsg.getType());
			}
		} catch (NumberFormatException e) {
			logger.info(" privateMsgId={}  error !!!",privateMsgId,e);
		}
	}
	private void doPushPrivateMsg(PrivateMessage privateMsg){
		LinkedHashSet<String> weiboUserIds = privateDao.zrange(Keys.WEIBO_USERS, 0, -1);
		for (String weiboUserId : weiboUserIds) {
			sendPrivateMsg(privateMsg,weiboUserId);
		}
		logger.info("PrivateMessage id={} push success! total reveicer count={}",privateMsg.getId(),weiboUserIds.size());
	}
	
	@Override
	public void sendPrivateMsg(PrivateMessage privateMsg,String recUserId){
		long score = privateMsg.getCreateTime().getTime();
		long ownerIdL = privateMsg.getOwnerId(); 
		long recUserIdL  = Long.parseLong(recUserId);
		privateDao.zadd(score,Keys.privateSessionUserId(recUserIdL),ownerIdL+"");
		privateDao.zadd(score,Keys.privateSessionUserId(ownerIdL),recUserId);
		privateDao.zadd(score,Keys.privatePeerMsgs(ownerIdL, recUserIdL),privateMsg.getId()+"");
		privateDao.zadd(score,Keys.privateMsgReceived(recUserIdL),privateMsg.getId()+"");
		logger.info("send PrivateMessage id={} to recUserId={}",privateMsg.getId(),recUserId);
		
		deliverUnreadPrivateMsg(recUserIdL, privateMsg.getId());
	}

	@Override
	public long sendSystemPrivateMsg(PrivateMessage privateMsg,
			List<String> receivers) {
		if(null == privateMsg){
			return -1;
		}
		long id = idGenerator.nextId();
		privateMsg.setId(id);
		privateMsg.setCreateTime(new Date());
		privateMsg.setType(MQMessageType.SYSTEM_MESSAGE.getType());
		privateDao.hashAdd(Keys.privateMsg(id), privateMsg);
		privateDao.zadd(privateMsg.getCreateTime().getTime(),Keys.privateMsgPosted(privateMsg.getOwnerId()),id+"");
		if(null != receivers && receivers.size() > 0){ //发给指定用户
			for (String recUserId : receivers) {
				sendPrivateMsg(privateMsg, recUserId);
			}
		} else {//发给全部用户
			messageSender.send(new PushPrivateMsgToAllHandle(id + ""));
		}
		return id;
	}

	@Override
	public PageResult<PrivateMessage> showMyPostedPrivateMsg(String ownerId,
			PageRequest pageRequest) {
		if (null == pageRequest) {
			pageRequest = new PageRequest();
		}
		int startIndex = pageRequest.getOffset();
		int endIndex = pageRequest.getPageIndex() * pageRequest.getPageSize() - 1;
		PageResult<PrivateMessage> pageResult = new PageResult<PrivateMessage>();
		List<PrivateMessage> myPostedPrivateMsg = new ArrayList<PrivateMessage>();
		pageRequest.setRecordCount((privateDao.zcard(Keys.privateMsgPosted(Long.parseLong(ownerId)))).intValue());
		LinkedHashSet<String> postedIds = privateDao.zrevrange(Keys.privateMsgPosted(Long.parseLong(ownerId)), startIndex, endIndex);
		for (String postedId : postedIds) {
			myPostedPrivateMsg.add(privateDao.findPrivateMessageById(Long.parseLong(postedId)));
		}
		pageResult.setPageRequest(pageRequest);
		pageResult.setResults(myPostedPrivateMsg);
		pageResult.setSuccess(true);
		return pageResult;
	}

	@Override
	public PageResult<PrivateMessageVO> showMyReceivedPrivateMsg(
			String weiboUserId, PageRequest pageRequest) {
		if (null == pageRequest) {
			pageRequest = new PageRequest();
		}
		int startIndex = pageRequest.getOffset();
		int endIndex = pageRequest.getPageIndex() * pageRequest.getPageSize() - 1;
		PageResult<PrivateMessageVO> pageResult = new PageResult<PrivateMessageVO>();
		List<PrivateMessageVO> myReceivedPrivateMsg = new ArrayList<PrivateMessageVO>();
		String key = Keys.privateMsgReceived(Long.parseLong(weiboUserId));
		pageRequest.setRecordCount((privateDao.zcard(key).intValue()));
		LinkedHashSet<String> receivedIds = privateDao.zrevrange((key), startIndex, endIndex);
		for (String receivedId : receivedIds) {
			PrivateMessage p = privateDao.findPrivateMessageById(Long.parseLong(receivedId));
			PrivateMessageVO pVO = new PrivateMessageVO();
			BeanUtils.copyProperties(p, pVO);
			myReceivedPrivateMsg.add(pVO);
		}
		pageResult.setPageRequest(pageRequest);
		pageResult.setResults(myReceivedPrivateMsg);
		pageResult.setSuccess(true);
		return pageResult;
	}
}
