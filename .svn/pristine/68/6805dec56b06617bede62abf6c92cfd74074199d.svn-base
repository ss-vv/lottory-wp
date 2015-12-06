package com.unison.lottery.weibo.msg.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.msg.service.RealWeiboService;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;

@Service
public class RealWeiboServiceImpl implements RealWeiboService{
	
	@Autowired
	MessageService messageService;
	@Autowired
	private BetSchemeService betSchemeService;
	@Override
	public PageResult<WeiboMsgVO> findRealWeiboByRecentDays(int recentDays) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public PageResult<WeiboMsgVO> findRealWeiboByRecentDaysAndSort(PageRequest pageRequest,
			int recentDays, String followCountSort, String buyAmountSort,
			String rateOfReturnSort, String timeSort,String lottery) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -recentDays);
		String fromDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		List<Long> schemeIds = null;
		if(PageRequest.SORT_ORDER_DESC.equals(followCountSort) || PageRequest.SORT_ORDER_ASC.equals(followCountSort)){
			schemeIds = betSchemeService.queryShowingSchemeBy(fromDate, followCountSort, null, null,null,lottery);
		} else if(PageRequest.SORT_ORDER_DESC.equals(timeSort) || PageRequest.SORT_ORDER_ASC.equals(timeSort) ){
			schemeIds = betSchemeService.queryShowingSchemeBy(fromDate, null, null, null,timeSort,lottery);
		} else if(PageRequest.SORT_ORDER_DESC.equals(buyAmountSort) || PageRequest.SORT_ORDER_ASC.equals(buyAmountSort)){
			schemeIds = betSchemeService.queryShowingSchemeBy(fromDate, null, buyAmountSort, null,null,lottery);
		} else if(PageRequest.SORT_ORDER_DESC.equals(rateOfReturnSort) || PageRequest.SORT_ORDER_ASC.equals(rateOfReturnSort)){
			schemeIds = betSchemeService.queryShowingSchemeBy(fromDate, null, null, rateOfReturnSort,null,lottery);
		}
		List<WeiboMsgVO> weiboMsgVOs = new ArrayList<WeiboMsgVO>();
		if(null != schemeIds){
			pageRequest.setRecordCount(schemeIds.size());
			for(int i = pageRequest.getOffset(); i < (pageRequest.getOffset()+pageRequest.getPageSize()) 
					&& i < pageRequest.getRecordCount(); i++){
				long weiboUid = messageService.getWeiboIdByShowSchemeId(schemeIds.get(i));
				weiboMsgVOs.add(messageService.getWeiboVoById(weiboUid));
			}
		}
		return new PageResult<WeiboMsgVO>(pageRequest, weiboMsgVOs);
	}
}
