package com.techweblearn;

import java.util.ArrayList;

public class PartialDownloadTasks {


    private static long start;
    private static long end;
    private static long sizeOfOnePart;

    public static DownloadTask[] getPartialDownloadTasks(FileDownloadInfo fileDownloadInfo,int noOfThreads)
    {
        end=fileDownloadInfo.getContent_length()-1;
        DownloadTask[]downloadTasks=new DownloadTask[noOfThreads];
        sizeOfOnePart=fileDownloadInfo.getContent_length()/noOfThreads;
        start=-1;
        end=-1;

        for (int i=0;i<noOfThreads;i++)
        {
            start=end+1;
            end=start+sizeOfOnePart-1;

            DownloadTask downloadTask=new DownloadTask();
            downloadTask.setEndRange(end);
            downloadTask.setStartRange(start);
            downloadTask.setUrl(fileDownloadInfo.getUrl());
            downloadTask.setFilename(fileDownloadInfo.getFileName()+"PART_00"+i);
            downloadTasks[i]=downloadTask;
        }


        downloadTasks[noOfThreads-1].setEndRange(fileDownloadInfo.getContent_length()-1);


        return downloadTasks;

    }

    public static DownloadTask[]getResumePartialDownloadTasks(ArrayList<Long>downloadedPartsInfo,FileDownloadInfo fileDownloadInfo,int noOfThreads)
    {
        end=fileDownloadInfo.getContent_length()-1;
        DownloadTask[]downloadTasks=new DownloadTask[noOfThreads];
        sizeOfOnePart=fileDownloadInfo.getContent_length()/noOfThreads;
        start=-1;
        end=-1;

        for (int i=0;i<noOfThreads;i++)
        {
            start=end+1;
            end=start+sizeOfOnePart-1;

            DownloadTask downloadTask=new DownloadTask();
            downloadTask.setEndRange(end);
            downloadTask.setStartRange(start+downloadedPartsInfo.get(i));
            downloadTask.setUrl(fileDownloadInfo.getUrl());
            downloadTask.setFilename(fileDownloadInfo.getFileName()+"PART_00"+i);
            downloadTask.setDownloaded(downloadedPartsInfo.get(i));
            downloadTasks[i]=downloadTask;
        }

        return downloadTasks;
    }

}
