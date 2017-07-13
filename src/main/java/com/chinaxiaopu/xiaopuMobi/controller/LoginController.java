package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.code.LoginResult;
import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.config.MainConfig;
import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.JsApiDto;
import com.chinaxiaopu.xiaopuMobi.dto.LoginUserDto;
import com.chinaxiaopu.xiaopuMobi.dto.UserUIDto;
import com.chinaxiaopu.xiaopuMobi.exception.NoLoginAuthorException;
import com.chinaxiaopu.xiaopuMobi.model.User;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.security.realm.ShiroUser;
import com.chinaxiaopu.xiaopuMobi.service.LoginService;
import com.chinaxiaopu.xiaopuMobi.service.UserService;
import com.chinaxiaopu.xiaopuMobi.util.Sha1Util;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
/**
 * Created by liuwei on 10/9/16.
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class LoginController extends ContextController{

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private WxMpConfigStorage configStorage;

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private SystemConfig systemConfig;


    @RequestMapping(value = "/appLogin", method = RequestMethod.POST)
    @ResponseBody
    public LoginResult appLogin(@RequestBody LoginUserDto loginUserDto) {
        log.debug("POST请求登录");
        LoginResult result = new LoginResult();
        try {
            result = loginService.appLogin(loginUserDto);
        } catch (Exception e){
            result.setResultCode(Constants.SYSTEM_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/weiChatLogin", method = RequestMethod.GET)
    @ApiIgnore
    public String weiChatLogin(HttpServletRequest request, HttpServletResponse response,RedirectAttributes attr) throws Exception{
        try {

            String code = request.getParameter("code");
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, "zh_CN");
            wxMpOAuth2AccessToken = wxMpService.oauth2refreshAccessToken(wxMpOAuth2AccessToken.getRefreshToken());
            boolean valid = wxMpService.oauth2validateAccessToken(wxMpOAuth2AccessToken);
            //微信验证登录完毕,构造token返回给客户端
            Map<String,String> resultMap = loginService.weixinLogin(wxMpUser);

            attr.addAttribute("clientDigest",resultMap.get("clientDigest"));
            attr.addAttribute("accessToken",resultMap.get("accessToken"));

            return "redirect:"+systemConfig.getWeixinindex();
        } catch (Exception e){
            log.error("微信登录失败:",e);
            response.getWriter().print("非法登录，请从微信端登入。");
        }
        return "redirect:"+systemConfig.getWeixinindex();
    }

    @RequestMapping(value = "/shareLogin", method = RequestMethod.GET)
    @ApiIgnore
    public LoginResult shareLogin() throws Exception{
        try {


        } catch (Exception e){
            log.error("微信登录失败:",e);
        }
        return null;
    }

    @RequestMapping("/token")
    @ApiIgnore
    public void token(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        if (StringUtils.isEmpty(signature) || StringUtils.isEmpty(echostr) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce)) {
            log.info("Not form weixin");
            return;
        }

        String[] str = {MainConfig.TOKEN, timestamp, nonce};
        Arrays.sort(str); // 字典序排序
        String bigStr = str[0] + str[1] + str[2];
        // SHA1加密
        String digest = null;

        digest = Sha1Util.getSha1(bigStr).toLowerCase();

        // 确认请求来至微信
        if (digest.equals(signature)) {
            response.getWriter().print(echostr);
        }
        return;
    }

    @ApiOperation(value = "微信根据accesstoken获取当前用户信息", notes = "根据token获取当前用户信息")
    //获取当前登录用户
    @RequestMapping(value = "/authApi/weixinCurrentUser", method = RequestMethod.POST)
    @ResponseBody
    public Result<UserInfo> currentUser(@RequestBody UserUIDto userUIDto) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(userUIDto.getClientDigest()) || StringUtils.isEmpty(userUIDto.getAccessToken())){
                result.setResultCode(Result.FAILURE);
            }
            UserInfo userInfo = loginService.getCurrentUserInfo(userUIDto);
            result.setObj(userInfo);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("获取当前登录用户:",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "微信获取jsApi信息", notes = "微信获取jsApi信息")
    @RequestMapping(value = "/jsApi", method = RequestMethod.POST)
    @ResponseBody
    public Result currentUser(@RequestBody JsApiDto jsApi , RedirectAttributes  attr) {
        Result result = new Result();
        WxJsapiSignature jsapiSignature = null;
        try {
            jsapiSignature = wxMpService.createJsapiSignature(jsApi.getCallUrl());
            result.setObj(jsapiSignature);
            result.setResultCode(Result.SUCCESS);
        } catch (WxErrorException e) {
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
            log.error("微信获取jsApi信息失败:",e);
        }
        return result;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Result logout(@RequestBody LoginUserDto loginUserDto) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(loginUserDto.getClientDigest())){
                result.setResultCode(Result.FAILURE);
            }
            loginService.logout(loginUserDto.getClientDigest(),loginUserDto.getRegistrationId());
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("退出失败:",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);

        }
        return result;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ApiIgnore
    public String login() {
        return "login";
    }


    @ApiIgnore
    @ResponseBody
    @RequestMapping(value = "/loginPost", method = RequestMethod.POST)
    public Result loginPost(@RequestBody LoginUserDto loginUserDto) {
        log.info("post login in");
        Result result = new Result();
        if (StringUtils.isEmpty(loginUserDto.getMobile())) {
            log.error("用户名不能为空");
            result.setResultCode(Result.FAILURE);
            result.setMsg("用户名不能为空");
            return result;
        }
        if (StringUtils.isEmpty(loginUserDto.getPassword())) {
            log.error("密码不能为空");
            result.setResultCode(Result.FAILURE);
            result.setMsg("密码不能为空");
            return result;
        }
        User loginUser = userService.selectByMobile(loginUserDto.getMobile());
        if (loginUser==null){
            result.setResultCode(Result.FAILURE);
            result.setMsg("账号不存在");
            return result;
        }
        //验证短信验证码
        if (systemConfig.isLoginSmsCaptcha()){
            Result smsResult = userService.confirmCaptcha(loginUserDto.getMobile(),loginUserDto.getSmsCaptcha());
            if(smsResult.getResultCode()!=Result.SUCCESS){
                result.setResultCode(Result.FAILURE);
                result.setMsg("短信验证码错误");
                return result;
            }
        }

        Subject subject = SecurityUtils.getSubject();
        try {
            String _password = DigestUtils.md5Hex(loginUser.getSalt()+loginUserDto.getPassword());
            UsernamePasswordToken token = new UsernamePasswordToken(loginUserDto.getMobile()+"", _password.toCharArray());
            subject.login(token);
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            if(shiroUser.getRoleSet().contains(Constants.ROLE_ADMIN)){
                result.setObj("/admin/index");
            } else if (shiroUser.getRoleSet().contains(Constants.ROLE_PRESIDENT)){
                result.setObj("/president/index");
            } else if (shiroUser.getRoleSet().contains(Constants.ROLE_PARTNERMANAGE)){
                result.setObj("/partnerManage/index");
            } else if (shiroUser.getRoleSet().contains(Constants.ROLE_ANCHOR)){
                result.setObj("/anchorManage/toAnchorAudioList");
            }
            result.setResultCode(Result.SUCCESS);
        } catch (UnknownAccountException e) {
            log.error("账号不存在：", e);
            result.setMsg("账号不存在");
            result.setResultCode(Result.FAILURE);
        } catch (IncorrectCredentialsException e) {
            log.error("密码错误 :", e);
            result.setMsg("密码错误");
            result.setResultCode(Result.FAILURE);
        } catch (NoLoginAuthorException e) {
            log.error("没登录权限 :", e);
            result.setMsg("该用户无登录权限");
            result.setResultCode(Result.FAILURE);
        } catch (RuntimeException e) {
            log.error("未知错误,请联系管理员：", e);
            result.setMsg("未知错误,请联系管理员");
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @RequestMapping(value = "/webLogout", method = RequestMethod.GET)
    @ApiIgnore
    public String webLogout(HttpServletRequest request) {
    	log.debug("登出");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String xmlHttpRequest = request.getHeader("X-Requested-With");
        if ( xmlHttpRequest != null && xmlHttpRequest.equalsIgnoreCase("XMLHttpRequest")) {//是ajax请求
            return "redirect:/user/ajaxUnLogin";
        }
        return "redirect:/user/login";
    }

    @ApiIgnore
    @ResponseBody
    @RequestMapping(value = "/ajaxUnLogin", method = RequestMethod.GET)
    public Result ajaxUnLogin(HttpServletRequest request) {
        Result result = new Result();
        result.setResultCode(Constants.UNKNOWN_LOGIN);
        result.setMsg(Result.NOT_LOGGED_IN);
        return result;
    }

}