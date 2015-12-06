package com.unison.lottery.api.redEnvalope.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import antlr.MakeGrammar;

import com.davcai.lottery.service.RedEnvalopeService;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.GrabRedEnvalopeResponse;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.unison.lottery.api.redEnvalope.model.EnvalopeGrabHistory;
import com.unison.lottery.api.redEnvalope.model.RedEnvalope;
import com.unison.lottery.weibo.common.nosql.RedEnvalopeRedisDao;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.UserService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.utils.DateUtils;

public class GrabRedEnvalopeBoImpl implements GrabRedEnvalopeBo {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private IStatusRepository statusRepository;
	
	@Autowired
	private RedEnvalopeService redEnvalopeService;
	
	@Autowired
	private RedEnvalopeRedisDao redEnvalopeRedisDao;
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserService userService;
	
	@Override
	@Transactional(rollbackFor=Exception.class,readOnly=false)
	public HaveReturnStatusResponse grabRedEnvalope(Long envalopeId,
			User user) {
		GrabRedEnvalopeResponse grabRedEnvalopeResponse = new GrabRedEnvalopeResponse();
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QUERY_GROUP_INFO_SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QUERY_GROUP_INFO_FAIL);
		ReturnStatus redEnvalopeNotExist = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.redEnvalope.RED_ENVALOPE_NOT_EXIST);
		ReturnStatus redEnvalopeGrabSuccess = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.redEnvalope.RED_ENVALOPE_GRAB_SUCCESS);
		ReturnStatus redEnvalopeInvalid = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.redEnvalope.RED_ENVALOPE_INVALID);
		ReturnStatus grabedRedEnvalope = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.redEnvalope.GRABED_RED_ENVALOPE);
		grabRedEnvalopeResponse.setReturnStatus(failStatus);
		if(envalopeId!=null&&user.getId()!=null){
			//获取红包信息
			RedEnvalope redEnvalope = redEnvalopeService.findRedEnvalopeById(envalopeId);
			if(redEnvalope!=null){
				Date createTime = redEnvalope.getCreateTime();
				Date nowDate = new Date();
				com.xhcms.lottery.commons.data.User userVO = userService.getUser(Long.parseLong(user.getId()));
				if((nowDate.getTime()-createTime.getTime())<=(24*60*60*1000)){
					//抢红包开始
					Long smallRedEnvalopeAmount = redEnvalopeRedisDao.grabRedAmountFromRedis(redEnvalope.getLtUserId(),redEnvalope.getEnvalopeId(),userVO.getId());
					if(smallRedEnvalopeAmount.longValue()==0){
						//红包已抢光
						grabRedEnvalopeResponse.setGrabResult(redEnvalopeNotExist.getStatusCodeForClient());
					}else if(smallRedEnvalopeAmount<0){
						//已抢过
						grabRedEnvalopeResponse.setGrabResult(grabedRedEnvalope.getStatusCodeForClient());
					}else{
						Long grabAmount = redEnvalope.getGrabedEnvalopeAmount();
						redEnvalope.setGrabedEnvalopeAmount(grabAmount!=null?grabAmount+=smallRedEnvalopeAmount:smallRedEnvalopeAmount);
						if(redEnvalope.getGrabedEnvalopeAmount().longValue()==redEnvalope.getRedEnvalopeAmount().longValue()){
							//已抢完，记录抢完所用分钟数
							redEnvalope.setSecondOfGrabed(DateUtils.getSecondOfTwoDate(new Date(),redEnvalope.getCreateTime()).intValue());
						}
						//对发红包用户的资金账户的冻结资金进行扣款,
						//并增加到抢红包用户的资金账户的赠款中
						Map<Integer,BigDecimal> data = null;
						try{
							data = accountService.withdGrabRedDeduct(userVO.getId(),smallRedEnvalopeAmount,redEnvalope);
							grabRedEnvalopeResponse.setFree(data.get(Constants.FREE).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
							grabRedEnvalopeResponse.setGrant(data.get(Constants.GRANT).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
							redEnvalopeService.updateRedEnvalope(redEnvalope,smallRedEnvalopeAmount,userVO.getId());
							grabRedEnvalopeResponse.setGrabResult(redEnvalopeGrabSuccess.getStatusCodeForClient());
							grabRedEnvalopeResponse.setGrabAmount(smallRedEnvalopeAmount);
						}catch(Exception e){
							e.printStackTrace();
							//冻结现金异常，返还抢到的红包
							redEnvalopeRedisDao.addEnvalopeToRedis(envalopeId, redEnvalope.getLtUserId(), new long[]{smallRedEnvalopeAmount});
							grabRedEnvalopeResponse.setGrabResult("4");//抢红包失败
						}
						
					}
				}else{
					//红包已失效
					grabRedEnvalopeResponse.setGrabResult(redEnvalopeInvalid.getStatusCodeForClient());
				}
				//查询红包历史
				String grabAmount = null;
				Map<String, Object> data = redEnvalopeService.findRedEnvalopeRecordById(envalopeId,userVO.getId(),grabAmount);
				List<EnvalopeGrabHistory> envalopeGrabHistories = (List<EnvalopeGrabHistory>) data.get("1");
				grabAmount = (String) data.get("2");
				makeGrabResponse(grabRedEnvalopeResponse, succStatus,
						redEnvalope, userVO, grabAmount, envalopeGrabHistories);
			}
		}
		return grabRedEnvalopeResponse;
	}

	private void makeGrabResponse(
			GrabRedEnvalopeResponse grabRedEnvalopeResponse,
			ReturnStatus succStatus, RedEnvalope redEnvalope,
			com.xhcms.lottery.commons.data.User userVO, String grabAmount,
			List<EnvalopeGrabHistory> envalopeGrabHistories) {
		if(grabAmount!=null&&grabRedEnvalopeResponse.getGrabAmount()==null){
			grabRedEnvalopeResponse.setGrabAmount(Long.valueOf(grabAmount));
		}
		grabRedEnvalopeResponse.setEnvalopeNum(redEnvalope.getEnvalopeNum());
		if(userVO.getId().equals(redEnvalope.getLtUserId())){
			grabRedEnvalopeResponse.setRedEnvalopeAmount(redEnvalope.getRedEnvalopeAmount());
			grabRedEnvalopeResponse.setGrabedAmount(redEnvalope.getGrabedEnvalopeAmount());
		}
		grabRedEnvalopeResponse.setMinuteOfGrabed(makeTimeAfterFormat(redEnvalope.getSecondOfGrabed()));
		if(redEnvalope.getRedEnvalopeAmount().equals(redEnvalope.getGrabedEnvalopeAmount())||"3".equals(grabRedEnvalopeResponse.getGrabResult())){
			//标志出抢金额最多的用户
			envalopeGrabHistories = makeGrabMostMoneyUser(envalopeGrabHistories);
		}
		grabRedEnvalopeResponse.setNickName(userVO.getNickName());
		grabRedEnvalopeResponse.setImageUrl(userVO.getHeadImageURL());
		grabRedEnvalopeResponse.setEnvalopeGrabHistory(envalopeGrabHistories);
		grabRedEnvalopeResponse.setReturnStatus(succStatus);
	}

	private String makeTimeAfterFormat(Integer secondOfGrabed) {
		int day = secondOfGrabed/(60*60*24);
		int hour = secondOfGrabed/(60*60)-day*24;
		int minute = secondOfGrabed/(60)-day*24*60-hour*60;
		int second = secondOfGrabed-day*24*60*60-hour*60*60-minute*60;
		String time = "";
		if(day!=0){
			time = day+"天"+hour+"小时";
		}else if(hour!=0){
			time = hour+"小时"+minute+"分";
		}else if(0!=minute){
			time = minute+"分"+second+"秒";
		}else
		{
			time = second+"秒";
		}
		
		return time;
	}

	private List<EnvalopeGrabHistory> makeGrabMostMoneyUser(
			List<EnvalopeGrabHistory> envalopeGrabHistories) {
		if(envalopeGrabHistories!=null){
			long maxAmount = 0;
			//抢红包最多金额用户的位置
			int maxUserPosition = -1;
			for(int i=0;i<envalopeGrabHistories.size();i++){
				EnvalopeGrabHistory envalopeGrabHistory = envalopeGrabHistories.get(i);
				if(maxAmount<envalopeGrabHistory.getGrabAmount()){
					maxAmount = envalopeGrabHistory.getGrabAmount();
					envalopeGrabHistory.setLuckUser(1);
					if(maxUserPosition!=-1){
						EnvalopeGrabHistory envalopeGrabHistoryBefore = envalopeGrabHistories.get(maxUserPosition);
						envalopeGrabHistoryBefore.setLuckUser(0);
					}
					maxUserPosition = i;
				}else {
					envalopeGrabHistory.setLuckUser(0);
				}
			}
		}
		return envalopeGrabHistories;
	}

}
