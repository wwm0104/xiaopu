package com.chinaxiaopu.xiaopuMobi.service;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.dto.partner.CreatePartnerDto;
import com.chinaxiaopu.xiaopuMobi.dto.partner.PartnerDetailsDto;
import com.chinaxiaopu.xiaopuMobi.dto.partner.PartnerEventDto;
import com.chinaxiaopu.xiaopuMobi.mapper.*;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.partner.GroupListVo;
import com.chinaxiaopu.xiaopuMobi.vo.partner.GroupPartnerVo;
import com.chinaxiaopu.xiaopuMobi.vo.partner.SchoolPartherPassVo;
import com.chinaxiaopu.xiaopuMobi.vo.partner.SchoolPartnerVo;
import com.chinaxiaopu.xiaopuMobi.vo.president.EventVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liuwei
 * date: 2016/10/21
 */
@Slf4j
@Service
public class PartnerService {

    @Autowired
    PartnerMapper partnerMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    InvitationCodeMapper invitationCodeMapper;
    @Autowired
    GroupMapper groupMapper;
    @Autowired
    PartnerGroupMapper partnerGroupMapper;
    @Autowired
    EventMapper eventMapper;




    /**
     * 添加合作伙伴
     *
     * @param partner
     * @return
     */
    public int insertPartner(Partner partner) {

        //根据手机号查询对应的学校信息
        partner.setJoinTime(new Date());
        partner.setType(1);     //设置为校园合伙人
        partner.setStatus(1);     //设置为待审核状态
        return partnerMapper.insertSelective(partner);
    }
    //校园
    public int selectCountByMobile(final Long mobile) {
        return partnerMapper.selectCountByMobile(mobile);
    }

    //社团
    public int selectCountByMobile1(final Long mobile) {
        return partnerMapper.selectCountByMobile1(mobile);
    }

    //查询待审核的校园合伙人
    public int selectCountBySchool() {
        return partnerMapper.selectCountBySchool();
    }

    //查询待审核的社团合伙人
    public int selectCountByGroup() {
        return partnerMapper.selectCountByGroup();
    }

    /**
     * 查询校园合作人待审核和审核未通过列表（分页+姓名模糊查询）
     *
     * @param partner
     * @return
     */
    public PageInfo<Partner> selectPartnerListLook(Partner partner) {
        if (partner.getPage() != null && partner.getRows() != null) {
            PageHelper.startPage(partner.getPage(), partner.getRows());
        }
        List<Partner> list = partnerMapper.selectPartnerListByStatusFail(partner);
        PageInfo<Partner> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 查询社团合作人待审核和审核未通过列表（分页+姓名模糊查询）
     *
     * @param partner
     * @return
     */
    public PageInfo<GroupPartnerVo> selectGroupPartnerListLook(Partner partner) {
        if (partner.getPage() != null && partner.getRows() != null) {
            PageHelper.startPage(partner.getPage(), partner.getRows());
        }
        List<GroupPartnerVo> list = partnerMapper.selectGroupPartnerListByStatusFail(partner);
        PageInfo<GroupPartnerVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 查询校园合作人通过列表（分页+姓名模糊查询）
     *
     * @param partner
     * @return
     */
    public PageInfo<SchoolPartherPassVo> selectPartnerList(Partner partner) {
        if (partner.getPage() != null && partner.getRows() != null) {
            PageHelper.startPage(partner.getPage(), partner.getRows());
        }
        List<SchoolPartherPassVo> list = partnerMapper.selectPartnerListByStatusPass(partner);
        PageInfo<SchoolPartherPassVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 查询社团合作人通过列表（分页+姓名模糊查询）
     *
     * @param partner
     * @return
     */
    public PageInfo<GroupPartnerVo> selectGroupPartnerList(Partner partner) {
        if (partner.getPage() != null && partner.getRows() != null) {
            PageHelper.startPage(partner.getPage(), partner.getRows());
        }
        List<GroupPartnerVo> list = partnerMapper.selectGroupPartnerListByStatusPass(partner);
        PageInfo<GroupPartnerVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


    /**
     * 审核校园合伙人通过操作
     *
     * @param partner
     * @return
     */
    public Result updateSchoolPartnerForPass(Partner partner) {
        Result result = new Result();
        if (partner.getStatus() == 1) {
            partner.setStatus(2);//设置状态为审核通过
            partner.setSchoolId(partner.getSchoolId()); //设置学校id
            partner.setSchoolName(partner.getSchoolName()); //设置学校名称
            partner.setSex(partner.getSex());    //设置申请人性别
            partner.setStarRating(partner.getStarRating());  //设置申请人星级
            partner.setCheckTime(new Date());//设置审核时间
            int a = partnerMapper.updateByPrimaryKeySelective(partner);
            if (a > 0) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
            }
        } else {
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 审核社团合伙人通过操作
     *
     * @param partner
     * @return
     */
    public Result updateGroupPartnerForPass(Partner partner) {
        Result result = new Result();
        if (partner.getStatus() == 1) {
            partner.setStatus(2);//设置状态为审核通过
            partner.setSex(partner.getSex());    //设置申请人性别
            partner.setStarRating(partner.getStarRating());  //设置申请人星级
            partner.setCheckTime(new Date());//设置审核时间
            int a = partnerMapper.updateByPrimaryKeySelective(partner);
            if (a > 0) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
            }
        } else {
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 审核（校园+社团）合伙人驳回操作
     *
     * @param partner
     * @return
     */
    public Result updatePartnerForRefuse(Partner partner) {
        Result result = new Result();
        if (partner.getStatus() == 1) {
            partner.setStatus(3);//审核通过
            partner.setCheckTime(new Date());
            int a = partnerMapper.updateByPrimaryKeySelective(partner);
            if (a > 0) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
            }
        } else {
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 审核（校园+社团）合伙人重新审核操作
     *
     * @param partner
     * @return
     */
    public Result updatePartnerForAgain(Partner partner) {
        Result result = new Result();
        if (partner.getStatus() == 3) {
            partner.setStatus(1);//待审核
            partner.setCheckTime(new Date());
            int a = partnerMapper.updateByPrimaryKeySelective(partner);
            if (a > 0) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
            }
        } else {
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 根据id查询详情
     *
     * @param id
     * @return
     */
    public Partner getById(Integer id) {
        return partnerMapper.selectByPrimaryKey(id);
    }

    //根据社团id查询详情
    public GroupPartnerVo getByGroupid(Integer id) {
        return partnerMapper.selectPartnerByGroupId(id);
    }


    //根据code查询所有下级用户信息
    public List<SchoolPartnerVo> selectUserInfoByCode(PartnerDetailsDto partnerDetailsDto) {
        return userInfoMapper.selectUserInfoByCode(partnerDetailsDto);
    }

    //根据partnerid查询所有下级用户信息(分页+条件)
    public PageInfo<SchoolPartnerVo> selectUserInfoByPartner(PartnerDetailsDto partnerDetailsDto) {
        if (partnerDetailsDto.getPage() != null && partnerDetailsDto.getRows() != null) {
            PageHelper.startPage(partnerDetailsDto.getPage(), partnerDetailsDto.getRows());
        }
        List<SchoolPartnerVo> schoolPartnerVoList = userInfoMapper.selectUserInfoByPartner(partnerDetailsDto);
        PageInfo<SchoolPartnerVo> pageInfo = new PageInfo<>(schoolPartnerVoList);
        return pageInfo;
    }

    //根据合伙人id查询所有邀请码
    public List<InvitationCode> selectCodesByPartnerId(Integer partnerid) {
        InvitationCodeExample example = new InvitationCodeExample();
        InvitationCodeExample.Criteria c = example.createCriteria();
        c.andPartnerIdEqualTo(partnerid);
        return invitationCodeMapper.selectByExample(example);
    }

    //根据手机号查询用户信息
    public UserInfo selectIsPresidentByMobile(Long mobile) {
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria c = example.createCriteria();
        c.andMobileEqualTo(mobile);
        List<UserInfo> userInfoList = userInfoMapper.selectByExample(example);
        if (!userInfoList.isEmpty()) {
            return userInfoList.get(0);
        }
        return null;
    }

    //根据手机号查询是否拥有社团
    public List<GroupListVo> selectByMobile(Long mobile) {
        return groupMapper.selectByMobile(mobile);
    }

    //添加社团申请
    public Result insertGroupPartner(CreatePartnerDto createPartnerDto) {
        Result result = new Result();
        Partner partner = new Partner();
        partner.setMobile(createPartnerDto.getMobile());
        partner.setRealName(createPartnerDto.getRealName());
        partner.setJoinTime(new Date());
        partner.setCheckTime(new Date());
        partner.setStatus(1);
        partner.setType(2);

        UserInfo userInfo = userInfoMapper.getUserInfoByMobile(createPartnerDto.getMobile()); //根据手机查询用户信息
        if (userInfo != null) {
            partner.setSchoolId(userInfo.getSchoolId());
            partner.setSchoolName(userInfo.getSchoolName());
        }

        int a = partnerMapper.insert(partner);
        if (a > 0) {
            PartnerGroup partnerGroup = new PartnerGroup();
            partnerGroup.setGroupId(createPartnerDto.getGroupId());
            partnerGroup.setPartnerId(partner.getId());
            int b = partnerGroupMapper.insert(partnerGroup);
            if (b > 0) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
            }
        } else {
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    //修改昵称
    public Result updatePartner(Partner partner) {
        Result result = new Result();
        int a = partnerMapper.updateByPrimaryKeySelective(partner);
        if (a > 0) {
            result.setResultCode(Result.SUCCESS);
        } else {
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 查询校园合伙人详情数据
     *
     * @param partnerDetailsDto
     * @return
     */
    public List<List<Object>> selectUserInfoByPartnerExport(PartnerDetailsDto partnerDetailsDto) {
        List<SchoolPartnerVo> schoolPartnerVoList = userInfoMapper.selectUserInfoByPartner(partnerDetailsDto);
        List<List<Object>>  list = new ArrayList<>();
        for(SchoolPartnerVo vo : schoolPartnerVoList){
            List<Object> voList = new ArrayList<>();
            if (StrUtils.isNotEmpty(vo.getUserId())) {
                voList.add(vo.getCode());
                voList.add(vo.getRealName());
                voList.add(vo.getStudentNo());
                voList.add(vo.getMobile());
                voList.add(vo.getQq());
                voList.add(vo.getSchoolName());
                voList.add(vo.getJoinTime());
                list.add(voList);
            }

        }
        return list;
    }

    //查询导出社团详情的数据
    public List<List<Object>> selectEventByPartnerExport(PartnerEventDto partnerEventDto){
        List<EventVo> eventVoList=eventMapper.selectByGroupId(partnerEventDto);
        List<List<Object>>  list = new ArrayList<>();
        for (EventVo vo:eventVoList) {
            List<Object> voList = new ArrayList<>();
            if(vo!=null){
                voList.add(vo.getSubject());
                voList.add(vo.getJoinCnt());
                voList.add("无");
                if(vo.getTimeStatus()==1){
                    voList.add("未开始");
                }
                if(vo.getTimeStatus()==2){
                    voList.add("进行中");
                }
                if(vo.getTimeStatus()==3){
                    voList.add("已结束");
                }
                voList.add(vo.getStartTime()+"-"+vo.getEndTime());
                list.add(voList);
            }
        }
        return list;
    }
}
