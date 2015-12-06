package com.unison.lottery.api.userdetail.bo;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.UserDetailResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.Recharge;
import com.xhcms.lottery.commons.data.Win;
import com.xhcms.lottery.commons.data.Withdraw;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.service.UserDetailQueryService;

public class UserDetailBOImpl implements UserDetailBO {

	private static final String WIN = "2";

	private static final String RECHARGE = "0";

	private static final String WITHDRAW = "1";

	@Autowired
	private IStatusRepository statusRepository;
	
	@Autowired
	private UserDetailQueryService userDetailQueryService;

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
    private AccountService accountService;
	
	
	@Override
	@Transactional
	public UserDetailResponse showUserDetail(User user, String type,
			int firstResult) {
		UserDetailResponse response=new UserDetailResponse();
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.UserDetail.SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.UserDetail.FAIL);
		response.setReturnStatus(failStatus);
		try{
			if(null!=user
					&&StringUtils.isNotBlank(type)
					&&firstResult>=0){
				if(type.equals(RECHARGE)){
					handleShowRechargeDetail(user,firstResult,response);
				}
				else if(type.equals(WITHDRAW)){
					handleShowWithdrawDetail(user,firstResult,response);
				}
				else if(type.equals(WIN)){
					handleShowWinDetail(user,firstResult,response);
				}
				Account account=accountService.getAccount(Long.parseLong(user.getId()));
				response.setAccount(account);
				response.setReturnStatus(succStatus);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取用户详情时出现异常:{}",e.getMessage());
			response.setReturnStatus(failStatus);
		}
		return response;
	}
	private void handleShowWinDetail(User user, int firstResult,
			UserDetailResponse response) {
		List<Win> winList = userDetailQueryService.listWin(Long.parseLong(user.getId()), firstResult);
		response.setWinList(winList);
	}
	private void handleShowWithdrawDetail(User user, int firstResult,
			UserDetailResponse response) {
		List<Withdraw> withdrawList=userDetailQueryService.listWithdraw(Long.parseLong(user.getId()), firstResult);
		response.setWithdrawList(withdrawList);
	}
	private void handleShowRechargeDetail(User user, int firstResult,
			UserDetailResponse response) {
		List<Recharge> rechargeList = userDetailQueryService.listRecharge(Long.parseLong(user.getId()), firstResult);
		response.setRechargeList(rechargeList);
	}

}
