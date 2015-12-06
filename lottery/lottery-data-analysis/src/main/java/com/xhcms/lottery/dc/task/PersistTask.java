package com.xhcms.lottery.dc.task;

import com.xhcms.commons.job.Job;
import com.xhcms.lottery.dc.core.DataStore;

public class PersistTask extends Job {

    private DataStore dataStore;

    @Override
    public void execute() {
        dataStore.persist();
    }

    public void setDataStore(DataStore dataStore) {
        this.dataStore = dataStore;
    }
    
    @Override
    public String toString(){
        return "persistTask";
    }
}
