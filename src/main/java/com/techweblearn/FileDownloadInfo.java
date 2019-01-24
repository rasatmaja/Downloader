package com.techweblearn;

public class FileDownloadInfo {


    private String fileName;
    private String url;
    private long content_length;
    private String content_type;
    private boolean isPartialSupported;
    private String checksumSHA1;
    private String checksumMD5;
    private String content_range;
    private boolean isHaveMD5;
    private boolean isHaveSHA1;
    private String savePath;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getContent_length() {
        return content_length;
    }

    public void setContent_length(long content_length) {
        this.content_length = content_length;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public boolean isPartialSupported() {
        return isPartialSupported;
    }

    public void setPartialSupported(boolean partialSupported) {
        isPartialSupported = partialSupported;
    }

    public String getChecksumSHA1() {
        return checksumSHA1;
    }

    public void setChecksumSHA1(String checksumSHA1) {
        this.checksumSHA1 = checksumSHA1;
    }

    public String getChecksumMD5() {
        return checksumMD5;
    }

    public void setChecksumMD5(String checksumMD5) {
        this.checksumMD5 = checksumMD5;
    }

    public String getContent_range() {
        return content_range;
    }

    public void setContent_range(String content_range) {
        this.content_range = content_range;
    }

    public boolean isHaveMD5() {
        return isHaveMD5;
    }

    public void setHaveMD5(boolean haveMD5) {
        isHaveMD5 = haveMD5;
    }

    public boolean isHaveSHA1() {
        return isHaveSHA1;
    }

    public void setHaveSHA1(boolean haveSHA1) {
        isHaveSHA1 = haveSHA1;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    @Override
    public String toString() {
        return "FileDownloadInfo{" +
                "fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                ", content_length=" + content_length +
                ", content_type='" + content_type + '\'' +
                ", isPartialSupported=" + isPartialSupported +
                ", checksumSHA1='" + checksumSHA1 + '\'' +
                ", checksumMD5='" + checksumMD5 + '\'' +
                ", content_range='" + content_range + '\'' +
                ", isHaveMD5=" + isHaveMD5 +
                ", isHaveSHA1=" + isHaveSHA1 +
                '}';
    }
}
