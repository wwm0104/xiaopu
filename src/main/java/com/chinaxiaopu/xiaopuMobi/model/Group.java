package com.chinaxiaopu.xiaopuMobi.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class Group extends AbstractModelWithImgs{

    private Integer schoolId;

    private String schoolName;

    private String name;

    private String categoryId;

    private String categoryName;

    private String slogan;

    private String description;

    private Integer presidentId;

    private String presidentName;

    @Setter @Getter
    private UserInfo userInfo;

    private String logoImgUrl;

    private Integer status;

    private Integer cnt;

    @Setter @Getter
    private String shareUrl;



    /**
     * 删除社团id参数列表
     * ycy
     */
    private List<Long> idList;
    
    @Setter @Getter
    private String createrStatus;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Date joinTime;

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan == null ? null : slogan.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getPresidentId() {
        return presidentId;
    }

    public void setPresidentId(Integer presidentId) {
        this.presidentId = presidentId;
    }

    public String getPresidentName() {
        return presidentName;
    }

    public void setPresidentName(String presidentName) {
        this.presidentName = presidentName == null ? null : presidentName.trim();
    }

    public String getLogoImgUrl() {
        return logoImgUrl;
    }

    public void setLogoImgUrl(String logoImgUrl) {
        this.logoImgUrl = logoImgUrl == null ? null : logoImgUrl.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

	
	

}