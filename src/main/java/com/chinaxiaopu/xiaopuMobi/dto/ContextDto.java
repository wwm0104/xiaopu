package com.chinaxiaopu.xiaopuMobi.dto;

import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Steven@chinaxiaopu.com on 10/6/16.
 */
public class ContextDto extends BaseDto {

    @Setter
    @Getter
    private String qrcodeContextPath;
    @Setter
    private String apiHostDomain;
    @Setter
    private String imgsHostDomain;

    public String getApiHostDomain() {
        try{
            return SystemConfig.getInstance().getApiDomain();
        }catch (Exception e){
            throw new RuntimeException("获取API服务器地址错误");
        }
    }

    public String getImgsHostDomain(){
        try{
            return SystemConfig.getInstance().getImgsDomain();
        }catch (Exception e){
            throw new RuntimeException("获取图片服务器地址错误");
        }
    }

}
