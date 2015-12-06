/**
 * 
 */
package com.xhcms.lottery.dc.task;

import java.util.HashSet;
import java.util.List;

import com.xhcms.commons.job.Job;
import com.xhcms.commons.job.Workspace;
import com.xhcms.lottery.dc.core.DataStore;
import com.xhcms.lottery.dc.task.fetch.FetchWorker;

/**
 * @author langhsu
 *
 */
public class FetchTask extends Job {
	private FetchWorker guide;
    private Workspace workspace;
    private DataStore dataStore;
    private HashSet<String> urlSet = new HashSet<String>();
    
    @Override
    public void execute() {
        synchronized(urlSet){
            urlSet.clear();
        }
        
        addWorker(guide);
    }

    public void setGuide(FetchWorker guide) {
        this.guide = guide;
        this.guide.setTask(this);
    }

    public final void addWorker(FetchWorker worker){
        String url = worker.getUrl();
        
        // 防止url重复抓取
        synchronized(urlSet){
            if(urlSet.contains(url)){
                if(log.isDebugEnabled()){
                    log.debug("Url repeat: " + url);
                }
                return;
            }
            urlSet.add(url);
        }
        worker.setTask(this);
        this.workspace.assign(worker);
    }

    public final void storeData(String name, List<?> data){
        dataStore.put(name, data);
    }
    
    public void setDataStore(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }
    
    @Override
    public String toString(){
        return "FetchTask";
    }
}
