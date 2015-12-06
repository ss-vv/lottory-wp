package com.xhcms.lottery.admin.web.action.play;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.PlayService;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.Play;

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

    private static final long serialVersionUID = 9102479908229495496L;

    @Autowired
    private PlayService playService;

    private String lotteryId;

    private List<Play> list;

    @Override
    public String execute() throws Exception {

        list = playService.list(lotteryId);

        return SUCCESS;
    }

    public List<Play> getList() {
        return list;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getLotteryId() {
        return lotteryId;
    }

}
