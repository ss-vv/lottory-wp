package com.unison.lottery.weibo.web.action.pc.ajax.scheme;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.ShowSchemeResult;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.service.BetSchemeBaseService;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.LotteryId;

/**
 * @desc 用户可以在投注记录中对指定的方案进行晒单操作
 * <p>方案要求：</p>
 * <p>1.支持彩种：目前只有竞彩篮球和竞彩足球</p>
 * <p>2.方案如果未晒单，则发出晒单微博</p>
 * <p>3.方案已晒单则发转发微博</p>
 * 
 * @author lei.li@unison.net.cn
 * @createTime 2014-4-22
 * @version 2.0
 * @lastUpdateTime 2014-12-1
 */
public class ShowSchemeWeiboAction extends BaseAction {

	private static final long serialVersionUID = 5896092198258232555L;

	private Logger log = LoggerFactory.getLogger(getClass());
	private Data data = Data.failure("操作失败");

	@Autowired
	private BetSchemeBaseService betSchemeBaseService;
	@Autowired
	private MessageService messageService;

	private Long schemeId;

	public String execute() {
		ShowSchemeResult showResult = new ShowSchemeResult();
		long userId = getUserId();
		if (userId <= 0) {
			showResult.setDesc("用户未登录");
		} else {
			try {
				BetSchemePO schemePO = betSchemeBaseService.getSchemePOById(schemeId);
				if(null == schemePO) {
					log.error("用户ID={},发起晒单：方案ID={};对应方案信息无法找到！", new Object[]{userId, schemeId});
					showResult.setDesc("找不到方案信息");
				} else {
					if(userId == schemePO.getSponsorId()) {
						log.info("用户ID={},发起晒单：方案ID={}, 方案详情={}", new Object[]{userId, schemeId, schemePO});
						String rs = validScheme(schemePO);
						if(StringUtils.isNotBlank(rs)) {
							showResult.setDesc(rs);
						} else {
							boolean result = betSchemeBaseService.publishShowScheme(schemePO, userId);
							if(result) {
								showResult.setStatus(true);
								data = Data.success(SUCCESS);
							}
						}
					} else {
						log.error("当前登录用户ID={}, 和方案用户ID={},不是同一人！操作非法。。。",
								new Object[]{userId, schemePO.getSponsorId()});
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		data.setData(showResult);
		return SUCCESS;
	}

	protected String validScheme(BetSchemePO po) {
		if(null == po || po.getId() <= 0) return "找不到方案信息";
		String lotteryId = po.getLotteryId();
		if(!LotteryId.JCZQ.name().equals(lotteryId) && 
				!LotteryId.JCLQ.name().equals(lotteryId)) {
			log.error("不支持的晒单操作，对方案ID={},lotteryId={}", new Object[]{
					po.getId(), lotteryId});
			return "方案彩种暂不被支持";
		}
		int type = po.getType();
		if(type == EntityType.BETTING_PARTNER) {
			return "合买暂不支持晒单";
		} else if(type == EntityType.BETTING_FOLLOW && 
				(po.getIsPublishShow() == EntityType.PUBLISH_SHOW)) {
			return "跟单暂不支持晒单";
		}
		return null;
	}
	
	public Data getData() {
		return data;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}
}