/**
 * 
 */
package com.xhcms.lottery.account.web.action.custommade;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.xhcms.lottery.account.service.UserService;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.data.CustomMade;
import com.xhcms.lottery.commons.data.Play;
import com.xhcms.lottery.commons.persist.service.CustomMadeService;
import com.xhcms.lottery.commons.persist.service.PlayService;

/**
 * @author Bean.Long
 * 合买跟单定制页面
 */
public class CustomMadeAction extends BaseAction implements ModelDriven<CustomMade> {
	private static final long serialVersionUID = 4793385862770980982L;
	
	private Long fuid;
	
	private List<Play> allPlays;
	private Set<String> selectedPlayIds = new HashSet<String>();
	private String[] splayids;
	private int count;
	private CustomMade customMade;

	@Autowired
	private PlayService playService;
	@Autowired
	private UserService userService;
	@Autowired
	private CustomMadeService customMadeService;
	@Autowired
    private UserAccountService userAccountService;
	private WeiboUser weiboUser;
	
	public CustomMadeAction() {
	}
	
	@Override
	public String execute() throws Exception {
		if(isGet()) {
			if(fuid == null || userService.getUser(fuid) == null) {
				return ERROR;
			}
			
			initData();
			return INPUT;
		} else if(isPost()) {
			//验证定制内容是否合法
			if(fuid == null) {
				addActionError(getText("sys.param.Error"));
				return ERROR;
			}
			//表单验证
			
			if(hasActionErrors()) {
				initData();
				return INPUT;
			}
			
			//玩法
			if(splayids != null && splayids.length > 0) {
				StringBuffer sb = new StringBuffer();
				for(int i = 0; i < splayids.length; i++) {
					sb.append(splayids[i]).append(",");
				}
				if(sb.length() > 0)
					sb.deleteCharAt(sb.length()-1);
				
				customMade.setPlayIds(sb.toString());
			} else {
				customMade.setPlayIds("");
			}
			
			customMadeService.updateCustomMade(getUserId(), fuid, customMade);
			
			return SUCCESS;
		}
		
		return ERROR;
	}
	
	@Override
	public CustomMade getModel() {
		if(fuid != null) {
			customMade = customMadeService.getCustomMade(getUserId(), fuid);
		}
		
		if(customMade == null) {
			customMade = new CustomMade();
			customMade.setGroupBuy(true);
			customMade.setFollowBuy(true);
			customMade.setFollowedUser(userService.getUser(fuid));
			weiboUser = userAccountService.findWeiboUserByLotteryUid(fuid.longValue() + "");
		}
		
		return customMade;
	}

	private void initData() {
		allPlays = playService.listCustomMadePlays();
		
		if(StringUtils.isNotEmpty(customMade.getPlayIds())) {
			String[] ids = customMade.getPlayIds().split(",");
			for(int i = 0 ; i < ids.length; i++) {
				selectedPlayIds.add(ids[i]);
			}
		}
		
 		count = customMadeService.countCustomMades(fuid);
	}
	
	public Long getFuid() {
		return fuid;
	}

	public void setFuid(Long fuid) {
		this.fuid = fuid;
	}

	public List<Play> getAllPlays() {
		return allPlays;
	}

	public void setAllPlays(List<Play> allPlays) {
		this.allPlays = allPlays;
	}

	public Set<String> getSelectedPlayIds() {
		return selectedPlayIds;
	}

	public void setSelectedPlayIds(Set<String> selectedPlayIds) {
		this.selectedPlayIds = selectedPlayIds;
	}

	public CustomMade getCustomMade() {
		return customMade;
	}

	public void setCustomMade(CustomMade customMade) {
		this.customMade = customMade;
	}

	public String[] getSplayids() {
		return splayids;
	}

	public void setSplayids(String[] splayids) {
		this.splayids = splayids;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public WeiboUser getWeiboUser() {
		return weiboUser;
	}

	public void setWeiboUser(WeiboUser weiboUser) {
		this.weiboUser = weiboUser;
	}
}
