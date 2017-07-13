package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.*;
import com.chinaxiaopu.xiaopuMobi.exception.UnknownLoginException;
import com.chinaxiaopu.xiaopuMobi.model.Event;
import com.chinaxiaopu.xiaopuMobi.model.Group;
import com.chinaxiaopu.xiaopuMobi.model.RecommendEvent;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.*;
import com.chinaxiaopu.xiaopuMobi.service.president.PresidentService;
import com.chinaxiaopu.xiaopuMobi.util.QrCodeUtil;
import com.chinaxiaopu.xiaopuMobi.vo.UserInfoListVo;
import com.chinaxiaopu.xiaopuMobi.vo.UserInfoVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.EventPhotoVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wang on 10/9/16.
 */
@Slf4j
@RestController
@RequestMapping("/event")
public class EventController extends ContextController {

    @Autowired
    private EventService eventService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private OSSClientService ossClientService;
    @Autowired
    private PresidentService presidentService;
    @Autowired
    private TicketService ticketService;

    @ApiOperation(value = "活动列表", notes = "根据活动名称,获取活动列表数据\r\n返回值：0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<List<Event>> list(@RequestBody EventListDto eventListDto) {
        Result<List<Event>> result = new Result<>();
        try {
            log.debug("POST请求 活动列表");
            List<Event> eventList = eventService.getEventListByEvent(eventListDto);
            PageInfo<Event> pageInfo = new PageInfo<>(eventList);
            if (eventListDto.getPage() > pageInfo.getPages()) {
                result.setMsg(Result.NO_DATA);
                result.setResultCode(Constants.DATA_NO);
            } else {
                result.setObj(eventList);
                result.setResultCode(Result.SUCCESS);
            }
        } catch (Exception e) {
            log.error("POST请求  活动列表失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "活动详情", notes = "根据活动ID获取活动详细信息\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/eventInfo/{id}", method = RequestMethod.POST)
    public Result<EventDetailDto> eventInfo(@PathVariable("id") final int id) {
        Result<EventDetailDto> result = new Result<>();
        EventDetailDto detailDto = new EventDetailDto();

        try {
            log.debug("POST请求 活动信息");
            detailDto.setEvent(eventService.getEventInfoByEvent(id));
            detailDto.setGroups(groupService.getGroupsByEventId(id));
            if (detailDto.getEvent().getOrganizeId() == 0) {
                if (detailDto.getGroups().isEmpty()) {
                    List<Group> groupList = new ArrayList<>();
                    Group group = new Group();
                    group.setId(0);
                    group.setName("校谱官方");

                    groupList.add(group);
                    detailDto.setGroups(groupList);
                }
            }
            //封装二维码
            detailDto.setShareUrl(SystemConfig.getInstance().getWeixinDomain() + SystemConfig.getInstance().getEvenSharePage() + id);
            if (StringUtils.isEmpty(detailDto.getEvent().getQrcode())) {
                //Base64编码暂缓使用
                String qrcode = genQrCode(id);
                eventService.initQrCode(id, getEventQrCodePath(id));
                detailDto.getEvent().setQrcode(getEventQrCodePath(id));
            }
            //封装待审核数量
            int untreatedMemberCnt = eventService.getUntreatedApplyCountByEventId(id);
            detailDto.setUntreatedMemberCnt(untreatedMemberCnt);

            //需要门票
            Integer isTicket = ticketService.isNeedTicket(id, 2);
            if (isTicket == 1) {
                detailDto.setIsTicket(isTicket);
                //封装剩余门票的数量
                Integer remainingTicketCnt = ticketService.getRemainingTicket(id, 2);
                detailDto.setRemainingCnt(remainingTicketCnt);
            }

            result.setObj(detailDto);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("POST请求 活动信息失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "发布活动", notes = "新增发布活动信息\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功")
    @RequestMapping(value = "/authApi/publish", method = RequestMethod.POST)
    public Result publish(@RequestBody EventPublishDto eventPublishDto) {
        Result result = new Result();
        try {
            log.debug("POST请求 活动发布");
            if (eventPublishDto.getStartTime().getTime() > eventPublishDto.getEndTime().getTime()) {
                result.setMsg("开始时间大于结束时间");
                result.setResultCode(Result.FAILURE);
                return result;
            }

            UserInfo current = getCurrentUser(eventPublishDto);
            //当前登录用户不是该社团的社长
            if (!current.getUserId().equals(eventPublishDto.getCreatorId())) {
                result.setMsg("非本社团的社长");
                result.setResultCode(Result.FAILURE);
                return result;
            }

            result = eventService.eventPublish(eventPublishDto);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (UnknownLoginException ue) {
            log.error("活动发布->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("POST请求  活动发布失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }

        return result;
    }

    @ApiOperation(value = "活动报名", notes = "用户参加报名活动\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功，4代表已加入该活动")
    @RequestMapping(value = "/authApi/register", method = RequestMethod.POST)
    public Result register(@RequestBody EventRegisterDto eventRegisterDto) {
        Result result = new Result();
        try {
            log.debug("POST请求 活动报名");
            UserInfo current = getCurrentUser(eventRegisterDto);
            return eventService.eventRegister(eventRegisterDto, current);
        } catch (UnknownLoginException ue) {
            log.error("活动报名失败-用户未登录");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("POST请求 活动报名失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "关注活动", notes = "用户关注活动信息\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功")
    @RequestMapping(value = "/authApi/concern", method = RequestMethod.POST)
    public Result concern(@RequestBody EventRegisterDto eventRegisterDto) {
        Result result = new Result();
        try {
            log.debug("POST请求 活动关注");
            UserInfo current = getCurrentUser(eventRegisterDto);
            return eventService.eventConcern(eventRegisterDto, current);
        } catch (UnknownLoginException ue) {
            log.error("活动关注失败->用户未登录");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("POST请求 活动关注失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "活动发布上下文环境", notes = "活动上下文环境，创建活动信息\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功")
    @RequestMapping(value = "/authApi/eventContext", method = RequestMethod.POST)
    public Result eventContext(@RequestBody UserUIDto userUIDto) {
        Result result = new Result();
        try {
            log.debug("POST请求 活动发布上下文环境");
            UserInfo userInfo = loginService.getCurrentUserInfo(userUIDto);
            result.setObj(groupService.getManagedGroupsByUserId(userInfo.getUserId()));
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (UnknownLoginException ue) {
            log.error("活动发布上下文环境->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("POST请求 活动发布上下文环境失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "确认用户是否关注", notes = "根据userId和eventId获取用户和活动之间的关系\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功")
    @RequestMapping(value = "/authApi/confirmUserEvent", method = RequestMethod.POST)
    public Result confirmUserEvent(@RequestBody EventUIDto eventUIDto) {
        Result result = new Result();
        try {
            log.debug("确认用户是否关注");
            UserInfo userInfo = getCurrentUser(eventUIDto);
            return eventService.confirmUserEvent(eventUIDto, userInfo);
        } catch (UnknownLoginException ue) {
            log.error("确认用户是否关注->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("确认用户是否关注 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "确认用户能否参加官方活动，并返回", notes = "根据userId和eventId获取用户和活动之间的关系\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功，2代表不属于社团")
    @RequestMapping(value = "/authApi/confirmUserJoinEvent", method = RequestMethod.POST)
    public Result<List<Group>> confirmUserJoinEvent(@RequestBody EventUIDto eventUIDto) {
        Result result = new Result();
        try {
            log.debug("确认用户能否参加活动");
            UserInfo userInfo = getCurrentUser(eventUIDto);
            List<Group> groups = groupService.confirmUserJoinEvent(eventUIDto.getEventId(), userInfo.getUserId());
            if (groups.isEmpty()) {
                result.setMsg("非社团成员");
                result.setResultCode(Constants.NO_IN_GROUPS);
                return result;
            }
            result.setObj(groups);
            result.setResultCode(Result.SUCCESS);
        } catch (UnknownLoginException ue) {
            log.error("确认用户能否参加活动->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("确认用户能否参加活动 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "top热门活动--Wang", notes = "top热门活动\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/hotEvent", method = RequestMethod.POST)
    public Result<List<RecommendEvent>> hotEvent() {
        Result<List<RecommendEvent>> result = new Result<>();
        try {
            log.debug("top热门活动");
            List<RecommendEvent> eventList = eventService.hotEvent();
            result.setObj(eventList);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("top热门活动 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "活动图片获取--Wang", notes = "活动图片获取\n" +
            "返回值：0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/event/info/photos", method = RequestMethod.POST)
    public Result<List<EventPhotoVo>> photos(@RequestBody EventUIDto eventUIDto) {
        Result<List<EventPhotoVo>> result = new Result<>();
        try {
            log.debug("活动图片获取");
            List<EventPhotoVo> list = eventService.photos(eventUIDto);
            PageInfo<EventPhotoVo> pageInfo = new PageInfo<>(list);
            if (list.isEmpty() || eventUIDto.getPage() > pageInfo.getPages()) {
                result.setMsg(Result.NO_DATA);
                result.setResultCode(Constants.DATA_NO);
            } else {
                result.setObj(list);
                result.setResultCode(Result.SUCCESS);
            }
        } catch (Exception e) {
            log.error("top热门活动 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "活动人员列表接口--Wang", notes = "获取活动人员列表\n" +
            "返回值：0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/member/list", method = RequestMethod.POST)
    public Result<UserInfoListVo> eventMember(@RequestBody EventUIDto eventUIDto) {
        Result<UserInfoListVo> result = new Result<>();
        UserInfoListVo userInfoListVo = new UserInfoListVo();
        try {
            log.debug("获取活动人员列表");
            UserInfo userInfo;

            Integer status = Constants.EVENT_MEMBERS_STATUS_ON;
            List<UserInfoVo> list = eventService.eventMemberList(eventUIDto, status);
            PageInfo<UserInfoVo> pageInfo = new PageInfo<>(list);
            if (list.isEmpty() || eventUIDto.getPage() > pageInfo.getPages()) {
                result.setMsg(Result.NO_DATA);
                result.setResultCode(Constants.DATA_NO);
            } else {

                Integer isTicket = ticketService.isNeedTicket(eventUIDto.getEventId(), 2);
                if (isTicket == 1) {
                    //需要门票
                    userInfoListVo.setIsTicket(isTicket);
                    //签到数量，社长默认签到，签到数量默认加1
                    Event event = eventService.getEventInfoByEvent(eventUIDto.getEventId());
                    for (UserInfoVo userInfoVo:list) {
                        if(userInfoVo.getUserId().equals(event.getCreatorId())){
                            userInfoVo.setIsSignIn(1);
                        }
                    }

                    Integer signCnt = ticketService.getSignCnt(eventUIDto.getEventId(), 2);
                    userInfoListVo.setSignCnt(signCnt+1);
                }
                userInfoListVo.setList(list);
                //判断是不是社长
                try {
                    userInfo = getCurrentUser(eventUIDto);
                } catch (UnknownLoginException ule) {
                    userInfo = null;
                }
                if(!org.springframework.util.StringUtils.isEmpty(userInfo)){
                    Boolean flag = eventService.isPresidentByEventIdAndUserId(eventUIDto.getEventId(),userInfo.getUserId());
                    if(flag){
                        userInfoListVo.setIsPresident(1);
                    }
                }
                result.setObj(userInfoListVo);
                result.setResultCode(Result.SUCCESS);
            }
        } catch (Exception e) {
            log.error("获取活动人员列表 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "活动待审核人员列表接口--Wang", notes = "活动待审核人员列表\n" +
            "返回值：-1代表未登录，0代表失败，1代表成功，41代表没有数据，45代表非该活动社长")
    @RequestMapping(value = "/authApi/member/check/list", method = RequestMethod.POST)
    public Result<UserInfoListVo> unEventMember(@RequestBody EventUIDto eventUIDto) {
        Result<UserInfoListVo> result = new Result<>();
        UserInfoListVo userInfoListVo = new UserInfoListVo();
        try {
            log.debug("活动待审核人员列表");
            UserInfo userInfo = getCurrentUser(eventUIDto);
            Boolean flag = eventService.isPresidentByEventIdAndUserId(eventUIDto.getEventId(), userInfo.getUserId());
            if (!flag) {
                result.setMsg("非该活动的社长");
                result.setResultCode(Constants.EVENT_GROUP_PRESIDENT);
                return result;
            }

            Integer status = Constants.EVENT_MEMBERS_STATUS_IN;
            List<UserInfoVo> list = eventService.eventMemberList(eventUIDto, status);
            PageInfo<UserInfoVo> pageInfo = new PageInfo<>(list);
            if (list.isEmpty() || eventUIDto.getPage() > pageInfo.getPages()) {
                result.setMsg(Result.NO_DATA);
                result.setResultCode(Constants.DATA_NO);
            } else {
                userInfoListVo.setList(list);
                result.setObj(userInfoListVo);
                result.setResultCode(Result.SUCCESS);
            }
        } catch (UnknownLoginException ue) {
            log.error("活动待审核人员列表->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("获取活动待审核人员列表 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "审核参加活动申请--Wang", notes = "审核参加活动申请\n" +
            "返回值：-1代表未登录，0代表失败，1代表成功，45代表非该活动社长，46代表门票发放完毕")
    @RequestMapping(value = "/authApi/member/confirm", method = RequestMethod.POST)
    @ResponseBody
    public Result confirmMember(@RequestBody EventMemberConfirmDto eventMemberConfirmDto) {
        Result result = new Result();
        try {
            log.debug("POST请求 审核参加活动申请");
            //获取当前登陆用户
            UserInfo userInfo = getCurrentUser(eventMemberConfirmDto);
            Boolean flag = eventService.isPresidentByEventIdAndUserId(eventMemberConfirmDto.getEventId(), userInfo.getUserId());
            if (!flag) {
                result.setMsg("非该活动的社长");
                result.setResultCode(Constants.EVENT_GROUP_PRESIDENT);
                return result;
            }
            eventMemberConfirmDto.setUserId(userInfo.getUserId());
            //审核申请加入活动人员
            int count = presidentService.confirmEventMember(eventMemberConfirmDto);
            if (count == 1) {
                result.setResultCode(Result.SUCCESS);
            } else if (count == Constants.TICKET_NO) {
                result.setResultCode(Constants.TICKET_NO);
                result.setMsg("门票发放完毕");
            }
            return result;
        } catch (UnknownLoginException ue) {
            result.setMsg(Result.NOT_LOGGED_IN);
            log.error("审核参加活动申请->用户未登录:");
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("审核参加活动申请 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }


    @ApiOperation(value = "审核所有参加活动申请--武宁", notes = "审核所有参加活动申请\n" +
            "返回值：-1代表未登录，0代表失败，1代表成功，45代表非该活动社长，46代表门票发放完毕 ，47 门票不足")
    @RequestMapping(value = "/authApi/member/confirmAll", method = RequestMethod.POST)
    @ResponseBody
    public Result confirmMemberAll(@RequestBody EventMemberConfirmAllDto eventMemberConfirmAllDto) {
        Result result = new Result();
        try {
            log.debug("POST请求 审核所有参加活动申请");
            //获取当前登陆用户
            UserInfo userInfo = getCurrentUser(eventMemberConfirmAllDto);
            Boolean flag = eventService.isPresidentByEventIdAndUserId(eventMemberConfirmAllDto.getEventId(), userInfo.getUserId());
            if (!flag) {
                result.setMsg("非该活动的社长");
                result.setResultCode(Constants.EVENT_GROUP_PRESIDENT);
                return result;
            }
            eventMemberConfirmAllDto.setUserId(userInfo.getUserId());
            int row = presidentService.confirmEventMemberAll(eventMemberConfirmAllDto);
            if (row == 1) {
                result.setResultCode(Result.SUCCESS);
            } else if (row == Constants.TICKET_NO) {
                result.setResultCode(Constants.TICKET_NO);
                result.setMsg("门票发放完毕");
            } else if (row == Constants.TICKET_INSUFFICIENT) {
                result.setResultCode(Constants.TICKET_INSUFFICIENT);
                Integer remainCnt = presidentService.selectRemainTicketNum(eventMemberConfirmAllDto.getEventId());
                result.setObj(remainCnt);
                result.setMsg("门票不足");
            }
            return result;
        } catch (UnknownLoginException ue) {
            result.setMsg(Result.NOT_LOGGED_IN);
            log.error("审核所有参加活动申请->用户未登录:");
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("审核所有参加活动申请 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    //生成Base64编码和png图片
    //TODO: 待重构
    private String genQrCode(Integer eventId) throws Exception {
        String filePath = systemConfig.getImgBasePath() + "event/event_qr_" + eventId;
        String qrCodeStr = QrCodeUtil.getLogoQRCode(getWeixinHostDomain() + "activityDetail.html?id=" + eventId, "", filePath);
        //二维码图片上传至OSS服务器
        ossClientService.putOSSByFilePath(getEventQrCodePath(eventId), filePath + ".png");
        return qrCodeStr;
    }

    private String getEventQrCodePath(Integer eventId) {
        return "event_qr_" + eventId + ".png";
    }
}