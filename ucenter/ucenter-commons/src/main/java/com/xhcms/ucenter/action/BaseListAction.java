package com.xhcms.ucenter.action;

import org.apache.commons.lang.StringUtils;

import com.xhcms.commons.lang.Paging;

/**
 * 
 * <p>Title: 查询列表的基础Action类</p>
 * <p>Description: 包装了分页信息和查询条件</p>
 * <p>Copyright：Copyright (c) 2010</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * @author wanged
 * @version 1.0
 */
public class BaseListAction extends BaseAction {

    private static final long serialVersionUID = 8821805938013339716L;

    protected int pageNo = 1;

    protected int maxResults = Paging.DEFAULT_MAX_RESULTS;

    public void setPageNo(String pageNo) {
        if (StringUtils.isEmpty(pageNo)) {
            this.pageNo = 1;
        } else {
            try {
                this.pageNo = Integer.parseInt(pageNo);
            } catch (NumberFormatException e) {
                this.pageNo = 1;
            }
        }
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    protected Paging wrapPaging() {
        return new Paging(pageNo, maxResults);
    }

    public long getAppId() {
		String value = request.getParameter("appId");
    	
    	if(value == null)
    		return 0L;
		
    	try {
    		return Long.parseLong(value);
    	}catch(NumberFormatException ex){
    		return 0L;
    	}
	}
    
}
