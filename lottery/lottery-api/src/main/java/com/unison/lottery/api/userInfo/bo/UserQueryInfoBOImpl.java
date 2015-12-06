package com.unison.lottery.api.userInfo.bo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.QueryUserInfoResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.HX_user;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.HX_userService;
import com.xhcms.ucenter.persistent.service.IUserService;
import com.xhcms.ucenter.persistent.service.IUserValidIdManager;

public class UserQueryInfoBOImpl implements UserQueryInfoBO {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IStatusRepository statusRepository;
	@Autowired
	private IUserService userService;
	@Autowired
	private IUserValidIdManager userValidIdManager;
	@Autowired
    private AccountService accountService;
	@Autowired
	private HX_userService hxUserService;
	
	@Override
	public QueryUserInfoResponse query(String validId) {
		ReturnStatus succStatus = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QUERY_GROUP_INFO_SUCC);
		ReturnStatus failStatus = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QUERY_GROUP_INFO_FAIL);
		
		User user = null;
		long userId = 0L;
		QueryUserInfoResponse resp = new QueryUserInfoResponse();
		resp.setReturnStatus(succStatus);
		resp.setValidId(validId);
		try {
			logger.info("\n查询用户信息:validId={}", validId);
			String userIdStr = userValidIdManager.findUserIdByValidIdAndCurrentTime(validId);
			logger.info("\n根据validId查询用户ID={}", userIdStr);
			if(StringUtils.isNotBlank(userIdStr)) {
				userId = Long.valueOf(userIdStr);
				user = userService.getUser(userId);
			}
			logger.info("\n根据validId查询用户信息={}", user);
			
			if(null != user && user.getId() > 0) {
				resp.setUser(user);
				
				//查询账户信息
				Account account = accountService.getAccount(userId);
				resp.setAccount(account);
				logger.info("\n查询用户账户,参数:validId={},得到userId={},account={}", 
						new Object[]{validId, userId, account});
				
				//查询环信用户信息
				HX_user hxUser = hxUserService.getHX_user(userId);
				if(null != hxUser && StringUtils.isNotBlank(hxUser.getUserId())) {
					resp.setHxUsername(hxUser.getUsername());
					resp.setHxPassword(hxUser.getPassword());
				}
			} else {
				resp.setReturnStatus(failStatus);
			}
		} catch (Exception e) {
			resp.setReturnStatus(failStatus);
			e.printStackTrace();
		}
		return resp;
	}
}