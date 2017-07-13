package com.chinaxiaopu.xiaopuMobi.service.topic;

import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.dto.topic.EventRankDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.UserRankDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.VotesRankDto;
import com.chinaxiaopu.xiaopuMobi.mapper.EventRankingMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.TopicRankingMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.UserRankingMapper;
import com.chinaxiaopu.xiaopuMobi.vo.topic.EventRankVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.UserRankVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.VotesRankVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 活动 日周月排行榜接口
 * Created by Administrator on 2016/11/2.
 */
@Slf4j
@Service
public class EventRankService {
    @Autowired
    private EventRankingMapper eventRankingMapper;
    @Autowired
    private TopicRankingMapper topicRankingMapper;
    @Autowired
    private UserRankingMapper userRankingMapper;

    /**
     * 活动 日周月排行榜接口
     *
     * @param eventRankDto
     * @return
     */
    public List<EventRankVo> getEventRankList(EventRankDto eventRankDto) {
        List<EventRankVo> _list = eventRankingMapper.getEventRankList(eventRankDto);
        String filePath = null;
        try {
            filePath = SystemConfig.getInstance().getImgsDomain();//图片路径
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (EventRankVo vo : _list) {
            vo.setPath(filePath);
        }
        return _list;
    }

    /**
     * 投票排行榜日，月，周榜
     *
     * @param votesRankDto
     * @return
     */
    public List<VotesRankVo> getVotesRankList(VotesRankDto votesRankDto) {
        List<VotesRankVo> _list = topicRankingMapper.getVotesRankList(votesRankDto);
        String filePath = null;
        try {
            filePath = SystemConfig.getInstance().getImgsDomain();//图片路径
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (VotesRankVo vo : _list) {
            vo.setPath(filePath);
            vo.setContentMap(vo.getContent().toString());
        }
        return _list;
    }

    /**
     * 不服榜 /达人榜
     *
     * @param userRankDto
     * @return
     */
    public List<UserRankVo> getUserRankList(UserRankDto userRankDto) {
        List<UserRankVo> _list =userRankingMapper.getUserRankList(userRankDto);
        String filePath = null;
        try {
            filePath = SystemConfig.getInstance().getImgsDomain();//图片路径
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (UserRankVo vo : _list) {
            vo.setPath(filePath);
        }
        return _list;
    }

    /**
     * 定时器  ： 活动  日排行 每隔一小时执行一次
     */
    @Transactional
    public void eventDay() {
        /**
         * 流程 ： 活动下的主题数量 排行 查询 ；查询老的数据修改状态 status 改为0  ；删除前天的数据
         */
        //1:查询数据 取前20条
        List<EventRankVo> newList = eventRankingMapper.getEventDayList();

        if (!newList.isEmpty()) {
            //2:修改状态 1改为 0
            eventRankingMapper.updateOldEventDay();
            //3：插入数据
            eventRankingMapper.insertNewEventDay(newList);
        }
        //4:删除前天的数据
        eventRankingMapper.deleteOldEventDay();
    }

    /**
     * 定时器  ： 活动  周排行排行 周一 凌晨执行
     */
    @Transactional
    public void eventWeek() {
        /**
         * 流程 ： 活动下的主题数量 排行 查询 ；查询老的数据修改状态 status 改为0  ；删除上周的数据
         */
        //1:查询数据 取前20条
        List<EventRankVo> newList = eventRankingMapper.getEventWeekList();

        if (!newList.isEmpty()) {
            //2:修改状态 1改为 0
            eventRankingMapper.updateOldEventWeek();
            //3：插入数据
            eventRankingMapper.insertNewEventWeek(newList);
        }
        //4:删除上周的数据
        eventRankingMapper.deleteOldEventWeek();
    }

    /**
     * 定时器  ： 活动  月排行排行 1号 凌晨执行
     */
    @Transactional
    public void eventMonth() {
        /**
         * 流程 ： 活动下的主题数量 排行 查询 ；查询老的数据修改状态 status 改为0  ；删除上月的数据
         */
        //1:查询数据 取前20条
        List<EventRankVo> newList = eventRankingMapper.getEventMonthList();

        if (!newList.isEmpty()) {
            //2:修改状态 1改为 0
            eventRankingMapper.updateOldEventMonth();
            //3：插入数据
            eventRankingMapper.insertNewEventMonth(newList);
        }
        //4:删除上月的数据
        eventRankingMapper.deleteOldEventMonth();
    }

    /**
     * 定时器 ：投票 日排行榜
     */
    @Transactional
    public void votesDay() {
        /**
         * 流程 ： 投票下的主题数量 排行 查询 ；查询老的数据修改状态 status 改为0  ；删除前天的数据
         */
        //1:查询数据 取前20条
        List<VotesRankVo> newList = topicRankingMapper.getVotesDayList();

        if (!newList.isEmpty()) {
            //2:修改状态 1改为 0
            topicRankingMapper.updateOldVotesDay();
            //3：插入数据
            topicRankingMapper.insertNewVotesDay(newList);
        }
        //4:删除前天的数据
        topicRankingMapper.deleteOldVotesDay();
    }

    /**
     * 定时器 ：投票排行榜 月榜
     */
    @Transactional
    public void votesMonth() {
        /**
         * 流程 ： 活动下的主题数量 排行 查询 ；查询老的数据修改状态 status 改为0  ；删除上月的数据
         */
        //1:查询数据 取前20条
        List<VotesRankVo> newList = topicRankingMapper.getVotesMonthList();

        if (!newList.isEmpty()) {
            //2:修改状态 1改为 0
            topicRankingMapper.updateOldVotesMonth();
            //3：插入数据
            topicRankingMapper.insertNewVotesMonth(newList);
        }
        //4:删除上月的数据
        topicRankingMapper.deleteOldVotesMonth();
    }

    /**
     * 定时器 ：投票排行榜 周榜
     */
    @Transactional
    public void votesWeek() {
        /**
         * 流程 ： 活动下的主题数量 排行 查询 ；查询老的数据修改状态 status 改为0  ；删除上周的数据
         */
        //1:查询数据 取前20条
        List<VotesRankVo> newList = topicRankingMapper.getVotesWeekList();

        if (!newList.isEmpty()) {
            //2:修改状态 1改为 0
            topicRankingMapper.updateOldVotesWeek();
            //3：插入数据
            topicRankingMapper.insertNewVotesWeek(newList);
        }
        //4:删除上周的数据
        topicRankingMapper.deleteOldVotesWeek();
    }

    /**
     * 定时器 ： 不服榜
     */
    @Transactional
    public void userBufu() {
        /**
         * 流程 ： 不服榜 排行 查询 ；查询老的数据修改状态 status 改为0  ；删除前天的数据
         */
        //1:查询数据 取前20条
        List<UserRankVo> newList = userRankingMapper.getUserBufuList();

        if (!newList.isEmpty()) {
            //2:修改状态 1改为 0
            userRankingMapper.updateUserBu();
            //3：插入数据
            userRankingMapper.insertUserBu(newList);
        }
        //4:删除前天的数据
        userRankingMapper.deleteUserBu();
    }

    /**
     * 定时器 ： 达人榜
     */
    @Transactional
    public void userDaren() {
        /**
         * 流程 ： 达人榜数量 排行 查询 ；查询老的数据修改状态 status 改为0  ；删除前天的数据
         */
        //1:查询数据 取前20条
        List<UserRankVo> newList = userRankingMapper.getUserDarenList();

        if (!newList.isEmpty()) {
            //2:修改状态 1改为 0
            userRankingMapper.updateUserDaren();
            //3：插入数据
            userRankingMapper.insertUserDaren(newList);
        }
        //4:删除前天的数据
        userRankingMapper.deleteUserDaren();
    }

    /**
     * 定时器 ： 投票汇总
     */
    @Transactional
    public void VotesPool() {
        /**
         * 流程 ： 投票汇总 排行 查询 ；查询老的数据修改状态 status 改为0  ；删除前天的数据
         */
        //1:查询数据
        List<VotesRankVo> newList = topicRankingMapper.getVotesPoolList();

        if (!newList.isEmpty()) {
            //2:修改状态 1改为 0
            topicRankingMapper.updateVotesPool();
            //3：插入数据
            topicRankingMapper.insertVotesPool(newList);
        }
        //4:删除前天的数据
        topicRankingMapper.deleteVotesPool();
    }
}
