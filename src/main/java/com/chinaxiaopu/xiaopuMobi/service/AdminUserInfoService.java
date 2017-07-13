package com.chinaxiaopu.xiaopuMobi.service;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.AdminListDto;
import com.chinaxiaopu.xiaopuMobi.mapper.UserInfoMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.UserMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.UserRoleMapper;
import com.chinaxiaopu.xiaopuMobi.model.User;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.model.UserRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by xiaohao on 2016/10/28.
 */
@Service
public class AdminUserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    /**
     * 管理员列表
     * @param adminListDto
     * @return
     */
    public PageInfo<UserInfo> getAdminList(AdminListDto adminListDto) {
        UserInfo userInfo=new UserInfo();
        //条件
        if(!StringUtils.isEmpty(adminListDto.getRealName()))
        {
            userInfo.setRealName(adminListDto.getRealName());
        }
        //分页
        if (!StringUtils.isEmpty(adminListDto.getPage()) && !StringUtils.isEmpty(adminListDto.getRows())) {
            PageHelper.startPage(adminListDto.getPage(), adminListDto.getRows());
        }

        List<UserInfo> userInfoList= userInfoMapper.selectUserInfoByRoleId(userInfo);//查询管理员的信息列表（roleId:1 管理员）
        PageInfo<UserInfo> pageInfo=new PageInfo<>(userInfoList);
        return pageInfo;
    }

    /**
     *添加管理员
     * @return
     */
    public Result add(AdminListDto adminListDto){
        Result result=new Result();
        User user=new User();
        UserInfo userInfo=new UserInfo();
        UserRole userRole=new UserRole();
        /** 手机号为空 */
        if (StringUtils.isEmpty(adminListDto.getUserInfo().getMobile())) {
            result.setResultCode(Constants.MOBILE_NULL);
            return result;
        } else {
            //判断手机号码是否已经注册过
            Long mobile = adminListDto.getUserInfo().getMobile();
            int count = userMapper.selectCountByMobile(mobile);
            if(count>0){
                result.setResultCode(Constants.USER_NOT_NULL);
                return result;
            }
        }
        //添加user信息
        Date date = new Date();
        user.setMobile(adminListDto.getUserInfo().getMobile());
        String salt = DigestUtils.md5Hex(RandomStringUtils.random(5));
        user.setPassword(DigestUtils.md5Hex(salt + adminListDto.getPassword()));
        user.setSalt(salt);
        user.setStatus(1);
        user.setJoinTime(date);
        user.setUpdateTime(date);
        user.setOrigin(1);
        int a= userMapper.insert(user);
        if(a>0)
        {
            //添加userInfo信息
            userInfo.setUserId(user.getId());
            userInfo.setUserSex(adminListDto.getUserInfo().getUserSex());  //新增性别
            userInfo.setRealName(adminListDto.getUserInfo().getRealName());
            userInfo.setSchoolId(adminListDto.getUserInfo().getSchoolId());
            userInfo.setSchoolName(adminListDto.getUserInfo().getSchoolName());
            userInfo.setQq(adminListDto.getUserInfo().getQq());
            userInfo.setStudentNo(adminListDto.getUserInfo().getStudentNo());
            userInfo.setMobile(adminListDto.getUserInfo().getMobile());
            userInfo.setValid(1);
            userInfo.setIsPresident(2);
            userInfo.setAvatarUrl("avatar.png");
            int b=userInfoMapper.insert(userInfo);
            if(b>0) {
                result.setResultCode(Result.SUCCESS);
            }else{
                result.setResultCode(Result.FAILURE);
            }

            //添加权限
            userRole.setUserId(user.getId());
            userRole.setRoleId(1);//系统管理员
            int c=userRoleMapper.insert(userRole);
            if(c>0) {
                result.setResultCode(Result.SUCCESS);
            }else{
                result.setResultCode(Result.FAILURE);
            }
        }
        return result;
    }

    /**
     * 删除管理员
     * @param userId
     * @return
     */
    public Result del(Integer userId) {
        Result result=new Result();
        if(!StringUtils.isEmpty(userId))
        {
            int a=userInfoMapper.deleteByPrimaryKey(userId);    //删除userInfo表数据
            if(a>0){
                result.setResultCode(Result.SUCCESS);
                int b=userMapper.deleteByPrimaryKey(userId);    //删除user表数据
                if(b>0){
                    result.setResultCode(Result.SUCCESS);
                    int c=userRoleMapper.deleteByPrimaryKey(userId);    //删除userRole表数据
                    if(c>0){
                        result.setResultCode(Result.SUCCESS);
                    }else{
                        result.setResultCode(Result.FAILURE);
                        return result;
                    }
                }else{
                    result.setResultCode(Result.FAILURE);
                    return result;
                }
            }else{
                result.setResultCode(Result.FAILURE);
                return result;
            }
        }else{
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

}
