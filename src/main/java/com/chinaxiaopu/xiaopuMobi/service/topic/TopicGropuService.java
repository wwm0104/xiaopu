package com.chinaxiaopu.xiaopuMobi.service.topic;

import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicGroupDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicUserInfoDto;
import com.chinaxiaopu.xiaopuMobi.mapper.EventGroupMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.GroupMemberMapper;
import com.chinaxiaopu.xiaopuMobi.model.Event;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by ycy on 2016/11/11.
 */
@Service
public class TopicGropuService {

    @Autowired
    private EventGroupMapper eventGroupMapper;

    @Autowired
    private GroupMemberMapper groupMemberMapper;

    public List<Event> selectTopicEventInGroup(TopicGroupDto topicGroupDto) throws Exception{
        if (!StringUtils.isEmpty(topicGroupDto.getPage()) && !StringUtils.isEmpty(topicGroupDto.getRows())) {
            PageHelper.startPage(topicGroupDto.getPage(), topicGroupDto.getRows());
        }
        return eventGroupMapper.selectEventByGroupId(topicGroupDto);
    }

    public List<TopicUserInfoDto> selectNotReviewMembersInGroup(TopicGroupDto topicGroupDto) throws Exception{
//        if (!StringUtils.isEmpty(topicGroupDto.getPage()) && !StringUtils.isEmpty(topicGroupDto.getRows())) {
//            PageHelper.startPage(topicGroupDto.getPage(), topicGroupDto.getRows());
//        }
        return groupMemberMapper.selectWSHGroupMemberByGroupId(topicGroupDto);
    }

    public List<TopicUserInfoDto> selectReadyReviewMembersInGroup(TopicGroupDto topicGroupDto) throws Exception{
//        if (!StringUtils.isEmpty(topicGroupDto.getPage()) && !StringUtils.isEmpty(topicGroupDto.getRows())) {
//            PageHelper.startPage(topicGroupDto.getPage(), topicGroupDto.getRows());
//        }
        return groupMemberMapper.selectYSHGroupMemberByGroupId(topicGroupDto);
    }

    public int checkPresident(Map<String, Object> map){
        return groupMemberMapper.checkIsPresident(map);
    }
}
