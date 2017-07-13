package com.chinaxiaopu.xiaopuMobi.dto;

import com.chinaxiaopu.xiaopuMobi.model.Event;
import com.chinaxiaopu.xiaopuMobi.model.EventGroup;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class EventDto extends Event{

	private String[] furtherList;

	private String timeStatus;

	private String schoolId;

	private String schoolName;

	private String realName;

	private String mobile;

	private String qq;

	private String studentNo;

	private Integer userId;

	private String allGroup;

	private String joinGroup;

	private List<Integer> groupIdList;

	private String statusName;

	private String timeStatusName;

	private List<EventGroup> eventGroupList;

	@Setter @Getter
	private Integer ticket;
	@Setter @Getter
	private Integer ticketCnt;

	public String[] getFurtherList() {
		return furtherList;
	}

	public void setFurtherList(String[] furtherList) {
		this.furtherList = furtherList;
	}

	public List<EventGroup> getEventGroupList() {
		return eventGroupList;
	}

	public void setEventGroupList(List<EventGroup> eventGroupList) {
		this.eventGroupList = eventGroupList;
	}

	public String getTimeStatusName() {
		return timeStatusName;
	}

	public void setTimeStatusName(String timeStatusName) {
		this.timeStatusName = timeStatusName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public List<Integer> getGroupIdList() {
		return groupIdList;
	}

	public void setGroupIdList(List<Integer> groupIdList) {
		this.groupIdList = groupIdList;
	}

	public String getJoinGroup() {
		return joinGroup;
	}

	public void setJoinGroup(String joinGroup) {
		this.joinGroup = joinGroup;
	}

	public String getAllGroup() {
		return allGroup;
	}

	public void setAllGroup(String allGroup) {
		this.allGroup = allGroup;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getTimeStatus() {
		return timeStatus;
	}

	public void setTimeStatus(String timeStatus) {
		this.timeStatus = timeStatus;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}



}
