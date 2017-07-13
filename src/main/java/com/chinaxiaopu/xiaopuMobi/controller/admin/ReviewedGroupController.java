package com.chinaxiaopu.xiaopuMobi.controller.admin;

import java.util.List;

import com.chinaxiaopu.xiaopuMobi.config.ShiroConfig;
import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.dto.GroupDto;
import com.chinaxiaopu.xiaopuMobi.dto.ReviewedGroupDto;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.chinaxiaopu.xiaopuMobi.security.realm.ShiroUser;
import com.chinaxiaopu.xiaopuMobi.service.GroupService;
import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.service.AdminPresidentService;
import com.chinaxiaopu.xiaopuMobi.service.ReviewedGroupService;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ycy on 10/9/16.
 */
@Slf4j
@Controller
@RequestMapping("/admin/reviewgroup")
public class ReviewedGroupController {

    @Autowired
    private ReviewedGroupService reviewedGroupService;

    @Autowired
    private AdminPresidentService adminPresidentService;

    @Autowired
    private GroupService groupService;

    /**
     * 进入审核列表页
     * @param model
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String reviewList(Model model) {
        try {
            List<School> list = reviewedGroupService.selectAllSchoolNameAndId();
            model.addAttribute("schools",list);
        } catch (Exception e) {
            log.error("查询学校列表失败", e);
        }
        return "group/reviewList";
    }

    /**
     * 首页跳转列表页面
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/goreviewList", method = RequestMethod.GET)
    public String goreviewList(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("status", 2);
        try {
            List<School> list = reviewedGroupService.selectAllSchoolNameAndId();
            model.addAttribute("schools",list);
        } catch (Exception e) {
            log.error("查询学校列表失败", e);
        }
        return "group/reviewList";
    }

    /**
     * 跳转未认领社团列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/unclaimed", method = RequestMethod.GET)
    public String unclaimedGroupList(Model model) {
        try {
            List<School> list = reviewedGroupService.selectAllSchoolNameAndId();
            model.addAttribute("schools",list);
        } catch (Exception e) {
            log.error("查询学校列表失败", e);
        }
        return "group/unclaimedGroupList";
    }

    /**
     * 跳转社团创建页面
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String creat(Model model, HttpServletRequest request, HttpServletResponse response) {
        try {
            model.addAttribute("imgsDomain", SystemConfig.getInstance().getImgsDomain());
            List<School> list = reviewedGroupService.selectAllSchoolNameAndId();
            model.addAttribute("schools",list);
            List<GroupCategory> list1 = groupService.getCategoryList();
            model.addAttribute("categorys",list1);
        } catch (Exception e) {
            log.error("进入社团创建页面失败", e);
        }
        return "group/createGroup";
    }

    /**
     * 跳转社长列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/presidentList", method = RequestMethod.GET)
    public String president(Model model) {
        try {
            List<School> list = reviewedGroupService.selectAllSchoolNameAndId();
            model.addAttribute("schools",list);
        } catch (Exception e) {
            log.error("查询学校列表失败", e);
        }
        return "group/presidentList";
    }

    /**
     * 获取审核社团的列表
     *
     * @param groupListDto
     * @return
     */
    @RequestMapping(value = "/reviewList", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<GroupDto>> groupList(@RequestBody GroupDto groupListDto) {
        Result<PageInfo<GroupDto>> result = new Result<PageInfo<GroupDto>>();
        try {
            result.setObj(reviewedGroupService.selectReviewedGroupList(groupListDto));
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("获取审核社团列表失败", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

    /**
     * 获取所有学校名称
     *
     * @return
     */
    @RequestMapping(value = "/schoolList", method = RequestMethod.POST)
    @ResponseBody
    public Result<List<School>> schoolList() {
        Result<List<School>> result = new Result<List<School>>();
        try {
            List<School> list = reviewedGroupService.selectAllSchoolNameAndId();
            if (list != null) {
                result.setObj(list);
            }
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("获取所有学校列表失败", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }


    /**
     * 社团审核
     *
     * @param groupListDto
     * @return
     */
    @RequestMapping(value = "/reviewGroup", method = RequestMethod.POST)
    @ResponseBody
    public Result reviewGroup(@RequestBody ReviewedGroupDto groupListDto) {
        Result result = new Result();
        try {
            //获取当前登陆用户
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            Integer userId = shiroUser.getId();
            groupListDto.setUserId(userId);
            reviewedGroupService.reviewGroup(groupListDto);
            result.setResultCode(Result.SUCCESS);

        } catch (Exception e) {
            log.error("社团审核失败", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }


    /**
     * 获取未认领社团的列表
     *
     * @param groupListDto
     * @return
     */
    @RequestMapping(value = "/unclamiledList", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<Group>> unclamiledgroupList(@RequestBody Group groupListDto) {
        Result<PageInfo<Group>> result = new Result<PageInfo<Group>>();

        try {
            result.setObj(reviewedGroupService.selectUnclaimedGroupList(groupListDto));
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("获取未认领社团列表失败", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

    /**
     * 获取社长的列表
     *
     * @param president
     * @return
     */
    @RequestMapping(value = "/getPresidentList", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<President>> getPresidentList(@RequestBody President president) {
        Result<PageInfo<President>> result = new Result<PageInfo<President>>();

        try {
            result.setObj(adminPresidentService.presidentList(president));
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("获取社长列表失败", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

    /**
     * 删除未认领社团
     * @param groupListDto
     * @return
     */
    @RequestMapping(value = "/deleteGroup", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteGroup(@RequestBody Group groupListDto) {
        Result result = new Result();
        try {
            reviewedGroupService.deleteGroup(groupListDto.getIdList());
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("删除社团失败", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

    /**
     * 创建社团
     * @param group
     * @return
     */
    @RequestMapping(value = "/creatGroup", method = RequestMethod.POST)
    @ResponseBody
    public Result creatGroup(@RequestBody Group group) {
        Result result = new Result();
        try {
            group.setPresidentId(0);
            group.setPresidentName("校谱官方");
            reviewedGroupService.createGroup(group);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("创建社团失败", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

    /**
     * 获取所有社团列表（用于下拉框）
     * @param group
     * @return
     */
    @RequestMapping(value = "/groupNameList", method = RequestMethod.POST)
    @ResponseBody
    public Result<List<Group>> getAllGroupList(@RequestBody Group group) {
        Result<List<Group>> result = new Result<List<Group>>();
        try {
            List<Group> list = reviewedGroupService.selectAllGroupBySchoolId(group);
            if (list != null) {
                result.setObj(list);
            }
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("获取所有社团列表失败", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

    /**
     * 跳转页面详情
     * @param id
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/goGroupDetails/{id}", method = RequestMethod.GET)
    public String godetails(@PathVariable("id") final int id, Model model, HttpServletRequest request, HttpServletResponse response) {
        Group group = new Group();
        group.setId(id);
        Group group1 = null;
        try {
            group1 = reviewedGroupService.selectGroupDetails(group);
            if (group1.getContentImgs() != null) {
                String[] imgs = group1.getContentImgs().split(",");
                model.addAttribute("imgs", imgs);
            }
            model.addAttribute("group", group1);
            model.addAttribute("imgsDomain", SystemConfig.getInstance().getImgsDomain());
        } catch (Exception e) {
            log.error("获取活动详情", e);
        }
        return "group/groupDetails";
    }
}