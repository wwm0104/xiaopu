package com.chinaxiaopu.xiaopuMobi.code;


import lombok.Data;


/**
 * 系统登录接口返回json内容类
 * Created by liuwei
 * date: 16/5/27
 */
public @Data class UpdateUserResult extends AbstractResult {

	private static final long serialVersionUID = 1L;
	
	private Integer resultCode;// -- 0：登录失败，1：登录成功；2：系统错误；11：手机号码为空；12：用户不存在；21：密码为空；22：密码错误；31：验证码为空；32：验证码错误；

}
