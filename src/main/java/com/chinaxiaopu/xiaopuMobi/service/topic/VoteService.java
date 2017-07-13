package com.chinaxiaopu.xiaopuMobi.service.topic;

import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.MsgFromDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicIdDto;
import com.chinaxiaopu.xiaopuMobi.mapper.PkVoteMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.PkVoteResultMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.TopicMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.TopicPkMapper;
import com.chinaxiaopu.xiaopuMobi.model.PkVote;
import com.chinaxiaopu.xiaopuMobi.model.PkVoteResult;
import com.chinaxiaopu.xiaopuMobi.model.Topic;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.MsgPushService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Wang on 2016/11/2.
 */
@Service
public class VoteService {

    @Autowired
    private PkVoteMapper pkVoteMapper;
    @Autowired
    private PkVoteResultMapper pkVoteResultMapper;
    @Autowired
    private TopicPkMapper topicPkMapper;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private MsgPushService msgPushService;//消息推送

    /**
     * 投票
     * @param topicIdDto
     * @param userInfo
     * @return
     */
    @Transactional
    public int createVote(TopicIdDto topicIdDto, UserInfo userInfo) {
        Integer result = 0;
        //是否已经投票
        int pkId = topicPkMapper.selectPkIdByTopicId(topicIdDto.getTopicId());
        int voteCount = pkVoteMapper.selectCountByPkIdAndUserId(pkId,userInfo.getUserId());
        if(voteCount>0){
            return result;
        }
        //插入投票信息
        PkVote pkVote = new PkVote();
        pkVote.setPkId(pkId);
        pkVote.setTopicId(topicIdDto.getTopicId());
        pkVote.setUserId(userInfo.getUserId());
        pkVote.setUserNickname(userInfo.getNickName());
        pkVote.setUserAvatar(userInfo.getAvatarUrl());
        int insertCount = pkVoteMapper.insertSelective(pkVote);
        //查询被投票人信息
        PkVoteResult pkVoteResult = pkVoteResultMapper.selectByTopicId(topicIdDto.getTopicId());
        //修改被投票人的票数
        pkVoteResult.setVoteCnt(pkVoteResult.getVoteCnt()+1);
        pkVoteResult.setUpdateTime(new Date());
        int updateCount = pkVoteResultMapper.updateByTopicId(pkVoteResult);
        if(insertCount==1 && updateCount==1){
            result = 1;
            //消息推送--投票
            Topic topic = topicMapper.selectByPrimaryKey(topicIdDto.getTopicId());
            int action = Constants.TOPIC_USER_VOTE;
            int userId = topic.getCreatorId();
            Set<Integer> users = new HashSet<>();
            users.add(userId);
            int sender = userInfo.getUserId();
            MsgFromDto msgFromDto = new MsgFromDto();
            msgFromDto.setUserId(userInfo.getUserId());
            msgFromDto.setUserName(userInfo.getNickName());
            msgFromDto.setTopicId(topic.getId());
            msgFromDto.setTopicName(topic.getSlogan());
            msgFromDto.setTopicType(topic.getType());
            msgFromDto.setIsPk(topic.getIsPk());
            if(topic.getIsPk()==1){
                msgFromDto.setExpireTime(topic.getExpireTime());
            }

            Map<String,Object> contentMap = new Gson().fromJson(topic.getContent(),Map.class);
            List<String> urls = (List<String>) contentMap.get("urls");
            if(topic.getType()==1){
                msgFromDto.setRightImgUrl(urls.get(0));
            } else if(topic.getType()==2){
                msgFromDto.setRightImgUrl(urls.get(1));
            }
            msgPushService.sendPushMsgByUser(action,users,sender,msgFromDto);
        }

        return result;
    }
}
