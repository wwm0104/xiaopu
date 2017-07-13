package com.chinaxiaopu.xiaopuMobi.vo.audio;

import lombok.Data;

import java.util.Date;

/**
 * 主播
 * Created by Wang on 2016/12/06.
 */
@Data
public class AnchorVo {
    private Integer userId;//主播Id
    private String nickName;//主播昵称
    private String avatar;//主播头像
    private Integer focusCnt;//关注人数
    private Integer isFocus = 0;//是否关注
}
