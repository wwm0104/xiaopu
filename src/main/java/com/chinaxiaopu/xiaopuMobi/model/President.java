package com.chinaxiaopu.xiaopuMobi.model;

import java.util.List;

public class President extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer schoolId;

    private String schoolName;

    private Long mobile;

    private String nickName;

    private String realName;

    private String studentNo;

    private String qq;

    private Integer valid;

    private Integer isPresident;


    private String avatarUrl;

    private List<String> groupNameList;

    private String allGroup;


    public String getAllGroup() {
        return allGroup;
    }

    public void setAllGroup(String allGroup) {
        this.allGroup = allGroup;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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
        this.schoolName = schoolName;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getIsPresident() {
        return isPresident;
    }

    public void setIsPresident(Integer isPresident) {
        this.isPresident = isPresident;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<String> getGroupNameList() {
        return groupNameList;
    }

    public void setGroupNameList(List<String> groupNameList) {
        this.groupNameList = groupNameList;
    }

}
