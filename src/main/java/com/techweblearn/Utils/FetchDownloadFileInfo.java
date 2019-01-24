package com.techweblearn.Utils;

import com.techweblearn.FileDownloadInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;


public class FetchDownloadFileInfo {

    private static FileDownloadInfo fileDownloadInfo;
    private static final String CONTENT_DISPOSITION="Content-Disposition";
    private static final String ACCEPT_RANGES="Accept-Ranges";
    private static final String CONTENT_LENGTH="Content-Length";
    private static final String CONTENT_TYPE="Content-Type";
    private static final String SHA1="X-Checksum-SHA1";
    private static final String MD5="X-Checksum-MD5";

    private static String URL;

    public static FileDownloadInfo getUrlFileInfo(String url)
    {
        URL=url;
        fileDownloadInfo=new FileDownloadInfo();
        fileDownloadInfo.setUrl(url);

        try {
            URL url1=new URL(url);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url1.openConnection();
            httpURLConnection.connect();
            Map<String,List<String>> headers=httpURLConnection.getHeaderFields();
            System.out.println(headers.toString());
            fileName(httpURLConnection.getHeaderField(CONTENT_DISPOSITION));
            fileDownloadInfo.setPartialSupported(isPartialOrResumeSupported(httpURLConnection.getHeaderField(ACCEPT_RANGES)));
            fileDownloadInfo.setContent_length(contentLength(httpURLConnection.getHeaderField(CONTENT_LENGTH)));
            fileDownloadInfo.setContent_type(httpURLConnection.getHeaderField(CONTENT_TYPE));
            checkSumSHA1(httpURLConnection.getHeaderField(SHA1));
            checkSumMD5(httpURLConnection.getHeaderField(MD5));

            return fileDownloadInfo;

        } catch (IOException e) {
            e.printStackTrace();
            return fileDownloadInfo;
        }
    }

    private static void fileName(String content_disposition)
    {
        if(content_disposition==null)
        {
            if(URL.charAt(URL.length()-1)=='/')
            {
                URL=URL.substring(0,URL.length()-1);
            }

            if(URL.lastIndexOf("?")>URL.lastIndexOf("/"))
            {
                fileDownloadInfo.setFileName(URL.substring(URL.lastIndexOf("/")+1,URL.lastIndexOf("?")));
            }
            else
            fileDownloadInfo.setFileName(URL.substring(URL.lastIndexOf("/")+1,URL.length()));
        }
        else
        {

            fileDownloadInfo.setFileName(content_disposition.substring(content_disposition.lastIndexOf("=")+1,content_disposition.length()).trim());
        }
        System.out.println(fileDownloadInfo.getFileName());
    }

    private static boolean isPartialOrResumeSupported(String accept_ranges)
    {
        if(accept_ranges==null||accept_ranges.equals("none"))
        {
            return false;
        }else return true;
    }


    private static long contentLength(String content_length)
    {
        return Long.parseLong(content_length);
    }


    private static void checkSumSHA1(String sha1)
    {
        if(sha1==null||sha1.equals("none"))
        {
            fileDownloadInfo.setHaveSHA1(false);
        }
        else
        {
            fileDownloadInfo.setHaveSHA1(true);
            fileDownloadInfo.setChecksumSHA1(sha1);
        }
    }

    private static void checkSumMD5(String md5)
    {
        if(md5==null||md5.equals("none"))
        {
            fileDownloadInfo.setHaveMD5(false);
        }
        else
        {
            fileDownloadInfo.setHaveMD5(true);
            fileDownloadInfo.setChecksumMD5(md5);
        }
    }



}
