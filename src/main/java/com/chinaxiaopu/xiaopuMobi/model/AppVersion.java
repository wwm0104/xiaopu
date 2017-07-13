package com.chinaxiaopu.xiaopuMobi.model;

public class AppVersion extends AbstractModelWithImgs {
    private Integer id;

    private String appId;

    private String appName;

    private String verison;

    private String useragent;

    private String useragentName;

    private String downloadUrl;

    private String description;

    private Integer isUpdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getVerison() {
        return verison;
    }

    public void setVerison(String verison) {
        this.verison = verison == null ? null : verison.trim();
    }

    public String getUseragent() {
        return useragent;
    }

    public void setUseragent(String useragent) {
        this.useragent = useragent == null ? null : useragent.trim();
    }

    public String getUseragentName() {
        return useragentName;
    }

    public void setUseragentName(String useragentName) {
        this.useragentName = useragentName == null ? null : useragentName.trim();
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Integer isUpdate) {
        this.isUpdate = isUpdate;
    }
}