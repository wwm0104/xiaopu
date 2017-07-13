package com.chinaxiaopu.xiaopuMobi.vo.topic;

import com.chinaxiaopu.xiaopuMobi.model.BannerImg;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;

import java.util.Map;

/**
 * Created by hao on 2016/11/12.
 */
public class BannerVo extends BannerImg {
    private Map<String,Object> urlMap;
    public Map<String, Object> getUrlMap() {
        return urlMap;
    }
    public void setUrlMap(String description) {
        Gson gson = new Gson();
        this.urlMap = gson.fromJson(description, Map.class);

        //BeanUtils.copyProperties(bannerIng,bannerVo);
    }
}
