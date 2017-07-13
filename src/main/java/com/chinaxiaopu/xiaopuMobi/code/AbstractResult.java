package com.chinaxiaopu.xiaopuMobi.code;


import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;

/**
 * Created by Steven@chinaxiaopu.com on 10/7/16.
 */
abstract class AbstractResult implements IResult{
    private String imgsHostDomain;

    public String getImgsHostDomain() {
        try {
            return SystemConfig.getInstance().getImgsDomain();
        }catch(Exception e){
            throw new RuntimeException("获取图片服务器地址错误");
        }
    }
}
