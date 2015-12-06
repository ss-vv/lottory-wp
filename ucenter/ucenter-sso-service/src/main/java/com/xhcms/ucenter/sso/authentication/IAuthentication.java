/**
 * 
 */
package com.xhcms.ucenter.sso.authentication;

import java.util.Date;
import java.util.Map;

import com.xhcms.ucenter.sso.principal.IPrincipal;


/**
 * @author bean
 *
 */
public interface IAuthentication {
    public IPrincipal getPrincipal();
    public Date getAuthenticatedDate();
    public Map<String, Object> getAttributes();
}
