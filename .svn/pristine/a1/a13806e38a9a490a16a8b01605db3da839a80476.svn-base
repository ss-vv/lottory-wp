package com.unison.lottery.weibo.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.service.ShareOrderTemplateService;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;

@Service
public class ShareOrderTemplateServiceImpl implements ShareOrderTemplateService {

	private static final String simple_share_tpl = "我发起了%s晒单，方案金额%s元，理论最高奖金%s元，回报率%s倍，快来跟单吧！";
	private static final String simple_partner_tpl = "我发起了%s合买，方案金额%s元，理论最高奖金%s元，回报率%s倍，快来参与合买吧！";
	private static final String TICKET_WIN = "中奖：又红单了，中大奖了，走向人生巅峰了！";
	private static final String TICKET_NOT_WIN = "遗憾：就差一点啊，太可惜了！";

	@Override
	public String getShowSchemeTpl(String lotteryName, int totalAmount,
			BigDecimal maxBonus, BigDecimal returnRatio, int schemeType) {
		String tpl = "";
		if(schemeType == EntityType.BETTING_PARTNER) {
			tpl = simple_partner_tpl;
		} else {
			tpl = simple_share_tpl;
		}
		return String.format(tpl, lotteryName, totalAmount,
				maxBonus, returnRatio);
	}

	@Override
	public String forwardWeiboTpl() {
		return "我发起了方案晒单，小伙伴们速来围观吧。。。";
	}

	@Override
	public String schemeStatus(int status) {
		if(status == EntityStatus.TICKET_NOT_WIN) {
			return TICKET_NOT_WIN;
		} else if(status == EntityStatus.TICKET_NOT_AWARD || status == EntityStatus.TICKET_AWARDED) {
			return TICKET_WIN;
		}
		return "";
	}
}