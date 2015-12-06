package com.unison.lottery.weibo.web.upload.progress;

public class ResourceFileUploadStatus {
	private long readedBytes = 0L;
    private long totalBytes = 0L;
    private int currentItem = 0;
   
    public long getReadedBytes() {
       return readedBytes;
    }
    public void setReadedBytes(long bytes) {
       readedBytes = bytes;
    }
    public long getTotalBytes() {
       return totalBytes;
    }
    public void setTotalBytes(long bytes) {
       totalBytes = bytes;
    }
    public int getCurrentItem() {
       return currentItem;
    }
    public void setCurrentItem(int item) {
       currentItem = item;
    }
}
