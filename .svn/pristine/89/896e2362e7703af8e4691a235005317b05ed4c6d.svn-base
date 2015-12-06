package com.xhcms.lottery.dc.task.crawl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.htmlparser.Node;

import com.xhcms.lottery.dc.data.LCOdds;
import com.xhcms.lottery.dc.data.OddsBase;

public abstract class AbstractLCDataWorker extends CrawlWorker {
	protected SimpleDateFormat formater = new SimpleDateFormat("yy-MM-dd HH:mm");
	
	protected void parseMatchCode(OddsBase bb, Node td) {
        String cnCode = td.toPlainTextString().trim();
        bb.setCode(toCode(cnCode));
    }
	
	protected void parseOfftime(OddsBase bb, SimpleDateFormat formater, Node td) throws ParseException {
		Calendar c = Calendar.getInstance();
        c.setTime(formater.parse("20" + td.toPlainTextString().trim()));
        bb.setOfftime(c.getTime());
    }
    
    protected String parseOdd(Node td) {
    	return td.toPlainTextString().trim();
    }
    
    protected void parseDefaultScore(LCOdds lc, Node td) {
    	String score = td.toPlainTextString().trim();
    	lc.setDefaultScore( Float.parseFloat(score));
    }
}
