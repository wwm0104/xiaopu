package com.chinaxiaopu.xiaopuMobi.service;

import com.chinaxiaopu.xiaopuMobi.code.LoginResult;
import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import com.chinaxiaopu.xiaopuMobi.dto.LoginUserDto;
import com.chinaxiaopu.xiaopuMobi.exception.UnknownLoginException;
import com.chinaxiaopu.xiaopuMobi.mapper.UserInfoMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.UserMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.UserWeixinMapper;
import com.chinaxiaopu.xiaopuMobi.model.User;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.model.UserWeixin;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.web.tags.SecureTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Created by liuwei
 * date: 16/9/26
 */
@Slf4j
@Service
public class LoginService extends AbstractService{

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserWeixinMapper userWeixinMapper;

    @Autowired
    private UserService userService;

    public LoginResult appLogin(LoginUserDto loginUserDto)  throws Exception {
        Long mobile = loginUserDto.getMobile();
        String password = loginUserDto.getPassword();
        String smsCaptcha = loginUserDto.getSmsCaptcha();
        String clientDigest = loginUserDto.getClientDigest();

        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        LoginResult result = new LoginResult();
        if (StringUtils.isEmpty(mobile)) {
            result.setResultCode(Constants.MOBILE_NULL);
            return result;
        }
        if (StringUtils.isEmpty(password)) {
            result.setResultCode(Constants.PASSWORD_NULL);
            return result;
        }
        if (!StringUtils.isEmpty(smsCaptcha)) {
            //验证短信验证码是否正确
            int resultCode = userService.confirmCaptcha(mobile,smsCaptcha).getResultCode();
            if(resultCode!=Result.SUCCESS){
                result.setResultCode(resultCode);
                return result;
            }
        }

        //验证用户是否正确
        try {
            User user = userMapper.selectByMobile(mobile);
            if(user==null){
                result.setResultCode(Constants.USER_NULL);
                return result;
            }
            String salt = user.getSalt();
            String userPwd = user.getPassword();
            String _password = DigestUtils.md5Hex(salt+password);
            if(!_password.equals(userPwd)){
                result.setResultCode(Constants.PASSWORD_ERROR);
                return result;
            }


            String accessToken = DigestUtils.md5Hex(RandomStringUtils.random(10));
            //将token加入到redis中去
            Map<String,String> userMap = new HashMap<>();
            userMap.put("accessToken",accessToken);
            userMap.put("userId",user.getId().toString());
            Gson gson = new Gson();
            valueOperations.set(Constants.APP_TOKEN+clientDigest, gson.toJson(userMap));

            result.setResultCode(Constants.LOGIN_OK);
            result.setClientDigest(clientDigest);
            result.setAccessToken(accessToken);
            result.setUserId(user.getId());

            String registrationId = loginUserDto.getRegistrationId();
            addUserRegistrationId(valueOperations,user.getId().toString(),registrationId);
//            if (!StringUtils.isEmpty(registrationId)){
//                String loginUserId = user.getId().toString();
//                //查询出登录用户的设备清单
//                String loginRegistrationIds = valueOperations.get(Constants.APP_USER+loginUserId);
//                Set<String> loinRegistrationSet;
//                if (StringUtils.isEmpty(loginRegistrationIds)){
//                    loinRegistrationSet = new HashSet<>();
//                } else {
//                    loinRegistrationSet = new Gson().fromJson(loginRegistrationIds,Set.class);
//                }
//                loinRegistrationSet.add(registrationId);
//                valueOperations.set(Constants.APP_USER+loginUserId,new Gson().toJson(loinRegistrationSet,Set.class));
//
//                //查询该设备是否有绑定用户
//                String userId = valueOperations.get(Constants.APP_REGISTRATION+registrationId);
//                //如果已经设备已经绑定了用户了,则把该设备从用户绑定集合中移除。
//                if(!StringUtils.isEmpty(userId) && !userId.equals(loginUserId)){
//                    removeUserRegistrationId(valueOperations,userId,registrationId);
//
//                }
//                valueOperations.set(Constants.APP_REGISTRATION+registrationId,loginUserId);
//            }

        } catch (Exception e) {
            log.error("用户登录失败:",e);
            result.setResultCode(Constants.SYSTEM_ERROR);
        }

        return result;
    }

    /**
     * 微信手机登陆验证接口
     * @return  LoginResult
     * */
    @Transactional
    public Map<String,String> weixinLogin(WxMpUser wxMpUser)  throws Exception {
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();

        UserWeixin userWeixin = userWeixinMapper.selectByOpenId(wxMpUser.getOpenId());
        if (userWeixin == null){
            //新增一条user表记录
            User user = new User();
            user.setOrigin(1);
            userMapper.insertSelective(user);

            //新增一条user_info记录
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(user.getId());
            userInfoMapper.insertSelective(userInfo);

            //新增一条微信用户记录
            userWeixin = new UserWeixin();
            userWeixin.setUserId(user.getId());
            userWeixin.setUnionid(wxMpUser.getUnionId());
            userWeixin.setOpenid(wxMpUser.getOpenId());
            if(wxMpUser.getSubscribe()!=null){
                userWeixin.setSubscribe(wxMpUser.getSubscribe() ? 1 : 0);
            }
            userWeixin.setNickname(wxMpUser.getNickname());
            userWeixin.setSex(wxMpUser.getSex());
            userWeixin.setCity(wxMpUser.getCity());
            userWeixin.setCountry(wxMpUser.getCountry());
            userWeixin.setProvince(wxMpUser.getProvince());
            userWeixin.setLanguage(wxMpUser.getLanguage());
            userWeixin.setHeadimgurl(wxMpUser.getHeadImgUrl());
            userWeixin.setSubscribeTime(wxMpUser.getSubscribeTime()+"");
            userWeixin.setRemark(wxMpUser.getRemark());
            userWeixin.setGroupid(wxMpUser.getGroupId());
            userWeixinMapper.insertSelective(userWeixin);

            userWeixin = userWeixinMapper.selectByOpenId(wxMpUser.getOpenId());
        }
        //将token加入到redis中去
        String clientDigest = DigestUtils.md5Hex(wxMpUser.getOpenId());
        String accessToken = DigestUtils.md5Hex(RandomStringUtils.random(10));

        Map<String,String> userMap = new HashMap<>();
        userMap.put("accessToken",accessToken);
        userMap.put("openid",wxMpUser.getOpenId());
        userMap.put("userId",userWeixin.getUserId().toString());
        Gson gson = new Gson();
        valueOperations.set(Constants.APP_TOKEN+clientDigest, gson.toJson(userMap));

        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("clientDigest",clientDigest);
        resultMap.put("accessToken",accessToken);
        resultMap.put("openid",wxMpUser.getOpenId());
        resultMap.put("userId",userWeixin.getUserId().toString());

        return resultMap;
    }


    /**
     * app手机退出登陆接口
     * @param clientDigest 客户端唯一标识
     * @return
     * */
    public void logout(String clientDigest,String registrationId){
        stringRedisTemplate.delete(Constants.APP_TOKEN+clientDigest);
        if (!StringUtils.isEmpty(registrationId)){
            ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
            String userId = valueOperations.get(Constants.APP_REGISTRATION+registrationId);
            if (!StringUtils.isEmpty(userId)){
                removeUserRegistrationId(valueOperations,userId,registrationId);
            }
            stringRedisTemplate.delete(Constants.APP_REGISTRATION+registrationId);

        }

    }

    public void addUserRegistrationId(ValueOperations<String,String> valueOperations,String loginUserId,String registrationId){
        if (!StringUtils.isEmpty(registrationId)){
            //查询出登录用户的设备清单
            String loginRegistrationIds = valueOperations.get(Constants.APP_USER+loginUserId);
            Set<String> loinRegistrationSet;
            if (StringUtils.isEmpty(loginRegistrationIds)){
                loinRegistrationSet = new HashSet<>();
            } else {
                loinRegistrationSet = new Gson().fromJson(loginRegistrationIds,Set.class);
            }
            loinRegistrationSet.add(registrationId);
            valueOperations.set(Constants.APP_USER+loginUserId,new Gson().toJson(loinRegistrationSet,Set.class));

            //查询该设备是否有绑定用户
            String userId = valueOperations.get(Constants.APP_REGISTRATION+registrationId);
            //如果已经设备已经绑定了用户了,则把该设备从用户绑定集合中移除。
            if(!StringUtils.isEmpty(userId) && !userId.equals(loginUserId)){
                removeUserRegistrationId(valueOperations,userId,registrationId);

            }
            valueOperations.set(Constants.APP_REGISTRATION+registrationId,loginUserId);
        }
    }

    private void removeUserRegistrationId(ValueOperations<String,String> valueOperations,String userId,String registrationId){
        //如果已经设备已经绑定了用户了,则把该设备从用户绑定集合中移除。
        String _registrationIds = valueOperations.get(Constants.APP_USER+userId);
        if (!StringUtils.isEmpty(_registrationIds)){
            Set<String> registrationSet = new Gson().fromJson(_registrationIds,Set.class);
            registrationSet.remove(registrationId);
            if(registrationSet.size()>0){
                valueOperations.set(Constants.APP_USER+userId,new Gson().toJson(registrationSet,Set.class));
            } else {
                stringRedisTemplate.delete(Constants.APP_USER+userId);
            }

        }
    }


    public UserInfo getCurrentUserInfo(ContextDto contextDto) throws UnknownLoginException{
        UserInfo userInfo =new UserInfo();
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        if(!StringUtils.isEmpty(contextDto)){

            String json = valueOperations.get(Constants.APP_TOKEN+contextDto.getClientDigest());
            if(StringUtils.isEmpty(json)){
                throw new UnknownLoginException("用户未登陆");
            }
            Gson gson = new Gson();
            Map<String,String> userMap = gson.fromJson(json,Map.class);
            if(contextDto.getAccessToken() != null && userMap != null && contextDto.getAccessToken().equals(userMap.get("accessToken"))){
                userInfo = userInfoMapper.selectByPrimaryKey(Integer.parseInt(userMap.get("userId")));
            } else {
                throw new UnknownLoginException("用户登录失效");
            }
        }
        return userInfo;
    }



}
