package com.chinaxiaopu.xiaopuMobi.util;

import com.chinaxiaopu.xiaopuMobi.config.MainConfig;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.util.http.URIUtil;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

/**
 * Created by Steven@chinaxiaopu.com on 10/2/16.
 */
@Slf4j
public class WeiXinUrlUtil {
        public static void u(String args[]) {
            String redirectURI = "http://xiaopu.proxy.chinaxiaopu.cn/index.html";
            String APP_ID = "wxf24853d3a823cd75";
            StringBuilder url = new StringBuilder();
            url.append("https://open.weixin.qq.com/connect/oauth2/authorize?");
            url.append("appid=").append(APP_ID);
            url.append("&redirect_uri=").append(URIUtil.encodeURIComponent(redirectURI));
            url.append("&response_type=code");
            url.append("&scope=").append(MainConfig.SCOPE);
            url.append("&state=").append(MainConfig.STATE);
            url.append("#wechat_redirect");
            log.warn(url.toString());
        }
}
