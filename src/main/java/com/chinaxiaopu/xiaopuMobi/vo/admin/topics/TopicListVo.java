package com.chinaxiaopu.xiaopuMobi.vo.admin.topics;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Wang on 2016/11/09.
 */
public class TopicListVo {
    @Setter @Getter
    private Integer id;
    @Setter @Getter
    private String nickName;
    @Setter @Getter
    private String realName;
    @Setter @Getter
    private String schoolName;
    @Setter @Getter
    private Integer type;
    @Setter @Getter @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @Setter @Getter
    private String rule;
    @Setter @Getter
    private String name;
    @Setter @Getter
    private Integer status;
    @Setter @Getter
    private Integer challengeCnt;
    @Setter @Getter
    private String tags;

}
