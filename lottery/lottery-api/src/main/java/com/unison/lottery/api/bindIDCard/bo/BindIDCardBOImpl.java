package com.unison.lottery.api.bindIDCard.bo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.BindIDCardResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.ucenter.persistent.service.IUserManager;
import com.xhcms.ucenter.persistent.service.IUserService;

public class BindIDCardBOImpl implements BindIDCardBO {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private IStatusRepository statusRepository;

	private Logger logger=LoggerFactory.getLogger(getClass());
	@Override
	public BindIDCardResponse bindIDCardForUser(String iDCard, User user) {
		BindIDCardResponse bindIDCardResponse=new BindIDCardResponse();
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.BindIDCard.SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.BindIDCard.FAIL);
		bindIDCardResponse.setReturnStatus(failStatus);
		try{
			if(null!=user&&StringUtils.isNotBlank(iDCard)){
				if(userService.idNumberBindToUser(iDCard)){
					ReturnStatus idNumberIsBindToUserStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.BindIDCard.ID_CARD_HAVE_BINDED);
					bindIDCardResponse.setReturnStatus(idNumberIsBindToUserStatus);
				}
				else{
					com.xhcms.lottery.commons.data.User user4Update=userService.getUser(Long.parseLong(user.getId()));
					if(StringUtils.isBlank(user4Update.getIdNumber())){					
						user4Update.setIdNumber(iDCard);
						userManager.updateIDNumber(user4Update);
						bindIDCardResponse.setReturnStatus(succStatus);
					}
				}
				
				
			}
			
			
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error("绑定身份证时出现异常:{}",e.getMessage());
			bindIDCardResponse.setReturnStatus(failStatus);
		}
		return bindIDCardResponse;
	}

}
