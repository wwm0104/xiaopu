package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ellien
 * date: 2016/12/6
 */
@Data
public class MsgFromDto implements Serializable {

    private int userId;
    private String userName;
    private int eventId;
    private String eventName;
    private int groupId;
    private String groupName;
    private int prizeId;
    private String prizeName;
    private int topicId;
    private String topicName;
    private int isPk;
    private int topicType;
    private String remark;
    private int prizeNum;
    private String leftImgUrl;
    private String rightImgUrl;
    private Date expireTime;
}
