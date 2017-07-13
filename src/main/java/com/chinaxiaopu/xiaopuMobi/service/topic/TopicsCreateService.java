package com.chinaxiaopu.xiaopuMobi.service.topic;

import com.chinaxiaopu.xiaopuMobi.dto.CreatTopicDto;
import com.chinaxiaopu.xiaopuMobi.dto.MyTopicParam;
import com.chinaxiaopu.xiaopuMobi.dto.topic.EventTopicDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.GroupTopicDto;
import com.chinaxiaopu.xiaopuMobi.mapper.*;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.chinaxiaopu.xiaopuMobi.quartz.DynamicJob;
import com.chinaxiaopu.xiaopuMobi.quartz.QuartzUtils;
import com.chinaxiaopu.xiaopuMobi.vo.topic.PrizeVo;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by ycy on 2016/11/2.
 */
@Service
public class TopicsCreateService {

    @Autowired
    private PkChannelMapper pkChannelMapper;

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
    private UserInfoMapper userInfoMapper;

    @Autowired
    private PkVoteResultMapper pkVoteResultMapper;
    @Autowired
    private QuartzUtils quartzUtils;




    /**
     * 新建页面查询频道列表
     * @return
     * @throws Exception
     */
    public List<PkChannel> getPkChannelList() throws Exception{
        return pkChannelMapper.selectPkChannel();
    }

    public List<PkChannel> selectOfficalPkChannel() throws Exception{
        return pkChannelMapper.selectOfficalPkChannel();
    }

    public List<PkChannel> selectOfficalAudioPkChannel() throws Exception{
        return pkChannelMapper.selectOfficalAudioPkChannel();
    }

    /**
     * 查询奖品类型
     * @param myTopicParam
     * @return
     * @throws Exception
     */
    public List<Prize> getPrizesType(MyTopicParam myTopicParam) throws Exception{
        return prizeMapper.selectAllPrizesType(myTopicParam);
    }

    /**
     * 根据奖品类型查询所有奖品名称
     * @param prize
     * @return
     * @throws Exception
     */
    public List<PrizeVo> getPrizesListByType(Prize prize) throws Exception{
        Gson gson = new Gson();
        List<Prize> list = new ArrayList<Prize>();
        List<PrizeVo> voList = new ArrayList<PrizeVo>();
        if(prize.getIsPublic()!= null && prize.getIsPublic() == 2){
            list = prizeMapper.selectPrizesNameByTypeForOffical(prize);
        }else{
            list = prizeMapper.selectPrizesNameByType(prize);
        }
        for(Prize p:list){
            String prizeXq = p.getPrize();
            //JSONObject obj = JSONObject.fromObject(prizeXq);
            Map<String,Object> jmap =gson.fromJson(prizeXq,Map.class);
            PrizeVo pvo = new PrizeVo();
            pvo.setDesc(jmap.get("desc").toString());
            String rule = jmap.get("rewardRule").toString();
            //JSONObject obj1 = JSONObject.fromObject(rule);
            Map<String,Object> jmap1 =gson.fromJson(rule,Map.class);
            double voteCnt = (double)jmap1.get("voteCnt");
            double challengeCnt =(double)jmap1.get("challengeCnt");
            pvo.setVoteCnt((int)voteCnt);
            pvo.setChallengeCnt((int)challengeCnt);
            pvo.setImgs(jmap.get("imgs").toString().split(","));
            BeanUtils.copyProperties(p,pvo);
            voList.add(pvo);
        }
        return voList;
    }

    /**
     * 创建话题
     *ycy
     */
    @Transactional
    public int createTopic(CreatTopicDto creatTopicDto) throws Exception{
        if(creatTopicDto.getIsPk() == 0 && creatTopicDto.getIsChallenger() == -1){
            //普通帖
            createNormalTopic(creatTopicDto);
        }
        if(creatTopicDto.getIsPk() == 1 && creatTopicDto.getIsChallenger() == 0){
            //挑战贴
             createChallengerTopic(creatTopicDto);
        }
        if(creatTopicDto.getIsPk() == 1 && creatTopicDto.getIsChallenger() == 1){
            //擂主贴
            Map<String,Object> map = createPkTopic(creatTopicDto);
            int pkId = (int)map.get("pkId");
            Date endTime = (Date)map.get("endTime");
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

    /**
     * 创建普通话题
     * @param creatTopicDto
     * @return
     */
    public int createNormalTopic(CreatTopicDto creatTopicDto){
        Topic topic = new Topic();
        Gson gson = new Gson();
        if(creatTopicDto.getContentJSON() != null) {
            String context = gson.toJson(creatTopicDto.getContentJSON());
            //JSONObject object = JSONObject.fromObject(creatTopicDto.getContentJSON());
            //String context = object.toString();
            creatTopicDto.setContent(context);
        }
        BeanUtils.copyProperties(creatTopicDto,topic);
        topic.setUpdateTime(new Date());
        topic.setIsDelete(1);
        topic.setLikeCnt(0);
        topic.setCommentCnt(0);
        topic.setFavoriteCnt(0);
        topic.setCommentCnt(0);
        topic.setRecommend(0);
        topic.setStatus(1);
        topic.setCreateTime(new Date());
        int flag = topicMapper.insert(topic);
        insertInNewAndTag(creatTopicDto.getEventList(),creatTopicDto.getGroupList(),topic.getId(),topic.getCreateTime());
        return flag;
    }

    /**
     * 创建挑战者话题
     * @param creatTopicDto
     * @return
     */
    public int createChallengerTopic(CreatTopicDto creatTopicDto) throws Exception{
        Topic topic = new Topic();
        Gson gson = new Gson();
        if(creatTopicDto.getContentJSON() != null) {
            String context = gson.toJson(creatTopicDto.getContentJSON());
            //JSONObject object = JSONObject.fromObject(creatTopicDto.getContentJSON());
            //String context = object.toString();
            creatTopicDto.setContent(context);
        }
        BeanUtils.copyProperties(creatTopicDto,topic);
        topic.setUpdateTime(new Date());
        topic.setIsDelete(1);
        topic.setLikeCnt(0);
        topic.setCommentCnt(0);
        topic.setFavoriteCnt(0);
        topic.setCommentCnt(0);
        topic.setRecommend(0);
        topic.setStatus(1);

        Topic Leitopic = topicMapper.selectTopicById(creatTopicDto.getChallengeTopicId());
        if(Leitopic.getCreatorId().equals( creatTopicDto.getCreatorId())){
                throw new Exception("用户不可以自己挑战自己！");
        }
        //查询pkid
        int pkId = topicPkMapper.selectPkIdByTopicId(creatTopicDto.getChallengeTopicId());
        //验证用户不可挑战2次
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("pkId",pkId);
        map.put("userId",creatTopicDto.getCreatorId());
        int count = pkVoteResultMapper.checkUserVote(map);

        if(count>0){
            throw new Exception("同一个用户不可以挑战两次！");
        }
        topic.setChallengeAvatar(Leitopic.getCreatorAvatar());
        topic.setChallengeNickname(Leitopic.getCreatorNickname());
        topic.setChallengeId(Leitopic.getCreatorId());
        topic.setCreateTime(new Date());
        topic.setSlogan(Leitopic.getSlogan());
        topic.setExpireTime(Leitopic.getExpireTime());
        int flag = topicMapper.insert(topic);
        insertInNewAndTag(creatTopicDto.getEventList(),creatTopicDto.getGroupList(),topic.getId(),topic.getCreateTime());




        PkVoteResult pkVoteResult = new PkVoteResult();
        pkVoteResult.setUpdateTime(new Date());
        pkVoteResult.setVoteCnt(0);
        pkVoteResult.setTopicId(topic.getId());
        pkVoteResult.setTopicSlogan(creatTopicDto.getSlogan());
        pkVoteResult.setPkId(pkId);
        pkVoteResult.setTopicSlogan(Leitopic.getSlogan());
        pkVoteResult.setCreatorId(creatTopicDto.getCreatorId());
        pkVoteResult.setCreatorNickname(creatTopicDto.getCreatorNickname());
        pkVoteResultMapper.insert(pkVoteResult);
        return 1;
    }

    /**
     * 创建擂主贴
     * @return
     */
    public Map<String,Object> createPkTopic(CreatTopicDto creatTopicDto){
        Gson gson = new Gson();
        Prize prize = prizeMapper.selectPrizeById(creatTopicDto.getPrizeId());
        creatTopicDto.setRewardExpireTime(prize.getExpireTime());
        String prizeXq = prize.getPrize();
        //JSONObject obj = JSONObject.fromObject(prizeXq);
        //JSONObject object = JSONObject.fromObject(creatTopicDto.getContentJSON());
        //String context = object.toString();
        Map<String,Object> jmap =gson.fromJson(prizeXq,Map.class);
        if(creatTopicDto.getContentJSON() != null){
        String context = gson.toJson(creatTopicDto.getContentJSON());
        creatTopicDto.setContent(context);
        }
        //int challengeCnt = Integer.valueOf(obj.getString("challengeCnt"));
        String rule =jmap.get("rewardRule").toString();
        double s =(double)jmap.get("status");
        int status = (int)s;
        creatTopicDto.setRule(rule);
        Topic topic = new Topic();
        BeanUtils.copyProperties(creatTopicDto,topic);
        topic.setStatus(1);
        //如果礼物有限制，话题置为待审核状态
        if(status == 1){
            topic.setStatus(0);
        }
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
        insertInNewAndTag(creatTopicDto.getEventList(),creatTopicDto.getGroupList(),topic.getId(),topic.getCreateTime());

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

        //插入投票结果表
        PkVoteResult pkVoteResult = new PkVoteResult();
        pkVoteResult.setUpdateTime(new Date());
        pkVoteResult.setTopicSlogan(creatTopicDto.getSlogan());
        pkVoteResult.setVoteCnt(0);
        pkVoteResult.setTopicId(topic.getId());
        pkVoteResult.setPkId(topicPk.getId());
        pkVoteResult.setCreatorId(creatTopicDto.getCreatorId());
        pkVoteResult.setCreatorNickname(creatTopicDto.getCreatorNickname());
        pkVoteResultMapper.insert(pkVoteResult);

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
        Map<String, Object>  map = new HashMap<String, Object>();
        map.put("pkId",topicPk.getId());
        map.put("endTime",topicPk.getEndTime());
        return map;
    }

    public int insertInNewAndTag(List<EventTopicDto> eventList, List<GroupTopicDto> groupList,Integer topicId,Date createTime){
        if(eventList != null) {
            for (EventTopicDto e : eventList) {
                TopicTag topicTag = new TopicTag();
                topicTag.setTopicId(topicId);
                topicTag.setTargetId(e.getEventId());
                topicTag.setTagType(2);
                topicTag.setTargetName(topicTagMapper.selectEventNameById(e.getEventId()));
                topicTag.setStatus(1);
                topicTagMapper.insert(topicTag);
            }
        }
        if(groupList != null) {
            for (GroupTopicDto g : groupList) {
                TopicTag topicTag = new TopicTag();
                topicTag.setTopicId(topicId);
                topicTag.setTargetId(g.getGroupId());
                topicTag.setTagType(1);
                topicTag.setTargetName(topicTagMapper.selectGroupNameById(g.getGroupId()));
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
//        if(type == 0) {
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(date);
//            cal.add(Calendar.DAY_OF_YEAR,days);
//            date = cal.getTime();
//        }
        return date;
    }

    public int checkPk(CreatTopicDto creatTopicDto){
        Topic Leitopic = topicMapper.selectTopicById(creatTopicDto.getChallengeTopicId());
        if(Leitopic.getCreatorId().equals(creatTopicDto.getCreatorId())){
            return 5;
        }
        //查询pkid
        int pkId = topicPkMapper.selectPkIdByTopicId(creatTopicDto.getChallengeTopicId());
        //验证用户不可挑战2次
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("pkId",pkId);
        map.put("userId",creatTopicDto.getCreatorId());
        int count = pkVoteResultMapper.checkUserVote(map);
        if(count>0){
            return 6;
        }
        return 1;
    }


    public boolean checkUrls(String content){
        Gson gson = new Gson();
        boolean checkResult = true;
        Map<String,Object> jmap =gson.fromJson(content,Map.class);
        if(null ==jmap.get("urls").toString() || "".equals(jmap.get("urls").toString()) || "[]".equals(jmap.get("urls").toString())){
            checkResult = false;
        }
        return  checkResult;
    }

}
