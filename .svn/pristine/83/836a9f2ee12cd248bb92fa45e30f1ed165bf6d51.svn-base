package com.unison.lottery.weibo.web.action;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.xhcms.commons.lang.Paging;

public class BaseListAction extends BaseAction {
	private static final long serialVersionUID = -5377527177409033969L;

	protected Paging paging;
	protected int pageNo = 1;
	protected int maxResults = Paging.DEFAULT_MAX_RESULTS;
	protected Date from;
    protected Date to;
    
    protected void init(){
        paging = wrapPaging();
        if(to == null){
            to = new Date(DateUtils.truncate(new Date(), Calendar.DATE).getTime());
        }
        
        if(from == null){
            from = DateUtils.addDays(to, -9);
        }
        
        Calendar cal = Calendar.getInstance();
    	cal.setTime(to);
    	cal.set(Calendar.HOUR_OF_DAY, 23);
    	cal.set(Calendar.MINUTE, 59);
    	cal.set(Calendar.SECOND, 59);
    	to = cal.getTime();
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
		this.maxResults = (maxResults < 0 ? 0 : (maxResults > 100 ? 100
				: maxResults));
	}

	protected Paging wrapPaging() {
		return new Paging(pageNo, maxResults);
	}

    public Paging getPaging() {
        return paging;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
