package com.chinaxiaopu.xiaopuMobi.service.shiro;

import java.util.Map;

/**
 * Created by Ellie on 2016/11/18.
 */
public interface FilterChainDefinitionsService {

    public static final String PREMISSION_STRING = "perms[{0}]"; // 资源结构格式
    public static final String ROLE_STRING = "authorization[{0}]"; // 角色结构格式

    /** 初始化框架权限资源配置 */
    public abstract void intiPermission();

    /** 重新加载框架权限资源配置 (强制线程同步) */
    public abstract void updatePermission();

    /** 初始化第三方权限资源配置 */
    public  Map<String, String> initOtherPermission();


}
