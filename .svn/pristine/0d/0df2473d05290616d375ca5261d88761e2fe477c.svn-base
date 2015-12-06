package com.xhcms.lottery.admin.web.action.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.data.RegistCode;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.DaVGroupService;
import com.xhcms.lottery.commons.persist.service.RegistrationCodeService;

public class RegCodeAction extends BaseListAction {
	private static final long serialVersionUID = -386976812897936299L;
	
	@Autowired
	DaVGroupService daVGroupService;
	
	@Autowired
	private RegistrationCodeService registrationCodeService;
	
	private int status=-1;//查询条件，表示全部
	private int vid=0;//查询条件，全部
	
	List<RegistCode> registCodes;
	List<User> davusers;
	@Override
	public String execute(){
		init();
		paging.setMaxResults(100);
		registrationCodeService.listByPagger(paging,status,vid);
		davusers = daVGroupService.listAll();
		return SUCCESS;
	}
	
	private long genvid;
	private int codeMaxSize;
	private int expiryDay;
	
	public String geneRegCode(){
		registrationCodeService.genereateCodeAndSave(genvid,codeMaxSize, expiryDay);
		return SUCCESS;
	}
	
	public List<RegistCode> getRegistCodes() {
		return registCodes;
	}

	public List<User> getDavusers() {
		return davusers;
	}

	public void setGenvid(long genvid) {
		this.genvid = genvid;
	}

	public void setCodeMaxSize(int codeMaxSize) {
		this.codeMaxSize = codeMaxSize;
	}

	public void setExpiryDay(int expiryDay) {
		this.expiryDay = expiryDay;
	}
}
