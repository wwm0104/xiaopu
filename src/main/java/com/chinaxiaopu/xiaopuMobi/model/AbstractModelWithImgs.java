package com.chinaxiaopu.xiaopuMobi.model;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import me.chanjar.weixin.common.util.StringUtils;

import java.util.List;

/**
 * Created by Steven@chinaxiaopu.com on 10/7/16.
 */
abstract class AbstractModelWithImgs extends BaseEntity {
    //add by steven
    @Setter @Getter
    private String smallPosterImg;
    @Setter @Getter
    private String posterImg;
    @Setter @Getter
    private String contentImgs;
    @Setter @Getter
    private String further;
    private String[] contentImgsArray;
    private List furtherArray;

    public String[] getContentImgsArray(){
        if(StringUtils.isNotEmpty(this.contentImgs)){
            contentImgsArray = this.contentImgs.split(",");
        }
        return contentImgsArray;
    }

    public List getFurtherArray() {
        if(StringUtils.isNotEmpty(this.further)){
            furtherArray = new Gson().fromJson(this.further,List.class);
        }
        return furtherArray;
    }
}
