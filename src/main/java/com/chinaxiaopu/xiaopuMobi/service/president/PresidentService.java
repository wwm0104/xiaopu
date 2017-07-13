package com.chinaxiaopu.xiaopuMobi.service.president;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.*;
import com.chinaxiaopu.xiaopuMobi.mapper.*;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.chinaxiaopu.xiaopuMobi.service.MsgPushService;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.EventMemberVo;
import com.chinaxiaopu.xiaopuMobi.vo.GroupMemberVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Wang on 2016/10/14.
 */
@Service
public class PresidentService {

    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private EventMemberMapper eventMemberMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private GroupMemberMapper groupMemberMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private UserTicketMapper userTicketMapper;
    @Autowired
    private MsgPushService msgPushService;//消息推送

    /**
     * 获取待审核活动人员数量
     *
     * @param userId
     * @param eventId
     * @return
     */
    public int getUntreatedEventApplyCount(Integer userId, Integer eventId) {
        return eventMemberMapper.getApplyCount(userId, eventId, 2);
    }

    /**
     * 获取总的活动人员数量，或某个活动的总的人员数量
     *
     * @param userId
     * @param eventId
     * @return
     */
    public int getEventApplyCount(Integer userId, Integer eventId) {
        return eventMemberMapper.getApplyCount(userId, eventId, null);
    }

    /**
     * 获取待审核社团人员数量
     *
     * @param userId
     * @return
     */
    public int getUntreatedGroupApplyCount(Integer userId, Integer groupId) {
        return groupMemberMapper.getApplyCount(userId, groupId, 2);
    }

    /**
     * 获取总的社团人员数量
     *
     * @param userId
     * @return
     */
    public int getGroupApplyCount(Integer userId, Integer groupId) {
        return groupMemberMapper.getApplyCount(userId, groupId, null);
    }

    /**
     * 获取发布活动的列表信息
     *
     * @param groupEventListDto
     * @return
     */
    public PageInfo<Event> getGroupEventList(GroupEventListDto groupEventListDto) {
        Event event = new Event();
        //关键字不为空
        if (!StringUtils.isEmpty(groupEventListDto.getKeyword())) {
            event.setOrganizeName(groupEventListDto.getKeyword());
            event.setSubject(groupEventListDto.getKeyword());
        }
        //状态码不为空
        if (!StringUtils.isEmpty(groupEventListDto.getStatus())) {
            event.setStatus(groupEventListDto.getStatus());
        }

        //分页
        if (!StringUtils.isEmpty(groupEventListDto.getPage()) && !StringUtils.isEmpty(groupEventListDto.getRows())) {
            PageHelper.startPage(groupEventListDto.getPage(), groupEventListDto.getRows());
        }
        List<Event> eventList = eventMapper.getGroupEventList(event, groupEventListDto.getTimePoint(), groupEventListDto.getUserId());
        PageInfo<Event> pageInfo = new PageInfo<>(eventList);

        return pageInfo;
    }

    /**
     * 获取活动人员列表
     *
     * @param eventMemberDto
     * @return
     */
    public PageInfo<EventMember> eventMemberlist(EventMemberDto eventMemberDto) {
        EventMember eventMember = new EventMember();

        if (!StringUtils.isEmpty(eventMemberDto.getEventId())) {
            eventMember.setEventId(eventMemberDto.getEventId());
        }
        if (!StringUtils.isEmpty(eventMemberDto.getStatus())) {
            eventMember.setStatus(eventMemberDto.getStatus());
        }
        String keyword = eventMemberDto.getKeyword();
        if (StringUtils.isEmpty(keyword)) {
            keyword = null;
        }

        //分页
        if (!StringUtils.isEmpty(eventMemberDto.getPage()) && !StringUtils.isEmpty(eventMemberDto.getRows())) {
            PageHelper.startPage(eventMemberDto.getPage(), eventMemberDto.getRows());
        }
        List<EventMember> eventMemberList = eventMemberMapper.selectByEventMemberAndKeyword(eventMember, keyword, eventMemberDto.getUserId());
        PageInfo<EventMember> pageInfo = new PageInfo<>(eventMemberList);

        return pageInfo;
    }

    /**
     * 获取社团人员列表
     *
     * @param groupMemberDto
     * @return
     */
    public PageInfo<GroupMember> groupMemberlist(GroupMemberDto groupMemberDto) {
        GroupMember groupMember = new GroupMember();
        if (!StringUtils.isEmpty(groupMemberDto.getGroupId())) {
            groupMember.setGroupId(groupMemberDto.getGroupId());
        }
        if (!StringUtils.isEmpty(groupMemberDto.getStatus())) {
            groupMember.setStatus(groupMemberDto.getStatus());
        }
        String keyword = groupMemberDto.getKeyword();
        if (StringUtils.isEmpty(keyword)) {
            keyword = null;
        }

        //分页
        if (!StringUtils.isEmpty(groupMemberDto.getPage()) && !StringUtils.isEmpty(groupMemberDto.getRows())) {
            PageHelper.startPage(groupMemberDto.getPage(), groupMemberDto.getRows());
        }
        List<GroupMember> groupMemberList = groupMemberMapper.selectByGroupMemberAndKeyword(groupMember, keyword, groupMemberDto.getUserId());
        PageInfo<GroupMember> pageInfo = new PageInfo<>(groupMemberList);

        return pageInfo;
    }

    /**
     * 获取管理的社团列表
     *
     * @param groupMemberDto
     * @return
     */
    public List<Group> manageGroupList(GroupMemberDto groupMemberDto) {
        Group group = new Group();
        group.setPresidentId(groupMemberDto.getUserId());
        group.setStatus(groupMemberDto.getStatus());
        return groupMapper.getGroupListByGroup(group);

    }

    /**
     * 获取发布活动列表
     *
     * @param eventMemberDto
     * @return
     */
    public List<Event> manageEventList(EventMemberDto eventMemberDto) {
        Event event = new Event();
        event.setStatus(eventMemberDto.getStatus());
        return eventMapper.getGroupEventList(event, null, eventMemberDto.getUserId());
    }

    /**
     * 审核申请加入活动人员
     *
     * @param eventMemberConfirmDto
     * @return
     */
    @Transactional
    public int confirmEventMember(EventMemberConfirmDto eventMemberConfirmDto) {
        Integer eventId = eventMemberConfirmDto.getEventId();
        Integer memberId = eventMemberConfirmDto.getMemberId();
        Integer isJoin = 1;
        EventMember eventMember = eventMemberMapper.selectByEventMember(eventId, memberId, isJoin, 2);
        Event event = eventMapper.selectByPrimaryKey(eventMember.getEventId());

        //若该活动需要门票，审核通过时，插入一条门票数据（需判断是否还有门票）
        Ticket ticket = ticketMapper.selectByBusinessIdAndType(eventId, 2);
        int eventMemberCount;
        //封装消息体
        int userId = memberId;
        Set<Integer> users = new HashSet<>();
        users.add(userId);
        int sender = eventMemberConfirmDto.getUserId();
        MsgFromDto msgFromDto = new MsgFromDto();
        msgFromDto.setEventId(eventMemberConfirmDto.getEventId());
        msgFromDto.setEventName(eventMember.getEventName());
        msgFromDto.setRightImgUrl(event.getPosterImg());

        //修改活动人员的状态，当状态为3(驳回) 或 需要门票但是已经没有了
        if (eventMemberConfirmDto.getStatus() == 3) {
            eventMember.setStatus(3);
            eventMember.setMemo(eventMemberConfirmDto.getMemo());
            eventMemberCount = eventMemberMapper.confirmEventMember(eventMember);
            if (eventMemberCount == 1) {
                //消息推送--申请加入活动审核：驳回
                int action = Constants.EVENT_JOIN_AUDIT_NO;
                msgFromDto.setRemark(eventMemberConfirmDto.getMemo());
                msgPushService.sendPushMsgByUser(action,users,sender,msgFromDto);

                return Result.SUCCESS;
            }
        } else if (!StringUtils.isEmpty(ticket) && ticket.getRemainingCnt() == 0) {
            return Constants.TICKET_NO;
        } else {
            eventMember.setStatus(eventMemberConfirmDto.getStatus());
            eventMemberCount = eventMemberMapper.confirmEventMember(eventMember);
            if (eventMemberCount == 1) {
                //审核通过，活动人数加1

                event.setJoinCnt(event.getJoinCnt() + 1);
                int eventCount = eventMapper.updateByPrimaryKeySelective(event);
                //实名认证
                int realNameCount = realNameAuthentication(eventMember.getMemberId());
                if (eventCount != 1 || realNameCount != 1) {
                    return Result.FAILURE;
                }
                //若该活动为仅社团人员参加，审核通过时，插入一条社团人员数据
                if (event.getJoinType() == 1) {
                    int groupMemberCount = insertGroupMember(event, eventMember);
                    if (groupMemberCount != 1) {
                        return Result.FAILURE;
                    }
                }
                //还有门票，添加门票信息到user_ticket表中，并将门票剩余数-1
                if (!StringUtils.isEmpty(ticket) && ticket.getRemainingCnt() > 0) {
                    UserTicket userTicket = new UserTicket();
                    userTicket.setUserId(memberId);
                    userTicket.setTicketId(ticket.getId());
                    userTicket.setBusinessId(ticket.getBusinessId());
                    userTicket.setBusinessType(ticket.getBusinessType());
                    Date date = new Date();
                    userTicket.setCreateTime(date);
                    userTicket.setUpdateTime(date);
                    userTicket.setStatus(0);
                    int userTicketCount = userTicketMapper.insert(userTicket);
                    ticket.setRemainingCnt(ticket.getRemainingCnt() - 1);
                    int ticketCount = ticketMapper.updateByPrimaryKeySelective(ticket);
                    if (userTicketCount != 1 || ticketCount != 1) {
                        return Result.FAILURE;
                    }
                }
                //消息推送--申请加入活动审核：通过
                int action = Constants.EVENT_JOIN_AUDIT_OK;
                msgPushService.sendPushMsgByUser(action,users,sender,msgFromDto);
            }
        }
        return Result.SUCCESS;
    }

    /**
     * 审核申请加入社团人员
     *
     * @param groupMemberConfirmDto
     * @return
     */
    @Transactional
    public int confirmGroupMember(GroupMemberConfirmDto groupMemberConfirmDto) {
        Integer groupId = groupMemberConfirmDto.getGroupId();
        Integer memberId = groupMemberConfirmDto.getMemberId();
        GroupMember groupMember = groupMemberMapper.selectByGroupIdAndMemberId(groupId, memberId);
        if(StringUtils.isEmpty(groupMember)){
            return Result.FAILURE;
        }
        //修改社团人员状态
        groupMember.setStatus(groupMemberConfirmDto.getStatus());
        groupMember.setMemo(groupMemberConfirmDto.getMemo());
        int groupMemberCount = groupMemberMapper.confirmGroupMember(groupMember);
        if (groupMemberCount != 1) {
            return Result.FAILURE;
        }
        //审核通过，社团人数加1
        Group group = groupMapper.selectByPrimaryKey(groupMember.getGroupId());
        //封装消息体
        int userId = memberId;
        Set<Integer> users = new HashSet<>();
        users.add(userId);
        int sender = groupMemberConfirmDto.getUserId();
        MsgFromDto msgFromDto = new MsgFromDto();
        msgFromDto.setGroupId(group.getId());
        msgFromDto.setGroupName(group.getName());
        msgFromDto.setRightImgUrl(group.getLogoImgUrl());

        if (groupMember.getStatus() == 1) {
            //修改社团人数
            group.setCnt(group.getCnt() + 1);
            int groupCount = groupMapper.updateByPrimaryKeySelective(group);
            //实名认证
            int realNameCount = realNameAuthentication(groupMember.getMemberId());
            if (groupCount != 1 || realNameCount != 1) {
                return Result.FAILURE;
            }
            //消息推送--加入社团审核：通过
            int action = Constants.GROUP_JOIN_AUDIT_OK;
            msgPushService.sendPushMsgByUser(action,users,sender,msgFromDto);
        } else {
            //消息推送--加入社团审核：驳回
            int action = Constants.GROUP_JOIN_AUDIT_NO;
            msgFromDto.setRemark(groupMemberConfirmDto.getMemo());
            msgPushService.sendPushMsgByUser(action,users,sender,msgFromDto);
        }
        return Result.SUCCESS;
    }

    /**
     * 实名认证
     *
     * @param userId
     * @return
     */
    public int realNameAuthentication(Integer userId) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if (userInfo.getValid() == 1) {
            return Result.SUCCESS;
        } else {
            userInfo.setValid(1);
            int count = userInfoMapper.updateByPrimaryKeySelective(userInfo);
            return count;
        }
    }

    /**
     * 审核通过加入活动时，插入一条加入社团数据
     */
    public int insertGroupMember(Event event, EventMember eventMember) {
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(event.getOrganizeId());
        groupMember.setMemberId(eventMember.getMemberId());
        groupMember.setStatus(1);
        groupMember = groupMemberMapper.selectByGroupMemberAndStatus(groupMember);

        //判断是否为该社团人员
        if (!StringUtils.isEmpty(groupMember)) {
            return Result.SUCCESS;
        }
        //是否以前已经申请过加入该社团
        groupMember = groupMemberMapper.selectStatusByGroupMemberAndStatus(event.getOrganizeId(), eventMember.getMemberId());
        int count;
        if (StringUtils.isEmpty(groupMember)) {
            //若不是，插入一条数据
            groupMember = new GroupMember();
            groupMember.setGroupId(event.getOrganizeId());
            groupMember.setMemberId(eventMember.getMemberId());
            groupMember.setStatus(1);
            groupMember.setJoinTime(new Date());

            count = groupMemberMapper.insertSelective(groupMember);
        } else {
            //若是，修改那条数据
            groupMember.setStatus(1);
            groupMember.setJoinTime(new Date());
            groupMember.setMemo(null);

            count = groupMemberMapper.updateStatusByGroupMember(groupMember);
        }
        if(count==1){
            Group group = groupMapper.selectByPrimaryKey(event.getOrganizeId());
            group.setCnt(group.getCnt() + 1);
            groupMapper.updateByPrimaryKeySelective(group);
        }

        return count;
    }

    public PageInfo<GroupMemberVo> getGroupList(ContextDto contextDto, UserInfo userInfo) {
        //分页
        if (!StringUtils.isEmpty(contextDto.getPage()) && !StringUtils.isEmpty(contextDto.getRows())) {
            PageHelper.startPage(contextDto.getPage(), contextDto.getRows());
        }
        List<GroupMemberVo> list = groupMapper.getGroupListByUserId(userInfo.getUserId());
        PageInfo<GroupMemberVo> pageInfo = new PageInfo<>(list);
        for (GroupMemberVo groupMemberVo : list) {
            int applyCount = getUntreatedGroupApplyCount(userInfo.getUserId(), groupMemberVo.getGroupId());
            groupMemberVo.setApplyCnt(applyCount);
        }
        pageInfo.setList(list);
        return pageInfo;
    }

    public PageInfo<EventMemberVo> getEventList(ContextDto contextDto, UserInfo userInfo) {
        //分页
        if (!StringUtils.isEmpty(contextDto.getPage()) && !StringUtils.isEmpty(contextDto.getRows())) {
            PageHelper.startPage(contextDto.getPage(), contextDto.getRows());
        }
        List<EventMemberVo> list = eventMapper.getEventListByUserId(userInfo.getUserId());
        PageInfo<EventMemberVo> pageInfo = new PageInfo<>(list);
        for (EventMemberVo eventMemberVo : list) {
            int applyCount = getUntreatedEventApplyCount(userInfo.getUserId(), eventMemberVo.getEventId());
            eventMemberVo.setApplyCnt(applyCount);
        }
        pageInfo.setList(list);
        return pageInfo;
    }

    /**
     * 批量审核社团活动
     *
     * @param eventMemberConfirmAllDto
     * @return
     */
    public int confirmEventMemberAll(EventMemberConfirmAllDto eventMemberConfirmAllDto) {
        int row = 0;
        TicketExample example = new TicketExample();
        example.createCriteria().andBusinessIdEqualTo(eventMemberConfirmAllDto.getEventId()).andBusinessTypeEqualTo(2);
        List<Ticket> ticketList = ticketMapper.selectByExample(example);
        if (StrUtils.isNotEmpty(ticketList)) {
            int remainCnt = (int) ticketList.get(0).getRemainingCnt();
            if (remainCnt < eventMemberConfirmAllDto.getMemberIds().length) {
                return 47; //电子门票不足
            }
        }
        EventMemberConfirmDto eventMemberConfirmDto = new EventMemberConfirmDto();
        eventMemberConfirmDto.setEventId(eventMemberConfirmAllDto.getEventId());
        eventMemberConfirmDto.setStatus(eventMemberConfirmAllDto.getStatus());
        eventMemberConfirmDto.setUserId(eventMemberConfirmAllDto.getUserId());
        for (Integer memberId : eventMemberConfirmAllDto.getMemberIds()) {
            eventMemberConfirmDto.setMemberId(memberId);
            row = this.confirmEventMember(eventMemberConfirmDto);
        }
        return row;
    }

    /**
     * 查询剩余门票数量
     *
     * @param eventId
     * @return
     */
    public Integer selectRemainTicketNum(Integer eventId) {
        Integer remainCnt=0;
        TicketExample example = new TicketExample();
        example.createCriteria().andBusinessIdEqualTo(eventId).andBusinessTypeEqualTo(2);
        List<Ticket> ticketList = ticketMapper.selectByExample(example);
        if (StrUtils.isNotEmpty(ticketList)) {
            remainCnt = ticketList.get(0).getRemainingCnt();

        }
        return remainCnt;
    }
}