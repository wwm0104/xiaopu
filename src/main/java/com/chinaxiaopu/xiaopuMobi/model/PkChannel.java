package com.chinaxiaopu.xiaopuMobi.model;

public class PkChannel extends BaseEntity{
    private Integer id;

    private String name;

    private String slogan;

    private String desc;

    private String posterImg;

    private Integer type;

    private Integer createId;

    private String createRealname;

    private Integer status;

    private Integer isOfficial;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan == null ? null : slogan.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getPosterImg() {
        return posterImg;
    }

    public void setPosterImg(String posterImg) {
        this.posterImg = posterImg == null ? null : posterImg.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public String getCreateRealname() {
        return createRealname;
    }

    public void setCreateRealname(String createRealname) {
        this.createRealname = createRealname == null ? null : createRealname.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(Integer isOfficial) {
        this.isOfficial = isOfficial;
    }
}