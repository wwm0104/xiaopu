package com.chinaxiaopu.xiaopuMobi.service.topic;

import com.chinaxiaopu.xiaopuMobi.dto.DeleteMyTopicDto;
import com.chinaxiaopu.xiaopuMobi.dto.MyTopicParam;
import com.chinaxiaopu.xiaopuMobi.dto.UserFansTopicLisDto;
import com.chinaxiaopu.xiaopuMobi.mapper.*;
import com.chinaxiaopu.xiaopuMobi.model.Prize;
import com.chinaxiaopu.xiaopuMobi.model.Topic;
import com.chinaxiaopu.xiaopuMobi.model.UserFans;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.util.DateTimeUtil;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.topic.MyTopicsVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.PrizeVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.TopicVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.TopicsVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ycy on 2016/11/3.
 */
@Service
public class MyFavTopicService {


    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private TopicLikeMapper topicLikeMapper;

    @Autowired
    private TopicFavMapper topicFavMapper;

    @Autowired
    private TopicTagMapper topicTagMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserFansMapper userFansMapper;

    /**
     * 查询我的话题列表
     * @param myTopicParam
     * @return
     */
    public PageInfo<Topic> selectMyTopics(MyTopicParam myTopicParam) throws Exception{
        if (!StringUtils.isEmpty(myTopicParam.getPage()) && !StringUtils.isEmpty(myTopicParam.getRows())) {
            PageHelper.startPage(myTopicParam.getPage(), myTopicParam.getRows());
        }

        List<Topic> topicList = topicMapper.selectUerTopic(myTopicParam);

        PageInfo<Topic> pageInfo =new PageInfo<Topic>(topicList);
        pageInfo.setList(topicList);
        return pageInfo;
    }

    /**
     * 删除我的图文，为逻辑删除update
     * @param deleteMyTopicDto
     * @return
     */
    public int deleteMyTopic(DeleteMyTopicDto deleteMyTopicDto){
        return topicMapper.updateMyTopic(deleteMyTopicDto);
    }

    public int checkIsChallenge(DeleteMyTopicDto deleteMyTopicDto){
        return topicMapper.selectIsChallenge(deleteMyTopicDto);
    }

    /**
     * 查看我收藏的话题
     * @param myTopicParam
     * @return
     * @throws Exception
     */
    public PageInfo<Topic> selectMyFavTopics(MyTopicParam myTopicParam) throws Exception{
        if (!StringUtils.isEmpty(myTopicParam.getPage()) && !StringUtils.isEmpty(myTopicParam.getRows())) {
            PageHelper.startPage(myTopicParam.getPage(), myTopicParam.getRows());
        }
        List<Topic> topicList = topicFavMapper.selectUserFavTopic(myTopicParam);
        PageInfo<Topic> pageInfo =new PageInfo<>(topicList);
        pageInfo.setList(topicList);
        return pageInfo;
    }

    public PageInfo<Topic> selectTopicsByUserId(MyTopicParam myTopicParam) throws Exception{
        if (!StringUtils.isEmpty(myTopicParam.getPage()) && !StringUtils.isEmpty(myTopicParam.getRows())) {
            PageHelper.startPage(myTopicParam.getPage(), myTopicParam.getRows());
        }
        List<Topic> topicList = topicMapper.selectTopicsByUserId(myTopicParam);
        PageInfo<Topic> pageInfo =new PageInfo<>(topicList);
        pageInfo.setList(topicList);
        return pageInfo;
    }



    public List<TopicVo> changeListToVo(List<Topic> topicList,MyTopicParam myTopicParam){
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(myTopicParam.getUserId());
        List<TopicVo> voList = new ArrayList<>();
        for (Topic t : topicList){
            TopicVo mytop = new TopicVo();
            BeanUtils.copyProperties(t,mytop);
            int count = topicLikeMapper.selectCountByTopicIdAndUserId(t.getId(),myTopicParam.getUserId());
            int count1 = topicFavMapper.selectCountByTopicIdAndUserId(t.getId(),myTopicParam.getUserId());
            mytop.setTopicId(t.getId());
            mytop.setIsFav(count1);
            mytop.setIsLike(count);
            if(userInfo != null){
                Integer sex = userInfo.getUserSex();
                mytop.setUserSex(sex);
            }
            mytop.setGroupList(topicTagMapper.getGroupTagsByTopicId(t.getId()));
            mytop.setEventList(topicTagMapper.getEventTagsByTopicId(t.getId()));
            mytop.setContentMap(mytop.getContent());
            voList.add(mytop);
        }
        return  voList;
    }
    public List<TopicVo> changeListToVo(List<Topic> topicList,UserFansTopicLisDto userFansTopicLisDto){
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userFansTopicLisDto.getUserId());
        List<TopicVo> voList = new ArrayList<>();
        for (Topic t : topicList){
            TopicVo mytop = new TopicVo();
            BeanUtils.copyProperties(t,mytop);
            int count1 = topicFavMapper.selectCountByTopicIdAndUserId(t.getId(),userFansTopicLisDto.getUserId());
            int count = topicLikeMapper.selectCountByTopicIdAndUserId(t.getId(),userFansTopicLisDto.getUserId());
            mytop.setTopicId(t.getId());
            mytop.setIsLike(count);
            mytop.setIsFav(count1);
            if(userInfo != null){
                Integer sex = userInfo.getUserSex();
                mytop.setUserSex(sex);
            }

            List<Prize> prizeList = topicMapper.selectPrizeByTopicId(t.getChallengeTopicId());  //根据 主贴ID 查询奖品信息
            if(StrUtils.isNotEmpty(prizeList)){
                Integer prizeId =  prizeList.get(0).getId();
                String  prizeName = prizeList.get(0).getName();
                mytop.setPrizeId(prizeId);
                mytop.setPrizeName(prizeName);
            }

            mytop.setGroupList(topicTagMapper.getGroupTagsByTopicId(t.getId()));
            mytop.setEventList(topicTagMapper.getEventTagsByTopicId(t.getId()));
            mytop.setContentMap(mytop.getContent());
            voList.add(mytop);
        }
        return  voList;
    }

    public List<TopicsVo> changeUserTopicListToVo(List<Topic> topicList, MyTopicParam myTopicParam){
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(myTopicParam.getUserId());
        //查询对方是否关注我
        UserFans userFans = new UserFans();
        userFans.setUserId(myTopicParam.getMyUserId());
        userFans.setFansId(myTopicParam.getUserId());
        int count2 = userFansMapper.selectIfFollow(userFans);
        //插叙我是否关注对方
        UserFans userFans1 = new UserFans();
        userFans1.setUserId(myTopicParam.getUserId());
        userFans1.setFansId(myTopicParam.getMyUserId());
        int count3 = userFansMapper.selectIfFollow(userFans1);
        List<TopicsVo> voList = new ArrayList<>();
        String firstTime = "";
        String _firstTime= "";
        int i=0;
        for (Topic t : topicList){
            TopicsVo mytop = new TopicsVo();
            _firstTime = DateFormatUtils.format(t.getCreateTime(),DateFormatUtils.ISO_DATE_FORMAT.getPattern());
            BeanUtils.copyProperties(t,mytop);
            if (i++==0){
                firstTime = _firstTime;
                mytop.setIsFirst(1);
            } else {
                if (firstTime.equals(_firstTime)){
                    mytop.setIsFirst(0);
                } else {
                    firstTime = _firstTime;
                    mytop.setIsFirst(1);
                }
            }
            if(myTopicParam.getMyUserId() == null){
                mytop.setIsFav(0);
                mytop.setIsLike(0);
            }else {
                int count = topicLikeMapper.selectCountByTopicIdAndUserId(t.getId(), myTopicParam.getMyUserId());
                int count1 = topicFavMapper.selectCountByTopicIdAndUserId(t.getId(), myTopicParam.getMyUserId());
                mytop.setIsFav(count1);
                mytop.setIsLike(count);
            }
            if(userInfo != null){
                Integer sex = userInfo.getUserSex();
                mytop.setUserSex(sex);
            }
            List<Prize> prizeList = topicMapper.selectPrizeByTopicId(t.getChallengeTopicId());  //根据 主贴ID 查询奖品信息
            if(StrUtils.isNotEmpty(prizeList)){
                Integer prizeId =  prizeList.get(0).getId();
                String  prizeName = prizeList.get(0).getName();
                mytop.setPrizeId(prizeId);
                mytop.setPrizeName(prizeName);
            }
            mytop.setTopicId(t.getId());
            mytop.setGroupList(topicTagMapper.getGroupTagsByTopicId(t.getId()));
            mytop.setEventList(topicTagMapper.getEventTagsByTopicId(t.getId()));
            mytop.setContentMap(mytop.getContent());
            if(count3 == 0){
                //未关注
                mytop.setIsFocus(0);
            }
//            if(count2 == 1 && count3 == 0){
//                //粉丝
//                mytop.setIsFocus(2);
//            }
            if(count3 != 0){
                //已关注
                mytop.setIsFocus(1);
            }
//            if(count2 == 1 && count3 == 1){
//                //互相关注
//                mytop.setIsFocus(4);
//            }
            voList.add(mytop);
        }
        return  voList;
    }

    public List<MyTopicsVo> changeMyListToVo(List<Topic> topicList,MyTopicParam myTopicParam){
        List<MyTopicsVo> voList = new ArrayList<MyTopicsVo>();
        String firstTime = "";
        String _firstTime= "";
        int i=0;
        for (Topic t : topicList){
            MyTopicsVo mytop = new MyTopicsVo();
            _firstTime = DateFormatUtils.format(t.getCreateTime(),DateFormatUtils.ISO_DATE_FORMAT.getPattern());
            BeanUtils.copyProperties(t,mytop);
            if (i++==0){
                firstTime = _firstTime;
                mytop.setIsFirst(1);
            } else {
                if (firstTime.equals(_firstTime)){
                    mytop.setIsFirst(0);
                } else {
                    firstTime = _firstTime;
                    mytop.setIsFirst(1);
                }
            }
            int countLike = topicLikeMapper.selectCountByTopicIdAndUserId(t.getId(),myTopicParam.getUserId());
            int countFav = topicFavMapper.selectCountByTopicIdAndUserId(t.getId(),myTopicParam.getUserId());
            List<Prize> prizeList = topicMapper.selectPrizeByTopicId(t.getChallengeTopicId());  //根据 主贴ID 查询奖品信息
            if(StrUtils.isNotEmpty(prizeList)){
                Integer prizeId =  prizeList.get(0).getId();
                String  prizeName = prizeList.get(0).getName();
                mytop.setPrizeId(prizeId);
                mytop.setPrizeName(prizeName);
            }
            mytop.setIfLike(countLike);
            mytop.setIfFav(countFav);
            mytop.setGroupList(topicTagMapper.getGroupTagsByTopicId(t.getId()));
            mytop.setEventList(topicTagMapper.getEventTagsByTopicId(t.getId()));
            mytop.setContentMap(mytop.getContent());
            mytop.setIsOver(1);
            if(t.getExpireTime()!= null &&t.getExpireTime().getTime()<new Date().getTime()){
                mytop.setIsOver(0);
            }
            voList.add(mytop);
        }
        return voList;
    }
}
