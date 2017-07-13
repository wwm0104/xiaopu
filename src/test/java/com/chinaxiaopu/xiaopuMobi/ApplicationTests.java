package com.chinaxiaopu.xiaopuMobi;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.MsgFromDto;
import com.chinaxiaopu.xiaopuMobi.dto.MsgViewDto;
import com.chinaxiaopu.xiaopuMobi.mapper.EventLotteryMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.InvitationCodeMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.UserVrActivityMapper;
import com.chinaxiaopu.xiaopuMobi.model.AppVersion;
import com.chinaxiaopu.xiaopuMobi.model.EventLottery;
import com.chinaxiaopu.xiaopuMobi.model.EventLotteryExample;
import com.chinaxiaopu.xiaopuMobi.model.User;
import com.chinaxiaopu.xiaopuMobi.service.*;
import com.google.gson.Gson;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.annotations.Param;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * Created by liuwei
 * date: 16/8/18
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class ApplicationTests {

    Map<String,String> testMap = new HashMap();
   List<String> list = new ArrayList<>();
    List<String> list1 = new LinkedList<>();

//    @Autowired
//    UserService userService;
//
    @Autowired
    StringRedisTemplate stringRedisTemplate;

//    @Autowired
//    CountDownLatch countDownLatch;


//
//    @Autowired
//    OSSClientService ossClientService;
//
//    @Autowired
//    AppVersionService appVersionService;
//
//    @Autowired
//    InvitationCodeService invitationCodeService;
//
//    @Autowired
//    InvitationCodeMapper invitationCodeMapper;
//
//    @Autowired
//    UserVrActivityMapper userVrActivityMapper;

    @Autowired
    MsgPushService msgPushService;
    @Autowired
    EventLotteryMapper eventLotteryMapper;


    @Before
    public void setUp() {

    }

    @Test
    public void test() throws Exception {

        int action = Constants.TOPIC_USER_LIKE;
        int userId = 1;
        int sender = 3;
        MsgFromDto msgFromDto = new MsgFromDto();
        msgFromDto.setUserId(3);
        msgFromDto.setUserName("小龙");
        msgFromDto.setTopicId(12);
        msgFromDto.setTopicName("图文标题---1");
        Set<Integer> users = new HashSet<>();
        users.add(userId);
        msgPushService.sendPushMsgByUser(action,users,sender,msgFromDto);
        System.out.println("push is end");

//        StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
//        CountDownLatch latch = ctx.getBean(CountDownLatch.class);


//        System.exit(0);

//        JPushClient jpushClient = new JPushClient("a28f341af7fac6a50494391e", "acc006fdf8ac5b5e09a01387");
//
//        // For push, all you need do is to build PushPayload object.
//        PushPayload payload = PushPayload.alertAll("hello world ");
//
//        try {
//            PushResult result = jpushClient.sendPush(payload);
//            log.info("Got result - " + result);
//
//        } catch (APIConnectionException e) {
//            // Connection error, should retry later
//            log.error("Connection error, should retry later", e);
//
//        } catch (APIRequestException e) {
//            // Should review the error, and fix the request
//            log.error("Should review the error, and fix the request", e);
//            log.info("HTTP Status: " + e.getStatus());
//            log.info("Error Code: " + e.getErrorCode());
//            log.info("Error Message: " + e.getErrorMessage());
//        }
        
        
//        System.out.println(userVrActivityMapper.selectSumCntByUserId(180));
//        System.out.println(invitationCodeMapper.selectByCode("YpQPw1"));
//        AppVersion appVersion = appVersionService.selectLastVersionByAppId("2");
//        System.out.println(appVersion.getVerison());
//        List list = new ArrayList();
//        Set set = new HashSet();
//        System.out.println(invitationCodeService.getInvitationCode());
//        for (int i=0;i<=100;i++){
//            String code = invitationCodeService.getInvitationCode();
//            list.add(code);
//            set.add(code);
//
//        }
//        System.out.println("list seize:"+list.size());
//        System.out.println(" set seize:"+set.size());


//        BufferedInputStream in = new BufferedInputStream(new FileInputStream("/Users/daer/webdata/imgs/group.png"));
//        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
//        byte[] temp = new byte[1024];
//        int size = 0;
//        while ((size = in.read(temp)) != -1) {
//            out.write(temp, 0, size);
//        }
//        in.close();
//        byte[] content = out.toByteArray();
////        ossClientService.putOSSByByte("group.jpg",content);
//        System.out.println("putOSSByByte is end");

        ///Users/daer/webdata/imgs/event

//        userService.testTransactional();
//        System.out.println("11111");

//        ossClientService.putOSSByFilePath("event_qr_58.png","/Users/daer/webdata/imgs/event/event_qr_58.png");
//        String openId= "oeuncvilYGGN42XCNXOoFriqrU8c";
//        String userId= "227";
//        String clientDigest = DigestUtils.md5Hex(openId);
//        String accessToken = DigestUtils.md5Hex(RandomStringUtils.random(10));
//
//        Map<String,String> userMap = new HashMap<>();
//        userMap.put("accessToken",accessToken);
//        userMap.put("openid",openId);
//        userMap.put("userId",userId);
//        Gson gson = new Gson();
//        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
//        valueOperations.set(clientDigest, gson.toJson(userMap));
//        System.out.println("clientDigest:"+clientDigest);
//        System.out.println("accessToken:"+gson.fromJson(valueOperations.get(clientDigest),Map.class).get("accessToken"));

//        String url = "http://gw.api.taobao.com/router/rest";
//        String appkey = "23480040";
//        String secret = "74f58b4ec79d1047a6049a50eea0046b";
//        String smsCaptcha = RandomStringUtils.randomNumeric(6);
//        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//        req.setSmsType("normal");
//        req.setSmsFreeSignName("大鱼测试");
//        Gson gson = new Gson();
//        Map<String,String> map = new HashMap<>();
//        map.put("code",smsCaptcha);
//        map.put("product","校普");
//        req.setSmsParamString(gson.toJson(map));
//        req.setRecNum("15618956058");
//        req.setSmsTemplateCode("SMS_17945052");
//        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//        System.out.println(rsp.getBody());

//        redisTemplate.delete("63501b603"+ Constants.CAPTCHA_DIGEST);

//        DigestUtils.md5Hex(str);
//        System.out.println("--->"+loginService.authlogin("admin"));
    }

    @Test
    public void test2() throws Exception {

        int action = Constants.EVENT_JOIN_AUDIT_OK;
        int userId = 1;
        int sender = 6;
        MsgFromDto msgFromDto = new MsgFromDto();
        msgFromDto.setEventId(1);
        msgFromDto.setEventName("活动名称");
        Set<Integer> users = new HashSet<>();
        users.add(userId);
        msgPushService.sendPushMsgByUser(action,users,sender,msgFromDto);
        System.out.println("push is end");
    }

    //测试插入event_lottery数据
    @Test
    public void test3() throws Exception {
        EventLottery eventLottery = new EventLottery();
        eventLottery.setEventId(1);
        eventLottery.setEventName("活动测试");
        eventLottery.setStauts(0);
        eventLottery.setCreateTime(new Date());
        eventLottery.setUpdateTime(new Date());
        eventLotteryMapper.insert(eventLottery);

    }

}