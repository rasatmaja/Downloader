package com.techweblearn;


import java.util.ArrayList;

public class Main {

    //Accept-Ranges: none // When No Partial Request
    public static void main(String[] args) {

        String URL_FILE = "https://az764295.vo.msecnd.net/stable/61122f88f0bf01e2ac16bdb9e1bc4571755f5bd8/VSCodeUserSetup-x64-1.30.2.exe";
        Downloader downloader = new Downloader(URL_FILE,4);

        downloader.setDownloadStatusListener(new DownloadListener() {
            @Override
            public void update(long downloaded, int speed) {
                System.out.println((1.0f * downloaded/downloader.getContent_length())*100);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onPause(ArrayList<Long> downloaded) {
                System.out.println(downloaded);
            }

            @Override
            public void onPartError(int code, String message, int partNo) {

            }

            @Override
            public void onError(String message) {
                System.out.println(message);
            }

            @Override
            public void onPartStatus(long downloaded, int partNo) {

            }

            @Override
            public void onPartCompleted(int partNo) {

            }
        });
        downloader.startDownload();

    }
}
