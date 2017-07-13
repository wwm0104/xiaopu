package com.chinaxiaopu.xiaopuMobi.service.authorization;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.authorization.RolesDto;
import com.chinaxiaopu.xiaopuMobi.dto.authorization.UserCreateDto;
import com.chinaxiaopu.xiaopuMobi.dto.authorization.UserInfoSelectDto;
import com.chinaxiaopu.xiaopuMobi.mapper.UserInfoMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.UserMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.UserRoleMapper;
import com.chinaxiaopu.xiaopuMobi.model.User;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.vo.authorization.UserRoleVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Ellie on 2016/12/2.
 */
@Service
public class UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    public UserRoleVo findUserInfoByUserId(int id) {
        return userInfoMapper.selectUserRoleById(id);
    }

    /**
     * 查询所有用户信息
     *
     * @param userInfoSelectDto
     * @return
     */
    public PageInfo<UserRoleVo> findAll(UserInfoSelectDto userInfoSelectDto) {

        if (!StringUtils.isEmpty(userInfoSelectDto.getPage()) && !StringUtils.isEmpty(userInfoSelectDto.getRows())) {
            PageHelper.startPage(userInfoSelectDto.getPage(), userInfoSelectDto.getRows());
        }

        List<UserRoleVo> userRoleVoList = userInfoMapper.selectUserRoleVoList(userInfoSelectDto);

        PageInfo<UserRoleVo> userRoleVoPageInfo = new PageInfo<>(userRoleVoList);
        return userRoleVoPageInfo;
    }

    /**
     * 获取用户信息以及角色信息
     *
     * @param userInfoSelectDto
     * @return
     */
    public PageInfo<UserRoleVo> getUserInfoList(UserInfoSelectDto userInfoSelectDto) {
        UserInfo userInfo = new UserInfo();

        //分页
        if (!StringUtils.isEmpty(userInfoSelectDto.getPage())) {
            userInfo.setPage(userInfoSelectDto.getPage());
        }
        if (!StringUtils.isEmpty(userInfoSelectDto.getRows())) {
            userInfo.setRows(userInfoSelectDto.getRows());
        }

        if (userInfo.getPage() != null && userInfo.getRows() != null) {
            PageHelper.startPage(userInfo.getPage(), userInfo.getRows());//设置起始位置
        }
        List<UserRoleVo> userRoleVoList = userInfoMapper.selectUserInfoByKeyWord(userInfoSelectDto);
        PageInfo<UserRoleVo> userRoleVoPageInfo = new PageInfo<>(userRoleVoList);

        return userRoleVoPageInfo;
    }

    public List<UserInfo> findUserInfoListUnderRole(int roleId) {
        List<UserInfo> userInfoList = userInfoMapper.selectUserInfoListByRoleId(roleId);
        return userInfoList;
    }

    /**
     * 查询角色下的所有用户
     *
     * @param rolesDto
     * @return
     */

    public PageInfo<UserInfo> findUserInfoListUnderRole(RolesDto rolesDto) {
        UserInfo userInfo = new UserInfo();

        //分页
        if (!StringUtils.isEmpty(rolesDto.getPage())) {
            userInfo.setPage(rolesDto.getPage());
        }
        if (!StringUtils.isEmpty(rolesDto.getRows())) {
            userInfo.setRows(rolesDto.getRows());
        }

        if (userInfo.getPage() != null && userInfo.getRows() != null) {
            PageHelper.startPage(userInfo.getPage(), userInfo.getRows());//设置起始位置
        }
        List<UserInfo> userInfoList = userInfoMapper.selectUserInfoListByRoleId(rolesDto.getRoleId());
        PageInfo<UserInfo> userInfoPageInfo = new PageInfo<>(userInfoList);

        return userInfoPageInfo;
    }

    /**
     * 查询不在此角色下的用户
     *
     * @param userInfoSelectDto
     * @return
     */
    public PageInfo<UserInfo> findUserNotInRole(UserInfoSelectDto userInfoSelectDto) {


        if (userInfoSelectDto.getPage() != null && userInfoSelectDto.getRows() != null) {
            PageHelper.startPage(userInfoSelectDto.getPage(), userInfoSelectDto.getRows());//设置起始位置
        }
        List<UserInfo> userInfoList = userInfoMapper.selectNotInRoleByKeyWord(userInfoSelectDto);
        PageInfo<UserInfo> userInfoPageInfo = new PageInfo<>(userInfoList);

        return userInfoPageInfo;
    }


    public int updateUserInfo(UserInfo userInfo) {
        return userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }


    public Result createUser(UserCreateDto userCreateDto) {
        Result result = new Result();
        User user = new User();
        UserInfo userInfo = new UserInfo();
        /** 手机号为空 */
        if (StringUtils.isEmpty(userCreateDto.getUserInfo().getMobile())) {
            result.setResultCode(Constants.MOBILE_NULL);
            return result;
        } else {
            System.out.println("验证手机号是否已经注册");
            Long mobile = userCreateDto.getUserInfo().getMobile();
            int count = userMapper.selectCountByMobile(mobile);
            if (count > 0) {
                result.setResultCode(Constants.USER_NOT_NULL);
                return result;
            }
        }
        //添加user信息
        Date date = new Date();
        user.setMobile(userCreateDto.getUserInfo().getMobile());
        String salt = DigestUtils.md5Hex(RandomStringUtils.random(5));
        user.setPassword(DigestUtils.md5Hex(salt + userCreateDto.getPassword()));
        user.setSalt(salt);
        user.setStatus(1);
        user.setShortName(userCreateDto.getUserInfo().getNickName());
        user.setJoinTime(date);
        user.setUpdateTime(date);
        user.setOrigin(1);
        int a = userMapper.insert(user);
        if (a > 0) {
            //添加userInfo信息
            userInfo.setUserId(user.getId());
            userInfo.setUserSex(userCreateDto.getUserInfo().getUserSex());  //新增性别
            userInfo.setRealName(userCreateDto.getUserInfo().getRealName());
            userInfo.setSchoolId(userCreateDto.getUserInfo().getSchoolId());
            userInfo.setSchoolName(userCreateDto.getUserInfo().getSchoolName());
            userInfo.setQq(userCreateDto.getUserInfo().getQq());
            userInfo.setStudentNo(userCreateDto.getUserInfo().getStudentNo());
            userInfo.setMobile(userCreateDto.getUserInfo().getMobile());
            userInfo.setNickName(userCreateDto.getUserInfo().getNickName());
            userInfo.setValid(1);
            userInfo.setIsPresident(2);
            userInfo.setAvatarUrl("avatar.png");
            int b = userInfoMapper.insert(userInfo);
            if (b > 0) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
            }

        }
        return result;
    }

}
