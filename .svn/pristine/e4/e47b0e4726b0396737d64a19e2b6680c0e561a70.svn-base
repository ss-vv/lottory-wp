package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "lt_play")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlayPO implements Serializable {

    private static final long serialVersionUID = 9123184446553911053L;

    @Id
    private String id;

    @Column(nullable = false, name = "lottery_id")
    private String lotteryId;

    @Column(nullable = false)
    private String name;

    @Column(name = "floor_ratio")
    private int floorRatio;

    @OneToMany
    @JoinTable(
        name = "lt_play_pass_type",
        joinColumns = { @JoinColumn(name = "play_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "pass_type_id", referencedColumnName = "id") }
    )
    @OrderBy(value = "index")
    private List<PassTypePO> passTypes;

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
    
    public List<PassTypePO> getPassTypes() {
        return passTypes;
    }

    public void setPassTypes(List<PassTypePO> passTypes) {
        this.passTypes = passTypes;
    }

}
