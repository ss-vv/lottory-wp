package com.unison.lottery.weibo.statistic.task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.statistic.service.WeiboStatisticService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.uc.service.WeixinService;
import com.xhcms.commons.job.Job;
import com.xhcms.lottery.commons.persist.entity.UserPO;

/**
 * 
 * @author haoxiang.jiang
 */
public class StatisticTask extends Job{
	private static final Logger logger = LoggerFactory
			.getLogger(StatisticTask.class);
	@Autowired
	WeiboStatisticService weiboStatisticService;
	@Autowired
	UserAccountService userAccountService;
	@Autowired
	WeixinService weixinService;
	public StatisticTask() {
		logger.info("execute task : static weibo data...");
	}
	
	@Override
	protected void execute() {
//		for (int i = 1; i <= 1; i++) {
//			weiboStatisticService.setFrom(getFrom(i));
//			weiboStatisticService.setTo(getTo(i));
//			weiboStatisticService.setPageRequest(getPageRequest(1, 100));
//			weiboStatisticService.statistic();
//		}
		staticWeixinUidUnique();
	}
	
	/**
	 * 统计微信uid是否是唯一用户
	 */
	@Transactional
	private void staticWeixinUidUnique(){
		List<UserPO> list = weiboStatisticService.findWeixinUidIsNotNull();
		
		for (UserPO userPO : list) {
			String openId = userPO.getWeixinUid();
			if(Constant.WeiboChannel.equals(userPO.getPid())){//davcai web注册的用户
				if(StringUtils.isNotBlank(openId)){
					userPO.setWeixinPCUid(openId);
					String uid = userAccountService.findWeiboUserByLotteryUid(userPO.getId()+"").getWeiboUserId()+"";
					WeiboUser weiboUser = userAccountService.findWeiboUserByWeiboUid(uid);
					weiboUser.setWeixinPCUid(userPO.getWeixinPCUid());
					if(weixinService.isTokenValid(weiboUser)){
						userPO.setWeixinToken(weiboUser.getWeixinToken());
						JSONObject jsonObject = weixinService.getWeixinUser(weiboUser);
						try {
							String unionid = jsonObject.getString("unionid");
							userPO.setWeixinUnionId(unionid);
							userPO.setWeixinUid(unionid);
						} catch (JSONException e) {
							logger.error("",e);
							continue;
						}
						userAccountService.updateOpenInfo("weixin", userPO);
						logger.info("更新PC用户微信数据：set userid={},WeixinPCUid={},WeixinUnionId={},WeixinUid={}",
								new Object[]{userPO.getId(),userPO.getWeixinPCUid(),userPO.getWeixinUnionId(),userPO.getWeixinUid()});
					} else {
						logger.info("无效的token： userid={},WeixinUid={},weixinToken={}",
								new Object[]{userPO.getId(),userPO.getWeixinUid(),userPO.getWeixinToken()});
						userAccountService.updateOpenInfo("weixin", userPO);
						logger.info("更新PC用户微信数据：set userid={},WeixinPCUid={}",
								new Object[]{userPO.getId(),userPO.getWeixinPCUid()});
					}
				}
			} else {
				if(StringUtils.isNotBlank(openId)){//客户端微信注册
					userPO.setWeixinUnionId(openId);
					userAccountService.updateOpenInfo("weixin", userPO);
					logger.info("更新Mobile用户微信数据：set userid={},WeixinUnionId={}",
							new Object[]{userPO.getId(),userPO.getWeixinUnionId()});
				}
			}
		}
	}
	
	private long getFrom(int i){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE,-i); //前i天
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime().getTime();
	}
	private long getTo(int i){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE,- i); //前i天
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime().getTime();
	}
	private PageRequest getPageRequest(int page,int size){
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPageIndex(page);
		pageRequest.setPageSize(size);
		return pageRequest;
	}
}
