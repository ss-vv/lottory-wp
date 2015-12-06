package com.xhcms.lottery.commons.event;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xhcms.commons.mq.XHMessage;

public class BuyTicketMessage implements XHMessage {

    private static final long serialVersionUID = -8649422773498947338L;
    
    private int priority;
    
    private List<Long> schemes;
    
    private String lotteryId;
    
    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String getType() {
        return getClass().getSimpleName();
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<Long> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<Long> schemes) {
        this.schemes = schemes;
    }

    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }
    
    public String toString(){
    	return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
