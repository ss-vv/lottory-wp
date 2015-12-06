package com.unison.lottery.api.protocol.response.xml.parser.methodparse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Item;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.QueryVouchersResponse;
import com.unison.lottery.api.protocol.response.model.Response;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.persist.entity.VoucherUserExtendPO;

/**
 * 查询可用优惠劵
 * @author Wang Lei
 *
 */
public class QueryVouchersResponseParser extends AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.QUERY_VOUCHERS_RESPONSE_VO_NAME);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		QueryVouchersResponse queryVouchersResponse = (QueryVouchersResponse)responseFromHttpRequest;
		if(null!=queryVouchersResponse&&null!=queryVouchersResponse.getReturnStatus()){
			resultResponse.result=new Result();
			resultResponse.result.item = new ArrayList<Item>();
			Paging paging = queryVouchersResponse.getPaging();
			resultResponse.totalPages = paging.getPageCount() < 1 ? 1 : paging.getPageCount();
			resultResponse.page = paging.getPageNo();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(paging != null && paging.getResults() != null){
				Iterator<VoucherUserExtendPO>  vus = (Iterator<VoucherUserExtendPO>) paging.getResults().iterator();
				while(vus.hasNext()){
					VoucherUserExtendPO vue = vus.next();
					Item resultItem = new Item();
					resultItem.voucherUserId = vue.getId().toString();
					resultItem.voucherType = vue.getVoucher().getType();
					resultItem.voucherName = vue.getVoucher().getName();
					resultItem.voucherInstructions = vue.getVoucher().getInstructions();
					resultItem.price = vue.getVoucher().getPrice();
					resultItem.grant = vue.getVoucher().getGrant();
					resultItem.limit = vue.getVoucher().getLimit();
					resultItem.effectTime = format.format(vue.getEffectTime());
					resultItem.deadTime = format.format(vue.getDeadTime());
					resultItem.voucherStatus = vue.getStatus();
					resultResponse.result.item.add(resultItem);
				}
			}
		}
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.QueryVoucers.SUCC;
	}
}
