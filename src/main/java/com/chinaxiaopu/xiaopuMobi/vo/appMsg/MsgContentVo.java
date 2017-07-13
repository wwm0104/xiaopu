package com.chinaxiaopu.xiaopuMobi.vo.appMsg;

import com.chinaxiaopu.xiaopuMobi.dto.MsgViewDto;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by ellien
 * date: 2016/12/8
 */
@Data
public class MsgContentVo {

    private int id;
    private String content;
    private List<MsgViewDto> view;
    private Map<String,Object> rightImgUrl;
    private Map<String,Object> leftImgUrl;
}
