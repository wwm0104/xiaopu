package com.chinaxiaopu.xiaopuMobi.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by FirenzesEagle on 2016/5/30 0030.
 * Email:liumingbo2008@gmail.com
 */
@Configuration
public class MainConfig {

    //TODO 填写公众号开发信息
    //公众号 APP_ID --生产
    protected static final String APP_ID = "wxc624a22b36dd5057";
    //公众号 APP_SECRET
    protected static final String APP_SECRET = "2f39f5d07cf580e6f7f303f6814f88de";

    //公众号 APP_ID --生产 - 刘为
//    protected static final String APP_ID = "wx46699d0d446448e0";
//    //公众号 APP_SECRET
//    protected static final String APP_SECRET = "573157035bb1145fdb54920a184cfed2";

    // TOKEN
    public static final String TOKEN = "chinaxiaopu";

    public static final String SCOPE = "snsapi_userinfo";

    public static final String STATE = "chinaxiaopu.state";

    //商站宝测试公众号 AES_KEY
    protected static final String AES_KEY = "";

    //商站宝微信支付商户号
    protected static final String PARTNER_ID = "";
    protected static final String PARTNER_KEY = "";

    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(MainConfig.APP_ID);
        configStorage.setSecret(MainConfig.APP_SECRET);
        configStorage.setToken(MainConfig.TOKEN);
        configStorage.setAesKey(MainConfig.AES_KEY);
        configStorage.setPartnerId(MainConfig.PARTNER_ID);
        configStorage.setPartnerKey(MainConfig.PARTNER_KEY);
        return configStorage;
    }

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

}
