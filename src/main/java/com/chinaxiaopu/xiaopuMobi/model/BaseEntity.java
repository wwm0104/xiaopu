package com.chinaxiaopu.xiaopuMobi.model;

import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;

/**
 * 基础信息
 * Created by liuwei
 * date: 16/8/25
 */
public class BaseEntity implements Serializable{

    @Id
    private @Getter @Setter Integer id;

    @Transient
    private @Getter @Setter Integer page = 1;

    @Transient
    private @Getter @Setter Integer rows = 10;

    @Setter @Getter
    private String qrcodeContextPath;
    @Setter
    private String apiHostDomain;
    @Setter
    private String imgsHostDomain;

    public String getApiHostDomain() {
        try {
            return SystemConfig.getInstance().getApiDomain();
        }catch(Exception e){
            throw new RuntimeException("获取API服务器地址错误");
        }
    }

    public String getImgsHostDomain() {
        try {
            return SystemConfig.getInstance().getImgsDomain();
        }catch(Exception e){
            throw new RuntimeException("获取图片服务器地址错误");
        }
    }

}
