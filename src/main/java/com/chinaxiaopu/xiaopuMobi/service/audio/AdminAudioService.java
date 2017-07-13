package com.chinaxiaopu.xiaopuMobi.service.audio;

import com.chinaxiaopu.xiaopuMobi.dto.CreatTopicDto;
import com.chinaxiaopu.xiaopuMobi.dto.admin.channel.ChannelRecommend;
import com.chinaxiaopu.xiaopuMobi.dto.audio.AudioConfirmDto;
import com.chinaxiaopu.xiaopuMobi.dto.audio.CreatePersonnelDto;
import com.chinaxiaopu.xiaopuMobi.dto.audio.PersonnelDto;
import com.chinaxiaopu.xiaopuMobi.dto.audio.PositionDto;
import com.chinaxiaopu.xiaopuMobi.mapper.*;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.TopicConfirmDto;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.chinaxiaopu.xiaopuMobi.quartz.AnchorAudioJob;
import com.chinaxiaopu.xiaopuMobi.quartz.QuartzUtils;
import com.chinaxiaopu.xiaopuMobi.service.topic.AawardPresenService;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.audio.AudioDetailVo;
import com.chinaxiaopu.xiaopuMobi.vo.audio.PersonnelVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by ycy on 2016/12/6.
 */
@Slf4j
@Service
public class AdminAudioService {

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private ChannelAnchorMapper channelAnchorMapper;

    @Autowired
    private PositionMapper positionMapper;

    @Autowired
    private QuartzUtils quartzUtils;

    @Autowired
    private AawardPresenService aawardPresenService;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private AnchorPositionMapper anchorPositionMapper;
    @Autowired
    private TopicPlayMapper topicPlayMapper;
    /**
     * 音频列表（主播）
     *
     * @param topic
     * @return
     * @throws Exception
     */
    public PageInfo<Topic> getAudioList(Topic topic) throws Exception {
        if (!StringUtils.isEmpty(topic.getPage()) && !StringUtils.isEmpty(topic.getRows())) {
            PageHelper.startPage(topic.getPage(), topic.getRows());
        }
        List<Topic> list = topicMapper.selectAudioList(topic);
        PageInfo<Topic> pageInfo = new PageInfo<Topic>(list);
        pageInfo.setList(list);
        return pageInfo;
    }

    /**
     * 音频列表（管理员）
     *
     * @param audioConfirmDto
     * @return
     * @throws Exception
     */
    public PageInfo<AudioDetailVo> getAudioList1(AudioConfirmDto audioConfirmDto) throws Exception {
        if (!StringUtils.isEmpty(audioConfirmDto.getPage()) && !StringUtils.isEmpty(audioConfirmDto.getRows())) {
            PageHelper.startPage(audioConfirmDto.getPage(), audioConfirmDto.getRows());
        }
        List<AudioDetailVo> list = topicMapper.selectAudioList1(audioConfirmDto);
        PageInfo<AudioDetailVo> pageInfo = new PageInfo<AudioDetailVo>(list);
        pageInfo.setList(list);
        return pageInfo;
    }

    /**
     * 职位列表
     * @param positionDto
     * @return
     * @throws Exception
     */
    public PageInfo<Position> getPositionList(PositionDto positionDto) throws Exception {
        if (!StringUtils.isEmpty(positionDto.getPage()) && !StringUtils.isEmpty(positionDto.getRows())) {
            PageHelper.startPage(positionDto.getPage(), positionDto.getRows());
        }
        List<Position> list = positionMapper.selectByPositionDto(positionDto);
        PageInfo<Position> pageInfo = new PageInfo<Position>(list);
        pageInfo.setList(list);
        return pageInfo;
    }

    /**
     * 人员列表
     * @param personnelDto
     * @return
     * @throws Exception
     */
    public PageInfo<PersonnelVo> getPersonnelList(PersonnelDto personnelDto) throws Exception {
        if (!StringUtils.isEmpty(personnelDto.getPage()) && !StringUtils.isEmpty(personnelDto.getRows())) {
            PageHelper.startPage(personnelDto.getPage(), personnelDto.getRows());
        }
        List<PersonnelVo> list = userInfoMapper.selectAnchorInfo(personnelDto);
        PageInfo<PersonnelVo> pageInfo = new PageInfo<PersonnelVo>(list);
        pageInfo.setList(list);
        return pageInfo;
    }

    /**
     * 职位验证
     * @param name
     * @return
     */
    public boolean checkPositionName(String name){
        boolean ishas=false;
        int a=positionMapper.selectCntByName(name);
        if(a>0){
            ishas=true;
        }
        return ishas;
    }
    /**
     * 添加职位
     * @param position
     * @return
     */
    public boolean addPosition(Position position)
    {
        boolean ishas = false;
        //判断职位是否存在
        if(checkPositionName(position.getPositionName())){
            return false;
        }
        position.setAvailable((byte)1);
        position.setType(1);
        position.setCreateTime(new Date());
        position.setIsOfficial((byte)1);
        int a = positionMapper.insert(position);
        if (a > 0) {
            ishas = true;
        }
        return ishas;
    }

    /**
     * 员工赋职
     * @param createPersonnelDto
     * @return
     */
    @Transactional
    public boolean addPersonnel(CreatePersonnelDto createPersonnelDto){
        boolean ishas = false;
        UserRole userRole=new UserRole();
        AnchorPosition anchorPosition=new AnchorPosition();
        int userId=aawardPresenService.checkUserInfo(createPersonnelDto.getRealName(),createPersonnelDto.getMobile());
        if(StringUtils.isEmpty(userId)){
            return false;
        }
        userRole.setUserId(userId);
        userRole.setRoleId(4);
        int a=userRoleMapper.insert(userRole);
        anchorPosition.setAnchorId(userId);
        anchorPosition.setPositionId(createPersonnelDto.getPositionId());
        int b=anchorPositionMapper.insert(anchorPosition);
        if(a>0 && b>0){
            ishas=true;
        }
        return ishas;
    }


    /**
     * 删除职位
     * @param id
     * @return
     */
    public boolean delPosition(Integer id)
    {
        boolean ishas = false;
        Position position=new Position();
        if(!StringUtils.isEmpty(id)){
            position.setId(id);
            position.setAvailable((byte)0);
        }
        int a = positionMapper.updateByPrimaryKeySelective(position);
        if (a > 0) {
            ishas = true;
        }
        return ishas;
    }

    /**
     * 删除权限
     * @param id
     * @return
     */
    @Transactional
    public boolean delPersonnel(Integer id)
    {
        boolean ishas = false;
        if(StringUtils.isEmpty(id)){
           return false;
        }
        int a=userRoleMapper.deleteById(id,4);
        int b=anchorPositionMapper.deleteById(id);
        if(a>0 && b>0){
            ishas=true;
        }
        return ishas;
    }




    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    public AudioDetailVo getAudioDetail(Integer id) {
        AudioDetailVo audioDetailVo = new AudioDetailVo();
        Topic topic = topicMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(topic, audioDetailVo);
        audioDetailVo.setContentMap(topic.getContent());
        audioDetailVo.setFurtherMap(topic.getFurther());
        return audioDetailVo;
    }

    /**
     * 通过or驳回
     *
     * @param topicConfirmDto
     * @return
     */
    public boolean updateStatus(TopicConfirmDto topicConfirmDto) {
        boolean ishas = false;
        Topic topic = new Topic();
        topic.setId(topicConfirmDto.getTopicId());
        topic.setStatus(topicConfirmDto.getStatus());
        if (!StringUtils.isEmpty(topicConfirmDto.getReason())) {
            topic.setFurther(topicConfirmDto.getFurther());
        }
        int a = topicMapper.updateByPrimaryKeySelective(topic);
        if (a > 0) {
            ishas = true;
        }
        return ishas;
    }


    /**
     * 创建音频
     *
     * @param creatTopicDto
     * @return
     */
    @Transactional
    public int createAudioTopic(CreatTopicDto creatTopicDto) {
        Topic topic = new Topic();
        Date date=new Date();
        Gson gson = new Gson();
        if (creatTopicDto.getAudioContentJSON() != null) {
            String context = gson.toJson(creatTopicDto.getAudioContentJSON());
            //JSONObject object = JSONObject.fromObject(creatTopicDto.getContentJSON());
            //String context = object.toString();
            creatTopicDto.setContent(context);
        }
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(creatTopicDto.getCreatorId());
        //creatTopicDto.setUserId(userInfo.getUserId());
        creatTopicDto.setCreatorNickname(userInfo.getNickName());
        creatTopicDto.setCreatorId(userInfo.getUserId());
        creatTopicDto.setCreatorAvatar(userInfo.getAvatarUrl());
        BeanUtils.copyProperties(creatTopicDto, topic);
        topic.setUpdateTime(date);
        if(creatTopicDto.getIsDelete()==1){  //如果为1表示立即上线
            topic.setIsDelete(1);
            topic.setExpireTime(date);
        }
        if(creatTopicDto.getIsDelete()==-1){
            topic.setIsDelete(-1);
            topic.setExpireTime(creatTopicDto.getOnlineTime());
        }
        topic.setLikeCnt(0);
        topic.setCommentCnt(0);
        topic.setFavoriteCnt(0);
        topic.setCommentCnt(0);
        topic.setRecommend(0);
        topic.setStatus(0);
        topic.setCreateTime(date);
        int flag = topicMapper.insert(topic);
        ChannelAnchor channelAnchor = new ChannelAnchor();
        channelAnchor.setAnchorId(topic.getCreatorId());
        channelAnchor.setChannelId(topic.getChannelId());
        int count = channelAnchorMapper.selectCountByInfo(channelAnchor);
        if (count == 0) {
            channelAnchorMapper.insert(channelAnchor);
        }
        TopicPlay topicPlay = new TopicPlay();
        topicPlay.setPlayCnt(0);
        topicPlay.setUpdateTime(date);
        topicPlay.setTopicId(topic.getId());
        topicPlayMapper.insert(topicPlay);
        if(creatTopicDto.getIsDelete()==-1) {   //只有在-1的情况下开启定时任务
            //擂主贴
            int id = topic.getId();
            Date endTime = creatTopicDto.getOnlineTime();
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
                quartzUtils.addJob("开启音频->" + id, AnchorAudioJob.class, time, id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    @Transactional
    public int updateIsDeleteById(Integer id) {
        return topicMapper.updateIsDeleteById(id);
    }
}
