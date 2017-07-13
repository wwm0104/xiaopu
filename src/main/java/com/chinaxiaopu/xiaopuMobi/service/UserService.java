package com.chinaxiaopu.xiaopuMobi.service;

import java.util.*;

import com.chinaxiaopu.xiaopuMobi.code.LoginResult;
import com.chinaxiaopu.xiaopuMobi.service.domain.SmsStatus;
import com.chinaxiaopu.xiaopuMobi.dto.*;
import com.chinaxiaopu.xiaopuMobi.mapper.*;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.chinaxiaopu.xiaopuMobi.vo.UserInvitationCodeVo;
import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.code.UserInfoResult;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by ellien date: 16/9/26
 */
@Slf4j
@Service
public class UserService extends AbstractService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private GroupMemberMapper groupMemberMapper;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private EventMemberMapper eventMemberMapper;
    @Autowired
    private InvitationCodeMapper invitationCodeMapper;
    @Autowired
    private UserInvitationCodeMapper userInvitationCodeMapper;
    @Autowired
    private InvitationCodeService invitationCodeService;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private AwardPresenMapper awardPresenMapper;
    @Autowired
    private UserTicketMapper userTicketMapper;
    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private LoginService loginService;



    public List<User> selectByAll() {
        UserExample example = new UserExample();
        return userMapper.selectByExample(example);
    }

    public UserInfo selectById(final Integer id){
        return userInfoMapper.selectByPrimaryKey(id);
    }

    public int insertUser(final User user) {
        return userMapper.insertSelective(user);
    }

    public User selectByMobile(Long mobile) {
        return userMapper.selectByMobile(mobile);
    }

    //生成10位随机数
    public String getRandomNickName(){
        String nickName = RandomStringUtils.randomAlphanumeric(10);
        int a=userInfoMapper.selectByNickName(nickName);
        if(a>0){
            getRandomNickName();
        }
        return Constants.NICK_NAME+nickName;
    }

    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    public UserInfo getUserInfoByUserId(final Integer id) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(id);

        return userInfo;
    }

    public UserInfoResult<UserInfo> getUserInfoAndCountByUserId(final Integer id) {
        UserInfoResult<UserInfo> result = new UserInfoResult<UserInfo>();
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(id);

        /** user为空 */
        if (StringUtils.isEmpty(userInfo)) {
            result.setResultCode(Result.FAILURE);
            return result;
        } else {
            result.setResultCode(Result.SUCCESS);
            result.setObj(userInfo);
        }
        GroupMember groupMember = new GroupMember();
        groupMember.setMemberId(userInfo.getUserId());
        groupMember.setStatus(1);
        result.setGroupCount(groupMemberMapper.selectByUserOpt(groupMember));

        EventMember eventMember = new EventMember();
        eventMember.setMemberId(id);
        result.setEventCount(eventMemberMapper.selectByUserOpt(eventMember));
        return result;
    }

    /**
     * friendList
     *
     * @param userInfo
     * @return
     */
    public List<UserInfo> getFriendListByUserId(UserInfo userInfo) {
        List<UserInfo> result = new ArrayList<UserInfo>();

        /** userId为空 */
        if (StringUtils.isEmpty(userInfo.getUserId())) {
            throw new RuntimeException("获取用户信息失败");
        }

        List<UserInfo> friendList = userInfoMapper.getFriendListByUserId(userInfo.getUserId());

        return friendList;
    }

    /**
     * 根据用户信息查询用户加入的社团列表
     *
     * @param userUIDto
     * @return
     */
    public List<Group> getMyGroupListByUserId(UserUIDto userUIDto,Integer status) {
        if (!StringUtils.isEmpty(userUIDto.getPage()) && !StringUtils.isEmpty(userUIDto.getRows())) {
            PageHelper.startPage(userUIDto.getPage(), userUIDto.getRows());
        }
        List<Group> myGroupList = groupMapper.getMyGroupListByUserId(userUIDto.getUserId(),status);

        return myGroupList;
    }
    public List<Group> getMyGroupTagsByUserId(UserUIDto userUIDto,Integer status) {
        List<Group> myGroupList = groupMapper.getMyGroupListByUserId(userUIDto.getUserId(),status);
        return myGroupList;
    }

    /**
     * 根据用户信息查询用户关注社团列表
     *
     * @param groupJoinDto
     * @return
     */
    public List<Group> getConcernGroupListByUserId(GroupJoinDto groupJoinDto,UserInfo userInfo) {

        if (!StringUtils.isEmpty(groupJoinDto.getPage()) && !StringUtils.isEmpty(groupJoinDto.getRows())) {
            PageHelper.startPage(groupJoinDto.getPage(), groupJoinDto.getRows());
        }
        List<Group> concernGroupList = groupMapper.getConcernGroupListByUserId(userInfo.getUserId());

        return concernGroupList;
    }

    /**
     * 根据用户ID获取该用户参加的活动列表
     *
     * @param userUIDto
     * @return
     */
    public List<Event> getMyEventListByUserId(UserUIDto userUIDto,Integer status) {
        if (!StringUtils.isEmpty(userUIDto.getPage()) && !StringUtils.isEmpty(userUIDto.getRows())) {
            PageHelper.startPage(userUIDto.getPage(), userUIDto.getRows());
        }
        List<Event> myEventList = eventMapper.getMyEventListByUserId(userUIDto.getUserId(),status);

        return myEventList;
    }
    public List<Event> getMyEventTagsByUserId(UserUIDto userUIDto,Integer status) {
        List<Event> myEventList = eventMapper.getMyEventListByUserId(userUIDto.getUserId(),status);
        return myEventList;
    }

    /**
     * 根据用户ID获取该用户关注活动列表
     *
     * @param userUIDto
     * @return
     */
    public List<Event> getConcernEventListByUserId(UserUIDto userUIDto,UserInfo userInfo) {

        if (!StringUtils.isEmpty(userUIDto.getPage()) && !StringUtils.isEmpty(userUIDto.getRows())) {
            PageHelper.startPage(userUIDto.getPage(), userUIDto.getRows());
        }
        List<Event> concernEventList = eventMapper.getConcernEventListByUserId(userInfo.getUserId());

        return concernEventList;
    }

    /**
     * 修改昵称
     *
     * @param userUploadDto,userInfo
     * @return
     */
    public Boolean nickNameUpdate(UserUploadDto userUploadDto,UserInfo userInfo) {
        userInfo.setNickName(userUploadDto.getNickName());
        int count = userInfoMapper.updateByPrimaryKey(userInfo);
        if(count==1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 修改头像
     *
     * @param userUploadDto
     * @return
     */
    public Boolean avatarUpdate(UserUploadDto userUploadDto,UserInfo userInfo) {
        userInfo.setAvatarUrl(userUploadDto.getAvatarUrl());
        int count = userInfoMapper.updateByPrimaryKey(userInfo);
        if(count==1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 用户注册
     *
     * @param registerUserDto
     * @return
     */
    @Transactional
    public LoginResult register(UserRegisterDto registerUserDto) {
        LoginResult result = new LoginResult();
        User user = new User();
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        /** 手机号为空 */
        if (StringUtils.isEmpty(registerUserDto.getMobile())) {
            result.setMsg("手机号为空");
            result.setResultCode(Constants.MOBILE_NULL);
            return result;
        } else {
            //判断手机号码是否已经注册过
            int count = userMapper.selectCountByMobile(registerUserDto.getMobile());
            if(count>0){
                result.setMsg("该手机号已注册");
                result.setResultCode(Constants.USER_NOT_NULL);
                return result;
            }
        }
        /** 密码为空 */
        if (StringUtils.isEmpty(registerUserDto.getPassword())) {
            result.setMsg("密码为空");
            result.setResultCode(Constants.PASSWORD_NULL);
            return result;
        } else {
            /** 密码不符合规则 */
            if (!(registerUserDto.getPassword().length() >= 6 && registerUserDto.getPassword().length() <= 16)) {
                result.setMsg("密码长度要在6到16之间");
                result.setResultCode(Constants.PASSWORD_ERROR);
                return result;
            }
        }
        /** Origin不能为空 */
        if (StringUtils.isEmpty(registerUserDto.getOrigin())) {
            result.setMsg("Origin为空");
            result.setResultCode(Result.FAILURE);
            return result;
        }
        //验证短信验证码是否正确
        result.setResultCode(confirmCaptcha(registerUserDto.getMobile(),registerUserDto.getSmsCaptcha()).getResultCode());
        if(result.getResultCode()!=1){
            result.setMsg("验证码错误");
            return result;
        }
        //验证邀请码是否正确
        if(!StringUtils.isEmpty(registerUserDto.getCode())) {
            int a = invitationCodeMapper.selectByCode(registerUserDto.getCode());
            int b =userInvitationCodeMapper.selectByCode(registerUserDto.getCode());
            if (a <= 0 && b<=0) {
                registerUserDto.setCode(""); //如果邀请码无  效置空
            }
        }

        Date date = new Date();
        user.setMobile(registerUserDto.getMobile());
        String salt = DigestUtils.md5Hex(RandomStringUtils.random(5));
        user.setPassword(DigestUtils.md5Hex(salt + registerUserDto.getPassword()));
        user.setSalt(salt);
        user.setStatus(1);
        user.setJoinTime(date);
        user.setUpdateTime(date);
        user.setOrigin(registerUserDto.getOrigin());

        /** 插入User */
        int count = userMapper.insertSelective(user);
        if (count == 1) {
            result.setResultCode(Result.SUCCESS);

            /** 查询userId */
            if (StringUtils.isEmpty(user.getId())) {
                result.setMsg("获取用户信息失败");
                result.setResultCode(Result.FAILURE);
                return result;
            }
            /**插入userInfo表信息*/
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(user.getId());
            userInfo.setNickName(getRandomNickName()); //注入生成昵称
            userInfo.setMobile(user.getMobile()); //注入手机号
            int b = userInfoMapper.insertSelective(userInfo);

            UserInvitationCode userInvitationCode=new UserInvitationCode();
            userInvitationCode.setUserCode(invitationCodeService.getInvitationCode());
            userInvitationCode.setUserId(user.getId());
            if(!StringUtils.isEmpty(registerUserDto.getCode()) || registerUserDto.getCode()!=""){
                userInvitationCode.setInvitationCode(registerUserDto.getCode());
            }
            int c = userInvitationCodeMapper.insert(userInvitationCode);

            //构造token登录
            String accessToken = DigestUtils.md5Hex(RandomStringUtils.random(10));
            //将token加入到redis中去
            Map<String,String> userMap = new HashMap<>();
            userMap.put("accessToken",accessToken);
            userMap.put("userId",user.getId().toString());

            Gson gson = new Gson();
            valueOperations.set(Constants.APP_TOKEN+registerUserDto.getClientDigest(), gson.toJson(userMap));

            result.setResultCode(Constants.ACCESS_OK);
            result.setClientDigest(registerUserDto.getClientDigest());
            result.setAccessToken(accessToken);
            result.setUserId(user.getId());

            String registrationId = registerUserDto.getRegistrationId();
            loginService.addUserRegistrationId(valueOperations,user.getId().toString(),registrationId);

        } else {
            result.setMsg("注册失败");
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 编辑用户信息
     *
     * @param userInfoCreateDto
     * @return
     */
    public Result userInfoUpdate(UserInfoCreateDto userInfoCreateDto,UserInfo userInfo) {
        Result result = new Result();

        /**已实名认证，不能修改*/
        if (StringUtils.isEmpty(userInfo.getValid()) || userInfo.getValid()==1) {
            result.setMsg("以实名认证");
            result.setResultCode(Result.FAILURE);
            return result;
        }

        if(StringUtils.isEmpty(userInfoCreateDto.getOrigin())){
            result.setMsg("Origin为空");
            result.setResultCode(Result.FAILURE);
            return result;
        }
        if(userInfoCreateDto.getOrigin()==1){
            result = confirmCaptcha(userInfoCreateDto.getMobile(),userInfoCreateDto.getSmsCaptcha());
            if(result.getResultCode()!=1){
                result.setMsg("短信验证码错误");
                return result;
            }
        }

        userInfo.setSchoolId(userInfoCreateDto.getSchoolId());
        userInfo.setSchoolName(userInfoCreateDto.getSchoolName());
        userInfo.setRealName(userInfoCreateDto.getName());
        userInfo.setStudentNo(userInfoCreateDto.getStudentNo());
        userInfo.setMobile(userInfoCreateDto.getMobile());
        userInfo.setQq(userInfoCreateDto.getQq());
        userInfo.setAvatarUrl(userInfoCreateDto.getAvatarUrl());
        if(!StringUtils.isEmpty(userInfoCreateDto.getUserSex())){
            userInfo.setUserSex(userInfoCreateDto.getUserSex());
        }

        int count = userInfoMapper.updateByPrimaryKeySelective(userInfo);

        if (count == 1) {
            result.setResultCode(Result.SUCCESS);
        } else {
            result.setMsg("编辑用户信息失败");
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 修改密码
     * @param registerUserDto
     * @return
     */
    public Result updatePassword(UserRegisterDto registerUserDto){
        Result result = new Result();

        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        /** 密码为空 */
        if (StringUtils.isEmpty(registerUserDto.getPassword())) {
            result.setMsg("密码为空");
            result.setResultCode(Constants.PASSWORD_NULL);
            return result;
        } else {
            /** 密码不符合规则 */
            if (!(registerUserDto.getPassword().length() >= 6 && registerUserDto.getPassword().length() <= 16)) {
                log.error(registerUserDto.getMobile()+":密码不符合规则->"+registerUserDto.getPassword());
                result.setMsg("密码要在6到16之间");
                result.setResultCode(Constants.PASSWORD_ERROR);
                return result;
            }
        }

        /** Origin不能为空 */
        if (StringUtils.isEmpty(registerUserDto.getOrigin())) {
            result.setMsg("Origin为空");
            result.setResultCode(Result.FAILURE);
            return result;
        }
        //验证短信验证码是否正确
        result = confirmCaptcha(registerUserDto.getMobile(),registerUserDto.getSmsCaptcha());
        if(result.getResultCode()!=1){
            return result;
        }

        User user = userMapper.selectByMobile(registerUserDto.getMobile());
        if(StringUtils.isEmpty(user)){
            result.setMsg("获取用户信息失败");
            result.setResultCode(Result.FAILURE);
            return result;
        }

        String salt = DigestUtils.md5Hex(RandomStringUtils.random(5));
        user.setPassword(DigestUtils.md5Hex(salt + registerUserDto.getPassword()));
        user.setSalt(salt);
        user.setUpdateTime(new Date());

        int count = userMapper.updateByPrimaryKeySelective(user);
        if(count==1){
            String accessToken = DigestUtils.md5Hex(RandomStringUtils.random(10));
            Map<String,String> userMap = new HashMap<>();
            userMap.put("accessToken",accessToken);
            userMap.put("userId",user.getId().toString());
            Gson gson = new Gson();
            valueOperations.set(Constants.APP_TOKEN+registerUserDto.getClientDigest(), gson.toJson(userMap));
            result.setResultCode(Result.SUCCESS);
        }else{
            result.setMsg("修改密码失败");
            result.setResultCode(Result.FAILURE);
        }

        return result;
    }

    /**
     * 绑定手机
     * @param userMobileDto
     * @return
     */
    public Result bindingMobile(UserMobileDto userMobileDto){
        Result result = new Result();

        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();

        //验证短信验证码是否正确
        result = confirmCaptcha(userMobileDto.getMobile(),userMobileDto.getSmsCaptcha());
        if(result.getResultCode()!=1){
            result.setMsg("短信验证码错误");
            return result;
        }
        User user = userMapper.selectByPrimaryKey(userMobileDto.getUserId());
        user.setUpdateTime(new Date());
        user.setMobile(userMobileDto.getMobile());

        int count = userMapper.updateByPrimaryKeySelective(user);

        if(count==1){
            result.setResultCode(Result.SUCCESS);
        }else{
            result.setMsg("绑定手机失败");
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 验证码是否正确
     * @param mobile
     * @param smsCaptcha
     *
     * */
    public Result confirmCaptcha(final Long mobile,final String smsCaptcha){
        Result result = new Result();
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();

        if (!StringUtils.isEmpty(smsCaptcha)) {
            try {
                String serverCaptcha = valueOperations.get("sms:"+mobile);
                Gson gson = new Gson();
                SmsStatus sum = gson.fromJson(serverCaptcha,SmsStatus.class);
                if (StringUtils.isEmpty(sum.getLastRandom()) || !sum.getLastRandom().equals(smsCaptcha)){
                    log.error("短信验证码错误:"+mobile);
                    result.setResultCode(Constants.CAPTCHA_ERROR);
                    result.setMsg("短信验证码错误");
                } else{
                    result.setResultCode(Constants.ACCESS_OK);
                }
                return result;
            } catch (NullPointerException e) {
                result.setMsg("短信验证码错误");
                result.setResultCode(Constants.CAPTCHA_ERROR);
                return result;
            }
        } else {
            result.setResultCode(Constants.CAPTCHA_NULL);
            result.setMsg("短信验证码不能为空");
            return result;
        }
    }

    /**
     * 判断当前登录人是否为管理员
     * @param userId
     * @return
     */
    public Result isHasAdmin(Integer userId){
        Result result=new Result();
        int a=userRoleMapper.selectByUserId(userId,1);
        if(a>0){
            result.setObj(1);
        }else{
            result.setObj(0);
        }
        result.setResultCode(Result.SUCCESS);
        return result;
    }



    /**
     * 判断当前登录人是否为社长
     * @param userId
     * @return
     */
    public Result isHasPresident(Integer userId){
        Result result=new Result();
        int a=userRoleMapper.selectByUserId(userId,2);
        if(a>0){
            result.setObj(1);
        }else{
            result.setObj(0);
        }
        result.setResultCode(Result.SUCCESS);
        return result;
    }


    /**
     * 判断是否为发奖人
     * @param userInfo
     * @return
     */
    public Result isHasAwardPresen(UserInfo userInfo){
        Result result=new Result();
        if(StringUtils.isEmpty(userInfo.getMobile()) || StringUtils.isEmpty(userInfo.getUserId())){
            result.setMsg("用户信息不完整");
            result.setResultCode(Result.FAILURE);
            return result;
        }
        int a=awardPresenMapper.selectByMobile(userInfo.getMobile());  //判断是否为发奖人
        //int b=userRoleMapper.selectByUserId(userInfo.getUserId(),2);
        int b=groupMapper.selectCntByUserId(userInfo.getUserId());     //判断是否为社长
        int c=ticketMapper.selectCntByUserId(userInfo.getUserId());    //判断是否创建电子门票活动
//        if(a>0 || (b>0 && c>0)){
//            result.setObj(1);
//        }else{
//            result.setObj(0);
//        }
        if(a>0){  //发奖人
            result.setObj(1);
            result.setMsg("拥有发奖人扫码功能");
        }else if(b>0 && c>0){  //社长电子门票
            result.setObj(1);
            result.setMsg("拥有社长扫码功能");
        }else{
            result.setObj(0);
            result.setMsg("未拥有扫码功能");
        }
        result.setResultCode(Result.SUCCESS);
        return result;
    }

    /**
     * 根据用户获取用户邀请人员注册数量
     * @param userCode
     * @return
     */
    public Integer selectInvitationCodeCntByUserCode(String userCode){
        return userInvitationCodeMapper.selectInvitationCodeCntByUserCode(userCode);
    }

    /**
     * 根据用户获取用户邀请人员注册数量
     * @param userInfo
     * @return
     */
    public UserInvitationCodeVo getUserCode(UserInfo userInfo) throws Exception{
        UserInvitationCodeVo vo = new UserInvitationCodeVo();

        BeanUtils.copyProperties(vo,userInfo);

        UserInvitationCode userInvitationCode = userInvitationCodeMapper.selectByUserId(userInfo.getUserId());
        if (userInvitationCode==null){
            userInvitationCode = new UserInvitationCode();
            userInvitationCode.setUserId(userInfo.getUserId());
            userInvitationCode.setUserCode(invitationCodeService.getInvitationCode());
            userInvitationCodeMapper.insert(userInvitationCode);
        }
        vo.setUserCode(userInvitationCode.getUserCode());
        return vo;
    }

}