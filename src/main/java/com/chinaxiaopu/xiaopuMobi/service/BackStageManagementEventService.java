package com.chinaxiaopu.xiaopuMobi.service;

import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.*;
import com.chinaxiaopu.xiaopuMobi.mapper.*;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class BackStageManagementEventService {
	@Autowired
	private EventGroupMapper eventGroupMapper;
	@Autowired
	private EventMapper eventMapper;
	@Autowired
	private ReviewedGroupMapper reviewedGroupMapper;
	@Autowired
	private BackStageManagementEventMapper backStageManagementEventMapper;
	@Autowired
	private TopicMapper topicMapper;
	@Autowired
	private RecommendEventMapper recommendEventMapper;
	@Autowired
	private TicketMapper ticketMapper;
	@Autowired
	private EventLotteryMapper eventLotteryMapper;
	@Autowired
	private MsgPushService msgPushService;//消息推送

	/**查询活动列表
	 * @param eventDto
	 * @return
	 */
	public PageInfo<EventDto> getEventList(EventDto eventDto){
		if (eventDto.getPage() != null && eventDto.getRows() != null) {
			PageHelper.startPage(eventDto.getPage(), eventDto.getRows());
		}
		List<EventDto> list = backStageManagementEventMapper.selectEventList(eventDto);
		for(EventDto ed:list){
			EventGroup eventGroup = new EventGroup();
			eventGroup.setEventId(ed.getId());
			//查询可参与该次活动的社团列表
			List<EventGroup>  gList = backStageManagementEventMapper.selectGroupByEventId(eventGroup);
			String str ="";
			if(gList != null){
				for(EventGroup eg:gList){
					if(eg!=null && eg.getGroupName() != null){
						str+="【"+eg.getGroupName()+"】";
					}
				}
				ed.setJoinGroup(str);
			}
		}
		PageInfo<EventDto> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public List<EventDto> selectEventList(EventDto eventDto){
		List<EventDto> list = backStageManagementEventMapper.selectEventList(eventDto);
		return  list;
	}


	/**查询活动详情
	 * @param eventDto
	 * @return
	 */
	public EventDto getEvenDetials(EventDto eventDto){
		EventDto eventDto1 = backStageManagementEventMapper.selectEventDteils(eventDto);
		GroupDto g =new GroupDto();
		EventGroup e =new EventGroup();
		e.setEventId(eventDto.getId());
		g.setPresidentId(eventDto1.getUserId());
		List<GroupDto> groupList = reviewedGroupMapper.selectReviewedGroupList(g);
		List<EventGroup> list= backStageManagementEventMapper.selectGroupByEventId(e);
		List<String> strList = new ArrayList<String>();
		for(GroupDto gr : groupList){
			strList.add(gr.getName());
		}
		//String[] arr = (String [])strList.toArray();
		String str =  org.apache.commons.lang3.StringUtils.join(strList,"、");
		if(eventDto1.getStatus() == 1){
			eventDto1.setStatusName("审核通过");
		}
		if(eventDto1.getStatus() == 0){
			eventDto1.setStatusName("待审核");
		}
		if(eventDto1.getStatus() == 0 && eventDto1.getEndTime().getTime()< new Date().getTime()){
			eventDto1.setStatusName("待审核(已失效)");
		}
		if(eventDto1.getStatus() == 2){
			eventDto1.setStatusName("审核未通过");
		}
		if(new Date().getTime() < eventDto1.getStartTime().getTime()){
			eventDto1.setTimeStatusName("未开始");
		}else if(eventDto1.getStartTime().getTime()<=new Date().getTime() && eventDto1.getEndTime().getTime()>=new Date().getTime()){
			eventDto1.setTimeStatusName("进行中");
			if(eventDto1.getStatus() == 2 || eventDto1.getStatus() == 0){
				eventDto1.setTimeStatusName("");
			}
		}else{
			eventDto1.setTimeStatusName("已结束");
			if(eventDto1.getStatus() == 2 || eventDto1.getStatus() == 0){
				eventDto1.setTimeStatusName("");
			}
		}
		eventDto1.setEventGroupList(list);
		eventDto1.setAllGroup(str);
		return eventDto1;
	}

	public void createOfficeEvent(EventDto eventDto) throws Exception{
		if(eventDto.getGroupIdList()==null || eventDto.getGroupIdList().size() == 0){
			eventDto.setJoinType(2);
		}else{
			eventDto.setJoinType(1);
		}
		eventDto.setCreatorId(0);
		eventDto.setAllowMultiGroups(1);
		eventDto.setJoinCnt(0);
		eventDto.setFollowCnt(0);
		eventDto.setStatus(1);
		Event event = new Event();
		if(event.getContentImgsArray() != null && event.getContentImgsArray().length > 0){
			event.setContentImgs(org.apache.commons.lang3.StringUtils.join(event.getContentImgs(),","));
		}
		BeanUtils.copyProperties(eventDto,event);
		event.setFurther("["+org.apache.commons.lang3.StringUtils.join(eventDto.getFurtherList(),",")+"]");
		eventMapper.insert(event);
		int insertId = event.getId();
		List<Integer> list = eventDto.getGroupIdList();
		for(Integer id : list){
			Group group =new Group();
			group.setId(id);
			Group g = backStageManagementEventMapper.selectInfoByGroupId(group);
			EventGroup eventGroup =new EventGroup();
			eventGroup.setEventId(insertId);
			eventGroup.setGroupName(g.getName());
			eventGroup.setGroupId(g.getId());
			eventGroup.setSchoolId(g.getSchoolId());
			eventGroup.setSchoolName(g.getSchoolName());
			eventGroupMapper.insert(eventGroup);
		}

		//判断是否需要门票，若是，在门票表中添加一条数据
		if(eventDto.getTicket()==1){
			Ticket ticket = new Ticket();
			ticket.setBusinessId(event.getId());
			ticket.setBusinessType(2);//活动的门票类型为2
			ticket.setTicketCnt(eventDto.getTicketCnt());
			ticket.setRemainingCnt(eventDto.getTicketCnt());
			ticket.setCreateId(event.getCreatorId());
			ticket.setCreateTime(event.getStartTime());
			ticket.setExpireTime(event.getEndTime());
			ticketMapper.insert(ticket);

            //审核通过，若是需要门票，向event_lottery插入一条数据
            insertLotteryForEventTicket(event);
		}
	}

	public GroupAndEventNum selectGroupAndEventNum(){
		GroupAndEventNum groupAndEventNum = new GroupAndEventNum();
		groupAndEventNum.setTopicNum(topicMapper.selectWaitReviewTopicCount());
		groupAndEventNum.setEventNum(backStageManagementEventMapper.selectEventCount());
		groupAndEventNum.setGroupNum(backStageManagementEventMapper.selectGroupCount());
		groupAndEventNum.setAllEventNum(backStageManagementEventMapper.selectAllEventNum());
		groupAndEventNum.setAllGroupNum(backStageManagementEventMapper.selectAllGroupNum());
		groupAndEventNum.setIndexEvent(backStageManagementEventMapper.selectIndexEvent());
		groupAndEventNum.setIndexGroup(backStageManagementEventMapper.selectIndexGroup());
		return groupAndEventNum;
	}

	//活动审核
	public int updateEventById(ReviewedEventDto event) throws Exception{
		EventDto eventDto = new EventDto();
		eventDto.setId(event.getId());
		eventDto=backStageManagementEventMapper.selectEventDteils(eventDto);
		int num = eventDto.getJoinCnt();
		if (event.getStatus() == 1) {
			event.setJoinCnt(num + 1);
		}
		int i = backStageManagementEventMapper.updateEventById(event);
		EventMember eventMember = new EventMember();
		if(eventDto != null) {
			eventMember.setMemberId(eventDto.getCreatorId());
			eventMember.setEventId(event.getId());
		}
		//消息体封装
		int userId = eventDto.getCreatorId();
		Set<Integer> users = new HashSet<>();
		users.add(userId);
		int sender = event.getUserId();
		MsgFromDto msgFromDto = new MsgFromDto();
		msgFromDto.setEventId(eventDto.getId());
		msgFromDto.setEventName(eventDto.getSubject());
		msgFromDto.setRightImgUrl(eventDto.getPosterImg());

		if (event.getStatus() == 1) {
			eventMember.setStatus(1);
			//审核通过，若是需要门票，向event_lottery插入一条数据
            insertLotteryForEventTicket(eventDto);

			//消息推送--活动审核：通过
			int action = Constants.EVENT_CREATE_AUDIT_OK;
			msgPushService.sendPushMsgByUser(action,users,sender,msgFromDto);

		} else {
			eventMember.setStatus(3);
			//消息推送--活动审核：驳回
			int action = Constants.EVENT_CREATE_AUDIT_NO;
			msgFromDto.setRemark(event.getMemo());
			msgPushService.sendPushMsgByUser(action,users,sender,msgFromDto);
		}

		backStageManagementEventMapper.updateEventMembers(eventMember);
		return i;
	}

    /**
     * 向活动抽奖表中添加一条数据
     * @param event
     */
	private void insertLotteryForEventTicket(Event event){
        Integer isTicket = ticketMapper.isNeedTicket(event.getId(), 2);
        if( isTicket == 1 ){
            EventLottery eventLottery = new EventLottery();
            eventLottery.setEventId(event.getId());
            eventLottery.setEventName(event.getSubject());
            eventLottery.setStauts(0);//未抽奖状态
            Date date = new Date();
            eventLottery.setCreateTime(date);
            eventLottery.setUpdateTime(date);
            eventLotteryMapper.insert(eventLottery);
        }
    }

	/**
	 * 获取活动人员列表
	 * @param eventMemberDto
	 * @return
	 */
	public PageInfo<EventMember> eventMemberlist(EventMemberDto eventMemberDto) {
		//分页
		if (!StringUtils.isEmpty(eventMemberDto.getPage()) && !StringUtils.isEmpty(eventMemberDto.getRows())) {
			PageHelper.startPage(eventMemberDto.getPage(), eventMemberDto.getRows());
		}
		EventMember eventMember = new EventMember();
		eventMember.setStatus(eventMemberDto.getStatus());
		eventMember.setEventId(eventMemberDto.getEventId());
		eventMember.setEventName(eventMemberDto.getKeyword());
		List<EventMember> eventMemberList = backStageManagementEventMapper.selectEventMember(eventMember);
		PageInfo<EventMember> pageInfo = new PageInfo<>(eventMemberList);
		return pageInfo;
	}

	/**
	 * 活动置顶
	 * @param eventId
	 * @return
	 */
    public Boolean top(int eventId) {
		Boolean result = false;
		RecommendEvent recommendEvent = recommendEventMapper.selectByEventId(eventId);
		int max = recommendEventMapper.selectMaxSort();
		int count;
		if(StringUtils.isEmpty(recommendEvent)){
			recommendEvent = new RecommendEvent();
			Event event = eventMapper.selectByPrimaryKey(eventId);
			recommendEvent.setEventId(event.getId());
			recommendEvent.setOrganizeId(event.getOrganizeId());
			recommendEvent.setOrganizeName(event.getOrganizeName());
			recommendEvent.setEventSubject(event.getSubject());
			recommendEvent.setPosterImg(event.getPosterImg());
			recommendEvent.setSort(max+1);
			recommendEvent.setRecommendTime(new Date());
			count = recommendEventMapper.insertSelective(recommendEvent);
		}else{
			recommendEvent.setSort(max+1);
			recommendEvent.setRecommendTime(new Date());
			count = recommendEventMapper.updateByPrimaryKeySelective(recommendEvent);
		}
		if(count==1){
			result = true;
		}
		return result;
    }
}
