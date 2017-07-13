package com.chinaxiaopu.xiaopuMobi.controller.topic;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.controller.ContextController;
import com.chinaxiaopu.xiaopuMobi.dto.topic.UserFanDto;
import com.chinaxiaopu.xiaopuMobi.exception.UnknownLoginException;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.topic.TopicDownService;
import com.chinaxiaopu.xiaopuMobi.vo.topic.UserFanCountsVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.UserFanListVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by hao on 2016/12/1.
 */
@Slf4j
@RestController
@RequestMapping("/userFan")
public class UserFanController extends ContextController {
    @Autowired
    private TopicDownService topicDownService;

    @ApiOperation(value = "我的粉丝列表-王运豪", notes = "我的粉丝列表\n" +
            "接收值：登录凭证+page+rows\t"+"返回值：0代表失败，1代表成功，41代表没有数据, -1代表用户未登录")
    @RequestMapping(value = "/authApi/myFanList",method = RequestMethod.POST)
    public Result<List<UserFanListVo>> getMyFanList(@RequestBody UserFanDto userFanDto){
        Result<List<UserFanListVo>> result=new Result<>();
        try {
            log.debug("Post请求 我的粉丝列表");
            //获取当前登录用户
            UserInfo userInfo=getCurrentUser(userFanDto);
            //获取粉丝列表
            List<UserFanListVo> userFanListVoList=topicDownService.getFanList(userFanDto,userInfo);
            PageInfo<UserFanListVo> pageInfo=new PageInfo<>(userFanListVoList);
            if (userFanDto.getPage() > pageInfo.getPageNum() || userFanListVoList ==null || userFanListVoList.size() ==0) {
                result.setResultCode(Constants.DATA_NO);
                result.setMsg("无数据");
                return result;
            } else {
                result.setResultCode(Result.SUCCESS);
                result.setObj(userFanListVoList);
            }
        } catch (UnknownLoginException ue) {
            log.error("我的粉丝列表->用户未登录");
            result.setResultCode(Constants.UNKNOWN_LOGIN);//-1：没有登录
            result.setMsg(Result.NOT_LOGGED_IN);
        }catch (Exception e){
            log.error("Post请求 我的粉丝列表失败",e);
            result.setResultCode(Result.FAILURE);//0失败
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

    @ApiOperation(value = "我的关注列表-王运豪", notes = "我的关注列表\n" +
            "接收值：登录凭证+page+rows\t"+"返回值：0代表失败，1代表成功，41代表没有数据, -1代表用户未登录")
    @RequestMapping(value = "/authApi/myFollowList",method = RequestMethod.POST)
    public Result<List<UserFanListVo>> getMyFollowList(@RequestBody UserFanDto userFanDto){
        Result<List<UserFanListVo>> result=new Result<>();
        try {
            log.debug("Post请求 我的关注列表");
            //获取当前登录用户
            UserInfo userInfo=getCurrentUser(userFanDto);
            //获取关注列表
            List<UserFanListVo> userFanListVoList=topicDownService.getFollowList(userFanDto,userInfo);
            PageInfo<UserFanListVo> pageInfo=new PageInfo<>(userFanListVoList);
            if (userFanDto.getPage() > pageInfo.getPageNum() || userFanListVoList ==null || userFanListVoList.size() ==0) {
                result.setResultCode(Constants.DATA_NO);
                result.setMsg("没有数据");
                return result;
            } else {
                result.setResultCode(Result.SUCCESS);
                result.setObj(userFanListVoList);
            }
        } catch (UnknownLoginException ue) {
            log.error("我的关注列表->用户未登录");
            result.setResultCode(Constants.UNKNOWN_LOGIN);//-1：没有登录
            result.setMsg(Result.NOT_LOGGED_IN);
        }catch (Exception e){
            log.error("Post请求 我的关注列表失败",e);
            result.setResultCode(Result.FAILURE);//0失败
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }
    @ApiOperation(value = "我的个人中心总数量-王运豪", notes = "我的个人中心总数量\n" +
            "接收值：登录凭证\t"+"返回值：0代表失败，1代表成功  -1代表用户未登录")
    @RequestMapping(value = "/authApi/myCountList",method = RequestMethod.POST)
    public Result<UserFanCountsVo> getMyCounts(@RequestBody UserFanDto userFanDto){
        Result<UserFanCountsVo> result=new Result<>();
        try {
            log.debug("Post请求 个人中心总数量");
            //获取当前登录用户
            UserInfo userInfo=getCurrentUser(userFanDto);
            result.setObj(topicDownService.getUserFanCounts(userInfo));
            result.setResultCode(Result.SUCCESS);
        }catch (UnknownLoginException ue) {
            log.error("我的个人中心总数量->用户未登录");
            result.setResultCode(Constants.UNKNOWN_LOGIN);//-1：没有登录
            result.setMsg(Result.NOT_LOGGED_IN);
        }catch (Exception e){
            log.error("Post请求 我的个人中心总数量失败",e);
            result.setResultCode(Result.FAILURE);//0失败
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }



    @ApiOperation(value = "粉丝列表-王运豪", notes = "粉丝列表\n" +
            "接收值：userId+page+rows\t"+"返回值：0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/fanList",method = RequestMethod.POST)
    public Result<List<UserFanListVo>> getFanList(@RequestBody UserFanDto userFanDto){
        Result<List<UserFanListVo>> result=new Result<>();
        try {
            log.debug("Post请求 粉丝列表");
            if(StringUtils.isEmpty(userFanDto.getUserId())){
                result.setMsg("用户id不能为空");
                result.setResultCode(Result.FAILURE);
                return result;
            }
            UserInfo userInfo=new UserInfo();
            userInfo.setUserId(userFanDto.getUserId());
            //获取粉丝列表
            List<UserFanListVo> userFanListVoList=topicDownService.getFanList(userFanDto,userInfo);
            PageInfo<UserFanListVo> pageInfo=new PageInfo<>(userFanListVoList);
            if (userFanDto.getPage() > pageInfo.getPageNum() || userFanListVoList ==null || userFanListVoList.size() ==0) {
                result.setResultCode(Constants.DATA_NO);
                result.setMsg("无数据");
                return result;
            } else {
                result.setResultCode(Result.SUCCESS);
                result.setObj(userFanListVoList);
            }
        }catch (Exception e){
            log.error("Post请求 粉丝列表失败",e);
            result.setResultCode(Result.FAILURE);//0失败
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }


    @ApiOperation(value = "关注列表-王运豪", notes = "关注列表\n" +
            "接收值：userId+page+rows\t"+"返回值：0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/followList",method = RequestMethod.POST)
    public Result<List<UserFanListVo>> getFollowList(@RequestBody UserFanDto userFanDto){
        Result<List<UserFanListVo>> result=new Result<>();
        try {
            log.debug("Post请求 关注列表");
            if(StringUtils.isEmpty(userFanDto.getUserId())){
                result.setMsg("用户id不能为空");
                result.setResultCode(Result.FAILURE);
                return result;
            }
            UserInfo userInfo=new UserInfo();
            userInfo.setUserId(userFanDto.getUserId());
            //获取关注列表
            List<UserFanListVo> userFanListVoList=topicDownService.getFollowList(userFanDto,userInfo);
            PageInfo<UserFanListVo> pageInfo=new PageInfo<>(userFanListVoList);
            if (userFanDto.getPage() > pageInfo.getPageNum() || userFanListVoList ==null || userFanListVoList.size() ==0) {
                result.setResultCode(Constants.DATA_NO);
                result.setMsg("没有数据");
                return result;
            } else {
                result.setResultCode(Result.SUCCESS);
                result.setObj(userFanListVoList);
            }
        } catch (Exception e){
            log.error("Post请求 关注列表失败",e);
            result.setResultCode(Result.FAILURE);//0失败
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }


    @ApiOperation(value = "个人中心总数量-王运豪", notes = "关注列表\n" +
            "接收值：userId\t"+"返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/countList",method = RequestMethod.POST)
    public Result<UserFanCountsVo> getCounts(@RequestBody UserFanDto userFanDto){
        Result<UserFanCountsVo> result=new Result<>();
        try {
            log.debug("Post请求 个人中心总数量");
            if(StringUtils.isEmpty(userFanDto.getUserId())){
                result.setMsg("用户id不能为空");
                result.setResultCode(Result.FAILURE);
                return result;
            }
            UserInfo userInfo=new UserInfo();
            userInfo.setUserId(userFanDto.getUserId());
            result.setObj(topicDownService.getUserFanCounts(userInfo));
            result.setResultCode(Result.SUCCESS);
        }catch (Exception e){
            log.error("Post请求 个人中心总数量失败",e);
            result.setResultCode(Result.FAILURE);//0失败
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }
}
