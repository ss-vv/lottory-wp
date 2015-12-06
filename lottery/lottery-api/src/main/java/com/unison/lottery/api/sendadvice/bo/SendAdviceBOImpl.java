package com.unison.lottery.api.sendadvice.bo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.api.login.service.ILoginService;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.SendAdviceResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.commons.persist.dao.AdviceDao;
import com.xhcms.lottery.commons.persist.entity.AdvicePO;

public class SendAdviceBOImpl implements SendAdviceBO {

	@Autowired
	private IStatusRepository statusRepository;

	private Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	private ILoginService loginService;

	@Autowired
	private AdviceDao adviceDao;
	
	@Override
	@Transactional
	public SendAdviceResponse sendAdvice(User user, String advice) {
		SendAdviceResponse response=new SendAdviceResponse();
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.SendAdvice.SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.SendAdvice.FAIL);
		response.setReturnStatus(failStatus);
		try{
			AdvicePO advicePO=makeAdvicePO(user,advice);
			if(null!=advicePO){
				adviceDao.save(advicePO);
			}
			response.setReturnStatus(succStatus);
		}
		catch(Exception e){
			e.printStackTrace();
			response.setReturnStatus(failStatus);
		}
		return response;
	}

	private AdvicePO makeAdvicePO(User user, String advice) {
		AdvicePO result=null;
		if(null!=user&&StringUtils.isNotBlank(advice)){
			result=new AdvicePO();
			result.setAdvice(advice);
			result.setChannel(user.getChannel());
			result.setClientVersion(user.getClientVersion());
			result.setCreateTime(new Date());
			result.setDeviceId(user.getDeviceId());
			result.setModelName(user.getModelName());
			if(StringUtils.isNotBlank(user.getValidId())){
				User userAfterLogin=loginService.tryGetAsAuthenticate(user);
				if(null!=userAfterLogin){
					result.setUserId(userAfterLogin.getId());
				}
			}
		}
		return result;
	}

}
