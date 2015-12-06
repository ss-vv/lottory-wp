package com.xhcms.lottery.commons.data;

import java.io.Serializable;

public class PassType implements Serializable {

    private static final long serialVersionUID = -5785893692091967325L;

    private String id;

    private String name;

    private int note;

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

}
