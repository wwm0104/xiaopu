package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.model.School;
import com.chinaxiaopu.xiaopuMobi.service.ReviewedGroupService;
import com.chinaxiaopu.xiaopuMobi.service.SchoolService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by liuwei
 * date: 16/9/29
 */

@Slf4j
@RestController
@RequestMapping("/school")
public class SchoolController extends ContextController{
	
	@Autowired
	private SchoolService schoolService;
    @Autowired
    private ReviewedGroupService reviewedGroupService;

    @ApiOperation(value = "学校列表", notes = "获取所有学校列表数据\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value="/list", method = RequestMethod.POST)
    public Result<List<School>> list() throws Exception {
        Result<List<School>> result= new Result<List<School>>();
        try {
            log.debug("POST请求 学校列表");
            result.setObj(reviewedGroupService.selectAllSchoolNameAndId());
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
        	log.error("POST请求 学校列表失败",e);
        	result.setResultCode(Result.FAILURE);
        }

        return result;
    }
}
