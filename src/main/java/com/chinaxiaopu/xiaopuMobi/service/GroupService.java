package com.chinaxiaopu.xiaopuMobi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.*;
import com.chinaxiaopu.xiaopuMobi.mapper.*;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.github.pagehelper.PageHelper;

/**
 * @author Wang
 */
@Service
public class GroupService extends AbstractService{

    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private GroupMemberMapper groupMemberMapper;
    @Autowired
    private GroupCategoryMapper groupCategoryMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private SystemConfig systemConfig;

    public PageInfo<Group> getListPageByGroup(GroupListDto groupListDto) {

        Group group = new Group();
        group.setName(groupListDto.getGroupName());
        group.setCategoryId(groupListDto.getCategoryId());
        group.setStatus(groupListDto.getGroupStatus());
        group.setSchoolId(groupListDto.getSchoolId());

        if (!StringUtils.isEmpty(groupListDto.getPage()) && !StringUtils.isEmpty(groupListDto.getRows())) {
            PageHelper.startPage(groupListDto.getPage(), groupListDto.getRows());
        }
        List<Group> groupList = groupMapper.getGroupListByGroup(group);
        PageInfo<Group> pageInfo = new PageInfo<>(groupList);

        return pageInfo;
    }

    public List<Group> getGroupListByGroup(GroupListDto groupListDto) {

        Group group = new Group();
        group.setName(groupListDto.getGroupName());
        group.setCategoryId(groupListDto.getCategoryId());
        group.setStatus(groupListDto.getGroupStatus());
        group.setSchoolId(groupListDto.getSchoolId());

        if (!StringUtils.isEmpty(groupListDto.getPage()) && !StringUtils.isEmpty(groupListDto.getRows())) {
            PageHelper.startPage(groupListDto.getPage(), groupListDto.getRows());
        }
        List<Group> groupList = groupMapper.getGroupListByGroup(group);

        return groupList;
    }

    /**
     * 创建社团
     *
     * */
    @Transactional
    public Result saveGroup(GroupCreateDto groupCreateDto,UserInfo userInfo) {
        Result result = new Result();
        Group group = new Group();

        if(StringUtils.isEmpty(groupCreateDto.getLogoImgUrl())){
            result.setMsg("获取传入社团Logo失败");
            result.setResultCode(Result.FAILURE);
            return result;
        }

        group.setSchoolId(groupCreateDto.getSchoolId());
        group.setSchoolName(groupCreateDto.getSchoolName());
        group.setName(groupCreateDto.getName());
        group.setSlogan(groupCreateDto.getSlogan());
        group.setPresidentId(userInfo.getUserId());
        group.setPresidentName(userInfo.getRealName());
        group.setDescription(groupCreateDto.getDescription());
        group.setCategoryId(groupCreateDto.getCategoryId());
        group.setLogoImgUrl(groupCreateDto.getLogoImgUrl());
        group.setCategoryName(groupCreateDto.getCategoryName());
        group.setPosterImg(systemConfig.getGroupPosterImg());
        group.setContentImgs(org.apache.commons.lang3.StringUtils.join(groupCreateDto.getContentImgs(),","));
        group.setStatus(2);
        group.setCnt(0);
        group.setJoinTime(new Date());
        group.setFurther(groupCreateDto.getFurther());

        int count = groupMapper.insertSelective(group);
        if (count == 1) {
            result.setResultCode(Result.SUCCESS);
            //将用户添加到社团人员中
            GroupMember groupMember = new GroupMember();
            groupMember.setGroupId(group.getId());
            groupMember.setMemberId(group.getPresidentId());
            groupMember.setStatus(2);
            groupMember.setJoinTime(new Date());
            groupMember.setIsPresident(1);
            int memberCount = groupMemberMapper.insert(groupMember);
            if (memberCount == 1) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
            }

        } else {
            result.setMsg("创建社团失败");
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    public Group getGroupInfoByGroupId(Integer id) {
        return groupMapper.selectByPrimaryKey(id);
    }

    /**
     * 认领社团
     *
     * */
    @Transactional
    public Result groupClaim(GroupClaimDto groupClaimDto,UserInfo userInfo) {
        Result result = new Result();
        Group group = new Group();
        if (StringUtils.isEmpty(groupClaimDto.getGroupId())) {
            result.setMsg("获取传入社团Id失败");
            result.setResultCode(Result.FAILURE);
            return result;
        } else {
            group = groupMapper.selectByPrimaryKey(groupClaimDto.getGroupId());
        }
        if(StringUtils.isEmpty(group) || group.getStatus()!=Constants.GROUP_STATUS_DEF){
            result.setResultCode(group.getStatus());
            return result;
        }

        group.setPresidentId(userInfo.getUserId());
        group.setPresidentName(userInfo.getRealName());
        group.setJoinTime(new Date());
        group.setStatus(Constants.GROUP_STATUS_IN);

        int count = groupMapper.updateByPrimaryKey(group);
        if (count == 1) {
            //将用户添加到社团人员中
            GroupMember groupMember = new GroupMember();
            groupMember.setGroupId(group.getId());
            groupMember.setMemberId(group.getPresidentId());
            groupMember.setStatus(2);
            groupMember.setJoinTime(new Date());
            groupMember.setIsPresident(1);
            int memberCount = groupMemberMapper.insert(groupMember);
            if (memberCount == 1) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setMsg("社团人员关联失败");
                result.setResultCode(Result.FAILURE);
            }
        } else {
            result.setMsg("认领社团失败");
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 加入社团
     * */
    @Transactional
    public Result groupJoin(GroupJoinDto groupJoinDto,UserInfo userInfo) {
        Result result = new Result();
        GroupMember groupMember = new GroupMember();
        if (StringUtils.isEmpty(groupJoinDto.getGroupId())) {
            result.setMsg("获取传入社团Id失败");
            result.setResultCode(Result.FAILURE);
            return result;
        }

        groupMember.setGroupId(groupJoinDto.getGroupId());
        groupMember.setMemberId(userInfo.getUserId());
        groupMember = groupMemberMapper.selectByGroupMember(groupMember);

        //判断是否为该社团人员
        if (!StringUtils.isEmpty(groupMember)) {
            result.setMsg("已经加入该社团");
            result.setResultCode(Constants.USER_NOT_NULL);
            return result;
        }
        //是否以前已经申请过加入该社团
        groupMember = groupMemberMapper.selectStatusByGroupMember(groupJoinDto.getGroupId(), userInfo.getUserId(),3);
        int count;
        if(StringUtils.isEmpty(groupMember)){
            //若不是，插入一条数据
            groupMember = new GroupMember();
            groupMember.setGroupId(groupJoinDto.getGroupId());
            groupMember.setMemberId(userInfo.getUserId());
            groupMember.setStatus(2);
            groupMember.setJoinTime(new Date());

            count = groupMemberMapper.insertSelective(groupMember);
        }else{
            //若是，修改那条数据
            groupMember.setStatus(2);
            groupMember.setJoinTime(new Date());
            groupMember.setMemo(null);

            count = groupMemberMapper.updateStatusByGroupMember(groupMember);
        }

        if (count == 1) {
            result.setResultCode(Result.SUCCESS);
        } else {
            result.setMsg("加入该社团失败");
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 退出社团
     * */
    @Transactional
    public Result groupExit(GroupExitDto groupExitDto,UserInfo userInfo) {
        Result result = new Result();
        GroupMember groupMember = new GroupMember();
        if (StringUtils.isEmpty(groupExitDto.getGroupId())) {
            result.setMsg("获取传入社团Id失败");
            result.setResultCode(Result.FAILURE);
            return result;
        }

        groupMember.setGroupId(groupExitDto.getGroupId());
        groupMember.setMemberId(userInfo.getUserId());
        groupMember = groupMemberMapper.selectByGroupMember(groupMember);

        if (StringUtils.isEmpty(groupMember)) {
            result.setResultCode(Result.SUCCESS);
            return result;
        }

        groupMember.setStatus(3);
        int count = groupMemberMapper.updateByGroupMember(groupMember);
        if (count == 1) {
            result.setResultCode(Result.SUCCESS);
        } else {
            result.setMsg("退出该社团失败");
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 编辑社团
     * */
    public Result groupUpdate(GroupCreateDto groupCreateDto, UserInfo userInfo) {
        Result result = new Result();
        Group group = new Group();

        if (StringUtils.isEmpty(groupCreateDto.getGroupId())) {
            result.setMsg("获取传入社团Id失败");
            result.setResultCode(Result.FAILURE);
            return result;
        } else {
            group = groupMapper.selectByPrimaryKey(groupCreateDto.getGroupId());
        }
        //社团不存在 或 当前用户不是该社团的社长
        if(StringUtils.isEmpty(group) || !group.getPresidentId().equals(userInfo.getUserId())){
            result.setMsg("社团不存在 或 当前用户不是该社团的社长");
            result.setResultCode(Result.FAILURE);
            return result;
        }

        group.setSchoolId(groupCreateDto.getSchoolId());
        group.setSchoolName(groupCreateDto.getSchoolName());
        group.setName(groupCreateDto.getName());
        group.setSlogan(groupCreateDto.getSlogan());
        group.setDescription(groupCreateDto.getDescription());
        group.setCategoryId(groupCreateDto.getCategoryId());
        group.setCategoryName(groupCreateDto.getCategoryName());

        if(!StringUtils.isEmpty(groupCreateDto.getLogoImgUrl())){
            group.setLogoImgUrl(groupCreateDto.getLogoImgUrl());
        }
        String contentImgs = org.apache.commons.lang3.StringUtils.join(groupCreateDto.getContentImgs(),",");
        group.setContentImgs(contentImgs);

        int count = groupMapper.updateByPrimaryKeySelective(group);
        if (count == 1) {
            result.setResultCode(Result.SUCCESS);
        } else {
            result.setMsg("修改社团信息失败");
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 社团申请审核
     */
    @Transactional
    public Result confirmRequest(GroupConfirmDto confirmDto) {
        Result result = new Result();
        Group group;
        GroupMember groupMember;
        UserInfo userInfo;
        result.setResultCode(Result.FAILURE);
        if (StringUtils.isEmpty(confirmDto.getGroupId())) {
            result.setMsg("获取传入社团Id");
            return result;
        } else {
            group = groupMapper.selectByPrimaryKey(confirmDto.getGroupId());
            if(StringUtils.isEmpty(group) || group.getStatus()==1)
                return result;
            userInfo = userInfoMapper.selectByPrimaryKey(group.getPresidentId());
            if (StringUtils.isEmpty(userInfo))
                return result;
            //新增社长到社团成员表
            groupMember = new GroupMember();
            groupMember.setGroupId(group.getId());
            groupMember.setMemberId(group.getPresidentId());
            groupMember.setJoinTime(new Date());
            groupMember.setIsPresident(1);
            groupMember.setStatus(1);
            //更新社团状态
            group.setStatus(1);
            group.setCnt(group.getCnt() + 1);
            //更新用户信息状态
            userInfo.setValid(1);
            userInfo.setIsPresident(1);
        }

        int countGroup = groupMapper.updateByPrimaryKeySelective(group);
        int countGroupMember = groupMemberMapper.insert(groupMember);
        int countUserInfo = userInfoMapper.updateByPrimaryKeySelective(userInfo);
        if (countGroup == 1 && countGroupMember==1 && countUserInfo ==1 ) {
            result.setResultCode(Result.SUCCESS);
        } else {
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 根据社长user_id查询社长管理的社团
     */
    public List<Group> getManagedGroupsByUserId(Integer userId){
        List<Group> groups = new ArrayList<Group>();
        if (userId != null) {
            Group group = new Group();
            group.setPresidentId(userId);
            group.setStatus(1);
            groups = groupMapper.getGroupListByGroup(group);
        }
        return groups;
    }

    /**
     * 判断用户能否加入社团
     * @param groupUIDto
     * @return
     */
    public Result confirmUserRequest(GroupUIDto groupUIDto) {
        Result result = new Result();
        if(StringUtils.isEmpty(groupUIDto.getGroupId()) || StringUtils.isEmpty(groupUIDto.getUserId())){
            result.setMsg("获取传入社团Id和用户Id失败");
            result.setResultCode(Result.FAILURE);
            return result;
        }
        GroupMember groupMember = new  GroupMember();
        groupMember.setGroupId(groupUIDto.getGroupId());
        groupMember.setMemberId(groupUIDto.getUserId());
        //已加入
        groupMember.setStatus(1);
        int count1 = groupMemberMapper.selectByUserOpt(groupMember);
        //审核中
        groupMember.setStatus(2);
        int count2 = groupMemberMapper.selectByUserOpt(groupMember);
        if(count1>0){
            result.setMsg("已加入该社团");
            result.setResultCode(Result.UNAPPLY);
        }else if(count2>0){
            result.setMsg("已提交加入该社团申请，审核中");
            result.setResultCode(Result.isAudit);
        }else{
            result.setMsg("能加入该社团");
            result.setResultCode(Result.APPLY);
        }

        //是否是社长
        Group group = groupMapper.selectByPrimaryKey(groupUIDto.getGroupId());
        if(!StringUtils.isEmpty(group.getPresidentId())){
            if(group.getPresidentId().equals(groupUIDto.getUserId())){
                result.setMsg("为该社团社长");
                result.setResultCode(Result.isPresident);
            }
        }
        return result;
    }

    /**
     * 返回活动相关的社团列表
     * */
    public List<Group> getGroupsByEventId(Integer eventId){
        if(StringUtils.isEmpty(eventId)){
            return null;
        }
        return groupMapper.getGroupsByEventId(eventId);
    }

    /**
     * 获取社团类型列表
     * */
    public List<GroupCategory> getCategoryList(){
        return groupCategoryMapper.selectByExample(null);
    }

    public int isGroupMember(Integer groupId, Integer memberId, int status) {
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(groupId);
        groupMember.setMemberId(memberId);
        groupMember.setStatus(status);
        return groupMemberMapper.selectByUserOpt(groupMember);
    }

    public List<Group> confirmUserJoinEvent(Integer eventId,Integer userId) {
        return groupMapper.confirmUserJoinEvent(eventId,userId);
    }

    public Boolean isName(Integer schoolId,String name) {
        Boolean result = false;
        int count = groupMapper.selectBySchoolNameAndName(schoolId, name);
        if(count == 0){
            result = true;
        }
        return result;
    }
}