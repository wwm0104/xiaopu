package com.chinaxiaopu.xiaopuMobi.dto.audio;

import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 *
 * Created by Wang on 2016/12/06.
 */

public class AudioSearchDto extends ContextDto{
    @Setter @Getter
    private String slogan;
    @Setter @Getter
    private Integer userId;
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
