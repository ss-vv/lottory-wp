package com.unison.lottery.weibo.web.action.pc.ajax.scheme;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.service.BetRecordCache;
import com.unison.lottery.weibo.common.service.SchemeService;
import com.unison.lottery.weibo.lang.BetRecordConstant;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.web.action.BaseListAction;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.service.AccountQueryService;
import com.xhcms.lottery.lang.LotteryId;

public class AjaxUserBetRecordAction extends BaseListAction {
	
	private static final long serialVersionUID = 1L;

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private AccountQueryService accountQueryService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private SchemeService schemeService;
	@Autowired
	private MessageService messageService;
	@Autowired
	BetRecordCache betRecordCache;
	
	private String lotteryId;
//	private int type = -1;
//	private int showScheme = -1;
	private Data data = Data.failure("操作失败");
	
	@Override
	public String execute() {
		try {
			if(getUserId() <= 0) {
				data.setData("用户未登录");
			} else {
				List<BetScheme> list = betRecordCache.loadSchemeList(lotteryId, getUserId());
				data = Data.success(loadBetScheme(list));
			}
		} catch (Exception e) {
			log.error("查看个人投注记录失败：{}", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	protected QueryBetRecordResult loadBetScheme(List<?> results) {
		QueryBetRecordResult betRecordResult = new QueryBetRecordResult();
		
		List<BetScheme> list = (List<BetScheme>) results;
		for(int i=0; i<list.size(); i++) {
			BetScheme betScheme = list.get(i);
			if(betScheme.getLotteryId().equals(LotteryId.JCZQ.name())) {
				betRecordResult.getJczq().add(betScheme);
			} else if(betScheme.getLotteryId().equals(LotteryId.JCLQ.name())) {
				betRecordResult.getJclq().add(betScheme);
			} else if(betScheme.getLotteryId().equals(LotteryId.CTZC.name())) {
				betRecordResult.getCtzc().add(betScheme);
			} else if(betScheme.getLotteryId().equals(LotteryId.BJDC.name())) {
				betRecordResult.getBjdc().add(betScheme);
			}
			if(betRecordResult.getAll().size() < BetRecordConstant.LOAD_RECORD_COUNT) {
				betRecordResult.getAll().add(betScheme);
			}
		}
		return betRecordResult;
	}
	
	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	
	public Data getData() {
		return data;
	}
}