package com.udacity.jwdnd.course1.cloudstorage.model;


public class Credential {

    private Integer credentialId;
    private String url;
    private String username;
    private String salt;
    private String password;
    private Integer userid;

    public Credential(Integer credentialId, String url, String username, String salt, String password, Integer userid) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.salt = salt;
        this.password = password;
        this.userid = userid;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public String getSalt() {
        return salt;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
