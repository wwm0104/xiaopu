package com.chinaxiaopu.xiaopuMobi.vo.president;

import com.chinaxiaopu.xiaopuMobi.model.Event;
import com.chinaxiaopu.xiaopuMobi.model.Group;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
/**
 * Created by Wang on 2016/11/7.
 */
public class EventVo extends Event{
    @Setter @Getter
    private Group group;  //社团名称
    @Setter @Getter
    private Integer applyCount;
    @Setter @Getter
    private Integer untreatedApplyCount;

    private Integer timeStatus;

    public Integer getTimeStatus() {
        Date date = new Date();
        if(getStartTime().getTime()>date.getTime()){
            return this.timeStatus = 1;
        }else if(getEndTime().getTime()<date.getTime()){
            return this.timeStatus = 3;
        }else{
            return this.timeStatus = 2;
        }
    }

}