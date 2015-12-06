package com.xhcms.lottery.admin.web.action.playoption;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.PlayOptionService;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.PlayOption;

/**
 * <p>Title: 跳转到页面</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public class ListAction extends BaseAction {

    private static final long serialVersionUID = 5866969643245566988L;

    @Autowired
    private PlayOptionService playOptionService;

    private String lotteryId;
    
    private String playId;

    private List<PlayOption> list;

    @Override
    public String execute() throws Exception {

        list = playOptionService.list(playId);

        return SUCCESS;
    }

    public List<PlayOption> getList() {
        return list;
    }

    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

}
