package com.unison.lottery.weibo.web.action.pc;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.msg.service.UserLotterySetService;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.xhcms.commons.lang.Data;

/**
 * 用户个人彩种设置
 */
public class UserLotterySetAction extends BaseAction {

	private static final long serialVersionUID = -8700565177834871349L;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserLotterySetService lotterySetService;

	private Data data = Data.success("成功");

	public String execute() {
		return SUCCESS;
	}

	public String loadLotterySet() {
		try {
			List<WeiboUser> list = lotterySetService
					.listUserLotteryRelation(getUserLaicaiWeiboId());
			data.setData(list);
		} catch (Exception e) {
			logger.error("加载用户彩种关注关系错误:{}", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public Data getData() {
		return data;
	}
}
