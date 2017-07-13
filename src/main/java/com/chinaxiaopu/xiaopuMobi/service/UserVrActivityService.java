package com.chinaxiaopu.xiaopuMobi.service;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.dto.UserVrActivityDto;
import com.chinaxiaopu.xiaopuMobi.dto.VrDto;
import com.chinaxiaopu.xiaopuMobi.mapper.UserInfoMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.UserInvitationCodeMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.UserVrActivityMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.VrActivityMapper;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.model.UserInvitationCode;
import com.chinaxiaopu.xiaopuMobi.model.UserVrActivity;
import com.chinaxiaopu.xiaopuMobi.model.VrActivity;
import com.chinaxiaopu.xiaopuMobi.service.domain.ISmsRequest;
import com.chinaxiaopu.xiaopuMobi.service.domain.SmsBaseSendRequest;
import com.chinaxiaopu.xiaopuMobi.service.domain.SmsStatus;
import com.chinaxiaopu.xiaopuMobi.util.DateTimeUtil;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.UserVrActivityVo;
import com.chinaxiaopu.xiaopuMobi.vo.VrUserVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuwei
 * date: 2016/11/12
 */
@Slf4j
@Service
public class UserVrActivityService {

    @Autowired
    private UserVrActivityMapper userVrActivityMapper;

    @Autowired
    private VrActivityMapper vrActivityMapper;

    @Autowired
    UserInvitationCodeMapper userInvitationCodeMapper;

    @Autowired
    InvitationCodeService invitationCodeService;

    @Autowired
    private UserService userService;

    @Autowired
    SmsService smsService;
    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserVrActivityVo initVrActivity(UserInfo userInfo){
        UserVrActivityVo vo = new UserVrActivityVo();
        vo.setMobile(userInfo.getMobile());
        vo.setRealName(userInfo.getRealName());
        vo.setSchoolName(userInfo.getSchoolName());
        vo.setStudentNo(userInfo.getStudentNo());
        Integer userId = userInfo.getUserId();
        vo.setAppointmentCnt(getAppointmentCntByUser(userId));
        String appointmentDate = DateTimeUtil.getOneDayStart(DateTimeUtil.SIMPLE_DATE_FORMAT_STRING);
        List<VrActivity> list = getVrActivityBydate(appointmentDate);
        vo.setList(list);
        return vo;

    }

    @Transactional
    public Result joinVrActivity(UserVrActivityDto dto, UserInfo userInfo) throws Exception {
        Result result = new Result();
        String mobile = userInfo.getMobile().toString();
        if(smsService.allownSend(mobile).getResultCode() != Result.SUCCESS){
            return smsService.allownSend(mobile);
        }
        ISmsRequest smsRequest = new SmsBaseSendRequest();
        try {
            VrActivity vrActivity = vrActivityMapper.selectByPrimaryKey(dto.getAppointmentId());

            if (getAppointmentCntByUser(userInfo.getUserId())<dto.getActivityCnt()){
                result.setResultCode(Result.FAILURE);
                result.setMsg("选择次数超过最大体验次数");
                return result;
            } else if((vrActivity.getAppointmentCnt()+dto.getActivityCnt())>vrActivity.getAppointmentMaxCnt()){
                result.setResultCode(Result.FAILURE);
                result.setMsg("选择体验次数超过该时间段剩余体验次数");
                return result;
            }
            String randomCode = RandomStringUtils.randomNumeric(6);
            UserVrActivity userVrActivity = new UserVrActivity();
            userVrActivity.setUserId(userInfo.getUserId());
            userVrActivity.setActivityCnt(dto.getActivityCnt());
            userVrActivity.setAppointmentId(dto.getAppointmentId());
            userVrActivity.setAppointmentDate(dto.getAppointmentDate());
            userVrActivity.setAppointmentTime(dto.getAppointmentTime());
            userVrActivity.setAppointmentCode(randomCode);
            userVrActivityMapper.insertSelective(userVrActivity);

            vrActivity.setAppointmentCnt(vrActivity.getAppointmentCnt()+dto.getActivityCnt());
            vrActivityMapper.updateByPrimaryKeySelective(vrActivity);

            //发送短信验证码
            smsRequest.setRecNum(mobile);
            Map<String,String> map = new HashMap<>();
            map.put("time",dto.getAppointmentDate()+" "+dto.getAppointmentTime());
            Gson gson = new Gson();
            smsRequest.setSmsParam(gson.toJson(map));
            smsService.sendSMS(smsRequest);
            SmsStatus smsStatus = new SmsStatus(mobile);
            smsService.updateSum(smsStatus);
            log.info("发送短信成功");
            result.setMsg("VR活动申请成功");
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            smsRequest = null;
            throw new Exception();
        }
        return result;

    }

    public List<VrActivity> getVrActivityBydate(String appointmentDate){
        List<VrActivity> list = vrActivityMapper.selectByDate(appointmentDate);
        if (list==null || list.size()<=0){
            String[] tempTime = new String[]{"09:00-10:00","10:00-11:00","11:00-12:00","12:00-13:00","13:00-14:00","14:00-15:00","15:00-16:00","16:00-17:00","17:00-18:00","18:00-19:00","19:00-20:00","20:00-21:00"};
            list = new ArrayList<>();
            for (String s : tempTime) {
                VrActivity vrActivity = new VrActivity();
                vrActivity.setAppointmentDate(appointmentDate);
                vrActivity.setAppointmentTime(s);
                vrActivity.setAppointmentMaxCnt(10);
                vrActivity.setAppointmentCnt(0);
                vrActivity.setAvailable(1);
                vrActivityMapper.insertSelective(vrActivity);
                list.add(vrActivity);
            }
        }
        return list;
    }

    /**
     * 获取用户最大体验次数
     *
     * */
    private int getAppointmentCntByUser(final int userId){
        UserInvitationCode userInvitationCode = userInvitationCodeMapper.selectByUserId(userId);
        int sumCount = 1;
        if(userInvitationCode==null){
            userInvitationCode = new UserInvitationCode();
            userInvitationCode.setUserId(userId);
            userInvitationCode.setUserCode(invitationCodeService.getInvitationCode());
            userInvitationCodeMapper.insert(userInvitationCode);
            sumCount = 1;
        } else {
            sumCount = userService.selectInvitationCodeCntByUserCode(userInvitationCode.getUserCode())+1;
        }
        int activityCnt = userVrActivityMapper.selectSumCntByUserId(userId);
        return sumCount-activityCnt;
    }
    //查询vr用户体验列表（分页+条件）
    public PageInfo<VrUserVo> selectVrList(VrDto vrDto){
        if (vrDto.getPage()!=null && vrDto.getRows()!=null) {
            PageHelper.startPage(vrDto.getPage(),vrDto.getRows());
        }
        List<VrUserVo> vrUserVoList=userInfoMapper.selectVrList(vrDto);
        PageInfo<VrUserVo> pageInfo=new PageInfo<>(vrUserVoList);
        return pageInfo;
    }

    //导出全部vr列表
    public List<List<Object>> exportVrList(){
        VrDto vrDto=new VrDto();
        List<VrUserVo> vrUserVoList=userInfoMapper.selectVrList(vrDto);
        List<List<Object>>  list = new ArrayList<>();
        for(VrUserVo vo : vrUserVoList){
            List<Object> voList = new ArrayList<>();
            if (StrUtils.isNotEmpty(vo.getUserId())) {
                voList.add(vo.getRealName());
                voList.add(vo.getMobile());
                voList.add(vo.getSchoolName());
                voList.add(vo.getStudentNo());
                voList.add(vo.getAppointmentDate()+"-"+vo.getAppointmentTime());
                voList.add(vo.getActivityCnt());
                list.add(voList);
            }
        }
        return list;
    }
}
