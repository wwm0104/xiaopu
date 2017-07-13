package com.chinaxiaopu.xiaopuMobi.dto;

import com.chinaxiaopu.xiaopuMobi.model.Event;
import com.chinaxiaopu.xiaopuMobi.model.EventGroup;
import com.chinaxiaopu.xiaopuMobi.model.Group;
import lombok.Data;

import java.util.List;

/**
 * Created by Steven@chinaxiaopu.com on 10/6/16.
 */
@Data
public class EventDetailDto extends ContextDto {
    private Event event;//活动信息
    private List<EventGroup> eventGroups;//参加该活动的社团列表
    private List<Group> groups;//发布该活动的社团信息
    private String shareUrl;//微信端二维码Url
    private Integer untreatedMemberCnt;//申请加入该活动的数量
    private Integer remainingCnt;//剩余门票数量
    private Integer isTicket = 0;//是否需要门票，1需要，0不需要
}
