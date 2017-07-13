package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.code.LoginResult;
import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.code.UserInfoResult;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.*;
import com.chinaxiaopu.xiaopuMobi.exception.UnknownLoginException;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.chinaxiaopu.xiaopuMobi.service.SchoolService;
import com.chinaxiaopu.xiaopuMobi.service.TicketService;
import com.chinaxiaopu.xiaopuMobi.service.UserFansService;
import com.chinaxiaopu.xiaopuMobi.service.UserService;
import com.chinaxiaopu.xiaopuMobi.service.president.PresidentService;
import com.chinaxiaopu.xiaopuMobi.service.topic.MyFavTopicService;
import com.chinaxiaopu.xiaopuMobi.service.topic.PrizeService;
import com.chinaxiaopu.xiaopuMobi.vo.EventMemberVo;
import com.chinaxiaopu.xiaopuMobi.vo.GroupMemberVo;
import com.chinaxiaopu.xiaopuMobi.vo.TicketListVo;
import com.chinaxiaopu.xiaopuMobi.vo.TicketVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.MyTopicsVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.PrizeListVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.TopicVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.TopicsVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
/**
 * Created by Wang on 10/9/16.
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends ContextController{

    @Autowired
    private MyFavTopicService myFavTopicService;
    @Autowired
    private UserService userService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private PrizeService prizeService;
    @Autowired
    private UserFansService userFansService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private PresidentService presidentService;

    @ApiOperation(value = "用户注册", notes = "用户注册\n" +
            "返回值：0代表失败，1代表成功，11代表手机号为空，13代表用户已存在，" +
            "21代表密码为空，22代表密码不符合标准（6-16位），31代表验证码为空，32代表验证码错误,33邀请码无效")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public LoginResult register(@RequestBody UserRegisterDto registerUserDto) throws Exception {
        LoginResult result = new LoginResult();
        try {
            log.debug("POST请求注册");
            return userService.register(registerUserDto);
        } catch (Exception e) {
            log.error("用户请求注册失败:",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "用户信息", notes = "根据userId获取用户信息\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功")
    @RequestMapping(value = "/authApi/userInfo", method = RequestMethod.POST)
    public UserInfoResult<UserInfo> userInfo(@RequestBody UserUIDto userUIDto) throws Exception {
        UserInfoResult<UserInfo> result = new UserInfoResult<UserInfo>();
        try {
            log.debug("POST请求用户信息");
            UserInfo current = getCurrentUser(userUIDto);
            UserInfo userInfo = userService.getUserInfoByUserId(current.getUserId());
            if (StringUtils.isEmpty(userInfo)) {
                result.setMsg("获取用户信息失败");
                result.setResultCode(Result.FAILURE);
            } else {
                result.setObj(userInfo);
                result.setResultCode(Result.SUCCESS);
            }
            return result;
        } catch (UnknownLoginException ue) {
            log.error("用户信息->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求获取用户失败:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "用户信息编辑", notes = "根据userId修改用户信息\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功")
    @RequestMapping(value = "/authApi/update", method = RequestMethod.POST)
    public Result update(@RequestBody UserInfoCreateDto userInfoCreateDto) throws Exception {
        Result result = new Result();
        try {
            log.debug("POST请求 用户信息修改提交");
            School school = schoolService.selectByPrimaryKey(userInfoCreateDto.getSchoolId()).getObj();
            if (school != null) {
                userInfoCreateDto.setSchoolName(school.getName());
                UserInfo current = getCurrentUser(userInfoCreateDto);
                result = userService.userInfoUpdate(userInfoCreateDto,current);
            }
        } catch (UnknownLoginException ue) {
            log.error("用户信息编辑->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求 用户信息修改提交失败:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "我的好友", notes = "根据userId获取好友信息\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功")
    @RequestMapping(value = "/authApi/friend", method = RequestMethod.POST)
    @ApiIgnore
    public Result<List<UserInfo>> friend(@RequestBody UserUIDto userUIDto) throws Exception {
        Result<List<UserInfo>> result = new Result<List<UserInfo>>();
        try {
            log.debug("POST请求我的好友列表");
            UserInfo current = getCurrentUser(userUIDto);
            List<UserInfo> friendList = userService.getFriendListByUserId(current);
            result.setObj(friendList);
            result.setResultCode(Result.SUCCESS);
        } catch (UnknownLoginException ue) {
            log.error("我的好友列表->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求 我的好友列表失败:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "关注的社团", notes = "根据userId获取关注社团信息\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功")
    @RequestMapping(value = "/authApi/concernGroup", method = RequestMethod.POST)
    @ApiIgnore
    public Result<List<Group>> concernGroup(@RequestBody GroupJoinDto groupJoinDto) throws Exception {
        Result<List<Group>> result = new Result<List<Group>>();

        try {
            log.debug("POST请求 关注的社团列表");
            UserInfo current = getCurrentUser(groupJoinDto);
            groupJoinDto.setUserId(current.getUserId());
            List<Group> concernGroupList = userService.getConcernGroupListByUserId(groupJoinDto, current);

            PageInfo<Group> pageInfo = new PageInfo<>(concernGroupList);
            if(groupJoinDto.getPage()>pageInfo.getPages()){
                result.setMsg(Result.NO_DATA);
                result.setResultCode(Constants.DATA_NO);
            }else{
                result.setObj(concernGroupList);
                result.setResultCode(Result.SUCCESS);
            }
            return result;
        } catch (UnknownLoginException ue) {
            log.error("关注的社团列表->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求关注的社团列表失败:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "我的社团", notes = "根据userId我的社团信息\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/authApi/myGroup", method = RequestMethod.POST)
    public Result<List<Group>> myGroup(@RequestBody UserUIDto userUIDto) throws Exception {
        Result<List<Group>> result = new Result<List<Group>>();
        try {
            log.debug("POST请求 我的社团列表");
            UserInfo current = getCurrentUser(userUIDto);
            userUIDto.setUserId(current.getUserId());
            List<Group> myGroupList = userService.getMyGroupListByUserId(userUIDto, 3);

            PageInfo<Group> pageInfo = new PageInfo<>(myGroupList);
            if(userUIDto.getPage()>pageInfo.getPages()){
                result.setMsg(Result.NO_DATA);
                result.setResultCode(Constants.DATA_NO);
            }else{
                result.setObj(myGroupList);
                result.setResultCode(Result.SUCCESS);
            }
            return result;
        } catch (UnknownLoginException ue) {
            log.error("我的社团列表->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求 我的社团列表失败:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "关注的活动", notes = "根据userId获取关注的活动信息\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/authApi/concernEvent", method = RequestMethod.POST)
    public Result<List<Event>> concernEvent(@RequestBody UserUIDto userUIDto) throws Exception {
        Result<List<Event>> result = new Result<List<Event>>();

        try {
            log.debug("POST请求 关注的活动列表");
            UserInfo current = getCurrentUser(userUIDto);
            List<Event> concernEventList = userService.getConcernEventListByUserId(userUIDto, current);

            PageInfo<Event> pageInfo = new PageInfo<>(concernEventList);
            if(userUIDto.getPage()>pageInfo.getPages()){
                result.setMsg(Result.NO_DATA);
                result.setResultCode(Constants.DATA_NO);
            }else{
                result.setObj(concernEventList);
                result.setResultCode(Result.SUCCESS);
            }
            return result;
        } catch (UnknownLoginException ue) {
            log.error("关注的活动列表->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求 关注的活动列表失败:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "我的活动", notes = "根据userId获取我的活动信息\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/authApi/myEvent", method = RequestMethod.POST)
    public Result<List<Event>> myEvent(@RequestBody UserUIDto userUIDto) throws Exception {
        Result<List<Event>> result = new Result<List<Event>>();
        try {
            log.debug("POST请求 我的活动列表");
            UserInfo current = getCurrentUser(userUIDto);
            userUIDto.setUserId(current.getUserId());
            List<Event> myEventList = userService.getMyEventListByUserId(userUIDto, null);

            PageInfo<Event> pageInfo = new PageInfo<>(myEventList);
            if(userUIDto.getPage()>pageInfo.getPages()){
                result.setMsg(Result.NO_DATA);
                result.setResultCode(Constants.DATA_NO);
            }else{
                result.setObj(myEventList);
                result.setResultCode(Result.SUCCESS);
            }
            return result;
        } catch (UnknownLoginException ue) {
            log.error("我的活动列表->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求 我的活动列表失败:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "忘记密码", notes = "忘记密码\n" +
            "返回值：0代表失败，1代表成功，21代表密码为空，22代表密码错误，31代表验证码为空，32代表验证码错误")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public Result updatePassword(@RequestBody UserRegisterDto userRegisterDto) throws Exception {
        Result result = new Result();
        try {
            log.debug("POST请求 修改密码");
            return userService.updatePassword(userRegisterDto);
        } catch (Exception e) {
            log.error("用户请求 修改密码失败:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "绑定手机", notes = "绑定手机\n" +
            "返回值：-1代表未登录，0代表失败，1代表成功")
    @RequestMapping(value = "/authApi/bindingMobile", method = RequestMethod.POST)
    public Result bindingMobile(@RequestBody UserMobileDto userMobileDto) throws Exception {
        Result result = new Result();
        try {
            log.debug("POST请求 绑定手机");
            UserInfo current = getCurrentUser(userMobileDto);
            userMobileDto.setUserId(current.getUserId());
            return userService.bindingMobile(userMobileDto);
        } catch (UnknownLoginException ue) {
            log.error("绑定手机->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求 绑定手机失败:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "用户反馈接口", notes = "用户反馈接口-暂时没有其他功能")
    @RequestMapping(value = "/authApi/feedback", method = RequestMethod.POST)
    public Result feedback(@RequestBody UserUIDto userUIDto) throws Exception {
        Result result = new Result();
        try {
            log.debug("POST请求 用户反馈接口");
            UserInfo current = getCurrentUser(userUIDto);
            result.setResultCode(Result.SUCCESS);
        } catch (UnknownLoginException ue) {
            log.error("用户反馈接口:",ue);
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求 用户反馈接口:" , e);
            result.setMsg("提交反馈失败");
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "活动标签--Wang", notes = "根据userId获取我的活动信息，审核通过的\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功  author：王小龙")
    @RequestMapping(value = "/authApi/myEvent/list", method = RequestMethod.POST)
    public Result<List<Event>> eventTags(@RequestBody UserUIDto userUIDto) throws Exception {
        Result<List<Event>> result = new Result<List<Event>>();
        try {
            log.debug("POST请求 我的活动标签");
            UserInfo current = getCurrentUser(userUIDto);
            userUIDto.setUserId(current.getUserId());
            List<Event> myEventList = userService.getMyEventTagsByUserId(userUIDto,1);

            result.setObj(myEventList);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (UnknownLoginException ue) {
            log.error("我的活动标签->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求 我的活动标签失败:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "社团标签--Wang", notes = "根据userId我的社团标签\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功")
    @RequestMapping(value = "/authApi/myGroup/list", method = RequestMethod.POST)
    public Result<List<Group>> groupTags(@RequestBody UserUIDto userUIDto) throws Exception {
        Result<List<Group>> result = new Result<List<Group>>();
        try {
            log.debug("POST请求 我的社团标签");
            UserInfo current = getCurrentUser(userUIDto);
            userUIDto.setUserId(current.getUserId());
            List<Group> myGroupList = userService.getMyGroupTagsByUserId(userUIDto, 1);

            result.setObj(myGroupList);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (UnknownLoginException ue) {
            log.error("我的社团标签->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求 我的社团标签失败:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "用户收藏列表-乐传阳", notes = "用户收藏列表\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功")
    @RequestMapping(value = "/authApi/favList", method = RequestMethod.POST)
    public Result favList(@RequestBody MyTopicParam myTopicParamy) throws Exception {
        Result<List<TopicVo>> result = new Result<List<TopicVo>>();
        try {
            log.debug("POST请求 我的话题列表");
            //UserInfo current = getCurrentUser(myTopicParamy);
            //myTopicParamy.setUserId(current.getUserId());
            if(myTopicParamy.getUserId() == null){
                result.setMsg(Result.NOT_LOGGED_IN);
                result.setResultCode(Constants.UNKNOWN_LOGIN);
                return result;
            }
            PageInfo<Topic> pageInfo =myFavTopicService.selectMyFavTopics(myTopicParamy);
            //PageInfo<TopicVo> pageInfo = new PageInfo<>(list);
            if(pageInfo.getList() == null || pageInfo.getList().size() == 0 || myTopicParamy.getPage()>pageInfo.getPages() ){
                result.setMsg(Result.NO_DATA);
                result.setResultCode(Constants.DATA_NO);
            }else{
                result.setObj(myFavTopicService.changeListToVo(pageInfo.getList(),myTopicParamy));
                result.setResultCode(Result.SUCCESS);
            }
            return result;
        } catch (UnknownLoginException ue) {
            result.setMsg(Result.NOT_LOGGED_IN);
            log.error("我的图文列表->用户未登录:");
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            result.setMsg(Result.SERVER_EXCEPTION);
            log.error("用户请求 我的图文列表失败:" , e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "通过userId查询user的topic列表-乐传阳", notes = "通过userId查询user的topic列表\n" +
            "参数：需查询的人的id：userId|当前用户的id：myUserId；返回值：-1代表没有登陆，0代表失败，1代表成功")
    @RequestMapping(value = "/authApi/userTopicList", method = RequestMethod.POST)
    public Result userTopicList(@RequestBody MyTopicParam myTopicParamy) throws Exception {
        Result<List<TopicsVo>> result = new Result<>();
        try {
            log.debug("POST请求 话题列表");
//            UserInfo current = getCurrentUser(myTopicParamy);
//            myTopicParamy.setMyUserId(current.getUserId());
            PageInfo<Topic> pageInfo =myFavTopicService.selectTopicsByUserId(myTopicParamy);
            //PageInfo<TopicVo> pageInfo = new PageInfo<>(list);
            if( pageInfo.getList() == null || pageInfo.getList().size() == 0 || myTopicParamy.getPage()>pageInfo.getPages() ){
                result.setResultCode(Constants.DATA_NO);
                result.setMsg(Result.NO_DATA);
            }else{
                result.setObj(myFavTopicService.changeUserTopicListToVo(pageInfo.getList(),myTopicParamy));
                result.setResultCode(Result.SUCCESS);
            }
            return result;
        } catch (Exception e) {
            result.setMsg(Result.SERVER_EXCEPTION);
            log.error("用户请求 话题列表:" , e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "我的图文列表-乐传阳", notes = "我的图文列表\n" +
            "（参数为userId 必传）" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功,ifLike 0:未点赞  1：已点赞")
    @RequestMapping(value = "/authApi/myTopic/list", method = RequestMethod.POST)
    public Result topicList(@RequestBody MyTopicParam myTopicParamy) throws Exception {
        Result<List<MyTopicsVo>> result = new Result<List<MyTopicsVo>>();
        try {
            log.debug("POST请求 我的话题列表");
            UserInfo current = getCurrentUser(myTopicParamy);
            myTopicParamy.setUserId(current.getUserId());
            PageInfo<Topic> pageInfo =myFavTopicService.selectMyTopics(myTopicParamy);
            //PageInfo<Topic> pageInfo = new PageInfo<>(list);
            if(myTopicParamy.getPage()>pageInfo.getPages() || pageInfo.getList().size() == 0 || pageInfo.getList() == null){
                result.setResultCode(Constants.DATA_NO);
                result.setMsg("没有有效的数据");
            }else{
                result.setObj(myFavTopicService.changeMyListToVo(pageInfo.getList(),myTopicParamy));
                result.setResultCode(Result.SUCCESS);
            }
            return result;
        } catch (UnknownLoginException ue) {
            result.setMsg(Result.NOT_LOGGED_IN);
            log.error("我的图文列表->用户未登录:");
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求 我的图文列表失败:" , e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

    @ApiOperation(value = "删除我的图文-乐传阳", notes = "删除我的图文\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功")
    @RequestMapping(value = "/authApi/myTopic/delete", method = RequestMethod.POST)
    public Result topicDelete(@RequestBody DeleteMyTopicDto deleteMyTopicDto) throws Exception {
        Result result = new Result();
        try {
            log.debug("POST请求 删除我的图文");
            UserInfo current = getCurrentUser(deleteMyTopicDto);
            deleteMyTopicDto.setUserId(current.getUserId());
            if(myFavTopicService.checkIsChallenge(deleteMyTopicDto) == 1){
                result.setResultCode(Result.FAILURE);
                result.setMsg("不能删除擂主贴");
                return result;
            }
            myFavTopicService.deleteMyTopic(deleteMyTopicDto);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (UnknownLoginException ue) {
            log.error("我的图文列表->用户未登录:");
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求 我的图文列表失败:" , e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "我的奖励列表--Wang", notes = "我的奖励列表\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功")
    @RequestMapping(value = "/authApi/myPrize/list", method = RequestMethod.POST)
    public Result<PrizeListVo> prizeList(@RequestBody ContextDto contextDto) throws Exception {
        Result<PrizeListVo> result = new Result<>();
        try {
            log.debug("我的奖励列表");
            UserInfo userInfo = getCurrentUser(contextDto);
            PrizeListVo prizeListVo =  prizeService.prizeList(userInfo);
            result.setObj(prizeListVo);
            result.setResultCode(Result.SUCCESS);
        } catch (UnknownLoginException ue) {
            log.error("我的奖励列表->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求 我的奖励列表失败:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "我的门票列表--Wang", notes = "我的门票列表\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/authApi/ticket/list", method = RequestMethod.POST)
    public Result<TicketListVo> ticketList(@RequestBody ContextDto contextDto) throws Exception {
        Result<TicketListVo> result = new Result<>();
        TicketListVo ticketListVo = new TicketListVo();
        try {
            log.debug("我的门票列表");
            UserInfo userInfo = getCurrentUser(contextDto);
            PageInfo<TicketVo> pageInfo = ticketService.ticketList(contextDto,userInfo);
            if (contextDto.getPage() > pageInfo.getPages()) {
                result.setResultCode(Constants.DATA_NO);
            } else {
                ticketListVo.setTicketList(pageInfo.getList());
                result.setObj(ticketListVo);
                result.setResultCode(Result.SUCCESS);
            }
        } catch (UnknownLoginException ue) {
            log.error("我的门票列表->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求 我的门票列表失败:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "我的社团人员审核列表--Wang", notes = "我的社团人员审核列表\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/authApi/groupMember/list", method = RequestMethod.POST)
    public Result<List<GroupMemberVo>> groupMemberList(@RequestBody ContextDto contextDto) throws Exception {
        Result<List<GroupMemberVo>> result = new Result<>();
        try {
            log.debug("我的社团人员审核列表");
            UserInfo userInfo = getCurrentUser(contextDto);
            PageInfo<GroupMemberVo> pageInfo = presidentService.getGroupList(contextDto,userInfo);
            if (contextDto.getPage() > pageInfo.getPages()) {
                result.setResultCode(Constants.DATA_NO);
            } else {
                result.setObj(pageInfo.getList());
                result.setResultCode(Result.SUCCESS);
            }
        } catch (UnknownLoginException ue) {
            log.error("我的社团人员审核列表->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求 我的社团人员审核列表失败:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "我的活动人员审核列表--Wang", notes = "我的活动人员审核列表\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/authApi/eventMember/list", method = RequestMethod.POST)
    public Result<List<EventMemberVo>> eventMemberList(@RequestBody ContextDto contextDto) throws Exception {
        Result<List<EventMemberVo>> result = new Result<>();
        try {
            log.debug("我的活动人员审核列表");
            UserInfo userInfo = getCurrentUser(contextDto);
            PageInfo<EventMemberVo> pageInfo = presidentService.getEventList(contextDto,userInfo);
            if (contextDto.getPage() > pageInfo.getPages()) {
                result.setResultCode(Constants.DATA_NO);
            } else {
                result.setObj(pageInfo.getList());
                result.setResultCode(Result.SUCCESS);
            }
        } catch (UnknownLoginException ue) {
            log.error("我的活动人员审核列表->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求 我的活动人员审核列表失败:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "我的审核--Wang", notes = "我的审核\n" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功")
    @RequestMapping(value = "/authApi/confirm/count", method = RequestMethod.POST)
    public Result<Integer> confirmCount(@RequestBody ContextDto contextDto) throws Exception {
        Result<Integer> result = new Result<>();
        try {
            log.debug("我的审核");
            UserInfo userInfo = getCurrentUser(contextDto);
            int eventApplyCount = presidentService.getUntreatedEventApplyCount(userInfo.getUserId(), null);
            int groupApplyCount = presidentService.getUntreatedGroupApplyCount(userInfo.getUserId(), null);
            int applyCount = eventApplyCount + groupApplyCount;
            result.setObj(applyCount);
            result.setResultCode(Result.SUCCESS);
        } catch (UnknownLoginException ue) {
            log.error("我的审核->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求 我的审核 失败:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "修改昵称--Wang", notes = "根据userId将nickName保存" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功 author：王小龙")
    @RequestMapping(value = "/authApi/updateNikeName", method = RequestMethod.POST)
    public Result updateNikeName(@RequestBody UserUploadDto UserUploadDto) throws Exception {
        Result result = new Result();
        try {
            log.debug("POST请求修改昵称");
            UserInfo userInfo = getCurrentUser(UserUploadDto);
            Boolean flag = userService.nickNameUpdate(UserUploadDto, userInfo);
            if(flag){
                result.setResultCode(Result.SUCCESS);
            }else{
                result.setMsg("服务器异常");
                result.setResultCode(Result.FAILURE);
            }
            return result;
        } catch (UnknownLoginException ue) {
            log.error("修改昵称->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求 修改昵称失败:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "修改头像--Wang", notes = "根据userId将avatarUrl保存" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功 author：王小龙")
    @RequestMapping(value = "/authApi/updateAvatarUrl", method = RequestMethod.POST)
    public Result updateAvatarUrl(@RequestBody UserUploadDto UserUploadDto) throws Exception {
        Result result = new Result();
        try {
            log.debug("POST请求修改头像");
            UserInfo userInfo = getCurrentUser(UserUploadDto);
            Boolean flag = userService.avatarUpdate(UserUploadDto, userInfo);
            if(flag){
                result.setResultCode(Result.SUCCESS);
            }else{
                result.setMsg("服务器异常");
                result.setResultCode(Result.FAILURE);
            }
            return result;
        } catch (UnknownLoginException ue) {
            log.error("修改头像->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("用户请求 修改头像失败:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }


    @ApiOperation(value = "获取用户邀请码信息",notes = "获取用户邀请码信息,登录参数")
    @RequestMapping(value = "/authApi/getUserCode",method = RequestMethod.POST)
    public Result getUserCode(@RequestBody ContextDto contextDto){
        Result result=new Result();
        try {
            log.debug("POST请求 获取用户邀请码信息");
            UserInfo userInfo=getCurrentUser(contextDto);
            result.setObj(userService.getUserCode(userInfo));
            result.setResultCode(Result.SUCCESS);
        } catch (UnknownLoginException ue) {
            log.error("获取用户邀请码信息>用户未登录:");
            result.setResultCode(Constants.UNKNOWN_LOGIN);
            result.setMsg(Result.NOT_LOGGED_IN);
        }catch (Exception e){
            log.error("获取用户邀请码信息:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return  result;
    }

    @ApiOperation(value = "判断是否为发奖人-王运豪",notes = "判断是否为发奖人\n" +
            "接收值：登录凭证 返回值：-1代表没有登陆，0代表失败，1代表成功，obj:1是 0 不是")
    @RequestMapping(value = "/authApi/isHasAwardPerson",method = RequestMethod.POST)
    public Result ishasAdmin(@RequestBody ContextDto contextDto){
        Result result=new Result();
        try {
            log.debug("POST请求 判断是否为发奖人");
            UserInfo userInfo=getCurrentUser(contextDto);
            return userService.isHasAwardPresen(userInfo);
        } catch (UnknownLoginException ue) {
            log.error("判断是否为发奖人>用户未登录:");
            result.setResultCode(Constants.UNKNOWN_LOGIN);
            result.setMsg(Result.NOT_LOGGED_IN);
        }catch (Exception e){
            log.error("用户请求 判断是否为发奖人:" , e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return  result;
    }

    @ApiOperation(value = "关注人的图文列表-乐传阳", notes = "关注人的图文列表\n" +
            "参数：登录状态，模糊内容：content" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功")
    @RequestMapping(value = "/authApi/followTopicList", method = RequestMethod.POST)
    public Result favList(@RequestBody UserFansTopicLisDto userFansTopicLisDto) throws Exception {
        Result<List<TopicVo>> result = new Result<List<TopicVo>>();
        try {
            log.debug("POST请求 关注人的图文列表");
            UserInfo current = getCurrentUser(userFansTopicLisDto);
            userFansTopicLisDto.setUserId(current.getUserId());
            PageInfo<Topic> pageInfo =userFansService.getFollowTopics(userFansTopicLisDto);
            //PageInfo<TopicVo> pageInfo = new PageInfo<>(list);
            if(pageInfo.getList() == null || pageInfo.getList().size() == 0 || userFansTopicLisDto.getPage()>pageInfo.getPages() ){
                result.setResultCode(Constants.DATA_NO);
                result.setMsg(Result.NO_DATA);
            }else{
                result.setObj(myFavTopicService.changeListToVo(pageInfo.getList(),userFansTopicLisDto));
                result.setResultCode(Result.SUCCESS);
            }
            return result;
        } catch (UnknownLoginException ue) {
            result.setMsg(Result.NOT_LOGGED_IN);
            log.error("我的图文列表->用户未登录:");
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            result.setMsg(Result.SERVER_EXCEPTION);
            log.error("用户请求 关注人的图文列表失败:" , e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "关注User-乐传阳", notes = "关注User\n" +
            "参数：登录状态，fansId：被关注的人的Id、 type：1：关注 2：取消关注" +
            "返回值：-1代表没有登陆，0代表失败，1代表成功")
    @RequestMapping(value = "/authApi/focus", method = RequestMethod.POST)
    public Result favList(@RequestBody UserFansDto userFans) throws Exception {
        Result<List<TopicVo>> result = new Result<List<TopicVo>>();
        try {
            log.debug("POST请求 关注");
            UserInfo current = getCurrentUser(userFans);
            userFans.setUserId(current.getUserId());
            UserFans u = new UserFans();
            u.setUserId(userFans.getUserId());
            u.setFansId(userFans.getFansId());
            if(userFans.getType() == 1){
                int flag = userFansService.FocusUser(u);
                if(flag == 0){
                    result.setMsg("用户已关注");
                    result.setResultCode(Result.FAILURE);
                    return result;
                }
                result.setMsg("关注成功");
                result.setResultCode(Result.SUCCESS);
            }
            if(userFans.getType() == 2){
                userFansService.removeFocus(u);
                result.setMsg("取消关注");
                result.setResultCode(Result.SUCCESS);
            }
        } catch (UnknownLoginException ue) {
            result.setMsg(Result.NOT_LOGGED_IN);
            log.error("请求关注->用户未登录:");
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            result.setMsg(Result.SERVER_EXCEPTION);
            log.error("用户请求 关注失败:" , e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }
}