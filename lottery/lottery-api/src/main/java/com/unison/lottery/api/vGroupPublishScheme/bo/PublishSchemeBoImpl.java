package com.unison.lottery.api.vGroupPublishScheme.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.api.login.hx.httpclient.apidemo.HX_sendMassage;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.PublishSchemeResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.unison.lottery.api.vGroup.data.GroupSecretkey;
import com.unison.lottery.api.vGroupPublishScheme.data.PublishSchemeClient;
import com.unison.lottery.api.vGroupPublishScheme.exception.BetSchemeIsBlankException;
import com.unison.lottery.api.vGroupPublishScheme.exception.SchemeIdOrGroupIdOrUserIdExcption;
import com.unison.lottery.api.vGroupPublishScheme.exception.UserIsBlankException;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.PublishBetScheme;
import com.xhcms.lottery.commons.persist.dao.BetMatchDao;
import com.xhcms.lottery.commons.persist.entity.BetMatchPO;
import com.xhcms.lottery.commons.persist.entity.GoldSchemePO;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.commons.persist.service.GoldSchemeService;
import com.xhcms.lottery.commons.persist.service.PublishBetSchemeService;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.SchemeDisplayMode;
import com.xhcms.lottery.utils.DES;
import com.xhcms.ucenter.persistent.service.IUserService;
import com.xhcms.ucenter.persistent.service.IUserValidIdManager;

public class PublishSchemeBoImpl implements PublishSchemeBo {

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IStatusRepository statusRepository;
	
	@Autowired
	private BetSchemeService betSchemeService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private PublishBetSchemeService publishBetSchemeService;
	
	@Autowired
	private IUserValidIdManager iUserValidIdManager;
	
	@Autowired
	private BetMatchDao betMatchDao;
	
	@Autowired
	private GoldSchemeService goldSchemeService;
	
	@Override
	public PublishSchemeResponse publishScheme2HX(User user,
			Map<String, Object> paramMap) {
        PublishSchemeResponse publishSchemeResponse = new PublishSchemeResponse();
        ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QUERY_GROUP_INFO_SUCC);
        ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QUERY_GROUP_INFO_FAIL);
        publishSchemeResponse.setReturnStatus(failStatus);
        String validId = user.getValidId();
        String groupid = (String) paramMap.get("groupid");
        String schemeId = (String) paramMap.get("schemeId");
        String channel = user.getChannel();
		try {
			if (StringUtils.isBlank(schemeId) || StringUtils.isBlank(groupid)
					|| StringUtils.isBlank(validId)) {
				throw new SchemeIdOrGroupIdOrUserIdExcption();
			}
			String userId = iUserValidIdManager.findUserIdByValidIdAndCurrentTime(validId);
			if(StringUtils.isBlank(userId)){
				logger.info("该用户为空, userId:{}", userId);
				throw new UserIsBlankException();
			}
			com.xhcms.lottery.commons.data.User userVo = userService.getUser(Long.valueOf(userId));
			if(userVo == null){
				logger.info("该用户为空, userId:{}", userId);
				throw new UserIsBlankException();
			}
			BetScheme bet = betSchemeService.getSchemeById(Long.valueOf(schemeId));
			if(bet == null){
				logger.info("该用户的此方案为空,userId:{} ,schemeId:{}", userId, schemeId);
				throw new BetSchemeIsBlankException();
			}
//			if(!StringUtils.equals(String.valueOf(bet.getSponsorId()), userId)){
//				logger.info("用户数据异常... sponsorId:{} userId : {} 不相等", bet.getSponsorId(), userId);
//				throw new SponsorIdAndUserIdNotEq();
//			}
			PublishSchemeClient publishSchemeClient = makePublishSchemeClientParams(userVo, bet, channel);
			String jsonStr = PublishSchemeClient.toJsonString(publishSchemeClient);
			//发到特定的群里
			HX_sendMassage hx_sendMassage = new HX_sendMassage();
			if(hx_sendMassage.sendMassage2Group(groupid, jsonStr)){
				PublishBetScheme publishBetScheme = makePublishSchemeParams(userId, schemeId, groupid, userVo.getNickName(), userVo.getHeadImageURL());
				List<BetMatchPO> betMatchPOs = betMatchDao.findBySchemeId(Long.valueOf(schemeId));
				boolean isExt = publishBetSchemeService.isExistPublishBetScheme(publishBetScheme);
				if(!isExt){
					boolean result = publishBetSchemeService.savePublishScheme(publishBetScheme, betMatchPOs);
					if(result){
						publishSchemeResponse.setReturnStatus(succStatus);
					} else {
						logger.info("用户userId:{}的方案schemeId:{}消息已发，但保存更新创建时间...", userId,schemeId);
					}
				}else{
					publishSchemeResponse.setReturnStatus(succStatus);
				}
			} else {
				publishSchemeResponse.setReturnStatus(failStatus);
			}
			
		} catch (SchemeIdOrGroupIdOrUserIdExcption e) {
			logger.info("validId : {} groupid :{} schemeId:{}其中有参数为空 ", validId, groupid, schemeId);
			ReturnStatus resquetParams = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.RESQUEST_PARAM_FAIL); 
			publishSchemeResponse.setReturnStatus(resquetParams);
			e.printStackTrace();
		}catch (BetSchemeIsBlankException e) {
			publishSchemeResponse.setReturnStatus(failStatus);
			e.printStackTrace();
		}catch (UserIsBlankException e) {
			publishSchemeResponse.setReturnStatus(failStatus);
			e.printStackTrace();
		}
//		catch (SponsorIdAndUserIdNotEq e) {
//			ReturnStatus dataFail = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.DATA_FAIL);
//			publishSchemeResponse.setReturnStatus(dataFail);
//			e.printStackTrace();
//		}
		catch (Exception  e){
			publishSchemeResponse.setReturnStatus(failStatus);
			e.printStackTrace();
		}
        
		return publishSchemeResponse;
	}

	private PublishBetScheme makePublishSchemeParams(String userId,
			String schemeId, String groupid,String nickName, String imageUrl) {
		PublishBetScheme publishBetScheme = new PublishBetScheme();
		publishBetScheme.setBetSchemeId(Long.valueOf(schemeId));
		try {
			publishBetScheme.setGroupid(DES.decryptDES(groupid, GroupSecretkey.secretKey, "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		publishBetScheme.setUserId(Long.valueOf(userId));
		publishBetScheme.setNickName(nickName);
		publishBetScheme.setImageUrl(imageUrl);
		return publishBetScheme;
	}

	private PublishSchemeClient makePublishSchemeClientParams(
			com.xhcms.lottery.commons.data.User userVo, BetScheme bet, String channel) {
		PublishSchemeClient publishScheme = new PublishSchemeClient();
		publishScheme.userId = userVo.getId();
		publishScheme.nickName = userVo.getNickName();
		publishScheme.rateOfReturn = bet.getReturnRation();
		publishScheme.betAmount = bet.getTotalAmount();
		publishScheme.maxBonus = bet.getMaxBonus().setScale(2, BigDecimal.ROUND_HALF_UP);
		publishScheme.lotteryId = bet.getLotteryId();
		publishScheme.bounus = bet.getAfterTaxBonus();
		publishScheme.betType = bet.getType();
		if(bet.getStatus() == EntityStatus.TICKET_NOT_AWARD || bet.getStatus() == EntityStatus.TICKET_AWARDED){
			publishScheme.afterTaxBonus = bet.getAfterTaxBonus() + "";//中奖金额
		}
		if(new Date().getTime() < bet.getOfftime().getTime()){
        	if(bet.getStatus() != EntityStatus.TICKET_BUY_FAIL){
        		bet.setStatus(EntityStatus.SCHEME_SALE);
        	}
	    }
		publishScheme.betSchemeStatus = bet.getStatus();
		publishScheme.schemeId = bet.getId();
		if (StringUtils.isNotBlank(userVo.getHeadImageURL())) {
			publishScheme.imageUrl = userVo.getHeadImageURL();
		} else {
			publishScheme.imageUrl = "#";
		}
		publishScheme.singlePrice = bet.getTotalAmount() / bet.getMultiple();
		publishScheme.displayMode = SchemeDisplayMode.getDisplayMode(bet.getType(), bet.getShowScheme(), bet.getIsPublishShow());
		try {
			String decryptStr = DES.decryptDES(channel, GroupSecretkey.secretKey, "utf-8");
			if(StringUtils.isNotBlank(decryptStr)){
				String[] strs = decryptStr.split("[+]");
				logger.info("环信groupId: {} , 大V id : {} , clientVersion : {}" , strs[0], strs[1], strs[2]);
				if(StringUtils.equals(String.valueOf(userVo.getId()), strs[1])||goldSchemeService.findGoldSchemeByGoldSchemeId(strs[1])==null){
					GoldSchemePO gs=new GoldSchemePO();
					gs.setGoldSchemeId(strs[1]);
					gs.setCreatetime(new Date());;
					goldSchemeService.addGoldScheme(gs);
					publishScheme.isDaV = true;
				} else {
					publishScheme.isDaV = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return publishScheme;
	}
	
}
