package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.dto.EventUIDto;
import com.chinaxiaopu.xiaopuMobi.exception.UnknownLoginException;
import com.chinaxiaopu.xiaopuMobi.model.TopicPk;
import com.chinaxiaopu.xiaopuMobi.service.topic.PkFinishPrizeService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by ycy on 2016/11/14.
 */
@Slf4j
@Controller
@RequestMapping("/testSocket")
public class TestController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/redisDemo", method = RequestMethod.GET)
    @ResponseBody
    public Result redisDemo() throws InterruptedException {
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        Result result = new Result();
        try {

            String json = valueOperations.get("123456");
            if(StringUtils.isEmpty(json)){
                throw new UnknownLoginException("用户未登陆");
            }
            log.info("user json:"+json);
            for(int i=0;i<=10;i++) {
                stringRedisTemplate.convertAndSend("xiaopuChat", "Hello from Redis -->"+i);
//                Thread.sleep(2000);
            }
            log.info("insert redis is end");
            result.setMsg("insert redis is end");
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e){
            log.error(e.getMessage());
            result.setMsg(e.getMessage());
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @RequestMapping("/helloSocket")
    public String helloSocket(){

        return "helloSocket";
    }

    @RequestMapping("/socketMsg")
    public String socketMsg(){

        return "socketMsg";
    }

}
