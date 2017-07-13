package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.dto.SmsCaptchaDto;
import com.chinaxiaopu.xiaopuMobi.service.domain.*;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Steven@chinaxiaopu.com on 10/13/16.
 */
@Slf4j
@Controller
@RequestMapping("/user")
@ResponseBody
public class SmsController extends ContextController {

    @ApiOperation(value = "注册短信验证", notes = "6位数字，每90s可发送一次，每天最多10次")
    @RequestMapping(value = "/registerSms", method = RequestMethod.POST)
    public Result registerSms(@RequestBody SmsCaptchaDto smsCaptchaDto){
        String mobile = smsCaptchaDto.getMobile();
        assert StringUtils.isNotEmpty(mobile);
        Result result = new Result();
        if(smsService.allownSend(mobile).getResultCode() != Result.SUCCESS){
            return smsService.allownSend(mobile);
        }
        ISmsRequest smsRequest = new SmsRegisterRequest();
        try {
            smsRequest.setRecNum(mobile);
            String randomCode = RandomStringUtils.randomNumeric(6);
            smsRequest.setSmsParam(randomCode);
            smsService.sendSMS(smsRequest);
            log.debug(mobile+"发送注册短信成功");
            SmsStatus smsStatus = new SmsStatus(mobile);
            smsStatus.setLastRandom(String.valueOf(randomCode));
            smsService.updateSum(smsStatus);
            result.setResultCode(Result.SUCCESS);
            result.setMsg("发送注册短信成功");
        } catch (Exception e) {
            smsRequest = null;
            result.setResultCode(Result.FAILURE);
            result.setMsg("发送注册短信失败，请稍后再试");
            e.printStackTrace();
        }
        return result;
    }

    @ApiOperation(value = "修改密码短信验证", notes = "6位数字，每90s可发送一次，每天最多10次")
    @RequestMapping(value = "/changePasswordSms", method = RequestMethod.POST)
    public Result changePasswordSms(@RequestBody SmsCaptchaDto smsCaptchaDto){
        String mobile = smsCaptchaDto.getMobile();
        assert StringUtils.isNotEmpty(mobile);
        Result result = new Result();
        if(smsService.allownSend(mobile).getResultCode() != Result.SUCCESS){
            return smsService.allownSend(mobile);
        }
        ISmsRequest smsRequest = new SmsChangePwdRequest();
        try {
            smsRequest.setRecNum(mobile);
            String randomCode = RandomStringUtils.randomNumeric(6);
            smsRequest.setSmsParam(randomCode);
            smsService.sendSMS(smsRequest);
            log.debug(mobile+"发送修改密码短信成功");
            SmsStatus smsStatus = new SmsStatus(mobile);
            smsStatus.setLastRandom(String.valueOf(randomCode));
            smsService.updateSum(smsStatus);
            result.setResultCode(Result.SUCCESS);
            result.setMsg("发送修改密码短信成功");
        } catch (Exception e) {
            smsRequest = null;
            result.setResultCode(Result.FAILURE);
            result.setMsg("发送修改密码短信失败，请稍后再试");
            e.printStackTrace();
        }
        return result;
    }

    @ApiOperation(value = "发送登录验证码", notes = "6位数字，每90s可发送一次，每天最多10次")
    @RequestMapping(value = "/loginSms", method = RequestMethod.POST)
    public Result loginSms(@RequestBody SmsCaptchaDto smsCaptchaDto){
        String mobile = smsCaptchaDto.getMobile();
        assert StringUtils.isNotEmpty(mobile);
        Result result = new Result();
        if(smsService.allownSend(mobile).getResultCode() != Result.SUCCESS){
            return smsService.allownTimeSend(mobile);
        }
        ISmsRequest smsRequest = new SmsLoginRequest();
        try {
            smsRequest.setRecNum(mobile);
            String randomCode = RandomStringUtils.randomNumeric(6);
            smsRequest.setSmsParam(randomCode);
            smsService.sendSMS(smsRequest);
            SmsStatus smsStatus = new SmsStatus(mobile);
            smsStatus.setLastRandom(String.valueOf(randomCode));
            smsService.updateSum(smsStatus);
            result.setResultCode(Result.SUCCESS);
            result.setMsg("发送短信成功");
        } catch (Exception e) {
            smsRequest = null;
            result.setResultCode(Result.FAILURE);
            result.setMsg("发送短信失败，请稍后再试");
            e.printStackTrace();
        }
        return result;
    }

    @ApiOperation(value = "根据类型发送验证码", notes = "6位数字，每90s可发送一次，每天最多10次")
    @RequestMapping(value = "/sendSmsByType", method = RequestMethod.POST)
    public Result sendSmsByType(@RequestBody SmsCaptchaDto smsCaptchaDto){
        String mobile = smsCaptchaDto.getMobile();
        assert StringUtils.isNotEmpty(mobile);
        Result result = new Result();
        if(smsService.allownSend(mobile).getResultCode() != Result.SUCCESS){
            return smsService.allownSend(mobile);
        }
        ISmsRequest smsRequest = new SmsSendRequest();
        try {
            String smsType = smsCaptchaDto.getSmsType();
            StringBuffer textBuff = new StringBuffer();
            if(StringUtils.isNotEmpty(smsType) && "2".equals(smsType)){
                textBuff.append("社团");
            } else {
                textBuff.append("校园");
            }
            String randomCode = RandomStringUtils.randomNumeric(6);

            Map<String,String> smsParam = new HashMap<>();
            smsParam.put("code",randomCode);
            smsParam.put("product","校谱");
            smsParam.put("text",textBuff.toString());
            Gson gson = new Gson();
            smsRequest.setRecNum(mobile);
            smsRequest.setSmsParam(gson.toJson(smsParam));

            smsService.sendSMS(smsRequest);
            log.debug(mobile+"发送短信");
            SmsStatus smsStatus = new SmsStatus(mobile);
            smsStatus.setLastRandom(String.valueOf(randomCode));
            smsService.updateSum(smsStatus);
            result.setResultCode(Result.SUCCESS);
            result.setMsg("发送短信成功");
        } catch (Exception e) {
            smsRequest = null;
            result.setResultCode(Result.FAILURE);
            result.setMsg("发送短信失败，请稍后再试");
            e.printStackTrace();
        }
        return result;
    }
}
