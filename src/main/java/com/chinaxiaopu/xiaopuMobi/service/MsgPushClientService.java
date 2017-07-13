package com.chinaxiaopu.xiaopuMobi.service;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.*;
import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;


/**
 * Created by liuwei
 * date: 2016/12/5
 */
@Slf4j
public class MsgPushClientService {

    public static void sendMsgPushByUser(Collection<String> registrations, String msg) throws Exception {
        MsgPushConfig msgPushConfig = MsgPushFactory.getInstance("JPUSH");
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(msgPushConfig.getSecret(), msgPushConfig.getAppkey(), null, clientConfig);
        try {
            PushResult result = jpushClient.sendPush(buildPushObjectMessage(registrations,msg));
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);

        } catch (APIRequestException e) {
           log.error("推送消息失败",e);
        }
    }

    public static void sendAlertPushByUser() throws Exception {
        MsgPushConfig msgPushConfig = MsgPushFactory.getInstance("JPUSH");
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(msgPushConfig.getSecret(), msgPushConfig.getAppkey(), null, clientConfig);
        try {
            PushResult result = jpushClient.sendPush(buildPushObject_alert(2));
            log.info("Got result - " + result);

        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);

        } catch (APIRequestException e) {
            log.error("推送通知失败",e);
        }
    }

    private static PushPayload buildPushObject_alert(int i) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId("101d8559094e1e41e76"))
//                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert("java 发送给ios的消息->!"+i)
                                .autoBadge()
                                .build())
                        .addPlatformNotification(AndroidNotification.newBuilder().
                                setAlert("java 发送给Android的消息!")
                                .build())
                        .build())
//                .setNotification(Notification.alert("JAVA消息推送测试!111->>101d8559094e1e41e76"))
                .build();
    }

    private static PushPayload buildPushObjectMessage(Collection<String> registrations, String msg) throws Exception{
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(registrations))
                .setMessage(Message.newBuilder()
                        .setMsgContent(msg)
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(SystemConfig.getInstance().isJpushApns())
                        .build())
                .build();
    }


}

interface MsgPushConfig extends IService {
    public String getAppkey();
    public String getSecret();
}

class MsgPushFactory{
    public static final String JPUSH="JPUSH";
    private static  MsgPushConfig msgPushConfig;
    public static MsgPushConfig getInstance(String whichSmsProvider){
        assert StringUtils.isNoneEmpty(whichSmsProvider);
        if(whichSmsProvider.equals(JPUSH)){
            if (msgPushConfig == null) {
                msgPushConfig = new JPushConfig();
            }
        }
        return msgPushConfig;
    }
    static final class JPushConfig implements MsgPushConfig{
        @Getter
        private String appkey = "c5887678cae2c4e9d392d7f6";
        @Getter
        private String secret = "3408c884ef2ba422d92ee641";
    }
}