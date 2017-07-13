package com.chinaxiaopu.xiaopuMobi.service;

import com.chinaxiaopu.xiaopuMobi.dto.UserFansTopicLisDto;
import com.chinaxiaopu.xiaopuMobi.mapper.UserFansMapper;
import com.chinaxiaopu.xiaopuMobi.model.Topic;
import com.chinaxiaopu.xiaopuMobi.model.UserFans;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by ycy on 2016/12/1.
 */
@Slf4j
@Service
public class UserFansService {

    @Autowired
    private UserFansMapper userFansMapper;

    public PageInfo<Topic> getFollowTopics(UserFansTopicLisDto userFansTopicLisDto)  throws Exception{
        if (!StringUtils.isEmpty(userFansTopicLisDto.getPage()) && !StringUtils.isEmpty(userFansTopicLisDto.getRows())) {
            PageHelper.startPage(userFansTopicLisDto.getPage(), userFansTopicLisDto.getRows());
        }
        List<Topic> topicList = userFansMapper.selectFollowTopics(userFansTopicLisDto);
        PageInfo<Topic> pageInfo =new PageInfo<>(topicList);
        pageInfo.setList(topicList);
        return pageInfo;
    }

    /**
     * userId 是当前登录的id
     * fansId 是对方id
     * @param userFans
     * @return
     */
    @Transactional
    public int FocusUser(UserFans userFans)  throws Exception{
        //查询对方是否关注我
        int count = userFansMapper.selectIfFollow(userFans);
        //插叙我是否关注对方
        UserFans userFans1 = new UserFans();
        userFans1.setUserId(userFans.getFansId());
        userFans1.setFansId(userFans.getUserId());
        int count1 = userFansMapper.selectIfFollow(userFans1);
        if(count1 != 0){
            return 0;
        }else {
            userFans1.setFoncusTime(new Date());
            userFans1.setUpdateTime(new Date());
            userFans1.setIsFocus("0");
            userFansMapper.insert(userFans1);
            if(count != 0){
                userFans.setIsFocus("1");
                userFans1.setIsFocus("1");
                userFansMapper.updateIsFocus(userFans);
                userFansMapper.updateIsFocus(userFans1);
            }
        }
        return 1;
    }

    /**
     * userId 是当前登录的id
     * fansId 是对方id
     * 取消关注
     * @param userFans
     * @return
     */
    @Transactional
    public int  removeFocus(UserFans userFans) throws Exception{
        UserFans userFans1 = new UserFans();
        userFans1.setUserId(userFans.getFansId());
        userFans1.setFansId(userFans.getUserId());
        userFansMapper.removeFocus(userFans1);
        userFans.setIsFocus("0");
        userFansMapper.updateIsFocus(userFans);
        return 1;
    }
}
