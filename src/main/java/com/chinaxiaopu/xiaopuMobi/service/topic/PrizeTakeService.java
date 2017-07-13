package com.chinaxiaopu.xiaopuMobi.service.topic;

import com.chinaxiaopu.xiaopuMobi.dto.topic.PrizeTakeDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.TicketTakeDto;
import com.chinaxiaopu.xiaopuMobi.mapper.*;
import com.chinaxiaopu.xiaopuMobi.model.PkPrizeTakeLog;
import com.chinaxiaopu.xiaopuMobi.model.UserTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ycy on 2016/11/11.
 */
@Service
public class PrizeTakeService {

    @Autowired
    private PkPrizeResultMapper pkPrizeResultMapper;

    @Autowired
    private PkPrizeTakeLogMapper pkPrizeTakeLogMapper;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private AwardPresenMapper awardPresenMapper;

    @Autowired
    private UserTicketMapper userTicketMapper;

    @Transactional
    public int takePrize(PrizeTakeDto prizeTakeDto) throws Exception {
//        int userId =0;
//        if(prizeTakeDto.getTopicId() != null) {
//            userId = topicMapper.selectUserIdByTopicId(prizeTakeDto.getTopicId());
//            prizeTakeDto.setRewardUserId(userId);
//        }
        prizeTakeDto.setTakeTime(new Date());
        pkPrizeResultMapper.updatePrizeTake(prizeTakeDto);
        PkPrizeTakeLog pkPrizeTakeLog = new PkPrizeTakeLog();
        pkPrizeTakeLog.setAwardUserId(prizeTakeDto.getAwardUserId());
//        if (prizeTakeDto.getRewardUserId() == null && prizeTakeDto.getTopicId() != null) {
//            pkPrizeTakeLog.setRewardUserId(userId);
//        } else {
        pkPrizeTakeLog.setRewardUserId(prizeTakeDto.getRewardUserId());
//        }
        pkPrizeTakeLog.setPkId(prizeTakeDto.getPkId());
        pkPrizeTakeLog.setTakeTime(new Date());
        pkPrizeTakeLogMapper.insert(pkPrizeTakeLog);
        awardPresenMapper.updateAwardCnt(prizeTakeDto.getAwardUserId());
        return 1;
    }

    public int checkIsTake(PrizeTakeDto prizeTakeDto) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pkId", prizeTakeDto.getPkId());
        int userId;
        if (prizeTakeDto.getRewardUserId() == null && prizeTakeDto.getTopicId() != null) {
            userId = topicMapper.selectUserIdByTopicId(prizeTakeDto.getTopicId());
        } else {
            userId = prizeTakeDto.getRewardUserId();
        }
        map.put("userId", userId);
        return pkPrizeResultMapper.checkIsTake(map);
    }

    public UserTicket selectUserTicket(TicketTakeDto ticketTakeDto){
        UserTicket userTicket = new UserTicket();
        userTicket.setTicketId(ticketTakeDto.getTicketId());
        userTicket.setBusinessType(ticketTakeDto.getBusinessType());
        userTicket.setBusinessId(ticketTakeDto.getBusinessId());
        userTicket.setUserId(ticketTakeDto.getTicketUserId());
        return userTicketMapper.selectTicketInfo(userTicket);
    }

    public int updateStatus(TicketTakeDto ticketTakeDto){
        UserTicket userTicket = new UserTicket();
        userTicket.setTicketId(ticketTakeDto.getTicketId());
        userTicket.setBusinessType(ticketTakeDto.getBusinessType());
        userTicket.setBusinessId(ticketTakeDto.getBusinessId());
        userTicket.setUserId(ticketTakeDto.getTicketUserId());
        return userTicketMapper.updateStatusByid(userTicket);
    }
}
