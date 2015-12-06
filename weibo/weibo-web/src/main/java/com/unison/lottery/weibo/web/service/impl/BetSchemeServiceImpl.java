package com.unison.lottery.weibo.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.common.nosql.MessageDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.SchemeService;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.vo.BetSchemeVo;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.web.service.BetSchemeService;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.utils.CombineBetMatchUtil;

@Service
public class BetSchemeServiceImpl implements BetSchemeService {
	@Autowired
	private BetSchemeDao betSchemeDao;
	@Autowired
	private SchemeService schemeService;
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private MessageService messageService;
	
	@Override
	@Transactional
	public List<BetSchemeVo> getLastBetScheme() {
		List<BetSchemeVo> bs = new ArrayList<BetSchemeVo>();
		List<Object> ids = betSchemeDao.getLastFourSchemeId();
		if (ids != null && ids.size() > 0) {
			for (int i = 0; i < ids.size(); i++) {
				BetSchemeVo vo = new BetSchemeVo();
				Long id = Long.parseLong(ids.get(i).toString());
				BetScheme bet = schemeService.viewSchemeCache(id, null,
						EntityType.DISPLAY_SHOW);
				//将同一赛事不同玩法合并
				bet = CombineBetMatchUtil.combine(bet);
				BeanUtils.copyProperties(bet, vo);
				//String weiboId = messageDao.getString(Keys.recommendSchemeToWeibo(id + ""));
				String weiboId = messageDao.getString(Keys.showSchemeToWeibo(id + ""));
				getWeiboAndFillData(vo, weiboId);
				bs.add(vo);
			}

		}
		return bs;
	}
	
	private void getWeiboAndFillData(BetSchemeVo bet, String weiboKey) {
		WeiboMsgVO vo = messageDao.getVO(weiboKey);
		messageService.addedInfo(vo);

		if (vo != null) {
			bet.setWeiboId(weiboKey);
			bet.setNikeName(vo.getUser() != null ? vo.getUser().getNickName()
					: "");
			// 评论
			bet.setCommentCount(vo.getCommentCount());
			// 赞
			bet.setLikeCount(vo.getLikeCount());
			if (vo.getUser() != null) {
				bet.setWeiboUserId(vo.getUser().getWeiboUserId() + "");
			}
		}
	}
}