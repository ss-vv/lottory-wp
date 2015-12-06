package com.unison.lottery.wap.action;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.xhcms.commons.lang.Paging;
import com.xhcms.ucenter.action.BaseAction;

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

    protected Paging paging;
	protected Date from;
    protected Date to;
    
    protected int pageNo = 1;

    protected int maxResults = Paging.DEFAULT_MAX_RESULTS;

    protected void init(){
        paging = wrapPaging();
        if(to == null){
            to = new Date(DateUtils.truncate(new Date(), Calendar.DATE).getTime() + 86400000);
        }
        if(from == null){
            from = DateUtils.addDays(to, -30);
        }
    }
    
    
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
	public Paging getPaging(){
		return paging;
	}
}
