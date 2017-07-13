package com.chinaxiaopu.xiaopuMobi.dto.topic;

import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import lombok.Data;

/**
 * Created by wuning on 2016/11/2.
 */
@Data
public class TopicPkDto extends ContextDto{
    private Integer parentType;
    private Integer orderType;// 1 ： 参与人数倒序 2 ： 参与人数正序  3：截止时间 倒序  4：截止时间正序   其他 ： 时间倒序
    private Integer type;//1:图片来战 2：视频来战  其他 ： 全部
    private Integer isOver;// 1 :未结束  2 ： 已结束  其他 ： 全部

}
