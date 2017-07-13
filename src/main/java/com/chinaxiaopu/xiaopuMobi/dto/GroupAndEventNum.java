package com.chinaxiaopu.xiaopuMobi.dto;

import com.chinaxiaopu.xiaopuMobi.model.Group;

import java.util.List;

/**
 * Created by ycy on 2016/10/19.
 */
public class GroupAndEventNum {

    private Long allGroupNum;

    private Long allEventNum;

    private Long groupNum;

    private Long eventNum;

    private List<Group> indexGroup;

    private List<EventDto> indexEvent;

    private Integer topicNum;

    private Integer groupPartner;

    private Integer schoolPartner;

    public Integer getGroupPartner() {
        return groupPartner;
    }

    public void setGroupPartner(Integer groupPartner) {
        this.groupPartner = groupPartner;
    }

    public Integer getSchoolPartner() {
        return schoolPartner;
    }

    public void setSchoolPartner(Integer schoolPartner) {
        this.schoolPartner = schoolPartner;
    }

    public Integer getTopicNum() {
        return topicNum;
    }

    public void setTopicNum(Integer topicNum) {
        this.topicNum = topicNum;
    }

    public List<Group> getIndexGroup() {
        return indexGroup;
    }

    public void setIndexGroup(List<Group> indexGroup) {
        this.indexGroup = indexGroup;
    }

    public List<EventDto> getIndexEvent() {
        return indexEvent;
    }

    public void setIndexEvent(List<EventDto> indexEvent) {
        this.indexEvent = indexEvent;
    }

    public Long getAllGroupNum() {
        return allGroupNum;
    }

    public void setAllGroupNum(Long allGroupNum) {
        this.allGroupNum = allGroupNum;
    }

    public Long getAllEventNum() {
        return allEventNum;
    }

    public void setAllEventNum(Long allEventNum) {
        this.allEventNum = allEventNum;
    }

    public Long getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Long groupNum) {
        this.groupNum = groupNum;
    }

    public Long getEventNum() {
        return eventNum;
    }

    public void setEventNum(Long eventNum) {
        this.eventNum = eventNum;
    }
}
