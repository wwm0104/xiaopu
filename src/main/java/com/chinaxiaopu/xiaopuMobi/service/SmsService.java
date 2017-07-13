package com.chinaxiaopu.xiaopuMobi.service;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.service.domain.ISmsRequest;
import com.chinaxiaopu.xiaopuMobi.service.domain.SmsStatus;
import com.google.gson.Gson;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Steven@chinaxiaopu.com on 10/13/16.
 *
 */
@Slf4j
@Service
public class SmsService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 发送短信接口
     * 每次时间间隔90s，每天每手机号发送不超过10次
     * @param smsRequest 手机号码、短信模版参数必须设置
     * */
    public boolean sendSMS(ISmsRequest smsRequest) throws ApiException {
        SmsConfig smsConfig = SmsFactory.getInstance(SmsFactory.ALIDAYU);//TODO:修改为配置文件实现
        TaobaoClient client = new DefaultTaobaoClient(smsConfig.getUrl(), smsConfig.getAppkey(), smsConfig.getSecret());
        AlibabaAliqinFcSmsNumSendRequest taobaoRequest = new AlibabaAliqinFcSmsNumSendRequest();
        BeanUtils.copyProperties(smsRequest,taobaoRequest);
        log.debug(smsRequest.getClass()+":"+taobaoRequest.getRecNum()+","+taobaoRequest.getSmsParam());
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(taobaoRequest);
        return rsp.isSuccess();
    }

    public Result allownSend(String mobile){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        SmsStatus sum ;
        Result result = new Result();
        try {
            Gson gson = new Gson();
            sum = gson.fromJson(valueOperations.get("sms:"+mobile),SmsStatus.class);
            if(sum == null) {
                result.setResultCode(Result.SUCCESS);
                result.setMsg("允许发送");
                return result;
            }

            long last = sum.getLast();
            int todayCnt = sum.getTodayCnt();
            //两次短信间隔小于90S 或当天发送大于10，限制用户短信发送
            if(System.currentTimeMillis()-last < Integer.valueOf(SystemConfig.getInstance().getSmsDuration())*1000) {
                throw new RuntimeException("短信发送小于" + SystemConfig.getInstance().getSmsDuration() + "秒");
            } else if(isSameDay(last) && todayCnt >SystemConfig.getInstance().getSmsTodayLimit()) {
                throw new RuntimeException("当天短信发送大于" + SystemConfig.getInstance().getSmsTodayLimit() + "次");
            }
            result.setResultCode(Result.SUCCESS);
        }catch (Exception e){
            result.setResultCode(Result.FAILURE);
            result.setMsg("短信服务异常："+e.getMessage());
            return result;
        }
        return result;
    }

    public Result allownTimeSend(String mobile){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        SmsStatus sum ;
        Result result = new Result();
        try {
            Gson gson = new Gson();
            sum = gson.fromJson(valueOperations.get("sms:"+mobile),SmsStatus.class);
            if(sum == null) {
                result.setResultCode(Result.SUCCESS);
                result.setMsg("允许发送登陆短信");
                return result;
            }

            long last = sum.getLast();
            int todayCnt = sum.getTodayCnt();
            //两次短信间隔小于120S ，限制用户短信发送
            if(System.currentTimeMillis()-last < Integer.valueOf(SystemConfig.getInstance().getSmsDuration())*1000) {
                throw new RuntimeException("短信发送小于" + SystemConfig.getInstance().getSmsDuration() + "秒");
            } else if(isSameDay(last) && todayCnt >100) {
                throw new RuntimeException("当天短信发送大于100次");
            }
            result.setResultCode(Result.SUCCESS);
        }catch (Exception e){
            result.setResultCode(Result.FAILURE);
            result.setMsg("短信服务异常："+e.getMessage());
            return result;
        }
        return result;
    }

    /**
     *
     * @param smsStatus  mobile smsParam必须设置
     * @return
     */
    public Result updateSum(SmsStatus smsStatus){
        assert smsStatus != null && StringUtils.isNotEmpty(smsStatus.getMobile());
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        String json  = valueOperations.get("sms:"+smsStatus.getMobile());
        SmsStatus sum = null;
        if(StringUtils.isNotBlank(json)) {
            Gson gson = new Gson();
            sum = gson.fromJson(json,SmsStatus.class);
            if(!isSameDay(sum.getLast())){
                sum.setTodayCnt(0);
            }
        } else {
            sum = new SmsStatus(smsStatus.getMobile());
        }
        sum.setLast(System.currentTimeMillis());
        sum.setMobile(smsStatus.getMobile());
        sum.setTodayCnt(sum.getTodayCnt()+1);
        sum.setLastRandom(smsStatus.getLastRandom());
        Gson gson = new Gson();
        valueOperations.set("sms:"+sum.getMobile(),gson.toJson(sum));
        Result result = new Result();
        result.setResultCode(Result.SUCCESS);
        return result;
    }

    private boolean isSameDay(long day){
        Date date = new Date(day);
        Date current = new Date(System.currentTimeMillis());
        return DateUtils.isSameDay(date,current);
    }

}


interface SmsConfig extends IService {
    public String getAppkey();
    public String getSecret();
    public String getUrl();
}


class SmsFactory{
    public static final String ALIDAYU="ALIDAYU";
    private static  SmsConfig smsconfig;
    public static SmsConfig getInstance(String whichSmsProvider){
        assert StringUtils.isNoneEmpty(whichSmsProvider);
        if(whichSmsProvider.equals(ALIDAYU)){
            if (smsconfig == null)
                smsconfig = new AliDayuSmsConfig();
        }
        return smsconfig;
    }
    static final class AliDayuSmsConfig implements SmsConfig{
        @Getter
        private String Appkey = "23480040";
        @Getter
        private String secret = "74f58b4ec79d1047a6049a50eea0046b";
        @Getter
        private String url = "https://eco.taobao.com/router/rest";
    }
}



