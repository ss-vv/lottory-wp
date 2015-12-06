package com.unison.lottery.api.follow.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.model.Item;
import com.unison.lottery.api.protocol.response.model.SelectFollowResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.commons.persist.dao.BetPartnerDao;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.entity.BetPartnerPO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.ucenter.persistent.service.IUserService;

public class SelectFollowBOImpl implements SelectFollowBO {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IStatusRepository status;
	
	@Autowired
	private BetSchemeDao betSchemeDao;
	
	@Autowired 
	private BetPartnerDao betPartnerDao;
	
	@Autowired
	private IUserService userService;
	
	@Override
	@Transactional(readOnly = true)
	public SelectFollowResponse selectFollow(User user, Long betSchemeID, int startPosition, String type) {
		SelectFollowResponse response = new SelectFollowResponse();
		ReturnStatus succStatus = status
				.getSystemStatusBySystemKey(SystemStatusKeyNames.SelectFollow.SELECT_FOLLOW_SUCC);
		ReturnStatus failStatus = status
				.getSystemStatusBySystemKey(SystemStatusKeyNames.SelectFollow.SELECT_FOLLOW_FAIL);

		response.setStatus(failStatus.getStatusCodeForClient());
		response.setDesc(failStatus.getDescForClient());
		response.setReturnStatus(failStatus);
		
		try {
			if(null != user) {
				List<Item> itemList = new ArrayList<Item>();
				if(StringUtils.equals("0", type)){//跟单用户
					List<BetSchemePO> betSchemeList = betSchemeDao.findFollowSchemesWithPager(betSchemeID, startPosition);
					for(BetSchemePO schemePO : betSchemeList) {
						com.xhcms.lottery.commons.data.User user2 = userService.getUser(schemePO.getSponsorId());
						Item item = new Item();
						item.nickName = user2.getNickName();
						item.imageUrl = user2.getHeadImageURL();
						item.multiple = schemePO.getMultiple();
						item.betAmount = BigDecimal.valueOf(schemePO.getBuyAmount());
						item.bonus = schemePO.getAfterTaxBonus();
						item.schemeStatus = schemePO.getStatus();
						itemList.add(item);
					}
				} else if (StringUtils.equals("1", type)) {//合买用户
					List<BetPartnerPO> partners = betPartnerDao.findPartnersWithPager(betSchemeID, startPosition);
					Map<Long, Integer> maps = new HashMap<Long, Integer>();
					if(partners != null && !partners.isEmpty()){
						List<Long> userIds = new ArrayList<Long>(); 
 						for(BetPartnerPO betPartnerPO : partners){
 							userIds.add(betPartnerPO.getUserId());
 							if(maps.keySet().contains(betPartnerPO.getUserId())){
 								maps.put(betPartnerPO.getUserId(), maps.get(betPartnerPO.getUserId()) + betPartnerPO.getBetAmount());
 							} else{
 								maps.put(betPartnerPO.getUserId(), betPartnerPO.getBetAmount());
 							}
						}
 						List<com.xhcms.lottery.commons.data.User> users = userService.findUsersByIds(userIds);
 						if(users != null && !users.isEmpty()){
 							for(com.xhcms.lottery.commons.data.User user2 : users){
 								Item item = new Item();
 								item.nickName = user2.getNickName();
 								item.imageUrl = user2.getHeadImageURL();
 								item.betAmount = BigDecimal.valueOf(maps.get(user2.getId()));
 								itemList.add(item);
 							}
 						}
					}
				}
				response.setList(itemList);
				
				response.setStatus(succStatus.getStatusCodeForClient());
				response.setDesc(succStatus.getDescForClient());
				response.setReturnStatus(succStatus);
			}
		} catch (Exception e) {
			logger.error("查询跟单用户时出错,用户：{}, 错误信息：{}",
					new Object[] { user, e.getMessage() });
		}
		return response;
	}
}