package com.xhcms.lottery.account.web.action.recommend;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.lang.Constant;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.account.thrift.UserAccountClient;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.service.BetSchemeRecService;

/**
 * @desc 发推荐:生成推荐方案和推荐微博 条件：1.当前用户存在对应微博账号 2.推荐微博内容不能为空且不能超出微博约定的最大长度 3.所选赛事不能为空
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-28
 * @version 1.0
 */
public class PublishRecommendAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(getClass());

	private String playId;

	private String betMatchs;

	private String passTypes;

	private int multiple;

	private int money;

	private String bonus;

	private String content;

	private Data data = Data.failure("推荐失败.");

	private String annotations;

	@Autowired
	private BetSchemeRecService schemeRecService;

	@Autowired
	private UserAccountClient userAccountClient;

	public String post() {
		try {
			log.debug(
					"发推荐,接收到的数据,playId={},betMatchs={},passTypes={},recommendContent={}",
					new Object[] { playId, betMatchs, passTypes, content });
			long userId = getUserId();
			if (userId > 0) {
//				if (StringUtils.isBlank(content)) {
//					log.info("微博内容为空！");
//					data = Data.success(null);
//				} else
				if (content.length() > Constant.WeiboContentLength.POST) {
					log.info("微博内容字数={} , 不能大于{}字！", content.length(),
							Constant.WeiboContentLength.POST);
				} else {
					long weiboUserId = userAccountClient
							.findWeiboUserIdByLotteryUid(userId);
					if (weiboUserId <= 0) {
						data.setData("对应微博用户不存在.");
					} else {
						BetScheme scheme = schemeRecService.getScheme(playId,
								betMatchs, passTypes, money, multiple, bonus);
						scheme.setSponsorId(userId);
						scheme.setSponsor(getUsername());

						schemeRecService.saveBetScheme(scheme, weiboUserId,
								content, annotations);

						data = Data.success(null);
					}
				}
			}
		} catch (Exception e) {
			data = Data.failure("发推荐请求异常。");
			log.error("发推荐异常.", e);
		}
		return SUCCESS;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public void setBetMatchs(String betMatchs) {
		this.betMatchs = betMatchs;
	}

	public void setPassTypes(String passTypes) {
		this.passTypes = passTypes;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	
	public Data getData() {
		return data;
	}

	public String getAnnotations() {
		return annotations;
	}

	public void setAnnotations(String annotations) {
		this.annotations = annotations;
	}
}