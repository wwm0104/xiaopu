package com.chinaxiaopu.xiaopuMobi.service;

import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.MsgFromDto;
import com.chinaxiaopu.xiaopuMobi.dto.MsgViewDto;
import com.chinaxiaopu.xiaopuMobi.mapper.NotifyMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.UserNotifyMapper;
import com.chinaxiaopu.xiaopuMobi.model.Notify;
import com.chinaxiaopu.xiaopuMobi.model.UserNotify;
import com.chinaxiaopu.xiaopuMobi.vo.appMsg.MsgContentVo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.*;

/**
 * Created by ellien
 * date: 2016/12/7
 */
@Slf4j
@Service
public class MsgPushService extends AbstractService {

    @Autowired
    private NotifyMapper notifyMapper;

    @Autowired
    private UserNotifyMapper userNotifyMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Transactional
    public void sendPushMsgByUser(final int action,final Collection<Integer> users,int sender,final MsgFromDto msgFromDto){
        StringBuffer msgContent = new StringBuffer();
        List<MsgViewDto> msgList = new ArrayList<>();
        int isPk;
        int topicType;
        Notify notify = new Notify();
        StringBuffer msg = new StringBuffer();

        try{
            notify.setAction(action);
            notify.setSender(sender);
            notify.setType(Constants.MSG_TYPE_REMIND);
            switch (action){
                case Constants.GROUP_JOIN_AUDIT_OK :
                    /** 申请加入社团通过 msg->恭喜您！您加入“[###]”的申请已通过审核！*/
                    msgContent.append(Constants.MSG_GROUP_JOIN_AUDIT_OK);
                    MsgViewDto msgViewDto1 = new MsgViewDto();
                    msgViewDto1.setType(Constants.MSG_GROUP_TYPE);
                    msgViewDto1.setId(msgFromDto.getGroupId());
                    msgViewDto1.setName(msgFromDto.getGroupName());
                    msgList.add(msgViewDto1);

                    notify.setTargetId(msgFromDto.getGroupId());
                    notify.setTargetType(Constants.MSG_GROUP_TYPE+"");

                    break;
                case Constants.GROUP_JOIN_AUDIT_NO :
                    /** 申请加入社团未通过 msg-> 您申请加入“[###]”被驳回，驳回原因：{0}。*/
                    msgContent.append(MessageFormat.format(Constants.MSG_GROUP_JOIN_AUDIT_NO,new Object[]{msgFromDto.getRemark()}));
                    MsgViewDto msgViewDto2 = new MsgViewDto();
                    msgViewDto2.setType(Constants.MSG_GROUP_TYPE);
                    msgViewDto2.setId(msgFromDto.getGroupId());
                    msgViewDto2.setName(msgFromDto.getGroupName());

                    msgList.add(msgViewDto2);

                    notify.setTargetId(msgFromDto.getGroupId());
                    notify.setTargetType(Constants.MSG_GROUP_TYPE+"");

                    break;
                case Constants.EVENT_JOIN_AUDIT_OK :
                    /** 申请加入活动通过 ->恭喜您！您加入“[###]”的申请已通过审核！*/
                    msgContent.append(Constants.MSG_EVENT_JOIN_AUDIT_OK);
                    MsgViewDto msgViewDto3 = new MsgViewDto();
                    msgViewDto3.setType(Constants.MSG_EVENT_TYPE);
                    msgViewDto3.setId(msgFromDto.getEventId());
                    msgViewDto3.setName(msgFromDto.getEventName());

                    msgList.add(msgViewDto3);

                    notify.setTargetId(msgFromDto.getEventId());
                    notify.setTargetType(Constants.MSG_EVENT_TYPE+"");

                    break;
                case Constants.EVENT_JOIN_AUDIT_NO :
                    /** 申请加入活动未通过 msg->您申请加入“[###]”被驳回，驳回原因：{0}。 */
                    msgContent.append(MessageFormat.format(Constants.MSG_EVENT_JOIN_AUDIT_NO,new Object[]{msgFromDto.getRemark()}));
                    MsgViewDto msgViewDto4 = new MsgViewDto();
                    msgViewDto4.setType(Constants.MSG_EVENT_TYPE);
                    msgViewDto4.setId(msgFromDto.getEventId());
                    msgViewDto4.setName(msgFromDto.getEventName());

                    msgList.add(msgViewDto4);

                    notify.setTargetId(msgFromDto.getEventId());
                    notify.setTargetType(Constants.MSG_EVENT_TYPE+"");

                    break;
                case Constants.PK_CREATE_AUDIT_OK :
                    /** 申请申请来战奖品通过 msg->恭喜您！您发布的来战“[###]” 申请的奖品已获批准，内容已发布。*/
                    msgContent.append(Constants.MSG_PK_CREATE_AUDIT_OK);
                    MsgViewDto msgViewDto5 = new MsgViewDto();
                    topicType = msgFromDto.getTopicType();
                    if(topicType==1){
                        msgViewDto5.setType(Constants.MSG_PK_IMG_TYPE);
                        notify.setTargetType(Constants.MSG_PK_IMG_TYPE+"");
                    } else {
                        msgViewDto5.setType(Constants.MSG_PK_VIDEO_TYPE);
                        notify.setTargetType(Constants.MSG_PK_VIDEO_TYPE+"");
                    }
                    msgViewDto5.setId(msgFromDto.getTopicId());
                    msgViewDto5.setName(msgFromDto.getTopicName());
                    if (msgFromDto.getExpireTime()!=null){
                        msgViewDto5.setExpireTime(msgFromDto.getExpireTime().getTime());
                    }


                    msgList.add(msgViewDto5);

                    notify.setTargetId(msgFromDto.getTopicId());


                    break;
                case Constants.PK_CREATE_AUDIT_NO :
                    /** 申请申请来战奖品未通过 msg->您发布的来战“[###]” 申请的奖品未获批准，请优化您要发布的内容。*/
                    msgContent.append(Constants.MSG_PK_CREATE_AUDIT_NO);
                    MsgViewDto msgViewDto6 = new MsgViewDto();
                    topicType = msgFromDto.getTopicType();
                    if(topicType==1){
                        msgViewDto6.setType(Constants.MSG_PK_IMG_TYPE);
                        notify.setTargetType(Constants.MSG_PK_IMG_TYPE+"");
                    } else {
                        msgViewDto6.setType(Constants.MSG_PK_VIDEO_TYPE);
                        notify.setTargetType(Constants.MSG_PK_IMG_TYPE+"");
                    }
                    msgViewDto6.setId(msgFromDto.getTopicId());
                    msgViewDto6.setName(msgFromDto.getTopicName());
                    if (msgFromDto.getExpireTime()!=null) {
                        msgViewDto6.setExpireTime(msgFromDto.getExpireTime().getTime());
                    }

                    msgList.add(msgViewDto6);

                    notify.setTargetId(msgFromDto.getTopicId());


                    break;
                case Constants.GROUP_CREATE_AUDIT_OK :
                    /** 申请创建社团通过*/
                    msgContent.append(Constants.MSG_GROUP_CREATE_AUDIT_OK);
                    MsgViewDto msgViewDto7 = new MsgViewDto();
                    msgViewDto7.setType(Constants.MSG_GROUP_TYPE);
                    msgViewDto7.setId(msgFromDto.getGroupId());
                    msgViewDto7.setName(msgFromDto.getGroupName());

                    msgList.add(msgViewDto7);

                    notify.setTargetId(msgFromDto.getGroupId());
                    notify.setTargetType(Constants.MSG_GROUP_TYPE+"");

                    break;
                case Constants.GROUP_CREATE_AUDIT_NO :
                    /** 申请创建社团未通过*/
                    msgContent.append(MessageFormat.format(Constants.MSG_GROUP_CREATE_AUDIT_NO,new Object[]{msgFromDto.getRemark()}));
                    MsgViewDto msgViewDto8 = new MsgViewDto();
                    msgViewDto8.setType(Constants.MSG_GROUP_TYPE);
                    msgViewDto8.setId(msgFromDto.getGroupId());
                    msgViewDto8.setName(msgFromDto.getGroupName());

                    msgList.add(msgViewDto8);

                    notify.setTargetId(msgFromDto.getGroupId());
                    notify.setTargetType(Constants.MSG_GROUP_TYPE+"");

                    break;
                case Constants.GROUP_CLAIM_AUDIT_OK :
                    /** 申请认领社团通过消息*/
                    msgContent.append(Constants.MSG_GROUP_CLAIM_AUDIT_OK);
                    MsgViewDto msgViewDto9 = new MsgViewDto();
                    msgViewDto9.setType(Constants.MSG_GROUP_TYPE);
                    msgViewDto9.setId(msgFromDto.getGroupId());
                    msgViewDto9.setName(msgFromDto.getGroupName());

                    msgList.add(msgViewDto9);

                    notify.setTargetId(msgFromDto.getGroupId());
                    notify.setTargetType(Constants.MSG_GROUP_TYPE+"");

                    break;
                case Constants.GROUP_CLAIM_AUDIT_NO :
                    /** 申请认领社团未通过消息*/
                    msgContent.append(MessageFormat.format(Constants.MSG_GROUP_CLAIM_AUDIT_NO,new Object[]{msgFromDto.getRemark()}));
                    MsgViewDto msgViewDto10 = new MsgViewDto();
                    msgViewDto10.setType(Constants.MSG_GROUP_TYPE);
                    msgViewDto10.setId(msgFromDto.getGroupId());
                    msgViewDto10.setName(msgFromDto.getGroupName());

                    msgList.add(msgViewDto10);

                    notify.setTargetId(msgFromDto.getGroupId());
                    notify.setTargetType(Constants.MSG_GROUP_TYPE+"");

                    break;
                case Constants.EVENT_CREATE_AUDIT_OK :
                    /** 申请发布活动通过*/
                    msgContent.append(Constants.MSG_EVENT_CREATE_AUDIT_OK);
                    MsgViewDto msgViewDto11 = new MsgViewDto();
                    msgViewDto11.setType(Constants.MSG_EVENT_TYPE);
                    msgViewDto11.setId(msgFromDto.getEventId());
                    msgViewDto11.setName(msgFromDto.getEventName());

                    msgList.add(msgViewDto11);

                    notify.setTargetId(msgFromDto.getEventId());
                    notify.setTargetType(Constants.MSG_EVENT_TYPE+"");

                    break;
                case Constants.EVENT_CREATE_AUDIT_NO :
                    /** 申请发布活动未通过*/
                    msgContent.append(MessageFormat.format(Constants.MSG_EVENT_CREATE_AUDIT_NO,new Object[]{msgFromDto.getRemark()}));
                    MsgViewDto msgViewDto12 = new MsgViewDto();
                    msgViewDto12.setType(Constants.MSG_EVENT_TYPE);
                    //TODO 活动未通过时,把ID设置为负数,APP判断不跳转
                    msgViewDto12.setId(-1);
                    msgViewDto12.setName(msgFromDto.getEventName());

                    msgList.add(msgViewDto12);

                    notify.setTargetId(msgFromDto.getEventId());
                    notify.setTargetType(Constants.MSG_EVENT_TYPE+"");

                    break;
                case Constants.TOPIC_USER_LIKE :
                    /**用户点赞消息*/
                    msgContent.append(Constants.MSG_TOPIC_USER_LIKE);

                    MsgViewDto msgViewDto13 = new MsgViewDto();
                    msgViewDto13.setType(Constants.MSG_USER_TYPE);
                    msgViewDto13.setId(msgFromDto.getUserId());
                    msgViewDto13.setName(msgFromDto.getUserName());

                    MsgViewDto msgViewDto14 = new MsgViewDto();
                    topicType = msgFromDto.getTopicType();
                    isPk = msgFromDto.getIsPk();
                    msgViewDto14.setType(getTopicType(isPk,topicType));
                    msgViewDto14.setId(msgFromDto.getTopicId());
                    msgViewDto14.setName(msgFromDto.getTopicName());
                    if (msgFromDto.getExpireTime()!=null) {
                        msgViewDto14.setExpireTime(msgFromDto.getExpireTime().getTime());
                    }

                    msgList.add(msgViewDto13);
                    msgList.add(msgViewDto14);

                    notify.setTargetId(msgFromDto.getTopicId());
                    notify.setTargetType(getTopicType(isPk,topicType)+"");

                    break;
                case Constants.TOPIC_USER_FAV :
                    /**用户收藏消息*/
                    msgContent.append(Constants.MSG_TOPIC_USER_FAV);

                    MsgViewDto msgViewDto15 = new MsgViewDto();
                    msgViewDto15.setType(Constants.MSG_USER_TYPE);
                    msgViewDto15.setId(msgFromDto.getUserId());
                    msgViewDto15.setName(msgFromDto.getUserName());

                    MsgViewDto msgViewDto16 = new MsgViewDto();
                    topicType = msgFromDto.getTopicType();
                    isPk = msgFromDto.getIsPk();
                    msgViewDto16.setType(getTopicType(isPk,topicType));
                    msgViewDto16.setId(msgFromDto.getTopicId());
                    msgViewDto16.setName(msgFromDto.getTopicName());
                    if (msgFromDto.getExpireTime()!=null) {
                        msgViewDto16.setExpireTime(msgFromDto.getExpireTime().getTime());
                    }

                    msgList.add(msgViewDto15);
                    msgList.add(msgViewDto16);

                    notify.setTargetId(msgFromDto.getTopicId());
                    notify.setTargetType(getTopicType(isPk,topicType)+"");

                    break;
                case Constants.TOPIC_USER_COMMENT :
                    /**用户评论消息*/
                    msgContent.append(Constants.MSG_TOPIC_USER_COMMENT);

                    MsgViewDto msgViewDto17 = new MsgViewDto();
                    msgViewDto17.setType(Constants.MSG_USER_TYPE);
                    msgViewDto17.setId(msgFromDto.getUserId());
                    msgViewDto17.setName(msgFromDto.getUserName());

                    MsgViewDto msgViewDto18 = new MsgViewDto();
                    topicType = msgFromDto.getTopicType();
                    isPk = msgFromDto.getIsPk();
                    msgViewDto18.setType(getTopicType(isPk,topicType));
                    msgViewDto18.setId(msgFromDto.getTopicId());
                    msgViewDto18.setName(msgFromDto.getTopicName());
                    if (msgFromDto.getExpireTime()!=null) {
                        msgViewDto18.setExpireTime(msgFromDto.getExpireTime().getTime());
                    }

                    msgList.add(msgViewDto17);
                    msgList.add(msgViewDto18);

                    notify.setTargetId(msgFromDto.getTopicId());
                    notify.setTargetType(getTopicType(isPk,topicType)+"");

                    break;
                case Constants.TOPIC_USER_VOTE :
                    /**用户投票消息*/
                    msgContent.append(Constants.MSG_TOPIC_USER_VOTE);

                    MsgViewDto msgViewDto19 = new MsgViewDto();
                    msgViewDto19.setType(Constants.MSG_USER_TYPE);
                    msgViewDto19.setId(msgFromDto.getUserId());
                    msgViewDto19.setName(msgFromDto.getUserName());

                    MsgViewDto msgViewDto20 = new MsgViewDto();
                    topicType = msgFromDto.getTopicType();
                    if(topicType==1){
                        msgViewDto20.setType(Constants.MSG_PK_IMG_TYPE);
                        notify.setTargetType(Constants.MSG_PK_IMG_TYPE+"");
                    } else {
                        msgViewDto20.setType(Constants.MSG_PK_VIDEO_TYPE);
                        notify.setTargetType(Constants.MSG_PK_VIDEO_TYPE+"");
                    }
                    msgViewDto20.setId(msgFromDto.getTopicId());
                    msgViewDto20.setName(msgFromDto.getTopicName());
                    if (msgFromDto.getExpireTime()!=null) {
                        msgViewDto20.setExpireTime(msgFromDto.getExpireTime().getTime());
                    }

                    msgList.add(msgViewDto19);
                    msgList.add(msgViewDto20);

                    notify.setTargetId(msgFromDto.getTopicId());


                    break;
                case Constants.PK_FINISH_OK :
                    /**PK达到要求,生效*/
                    msgContent.append(Constants.MSG_PK_FINISH_OK);
                    MsgViewDto msgViewDto21 = new MsgViewDto();
                    topicType = msgFromDto.getTopicType();
                    if(topicType==1){
                        msgViewDto21.setType(Constants.MSG_PK_IMG_TYPE);
                        //设置类型
                        notify.setTargetType(Constants.MSG_PK_IMG_TYPE+"");
                    } else {
                        msgViewDto21.setType(Constants.MSG_PK_VIDEO_TYPE);
                        //设置类型
                        notify.setTargetType(Constants.MSG_PK_VIDEO_TYPE+"");
                    }
                    msgViewDto21.setId(msgFromDto.getTopicId());
                    msgViewDto21.setName(msgFromDto.getTopicName());
                    if (msgFromDto.getExpireTime()!=null) {
                        msgViewDto21.setExpireTime(msgFromDto.getExpireTime().getTime());
                    }

                    msgList.add(msgViewDto21);

                    notify.setTargetId(msgFromDto.getTopicId());


                    break;
                case Constants.PK_FINISH_NO :
                    /**PK未达到要求,未生效*/
                    msgContent.append(Constants.MSG_PK_FINISH_NO);
                    MsgViewDto msgViewDto22 = new MsgViewDto();
                    topicType = msgFromDto.getTopicType();
                    if(topicType==1){
                        msgViewDto22.setType(Constants.MSG_PK_IMG_TYPE);
                        notify.setTargetType(Constants.MSG_PK_IMG_TYPE+"");
                    } else {
                        msgViewDto22.setType(Constants.MSG_PK_VIDEO_TYPE);
                        notify.setTargetType(Constants.MSG_PK_IMG_TYPE+"");
                    }
                    msgViewDto22.setId(msgFromDto.getTopicId());
                    msgViewDto22.setName(msgFromDto.getTopicName());
                    if (msgFromDto.getExpireTime()!=null) {
                        msgViewDto22.setExpireTime(msgFromDto.getExpireTime().getTime());
                    }

                    msgList.add(msgViewDto22);

                    notify.setTargetId(msgFromDto.getTopicId());


                    break;
                case Constants.USER_GET_PK_PRIZE :
                    /**用户PK胜利获得奖励*/
                    msgContent.append(MessageFormat.format(Constants.MSG_USER_GET_PK_PRIZE,new Object[]{msgFromDto.getPrizeNum()}));
                    MsgViewDto msgViewDto23 = new MsgViewDto();
                    topicType = msgFromDto.getTopicType();
                    if(topicType==1){
                        msgViewDto23.setType(Constants.MSG_PK_IMG_TYPE);
                        notify.setTargetType(Constants.MSG_PK_IMG_TYPE+"");
                    } else {
                        msgViewDto23.setType(Constants.MSG_PK_VIDEO_TYPE);
                        notify.setTargetType(Constants.MSG_PK_IMG_TYPE+"");
                    }
                    msgViewDto23.setId(msgFromDto.getTopicId());
                    msgViewDto23.setName(msgFromDto.getTopicName());
                    if (msgFromDto.getExpireTime()!=null) {
                        msgViewDto23.setExpireTime(msgFromDto.getExpireTime().getTime());
                    }

                    MsgViewDto msgViewDto24 = new MsgViewDto();
                    msgViewDto24.setType(Constants.MSG_PRIZE_TYPE);
                    msgViewDto24.setId(msgFromDto.getPrizeId());
                    msgViewDto24.setName(msgFromDto.getPrizeName());

                    msgList.add(msgViewDto23);
                    msgList.add(msgViewDto24);

                    notify.setTargetId(msgFromDto.getTopicId());

                    break;
                case Constants.USER_NO_PK_PRIZE :
                    /**PK结束用户未获得奖励消息*/

                    msgContent.append(Constants.MSG_USER_NO_PK_PRIZE);

                    MsgViewDto msgViewDto25 = new MsgViewDto();
                    topicType = msgFromDto.getTopicType();
                    if(topicType==1){
                        msgViewDto25.setType(Constants.MSG_PK_IMG_TYPE);
                        notify.setTargetType(Constants.MSG_PK_IMG_TYPE+"");
                    } else {
                        msgViewDto25.setType(Constants.MSG_PK_VIDEO_TYPE);
                        notify.setTargetType(Constants.MSG_PK_VIDEO_TYPE+"");
                    }
                    msgViewDto25.setId(msgFromDto.getTopicId());
                    msgViewDto25.setName(msgFromDto.getTopicName());
                    if (msgFromDto.getExpireTime()!=null) {
                        msgViewDto25.setExpireTime(msgFromDto.getExpireTime().getTime());
                    }

                    MsgViewDto msgViewDto26 = new MsgViewDto();
                    msgViewDto26.setType(Constants.MSG_USER_TYPE);
                    msgViewDto26.setId(msgFromDto.getUserId());
                    msgViewDto26.setName(msgFromDto.getUserName());

                    msgList.add(msgViewDto25);
                    msgList.add(msgViewDto26);

                    notify.setTargetId(msgFromDto.getTopicId());

                    break;
                case Constants.SYS_EVENT_DEL :
                    /**用户活动被删除*/
                    msgContent.append(Constants.MSG_SYS_EVENT_DEL);
                    MsgViewDto msgViewDto27 = new MsgViewDto();
                    msgViewDto27.setType(Constants.MSG_EVENT_TYPE);
                    msgViewDto27.setId(msgFromDto.getEventId());
                    msgViewDto27.setName(msgFromDto.getEventName());
                    msgList.add(msgViewDto27);

                    notify.setTargetId(msgFromDto.getEventId());
                    notify.setTargetType(Constants.MSG_EVENT_TYPE+"");

                    break;
                case Constants.SYS_TOPIC_DEL :
                    /**用户内容被删除*/
                    msgContent.append(Constants.MSG_SYS_TOPIC_DEL);
                    MsgViewDto msgViewDto28 = new MsgViewDto();
                    topicType = msgFromDto.getTopicType();
                    isPk = msgFromDto.getIsPk();
                    msgViewDto28.setType(getTopicType(isPk,topicType));
                    msgViewDto28.setId(msgFromDto.getTopicId());
                    msgViewDto28.setName(msgFromDto.getTopicName());
                    if (msgFromDto.getExpireTime()!=null) {
                        msgViewDto28.setExpireTime(msgFromDto.getExpireTime().getTime());
                    }

                    msgList.add(msgViewDto28);

                    notify.setTargetId(msgFromDto.getTopicId());
                    notify.setTargetType(getTopicType(isPk,topicType)+"");

                    break;
                case Constants.USER_FOUCS :
                    /**用户被关注*/
                    msgContent.append(Constants.MSG_USER_FOUCS);
                    MsgViewDto msgViewDto29 = new MsgViewDto();
                    msgViewDto29.setType(Constants.MSG_USER_TYPE);
                    msgViewDto29.setId(msgFromDto.getUserId());
                    msgViewDto29.setName(msgFromDto.getUserName());
                    msgList.add(msgViewDto29);

                    notify.setTargetId(msgFromDto.getUserId());
                    notify.setTargetType(Constants.MSG_USER_TYPE+"");

                    break;
                default :
                    log.error("请选择发送类型");
                    break;
            }
            notify.setContent(msgContent.toString());

            Map<String,Object> parameterMap = new HashMap<>();
            Map<String,Object> msgContentMap = new HashMap<>();

            Map<String,Object> imgMap = new HashMap<>();

            imgMap.put("id",notify.getTargetId());
            imgMap.put("type",notify.getTargetType());
            if (msgFromDto.getExpireTime()!=null) {
                imgMap.put("expireTime",msgFromDto.getExpireTime().getTime());
            } else {
                imgMap.put("expireTime","");
            }

            if (StringUtils.isNotEmpty(msgFromDto.getRightImgUrl())){
                imgMap.put("url",msgFromDto.getRightImgUrl());
                parameterMap.put("rightImgUrl",imgMap);
                msgContentMap.put("rightImgUrl",imgMap);
            } else {
                parameterMap.put("rightImgUrl","");
                msgContentMap.put("rightImgUrl","");
            }
            if (StringUtils.isNotEmpty(msgFromDto.getLeftImgUrl())){
                imgMap.put("url",msgFromDto.getLeftImgUrl());
                parameterMap.put("leftImgUrl",imgMap);
                msgContentMap.put("leftImgUrl",imgMap);
            } else {
                parameterMap.put("leftImgUrl","");
                msgContentMap.put("leftImgUrl","");
            }


            parameterMap.put("list",msgList);

            notify.setParameter(new Gson().toJson(parameterMap,Map.class));
            notifyMapper.insertSelective(notify);

            ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();

            Set<String> registrations = new HashSet<>();
            for (Integer userId : users) {
                UserNotify userNotify = new UserNotify();
                userNotify.setNotifyId(notify.getId());
                userNotify.setUserId(userId);
                userNotifyMapper.insertSelective(userNotify);
                String json = valueOperations.get(Constants.APP_USER+userId);
                if(StringUtils.isNotBlank(json)){
                    Gson gson = new Gson();
                    Set<String> _registrations = gson.fromJson(json,Set.class);
                    registrations.addAll(_registrations);
                }

            }

            msgContentMap.put("id",notify.getId());
            msgContentMap.put("content",msgContent.toString());
            msgContentMap.put("view",msgList);

            msg.append(new Gson().toJson(msgContentMap,Map.class));

            log.info("消息内容:"+msg.toString());
            //发送消息
            if (registrations!=null && registrations.size()>0){
                MsgPushClientService.sendMsgPushByUser(registrations,msg.toString());
            }

        } catch (Exception e) {
            log.error(msg.toString()+"->消息推送失败:",e);
        }

    }

    private int getTopicType(int isPk,int topicType){
        int type;
        if (isPk==1){
            if(topicType==1){
                type = Constants.MSG_PK_IMG_TYPE;
            } else {
                type = Constants.MSG_PK_VIDEO_TYPE;
            }
        } else {
            if(topicType==1){
                type = Constants.MSG_IMG_TYPE;
            } else {
                type = Constants.MSG_VIDEO_TYPE;
            }
        }
        return type;
    }
}
