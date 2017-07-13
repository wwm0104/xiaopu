package com.chinaxiaopu.xiaopuMobi.service;

import com.chinaxiaopu.xiaopuMobi.dto.EventUserMsgDto;
import com.chinaxiaopu.xiaopuMobi.mapper.EventUserMsgMapper;
import com.chinaxiaopu.xiaopuMobi.model.EventUserMsg;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liuwei
 * date: 2016/12/13
 */
@Service
public class EventUserMsgService extends AbstractService {

    @Autowired
    private EventUserMsgMapper eventUserMsgMapper;

    public void sendMsg(EventUserMsgDto eventUserMsgDto) throws Exception {
        EventUserMsg eventUserMsg = new EventUserMsg();
        BeanUtils.copyProperties(eventUserMsg,eventUserMsgDto);
        eventUserMsgMapper.insertSelective(eventUserMsg);
    }
}
