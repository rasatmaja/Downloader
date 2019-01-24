package com.techweblearn;

public interface PartDownloadListener {

    void update(long downloaded,int partNo);
    void completed(int partNo);
    void error(int partNo);
    void pause(int partNo,long downloaded);
    void error(int code,String message,int partno);


}
