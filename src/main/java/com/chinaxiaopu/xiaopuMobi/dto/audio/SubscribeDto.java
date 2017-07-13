package com.chinaxiaopu.xiaopuMobi.dto.audio;

import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 订阅
 * Created by Wang on 2016/12/09.
 */
@Data
public class SubscribeDto extends ContextDto{
    private Date timePoint;//时间点，用来避免数据分页造成的数据重复出现

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
