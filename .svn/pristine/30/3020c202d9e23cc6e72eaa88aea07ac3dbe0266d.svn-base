package com.unison.lottery.api.showAndFollow.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.api.model.ClientVersion;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.ShowAndFollowResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.unison.lottery.api.showAndFollow.model.BetShemeParams;
import com.unison.lottery.api.util.AllowReturnResultService;
import com.unison.lottery.api.util.ClientVersionUtil;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.ucenter.persistent.service.IUserService;

public class ShowAndFollowBOImpl implements ShowAndFollowBO {

	
    @Autowired
    private BetSchemeService betSchemeService;

	@Autowired
	private IStatusRepository statusRepository;
    
	@Autowired
	private IUserService userService;
	
	@Autowired
    private AccountService accountService;
	
	@Autowired
	private AllowReturnResultService allowReturnResultService;
	@Override
	public ShowAndFollowResponse queryShowingScheme(Map<String,String> paramMap,User user) {
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.ShowAndFollow.SUCC);
		ShowAndFollowResponse response=null;
		if(allowReturnResultService.shouldDecideAllowReturnResult(user.getClientVersion())){//需要控制用户访问
			if(allowReturnResultService.allow(user)){
				response = doQueryShowScheme(paramMap, succStatus);
			}
			else{
				response=new ShowAndFollowResponse();
				response.setReturnStatus(succStatus);
			}
		}
		else{
			response = doQueryShowScheme(paramMap, succStatus);
		}
		return response;
	}
	private ShowAndFollowResponse doQueryShowScheme(
			Map<String, String> paramMap, ReturnStatus succStatus) {
		String first = paramMap.get(Constants.FIRST_RESULT);
		String schemeType = paramMap.get(Constants.SCHEME_FILTER);
		String type = paramMap.get("type");
		//对于混合投注查看赛单跟单老版本不支持，通过客户端传递的版本号进行过滤
		
		String clientVersion = paramMap.get(Constants.CLIENT_VERSION_NAME);
		ClientVersion clientVersionObj = ClientVersionUtil.transferToClientVersionObj(clientVersion);
		
		ShowAndFollowResponse response = new ShowAndFollowResponse();
		
		List<BetSchemePO> list = null;
		if(StringUtils.equals("0", type)){//晒单
			list = betSchemeService.queryShowingSchemeAll(schemeType, Integer.parseInt(first), Constants.PAGING_MAX_RESULT);
		} else if(StringUtils.equals("1", type)){//合买
			if(shouldPage(clientVersionObj.getPlatform(),clientVersionObj.getVersionNumber())){
				list = betSchemeService.queryShowingGroupbuy(schemeType, Integer.parseInt(first), Constants.PAGING_MAX_RESULT);
			}
			else{
				list=betSchemeService.queryShowingGroupbuyWithoutPage(schemeType);
			}
			
			
		}
		List<BetShemeParams> lists = new ArrayList<BetShemeParams>();
		com.xhcms.lottery.commons.data.User user = null;
		Account account = null;
		BetShemeParams betShemeParams = null;
		for(BetSchemePO betSchemePO : list){
			user = userService.getUser(betSchemePO.getSponsorId());
			account = accountService.getAccount(betSchemePO.getSponsorId());
			betShemeParams = new BetShemeParams();
			betShemeParams.setBetSchemePO(betSchemePO);
			betShemeParams.setImageUrl(user.getHeadImageURL());
			betShemeParams.setMilitaryExploits(account.getTotalAward());
			betShemeParams.setNickName(user.getNickName());
			if(StringUtils.equals("1", type)){ //合买
				betShemeParams.setType("1");
			} else if(StringUtils.equals("0", type)){//晒单
				betShemeParams.setType("0");
			} 
			lists.add(betShemeParams);
		}
		response.setList(lists);
		response.setReturnStatus(succStatus);
		return response;
	}
	
	private boolean shouldPage(String platform, int version) {
		boolean result=false;
		if(StringUtils.equals(platform, "android")){//安卓版本高于1.0.5使用分页
			if(version>=105){//高版本使用分页
				result=true;
			}
			
		}
		else if(StringUtils.equals(platform, "ios")){//ios版本全使用分页
			result=true;
		}
		return result;
	}
}
