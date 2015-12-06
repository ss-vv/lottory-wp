package com.unison.lottery.api.redEnvalope.bo;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.activemq.store.journal.JournalTransactionStore.Tx;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.service.RedEnvalopeService;
import com.unison.lottery.api.login.hx.httpclient.apidemo.HX_sendMassage;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.SendRedEnvalopeResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.unison.lottery.api.redEnvalope.util.RedEnvalopeAlgorithm;
import com.unison.lottery.api.vGroup.data.GroupSecretkey;
import com.unison.lottery.api.vGroupPublishScheme.data.ExtData;
import com.unison.lottery.api.vGroupPublishScheme.data.Message;
import com.unison.lottery.api.vGroupPublishScheme.data.PublishSchemeClient;
import com.unison.lottery.api.vGroupPublishScheme.data.SendRedEnvalopeMessage;
import com.unison.lottery.weibo.common.nosql.RedEnvalopeRedisDao;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.UserService;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.utils.DES;

public class SendRedEnvalopeBoImpl implements SendRedEnvalopeBo{
	
	private static final String ACOUNT_NOT_ENOUGH = "1"; //余额不足状态码
	private static final String SEND_SUCCESS = "0"; //成功
	private static final String resultDescSuccess = "成功";
	private static final String resultDescAcountNotEnough = "你的现金账户余额不足，请修改红包金额或去充值";
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private IStatusRepository statusRepository;
	
	@Autowired
	private RedEnvalopeService redEnvalopeService;
	
	@Autowired
	private RedEnvalopeRedisDao redEnvalopeRedisDao;
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserService userService;
	
	@Override
	public SendRedEnvalopeResponse sendRedEnvalope(Long redEnvalopeAmount, Integer envalopeNum, User user,String groupId,String hxUserName,String greetings) {
		SendRedEnvalopeResponse sendRedEnvalopeResponse = new SendRedEnvalopeResponse();
		//设置祝福语
		sendRedEnvalopeResponse.setGreetings(greetings);
		logger.debug("发红包祝福语debug"+greetings);
		
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QUERY_GROUP_INFO_SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QUERY_GROUP_INFO_FAIL);
		sendRedEnvalopeResponse.setReturnStatus(failStatus);
		if(redEnvalopeAmount!=null&&envalopeNum!=null&&user!=null
				&&StringUtils.isNotBlank(user.getId())
				&&StringUtils.isNotBlank(groupId)){
			String uid = user.getId();
			if(uid!=null&&redEnvalopeAmount>=1&&redEnvalopeAmount<=500000&&envalopeNum>0&&envalopeNum<=100){
				
				com.xhcms.lottery.commons.data.User userVO = userService.getUser(Long.parseLong(user.getId()));
			    
				System.out.println("传进来的groupId"+groupId);
			    try {
					groupId = DES.decryptDES(groupId, GroupSecretkey.secretKey, "utf-8");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    
			    
				//生成相应个数的红包
				//每个小红包金额的上下波动范围
				long maxAmount = redEnvalopeAmount/envalopeNum*140/100;
				long minAmount = redEnvalopeAmount/envalopeNum*80/100;
				long[] redArray = RedEnvalopeAlgorithm.generate(redEnvalopeAmount, envalopeNum
						, maxAmount, minAmount);
				//冻结资金
				//获取现金账户信息
				Account account = accountService.getAccount(userVO.getId());
				BigDecimal redEnvalopeAmountBigDecimal = new BigDecimal(redEnvalopeAmount).divide(new BigDecimal(100.0),2,BigDecimal.ROUND_HALF_UP); //转换时转化为以人民币元为单位
				if(account.getFund().compareTo(redEnvalopeAmountBigDecimal)==1||account.getFund().compareTo(redEnvalopeAmountBigDecimal)==0){
					logger.info("用户{}发的红包总额为{},生成的小红包{}",userVO.getNickName(),redEnvalopeAmount,redArray);
					long envalopeId = 0;
					try{
						envalopeId = redEnvalopeService.addRedEnvalope(redEnvalopeAmount,envalopeNum,userVO.getId(),redArray);
						Map<Integer,BigDecimal> data = accountService.withRedFrozen(userVO.getId(), redEnvalopeAmountBigDecimal, BigDecimal.ZERO, envalopeId);
						//将红包放入redis
						redEnvalopeRedisDao.addEnvalopeToRedis(envalopeId,userVO.getId(),redArray);
						
						pushMessageToHX(redEnvalopeAmount, groupId,
								sendRedEnvalopeResponse, userVO, envalopeId,hxUserName,greetings);
						sendRedEnvalopeResponse.setFund(data.get(Constants.FUND).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
						sendRedEnvalopeResponse.setFree(data.get(Constants.FREE).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
						sendRedEnvalopeResponse.setFrozenFund(data.get(Constants.FROZEN_FUND).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
						sendRedEnvalopeResponse.setEnvalopeId(envalopeId);
					}catch(Exception e){
						if(e instanceof XHRuntimeException){
							XHRuntimeException runtimeException = (XHRuntimeException) e;
							if(runtimeException.getCode()==AppCode.INSUFFICIENT_BALANCE){
								if(envalopeId!=0){
									//删除发送的红包记录
									redEnvalopeService.deleteRedEnvalopeById(envalopeId);
								}
								sendRedEnvalopeResponse.setResultStatus(ACOUNT_NOT_ENOUGH);
								sendRedEnvalopeResponse.setResultDesc(resultDescAcountNotEnough);
							}
						}
					}
				}else{
					sendRedEnvalopeResponse.setResultStatus(ACOUNT_NOT_ENOUGH);
					sendRedEnvalopeResponse.setResultDesc(resultDescAcountNotEnough);
				}
			}
			sendRedEnvalopeResponse.setReturnStatus(succStatus);
		}
		return sendRedEnvalopeResponse;
	}

	private void pushMessageToHX(Long redEnvalopeAmount, String groupId,
			SendRedEnvalopeResponse sendRedEnvalopeResponse,
			com.xhcms.lottery.commons.data.User userVO, long envalopeId, String hxUserName,String greetings) {
		SendRedEnvalopeMessage publishSchemeClient = makePublishSchemeClientParams(userVO,envalopeId,hxUserName,groupId,greetings);
		String jsonStr = SendRedEnvalopeMessage.toJsonString(publishSchemeClient);
		//发到特定的群里
		HX_sendMassage hx_sendMassage = new HX_sendMassage();
		if(hx_sendMassage.sendMassage2Group(groupId, jsonStr,publishSchemeClient.target_type)){
			sendRedEnvalopeResponse.setResultStatus(SEND_SUCCESS);
			sendRedEnvalopeResponse.setResultDesc(resultDescSuccess);
			sendRedEnvalopeResponse.setEnvalopeId(envalopeId);
		}else{
			logger.error("用户id{}的红包发送环信失败，红包金额{}",userVO.getId(),redEnvalopeAmount);
		}
	}

	private SendRedEnvalopeMessage makePublishSchemeClientParams(
			com.xhcms.lottery.commons.data.User userVO, long envalopeId, String hxUserName, String groupId,String greetings) {
		SendRedEnvalopeMessage publishSchemeClient = new SendRedEnvalopeMessage();
		publishSchemeClient.target_type = "chatgroups";
		publishSchemeClient.target = new String[]{groupId};
//		publishSchemeClient.from = hxUserName;
		publishSchemeClient.msg = new Message();
		publishSchemeClient.msg.type = "txt";
		publishSchemeClient.msg.msg = "[大V彩红包] 恭喜发财，大吉大利！";
		publishSchemeClient.ext = new ExtData();
		publishSchemeClient.ext.userId = userVO.getId();
		publishSchemeClient.ext.nickName = userVO.getNickName();
		publishSchemeClient.ext.imageUrl = userVO.getHeadImageURL();
		publishSchemeClient.ext.envalopeId = String.valueOf(envalopeId);
		publishSchemeClient.ext.type = 8;
		publishSchemeClient.ext.hxUserName = hxUserName;
		publishSchemeClient.ext.greetings = greetings;
		return publishSchemeClient;
	}

	public IStatusRepository getStatusRepository() {
		return statusRepository;
	}

	public void setStatusRepository(IStatusRepository statusRepository) {
		this.statusRepository = statusRepository;
	}

	public RedEnvalopeService getRedEnvalopeService() {
		return redEnvalopeService;
	}

	public void setRedEnvalopeService(RedEnvalopeService redEnvalopeService) {
		this.redEnvalopeService = redEnvalopeService;
	}
	
	

}
