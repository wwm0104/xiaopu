package com.chinaxiaopu.xiaopuMobi.code;

import lombok.Getter;
import lombok.Setter;



/**
 * 系统登录接口返回json内容类
 * Created by ellien
 * date: 16/5/27
 */
public class RegisterResult extends AbstractResult {

	private static final long serialVersionUID = 1L;
	
	private @Getter @Setter Integer resultCode;// -- 0：登录失败，1：登录成功；2：系统错误；11：手机号码为空；12：用户不存在；21：密码为空；22：密码错误；31：验证码为空；32：验证码错误；
    private @Getter @Setter Integer userId;// --用户唯一标识

}
