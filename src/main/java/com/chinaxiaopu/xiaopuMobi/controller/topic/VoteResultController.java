package com.chinaxiaopu.xiaopuMobi.controller.topic;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.controller.ContextController;
import com.chinaxiaopu.xiaopuMobi.dto.topic.PKResultDetailDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.PkIdDto;
import com.chinaxiaopu.xiaopuMobi.exception.UnknownLoginException;
import com.chinaxiaopu.xiaopuMobi.service.topic.TopicPkService;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.topic.VoteResultDetailService;
import com.chinaxiaopu.xiaopuMobi.vo.topic.*;
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
 * Created by Wang on 2016/11/2.
 */

@Slf4j
@RestController
@RequestMapping("/voteResult")
public class VoteResultController extends ContextController {
    @Autowired
    private VoteResultDetailService voteResultDetailService;

    @Autowired
    private TopicPkService topicPkService;

    @ApiOperation(value = "查看投票详情--Wang", notes = "查看投票详情\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<VoteResultVo> list(@RequestBody PkIdDto pkIdDto) {
        Result<VoteResultVo> result = new Result<>();
        try {
            log.debug("查看投票详情");
            UserInfo userInfo;
            try {
                userInfo = getCurrentUser(pkIdDto);//获取当前登录人信息
            } catch (UnknownLoginException ule) {
                userInfo = null;
            }

            VoteResultVo voteResultVo = topicPkService.voteList(pkIdDto,userInfo);
            result.setObj(voteResultVo);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("查看投票详情 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "PK投票结果 -武宁", notes = "PK投票结果\n  获取主题id" +
            "返回值：0代表失败，1代表成功 投票用户列表排序 ： userId:用户id;userName : 用户昵称;userImages : 用户头像； voteCnt : 投票数 ；isGet :是否获奖 0未获奖  1:获奖")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public Result view(@RequestBody PKResultDetailDto pKResultDetailDto) {
        Result result = new Result<>();
        try {
            log.debug("POST请求 PK投票结果");
            UserInfo userInfo;
            try {
                userInfo = getCurrentUser(pKResultDetailDto);//获取当前登录人信息
            } catch (UnknownLoginException ule) {
                userInfo = null;
            }
            if (!StringUtils.isEmpty(userInfo)) {
                pKResultDetailDto.setUserId(userInfo.getUserId());
            } else {
                pKResultDetailDto.setUserId(-1);//如果未登陆 赋值-1
            }
            List<PKResultDetailVo> list = voteResultDetailService.getPkDetail(pKResultDetailDto);
            PageInfo<PKResultDetailVo> pageInfo = new PageInfo<>(list);
            if (pKResultDetailDto.getPage() > pageInfo.getPageNum()) {
                result.setResultCode(Constants.DATA_NO);
            } else {
                result.setObj(list);
                result.setResultCode(Result.SUCCESS);
            }
            return result;
        } catch (Exception e) {
            log.error("POST请求  PK投票结果", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

}