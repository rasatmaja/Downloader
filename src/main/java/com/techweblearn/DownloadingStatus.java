package com.techweblearn;

import java.util.ArrayList;

public class DownloadingStatus {

   private long downloaded;
   private int noOfParts;
   private ArrayList<Long>individualPartDownloadedStatus;

    public long getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(long downloaded) {
        this.downloaded = downloaded;
    }

    public int getNoOfParts() {
        return noOfParts;
    }

    public void setNoOfParts(int noOfParts) {
        this.noOfParts = noOfParts;
    }

    public ArrayList<Long> getIndividualPartDownloadedStatus() {
        return individualPartDownloadedStatus;
    }

    public void setIndividualPartDownloadedStatus(ArrayList<Long> individualPartDownloadedStatus) {
        this.individualPartDownloadedStatus = individualPartDownloadedStatus;
    }
}
