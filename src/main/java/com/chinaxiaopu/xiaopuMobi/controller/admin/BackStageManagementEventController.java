package com.chinaxiaopu.xiaopuMobi.controller.admin;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.EventDto;
import com.chinaxiaopu.xiaopuMobi.dto.EventMemberConfirmDto;
import com.chinaxiaopu.xiaopuMobi.dto.EventMemberDto;
import com.chinaxiaopu.xiaopuMobi.dto.ReviewedEventDto;
import com.chinaxiaopu.xiaopuMobi.mapper.RecommendEventMapper;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.chinaxiaopu.xiaopuMobi.security.realm.ShiroUser;
import com.chinaxiaopu.xiaopuMobi.service.*;
import com.chinaxiaopu.xiaopuMobi.service.president.PresidentService;
import com.chinaxiaopu.xiaopuMobi.vo.president.EventMemberVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台活动管理控制器
 * @author ycy
 *
 */
@Slf4j
@Controller
@RequestMapping("/admin/event")
public class BackStageManagementEventController {

	@Autowired
	private PresidentService presidentService;

	@Autowired
	private BackStageManagementEventService backStageManagementEventService;

	@Autowired
	private ReviewedGroupService reviewedGroupService;

	@Autowired
	private EventService eventService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userService;
	@Autowired
	private RecommendEventMapper recommendEventMapper;


	/**
	 * 跳转活动列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getEventList(Model model){
		try {
			List<School> list = reviewedGroupService.selectAllSchoolNameAndId();
			model.addAttribute("schools",list);
		} catch (Exception e) {
			log.error("查询学校列表失败", e);
		}
		return "group/eventList";
	}


	/**
	 * 首页跳转活动列表
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/goEventList", method = RequestMethod.GET)
	public String goEventList( Model model,HttpServletRequest request, HttpServletResponse response){
		try {
			List<School> list = reviewedGroupService.selectAllSchoolNameAndId();
			model.addAttribute("schools",list);
		} catch (Exception e) {
			log.error("查询学校列表失败", e);
		}
		model.addAttribute("status",0);
		return "group/eventList";
	}


	//@RequestMapping(value = "/test", method = RequestMethod.GET)
	//public String test(){
		//return "group/test";
	//}

	/**
	 * 跳转官方创建的活动列表
	 * @return
	 */
	@RequestMapping(value = "/official", method = RequestMethod.GET)
	public String toOffice() {
		return "group/officialEventList";
	}


	/**
	 * 跳转活动创建页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String toOfficeCreate(Model model) {
		try {
			model.addAttribute("imgsDomain", SystemConfig.getInstance().getImgsDomain());
		}catch (Exception e){
			log.error("进入活动创建页面失败",e);
		}
		model.addAttribute("groupName","校谱官方");
		return "group/officialEventCreate";
	}

	/**
	 * 跳转活动人员加入审核
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/eventMember", method = RequestMethod.GET)
	public String eventMemberIndex(Model model){
		EventDto eventDto = new EventDto();
		eventDto.setOrganizeId(0);
        List<EventDto> eventList = backStageManagementEventService.selectEventList(eventDto);
		model.addAttribute("eventList",eventList);

		return "group/eventMemberList";
	}

	/**
	 * 获取活动列表
	 * @param eventDto
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Result<PageInfo<EventDto>> list(@RequestBody EventDto eventDto) {
		Result<PageInfo<EventDto>> result = new Result<PageInfo<EventDto>>();
		if( "".equals(eventDto.getSchoolId() )){
			eventDto.setSchoolId(null);
		}
		try {
			result.setObj(backStageManagementEventService.getEventList(eventDto));
			result.setResultCode(Result.SUCCESS);
			return result;
		} catch (Exception e) {
			log.error("获取活动列表",e);
			result.setResultCode(Result.FAILURE);
			result.setMsg(Result.SERVER_EXCEPTION);
		}
		return result;
	}

	/**
	 * 获取活动详情
	 * @param eventDto
	 * @return
	 */
	@RequestMapping(value = "/details", method = RequestMethod.POST)
	@ResponseBody
	public Result<EventDto> details(@RequestBody EventDto eventDto) {
		Result<EventDto> result = new Result<EventDto>();
		try {
			result.setObj(backStageManagementEventService.getEvenDetials(eventDto));
			result.setResultCode(Result.SUCCESS);
			return result;
		} catch (Exception e) {
			log.error("P请求社团详情失败",e);
			result.setResultCode(Result.FAILURE);
			result.setMsg(Result.SERVER_EXCEPTION);
		}
		return result;
	}


	/**
	 * 跳转指定ID的详情页面
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/goDetails/{id}", method = RequestMethod.GET)
	public String godetails(@PathVariable("id") final int id, Model model, HttpServletRequest request, HttpServletResponse response) {
		EventDto eventDto = new EventDto();
		eventDto.setId(id);
		EventDto event = null;
		try {
			event=backStageManagementEventService.getEvenDetials(eventDto);
			if(event.getContentImgs() != null){
				String[] imgs = event.getContentImgs().split(",");
				model.addAttribute("imgs",imgs);
			}
			model.addAttribute("event",event);
			model.addAttribute("imgsDomain", SystemConfig.getInstance().getImgsDomain());
			// 添加是否置顶的状态
			Integer isTop = recommendEventMapper.isTop(event.getId());
			model.addAttribute("isTop",isTop);
		} catch (Exception e) {
			log.error("获取活动详情",e);
		}
		return "group/eventDetails";
	}

	/**
	 * 跳转官方活动详情
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/goOfficialDetails/{id}", method = RequestMethod.GET)
	public String goOffdetails(@PathVariable("id") final int id, Model model, HttpServletRequest request, HttpServletResponse response) {
		EventDto eventDto = new EventDto();
		eventDto.setId(id);
		EventDto event = null;
		try {
			event=backStageManagementEventService.getEvenDetials(eventDto);
			if(event.getContentImgs() != null){
				String[] imgs = event.getContentImgs().split(",");
				model.addAttribute("imgs",imgs);
			}
			model.addAttribute("event",event);
			model.addAttribute("imgsDomain", SystemConfig.getInstance().getImgsDomain());
			// 添加是否置顶的状态
			Integer isTop = recommendEventMapper.isTop(event.getId());
			model.addAttribute("isTop",isTop);
		} catch (Exception e) {
			log.error("获取活动详情",e);
		}
		return "group/officialEventDetials";
	}

	/**
	 * 创建活动
	 * @param eventDto
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public Result create(@RequestBody EventDto eventDto) {
		Result<EventDto> result = new Result<>();
		try {
			backStageManagementEventService.createOfficeEvent(eventDto);
			result.setResultCode(Result.SUCCESS);
			return result;
		} catch (Exception e) {
			log.error("创建活动失败",e);
			result.setResultCode(Result.FAILURE);
			result.setMsg(Result.SERVER_EXCEPTION);
		}
		return result;
	}

	/**
	 * 审核活动
	 * @param event
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(@RequestBody ReviewedEventDto event) {
		Result<Event> result = new Result<Event>();
		try {
			//获取当前登陆用户
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			Integer userId = shiroUser.getId();
			event.setUserId(userId);
			backStageManagementEventService.updateEventById(event);
			result.setResultCode(Result.SUCCESS);
			return result;
		} catch (Exception e) {
			log.error("审核活动失败",e);
			result.setResultCode(Result.FAILURE);
			result.setMsg(Result.SERVER_EXCEPTION);
		}
		return result;
	}


	@RequestMapping(value = "/eventMember/list", method = RequestMethod.POST)
	@ResponseBody
	public Result<PageInfo<EventMemberVo>> eventMemberList(@RequestBody EventMemberDto eventMemberDto) {
		Result<PageInfo<EventMemberVo>> result = new Result<PageInfo<EventMemberVo>>();
		PageInfo<EventMemberVo> eventMemberVoList = new PageInfo<>();
		try {
			log.debug("POST请求 活动申请人列表");
			//获取当前登陆用户
			//获取发布活动的人员列表信息，并封装到PageInfo<EventMember>\
			PageInfo<EventMember> eventMemberList = backStageManagementEventService.eventMemberlist(eventMemberDto);
			BeanUtils.copyProperties(eventMemberList,eventMemberVoList);
			List<EventMemberVo> eventMemberVos = new ArrayList();
			Event event;
			Group group;
			UserInfo userInfo;
			for (EventMember eventMember:eventMemberVoList.getList()){
				EventMemberVo eventMemberVo = new EventMemberVo();
				BeanUtils.copyProperties(eventMember,eventMemberVo);

				//获取活动详情，并封装到EventMemberVo
				event = eventService.getEventInfoByEvent(eventMemberVo.getEventId());
				eventMemberVo.setEvent(event);

				//获取社员详情，并封装到EventMemberVo
				userInfo = userService.getUserInfoByUserId(eventMemberVo.getMemberId());
				eventMemberVo.setUserInfo(userInfo);

				eventMemberVos.add(eventMemberVo);
			}
			eventMemberVoList.setList(eventMemberVos);

			result.setObj(eventMemberVoList);
			result.setResultCode(Result.SUCCESS);

			return result;
		} catch (Exception e) {
			log.error("POST请求  活动申请人列表失败", e);
			result.setResultCode(Result.FAILURE);
			result.setMsg(Result.SERVER_EXCEPTION);
		}
		return result;
	}


	@RequestMapping(value = "/eventMember/confirm", method = RequestMethod.POST)
	@ResponseBody
	public Result confirmEventList(@RequestBody EventMemberConfirmDto eventMemberConfirmDto) {
		Result result = new Result();
		try {
			log.debug("POST请求 审核参加校谱活动申请");
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			Integer userId = shiroUser.getId();
			eventMemberConfirmDto.setUserId(userId);

			int count = presidentService.confirmEventMember(eventMemberConfirmDto);
			if(count == 1){
				result.setResultCode(Result.SUCCESS);
			} else if(count== Constants.TICKET_NO){
				result.setResultCode(Result.SUCCESS);
				result.setMsg("门票发放完毕");
			}
			return result;
		} catch (Exception e) {
			log.error("POST请求  审核参加活动申请失败", e);
			result.setResultCode(Result.FAILURE);
			result.setMsg(Result.SERVER_EXCEPTION);
		}
		return result;
	}

	@RequestMapping(value = "/top/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Result confirmEventList(@PathVariable("id") final int id) {
		Result result = new Result();
		try {
			log.debug("POST请求 活动置顶");
			Boolean flag = backStageManagementEventService.top(id);
			if(flag){
				result.setResultCode(Result.SUCCESS);
			}else{
				result.setResultCode(Result.FAILURE);
			}
		} catch (Exception e) {
			log.error("活动置顶 失败", e);
			result.setMsg(Result.SERVER_EXCEPTION);
			result.setResultCode(Result.FAILURE);
		}
		return result;
	}
}
