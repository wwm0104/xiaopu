package com.chinaxiaopu.xiaopuMobi.controller.topic;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.controller.ContextController;
import com.chinaxiaopu.xiaopuMobi.dto.GroupMemberConfirmAllDto;
import com.chinaxiaopu.xiaopuMobi.dto.GroupMemberConfirmDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicGroupDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.TopicUserInfoDto;
import com.chinaxiaopu.xiaopuMobi.model.Event;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.president.PresidentService;
import com.chinaxiaopu.xiaopuMobi.service.topic.TopicGropuService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ycy on 2016/11/12.
 */

@Slf4j
@RestController
@RequestMapping("/topicGroup")
public class TopicGroupController extends ContextController {

    @Autowired
    private TopicGropuService topicGropuService;

    @Autowired
    private PresidentService presidentService;

    @ApiOperation(value = "获取社团活动列表-乐传阳", notes = "参数：groupId\n" +
            "返回值：0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/eventList", method = RequestMethod.POST)
    public Result<List<Event>> getEventListInGroup(@RequestBody TopicGroupDto topicGroupDto) {
        Result<List<Event>> result = new Result<>();
        try {
            List list = topicGropuService.selectTopicEventInGroup(topicGroupDto);
            PageInfo<Event> pageInfo = new PageInfo<>(list);
            if (topicGroupDto.getPage() > pageInfo.getPageNum() || list == null || list.size() == 0) {
                result.setResultCode(Constants.DATA_NO);
                result.setMsg("没有数据");
            } else {
                result.setObj(list);
                result.setResultCode(Result.SUCCESS);
            }
            return result;
        } catch (Exception e) {
            log.error("POST请求  获取社团活动列表", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }


    @ApiOperation(value = "获取社团人员列表-乐传阳", notes = "参数：groupId\n,status 1:审核通过 2：待审核" +
            "返回值：0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/groupMembers", method = RequestMethod.POST)
    public Result<List<TopicUserInfoDto>> selectNotReviewMembersInGroup(@RequestBody TopicGroupDto topicGroupDto) {
        Result<List<TopicUserInfoDto>> result = new Result<>();
        List<TopicUserInfoDto> list = new ArrayList<TopicUserInfoDto>();
        try {
            if (topicGroupDto.getStatus() == 2) {
                list = topicGropuService.selectNotReviewMembersInGroup(topicGroupDto);
            } else if (topicGroupDto.getStatus() == 1) {
                list = topicGropuService.selectReadyReviewMembersInGroup(topicGroupDto);
            }
            PageInfo<TopicUserInfoDto> pageInfo = new PageInfo<>(list);
            if (topicGroupDto.getPage() > pageInfo.getPageNum() || list == null || list.size() == 0) {
                result.setResultCode(Constants.DATA_NO);
                result.setMsg("没有数据");
            } else {
                result.setResultCode(Result.SUCCESS);
                result.setObj(list);
            }
            return result;
        } catch (Exception e) {
            log.error("POST请求未审核人员列表", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }


    @ApiOperation(value = "审核参加社团申请-乐传阳", notes = "审核参加社团申请\n 需登录" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/groupMember/confirm", method = RequestMethod.POST)
    public Result confirmGroupList(@RequestBody GroupMemberConfirmDto groupMemberConfirmDto) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            UserInfo current = getCurrentUser(groupMemberConfirmDto);
            map.put("groupId", groupMemberConfirmDto.getGroupId());
            map.put("memberId", current.getUserId());
            int check = topicGropuService.checkPresident(map);
            if (check == 0) {
                result.setResultCode(Result.FAILURE);
                log.debug("该用户不是本社社长");
                result.setMsg("用户不是该社社长");
                return result;
            }
            log.debug("POST请求 审核参加社团申请");
            groupMemberConfirmDto.setUserId(current.getUserId());
            int count = presidentService.confirmGroupMember(groupMemberConfirmDto);
            if (count == 1) {
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




    @ApiOperation(value = "批量审核参加社团申请-武宁", notes = "批量审核参加社团申请\n 需登录" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/groupMember/confirmAll", method = RequestMethod.POST)
    public Result confirmGroupListAll(@RequestBody GroupMemberConfirmAllDto groupMemberConfirmAllDto) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            UserInfo current = getCurrentUser(groupMemberConfirmAllDto);
            map.put("groupId", groupMemberConfirmAllDto.getGroupId());
            map.put("memberId", current.getUserId());
            int check = topicGropuService.checkPresident(map);
            if (check == 0) {
                result.setResultCode(Result.FAILURE);
                log.debug(" 该用户不是本社社长");
                result.setMsg("用户不是该社社长");
                return result;
            }
            log.debug("POST请求 批量审核参加社团申请");
            int len = groupMemberConfirmAllDto.getMemberIds().length;
            int count=0;
            if(len>0){
                GroupMemberConfirmDto groupMemberConfirmDto =new GroupMemberConfirmDto();
                groupMemberConfirmDto.setGroupId(groupMemberConfirmAllDto.getGroupId());
                groupMemberConfirmDto.setStatus(groupMemberConfirmAllDto.getStatus());
                groupMemberConfirmDto.setUserId(current.getUserId());
                for(Integer memberId : groupMemberConfirmAllDto.getMemberIds()){
                    groupMemberConfirmDto.setMemberId(memberId);
                    count = presidentService.confirmGroupMember(groupMemberConfirmDto);
                }
            }
            if (count == 1) {
                result.setResultCode(Result.SUCCESS);
            }
            return result;
        } catch (Exception e) {
            log.error("POST请求  批量审核参加社团申请 fail", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

}

