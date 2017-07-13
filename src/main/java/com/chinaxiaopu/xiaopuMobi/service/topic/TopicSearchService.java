package com.chinaxiaopu.xiaopuMobi.service.topic;

import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicSearchDto;
import com.chinaxiaopu.xiaopuMobi.mapper.TopicMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.TopicTagMapper;
import com.chinaxiaopu.xiaopuMobi.util.DateTimeUtil;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.topic.TopicSearchVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.TopicTagsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取图文列表
 * Created by Administrator on 2016/11/2.
 */
@Slf4j
@Service
public class TopicSearchService {
    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private TopicTagMapper topicTagMapper;

    /**
     * 根据图文标题搜索列表
     *
     * @param topicSearchDto
     * @return
     */
    public List<TopicSearchVo> getSearchList(TopicSearchDto topicSearchDto) {
        List<TopicSearchVo> _list = topicMapper.getSearchList(topicSearchDto);
        List<TopicTagsVo> groupList = topicTagMapper.getTagsGroupList(); //主题对应社团查询
        List<TopicTagsVo> eventList = topicTagMapper.getTagsEventList(); //主题对应活动查询
        String filePath = null;
        try {
            filePath = SystemConfig.getInstance().getImgsDomain(); //图片路径
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!StringUtils.isEmpty(_list)) {
            for (TopicSearchVo search : _list) {
                //处理字符串为json
                search.setShowTime(DateTimeUtil.getShowTime(search.getCreateTime()));
                search.setContentMap(search.getContent());
                search.setTimePoint(topicSearchDto.getTimePoint());
                search.setImgsHostDomain(filePath);
                if (StrUtils.isNotEmpty(groupList)) {
                    for (TopicTagsVo group : groupList) {
                        if ((group.getTopicId()).equals(search.getTopicId())) {
                            List<TopicTagsVo> _groupList = search.getGroupList();
                            if (_groupList == null) {
                                _groupList = new ArrayList<>();
                                _groupList.add(group);
                                search.setGroupList(_groupList);
                            } else {
                                _groupList.add(group);
                            }
                        }
                    }
                }
                if (StrUtils.isNotEmpty(eventList)) {
                    for (TopicTagsVo event : eventList) {
                        if ((event.getTopicId()).equals(search.getTopicId())) {
                            List<TopicTagsVo> _enentList = search.getEventList();
                            if (_enentList == null) {
                                _enentList = new ArrayList<>();
                                _enentList.add(event);
                                search.setEventList(_enentList);
                            } else {
                                _enentList.add(event);
                            }
                        }
                    }
                }
            }
        }
        return _list;
    }
}
