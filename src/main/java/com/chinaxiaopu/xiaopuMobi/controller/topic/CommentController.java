package com.chinaxiaopu.xiaopuMobi.controller.topic;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.controller.ContextController;
import com.chinaxiaopu.xiaopuMobi.dto.CommentDto;
import com.chinaxiaopu.xiaopuMobi.exception.UnknownLoginException;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.topic.TopicDownService;
import com.chinaxiaopu.xiaopuMobi.vo.topic.TopicCommentVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Wang on 2016/11/2.
 */

@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController extends ContextController{
    @Autowired
    private TopicDownService topicDownService;
    @ApiOperation(value = "用户评论列表-王运豪", notes = "用户评论列表\n" +
            "接收值：topicId+page+rows\t"+"返回值：0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<List<TopicCommentVo>> list(@RequestBody CommentDto commentDto) {
        Result<List<TopicCommentVo>> result=new Result<>();
        try {
            log.debug("Post请求 获取评论列表");
            //获取评论列表
            return topicDownService.getCommentList(commentDto);
        }catch (Exception e){
            log.error("获取评论列表失败",e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

    @ApiOperation(value = "用户评论图文内容-王运豪", notes = "用户评论图文内容\n" +
            "接收值：topicId+评论内容comment+登录凭证\t"+"返回值：0代表失败，1代表成功，-1代表没有登录")
    @RequestMapping(value = "/authApi/create", method = RequestMethod.POST)
    public Result create(@RequestBody CommentDto commentDto) {
        Result result=new Result();
        try {
            log.debug("Post请求 添加评论");
            //获取当前登录用户
            UserInfo userInfo=getCurrentUser(commentDto);
            //评论操作
            return topicDownService.doComment(commentDto,userInfo);
        } catch (UnknownLoginException ue) {
            log.error("用户评论失败->用户未登录");
            result.setResultCode(Constants.UNKNOWN_LOGIN);//-1：没有登录
            result.setMsg(Result.NOT_LOGGED_IN);
        }catch (Exception e){
            log.error("Post请求 用户评论失败",e);
            result.setResultCode(Result.FAILURE);//0失败
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

}