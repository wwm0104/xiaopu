package com.chinaxiaopu.xiaopuMobi.service;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.EventListDto;
import com.chinaxiaopu.xiaopuMobi.dto.EventPublishDto;
import com.chinaxiaopu.xiaopuMobi.dto.EventRegisterDto;
import com.chinaxiaopu.xiaopuMobi.dto.EventUIDto;
import com.chinaxiaopu.xiaopuMobi.dto.partner.PartnerEventDto;
import com.chinaxiaopu.xiaopuMobi.mapper.*;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.chinaxiaopu.xiaopuMobi.vo.UserInfoVo;
import com.chinaxiaopu.xiaopuMobi.vo.president.EventVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.EventPhotoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Wang
 *
 */
@Slf4j
@Service
public class EventService extends AbstractService{

	@Autowired
	private LoginService loginService;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private EventMemberMapper eventMemberMapper;
    @Autowired
    private EventGroupMapper eventGroupMapper;
    @Autowired
    private GroupMemberMapper groupMemberMapper;
    @Autowired
    private RecommendEventMapper recommendEventMapper;
    @Autowired
    private TicketMapper ticketMapper;
	@Autowired
	private MsgPushService msgPushService;//消息推送

    /**
     * EventList
     * @param eventListDto
     * @return
     */
	public List<Event> getEventListByEvent(EventListDto eventListDto) {
		Event event = new Event();
		event.setSubject(eventListDto.getEventName());

		if (!StringUtils.isEmpty(eventListDto.getPage()) && !StringUtils.isEmpty(eventListDto.getRows())) {
			PageHelper.startPage(eventListDto.getPage(), eventListDto.getRows());
		}

		List<Event> eventList;
        if(StringUtils.isEmpty(eventListDto.getSchoolId())){
			eventList = eventMapper.getEventListByEvent(event);
		}else{
			eventList = eventMapper.getEventListByEventAndSchoolId(event, eventListDto.getSchoolId());
		}

		return eventList;
	}

	/**
	 * EventInfo
	 * @param id
	 * @return
	 */
	public Event getEventInfoByEvent(Integer id) {
		Event event = eventMapper.selectByPrimaryKey(id);

		return event;
	}

	/**
	 * EventPublish
	 * @param eventPublishDto
	 * @return
	 */
	@Transactional
	public Result<Event> eventPublish(EventPublishDto eventPublishDto) {
		Result result = new Result();
		Event event = new Event();

		if(StringUtils.isEmpty(eventPublishDto.getSubject())){
			result.setMsg("活动主题不能为空");
			result.setResultCode(Result.FAILURE);
			return result;
		}

		event.setCreatorId(eventPublishDto.getCreatorId());
		event.setOrganizeId(eventPublishDto.getOrganizeId());
		event.setOrganizeName(eventPublishDto.getOrganizeName());
		event.setSubject(eventPublishDto.getSubject());
		event.setStartTime(eventPublishDto.getStartTime());
		event.setEndTime(eventPublishDto.getEndTime());
		event.setAddress(eventPublishDto.getAddress());
		event.setJoinType(eventPublishDto.getJoinType());
		event.setDescription(eventPublishDto.getDescription());
		event.setStatus(0);
		event.setAllowMultiGroups(0);
		event.setJoinCnt(0);
		event.setFollowCnt(0);
		event.setCreateTime(new Date());
		event.setSmallPosterImg(eventPublishDto.getSmallPosterImg());
		event.setPosterImg(eventPublishDto.getPosterImg());
		event.setContentImgs(org.apache.commons.lang3.StringUtils.join(eventPublishDto.getContentImgs(),","));
		event.setFurther(eventPublishDto.getFurther());
		int count = eventMapper.insert(event);
		if(count==1){
			//将活动和社团进行关联
			EventGroup eventGroup = new EventGroup();
			eventGroup.setEventId(event.getId());
			eventGroup.setGroupId(eventPublishDto.getOrganizeId());
			eventGroup.setGroupName(eventPublishDto.getOrganizeName());
			eventGroup.setSchoolId(eventPublishDto.getSchoolId());
			eventGroup.setSchoolName(eventPublishDto.getSchoolName());
			int eventGroupCount =  eventGroupMapper.insert(eventGroup);
			if(eventGroupCount!=1){
				result.setMsg("活动关联社团数据插入失败");
				result.setResultCode(Result.FAILURE);
				return result;
			}

			//将社长加入到活动中
			EventMember eventMember = new EventMember();
			eventMember.setEventId(event.getId());
			eventMember.setEventName(event.getSubject());
			eventMember.setGroupId(event.getOrganizeId());
			eventMember.setMemberId(event.getCreatorId());
			eventMember.setStatus(2);
			eventMember.setIsJoin(1);
			eventMember.setJoinTime(new Date());
			int eventMemberCount = eventMemberMapper.insertSelective(eventMember);
			if(eventMemberCount!=1){
				result.setMsg("活动人员关联数据插入失败");
				result.setResultCode(Result.FAILURE);
				return result;
			}

			//判断是否需要门票，若是，在门票表中添加一条数据
			if(!StringUtils.isEmpty(eventPublishDto.getTicket()) && eventPublishDto.getTicket()==1){
				if(StringUtils.isEmpty(eventPublishDto.getTicketCnt()) || eventPublishDto.getTicketCnt()<=0){
					result.setMsg("电子门票门票数量为空或为0");
					result.setResultCode(Result.FAILURE);
					return result;
				}
				Ticket ticket = new Ticket();
				ticket.setBusinessId(event.getId());
				ticket.setBusinessType(2);//活动的门票类型为2
				ticket.setTicketCnt(eventPublishDto.getTicketCnt());
				ticket.setRemainingCnt(eventPublishDto.getTicketCnt());
				ticket.setCreateId(event.getCreatorId());
				ticket.setCreateTime(event.getStartTime());
				ticket.setExpireTime(event.getEndTime());

				int ticketCount = ticketMapper.insert(ticket);
				if(ticketCount!=1){
					result.setMsg("电子门票关联数据插入失败");
					result.setResultCode(Result.FAILURE);
					return result;
				}
			}

			result.setResultCode(Result.SUCCESS);
			result.setObj(event);
		}else{
			result.setMsg("活动数据插入失败");
			result.setResultCode(Result.FAILURE);
		}
		return result;
	}

	/**
	 * eventUpdate
	 * @param eventPublishDto
	 * @return
	 */
	@Transactional
	public Result eventUpdate(EventPublishDto eventPublishDto) {
		Result result = new Result();

		if(StringUtils.isEmpty(eventPublishDto.getSubject())){
			result.setResultCode(Result.FAILURE);
			return result;
		}

		//修改活动信息，并修改状态为未审核状态
        Event event = eventMapper.selectByPrimaryKey(eventPublishDto.getEventId());
		event.setOrganizeId(eventPublishDto.getOrganizeId());
		event.setOrganizeName(eventPublishDto.getOrganizeName());
		event.setSubject(eventPublishDto.getSubject());
		event.setStartTime(eventPublishDto.getStartTime());
		event.setEndTime(eventPublishDto.getEndTime());
		event.setAddress(eventPublishDto.getAddress());
		event.setJoinType(eventPublishDto.getJoinType());
		event.setDescription(eventPublishDto.getDescription());
		event.setStatus(0);
		event.setCreateTime(new Date());
		event.setSmallPosterImg(eventPublishDto.getSmallPosterImg());
		event.setPosterImg(eventPublishDto.getPosterImg());
		event.setContentImgs(org.apache.commons.lang3.StringUtils.join(eventPublishDto.getContentImgs(),","));
		event.setFurther(eventPublishDto.getFurther());
		int count = eventMapper.updateByPrimaryKey(event);
		if(count==1){

			//修改社长加入活动的状态为未审核状态
			EventMember eventMember = eventMemberMapper.selectByEventMember(eventPublishDto.getEventId(),eventPublishDto.getCreatorId(),1,3);
			eventMember.setStatus(2);
			eventMember.setJoinTime(new Date());
			int eventMemberCount = eventMemberMapper.updateStatusByEventMember(eventMember);
			if(eventMemberCount!=1){
				result.setResultCode(Result.FAILURE);
				return result;
			}

			result.setResultCode(Result.SUCCESS);
		}else{
			result.setResultCode(Result.FAILURE);
		}
		return result;
	}

	/**
	 * EventRegister
	 * @param eventRegisterDto
	 * @return
	 */
	@Transactional
	public Result eventRegister(EventRegisterDto eventRegisterDto,UserInfo userInfo) {
		Result result = new Result();
		EventMember eventMember = new EventMember();
		if(StringUtils.isEmpty(eventRegisterDto.getEventId())){
			result.setMsg("传入活动Id获取失败");
			result.setResultCode(Result.FAILURE);
			return result;
		}

		Event event = eventMapper.selectByPrimaryKey(eventRegisterDto.getEventId());
		if (event==null){
			result.setMsg("获取活动信息失败");
			result.setResultCode(Result.FAILURE);
			return result;
		}

//		/**
//		 * 判断是以个人还是社团 参加该活动，0代表 以个人名义参加活动，>0代表 以社团名义参加
//		 * 以个人名义不需要判断是否属于某个社团
//		 * 以社团名义需要判断用户是否属于该社团
//		 * */
//		if(eventRegisterDto.getGroupId()>0){
//
//			GroupMember groupMember = new GroupMember();
//			groupMember.setMemberId(userInfo.getUserId());
//			groupMember.setGroupId(eventRegisterDto.getGroupId());
//			groupMember.setStatus(3);
//
//			//检测是否为该社团的成员
//			int groupSelectCount = groupMemberMapper.selectByUserOpt(groupMember);
//
//			//不是该社团的成员
//			if(groupSelectCount==0){
//				groupMember.setStatus(2);
//				groupMember.setJoinTime(new Date());
//				//申请加入社团
//				int groupInsertCount = groupMemberMapper.insertSelective(groupMember);
//				if(groupInsertCount!=1){
//					result.setResultCode(Result.FAILURE);
//					return result;
//				}
//			}
//		}

		//活动报名申请
		eventMember.setEventId(eventRegisterDto.getEventId());
		eventMember.setEventName(event.getSubject());
		eventMember.setMemberId(userInfo.getUserId());
		eventMember.setGroupId(event.getOrganizeId());
		eventMember.setStatus(2);
		eventMember.setIsJoin(1);
		eventMember.setJoinTime(new Date());

		int num = eventMemberMapper.selectByUserOpt(eventMember);
		if(num>0){
			result.setMsg("已提交加入活动申请");
			result.setResultCode(Constants.EVENT_IS_IN);
			return result;
		}

		//查看之前是否参加过该活动
		EventMember selEventMember = eventMemberMapper.selectByEventMember(eventMember.getEventId(), eventMember.getMemberId(), 1, 3);
		int count;
		if(!StringUtils.isEmpty(selEventMember)){
			//已经申请参加过，但是被拒绝了；再次申请，修改那条数据
			selEventMember.setStatus(2);
			selEventMember.setJoinTime(new Date());
			selEventMember.setMemo(null);
			count = eventMemberMapper.updateStatusByEventMember(selEventMember);
		}else{
			//没申请过，插入一条数据
			count = eventMemberMapper.insertSelective(eventMember);
		}
		if(count==1){
			result.setResultCode(Result.SUCCESS);
		}else{
			result.setMsg("活动人员关联失败");
			result.setResultCode(Result.FAILURE);
		}
		return result;
	}

	/**
	 * eventConcern
	 * @param eventRegisterDto
	 * @return
	 */
	@Transactional
	public Result eventConcern(EventRegisterDto eventRegisterDto,UserInfo userInfo) {
		Result result = new Result();
		EventMember eventMember = new EventMember();
		if(StringUtils.isEmpty(eventRegisterDto.getEventId())){
			result.setMsg("传入活动Id获取失败");
			result.setResultCode(Result.FAILURE);
			return result;
		}

		Event event = eventMapper.selectByPrimaryKey(eventRegisterDto.getEventId());

		//关注数量加1，取消关注数量减1
		if(eventRegisterDto.getIsFollow()==0){
			event.setFollowCnt(event.getFollowCnt()-1);
		}else if(eventRegisterDto.getIsFollow()==1){
			event.setFollowCnt(event.getFollowCnt()+1);
		}

		eventMapper.updateByPrimaryKeySelective(event);
		if (event==null){
			result.setMsg("获取活动信息失败");
			result.setResultCode(Result.FAILURE);
			return result;
		}

		eventMember.setEventId(eventRegisterDto.getEventId());
		eventMember.setEventName(event.getSubject());
		eventMember.setGroupId(event.getOrganizeId());
		eventMember.setMemberId(userInfo.getUserId());
		eventMember.setStatus(1);
		eventMember.setIsFollow(eventRegisterDto.getIsFollow());

		EventMember resultEventMember = eventMemberMapper.selectByIsFollow(eventMember);

		int count = 0;
		if(StringUtils.isEmpty(resultEventMember)){
			count = eventMemberMapper.insertSelective(eventMember);
		}else{
			resultEventMember.setIsFollow(eventRegisterDto.getIsFollow());
			count = eventMemberMapper.updateByEventMember(resultEventMember);
		}

		if(count==1){
			result.setResultCode(Result.SUCCESS);
		}else{
			result.setMsg("活动人员关联失败");
			result.setResultCode(Result.FAILURE);
		}

		return result;
	}

	/**
	 * 根据event_id 获取参加活动的社团
	 * @param eventId
	 * */
	public List<EventGroup> getGroupsByEventId(Integer eventId){
		return  eventGroupMapper.selectByEventId(eventId);
	}

	public int initQrCode(Integer eventId,String qrCode){
		Event event = new Event();
		event.setId(eventId);
		event.setQrcode(qrCode);
		return eventMapper.updateByPrimaryKeySelective(event);
	}

    public Result confirmUserEvent(EventUIDto eventUIDto,UserInfo userInfo){
        Result result = new Result();

        if(StringUtils.isEmpty(eventUIDto.getEventId())){
			result.setMsg("获取传入用户Id失败");
            result.setResultCode(Result.FAILURE);
            return result;
        }

        EventMember eventMember = new EventMember();
        eventMember.setEventId(eventUIDto.getEventId());
        eventMember.setMemberId(userInfo.getUserId());
        eventMember.setIsFollow(1);

        int count = eventMemberMapper.selectByUserOpt(eventMember);
        if(count==1){
			result.setMsg("已关注");
            result.setResultCode(Result.SUCCESS);
        }else{
			result.setMsg("未关注");
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    public List<RecommendEvent> hotEvent() {
		return recommendEventMapper.hotEvent();
    }

    //根据(社团id+活动名+状态)查询所有活动
    public PageInfo<EventVo> getEventByGroup(PartnerEventDto partnerEventDto){
		if (partnerEventDto.getPage() != null && partnerEventDto.getRows() != null) {
			PageHelper.startPage(partnerEventDto.getPage(), partnerEventDto.getRows());
		}
		List<EventVo> eventList=eventMapper.selectByGroupId(partnerEventDto);
		PageInfo<EventVo> eventPageInfo=new PageInfo<>(eventList);
		return eventPageInfo;
	}

	public List<EventPhotoVo> photos(EventUIDto eventUIDto) {

		if (eventUIDto.getPage() != null && eventUIDto.getRows() != null) {
			PageHelper.startPage(eventUIDto.getPage(), eventUIDto.getRows());
		}
		return eventMapper.photos(eventUIDto.getEventId());
	}

	public int getUntreatedApplyCountByEventId(Integer eventId) {
		return eventMemberMapper.getUntreatedApplyCountByEventId(eventId);
	}

    public List<UserInfoVo> eventMemberList(EventUIDto eventUIDto, Integer status) {
//		if (eventUIDto.getPage() != null && eventUIDto.getRows() != null) {
//			PageHelper.startPage(eventUIDto.getPage(), eventUIDto.getRows());
//		}
		return eventMemberMapper.selectUserInfoByEventIdAndStatus(eventUIDto.getEventId(),status);
    }

	public Boolean isPresidentByEventIdAndUserId(Integer eventId,Integer userId) {
		Boolean result = false;
		int count = eventMapper.isPresidentByEventIdAndUserId(eventId,userId);
		if(count == 1){
			result = true;
		}
		return result;
	}
}
