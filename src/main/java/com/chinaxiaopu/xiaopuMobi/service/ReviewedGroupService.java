package com.chinaxiaopu.xiaopuMobi.service;

import java.util.*;

import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.GroupDto;
import com.chinaxiaopu.xiaopuMobi.dto.MsgFromDto;
import com.chinaxiaopu.xiaopuMobi.dto.ReviewedGroupDto;
import com.chinaxiaopu.xiaopuMobi.mapper.GroupMemberMapper;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinaxiaopu.xiaopuMobi.mapper.GroupMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.ReviewedGroupMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.SchoolMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.util.StringUtils;


/**
 * 社团审核service
 * @author ycy
 *
 */
/**
 * @author ycy
 *
 */
@Service
public class ReviewedGroupService {
	@Autowired
	private ReviewedGroupMapper reviewedGroupMapper;
	@Autowired
	private SchoolMapper  schoolMapper;
	@Autowired
	private GroupMapper groupMapper;
	@Autowired
	private GroupMemberMapper groupMemberMapper;
	@Autowired
	private MsgPushService msgPushService;//消息推送

	/**
	 * 查询审核社团列表
	 * @param group
	 * ycy
	 * @return
	 */
	public PageInfo<GroupDto> selectReviewedGroupList(GroupDto group) throws Exception{
		if (group.getPage() != null && group.getRows() != null) {
			PageHelper.startPage(group.getPage(), group.getRows());
		}
		List<GroupDto> list = reviewedGroupMapper.selectReviewedGroupList(group);
		PageInfo<GroupDto> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}


	/**
	 * 查看审核社团详情
	 * ycy
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public Group selectReviewedGroupDetails(Group group) throws Exception{
		Group group1 = reviewedGroupMapper.selectReviewedGroupDetails(group);
		return group1;
	}


	/**
	 * 社团审核
	 * @param group
	 * @throws Exception
	 */
	public void reviewGroup(ReviewedGroupDto group) throws Exception{
		GroupMember groupMember = new GroupMember();
		groupMember.setGroupId(group.getId());
		Group group1 = new Group();
		group1.setId(group.getId());
		Group group2 = reviewedGroupMapper.selectGroupDetails(group1);
		groupMember.setMemberId(group2.getPresidentId());
		groupMember.setJoinTime(new Date());

		//封装消息体
		int userId = group2.getPresidentId();
		Set<Integer> users = new HashSet<>();
		users.add(userId);
		int sender = group.getUserId();
		MsgFromDto msgFromDto = new MsgFromDto();
		msgFromDto.setGroupId(group2.getId());
		msgFromDto.setGroupName(group2.getName());
		msgFromDto.setRightImgUrl(group2.getLogoImgUrl());

		if(group.getStatus() == Constants.GROUP_STATUS_ON){
			//社团审核通过
			group.setCnt(group2.getCnt()+1);
			groupMember.setStatus(Constants.GROUP_STATUS_ON);
			groupMember.setIsPresident(1);
			UserInfo user = new UserInfo();
			user.setUserId(group2.getPresidentId());
			user.setValid(1);
			user.setIsPresident(1);
			UserRole userRole = new UserRole();
			userRole.setRoleId(Constants.GROUP_PRESIDENT_ROLE_ID);
			userRole.setUserId(group2.getPresidentId());
			Long num = reviewedGroupMapper.selectRoleNum(userRole);
			if(num == 0){
				//审核通过加入社长权限
				reviewedGroupMapper.insertPresidentRoleId(userRole);
			}
			reviewedGroupMapper.updateUserInfoById(user);

			//消息推送--社团审核：通过
			int action;
			if( !StringUtils.isEmpty(group2.getFurther()) && group2.getFurther().contains("{\"isCreate\":\"1\"}")){
				action = Constants.GROUP_CREATE_AUDIT_OK;
			} else {
				action = Constants.GROUP_CLAIM_AUDIT_OK;
			}

			msgPushService.sendPushMsgByUser(action,users,sender,msgFromDto);
		}else{
			//社团审核驳回
			groupMember.setStatus(3);
			groupMember.setIsPresident(2);

			//消息推送--社团审核：驳回
			int action;
			if( !StringUtils.isEmpty(group2.getFurther()) && group2.getFurther().contains("{\"isCreate\":\"1\"}")){
				action = Constants.GROUP_CREATE_AUDIT_NO;
			} else {
				action = Constants.GROUP_CLAIM_AUDIT_NO;
			}
			msgFromDto.setRemark(group.getMemo());
			msgPushService.sendPushMsgByUser(action,users,sender,msgFromDto);
		}
		reviewedGroupMapper.updateByGroupMember(groupMember);
		reviewedGroupMapper.reviewGroup(group);
	}


	/**
	 * 审核页面查询所有学校名称
	 * ycy
	 * @return
	 * @throws Exception
	 */
	public List<School> selectAllSchoolNameAndId() throws Exception{
		List<School> list = schoolMapper.selectAll();
		return list;
	}


	/**
	 * 查询未认领社团
	 * ycy
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public PageInfo<Group> selectUnclaimedGroupList(Group group) throws Exception{
		if (group.getPage() != null && group.getRows() != null) {
			PageHelper.startPage(group.getPage(), group.getRows());
		}
		List<Group> list = reviewedGroupMapper.selectUnclaimedGroupList(group);
		PageInfo<Group> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}



	/**
	 * 删除社团
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int deleteGroup(List<Long> list) throws Exception{
		int a = reviewedGroupMapper.deleteGroup(list);
		return a;
	}


	public int createGroup(Group group){
		if(group.getContentImgsArray() != null && group.getContentImgsArray().length > 0){
			group.setContentImgs(org.apache.commons.lang3.StringUtils.join(group.getContentImgs(),","));
		}
		group.setCnt(0);
		group.setJoinTime(new Date());
		return groupMapper.insert(group);
	}


	public  List<Group> selectAllGroupBySchoolId(Group group){
		return reviewedGroupMapper.selectAllGroupBySchoolId(group);
	}

	public Group selectGroupDetails(Group group) throws Exception{
		return reviewedGroupMapper.selectGroupDetails(group);
	}
}
