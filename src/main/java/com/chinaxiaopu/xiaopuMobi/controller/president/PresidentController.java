package com.chinaxiaopu.xiaopuMobi.controller.president;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.controller.ContextController;
import com.chinaxiaopu.xiaopuMobi.dto.*;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.chinaxiaopu.xiaopuMobi.vo.president.EventMemberVo;
import com.chinaxiaopu.xiaopuMobi.vo.president.EventVo;
import com.chinaxiaopu.xiaopuMobi.vo.president.GroupMemberVo;
import com.chinaxiaopu.xiaopuMobi.security.realm.ShiroUser;
import com.chinaxiaopu.xiaopuMobi.service.EventService;
import com.chinaxiaopu.xiaopuMobi.service.GroupService;
import com.chinaxiaopu.xiaopuMobi.service.UserService;
import com.chinaxiaopu.xiaopuMobi.service.president.PresidentService;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Wang on 2016/11/2.
 */
@Slf4j
@Controller
@RequestMapping("/president")
public class PresidentController extends ContextController{

    @Autowired
    private PresidentService presidentService;
    @Autowired
    private EventService eventService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model){
        try {
            log.debug("POST请求 社团管理首页");
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            Integer userId = shiroUser.getId();
            //活动人员待审核数量
            int untreatedEventApplyCount = presidentService.getUntreatedEventApplyCount(userId,null);
            model.addAttribute("untreatedEventApplyCount",untreatedEventApplyCount);
            //活动人员总数量
            int eventApplyCount = presidentService.getEventApplyCount(userId,null);
            model.addAttribute("eventApplyCount",eventApplyCount);
            //社团人员待审核数量
            int untreatedGroupApplyCount = presidentService.getUntreatedGroupApplyCount(userId,null);
            model.addAttribute("untreatedGroupApplyCount",untreatedGroupApplyCount);
            //社团人员总数量
            int groupApplyCount = presidentService.getGroupApplyCount(userId,null);
            model.addAttribute("groupApplyCount",groupApplyCount);

        } catch (Exception e) {
            log.error("POST请求  社团管理首页失败", e);
            model.addAttribute("msg","获取社团管理首页数据失败");
        }
        return "president/index";
    }

    @RequestMapping(value = "/event/list", method = RequestMethod.GET)
    public String eventList(){
        return "president/eventList";
    }
    @RequestMapping(value = "/event/list/forward", method = RequestMethod.GET)
    public String eventList1(){
        return "president/eventList";
    }

    @RequestMapping(value = "/event/publishContext", method = RequestMethod.GET)
    public String publishContext(Model model){
        //图片服务器地址
        String imgsHostDomain = new ContextDto().getImgsHostDomain();
        model.addAttribute("imgsHostDomain",imgsHostDomain);

        //获取当前登录用户
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Integer userId = shiroUser.getId();
        //获取我的社团管理列表信息
        GroupMemberDto groupMemberDto = new GroupMemberDto();
        groupMemberDto.setUserId(userId);
        groupMemberDto.setStatus(1);
        List<Group> groupList = presidentService.manageGroupList(groupMemberDto);
        model.addAttribute("groupList",groupList);

        //获取学校信息
        if(!StringUtils.isEmpty(groupList)){
            Integer schoolId = groupList.get(0).getSchoolId();
            String schoolName = groupList.get(0).getSchoolName();
            model.addAttribute("schoolId",schoolId);
            model.addAttribute("schoolName",schoolName);
        }

        return "president/eventPublish";
    }

    @RequestMapping(value = "/eventMember/listInit/{eventId}/{status}", method = RequestMethod.GET)
    public String eventMemberIndex(Model model,@PathVariable("status") final int status,@PathVariable("eventId") final int eventId){
        if(!StringUtils.isEmpty(status)){
            model.addAttribute("status",status);
        }
        if(!StringUtils.isEmpty(eventId)){
            model.addAttribute("eventId",eventId);
        }
        //获取当前登录用户
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Integer userId = shiroUser.getId();
        //获取发布活动列表信息
        EventMemberDto eventMemberDto = new EventMemberDto();
        eventMemberDto.setUserId(userId);
        eventMemberDto.setStatus(1);
        List<Event> eventList = presidentService.manageEventList(eventMemberDto);
        model.addAttribute("eventList",eventList);

        return "president/eventMemberList";
    }

    @RequestMapping(value = "/event/detail/{eventId}", method = RequestMethod.GET)
    public String eventDetailInit(Model model,@PathVariable("eventId") final int eventId){
        //获取当前登录用户
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Integer userId = shiroUser.getId();
        if(!StringUtils.isEmpty(eventId)){
            //获取活动详情，封装到EventVo
            Event event = eventService.getEventInfoByEvent(eventId);
            EventVo eventVo = new EventVo();
            BeanUtils.copyProperties(event,eventVo);
            //获取社团详情，封装到EventVo
            Group group = groupService.getGroupInfoByGroupId(eventVo.getOrganizeId());
            eventVo.setGroup(group);
            //获取待审核申请加入活动的人员数量
            int untreatedApplyCount = presidentService.getUntreatedEventApplyCount(userId,event.getId());
            eventVo.setUntreatedApplyCount(untreatedApplyCount);
            //获取总的申请加入活动的人员数量
            int applyCount = presidentService.getEventApplyCount(userId,event.getId());
            eventVo.setApplyCount(applyCount);
            model.addAttribute("event",eventVo);
        }

        return "president/eventDetail";
    }

    @RequestMapping(value = "/groupMember/list", method = RequestMethod.GET)
    public String groupMemberList(){
        return "president/groupMemberList";
    }

    @RequestMapping(value = "/eventMember/list", method = RequestMethod.GET)
    public String eventMemberList(Model model){
        //获取当前登陆用户
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Integer userId = shiroUser.getId();
        //获取发布活动的列表信息
        EventMemberDto eventMemberDto = new EventMemberDto();
        eventMemberDto.setUserId(userId);
        eventMemberDto.setStatus(1);
        List<Event> eventList = presidentService.manageEventList(eventMemberDto);
        model.addAttribute("eventList",eventList);
        return "president/eventMemberList";
    }

    @RequestMapping(value = "/groupMember/listInit/{status}", method = RequestMethod.GET)
    public String groupMemberListInit(Model model,@PathVariable("status") final int status){
        if(!StringUtils.isEmpty(status)){
            model.addAttribute("status",status);
        }
        //获取当前登录用户
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Integer userId = shiroUser.getId();
        //获取管理社团列表信息
        GroupMemberDto groupMemberDto = new GroupMemberDto();
        groupMemberDto.setUserId(userId);
        groupMemberDto.setStatus(1);
        List<Group> groupList = presidentService.manageGroupList(groupMemberDto);
        model.addAttribute("groupList",groupList);

        return "president/groupMemberList";
    }

    @ApiOperation(value = "社团活动列表", notes = "根据社长Id,获取所有社团，再获取社团活动列表数据\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/event/list", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<EventVo>> eventList(@RequestBody GroupEventListDto groupEventListDto) {
        Result<PageInfo<EventVo>> result = new Result<PageInfo<EventVo>>();
        PageInfo<Event> eventList;
        PageInfo<EventVo> eventVoList = new PageInfo<>();
        try {
            log.debug("POST请求 社团活动列表");
            //获取当前登录用户
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            Integer userId = shiroUser.getId();
            groupEventListDto.setUserId(userId);
            //获取发布活动列表，并将其封装到PageInfo<EventVo>
            eventList =  presidentService.getGroupEventList(groupEventListDto);
            BeanUtils.copyProperties(eventList,eventVoList);
            List<EventVo> events = new ArrayList();
            Group group;
            for (Event event:eventVoList.getList()){
                EventVo eVo = new EventVo();
                BeanUtils.copyProperties(event,eVo);
                //获取社团信息，封装到EventVo
                group = groupService.getGroupInfoByGroupId(eVo.getOrganizeId());
                eVo.setGroup(group);
                //获取待审核申请加入活动的人员数量
                int untreatedApplyCount = presidentService.getUntreatedEventApplyCount(groupEventListDto.getUserId(),event.getId());
                eVo.setUntreatedApplyCount(untreatedApplyCount);
                //获取总的申请加入活动的人员数量
                int applyCount = presidentService.getEventApplyCount(groupEventListDto.getUserId(),event.getId());
                eVo.setApplyCount(applyCount);

                events.add(eVo);
            }
            eventVoList.setList(events);

            result.setObj(eventVoList);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("POST请求  社团活动列表失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "活动申请人列表", notes = "根据社长Id,获取所有社团，再获取社团活动申请人列表数据\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/eventMember/list", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<EventMemberVo>> eventMemberList(@RequestBody EventMemberDto eventMemberDto) {
        Result<PageInfo<EventMemberVo>> result = new Result<PageInfo<EventMemberVo>>();
        PageInfo<EventMemberVo> eventMemberVoList = new PageInfo<>();
        try {
            log.debug("POST请求 活动申请人列表");
            //获取当前登陆用户
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            Integer userId = shiroUser.getId();
            //获取发布活动的人员列表信息，并封装到PageInfo<EventMember>
            eventMemberDto.setUserId(userId);
            PageInfo<EventMember> eventMemberList = presidentService.eventMemberlist(eventMemberDto);
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

                //获取社团详情，并封装到EventMemberVo
                group = groupService.getGroupInfoByGroupId(event.getOrganizeId());
                eventMemberVo.setGroup(group);

                //获取社员详情，并封装到EventMemberVo
                userInfo = userService.getUserInfoByUserId(eventMemberVo.getMemberId());
                eventMemberVo.setUserInfo(userInfo);

                //判断是否为该社团社员，并封装到EventMemberVo
                int isGroupMember = groupService.isGroupMember(group.getId(), userInfo.getUserId(), 1);
                eventMemberVo.setIsGroupMember(isGroupMember);

                eventMemberVos.add(eventMemberVo);
            }
            eventMemberVoList.setList(eventMemberVos);

            result.setObj(eventMemberVoList);
            result.setResultCode(Result.SUCCESS);

            return result;
        } catch (Exception e) {
            log.error("POST请求  活动申请人列表失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "发布活动", notes = "新增发布活动信息   返回值：-1代表没有登陆，0代表失败，1代表成功")
    @RequestMapping(value = "/event/publish", method = RequestMethod.POST)
    @ResponseBody
    public Result publish(@RequestBody EventPublishDto eventPublishDto) {
        Result result = new Result();
        try {
            log.debug("POST请求 活动发布");
            //获取当前登录用户
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            //活动发布
            eventPublishDto.setCreatorId(shiroUser.getId());
            result = eventService.eventPublish(eventPublishDto);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("POST请求  活动发布失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }

        return result;
    }

    @ApiOperation(value = "活动修改上下文", notes = "活动修改上下文")
    @RequestMapping(value = "/event/updateContext/{eventId}", method = RequestMethod.GET)
    public String updateContext(Model model,@PathVariable("eventId") final int eventId) {
        try {
            log.debug("POST请求 活动修改上下文");
            if(!StringUtils.isEmpty(eventId)){
                //获取当前登录用户
                ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
                Integer userId = shiroUser.getId();
                //获取活动信息，并封装到EventVo
                Event event = eventService.getEventInfoByEvent(eventId);
                EventVo eventVo = new EventVo();
                BeanUtils.copyProperties(event,eventVo);
                //获取社团信息，并封装到EventVo
                Group group = groupService.getGroupInfoByGroupId(eventVo.getOrganizeId());
                eventVo.setGroup(group);
                model.addAttribute("event",eventVo);

                //获取管理社团列表信息
                GroupMemberDto groupMemberDto = new GroupMemberDto();
                groupMemberDto.setUserId(userId);
                groupMemberDto.setStatus(1);
                List<Group> groupList = presidentService.manageGroupList(groupMemberDto);
                model.addAttribute("groupList",groupList);

                //图片服务器地址
                String imgsHostDomain = new ContextDto().getImgsHostDomain();
                model.addAttribute("imgsHostDomain",imgsHostDomain);

                //判断图片是否存在
                if(!StringUtils.isEmpty(eventVo.getPosterImg())){
                    model.addAttribute("posterImg",1);
                }else{
                    model.addAttribute("posterImg",0);
                }
                if(!StringUtils.isEmpty(eventVo.getContentImgsArray())){
                    if(eventVo.getContentImgsArray().length<1){
                        model.addAttribute("contentImg1","");
                    }else{
                        model.addAttribute("contentImg1",eventVo.getContentImgsArray()[0]);
                    }
                    if(eventVo.getContentImgsArray().length<2){
                        model.addAttribute("contentImg2","");
                    }else{
                        model.addAttribute("contentImg2",eventVo.getContentImgsArray()[1]);
                    }
                    if(eventVo.getContentImgsArray().length<3){
                        model.addAttribute("contentImg3","");
                    }else{
                        model.addAttribute("contentImg3",eventVo.getContentImgsArray()[2]);
                    }
                }
                //将图片的宽度和高度进行存储
                Gson gson = new Gson();
                List<Map<String,String>> furtherList = gson.fromJson(eventVo.getFurther(),List.class);
                for(int i=2;i<5;i++){
                    if( furtherList.size() >= (i-1) ){
                        model.addAttribute("width"+i,furtherList.get(i-2).get("imageWidth"));
                        model.addAttribute("height"+i,furtherList.get(i-2).get("imageHeight"));
                    }
                }

            }
        } catch (Exception e) {
            log.error("POST请求  活动修改上下文失败", e);
        }
        return "president/eventUpdate";
    }

    @ApiOperation(value = "修改活动,再次审核", notes = "修改活动,再次审核   返回值：-1代表没有登陆，0代表失败，1代表成功")
    @RequestMapping(value = "/event/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody EventPublishDto eventPublishDto) {
        Result result = new Result();
        try {
            log.debug("POST请求 修改活动,再次审核");
            //获取当前登录用户
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            eventPublishDto.setCreatorId(shiroUser.getId());
            //活动修改，再次提交审核
            result = eventService.eventUpdate(eventPublishDto);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("POST请求  修改活动,再次审核失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "社团申请人列表", notes = "根据社长Id,获取所有社团，再获取社团申请人列表数据\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/groupMember/list", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<GroupMemberVo>> groupMemberList(@RequestBody GroupMemberDto groupMemberDto) {
        Result<PageInfo<GroupMemberVo>> result = new Result<PageInfo<GroupMemberVo>>();
        PageInfo<GroupMember> groupMemberList;
        try {
            log.debug("POST请求 社团申请人列表");
            //获取当前登录用户
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            Integer userId = shiroUser.getId();
            //获取管理社团的人员列表信息，并封装到PageInfo<GroupMember>
            groupMemberDto.setUserId(userId);
            groupMemberList = presidentService.groupMemberlist(groupMemberDto);
            PageInfo<GroupMemberVo> groupMemberVoList = new PageInfo<>();
            BeanUtils.copyProperties(groupMemberList,groupMemberVoList);
            List<GroupMemberVo> groupMemberVos = new ArrayList();
            Group group;
            UserInfo userInfo;
            for (GroupMember groupMember:groupMemberVoList.getList()){
                //将社团人员信息，封装到GroupMemberVo
                GroupMemberVo groupMemberVo = new GroupMemberVo();
                BeanUtils.copyProperties(groupMember,groupMemberVo);
                //获取人员信息，并封装到GroupMemberVo
                userInfo = userService.getUserInfoByUserId(groupMemberVo.getMemberId());
                groupMemberVo.setUserInfo(userInfo);
                //获取社团信息，并封装到GroupMemberVo
                group = groupService.getGroupInfoByGroupId(groupMemberVo.getGroupId());
                groupMemberVo.setGroup(group);

                groupMemberVos.add(groupMemberVo);
            }
            groupMemberVoList.setList(groupMemberVos);
            result.setObj(groupMemberVoList);
            result.setResultCode(Result.SUCCESS);

            return result;
        } catch (Exception e) {
            log.error("POST请求  社团申请人列表失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "管理社团列表", notes = "根据社长Id,获取所有社团\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/groupList/manage", method = RequestMethod.POST)
    @ResponseBody
    public Result<List<Group>> manageGroupList(@RequestBody GroupMemberDto groupMemberDto) {
        Result<List<Group>> result = new Result<List<Group>>();
        try {
            log.debug("POST请求 管理社团列表");
            //获取当前登录用户
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            Integer userId = shiroUser.getId();
            //获取管理社团列表信息
            groupMemberDto.setUserId(userId);
            List<Group> groupList = presidentService.manageGroupList(groupMemberDto);
            result.setObj(groupList);
            result.setResultCode(Result.SUCCESS);

            return result;
        } catch (Exception e) {
            log.error("POST请求  管理社团列表失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "管理活动列表", notes = "根据社长Id,获取所有社团活动\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/eventList/manage", method = RequestMethod.POST)
    @ResponseBody
    public Result<List<Event>> manageEventList(@RequestBody EventMemberDto eventMemberDto) {
        Result<List<Event>> result = new Result<List<Event>>();
        try {
            log.debug("POST请求 管理活动列表");
            //获取当前登陆用户
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            Integer userId = shiroUser.getId();
            //获取发布活动的列表信息
            eventMemberDto.setUserId(userId);
            List<Event> eventList = presidentService.manageEventList(eventMemberDto);
            result.setObj(eventList);
            result.setResultCode(Result.SUCCESS);

            return result;
        } catch (Exception e) {
            log.error("POST请求  管理活动列表失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "审核参加活动申请", notes = "审核参加活动申请\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/eventMember/confirm", method = RequestMethod.POST)
    @ResponseBody
    public Result confirmEventList(@RequestBody EventMemberConfirmDto eventMemberConfirmDto) {
        Result result = new Result();
        try {
            log.debug("POST请求 审核参加活动申请");
            //获取当前登陆用户
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            Integer userId = shiroUser.getId();
            eventMemberConfirmDto.setUserId(userId);

            //审核申请加入活动人员
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
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "审核参加社团申请", notes = "审核参加社团申请\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/groupMember/confirm", method = RequestMethod.POST)
    @ResponseBody
    public Result confirmGroupList(@RequestBody GroupMemberConfirmDto groupMemberConfirmDto) {
        Result result = new Result();
        try {
            log.debug("POST请求 审核参加社团申请");
            //获取当前登录用户
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            Integer userId = shiroUser.getId();
            groupMemberConfirmDto.setUserId(userId);
            //审核申请加入社团人员
            int count = presidentService.confirmGroupMember(groupMemberConfirmDto);
            if(count == 1){
                result.setResultCode(Result.SUCCESS);
            }
            return result;
        } catch (Exception e) {
            log.error("POST请求  审核参加社团申请失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

}