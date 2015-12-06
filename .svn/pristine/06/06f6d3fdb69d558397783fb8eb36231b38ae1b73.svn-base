package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "lt_pass_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PassTypePO implements Serializable {

    private static final long serialVersionUID = 8389325343200653270L;

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    private int note;

    private int index;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
}
