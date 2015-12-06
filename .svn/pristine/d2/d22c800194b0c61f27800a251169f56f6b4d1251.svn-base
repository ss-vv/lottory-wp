package com.xhcms.lottery.dc.task.ticket;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.xhcms.lottery.conf.SystemConf;
import com.xhcms.lottery.commons.persist.dao.IssueInfoDao;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.LotteryId;

public class OpenSaleIntercessor {
    private SaleTime[] fbSaleTime = new SaleTime[7];
    private SaleTime[] bbSaleTime = new SaleTime[7];
	private List<String> highFrequenceLotteryIdList=new ArrayList<String>();
	
	@Autowired
	private IssueInfoDao issueInfoDao;
	private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private SystemConf systemConf;
    
    /**
     * 检查当前时间，彩票id为lotteryId的彩种是否允许出票。
     * 注：针对中民接口
     */
	@Transactional
    public boolean isAllowable(String lotteryId) {
    	return isAllowable(lotteryId, new Date());
    }
    
    /**
     * 检查在targetDate时，彩票id为lotteryId的彩种是否允许
     * 出票
     * 注：针对中民接口
     */
	@Transactional
    public boolean isAllowable(String lotteryId,Date targetDate){
    	if(isHighFrequence(lotteryId)){
    		return isAllowable4HF(lotteryId,targetDate);
    	}
    	else{
    		// TODO: add for BJDC
    		return isAllowable4JC(lotteryId,targetDate);
    	}
    }

    /**
     * 判断高频彩是否处于可以购买的时间
     * @param lotteryId
     * @param targetDate 
     * @return
     */
    @Transactional
	private boolean isAllowable4HF(String lotteryId, Date targetDate) {
		boolean result=false;
		if(null!=targetDate){
			IssueInfoPO validBuyTicketTime = issueInfoDao.findValidBuyTicketTimeByLotteryId(lotteryId, targetDate);
			if(null!=validBuyTicketTime){
				result=betweenStartTimeAndZMCloseTime(targetDate, validBuyTicketTime)
						||equalsStartTimeOrZMCloseTime(targetDate,
								validBuyTicketTime);
			}
		}
		
		return result;
	}

	private boolean equalsStartTimeOrZMCloseTime(Date targetDate,
			IssueInfoPO validBuyTicketTime) {
		return targetDate.equals(validBuyTicketTime.getStartTime())
				||targetDate.equals(validBuyTicketTime.getZMCloseTime());
	}

	private boolean betweenStartTimeAndZMCloseTime(Date targetDate,
			IssueInfoPO validBuyTicketTime) {
		return targetDate.after(validBuyTicketTime.getStartTime())
				&& targetDate.before(validBuyTicketTime.getZMCloseTime());
	}

	/**
	 * 判断竞彩是否处于可以购买的时间
	 * @param lotteryId
	 * @param targetDate 
	 * @return
	 */
	private boolean isAllowable4JC(String lotteryId, Date targetDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(targetDate);

        long curTime = c.getTimeInMillis(); // 当前时间
        c = DateUtils.truncate(c, Calendar.DATE);
        int week = c.get(Calendar.DAY_OF_WEEK);
        long offset = curTime - c.getTimeInMillis();// 相对当天0点的毫秒数

        int code = match(lotteryId, week, offset,true);
        if (code != -1) {
            return (code == 0);
        }
        // 看跨天的配置(从昨天开始的)
        return (match(lotteryId, (week + 6) % 7, offset + 86400000,true) == 0);
	}
	
	/**
	 * 是否为高频彩的id
	 * @param lotteryId
	 * @return
	 */
	private boolean isHighFrequence(String lotteryId) {
		return StringUtils.isNotBlank(lotteryId)
				&& highFrequenceLotteryIdList.contains(lotteryId);
	}

	/**
	 * 根据比赛开始时间计算投注截止时间。
	 */
	public Date parseEntrustDeadline(String lotteryId, Date playTime) {
		
		// 根据彩种调用不同处理函数
		LotteryId lottery = LotteryId.valueOf(lotteryId);
		switch(lottery){
			case BJDC:
			case BDSF:
				return parseEntrustDeadlineBD(lotteryId, playTime);
			default:
				return parseEntrustDeadlineDefault(lotteryId, playTime);
		}
	}
	
	// TODO: 计算北单比赛的截止投票时间
	private Date parseEntrustDeadlineBD(String lotteryId, Date playTime){
		Calendar c = Calendar.getInstance();
		c.setTime(playTime);
		long curTime = c.getTimeInMillis(); //比赛时间
		//获取北单的需要的间隔时间
		long time=systemConf.getLongValueByKey(SystemConf.CLOSETIME.BJDC_BEFORE_CLOSE_SECOND);
		//截止投票时间=比赛时间-time-1分钟
		c.setTimeInMillis(curTime-time*1000-60*1000);
		return c.getTime();
	}
	
	private Date parseEntrustDeadlineDefault(String lotteryId, Date playTime){
		Calendar c = Calendar.getInstance();
		c.setTime(playTime);
		long curTime = c.getTimeInMillis(); // 当前时间
		c = DateUtils.truncate(c, Calendar.DATE);
		int week = c.get(Calendar.DAY_OF_WEEK);
		long offset = curTime - c.getTimeInMillis();// 相对当天0点的毫秒数

		int code = match(lotteryId, week, offset);

		if (code == 0) {
			return playTime;
		} else if (code == 1) {
			return completeTime(c.getTime(), endTime(lotteryId, week));
		} else {
			int precode = match(lotteryId, (week + 6) % 7, offset + 86400000);
			if (precode == 0) {
				return playTime;
			} else {
				c.add(Calendar.DAY_OF_MONTH, -1);
				return completeTime(c.getTime(), endTime(lotteryId, (week + 6) % 7));
			}
		}
	}

    private int match(String lotteryId, int week, long offset) {
        return match(lotteryId, week, offset, false);
    }
    
    private int match(String lotteryId, int week, long offset,boolean extend) {
    	SaleTime st = null;
    	if (Constants.JCLQ.equals(lotteryId)) {
    		st = bbSaleTime[(week + 7 - Calendar.MONDAY) % 7];
    	}
    	if (Constants.JCZQ.equals(lotteryId)) {
    		st = fbSaleTime[(week + 7 - Calendar.MONDAY) % 7];
    	}
    	if (st == null) {
    		return 0;
    	}
    	long newEnd = st.end;
    	if(extend){
    		long extendTime = systemConf.getLongValueByKey(SystemConf.ISSUE.EXTEND_SECOND);
    		newEnd = st.end + extendTime * 1000;
    		logger.info("lotteryId:" + lotteryId + ",xx:" + st.xx + ",yy:" + st.yy);
    		logger.info("START:" + st.start +",ISSUE_EXTEND_MILLISECOND:{},END:{},offset:"+offset,extendTime*1000,String.valueOf(newEnd));
    	}
    	if (offset >= st.start && offset <= newEnd) {
    		logger.info("Allow the issuer");
    		return 0;
    	}
    	if (offset < st.start) {
    		return -1;
    	}
    	return 1;
    }

    private long endTime(String lotteryId, int week) {
    	SaleTime st = null;
        if (Constants.JCLQ.equals(lotteryId)) {
            st = bbSaleTime[(week + 7 - Calendar.MONDAY) % 7];
        }
        if (Constants.JCZQ.equals(lotteryId)) {
            st = fbSaleTime[(week + 7 - Calendar.MONDAY) % 7];
        }
    	return st.end;
    }
    
	/**
	 * 补全日期和时间
	 * @param date	日期
	 * @param time	相隔于0点的毫秒数
	 * @return
	 */
	private Date completeTime(Date date, long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime() + time);
		return c.getTime();
	}
    
    public void setFbSaleTime(String[] values) {
        setValue(this.fbSaleTime, values);
    }

    public void setBbSaleTime(String[] values) {
        setValue(this.bbSaleTime, values);
    }

    private void setValue(SaleTime[] st, String[] values) {
        for (int i = 0, len = Math.max(st.length, values.length); i < len; i++) {
            st[i] = new SaleTime(values[i]);
        }
    }

    private class SaleTime {
        long start;
        long end;
        long xx;
        long yy;
        
        public SaleTime(String v) {
            String[] vs = v.split("-");
            long x = Long.parseLong(vs[0]);
            xx= x;
            long y = Long.parseLong(vs[1]);
            yy=y;
            x = ((x / 100) * 60 + (x % 100)) * 60000;
            y = ((y / 100) * 60 + (y % 100)) * 60000;

            this.start = x;
            if (x < y) {
                this.end = y;
            } else {
                this.end = y + 86400000;
            }
        }
    }

	public List<String> getHighFrequenceLotteryIdList() {
		return highFrequenceLotteryIdList;
	}

	public void setHighFrequenceLotteryIdList(
			List<String> highFrequenceLotteryIdList) {
		this.highFrequenceLotteryIdList = highFrequenceLotteryIdList;
	}

	public IssueInfoDao getIssueInfoDao() {
		return issueInfoDao;
	}

	public void setIssueInfoDao(IssueInfoDao issueInfoDao) {
		this.issueInfoDao = issueInfoDao;
	}

}
