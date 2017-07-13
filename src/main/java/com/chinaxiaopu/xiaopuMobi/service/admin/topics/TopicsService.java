package com.chinaxiaopu.xiaopuMobi.service.admin.topics;

import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.MsgFromDto;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.ChannelListDto;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.TopicIndexDto;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.TopicsListDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.AllChallengeDto;
import com.chinaxiaopu.xiaopuMobi.mapper.*;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.chinaxiaopu.xiaopuMobi.security.realm.ShiroUser;
import com.chinaxiaopu.xiaopuMobi.service.MsgPushService;
import com.chinaxiaopu.xiaopuMobi.service.topic.TopicService;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.admin.topics.TipoffVo;
import com.chinaxiaopu.xiaopuMobi.vo.admin.topics.TopicDetaVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.AllTopickInSamePkVo;
import com.chinaxiaopu.xiaopuMobi.vo.admin.topics.TopicIndexVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.TopicTagsVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.intellij.lang.annotations.Flow;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 内容管理
 * Created by Administrator on 2016/11/7.
 */
@Slf4j
@Service
public class TopicsService {
    @Autowired
    private TopicPkMapper topicPkMapper;
    @Autowired
    private PkChannelMapper pkChannelMapper;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private TopicTagMapper topicTagMapper;

    @Autowired
    private PkVoteResultMapper pkVoteResultMapper;

    @Autowired
    private TipoffMapper tipoffMapper;
    @Autowired
    private TopicCommentMapper topicCommentMapper;

    @Autowired
    MsgPushService msgPushService;


    /**
     * 查询频道列表
     *
     * @return
     */
    public List<PkChannel> getChannelsList() {

        return pkChannelMapper.selectPkChannel();
    }

    /**
     * 后台 ：查询 图文视频列表
     *
     * @param topicsListDto
     * @return
     */
    public PageInfo<TopicsListDto> selectTopicsList(TopicsListDto topicsListDto) {
        if (topicsListDto.getPage() != null && topicsListDto.getRows() != null) {
            PageHelper.startPage(topicsListDto.getPage(), topicsListDto.getRows());
        }
        List<TopicsListDto> list = topicMapper.selectTopicsList(topicsListDto);
        for (TopicsListDto vo : list) {
            if (vo.getIsOfficial() == 1) {
                vo.setRealName("校普官方");
            }

        }
        PageInfo<TopicsListDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 查询 频道列表
     *
     * @return
     */
    public PageInfo<ChannelListDto> channelList(ChannelListDto channelListDto) {
        if (channelListDto.getPage() != null && channelListDto.getRows() != null) {
            PageHelper.startPage(channelListDto.getPage(), channelListDto.getRows());
        }
        String filePath = null;
        try {
            filePath = SystemConfig.getInstance().getImgsDomain();//图片路径
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<ChannelListDto> list = topicMapper.channelList(channelListDto);
        for (ChannelListDto vo : list) {
            vo.setPath(filePath);
        }
        PageInfo<ChannelListDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 添加 频道
     *
     * @param pk
     * @return
     */
    public int saveChannel(PkChannel pk) {
        int row = 0;
        if (StrUtils.isNotEmpty(pk.getId())) {
            row = pkChannelMapper.updateByPrimaryKeySelective(pk);
        } else {
            pk.setType(1);
            pk.setStatus(1);
            row = pkChannelMapper.insert(pk);
        }
        return row;
    }

    /**
     * 查询所有来战者
     *
     * @param
     * @return
     */
    public PageInfo<AllTopickInSamePkVo> getAllTopickInSamePkVo(AllChallengeDto allChallengeDto) throws Exception {
        if (allChallengeDto.getPage() != null && allChallengeDto.getRows() != null) {
            PageHelper.startPage(allChallengeDto.getPage(), allChallengeDto.getRows());
        }
        List<AllTopickInSamePkVo> list = topicPkMapper.selectSomeInfoByPkId(allChallengeDto);
        for (AllTopickInSamePkVo all : list) {
            List<TopicTagsVo> groupList = topicTagMapper.getGroupTagsByTopicId(all.getTopicId());
            List<TopicTagsVo> eventList = topicTagMapper.getEventTagsByTopicId(all.getTopicId());
            String tags = "";
            for (TopicTagsVo topicTagsVo : groupList) {
                tags += topicTagsVo.getName() + "、";
            }
            for (TopicTagsVo topicTagsVo : eventList) {
                tags += topicTagsVo.getName() + "、";
            }
            if (tags != "") {
                tags = tags.substring(0, tags.length() - 1);
            }
            all.setTags(tags);
        }
        PageInfo<AllTopickInSamePkVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 推荐
     *
     * @param topic
     * @return
     */
    public int updateRecoment(Topic topic) {
        int row = 0;
        /**
         * 判断当前的推荐值是 0,1,2,3,4,5中的那个
         */
        Topic e = topicMapper.selectTopicById(topic.getId());//当天主题的推荐值
        TopicExample example = new TopicExample();

        example.createCriteria().andRecommendEqualTo(topic.getRecommend());
        List<Topic> topicList = topicMapper.selectByExample(example);//原推荐用户
        if (StrUtils.isNotEmpty(e) && StrUtils.isNotEmpty(topicList)) {
            Topic oldTopic = topicList.get(0);
            oldTopic.setRecommend(e.getRecommend());
            //topicMapper.updateOldrecommend(oldTopic);
            topicMapper.updateByPrimaryKeySelective(oldTopic);
        }
        e.setRecommend(topic.getRecommend());
        row = topicMapper.updateByPrimaryKeySelective(e);
        return row;
    }

    /**
     * 删除图文（is_delete =0）
     *
     * @param topic
     * @return
     * @throws Exception
     */
    public int deleteTopic(Topic topic) throws Exception {
        int row = topicMapper.deleteTopic(topic.getId());
        /***********消息推送************/
       if (row > 0) {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        //基础数据获取
        TopicExample example = new TopicExample();
        example.createCriteria().andIdEqualTo(topic.getId());
        List<Topic> list = topicMapper.selectByExample(example);
        if (StrUtils.isNotEmpty(list)) {
            Gson gson = new Gson();
            Map<String,Object> map = gson.fromJson(list.get(0).getContent(), Map.class);
            String imgUrl = "";
            String name="";
            String[] img = map.get("urls").toString().substring(1,map.get("urls").toString().length()-1).split(",");
            if (list.get(0).getType()==1) {
                imgUrl=img[0];
                name="一个图文";
            } else {
                name="一个视频";
                imgUrl=img[1];
            }
            int action = Constants.SYS_TOPIC_DEL;
            int userId = list.get(0).getCreatorId();
            int sender = shiroUser.getId();
            MsgFromDto msgFromDto = new MsgFromDto();
            msgFromDto.setTopicId(topic.getId());
            if (StrUtils.isNotEmpty(list.get(0).getSlogan()) || "".equals(list.get(0).getSlogan())) {
                msgFromDto.setTopicName(name);
            } else {
                msgFromDto.setTopicName(list.get(0).getSlogan());
            }
            msgFromDto.setTopicName(list.get(0).getSlogan());
            msgFromDto.setIsPk(list.get(0).getIsPk());
            msgFromDto.setTopicType(list.get(0).getType());
            msgFromDto.setRightImgUrl(imgUrl);
            Set<Integer> users = new HashSet<>();
            users.add(userId);
            msgPushService.sendPushMsgByUser(action,users,sender,msgFromDto);
            System.out.println("push is end");
        }
       }
        return row;
    }

    public Map<String, Object> getPkLeizhu(Integer topicId) throws Exception {
        int joinCnt = topicPkMapper.selectCountBytopicId(topicId);
        Date endTime = topicPkMapper.getEndTime(topicId);
        String name = topicPkMapper.whoIsLeiZhu(topicId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("joinCnt", joinCnt);
        map.put("endTime", endTime);
        map.put("name", name);
        return map;
    }

    /**
     * 查询详情
     *
     * @param topicId
     * @return
     */
    public TopicDetaVo selectTopicsDetail(Integer topicId) {
        TopicDetaVo vo = topicMapper.selectTopicsDetail(topicId);
        //查询创建人所有的社团
        TopicDetaVo groupVo = topicMapper.selectTopicsGroupById(vo.getCreatorId());
        if (StrUtils.isNotEmpty(groupVo)) {
            if (StrUtils.isNotEmpty(groupVo.getGroupName())) {
                vo.setGroupName(groupVo.getGroupName());
            }
        }
        //查询主题标签
        TopicDetaVo tagVo = topicMapper.selectTopicsTagById(topicId);
        if (StrUtils.isNotEmpty(tagVo)) {
            if (StrUtils.isNotEmpty(tagVo.getTagName())) {
                vo.setTagName(tagVo.getGroupName());
            }
        }
        return vo;
    }

    /**
     * 查询详情
     *
     * @param topicId
     * @return
     */
    public TopicDetaVo selectTopicsDetail1(Integer topicId) {
        TopicDetaVo vo = topicMapper.selectTopicsDetail1(topicId);
        //查询 主题的所有挑战人数
        Integer challenteCount = topicMapper.selectChallergeCount(topicId);
        vo.setChallenteCount(challenteCount);
        //查询当前主题的投票数
        TopicDetaVo voteCntVo = topicMapper.selectVoteCnt(topicId);
        if (voteCntVo != null) {
            if (StrUtils.isNotEmpty(voteCntVo.getVoteCnt())) {
                vo.setVoteCnt(voteCntVo.getVoteCnt());
            } else {
                vo.setVoteCnt(0);
            }
        } else {
            vo.setVoteCnt(0);
        }
        return vo;
    }


    /**
     * 查询投诉列表
     *
     * @param topicId
     * @return
     */
    public List<TipoffVo> selectTipOffList(Integer topicId) {
        return tipoffMapper.selectTipOffList(topicId);
    }

    /**
     * 评论列表
     *
     * @param topicId
     * @return
     */
    public List<TopicComment> selectCommentList(Integer topicId) {
        return topicCommentMapper.selectByTopicId(topicId);
    }

    /**
     * 删除评论
     *
     * @param tc
     */
    public int deleteComment(TopicComment tc) {
       /* return
                topicCommentMapper.deleteByPrimaryKey(tc.getId());*/
        int row = topicCommentMapper.deleteByPrimaryKey(tc.getId());
        int row1 = 0;
        if (row > 0) {
            /**
             * 修改评论数
             */
            row1 = topicMapper.updateCommentCnt(tc.getTopicId());
        }
        return row1;
    }

    /**
     * 不显示处理
     *
     * @param tc
     */
    public int updateComment(TopicComment tc) {
        tc.setComment("该评论已被管理员删除");
        return topicCommentMapper.updateByPrimaryKeySelective(tc);
    }

    /**
     * 删除投诉内容
     *
     * @param tc
     */
    public int deleteTipOff(Tipoff tc) {
        return tipoffMapper.deleteByPrimaryKey(tc.getId());
    }

    /**
     * 删除频道
     *
     * @param pk
     * @return
     */
    public int deleteChannel(PkChannel pk) {
        int row = 0;
        TopicExample exampl = new TopicExample();
        exampl.createCriteria().andChannelIdEqualTo(pk.getId()).andStatusEqualTo(1).andIsDeleteEqualTo(1);
        long count = topicMapper.countByExample(exampl);
        if (count > 0) {
            row = 2;
        } else {
            row = 1;
            PkChannel e = new PkChannel();
            e.setStatus(-1);
            PkChannelExample pkExample = new PkChannelExample();
            pkExample.createCriteria().andIdEqualTo(pk.getId()).andStatusEqualTo(1);
            pkChannelMapper.updateByExampleSelective(e, pkExample);
        }
        return row;
    }
}
