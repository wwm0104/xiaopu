package com.chinaxiaopu.xiaopuMobi.controller.admin;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.GroupListDto;
import com.chinaxiaopu.xiaopuMobi.model.Group;
import com.chinaxiaopu.xiaopuMobi.service.GroupService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liuwei
 * date: 16/10/12
 */
@Slf4j
@Controller
@RequestMapping("/admin/group")
public class GroupComtroller {

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "group/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<Group>> list(@RequestBody GroupListDto groupListDto) {
        Result<PageInfo<Group>> result = new Result<PageInfo<Group>>();
        try {
            result.setObj(groupService.getListPageByGroup(groupListDto));
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("P请求 社团列表失败",e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

}
