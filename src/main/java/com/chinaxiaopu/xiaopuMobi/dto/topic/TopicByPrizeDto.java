package com.chinaxiaopu.xiaopuMobi.dto.topic;

import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by hao on 2016/11/29.
 */
public class TopicByPrizeDto extends ContextDto {
    @Setter @Getter
    private Integer prizeId;  //奖品id
    @Setter @Getter
    private Integer userId;   //用户id
    @Setter @Getter
    private Integer orderType;  //条件： 1参与人数升序 2参与人数降序 3截止时间升序 4截止时间降序
    @Setter @Getter
    private Integer topicType;     //条件：1:图文；2:视频；
    @Setter @Getter
    private Integer isOver;     //条件：1未结束 2结束

    private Date timePoint;
    public Date getTimePoint() {
        if(StringUtils.isEmpty(timePoint) && getPage()==1){
            setTimePoint(new Date());
        }
        return timePoint;
    }
    public void setTimePoint(Date timePoint) {
        this.timePoint = timePoint;
    }
}
