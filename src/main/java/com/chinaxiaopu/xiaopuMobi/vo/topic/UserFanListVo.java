package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.chinaxiaopu.xiaopuMobi.vo.ContextVo;
import lombok.Data;

/**
 * Created by hao on 2016/12/1.
 */
@Data
public class UserFanListVo extends ContextVo {
    private Integer userId;    //用户id
    private String avatarUrl;  //用户图像
    private String nickName;   //用户昵称
    private Integer isFocus;   //是否互相关注
}
