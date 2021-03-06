package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {

    private Integer fileId;
    private String filename;
    private String contenttype;
    private String filesize;
    private Integer userid;
    private byte[] filedata;

    public File(Integer fileId, String filename, String contenttype, String filesize, Integer userid, byte[] filedata) {
        this.fileId = fileId;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.filedata = filedata;
    }

    public Integer getFileId() {
        return fileId;
    }

    public String getFilename() {
        return filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public String getFilesize() {
        return filesize;
    }

    public Integer getUserid() {
        return userid;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }
}
