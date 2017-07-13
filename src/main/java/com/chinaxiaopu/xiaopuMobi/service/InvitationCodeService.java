package com.chinaxiaopu.xiaopuMobi.service;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.mapper.InvitationCodeMapper;
import com.chinaxiaopu.xiaopuMobi.model.InvitationCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by liuwei
 * date: 2016/11/4
 */
@Service
public class InvitationCodeService {

    @Autowired
    InvitationCodeMapper invitationCodeMapper;

    /**
     * 生成推广邀请码唯一标识
     *
     * @return invitationCode 邀请码
     * */
    public String getInvitationCode(){
        String invitationCode = RandomStringUtils.randomAlphanumeric(5);
        int count = invitationCodeMapper.selectByCode(invitationCode);
        if (count>0){
            getInvitationCode();
        }
        return invitationCode;

    }
    //判断是否为正确邀请码
    public int selectByCode(String invitationCode){
        return invitationCodeMapper.selectByCode(invitationCode);
    }

    public Result addCode(InvitationCode invitationCode){
        Result result=new Result();
        if(StringUtils.isEmpty(invitationCode.getCode())){
            result.setResultCode(Result.FAILURE);
            result.setMsg("邀请码不能为空");
            return result;
        }
        if(StringUtils.isEmpty(invitationCode.getPartnerId())){
            result.setResultCode(Result.FAILURE);
            result.setMsg("合伙人无效");
            return result;
        }
        int a=invitationCodeMapper.selectByCode(invitationCode.getCode());
        if(a>0){
            result.setResultCode(Result.FAILURE);
            result.setMsg("邀请码无效");
            return result;
        }
        InvitationCode invitationCode1=new InvitationCode();
        invitationCode1.setPartnerId(invitationCode.getPartnerId());
        invitationCode1.setCode(invitationCode.getCode());
        invitationCode1.setCreateTime(new Date());
        invitationCode1.setUserCnt(0);
        int b=invitationCodeMapper.insertSelective(invitationCode1);
        if(b>0){
            result.setResultCode(Result.SUCCESS);
            result.setMsg("添加成功");
        }
        return result;
    }
}
