package com.chinaxiaopu.xiaopuMobi.service;

import com.chinaxiaopu.xiaopuMobi.dto.UserNotifyDto;
import com.chinaxiaopu.xiaopuMobi.dto.UserUIDto;
import com.chinaxiaopu.xiaopuMobi.mapper.UserNotifyMapper;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.vo.UserNotifyVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by liuwei
 * date: 2016/12/7
 */
@Slf4j
@Service
public class UserNotifyService extends AbstractService {

    @Autowired
    UserNotifyMapper userNotifyMapper;

    public PageInfo<UserNotifyVo> selectByUser(UserNotifyDto userNotifyDto, UserInfo current){
        if (!StringUtils.isEmpty(userNotifyDto.getPage()) && !StringUtils.isEmpty(userNotifyDto.getRows())) {
            PageHelper.startPage(userNotifyDto.getPage(), userNotifyDto.getRows());
        }
        List<UserNotifyVo> list = userNotifyMapper.selectByUser(current.getUserId(), userNotifyDto.getTimePoint());
        PageInfo<UserNotifyVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public void notifyRead(Integer userId){
        userNotifyMapper.notifyRead(userId);
    }

}
