package com.xhcms.lottery.dc.task.crawl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.htmlparser.Node;

import com.xhcms.lottery.dc.data.OddsBase;

/**
 * @author xulang
 */
public abstract class AbstractZCDataWorker extends CrawlWorker {
	protected SimpleDateFormat formater = new SimpleDateFormat("yy-MM-dd HH:mm");
	
	protected void parseMatchCode(OddsBase fb, Node td) {
        String cnCode = td.toPlainTextString().trim();
        fb.setCode(toCode(cnCode));
    }
	
    protected void parseOfftime(OddsBase fb, SimpleDateFormat formater, Node td) throws ParseException {
        parseOfftime(fb, formater, td.toPlainTextString().trim());
    }
    
    protected void parseOfftime(OddsBase fb, SimpleDateFormat formater, String date) throws ParseException {
        Calendar c = parseTime(date, formater);
        fb.setOfftime(c.getTime());
    }
    
    protected Calendar parseTime(String time, SimpleDateFormat formater) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(formater.parse("20" + time));
        return c;
    }
    
    protected String parseOdd(Node td) {
    	return td.toPlainTextString().trim();
    }

}
