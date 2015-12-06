package com.xhcms.ucenter.sso.ticket;

import com.xhcms.ucenter.sso.principal.IPrincipal;


public interface IService extends IPrincipal {
	public static final String SERVICE_URL = "_service_url_";
	public static final String IP = "_ip_";
	public static final String CHECK_DATE = "_check_date_";
	public static final String TICKET = "_ticket_";
	
	public String getId();
	public void setId(String id);
	
	/**
	 * 从当前服务上退出登录
	 * @return
	 */
    boolean logOutOfService();
    
    public Object getAtrribute(String name);
    
    public void setAttribute(String name, Object value);
}
