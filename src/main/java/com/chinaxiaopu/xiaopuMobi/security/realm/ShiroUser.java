package com.chinaxiaopu.xiaopuMobi.security.realm;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by liuwei
 * date: 16/10/17
 */
public @Data class ShiroUser implements Serializable {

    private int id;
    private Long mobile;
    private String nickName;
    private String realName;
    private Integer isPresident;
    private String avatarUrl;
    private Set<String> roleSet;
    private Set<String> permissionSet;

}
