package com.unison.lottery.api.buyHistory.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.BuyHistoryResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.persist.service.AccountQueryService;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.lang.Constants;

/**
 * 购彩记录
 * @author Wang Lei
 *
 */
public class BuyHistoryBOImpl implements BuyHistoryBO{
	@Autowired
	private IStatusRepository statusRepository;
	@Autowired
	private AccountQueryService accountQueryService;
	@Autowired
    private AccountService accountService;
	
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	@Transactional(readOnly=true)
	public BuyHistoryResponse buyHistory(Map<String, String> paramMap,String userId) {
		BuyHistoryResponse buyHistoryResponse = new BuyHistoryResponse();
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.BuyHistory.SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.BuyHistory.FAIL);
		buyHistoryResponse.setReturnStatus(failStatus);
		
		if(StringUtils.isBlank(userId)){
			logger.info("用户必须登录！");
			return buyHistoryResponse;
		}
		String type = paramMap.get(com.unison.lottery.api.protocol.common.Constants.BET_TYPE);
		if(StringUtils.isBlank(type)){
			logger.info("方案类型不能为空");
			return buyHistoryResponse;
		}
		int firstResult = Integer.parseInt(paramMap.get(com.unison.lottery.api.protocol.common.Constants.FIRST_RESULT));
		int schemeType = Integer.parseInt(type);
		int showScheme = Constants.NOT_SHOW_SCHEME;
		
		Calendar calendar = Calendar.getInstance();
		Date endDate = new Date();
		calendar.setTime(endDate);
		calendar.add(Calendar.MONTH, -2); // 查询当前时间到2个月以前的数据
		Date startDate = calendar.getTime();
				
		try {
			switch (schemeType) {
			case Constants.TYPE_ALL: // 所有
				showScheme = Constants.SHOW_ALL;
				break;
			case Constants.TYPE_BUY: // 代购
				showScheme = Constants.NOT_SHOW_SCHEME;
				break;
			case Constants.TYPE_FOLLOW: // 跟单
				showScheme = Constants.NOT_SHOW_SCHEME;
				break;
			case Constants.TYPE_GROUP: // 合买
				showScheme = Constants.SHOW_ALL;
				break;
			default:
				logger.info("方案类型错误！:{}",schemeType);
				return buyHistoryResponse;
			}
			
			buyHistoryResponse.setResults(accountQueryService.listBetHistory(null,Long.parseLong(userId), 
					startDate, endDate, -1,  firstResult,com.unison.lottery.api.protocol.common.Constants.PAGING_MAX_RESULT,schemeType,showScheme));
			//查询账户信息
			Account account = accountService.getAccount(Long.parseLong(userId));
			
			buyHistoryResponse.setReturnStatus(succStatus);
			buyHistoryResponse.setAccount(account);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询购彩记录时出现异常:{}",e.getMessage());
			buyHistoryResponse.setReturnStatus(failStatus);
		}
		
		return buyHistoryResponse;
	}

}
