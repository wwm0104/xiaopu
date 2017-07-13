package com.chinaxiaopu.xiaopuMobi.vo;

import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import lombok.Setter;

/**
 * Created by Wang on 2016/11/07.
 */
public class ContextVo{

    @Setter
    private String imgsHostDomain;

    public String getImgsHostDomain() {
        try {
            return SystemConfig.getInstance().getImgsDomain();
        }catch(Exception e){
            throw new RuntimeException("获取图片服务器地址错误");
        }
    }
}
