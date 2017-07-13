package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import com.chinaxiaopu.xiaopuMobi.exception.UnknownLoginException;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.security.realm.ShiroUser;
import com.chinaxiaopu.xiaopuMobi.service.LoginService;
import com.chinaxiaopu.xiaopuMobi.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Steven@chinaxiaopu.com on 10/6/16.
 */
@Slf4j
public abstract class ContextController {
    @Autowired
    private LoginService loginService;

    @Autowired
    SmsService smsService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    String RetMsg = "msg";
    protected String query_EMPTY = "查无数据";

    protected UserInfo getCurrentUser(ContextDto contextDto) throws UnknownLoginException {
        if (contextDto == null || StringUtils.isEmpty(contextDto.getAccessToken()) || StringUtils.isEmpty(contextDto.getClientDigest())) {
            throw new UnknownLoginException("无效凭证，请重新登录");
        }
        return loginService.getCurrentUserInfo(contextDto);
    }

    protected String getWeixinHostDomain() throws Exception {
        return SystemConfig.getInstance().getWeixinDomain();
    }


    protected String getAPIHostDomain() throws Exception {
        return SystemConfig.getInstance().getApiDomain();
    }

    protected String getImgsHostDomain() throws Exception {
        return SystemConfig.getInstance().getImgsDomain();
    }

    protected String getQrcodeRootPath() throws Exception {
        return SystemConfig.getInstance().getQrcodeRootPath();
    }

    /**
     * 添加Model消息
     *
     * @param messages
     */
    protected void addMessage(Model model, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        model.addAttribute(RetMsg, sb.toString());
    }

    /**
     * 添加Flash消息
     *
     * @param messages
     */
    protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        redirectAttributes.addFlashAttribute(RetMsg, sb.toString());
    }

    /**
     * 图片上传路径地址
     *
     * @return
     */
    protected String imagesPath() {
        try {
            return SystemConfig.getInstance().getImgsDomain();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当前登录人的id
     *
     * @return
     */
    protected Integer getCurrentId() {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Integer userId = shiroUser.getId();
        return userId;
    }

    /**
     * 获取当前登录人的真实姓名
     *
     * @return
     */
    protected String getCurrentName() {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        String userName = shiroUser.getRealName();
        return userName;
    }


}
