package com.unison.lottery.api.voucher.bo;

import java.util.Map;

import com.unison.lottery.api.protocol.response.model.QueryVouchersResponse;
import com.unison.lottery.api.protocol.response.model.UseVoucherResponse;

/**
 * 优惠劵
 * @author Wang Lei
 *
 */
public interface VoucherBO {
	/**
	 * 查询用户可用优惠劵
	 * @param paramMap
	 * @param userId
	 * @return
	 */
	QueryVouchersResponse queryVouchers(Map<String,Object>  paramMap,String userId);
	
	/**
	 * 使用彩金优惠劵
	 * @param voucherUserId
	 * @param userId
	 */
	UseVoucherResponse useVoucher(Map<String, Object> paramMap ,String userId);
}
