package com.chinaxiaopu.xiaopuMobi.service.topic;

import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import com.chinaxiaopu.xiaopuMobi.dto.MsgFromDto;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.TopicConfirmDto;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.TopicListDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicByPrizeDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicIdDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicListDtos;
import com.chinaxiaopu.xiaopuMobi.mapper.*;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.chinaxiaopu.xiaopuMobi.service.MsgPushService;
import com.chinaxiaopu.xiaopuMobi.util.DateTimeUtil;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.admin.topics.TopicDetailVo;
import com.chinaxiaopu.xiaopuMobi.vo.admin.topics.TopicListVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by Wang on 2016/11/2.
 */
@Service
public class TopicService {

    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private TopicLikeMapper topicLikeMapper;
    @Autowired
    private TopicFavMapper topicFavMapper;
    @Autowired
    private TopicPkMapper topicPkMapper;
    @Autowired
    private TopicTagMapper topicTagMapper;
    @Autowired
    private PkVoteResultMapper pkVoteResultMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private PrizeMapper prizeMapper;
    @Autowired
    private PrizeImprestMapper prizeImprestMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private PkVoteMapper pkVoteMapper;
    @Autowired
    private PkChannelMapper pkChannelMapper;
    @Autowired
    private MsgPushService msgPushService;//消息推送

    /**
     * 获取最新图文内容列表
     *
     * @return List<TopicVo>
     * author:Wang
     */
    public PageInfo<TopicVo> newList(TopicListDtos topicListDtos, UserInfo userInfo) {
        List<TopicVo> topicVoList = new ArrayList<>();
        if (!StringUtils.isEmpty(topicListDtos.getPage()) && !StringUtils.isEmpty(topicListDtos.getRows())) {
            PageHelper.startPage(topicListDtos.getPage(), topicListDtos.getRows());
        }
        if (!StringUtils.isEmpty(userInfo)) {
            topicListDtos.setUserId(userInfo.getUserId());
        }
        List<TopicVo> topicList = topicMapper.newList(topicListDtos);
        PageInfo<TopicVo> pageInfo = new PageInfo<>(topicList);

        for (TopicVo topicVo : topicList) {
            //得到标签
            topicVo = getTags(topicVo);
            topicVo.setContentMap(topicVo.getContent());
            topicVoList.add(topicVo);
        }
        pageInfo.setList(topicVoList);
        return pageInfo;
    }

    /**
     * 获取最热文内容列表
     *
     * @return List<TopicVo>
     * author:Wang
     */
    public PageInfo<TopicVo> hotList(ContextDto contextDto, UserInfo userInfo) {
        List<TopicVo> topicVoList = new ArrayList<>();
        if (!StringUtils.isEmpty(contextDto.getPage()) && !StringUtils.isEmpty(contextDto.getRows())) {
            PageHelper.startPage(contextDto.getPage(), contextDto.getRows());
        }
        Integer userId = 0;
        if (!StringUtils.isEmpty(userInfo)) {
            userId = userInfo.getUserId();
        }
        List<TopicVo> topicList = topicMapper.hotList(userId);
        PageInfo<TopicVo> pageInfo = new PageInfo<>(topicList);

        for (TopicVo topicVo : topicList) {
            //得到标签
            topicVo = getTags(topicVo);
            topicVo.setContentMap(topicVo.getContent());
            topicVoList.add(topicVo);
        }
        pageInfo.setList(topicVoList);
        return pageInfo;
    }

    /**
     * 是否点赞或收藏
     *
     * @param topicVo
     * @param userInfo
     * @return
     */
    private TopicVo isLikeOrFav(TopicVo topicVo, UserInfo userInfo) {
        if (!StringUtils.isEmpty(userInfo)) {
            int likeCount = topicLikeMapper.selectCountByTopicIdAndUserId(topicVo.getTopicId(), userInfo.getUserId());
            if (likeCount == 1) {
                topicVo.setIsLike(likeCount);
            }
            int favCount = topicFavMapper.selectCountByTopicIdAndUserId(topicVo.getTopicId(), userInfo.getUserId());
            if (favCount == 1) {
                topicVo.setIsFav(favCount);
            }
        }
        return topicVo;
    }

    private TopicVo isLikeOrFavByUser(TopicVo topicVo, List<Integer> likeList, List<Integer> favList) {
        Boolean isLike = likeList.contains(topicVo.getTopicId());
        if (isLike) {
            topicVo.setIsLike(1);
        }
        Boolean isFav = favList.contains(topicVo.getTopicId());
        if (isFav) {
            topicVo.setIsFav(1);
        }
        return topicVo;
    }

    /**
     * 根据奖励物品id查询图文信息
     *
     * @param topicByPrizeDto
     * @param userInfo
     * @return
     */
    public PageInfo<TopicVo> topicListByPrizeId(TopicByPrizeDto topicByPrizeDto, UserInfo userInfo) {
        List<TopicVo> topicVoList = new ArrayList<>();
        if (!StringUtils.isEmpty(topicByPrizeDto.getPage()) && !StringUtils.isEmpty(topicByPrizeDto.getRows())) {
            PageHelper.startPage(topicByPrizeDto.getPage(), topicByPrizeDto.getRows());
        }
        if (!StringUtils.isEmpty(userInfo)) {
            topicByPrizeDto.setUserId(userInfo.getUserId());
        }

        if (userInfo == null) {
            topicByPrizeDto.setUserId(0);   //用户不存在
        }

        List<TopicVo> topicList = topicMapper.topicListByPrizeId(topicByPrizeDto);

        PageInfo<TopicVo> pageInfo = new PageInfo<>(topicList);
        for (TopicVo vo : topicList) {
            vo = getTags(vo);  //得到标签
            vo.setContentMap(vo.getContent());
            topicVoList.add(vo);
            List<UserPkVo> userList = pkChannelMapper.getChannelTopicUserList(vo.getTopicId()); //参加此次PK的所有用户
            if (StrUtils.isNotEmpty(userList)) {
                vo.setUserList(userList);
               /* for (UserPkVo userPkVo : userList) {
                    if ((userPkVo.getTopicId()).equals(vo.getTopicId())) {
                        List<UserPkVo> _userList = vo.getUserList();
                        if (_userList == null) {
                            _userList = new ArrayList<>();
                            _userList.add(userPkVo);
                            vo.setUserList(_userList);
                        } else {
                            _userList.add(userPkVo);
                        }
                    }
                }*/
            }
        }
        pageInfo.setList(topicVoList);
        return pageInfo;
    }

    /**
     * 获取标签
     *
     * @param topicVo
     * @return TopicVo
     */
    private TopicVo getTags(TopicVo topicVo) {

        List<TopicTagsVo> eventTags = topicTagMapper.getEventTagsByTopicId(topicVo.getTopicId());
        List<TopicTagsVo> groupTags = topicTagMapper.getGroupTagsByTopicId(topicVo.getTopicId());
        topicVo.setEventList(eventTags);
        topicVo.setGroupList(groupTags);

        return topicVo;
    }

    /**
     * 推荐的主题
     *
     * @return List<TopicVo>
     */
    public List<LatestTopicVo> VoteTop5() {
//        return topicMapper.VoteTop5();
        return topicMapper.VoteTop5_recommend();
    }

    /**
     * pk主题上下文
     *
     * @param topicIdDto
     * @param userInfo
     * @return List<TopicVo>
     */
    public PKContextVo voteContext(TopicIdDto topicIdDto, UserInfo userInfo) {
        PKContextVo pkContextVo = new PKContextVo();
        //查询显示主题
        Topic selTopic = topicMapper.selectByPrimaryKey(topicIdDto.getTopicId());

        List<Integer> favList = new ArrayList<>();
        List<Integer> likeList = new ArrayList<>();
        if (!StringUtils.isEmpty(userInfo)) {
            likeList = topicLikeMapper.selectTopicIdListByUserId(userInfo.getUserId());
            favList = topicFavMapper.selectTopicIdListByUserId(userInfo.getUserId());
        }

        TopicVo topicVo = new TopicVo();
        if (!StringUtils.isEmpty(selTopic)) {
            BeanUtils.copyProperties(selTopic, topicVo);
            topicVo.setTopicId(selTopic.getId());
            //是否点赞和收藏
            if (!StringUtils.isEmpty(userInfo)) {
                topicVo = isLikeOrFavByUser(topicVo, likeList, favList);
            }
            //得到标签
            topicVo = getTags(topicVo);
            topicVo.setContentMap(topicVo.getContent());
            //关联user_info中的数据
            UserInfo creator = userInfoMapper.selectByPrimaryKey(topicVo.getCreatorId());
            if (!StringUtils.isEmpty(creator)) {
                topicVo.setCreatorNickname(creator.getNickName());
                topicVo.setCreatorAvatar(creator.getAvatarUrl());
            }
            //非官方 查询投票数
            if (topicVo.getIsOfficial() != 1) {
                PkVoteResult pkVoteResult = pkVoteResultMapper.selectByTopicId(topicIdDto.getTopicId());
                if (StrUtils.isNotEmpty(pkVoteResult)) {
                    topicVo.setVoteCnt(pkVoteResult.getVoteCnt());
                }
            }
            pkContextVo.setTopicVo(topicVo);
        }
        List<TopicVo> _topicList;
        if (!StringUtils.isEmpty(topicIdDto.getType()) && topicIdDto.getType() == 2) {
            //视频
            _topicList = topicMapper.voteContextMovie(selTopic.getChallengeTopicId());
        } else {
            //图文
            _topicList = topicMapper.voteContext(selTopic.getChallengeTopicId());
        }
        List<TopicVo> topicVoList = new ArrayList<>();
        //排序序号
        int topicRank = 1;

        for (TopicVo topic : _topicList) {
            if (!StringUtils.isEmpty(userInfo)) {
                topicVo = isLikeOrFavByUser(topicVo, likeList, favList);
            }
            topic = getTags(topic);
            topic.setContentMap(topic.getContent());
            if (!StringUtils.isEmpty(topicIdDto.getType()) && topicIdDto.getType() == 2) {
                //视频
                topic.setTopicRank(topicRank++);
                if (topic.getTopicId().equals(topicVo.getChallengeTopicId())) {
                    pkContextVo.setChallengeTopicVo(topic);
                } else {
                    topicVoList.add(topic);
                }
            } else {
                //图文
                if (!topic.getTopicId().equals(topicVo.getTopicId())) {
                    topicVoList.add(topic);
                }
            }

        }
        pkContextVo.setList(topicVoList);

        //pkId
        Integer pkId = topicPkMapper.selectPkIdByTopicId(topicIdDto.getTopicId());
        pkContextVo.setPkId(pkId);

        //投票的TopicId
        if (!StringUtils.isEmpty(userInfo)) {
            PkVote pkVote = pkVoteMapper.selectByPkIdAndUserId(pkId, userInfo.getUserId());
            if (!StringUtils.isEmpty(pkVote)) {
                pkContextVo.setVoteTopicId(pkVote.getTopicId());
            }
        }


        return pkContextVo;
    }

    /**
     * 主题详情
     *
     * @param id
     * @param userInfo
     * @return
     */
    public TopicVo view(int id, UserInfo userInfo) {
        Topic topic = topicMapper.selectByPrimaryKey(id);
        TopicVo topicVo = new TopicVo();
        if (!StringUtils.isEmpty(topic)) {
            BeanUtils.copyProperties(topic, topicVo);
            topicVo.setTopicId(topic.getId());
            //是否点赞和收藏
            topicVo = isLikeOrFav(topicVo, userInfo);
            //得到标签
            topicVo = getTags(topicVo);
            topicVo.setContentMap(topicVo.getContent());
        }
        return topicVo;
    }

    /**
     * 能否发起挑战
     *
     * @param pkId
     * @param userInfo
     * @return
     */
    public Boolean isAttend(Integer pkId, UserInfo userInfo) {
        Boolean result = false;
        int count = topicMapper.isAttend(pkId, userInfo.getUserId());
        if (count == 0) {
            result = true;
        }
        return result;
    }

    /**
     * 后台获取需要审核的主题
     *
     * @param topicListDto
     * @return
     */
    public PageInfo<TopicListVo> topicList(TopicListDto topicListDto) {
        if (!StringUtils.isEmpty(topicListDto.getPage()) && !StringUtils.isEmpty(topicListDto.getRows())) {
            PageHelper.startPage(topicListDto.getPage(), topicListDto.getRows());
        }
        if (StringUtils.isEmpty(topicListDto.getKeyword())) {
            topicListDto.setKeyword(null);
        }
        if (StringUtils.isEmpty(topicListDto.getStartTime()) || StringUtils.isEmpty(topicListDto.getEndTime())) {
            topicListDto.setStartTime(null);
            topicListDto.setEndTime(null);
        }
        List<TopicListVo> topicList = topicMapper.topicList(topicListDto);
        for (TopicListVo topicListVo : topicList) {
            List<TopicTagsVo> groupTags = topicTagMapper.getGroupTagsByTopicId(topicListVo.getId());
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < groupTags.size(); i++) {
                stringBuffer.append(groupTags.get(i).getName()).append(" ");
            }
            //标签拼接
            topicListVo.setTags(stringBuffer.toString());
            //挑战数
            Map<String, Double> map = new Gson().fromJson(topicListVo.getRule(), Map.class);
            topicListVo.setChallengeCnt(map.get("challengeCnt").intValue());
        }
        PageInfo<TopicListVo> pageInfo = new PageInfo<>(topicList);
        return pageInfo;
    }

    /**
     * 后台获取主题的详情
     *
     * @param id
     * @return
     */
    public TopicDetailVo topicDetail(int id) {
        TopicDetailVo topicDetailVo = topicMapper.topicDetail(id);

        Integer pkCnt = topicMapper.selectPkCntByUserId(topicDetailVo.getCreatorId());
        Integer winCnt = topicMapper.selectWinCntByUserId(topicDetailVo.getCreatorId());
        List<Group> groupList = groupMapper.getMyGroupListByUserId(topicDetailVo.getCreatorId(), 1);
        StringBuffer groupBuffer = new StringBuffer();
        for (Group group : groupList) {
            groupBuffer.append(group.getName()).append(" ");
        }

        List<TopicTagsVo> eventTags = topicTagMapper.getEventTagsByTopicId(topicDetailVo.getId());
        List<TopicTagsVo> groupTags = topicTagMapper.getGroupTagsByTopicId(topicDetailVo.getId());
        StringBuffer tagsBuffer = new StringBuffer();
        for (TopicTagsVo eventTagsVo : eventTags) {
            tagsBuffer.append(eventTagsVo.getName()).append(" ");
        }
        for (TopicTagsVo groupTagsVo : groupTags) {
            tagsBuffer.append(groupTagsVo.getName()).append(" ");
        }
        //参加pk的次数
        topicDetailVo.setPkCnt(pkCnt);
        //赢得冠军的次数
        topicDetailVo.setWinCnt(winCnt);
        topicDetailVo.setGroups(groupBuffer.toString());
        topicDetailVo.setTags(tagsBuffer.toString());

        return topicDetailVo;
    }

    @Transactional
    public Boolean topicConfirm(TopicConfirmDto topicConfirmDto) {
        Boolean result = false;
        Topic topic = topicMapper.selectByPrimaryKey(topicConfirmDto.getTopicId());
        topic.setStatus(topicConfirmDto.getStatus());
        topic.setUpdateTime(new Date());
        if (!StringUtils.isEmpty(topicConfirmDto.getReason())) {
            topic.setFurther(topicConfirmDto.getFurther());
        }
        int count = topicMapper.updateByPrimaryKeySelective(topic);
        if (count == 1) {
            //封装消息体
            int userId = topic.getCreatorId();
            int sender = topicConfirmDto.getUserId();
            MsgFromDto msgFromDto = new MsgFromDto();
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
            Set<Integer> users = new HashSet<>();
            users.add(userId);

            if (topic.getStatus() == 2) {
                //审核pk申请奖品不通过 还库存奖品数量
                TopicPk topicPk = topicPkMapper.selectByTopicId(topic.getId());
                Map<String, Object> map = new HashedMap();
                map.put("pkId", topicPk.getId());
                map.put("prizeId", topicPk.getPrizeId());
                prizeMapper.giveBackStock(topicPk.getPrizeId());
                prizeImprestMapper.delectImprestWhenTurnDown(map);

                //消息推送--申请PK奖品审核：驳回
                int action = Constants.PK_CREATE_AUDIT_NO;
                msgFromDto.setRemark(topicConfirmDto.getReason());
                msgPushService.sendPushMsgByUser(action,users,sender,msgFromDto);
            }else{
                //消息推送--申请PK奖品审核：通过
                int action = Constants.PK_CREATE_AUDIT_OK;
                msgPushService.sendPushMsgByUser(action,users,sender,msgFromDto);
            }
            result = true;
        }
        return result;
    }

    /**
     * 获取首页推荐列表
     *
     * @return
     */
    public List<TopicIndexVo> getIndexList() {
        List<TopicIndexVo> list = topicMapper.getIndexList();
        if (StrUtils.isNotEmpty(list)) {
            for (TopicIndexVo vo : list) {
                vo.setContentMap(vo.getContent());
            }
        }
        return list;
    }

    public TopicVo latest(TopicIdDto topicIdDto, UserInfo userInfo) {
        TopicVo topicVo = topicMapper.latestTopic(topicIdDto.getTopicId(),userInfo.getUserId());
        topicVo = getTags(topicVo);
        topicVo.setContentMap(topicVo.getContent());
        return topicVo;
    }
}