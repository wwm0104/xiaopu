package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/2.
 */
@Data
public class TopicPkVo {
    private Integer id;//id
    private String slogan;//标题
    private String content;//详细数据
    private Integer isOver;//是否结束 0：结束 1：未结束
    private Integer toatle;//参加人数 包括擂主
    //private String avatars;//参加人数的头像
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;//截止时间
    private List<UserPkVo> userList; //pk贴对应的参加人员
    private String path;
    private Integer type;

    private String prizeName;
    private Integer prizeId;

    private Map<String,Object> contentMap;
    public  Map<String,Object> getContentMap() {
        return contentMap;
    }

    public  void setContentMap(String content) {
        Gson gson = new Gson();
        this.contentMap = gson.fromJson(content,Map.class);
    }
}
