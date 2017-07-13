package com.chinaxiaopu.xiaopuMobi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by ellien
 * date: 16/9/27
 */
@Component
@ConfigurationProperties(prefix = "system")
public class SystemConfig {
    private static SystemConfig systemConfig = null;

    private  SystemConfig(){

    }

    @Setter
    @Getter
    private String weixinindex;
    @Setter
    @Getter
    private String weixinBinding;
    @Setter
    @Getter
    private String imgBasePath;
    @Setter
    @Getter
    private String weixinDomain;
    @Setter
    @Getter
    private String apiDomain;
    @Setter
    @Getter
    private String imgsDomain;
    @Setter
    @Getter
    private String qrcodeRootPath;
    @Setter
    @Getter
    private String groupPosterImg;
    @Setter
    @Getter
    private String smsDuration;
    @Setter
    @Getter
    private Integer smsTodayLimit;
    @Setter
    @Getter
    private String evenSharePage;
    @Setter
    @Getter
    private String groupSharePage;
    @Setter
    @Getter
    private String partnerIndex;
    @Setter
    @Getter
    private String alyunoss;
    @Getter
    @Setter
    private boolean jpushApns;
    @Getter
    @Setter
    private boolean loginSmsCaptcha;

    @Bean
    public static SystemConfig getInstance() throws Exception {
        if(systemConfig == null)
            systemConfig = new SystemConfig();
        return systemConfig;
    }

}
