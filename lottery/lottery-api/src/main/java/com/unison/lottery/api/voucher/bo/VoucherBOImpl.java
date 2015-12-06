package com.unison.lottery.api.voucher.bo;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.QueryVouchersResponse;
import com.unison.lottery.api.protocol.response.model.UseVoucherResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.entity.VoucherPO;
import com.xhcms.lottery.commons.persist.entity.VoucherUserExtendPO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.VoucherUserService;
import com.xhcms.lottery.lang.VoucherLimit;
import com.xhcms.lottery.lang.VoucherType;
import com.xhcms.lottery.lang.VoucherUserViewStatus;

/**
 * 查询可用优惠劵
 * @author Wang Lei
 *
 */
public class VoucherBOImpl implements VoucherBO {
	private Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private VoucherUserService voucherUserService;
	@Autowired
	private IStatusRepository statusRepository;
	@Autowired
    private AccountService accountService;
	
	/**
	 * 查询可用优惠劵
	 */
	@Override
	@Transactional(readOnly=true)
	public QueryVouchersResponse queryVouchers(Map<String, Object> paramMap,
			String userId) {
		QueryVouchersResponse queryVouchersResponse = new QueryVouchersResponse();
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QueryVoucers.SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QueryVoucers.FAIL);
		queryVouchersResponse.setReturnStatus(failStatus);
		
		try {
			if(StringUtils.isBlank(userId)){
				logger.info("用户必须登录！");
				return queryVouchersResponse;
			}
			Object vt = paramMap.get(com.unison.lottery.api.protocol.common.Constants.VOUCHER_TYPE);
			String voucherType = (String) ((vt == null)  ? vt : vt.toString());
			int page = Integer.parseInt(paramMap.get(com.unison.lottery.api.protocol.common.Constants.PAGE).toString());
			int pageCount = Integer.parseInt(paramMap.get(com.unison.lottery.api.protocol.common.Constants.PERPAGE_COUNT).toString());
			
			Paging paging = new Paging();
			voucherUserService.list(initPaging(paging, page,pageCount), 
					VoucherUserViewStatus.UNUSED.name(), initQueryParameters(voucherType,Long.parseLong(userId)));
			queryVouchersResponse.setPaging(paging);
			queryVouchersResponse.setReturnStatus(succStatus);
		} catch (Exception e) {
			logger.error("查询用户ID:{},当前可用优惠劵失败！:{}",userId,e);
		}
		return queryVouchersResponse;
	}
	
	/**
	 * 初始化分页参数
	 * @param page 优惠券的页码，从1开始"
	 * @param pageCount  每页的个数
	 * @return
	 */
	private Paging initPaging(Paging paging ,int page ,int pageCount){
		if(page < 1){
			paging.setPageNo(1);
		}else{
			paging.setPageNo(page);
		}
		if(pageCount < 1){
			paging.setMaxResults(20);
		}else{
			paging.setMaxResults(pageCount);
		}
		return paging;
	}
	
	/**
	 * 初始化查询参数
	 * @param voucherType 优惠劵类型
	 * @return
	 */
	private VoucherUserExtendPO initQueryParameters(String voucherType , Long userId){
		VoucherUserExtendPO voucherUser = new VoucherUserExtendPO();
		UserPO user = new UserPO();
		user.setId(userId);
		voucherUser.setUser(user);
		if(StringUtils.isNotBlank(voucherType)){
			VoucherPO voucherPO = new VoucherPO();
			voucherPO.setType(voucherType);
			voucherUser.setVoucher(voucherPO);
			if(voucherType.equals(VoucherType.recharge.name())){
				voucherPO.setLimit(VoucherLimit.client.name());
			}
		}
		return voucherUser;
	}

	/**
	 * 使用客户端彩金优惠劵
	 */
	@Override
	@Transactional
	public UseVoucherResponse useVoucher(Map<String, Object> paramMap ,String userId) {
		UseVoucherResponse useVoucherResponse = new UseVoucherResponse();
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.UseVoucer.SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.UseVoucer.FAIL);
		useVoucherResponse.setReturnStatus(failStatus);
		Object voucherIdO = paramMap.get(com.unison.lottery.api.protocol.common.Constants.VOUCHER_USER_ID);
		String voucherId = (String) (voucherIdO == null ? voucherIdO : voucherIdO);
		
		if(StringUtils.isBlank(userId)){
			logger.info("用户必须登录！");
			return useVoucherResponse;
		}
		if(StringUtils.isBlank(voucherId)){
			logger.info("优惠劵id不能为空！");
			return useVoucherResponse;
		}
		Long voucherUserId = Long.parseLong(voucherId);
		Long uid = Long.parseLong(userId);
		
		try {
			voucherUserService.useClientGrantVoucher(voucherUserId, uid);
			useVoucherResponse.setDesc("使用优惠券成功");
			useVoucherResponse.setReturnStatus(succStatus);
		} catch (Exception e) {
			logger.error("使用客户端优惠劵失败！优惠劵ID:{} , 用户ID:{} , {}", new String[]{voucherUserId.toString(),userId},e);
		} finally{
			Account account = accountService.getAccount(uid);
			useVoucherResponse.setFree(account.getFree());
			useVoucherResponse.setFund(account.getFund());
			useVoucherResponse.setGrant(account.getGrant());
		}
		return useVoucherResponse;
	}
}