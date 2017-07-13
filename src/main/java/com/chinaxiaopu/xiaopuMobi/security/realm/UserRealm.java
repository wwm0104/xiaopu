package com.chinaxiaopu.xiaopuMobi.security.realm;

import com.chinaxiaopu.xiaopuMobi.exception.NoLoginAuthorException;
import com.chinaxiaopu.xiaopuMobi.model.Role;
import com.chinaxiaopu.xiaopuMobi.model.User;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.RoleService;
import com.chinaxiaopu.xiaopuMobi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by liuwei
 * date: 16/8/29
 */
@Slf4j
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;


    /**
     * Shiro登录认证(原理：用户提交 用户名和密码  --- shiro 封装令牌 ---- realm 通过用户名将密码查询返回 ---- shiro 自动去比较查询出密码和用户输入密码是否一致---- 进行登陆控制 )
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.debug("Shiro开始数据库登录认证");
        Long mobile = Long.parseLong(token.getPrincipal().toString());
        User user = userService.selectByMobile(mobile);
        if (user==null){
            log.error(mobile+" : 用户不存在!");
            throw new UnknownAccountException();//没找到帐号
        }
        UserInfo userInfo = userService.selectById(user.getId());
        List<Role> roleList = roleService.selectRoleByUserId(user.getId());

        //判断是否是超级管理员或者社长权限-只有这个角色才能登陆后台。
        //TODD 需要修改
        if (roleList==null || roleList.size()==0){
            log.error(mobile+" : 用户不是管理员或者社长!");
            //没有登陆权限
            throw new NoLoginAuthorException();
        }
        Set<String> roleSet = new HashSet<>();
        for (Role role : roleList) {
            roleSet.add(role.getRoleKey());
        }

        ShiroUser shiroUser = new ShiroUser();
        shiroUser.setId(user.getId());
        shiroUser.setMobile(user.getMobile());
        shiroUser.setNickName(userInfo.getNickName());
        shiroUser.setRealName(userInfo.getRealName());
        shiroUser.setIsPresident(userInfo.getIsPresident());
        shiroUser.setAvatarUrl(userInfo.getAvatarUrl());
        if(roleSet!=null && roleSet.size()>0){
            shiroUser.setRoleSet(roleSet);
        }
        // 认证缓存信息
        return new SimpleAuthenticationInfo(shiroUser, user.getPassword().toCharArray(), getName());

    }

    /**
     * Shiro访问链接权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.debug("Shiro开始数据库权限认证");
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        List<Role> roleList = roleService.selectRoleByUserId(shiroUser.getId());
        Set<String> roleSet = new HashSet<>();
        for (Role role : roleList) {
            roleSet.add(role.getRoleKey());
        }
        if(roleSet!=null && roleSet.size()>0){
            shiroUser.setRoleSet(roleSet);
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roleSet);
        return info;
    }

}