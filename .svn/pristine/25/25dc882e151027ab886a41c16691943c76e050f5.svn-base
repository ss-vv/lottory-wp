package com.xhcms.lottery.commons.event;

import com.xhcms.commons.mq.XHMessage;

public class SMSSendMessage implements XHMessage {

    private static final long serialVersionUID = -2773872338716463656L;

    private int priority;

    /**
     * 发送的手机号码
     * 多个号码用半角的逗号隔开
     */
    private String mobile;

    /**
     * 短信内容
     * 短信内容长度不超过500个汉字
     * 每个英文或阿拉伯字符也算1个汉字
     */
    private String content;

    /**
     * 用户发送短信自己定义的短信id用于处理群发状态报告 可以为空
     * 英文和数字的组合总长度不超过50个字节
     */
    private String msgId;

    /**
     * 用户自己分配的小号（即显示在接收手机上的主叫尾号，可用于上行信息匹配）
     * 从00000到99999的范围
     */
    private String ext;

    @Override
    public String getType() {
        return getClass().getSimpleName();
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

}
