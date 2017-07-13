package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.PartnerDto;
import com.chinaxiaopu.xiaopuMobi.dto.UserMobileDto;
import com.chinaxiaopu.xiaopuMobi.dto.partner.CreatePartnerDto;
import com.chinaxiaopu.xiaopuMobi.service.PartnerService;
import com.chinaxiaopu.xiaopuMobi.service.UserService;
import com.chinaxiaopu.xiaopuMobi.vo.partner.GroupListVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created by liuwei
 * date: 2016/10/21
 */
@Slf4j
@RestController
@RequestMapping("/partner")
public class PartnerController extends ContextController{

    @Autowired
    PartnerService partnerService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "校园合伙人加入-王运豪", notes = "校园合伙人加入\n" +
            "返回值：0代表失败，1代表成功，13,该用户已经存在,31代表验证码为空，32代表验证码错误")
    @RequestMapping(value="/anonApi/join", method = RequestMethod.POST)
    public Result create(@RequestBody PartnerDto partnerDto) {
        Result result = new Result();
        try {
            //验证短信码是否正确
            result = userService.confirmCaptcha(partnerDto.getMobile(),partnerDto.getSmsCaptcha());
            if(result.getResultCode()!=Result.SUCCESS){
                result.setMsg("验证码错误");
                return result;
            }
            //判断该手机号码是否已经加入
            int count = partnerService.selectCountByMobile(partnerDto.getMobile());
            if(count>0){
                result.setResultCode(Constants.USER_NOT_NULL);
                result.setMsg("该手机号码已申请");
                return result;
            }
            partnerService.insertPartner(partnerDto);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("合伙人加入失败", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg("合伙人加入失败");

        }
        return result;
    }
    @ApiOperation(value = "验证手机号是否为社长-王运豪",notes = "验证手机是否为社长\n"+
            "接收值：手机号\r"+"返回值：0失败,1成功并返回拥有社团集合")
    @RequestMapping(value = "/mobileIsPresident",method = RequestMethod.POST)
    public Result<List<GroupListVo>> moblieIsGroup(@RequestBody UserMobileDto userMobileDto){
        Result<List<GroupListVo>> result=new Result();
        try {
            List<GroupListVo> groupListVos=partnerService.selectByMobile(userMobileDto.getMobile());
            if(groupListVos.isEmpty()){
                result.setResultCode(Result.FAILURE);
                result.setMsg("您不是社长");
                return result;
            }else{
                result.setObj(groupListVos);
                result.setResultCode(Result.SUCCESS);
                return result;
            }
        }catch (Exception e){
            log.error("请求社团列表错误",e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

    @ApiOperation(value = "社团合伙人加入-王运豪", notes = "社团合伙人加入\n"+
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/createGroupPartner",method = RequestMethod.POST)
    public Result createGroupPartner(@RequestBody CreatePartnerDto createPartnerDto){
        Result result=new Result();
        try {
            //验证短信码是否正确
            result = userService.confirmCaptcha(createPartnerDto.getMobile(),createPartnerDto.getSmsCaptcha());
            if(result.getResultCode()!=Result.SUCCESS){
                result.setMsg("验证码错误");
                return result;
            }
            //判断该手机号码是否已经加入
            int count = partnerService.selectCountByMobile1(createPartnerDto.getMobile());
            if(count>0){
                result.setResultCode(Constants.USER_NOT_NULL);
                result.setMsg("该手机号码已申请");
                return result;
            }

            if(StringUtils.isEmpty(createPartnerDto.getGroupId())){
                result.setResultCode(Result.FAILURE);
                result.setMsg("请选择社团");
                return result;
            }
            if(StringUtils.isEmpty(createPartnerDto.getRealName())){
                result.setResultCode(Result.FAILURE);
                result.setMsg("请填写真实姓名");
                return result;
            }
            List<GroupListVo> groupListVos=partnerService.selectByMobile(createPartnerDto.getMobile());
            if(groupListVos.isEmpty()){
                result.setResultCode(Result.FAILURE);
                result.setMsg("您不是社长");
                return result;
            }
            return partnerService.insertGroupPartner(createPartnerDto);
        }catch (Exception e){
            log.error("POST请求 社团合伙人加入失败",e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }
}
