package com.unison.lottery.weibo.common.data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.unison.lottery.weibo.data.WeiboMsg;
import com.xhcms.commons.mq.XHMessage;

/**
 * 异步发布微博到指定时间线命令
 * 
 * @author Yang Bo
 */
public class PublishCommand implements XHMessage {
	private static final long serialVersionUID = 3292678480275030788L;
	
	private WeiboMsg weiboMsg;
	private Set<TimeLineType> destTimelines = new HashSet<>();
	
	public PublishCommand(WeiboMsg weibo, TimeLineType timeline){
		this.weiboMsg = weibo;
		this.destTimelines.add(timeline);
	}

	public void addTimelines(Collection<TimeLineType> timelines){
		this.destTimelines.addAll(timelines);
	}
	
	public WeiboMsg getWeiboMsg() {
		return weiboMsg;
	}

	public void setWeiboMsg(WeiboMsg weiboMsg) {
		this.weiboMsg = weiboMsg;
	}

	public Set<TimeLineType> getDestTimelines() {
		return destTimelines;
	}

	@Override
	public int getPriority() {
		return 0;
	}

    public String toString(){
    	return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}

	@Override
	public void setPriority(int priority) {
	}
}
