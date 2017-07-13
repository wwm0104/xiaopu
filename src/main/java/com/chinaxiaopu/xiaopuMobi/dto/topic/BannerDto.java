package com.chinaxiaopu.xiaopuMobi.dto.topic;

import com.chinaxiaopu.xiaopuMobi.model.BannerImg;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

/**
 * Created by hao on 2016/11/12.
 */
public class BannerDto extends BannerImg {
    @Setter @Getter
    private String url; //链接地址

    private Map<String,Object> urlMap=new HashedMap();

    public Map<String,Object> getUrlMap(){
        urlMap.put("url",url);
        return urlMap;
    }
}
