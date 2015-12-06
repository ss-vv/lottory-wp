/**
 * 
 */
package com.xhcms.lottery.dc.task.fetch;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.xhcms.commons.job.Job;
import com.xhcms.lottery.commons.client.HttpClient;
import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.dc.task.FetchTask;
import com.xhcms.lottery.dc.task.ticket.OpenSaleIntercessor;

/**
 * @author langhsu
 * 
 */
public abstract class FetchWorker extends Job {
	private FetchTask task;
	private OpenSaleIntercessor openSaleIntercessor;
	private String url;
	private String encoding = "utf-8";
	private int haltSales = -5;
	private int increment = 1;
	private int days = 0;
	
	protected SimpleDateFormat formater = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
	
	@Override
	public void execute() throws Exception {
	    if(increment == 0 && days == 0){
	        if(log.isDebugEnabled()){
                log.debug("get url --> " + url);
            }
            getRequest(url);
	    }else{
    		// 构造HttpClient的实例
    		Calendar today = Calendar.getInstance();
    		
    		if (increment != 0) {
    			String reqpath = url + "?date=" +DateFormatUtils.format(today, "yyyyMMdd");
    			if(log.isDebugEnabled()){
    				log.debug("get url --> " + reqpath);
    			}
    			getRequest(reqpath);
    		}
    		int d = days;
    		while(d != 0) {
    			today.add(Calendar.DATE,increment);
    			String reqpath = url + "?date=" +DateFormatUtils.format(today, "yyyyMMdd");
    			if(log.isDebugEnabled()){
    				log.debug("get url --> " + reqpath);
    			}
    			getRequest(reqpath);
    			d--;
    		}
		}
	}
	
	private void getRequest(String url) throws HttpException, IOException, DocumentException {
		HttpClient client = new HttpClient();
		String response = client.get(url, null);
		if (response != null) {
			SAXReader reader = new SAXReader();
			reader.setEncoding(encoding);
			Document doc = reader.read(new ByteArrayInputStream(response.getBytes()));
			parser(doc.getRootElement());
		}
	}

	public abstract void parser(Element root);

	public void destroy() {
		this.task = null;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
	
	public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getEncoding() {
        return encoding;
    }

	public void setTask(FetchTask task) {
		this.task = task;
	}

	@Override
	public String toString() {
		return "FetchWorker";
	}

	protected void addWorker(FetchWorker worker) {
		task.addWorker(worker);
	}

	protected void storeData(String name, List<?> data) {
		task.storeData(name, data);
	}
	
	public void setHaltSales(int haltSales) {
		this.haltSales = haltSales;
	}
	
	public void setIncrement(int increment) {
		this.increment = increment;
	}

	public void setDays(int days) {
		this.days = Math.abs(days);
	}

	public void setOpenSaleIntercessor(OpenSaleIntercessor openSaleIntercessor) {
		this.openSaleIntercessor = openSaleIntercessor;
	}

	protected String toCode(String cnCode) {
        String code = null;
        if (cnCode.startsWith("周一")) {
            code = "1";
        } else if (cnCode.startsWith("周二")) {
            code = "2";
        } else if (cnCode.startsWith("周三")) {
            code = "3";
        } else if (cnCode.startsWith("周四")) {
            code = "4";
        } else if (cnCode.startsWith("周五")) {
            code = "5";
        } else if (cnCode.startsWith("周六")) {
            code = "6";
        } else {
            code = "7";
        }
        
        return code + cnCode.substring(2);
    }
	
	/*
	 *  停售截止时间从目前赛事赛前35分钟修改为赛事赛前11分钟截止 
	 *  与接口出票对接时间修改如下：
	 *  例如周一、二、三、四、五为开始时间9:00 截止时间23：49分
	 *  周六、日为开始时间9:00 截止时间次日0:49 
	 *  
	 */
	protected void parseOfftime(String lotteryId, Match bb, String time) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(formater.parse(time));
        
        bb.setOfftime(c.getTime());

        // 开赛时间
        c.add(Calendar.MINUTE, 1);
        bb.setPlayingTime(c.getTime());

        // 停售时间
        c.add(Calendar.MINUTE, haltSales);
        bb.setEntrustDeadline(openSaleIntercessor.parseEntrustDeadline(lotteryId, c.getTime()));
    }
}
