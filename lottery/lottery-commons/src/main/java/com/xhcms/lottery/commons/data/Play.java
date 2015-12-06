package com.xhcms.lottery.commons.data;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xhcms.lottery.commons.persist.entity.PlayPO;

/**
 * 玩法。
 * {@link PlayPO}
 */
public class Play implements Serializable {

    private static final long serialVersionUID = 2010315386984460375L;

    private String id;

    private String lotteryId;

    private String name;

    private int floorRatio;

    private List<PassType> passTypes;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getFloorRatio() {
        return floorRatio;
    }

    public void setFloorRatio(int floorRatio) {
        this.floorRatio = floorRatio;
    }

    public List<PassType> getPassTypes() {
        return passTypes;
    }

    public void setPassTypes(List<PassType> passTypes) {
        this.passTypes = passTypes;
    }
    
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
