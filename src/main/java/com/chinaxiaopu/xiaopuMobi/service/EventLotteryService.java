package com.chinaxiaopu.xiaopuMobi.service;

import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import com.chinaxiaopu.xiaopuMobi.dto.eventLottery.EventLotteryLoadInfoDto;
import com.chinaxiaopu.xiaopuMobi.mapper.EventLotteryMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.EventLotteryUserMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.EventMapper;
import com.chinaxiaopu.xiaopuMobi.model.Event;
import com.chinaxiaopu.xiaopuMobi.model.EventLottery;
import com.chinaxiaopu.xiaopuMobi.model.EventLotteryExample;
import com.chinaxiaopu.xiaopuMobi.model.EventLotteryUser;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.eventLottery.EventLotteryLoadInfoVo;
import com.chinaxiaopu.xiaopuMobi.vo.eventLottery.LotteryListVo;
import com.chinaxiaopu.xiaopuMobi.vo.eventLottery.LotteryUserAndRoundVo;
import com.chinaxiaopu.xiaopuMobi.vo.eventLottery.LotteryUserVo;
import com.chinaxiaopu.xiaopuMobi.vo.eventLottery.EventLotteryUserVo;
import com.chinaxiaopu.xiaopuMobi.vo.eventLottery.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hao on 2016/12/10.
 */
@Slf4j
@Service
public class EventLotteryService {
    @Autowired
    private EventLotteryMapper  eventLotteryMapper;
    @Autowired
    private EventLotteryUserMapper eventLotteryUserMapper;
    @Autowired
    private EventMapper eventMapper;

    /**
     * 进入抽奖界面初始化数据
     *
     * @param eventLotteryLoadInfoDto
     * @return
     */
    public EventLotteryLoadInfoVo loadInfo(EventLotteryLoadInfoDto eventLotteryLoadInfoDto) {
        // 查询基础数据
        EventLotteryLoadInfoVo vo = new EventLotteryLoadInfoVo();

        EventLotteryExample example = new EventLotteryExample();
        example.createCriteria().andEventIdEqualTo(eventLotteryLoadInfoDto.getEventId());
        List<EventLottery> eventLotteryList = eventLotteryMapper.selectByExample(example);  //查询活动名称
        if (StrUtils.isNotEmpty(eventLotteryList)) {
            vo.setEventName(eventLotteryList.get(0).getEventName()); //存放社团名称 (基础信息)
        }
        if (StrUtils.isEmpty(eventLotteryLoadInfoDto.getRound())) { //如果轮数为空  --->查询最大轮数
            EventLotteryUser elUser = eventLotteryUserMapper.selectEventLotteryUserByEventId(eventLotteryLoadInfoDto.getEventId());  //查询最大轮数
            if (elUser != null) { //存在轮数
                vo.setRound(elUser.getRound());
                eventLotteryLoadInfoDto.setRound(elUser.getRound());
            } else { //不存在轮数
                vo.setRound(1);
                eventLotteryLoadInfoDto.setRound(1);
            }
        } else {
            vo.setRound(eventLotteryLoadInfoDto.getRound());
        }
        //查询当前轮数的获奖用户
        List<EventLotteryUserVo> winnerUserList = selectAllWinerUser(eventLotteryLoadInfoDto.getEventId(), eventLotteryLoadInfoDto.getRound());
        vo.setWinnersList(winnerUserList); //所有的获奖人员

        //存放所有的待抽奖人员
        List<EventLotteryUserVo> userList = selectAllUserList(eventLotteryLoadInfoDto.getEventId());
        vo.setUserList(userList);

        return vo;
    }

    /**
     * 查询所有的为抽奖用户
     *
     * @param eventId
     * @return
     */
    private List<EventLotteryUserVo> selectAllUserList(Integer eventId) {
        return eventLotteryUserMapper.selectAllUserList(eventId);
    }

    /**
     * 查询所有的获奖用户
     *
     * @return
     */
    public List<EventLotteryUserVo> selectAllWinerUser(Integer eventId, Integer round) {
        return eventLotteryUserMapper.selectAllWinerUser(eventId, round);
    }

    /**
     * 查询活动是否结束
     *
     * @param eventLotteryLoadInfoDto
     * @return
     */
    public int selectEventIsOver(EventLotteryLoadInfoDto eventLotteryLoadInfoDto) {
        int row = 0;
        EventLotteryExample example = new EventLotteryExample();
        example.createCriteria().andEventIdEqualTo(eventLotteryLoadInfoDto.getEventId());
        List<EventLottery> eventLotteryList = eventLotteryMapper.selectByExample(example);
        if (StrUtils.isNotEmpty(eventLotteryList)) {
            Integer status = eventLotteryList.get(0).getStauts();
            if (status == 0) { //未抽奖  101
                row = 101;
            } else if (status == 1) { //已结束抽奖  102
                row = 102;
            }
        }else{
            row=-1;
        }
        return row;
    }

    /**
     * 查询活动抽奖结果
     * @param id
     * @return
     */
    public LotteryListVo getUserListByEventId(Integer id)
    {
        LotteryListVo lotteryListVo=new LotteryListVo();
        List<LotteryUserAndRoundVo> lotteryUserAndRoundVos=new ArrayList<>();
        //1，查询活动信息
        Event event=eventMapper.selectByPrimaryKey(id);
        if(!StringUtils.isEmpty(event)){
            lotteryListVo.setEventId(event.getId());
            lotteryListVo.setEventName(event.getSubject());
        }
        //2，查询轮次
        List<Integer> rounds=eventLotteryUserMapper.selectRoundByEventId(id);
        for (int i=0;i<rounds.size();i++){
            LotteryUserAndRoundVo userAndRound=new LotteryUserAndRoundVo();
            userAndRound.setRound(rounds.get(i));
            //3,获取对应轮次获奖用户集
            List<LotteryUserVo> userList=eventLotteryUserMapper.selectUserByEventIdAndRound(id,rounds.get(i));
            userAndRound.setUserList(userList.toArray());
            lotteryUserAndRoundVos.add(userAndRound);
        }
        lotteryListVo.setRoundList(lotteryUserAndRoundVos.toArray());
        return lotteryListVo;
    }

    /**
     * 抽奖
     *
     * @param eventLotteryLoadInfoDto
     * @return
     */
    @Transactional
    public EventLotteryLoadInfoVo lotteryPrize(EventLotteryLoadInfoDto eventLotteryLoadInfoDto) {
        Integer round = eventLotteryLoadInfoDto.getRound();
        Integer eventId = eventLotteryLoadInfoDto.getEventId();
        // 查询基础数据
        EventLotteryUser user = new EventLotteryUser();
        EventLotteryLoadInfoVo vo = new EventLotteryLoadInfoVo();
        vo.setRound(round);
        EventLotteryExample example = new EventLotteryExample();
        example.createCriteria().andEventIdEqualTo(eventId);
        List<EventLottery> eventLotteryList = eventLotteryMapper.selectByExample(example);  //查询活动名称
        if (StrUtils.isNotEmpty(eventLotteryList)) {
            vo.setEventName(eventLotteryList.get(0).getEventName()); //存放社团名称 (基础信息)
            user.setEventName(eventLotteryList.get(0).getEventName());
            user.setLotteryId(eventLotteryList.get(0).getId());
        }
        //所有的待抽奖人员
        List<EventLotteryUserVo> allUserList = selectAllUserList(eventId);
        //抽奖
        if(StrUtils.isNotEmpty(allUserList)){
            List<EventLotteryUserVo> winnerUserList = lottery(allUserList);
            vo.setWinnersList(winnerUserList);
            //并添加到数据库
            user.setRound(round);
            user.setEventId(eventId);
            user.setUserId(winnerUserList.get(0).getUserId());
            eventLotteryUserMapper.insertSelective(user);
        }
        //排除抽到的用户  存放到所有待抽奖人员表
        if(StrUtils.isNotEmpty(allUserList)){
            for(int i=0;i<allUserList.size();i++){
                EventLotteryUserVo  value = allUserList.get(i);
               if(user.getUserId().equals(value.getUserId())){
                   allUserList.remove(value);
                   i--;
               }
            }
        }
         vo.setUserList(allUserList);
        return vo;
    }

    /**
     * 抽奖
     *
     * @param allUserList
     * @return
     */
    private List<EventLotteryUserVo> lottery(List<EventLotteryUserVo> allUserList) {
        List<EventLotteryUserVo> winner = new ArrayList<>();
        Random rand = new Random();
        EventLotteryUserVo e =allUserList.get(rand.nextInt(allUserList.size()));
        winner.add(e);
        return winner;
    }

    /**
     * 结束抽奖
     * @param eventLotteryLoadInfoDto
     * @return
     */
    public int lotteryOver(EventLotteryLoadInfoDto eventLotteryLoadInfoDto) {
        EventLottery eventLottery = new EventLottery();
        eventLottery.setStauts(1);
        EventLotteryExample eventLotteryExample = new EventLotteryExample();
        eventLotteryExample.createCriteria().andEventIdEqualTo(eventLotteryLoadInfoDto.getEventId());
        return eventLotteryMapper.updateByExampleSelective(eventLottery,eventLotteryExample);
    }
    public PageInfo<EventLotteryVo> getEventLotteryList(ContextDto contextDto) {
        if (!StringUtils.isEmpty(contextDto.getPage()) && !StringUtils.isEmpty(contextDto.getRows())) {
            PageHelper.startPage(contextDto.getPage(), contextDto.getRows());
        }
        List<EventLotteryVo> eventLotteryVoList = eventLotteryMapper.getEventLotteryList();
        PageInfo<EventLotteryVo> pageInfo = new PageInfo<>(eventLotteryVoList);
        return pageInfo;
    }
}
