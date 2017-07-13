package com.chinaxiaopu.xiaopuMobi;

import com.chinaxiaopu.xiaopuMobi.vo.topic.PrizeViewVo;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by liuwei
 * date: 16/10/17
 */
public class MyTest {

    public static void main(String[] args) {
        String str = "{0}点赞了您的\"{1}\"";
//        {"":"","":}

        System.out.println(MessageFormat.format(str,new Object[]{"小龙","小龙我最帅图文"}));
//        String payloadString = "{\"platform\":{\"all\":false,\"deviceTypes\":[\"IOS\"]},\"audience\":{\"all\":false,\"targets\":[{\"audienceType\":\"TAG_AND\",\"values\":[\"tag1\",\"tag_all\"]}]},\"notification\":{\"notifications\":[{\"soundDisabled\":false,\"badgeDisabled\":false,\"sound\":\"happy\",\"badge\":\"5\",\"contentAvailable\":false,\"alert\":\"Test from API Example - alert\",\"extras\":{\"from\":\"JPush\"},\"type\":\"cn.jpush.api.push.model.notification.IosNotification\"}]},\"message\":{\"msgContent\":\"Test from API Example - msgContent\"},\"options\":{\"sendno\":1429488213,\"overrideMsgId\":0,\"timeToLive\":-1,\"apnsProduction\":true,\"bigPushDuration\":0}}";
//
//        System.out.println(payloadString);
    }

//    public static void main(String[] args) throws Exception {
//        System.out.println("hello world");
//    }
//
//
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(
//                        new TypeToken<Map<String, Object>>(){}.getType(),
//                        new JsonDeserializer<Map<String, Object>>() {
//                            @Override
//                            public Map<String, Object> deserialize(
//                                    JsonElement json, Type typeOfT,
//                                    JsonDeserializationContext context) throws JsonParseException {
//
//                                Map<String, Object> treeMap = new HashMap<>();
//                                JsonObject jsonObject = json.getAsJsonObject();
//                                Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
//                                for (Map.Entry<String, JsonElement> entry : entrySet) {
//                                    treeMap.put(entry.getKey(), entry.getValue());
//                                }
//                                return treeMap;
//                            }
//                        }).create();
//        String jsonStr = "{\n" +
//                "  \"msg\": \"string\",\n" +
//                "  \"obj\": {\n" +
//                "    \"code\": \"string\",\n" +
//                "    \"hasTake\": 1,\n" +
//                "    \"prize\": \"{'sss':'111','ttt':'222'}\",\n" +
//                "    \"prizeId\": 1,\n" +
//                "    \"prizeName\": \"string\",\n" +
//                "    \"prizeNum\": 5,\n" +
//                "    \"topicId\": 10,\n" +
//                "    \"topicSlogan\": \"string\"\n" +
//                "  },\n" +
//                "  \"resultCode\": 1\n" +
//                "}";
//        System.out.println(jsonStr);
//        Map<String,String> map =  gson.fromJson(jsonStr, new TypeToken<Map<String, Object>>(){}.getType());
//
//        System.out.println(map);
//
//        Map<String,Object> map1= new HashMap<>();
//        map1.put("111",1);
//        map1.put("222",3);
//        String jsonstr1 = gson.toJson(map1);
//        System.out.println(jsonstr1);
////
////        String salt = DigestUtils.md5Hex(RandomStringUtils.random(5));
////        String password = DigestUtils.md5Hex(salt + "61a802726");
////
////        System.out.println("salt:"+salt);
////        System.out.println("password:"+password);
////        List<PrizeViewVo> list = new ArrayList();
////        for (int i=0;i<10;i++){
////            PrizeViewVo prizeViewVo = new PrizeViewVo();
////            if (i<5){
////                prizeViewVo.setType(1);
////            } else {
////                prizeViewVo.setType(2);
////            }
////            prizeViewVo.setCode("code"+i);
////            list.add(prizeViewVo);
////        }
////        Map<Integer,List<PrizeViewVo>> map = new HashMap<>();
////
////        for (PrizeViewVo prizeViewVo : list) {
////            if(map.containsKey(prizeViewVo.getType())){
////                List<PrizeViewVo> _list = map.get(prizeViewVo.getType());
////                _list.add(prizeViewVo);
////            } else {
////                List<PrizeViewVo> _list1 = new ArrayList();
////                _list1.add(prizeViewVo);
////                map.put(prizeViewVo.getType(),_list1);
////            }
////        }
////        System.out.println("1111111");
////
//    }

}
