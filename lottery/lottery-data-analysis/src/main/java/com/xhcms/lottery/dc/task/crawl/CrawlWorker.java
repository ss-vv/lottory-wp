package com.xhcms.lottery.dc.task.crawl;

import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;

import com.xhcms.commons.job.Job;
import com.xhcms.lottery.dc.task.CrawlTask;

public abstract class CrawlWorker extends Job {
    private CrawlTask task;
    private String url;
    private String encoding = "gbk";

    @Override
    public void execute() throws Exception {
        Parser parser = new Parser(url);
        parser.setEncoding(encoding);
        crawl(parser);
    }

    public abstract void crawl(Parser parser) throws ParserException;

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

    public void setTask(CrawlTask task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "crawlWorker";
    }

    protected void addWorker(CrawlWorker worker) {
        task.addWorker(worker);
    }

    protected void storeData(String name, List<?> data) {
        task.storeData(name, data);
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
}
