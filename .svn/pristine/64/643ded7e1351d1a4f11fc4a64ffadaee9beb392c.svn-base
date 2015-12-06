package com.xhcms.lottery.dc.core;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataStore {
    protected Logger log = LoggerFactory.getLogger(getClass());

    private ConcurrentLinkedQueue<Entry> queue = new ConcurrentLinkedQueue<Entry>();

    @SuppressWarnings("rawtypes")
    private Map<String, Persister> persisters;

    public void put(String name, List<?> data) {
        if(data.size() > 0){
            if (!persisters.containsKey(name)) {
                log.warn("Unrecognized persister: " + name + ", data will be discarded");
            }
            queue.offer(new Entry(name, data));
            log.info("DataStore queue's size {}", queue.size());
        }
    }

    @SuppressWarnings("unchecked")
    public void persist() {
        Entry e = null;
        while((e = queue.poll()) != null){
            if(log.isDebugEnabled()){
                log.debug("persist data number: " + e.data.size());
            }
            
            try {
            	persisters.get(e.name).persist(e.data);
            } catch(Throwable exp) {
            	log.error(exp.getMessage(), exp);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public void setPersisters(Map<String, Persister> persisters) {
        this.persisters = persisters;
    }

    private class Entry {
        private String name;
        private List<?> data;

        public Entry(String name, List<?> data) {
            this.name = name;
            this.data = data;
        }
    }
}
