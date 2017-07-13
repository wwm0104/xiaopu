package com.chinaxiaopu.xiaopuMobi.service.admin.topics;

import com.chinaxiaopu.xiaopuMobi.dto.CreatTopicDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.EventTopicDto;
import com.chinaxiaopu.xiaopuMobi.mapper.*;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.chinaxiaopu.xiaopuMobi.quartz.DynamicJob;
import com.chinaxiaopu.xiaopuMobi.quartz.QuartzUtils;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by ycy on 2016/11/8.
 */
@Service
public class AdminTopickCreateService {

    @Autowired
    private PrizeMapper prizeMapper;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private TopicPkMapper topicPkMapper;


    @Autowired
    private PrizeImprestMapper prizeImprestMapper;

    @Autowired
    private TopicTagMapper topicTagMapper;

    @Autowired
    private TopicNewMapper topicNewMapper;

    @Autowired
    private QuartzUtils quartzUtils;

    /**
     * 创建擂主贴
     * @return
     */
    @Transactional
    public int createPkTopic(CreatTopicDto creatTopicDto){
        Gson gson = new Gson();
        Prize prize = prizeMapper.selectPrizeById(creatTopicDto.getPrizeId());
        creatTopicDto.setRewardExpireTime(prize.getExpireTime());
        String prizeXq = prize.getPrize();
        //JSONObject obj = JSONObject.fromObject(prizeXq);
        //JSONObject object = JSONObject.fromObject(creatTopicDto.getContentJSON());
        String context = gson.toJson(creatTopicDto.getContentJSON());
        //String context = object.toString();
        Map<String,Object> jmap =gson.fromJson(prizeXq,Map.class);
        creatTopicDto.setContent(context);
        //int challengeCnt = Integer.valueOf(obj.getString("challengeCnt"));
        String rule =jmap.get("rewardRule").toString();
        double s =(double)jmap.get("status");
        int status = (int)s;
        creatTopicDto.setRule(rule);
        Topic topic = new Topic();
        BeanUtils.copyProperties(creatTopicDto,topic);
        topic.setStatus(1);
        topic.setUpdateTime(new Date());
        topic.setIsDelete(1);
        topic.setLikeCnt(0);
        topic.setCommentCnt(0);
        topic.setFavoriteCnt(0);
        topic.setCommentCnt(0);
        topic.setRecommend(0);
        topic.setChallengeAvatar(creatTopicDto.getCreatorAvatar());
        topic.setChallengeId(creatTopicDto.getCreatorId());
        topic.setChallengeNickname(creatTopicDto.getCreatorNickname());
        topic.setCreateTime(new Date());
        if(creatTopicDto.getDays() == null){
            creatTopicDto.setDays(0);
        }
        topic.setExpireTime(calculatedEndTime(topic.getCreateTime(),creatTopicDto.getPeriodType(),creatTopicDto.getDays()));
        int flag = topicMapper.insert(topic);
        topicMapper.updateChallengeTopicIdAsId(topic.getId());
        List<EventTopicDto> list = new ArrayList<EventTopicDto>();
        if(creatTopicDto.getEventIdList() != null) {
            int[] arr = creatTopicDto.getEventIdList();
            for (int i = 0; i < arr.length; i++) {
                String name = topicTagMapper.selectEventNameById(arr[i]);
                EventTopicDto eventTopicDto = new EventTopicDto();
                eventTopicDto.setEventId(arr[i]);
                eventTopicDto.setEventSubject(name);
                list.add(eventTopicDto);
            }
        }
        insertInNewAndTag(list,topic.getId(),topic.getCreateTime());

        //插入PK表
        TopicPk topicPk = new TopicPk();
        topicPk.setRule(creatTopicDto.getRule());
        topicPk.setIsFinish(0);
        topicPk.setTopicId(topic.getId());
        topicPk.setPeriodType(creatTopicDto.getPeriodType());
        topicPk.setExpireTime(creatTopicDto.getRewardExpireTime());
        topicPk.setStartTime(new Date());
        topicPk.setEndTime(topic.getExpireTime());
        topicPk.setPrizeId(creatTopicDto.getPrizeId());
        topicPk.setRewardType(creatTopicDto.getRewardType());
        topicPkMapper.insert(topicPk);

//        //插入投票结果表
//        PkVoteResult pkVoteResult = new PkVoteResult();
//        pkVoteResult.setUpdateTime(new Date());
//        pkVoteResult.setTopicSlogan(creatTopicDto.getSlogan());
//        pkVoteResult.setVoteCnt(0);
//        pkVoteResult.setTopicId(topic.getId());
//        pkVoteResult.setPkId(topicPk.getId());
//        pkVoteResult.setCreatorId(creatTopicDto.getCreatorId());
//        pkVoteResult.setCreatorNickname(creatTopicDto.getCreatorNickname());
//        pkVoteResultMapper.insert(pkVoteResult);

        //修改库存
        prizeMapper.updateStockById(creatTopicDto.getPrizeId());
        //插入库存支出表
        PrizeImprest prizeImprest = new PrizeImprest();
        prizeImprest.setPrizeId(creatTopicDto.getPrizeId());
        prizeImprest.setPkId(topicPk.getId());
        prizeImprest.setStockOut(1);
        prizeImprest.setCreateTime(new Date());
        prizeImprest.setTopicId(topic.getId());
        prizeImprestMapper.insert(prizeImprest);
        {
            //擂主贴

            int pkId = topicPk.getId();
            Date endTime = topicPk.getEndTime();
            try {
                Timestamp times = new Timestamp(endTime.getTime());
                String time = "";
                int sec = times.getSeconds();
                int min = times.getMinutes();
                int hou = times.getHours();
                int day = times.getDate();
                int mon = times.getMonth() + 1;
                int yea = times.getYear() + 1900;
                time += sec + " " + min + " " + hou + " " + day + " " + mon + " ? " + yea;
                System.out.println(time);//输出结果
                quartzUtils.addJob("计算获奖结果->"+pkId, DynamicJob.class, time,pkId);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return 1;
    }


    public int insertInNewAndTag(List<EventTopicDto> eventList, Integer topicId, Date createTime){
        if(eventList != null && eventList.size() > 0){
        for(EventTopicDto e : eventList){
            TopicTag topicTag = new TopicTag();
            topicTag.setTopicId(topicId);
            topicTag.setTargetId(e.getEventId());
            topicTag.setTagType(2);
            topicTag.setTargetName(topicTagMapper.selectEventNameById(e.getEventId()));
            topicTag.setStatus(1);
            topicTagMapper.insert(topicTag);
           }
        }
        TopicNew topicNew = new TopicNew();
        topicNew.setCreateTime(createTime);
        topicNew.setTopicId(topicId);
        topicNew.setType(0);
        topicNewMapper.insert(topicNew);
        return 1;
    }

    public Date calculatedEndTime(Date date,int type,int days){
        //当天
        if(type == 1){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_YEAR,1);
            date = cal.getTime();
        }
        //当周
        if(type == 2){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_YEAR,7);
            date = cal.getTime();
        }
        //当月
        if(type == 3){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH,1);
            date = cal.getTime();
        }
        //当季
        if(type == 4){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH,3);
            date = cal.getTime();
        }
        //当年
        if(type == 5){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.YEAR,1);
            date = cal.getTime();
        }
        //自定义
        if(type == 0) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_YEAR,days);
            date = cal.getTime();
        }
        return date;
    }

}
