package com.unison.lottery.api.vGroupSysScheme.bo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List; 
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.QuerySysSchemeResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.unison.lottery.api.vGroup.data.GroupSecretkey;
import com.unison.lottery.api.vGroupSysScheme.data.SysScheme;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.PublishBetScheme;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.commons.persist.service.PublishBetSchemeService;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.SchemeDisplayMode;
import com.xhcms.lottery.utils.DES;
import com.xhcms.ucenter.persistent.service.IUserService;
import com.xhcms.ucenter.persistent.service.IUserValidIdManager;

public class QuerySysSchemeBoImpl implements QuerySysSchemeBo{

	private Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	private IStatusRepository statusRepository;
	
	@Autowired
	private BetSchemeService betSchemeService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IUserValidIdManager iUserValidIdManager;
	
	@Autowired
	private PublishBetSchemeService publishBetSchemeService;
	
	@Override
	public QuerySysSchemeResponse makeSysScheme(Map<String, String> map) {
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QUERY_GROUP_INFO_SUCC);
        ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QUERY_GROUP_INFO_FAIL);
        QuerySysSchemeResponse querySysSchemeResponse = new QuerySysSchemeResponse();
        querySysSchemeResponse.setReturnStatus(failStatus);
        String type = map.get("type");//0(大v的——默认)|1(自己的)|2(其他人的)
        String channel = map.get("channel");
        String validId = map.get("validId");
        String pages = map.get("pages");
        try{
        	String[] strs = DES.decryptDES(channel, GroupSecretkey.secretKey, "utf-8").split("[+]");
        	if(strs == null || strs.length == 0){
        		logger.info("解析channel出错:{}", channel);
        	}
        	List<PublishBetScheme> publishBetSchemes = new LinkedList<PublishBetScheme>();;
        	String userId = iUserValidIdManager.findUserIdByValidIdAndCurrentTime(validId);
	        if(StringUtils.equals("1", type)){//dav
	        	publishBetSchemes = publishBetSchemeService.getPublishBetSchemeByUserIdWithPages(Long.valueOf(strs[1]), pages, Constants.MAX_PAGES, strs[0]);
	        } else if (StringUtils.equals("0", type)) {//自己
        	    if(StringUtils.isNotBlank(userId)){
        		  publishBetSchemes = publishBetSchemeService.getPublishBetSchemeByUserIdWithPages(Long.valueOf(userId), pages, Constants.MAX_PAGES, strs[0]);
        	    }
			} else if (StringUtils.equals("2", type)) {//其他
				publishBetSchemes = publishBetSchemeService.getPubSchemeIsOthersWithPages(Long.valueOf(userId), Long.valueOf(strs[1]), pages, Constants.MAX_PAGES,strs[0]);
			}
	        if(publishBetSchemes != null && !publishBetSchemes.isEmpty()){
//	        	HashSet<PublishBetScheme> hs = new HashSet<PublishBetScheme>(publishBetSchemes);// 去重
	        	for(PublishBetScheme pBetScheme : publishBetSchemes){
	        		BetScheme betScheme = betSchemeService.getSchemeById(pBetScheme.getBetSchemeId());
	        		if(betScheme != null){
	        			SysScheme sysScheme = makeSysSchemeParams(betScheme, pBetScheme);
	        			querySysSchemeResponse.getSysSchemes().add(sysScheme);
	        		}
	        	}
	        	if(publishBetSchemes.size() < Constants.MAX_PAGES){
	        		querySysSchemeResponse.setPage(-1);
	        	} else{
	        		querySysSchemeResponse.setPage(Integer.valueOf(pages) + Constants.MAX_PAGES);
	        	}
            } else {
            	querySysSchemeResponse.setPage(-1);
            }
			querySysSchemeResponse.setReturnStatus(succStatus);
        } catch(Exception e){
			e.printStackTrace();
		}
        return querySysSchemeResponse;
	}

	private SysScheme makeSysSchemeParams(BetScheme betScheme, PublishBetScheme pBetScheme) {
		SysScheme sysScheme = new SysScheme();
		sysScheme.rateOfReturn = betScheme.getReturnRation();
		sysScheme.betAmount = betScheme.getTotalAmount();
		sysScheme.maxBonus = betScheme.getMaxBonus().setScale(2, BigDecimal.ROUND_HALF_UP);
		sysScheme.lotteryId = betScheme.getLotteryId();
		sysScheme.bounus = betScheme.getAfterTaxBonus();
		sysScheme.betType = betScheme.getType();
		if(betScheme.getStatus() == EntityStatus.TICKET_NOT_AWARD || betScheme.getStatus() == EntityStatus.TICKET_AWARDED){
			sysScheme.afterTaxBonus = betScheme.getAfterTaxBonus() + "";//中奖金额
		}
		if(new Date().getTime() < betScheme.getOfftime().getTime()){
        	if(betScheme.getStatus() != EntityStatus.TICKET_BUY_FAIL){
        		betScheme.setStatus(EntityStatus.SCHEME_SALE);
        	}
	     }
		sysScheme.betSchemeStatus = betScheme.getStatus();
		sysScheme.schemeId = betScheme.getId();
		User user = userService.getUser(pBetScheme.getUserId());
		if (user != null) {
			if (StringUtils.isNotBlank(user.getHeadImageURL())) {
				sysScheme.imageUrl = user.getHeadImageURL();
			} else {
				sysScheme.imageUrl = "#";
			}
			sysScheme.nickName = user.getNickName();
		}
		sysScheme.displayMode = SchemeDisplayMode.getDisplayMode(betScheme.getType(), betScheme.getShowScheme(), betScheme.getIsPublishShow());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		sysScheme.createDate = dateFormat.format(pBetScheme.getCreateDate());
		sysScheme.singlePrice = betScheme.getTotalAmount() / betScheme.getMultiple();
		return sysScheme;
	}

}
