package com.chinaxiaopu.xiaopuMobi.model;

public class Channel {
    private Integer id;

    private String name;

    private String slogan;

    private String desc;

    private String posterImg;

    private Integer sort;

    private Integer more;

    private Integer type;

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getMore() {
        return more;
    }

    public void setMore(Integer more) {
        this.more = more;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}