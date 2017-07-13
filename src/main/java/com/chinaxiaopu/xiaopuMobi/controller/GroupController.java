package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.*;
import com.chinaxiaopu.xiaopuMobi.dto.topic.GroupNameDto;
import com.chinaxiaopu.xiaopuMobi.exception.UnknownLoginException;
import com.chinaxiaopu.xiaopuMobi.model.Group;
import com.chinaxiaopu.xiaopuMobi.model.GroupCategory;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.GroupService;
import com.chinaxiaopu.xiaopuMobi.service.SchoolService;
import com.chinaxiaopu.xiaopuMobi.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Created by Wang on 10/9/16.
 */
@Slf4j
@RestController
@RequestMapping("/group")
public class GroupController extends ContextController{

	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userService;

	/**
	 * 查询社团列表
	 * @param groupListDto
	 * @return result
	 * */
	@ApiOperation(value = "社团列表", notes = "根据社团名称,获取社团列表数据\n" +
			"返回值：0代表失败，1代表成功，41代表没有数据")
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public Result<List<Group>> list(@RequestBody GroupListDto groupListDto) {
		Result<List<Group>> result = new Result<List<Group>>();
		try {
			log.debug("POST请求 社团列表");
			List<Group> groupList = groupService.getGroupListByGroup(groupListDto);
			PageInfo<Group> pageInfo = new PageInfo<>(groupList);
			if(groupListDto.getPage()>pageInfo.getPages()){
				result.setResultCode(Constants.DATA_NO);
				result.setMsg(Result.NO_DATA);
			}else{
				result.setObj(groupList);
				result.setResultCode(Result.SUCCESS);
			}
			return result;
		} catch (Exception e) {
			log.error("POST请求 社团列表失败",e);
			result.setMsg(Result.SERVER_EXCEPTION);
			result.setResultCode(Result.FAILURE);
		}
		return result;
	}

	/**
	 * 根据社团ID获取社团信息
	 * @param id
	 * */
	@ApiOperation(value = "社团信息", notes = "根据社团ID,获取社团列表数据\n" +
			"返回值：0代表失败，1代表成功")
	@RequestMapping(value="/groupInfo/{id}", method = RequestMethod.POST)
	public Result<Group> groupInfoById(@PathVariable("id") final int id) {
		Result<Group> result = new Result<Group>();
		try {
			log.debug("POST请求 社团信息");
			Group group = groupService.getGroupInfoByGroupId(id);
			if(StringUtils.isEmpty(group)){
				result.setMsg("获取社团信息失败");
				result.setResultCode(Result.FAILURE);
				return result;
			}

			group.setShareUrl(SystemConfig.getInstance().getWeixinDomain()+SystemConfig.getInstance().getGroupSharePage()+id);

			result.setObj(group);
            Integer userId = group.getPresidentId();
            if(!StringUtils.isEmpty(userId)){
                result.getObj().setUserInfo(userService.selectById(userId));
            }
            result.setResultCode(Result.SUCCESS);
            return result;
		} catch (Exception e) {
			log.error("POST请求社团信息失败",e);
			result.setMsg(Result.SERVER_EXCEPTION);
			result.setResultCode(Result.FAILURE);
		}
		return result;
	}

	@ApiOperation(value = "修改社团", notes = "社团信息修改提交\n" +
			"返回值：-1代表没有登陆，0代表失败，1代表成功")
	@RequestMapping(value="/authApi/update", method = RequestMethod.POST)
	public Result update(@RequestBody GroupCreateDto groupCreateDto) {
		Result result = new Result();
		try {
			log.debug("POST请求 社团信息修改提交");
			UserInfo userInfo = getCurrentUser(groupCreateDto);
			return groupService.groupUpdate(groupCreateDto, userInfo);
		} catch (UnknownLoginException ue) {
			result.setMsg(Result.NOT_LOGGED_IN);
			log.error("修改社团->用户未登录:");
			result.setResultCode(Constants.UNKNOWN_LOGIN);
		} catch (Exception e) {
			log.error("POST请求 社团信息修改提交失败",e);
			result.setMsg(Result.SERVER_EXCEPTION);
			result.setResultCode(Result.FAILURE);
		}
		return result;
	}

	@ApiOperation(value = "创建社团", notes = "创建社团\n" +
			"返回值：-1代表没有登陆，0代表失败，1代表成功，43代表社团名已存在")
	@RequestMapping(value="/authApi/create", method = RequestMethod.POST)
	public Result create(@RequestBody GroupCreateDto groupCreateDto) {
		Result result = new Result();
		try {
			log.debug("POST请求 社团创建");
			Boolean flag = groupService.isName(groupCreateDto.getSchoolId(),groupCreateDto.getName());
			if(!flag){
				result.setMsg("社团名已存在");
				result.setResultCode(Constants.GROUP_NAME_EXIST);
				return result;
			}
			UserInfo userInfo = getCurrentUser(groupCreateDto);

			groupCreateDto.setFurther("[{\"isCreate\":\"1\"}]");
			return groupService.saveGroup(groupCreateDto,userInfo);
		} catch (UnknownLoginException ue) {
			result.setMsg(Result.NOT_LOGGED_IN);
			log.error("创建社团->用户未登录:");
			result.setResultCode(Constants.UNKNOWN_LOGIN);
		} catch (Exception e) {
			log.error("POST请求 社团创建失败",e);
			result.setMsg(Result.SERVER_EXCEPTION);
			result.setResultCode(Result.FAILURE);
		}
		return result;
	}

	@ApiOperation(value = "初始化创建社团", notes = "初始化创建社团数据\n" +
			"返回值：-1代表没有登陆，0代表失败，1代表成功")
	@RequestMapping(value="/authApi/initCreate", method = RequestMethod.POST)
	public Result initCreate(@RequestBody GroupJoinDto GroupJoinDto) {
		Result result = new Result();
		try {
			UserInfo userInfo = getCurrentUser(GroupJoinDto);
			result.setObj(userInfo);
			result.setResultCode(Result.SUCCESS);
		} catch (UnknownLoginException ue) {
			result.setMsg(Result.NOT_LOGGED_IN);
			log.error("初始化创建社团->用户未登录:");
			result.setResultCode(Constants.UNKNOWN_LOGIN);
		} catch (Exception e) {
			log.error("POST请求 社团创建失败",e);
			result.setMsg(Result.SERVER_EXCEPTION);
			result.setResultCode(Result.FAILURE);
		}
		return result;
	}

	@ApiOperation(value = "社团认领页面初始化", notes = "社团认领页面初始化数据\n" +
			"返回值：-1代表没有登陆，0代表失败，1代表成功")
	@RequestMapping(value="/initClaim", method = RequestMethod.POST)
	public Result initClaim(@RequestBody GroupClaimDto groupClaimDto)  {
		Result<Group> result = new Result();
		try {
			log.debug("POST请求 社团认领页面初始化");
			Group group = groupService.getGroupInfoByGroupId(groupClaimDto.getGroupId());
			if(StringUtils.isEmpty(group)){
				result.setMsg("获取社团信息失败");
				result.setResultCode(Result.FAILURE);
				return result;
			}
			result.setObj(group);
			UserInfo userInfo = getCurrentUser(groupClaimDto);
			result.getObj().setUserInfo(userInfo);
			result.setResultCode(Result.SUCCESS);
		} catch (UnknownLoginException ue) {
			result.setMsg(Result.NOT_LOGGED_IN);
			log.error("社团认领页面初始化->用户未登录:");
			result.setResultCode(Constants.UNKNOWN_LOGIN);
		} catch (Exception e) {
			log.error("POST请求 社团认领页面初始化 失败",e);
			result.setMsg(Result.SERVER_EXCEPTION);
			result.setResultCode(Result.FAILURE);
		}
		return result;
	}

	@ApiOperation(value = "社团认领", notes = "社长认领社团\n" +
			"返回值：-1代表没有登陆，0代表失败，1代表成功")
	@RequestMapping(value="/authApi/claim", method = RequestMethod.POST)
	public Result claim(@RequestBody GroupClaimDto groupClaimDto) {
		Result result = new Result();
		try {
			log.debug("POST请求 社团认领");
			UserInfo userInfo = getCurrentUser(groupClaimDto);
			return groupService.groupClaim(groupClaimDto,userInfo);
		} catch (UnknownLoginException ue) {
			result.setMsg(Result.NOT_LOGGED_IN);
			log.error("社团认领->用户未登录:");
			result.setResultCode(Constants.UNKNOWN_LOGIN);
		} catch (Exception e) {
			log.error("POST请求 社团认领失败",e);
			result.setMsg(Result.SERVER_EXCEPTION);
			result.setResultCode(Result.FAILURE);
		}
		return result;
	}

	@ApiOperation(value = "加入社团", notes = "用户加入社团\n" +
			"返回值：-1代表没有登陆，0代表失败，1代表成功")
	@RequestMapping(value="/authApi/join", method = RequestMethod.POST)
	public Result join(@RequestBody GroupJoinDto groupJoinDto) {
		Result result = new Result();
		try {
			log.debug("POST请求 加入社团");
			UserInfo userInfo = getCurrentUser(groupJoinDto);
			return groupService.groupJoin(groupJoinDto,userInfo);
		} catch (UnknownLoginException ue) {
			result.setMsg(Result.NOT_LOGGED_IN);
			log.error("加入社团->用户未登录:");
			result.setResultCode(Constants.UNKNOWN_LOGIN);
		} catch (Exception e) {
			log.error("POST请求 加入社团失败",e);
			result.setMsg(Result.SERVER_EXCEPTION);
			result.setResultCode(Result.FAILURE);
		}
		return result;
	}

	@ApiOperation(value = "退出社团", notes = "用户退出社团\n" +
			"返回值：-1代表没有登陆，0代表失败，1代表成功")
	@RequestMapping(value="/authApi/exit", method = RequestMethod.POST)
	public Result exit(@RequestBody GroupExitDto groupExitDto) {
		Result result = new Result();
		try {
			log.debug("POST请求 退出社团");
            UserInfo userInfo = getCurrentUser(groupExitDto);
			return groupService.groupExit(groupExitDto,userInfo);
		} catch (UnknownLoginException ue) {
			result.setMsg(Result.NOT_LOGGED_IN);
			log.error("退出社团->用户未登录:");
			result.setResultCode(Constants.UNKNOWN_LOGIN);
		} catch (Exception e) {
			log.error("POST请求  退出社团失败",e);
			result.setMsg(Result.SERVER_EXCEPTION);
			result.setResultCode(Result.FAILURE);
		}
		return result;
	}

	@ApiOperation(value = "审核社团申请", notes = "审核社团申请，需要更新社团表、社团成员表、标记用户认证状态\n" +
			"返回值：-1代表没有登陆，0代表失败，1代表成功")
	@RequestMapping(value="/authApi/confirmGroupRequest", method = RequestMethod.POST)
	public Result confirmRequest(@ApiParam(required = true, name = "confirmDto", value = "operator,groupId必选") @RequestBody GroupConfirmDto confirmDto) {
		Result result = new Result();
		try {
			return groupService.confirmRequest(confirmDto);
		} catch (Exception e) {
			log.error("POST请求  审核社团申请失败",e);
			result.setMsg(Result.SERVER_EXCEPTION);
			result.setResultCode(Result.FAILURE);
		}
		return result;
	}

	@ApiOperation(value = "判断用户能否加入社团", notes = "判断用户能否加入社团，根据userId和groupId判断是否能够申请加入\n" +
			"返回值：-1代表没有登陆，0代表失败，1代表成功,2代表能申请加入，3代表已经加入，4代表是该社团社长，5代表审核中")
	@RequestMapping(value="/authApi/confirmUserRequest", method = RequestMethod.POST)
	public Result confirmUserRequest(@RequestBody GroupUIDto groupUIDto) {
		Result result = new Result();
		try {
			UserInfo userInfo = getCurrentUser(groupUIDto);
			groupUIDto.setUserId(userInfo.getUserId());
			return groupService.confirmUserRequest(groupUIDto);
		} catch (UnknownLoginException ue) {
			log.error("确认用户是否关注->用户未登录:");
			result.setMsg(Result.NOT_LOGGED_IN);
			result.setResultCode(Constants.UNKNOWN_LOGIN);
		} catch (Exception e) {
			log.error("POST请求  审核用户申请失败",e);
			result.setMsg(Result.SERVER_EXCEPTION);
			result.setResultCode(Result.FAILURE);
		}
		return result;
	}

    @ApiOperation(value = "社团分类", notes = "社团分类\n" +
			"返回值：0代表失败，1代表成功")
    @RequestMapping(value="/category", method = RequestMethod.POST)
    public Result<List<GroupCategory>> category() throws Exception {
        Result<List<GroupCategory>> result= new Result<List<GroupCategory>>();
        try {
            log.debug("POST请求 社团类型列表");
            result.setObj(groupService.getCategoryList());
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("POST请求 社团类型列表失败",e);
			result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "判断社团创建时，名字是否存在", notes = "判断社团创建时，名字是否存在\n" +
			"返回值：0代表失败，1代表成功，43代表社团名已存在")
    @RequestMapping(value="/isName", method = RequestMethod.POST)
    public Result isName(@RequestBody GroupNameDto groupNameDto) throws Exception {
        Result result= new Result();
        try {
            log.debug("POST请求 判断社团创建时，名字是否存在");
			Boolean flag = groupService.isName(groupNameDto.getSchoolId(),groupNameDto.getName());
			if(flag){
				result.setResultCode(Result.SUCCESS);
			} else {
				result.setMsg("社团名已存在");
				result.setResultCode(Constants.GROUP_NAME_EXIST);
			}
        } catch (Exception e) {
            log.error("POST请求 判断社团创建时，名字是否存在 失败",e);
			result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }
}