package com.chinaxiaopu.xiaopuMobi.model;

public class School extends BaseEntity{

    private String name;

    private Integer areaCode;

    private String areaName;

    private String address;

    private String lat;

    private String lon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat == null ? null : lat.trim();
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon == null ? null : lon.trim();
    }
}