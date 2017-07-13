package com.chinaxiaopu.xiaopuMobi.service.topic;

import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.MsgFromDto;
import com.chinaxiaopu.xiaopuMobi.mapper.*;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.chinaxiaopu.xiaopuMobi.service.MsgPushService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by ycy on 2016/11/4.
 */
@Service
public class PkFinishPrizeService {

    @Autowired
    private PkVoteResultMapper pkVoteResultMapper;

    @Autowired
    private TopicPkMapper topicPkMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private PkResultMapper pkResultMapper;

    @Autowired
    private PrizeImprestMapper prizeImprestMapper;

    @Autowired
    private PrizeMapper prizeMapper;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private PkPrizeResultMapper pkPrizeResultMapper;

    @Autowired
    MsgPushService msgPushService;

    @Transactional
    public void PkFinish(Integer pkId){
        //获取所有挑战者的id列表（消息推送对象）
        Gson gson = new Gson();
        List<Integer> idList = pkVoteResultMapper.selectUserIdList(pkId);
        PkVoteResult pkVoteResult = pkVoteResultMapper.selectVoteMax(pkId);
        TopicPk topicPk = topicPkMapper.selectByPkId(pkId);
        Topic topic = topicMapper.selectTopicById(topicPk.getTopicId());
        String imgUrl = "";
        List<String> imgs = (List<String>) gson.fromJson(topic.getContent(),Map.class).get("urls");
        if (topic.getType()==1) {
            imgUrl=imgs.get(0);
        } else {
            imgUrl=imgs.get(1);
        }

        //Topic mainTopic = topicMapper.selectTopicById(topicPk.getTopicId());
        if(pkVoteResult == null){
            //无人挑战，直接还库
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("pkId",pkId);
            map.put("prizeId",topicPk.getPrizeId());
            prizeMapper.giveBackStock(topicPk.getPrizeId());
            prizeImprestMapper.delectImprestWhenTurnDown(map);
            return;
        }
        PkResult pkResult = new PkResult();
        pkResult.setChallengeTopicId(topicPk.getTopicId());
        pkResult.setPkId(pkId);
        pkResult.setRanking(1);
        pkResult.setVoteCnt(pkVoteResult.getVoteCnt());
        pkResult.setFinishTime(new Date());
        pkResult.setRewardUserId(pkVoteResult.getCreatorId());
        UserInfo userInfo =userInfoMapper.selectUserInfoById(pkVoteResult.getCreatorId());
        pkResult.setRewardUserAvatar(userInfo.getAvatarUrl());
        pkResult.setRewardUserNickname(userInfo.getNickName());
        pkResult.setRewardUserRealname(userInfo.getRealName());
        pkResult.setIsFinish(0);
        pkResultMapper.insert(pkResult);
        //查询参与pk的帖子数
        int cNum = pkVoteResultMapper.selectCountByPkId(pkId);
        int stockNum = prizeImprestMapper.selectCountByPkId(pkId);
        int voCntSum = pkVoteResultMapper.selectVoteCntByPkId(pkId);
        //校验规则
        if(topicPk.getRule() != null){
            String rule = topicPk.getRule();
            //JSONObject obj = JSONObject.fromObject(rule);
            Map<String,Object> jmap =gson.fromJson(rule,Map.class);
            int challengeCnt = (int)((double)jmap.get("challengeCnt"));
            int voteCnt = (int)((double)jmap.get("voteCnt"));
            //奖品未过期
            if(topicPk.getExpireTime().compareTo(new Date())>0 && challengeCnt<=cNum && voteCnt<=voCntSum && stockNum > 0){
                //符合获奖规则//库存有出库记录//符合条件   更改topic_pk  pk_result表中的if_finish字段  向pk_prize_result中插入数据
               topicPkMapper.updateFinish(pkId);
               topicPkMapper.updateResultFinish(pkId);
               Prize prize = prizeMapper.selectPrizeById(topicPk.getPrizeId());
               PkPrizeResult pkPrizeResult = new PkPrizeResult();
               pkPrizeResult.setRewardUserId(userInfo.getUserId());
               pkPrizeResult.setPkId(pkId);
               pkPrizeResult.setPrizeNum(1);
               pkPrizeResult.setCode("");
               pkPrizeResult.setPrizeName(prize.getName());
               pkPrizeResult.setPrizeType(prize.getType());
               pkPrizeResult.setRewardUserRealname(userInfo.getRealName());
               pkPrizeResult.setRewardUserNickname(userInfo.getNickName());
               pkPrizeResult.setRewardUserAvatar(userInfo.getAvatarUrl());
               pkPrizeResult.setChallengeTopicId(topicPk.getTopicId());
               pkPrizeResult.setChallengeTopicSlogan(topic.getSlogan());
               pkPrizeResult.setEffectiveTime(topicPk.getEndTime());
               pkPrizeResult.setExpireTime(prize.getExpireTime());
               pkPrizeResult.setHasTake(0);
               pkPrizeResultMapper.insert(pkPrizeResult);
               for (int i = 0; i < idList.size(); i++) {
                   if (idList.get(i).equals(pkVoteResult.getCreatorId())) {
                        idList.remove(i);
                         break;
                   }
               }
                //idList 未获奖人的id集合
                //pkVoteResult.getTopicSlogan();   挑战口号
                //pkVoteResult.getCreatorId();   获奖人id
                //给创建者推送消息
                if(topic.getCreatorId() != 0) {
                    MsgFromDto msgFromDto2 = new MsgFromDto();
                    msgFromDto2.setTopicId(topicPk.getTopicId());
                    msgFromDto2.setTopicName(pkVoteResult.getTopicSlogan());
                    msgFromDto2.setTopicType(topic.getType());
                    msgFromDto2.setIsPk(topic.getIsPk());
                    msgFromDto2.setExpireTime(topic.getExpireTime());
                    msgFromDto2.setRightImgUrl(imgUrl);
                    Set<Integer> users4 = new HashSet<Integer>();
                    users4.add(topic.getCreatorId());
                    msgPushService.sendPushMsgByUser(Constants.PK_FINISH_OK, users4, 0, msgFromDto2);
                }

                //给获奖人推送消息
                MsgFromDto msgFromDto = new MsgFromDto();
                msgFromDto.setTopicId(topicPk.getTopicId());
                //msgFromDto.setUserId(pkVoteResult.getCreatorId());
                msgFromDto.setTopicName(pkVoteResult.getTopicSlogan());
                msgFromDto.setTopicType(topic.getType());
                msgFromDto.setIsPk(topic.getIsPk());
                msgFromDto.setExpireTime(topic.getExpireTime());
                msgFromDto.setPrizeId(prize.getId());
                msgFromDto.setPrizeName(prize.getName());
                msgFromDto.setPrizeNum(pkPrizeResult.getPrizeNum());
                msgFromDto.setRightImgUrl(imgUrl);
                Set<Integer> users3 =new HashSet<Integer>();
                users3.add(pkPrizeResult.getRewardUserId());
                msgPushService.sendPushMsgByUser(Constants.USER_GET_PK_PRIZE,users3,0,msgFromDto);

                //给未获奖的人推送消息
                MsgFromDto msgFromDto1 = new MsgFromDto();
                msgFromDto1.setTopicId(topicPk.getTopicId());
                msgFromDto1.setTopicName(pkVoteResult.getTopicSlogan());
                msgFromDto1.setTopicType(topic.getType());
                msgFromDto1.setIsPk(topic.getIsPk());
                msgFromDto1.setExpireTime(topic.getExpireTime());
                msgFromDto1.setUserId(pkPrizeResult.getRewardUserId());
                msgFromDto1.setUserName(pkPrizeResult.getRewardUserNickname());
                msgFromDto1.setRightImgUrl(imgUrl);
                Set<Integer> users1 =new HashSet<Integer>();
                users1.addAll(idList);
                msgPushService.sendPushMsgByUser(Constants.USER_NO_PK_PRIZE,users1,0,msgFromDto1);
                }else{

                //还库
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("pkId",pkId);
                map.put("prizeId",topicPk.getPrizeId());
                prizeMapper.giveBackStock(topicPk.getPrizeId());
                prizeImprestMapper.delectImprestWhenTurnDown(map);
                //pkVoteResult.getTopicSlogan();   挑战口号
                //idList 未获奖人的id集合
                //活动未达到要求 给创建人推送消息
                if(topic.getCreatorId() != 0) {
                    MsgFromDto msgFromDto3 = new MsgFromDto();
                    msgFromDto3.setTopicName(pkVoteResult.getTopicSlogan());
                    msgFromDto3.setTopicId(topicPk.getTopicId());
                    msgFromDto3.setTopicType(topic.getType());
                    msgFromDto3.setRightImgUrl(imgUrl);
                    Set<Integer> users2 = new HashSet<Integer>();
                    users2.add(topic.getCreatorId());
                    msgPushService.sendPushMsgByUser(Constants.PK_FINISH_NO, users2, 0, msgFromDto3);
                }
            }
        }
    }

    @Transactional
    public void updatePkFinish(Integer pkId){
        PkFinish(pkId);
        updateExpireTime(pkId);
    }

    public void updateExpireTime(Integer pkId){
        topicMapper.updateExpireTime(pkId);
    }
}
