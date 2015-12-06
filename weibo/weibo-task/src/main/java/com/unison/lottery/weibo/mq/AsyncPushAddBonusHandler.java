package com.unison.lottery.weibo.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.thrift.client.bet.BetSchemeClient;
import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.data.weibo.WinAddBonus;

/**
 *	异步推送“中奖加奖”微博
 * @author lei.li@davcai.com
 */
public class AsyncPushAddBonusHandler implements MessageHandler<WinAddBonus> {

	private static final long serialVersionUID = -3305196252848895594L;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageService messageService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private BetSchemeClient betSchemeClient;
	
	private static final long jczqUserId = 577735160761344L;	//竞彩足球彩种用户
	private static final String tpl = "恭喜 @%s 成功领取加奖奖金，%s元";
	
	@Override
	public void handle(WinAddBonus winAddBonus) {
		logger.error("收到“中奖加奖”发送微博通知:{}", winAddBonus);
		if (winAddBonus == null) {
			return;
		}
		long schemeId = winAddBonus.getSchemeId();
		long userId = winAddBonus.getUserId();
		
		WeiboMsg weiboMsg = new WeiboMsg();
		weiboMsg .setSchameId(schemeId);
		long postId = messageService.getWeiboIdByShowSchemeId(schemeId);
		if(postId > 0) {
			WeiboUser weiboUser = userAccountService.findWeiboUserByLotteryUid(userId+"");
			weiboMsg.setOwnerId(jczqUserId);
			weiboMsg.setPostId(postId);
			weiboMsg.setContent(String.format(tpl, weiboUser.getNickName(), winAddBonus.getBonus()));
			WeiboMsgVO vo = messageService.forward(weiboMsg);
			if(null != vo) {
				messageService.addShowSchemeToWeiboTimeline(schemeId, vo.getId());
				betSchemeClient.updateBetSchemePublishShow(schemeId);
			}
		} else {
			logger.error("转发方案ID={} 的微博，找不到微博信息.", schemeId);
		}
	}
}