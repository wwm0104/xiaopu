package com.chinaxiaopu.xiaopuMobi.service.topic;

import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicPkDto;
import com.chinaxiaopu.xiaopuMobi.mapper.PkChannelMapper;
import com.chinaxiaopu.xiaopuMobi.model.PkChannel;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.topic.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
@Slf4j
@Service
public class FindChannelService {
    @Autowired
    private PkChannelMapper pkChannelMapper;

    /**
     * 频道投票汇总
     *
     * @return
     */
    public List<FindChannelVo> getFindChannelMenu() {
        String filePath = null;
        try {
            filePath = SystemConfig.getInstance().getImgsDomain();//图片路径
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return pkChannelMapper.getFindChannelMenu();
        List<FindChannelVo> _list = pkChannelMapper.getFindChannelMenu();
        for (FindChannelVo vo : _list) {
            vo.setPath(filePath);
        }
        return _list;
    }

    /**
     * 按照频道分类查询投票列表
     *
     * @param topicPkDto
     * @return
     */
    public List<TopicPkVo> getChannelTopicList(TopicPkDto topicPkDto) {
        List<TopicPkVo> list = pkChannelMapper.getChannelTopicList(topicPkDto);

        String filePath = null;
        try {
            filePath = SystemConfig.getInstance().getImgsDomain(); //图片路径
            for (TopicPkVo vo : list) {
                vo.setContentMap(vo.getContent());
                vo.setPath(filePath);
                List<UserPkVo> userList = pkChannelMapper.getChannelTopicUserList(vo.getId()); //参加此次PK的所有用户
                if (StrUtils.isNotEmpty(userList)) {
                    vo.setUserList(userList);
                   /* for (UserPkVo userPkVo : userList) {
                        if ((userPkVo.getTopicId()).equals(vo.getId())) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 武宁  2016.12.1
     * 首页热门频道列表
     *
     * @return
     */
    public List<MoreChildChannelVo> hotChannel() {

        return pkChannelMapper.hotChannel();
    }

    /**
     * 武宁 2016.12.1
     * 首页获取更多频道列表
     *
     * @return
     */
    public List<MoreChannelVo> moreChannel() {
        List<MoreChannelVo> mainList = pkChannelMapper.moreChannel(); //获取父级 列表
        List<MoreChildChannelVo> childList = pkChannelMapper.moreChildList();////获取 父级和 子级列表
        if (StrUtils.isNotEmpty(mainList) && StrUtils.isNotEmpty(childList)) {
            for (MoreChannelVo vo : mainList) {
                List<MoreChildChannelVo> newList = new ArrayList<>();
                for (MoreChildChannelVo cVo : childList){
                    if(cVo.getPId().equals(vo.getChanId())){
                        newList.add(cVo);
                    }
                }
                if(StrUtils.isNotEmpty(newList)){
                    vo.setChildList(newList);
                }
            }
        }
        return mainList;
    }
}
