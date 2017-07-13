package com.chinaxiaopu.xiaopuMobi.code;

import lombok.Getter;
import lombok.Setter;



/**
 * 系统登录接口返回json内容类
 * Created by liuwei
 * date: 16/5/27
 */
public class SmsCaptchaResult extends AbstractResult{

    private static final long serialVersionUID = 5576237395711742681L;

    private @Getter @Setter Integer resultCode;// -- 0：获取失败，1：登录成功；
    private @Getter @Setter String clientDigest;// --客户端唯一表示
    private @Getter @Setter String smsCaptcha;// -- 短信验证码

}
