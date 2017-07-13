package com.chinaxiaopu.xiaopuMobi.mapper;

import java.util.List;

import com.chinaxiaopu.xiaopuMobi.dto.GroupDto;
import com.chinaxiaopu.xiaopuMobi.model.*;

public interface ReviewedGroupMapper {

    /**
     * 查询社团审核列表
     * 乐传阳
     *
     * @return
     */
    List<GroupDto> selectReviewedGroupList(GroupDto group);

    /**
     * 查看审核社团详情
     * ycy
     *
     * @param group
     * @return
     */
    Group selectReviewedGroupDetails(Group group);


    /**
     * 社团审核
     * ycy
     *
     * @param group
     */
    void reviewGroup(Group group);


    /**
     * 查询未认领社团
     * ycy
     *
     * @param group
     * @return
     */
    List<Group> selectUnclaimedGroupList(Group group);


    /**
     * 删除社团
     * ycy
     *
     * @param list
     * @return
     */
    int deleteGroup(List<Long> list);


    List<Group> selectAllGroupBySchoolId(Group group);

    /**
     * 查看社团详情
     * ycy
     *
     * @param group
     * @return
     */
    Group selectGroupDetails(Group group);


    int updateByGroupMember(GroupMember groupMember);

    int updateUserInfoById(UserInfo userInfo);

    int insertPresidentRoleId(UserRole userRole);

    Long selectRoleNum(UserRole userRole);

//	/**
//	 * 审核页面查询所有学校名称
//	 * ycy
//	 * @return
//	 */
//	List<School> selectAllSchoolNameAndId();
}
