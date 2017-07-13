package com.chinaxiaopu.xiaopuMobi.controller.partner;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.dto.partner.PartnerDetailsDto;
import com.chinaxiaopu.xiaopuMobi.dto.partner.PartnerEventDto;
import com.chinaxiaopu.xiaopuMobi.model.InvitationCode;
import com.chinaxiaopu.xiaopuMobi.model.Partner;
import com.chinaxiaopu.xiaopuMobi.service.EventService;
import com.chinaxiaopu.xiaopuMobi.service.InvitationCodeService;
import com.chinaxiaopu.xiaopuMobi.service.PartnerService;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.partner.GroupPartnerVo;
import com.chinaxiaopu.xiaopuMobi.vo.partner.SchoolPartherPassVo;
import com.chinaxiaopu.xiaopuMobi.vo.partner.SchoolPartnerVo;
import com.chinaxiaopu.xiaopuMobi.vo.president.EventVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Created by xiaohao on 2016/11/3.
 */
@Slf4j
@Controller
@RequestMapping("/partnerManage")
public class PartherController {
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private EventService eventService;
    @Autowired
    private InvitationCodeService invitationCodeService;


    @RequestMapping("/index")
    @ApiIgnore
    public String partnerIndex(Model model) {
        //校园待审核人数
        model.addAttribute("schoolNo",partnerService.selectCountBySchool());
        //社团待审核人数
        model.addAttribute("groupNo",partnerService.selectCountByGroup());
        return "partner/partherIndex";
    }
    /**
     * 跳转校园合伙人管理
     * @return
     */
    @RequestMapping(value = "/school", method = RequestMethod.GET)
    public String schoolList(){
        return "partner/schoolPartherList";
    }

    /**
     * 跳转社团合伙人审核界面
     * @return
     */
    @RequestMapping(value = "/schoolLook", method = RequestMethod.GET)
    public String schoolLook(){
        return "partner/schoolPartherLook";
    }

    /**
     * 跳转社团合伙人管理
     * @return
     */
    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public String groupList(){
        return "partner/groupPartherList";
    }

    /**
     * 跳转社团合伙人审核界面
     * @return
     */
    @RequestMapping(value = "/groupLook", method = RequestMethod.GET)
    public String groupLook(){
        return "partner/groupPartherLook";
    }

    /**
     * 跳转社团合伙人详情
     * @return
     */
    @RequestMapping(value = "/groupDetails/{groupid}", method = RequestMethod.GET)
    public String groupDetails(@PathVariable("groupid") final int groupid, Model model){
        GroupPartnerVo groupPartnerVo=new GroupPartnerVo();
        groupPartnerVo=partnerService.getByGroupid(groupid);
        model.addAttribute("partner",groupPartnerVo);
        return "partner/groupPartherDetails";
    }

    /**
     * 根据社团id获取活动详情（分页+条件）
     * @param partnerEventDto
     * @return
     */
    @RequestMapping(value = "/groupDetailsByGroup",method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<EventVo>> getEventByGroup(@RequestBody PartnerEventDto partnerEventDto){
        Result<PageInfo<EventVo>> result=new Result<>();
        try {
            result.setObj(eventService.getEventByGroup(partnerEventDto));
            result.setResultCode(Result.SUCCESS);
        }catch (Exception e){
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setMsg("获取失败");
        }
        return result;
    }

    /**
     * 跳转校园合伙人详情
     * @return
     */
    @RequestMapping(value = "/schoolDetails/{id}", method = RequestMethod.GET)
    public String schoolDetails(@PathVariable("id") final int id, Model model){
        Partner partner=new Partner();
        partner=partnerService.getById(id);
        //合伙人信息
        model.addAttribute("partner",partner);
        List<InvitationCode> invitationCodeList=partnerService.selectCodesByPartnerId(id);
        model.addAttribute("codes",invitationCodeList);
        return "partner/schoolPartherDetails";
    }

    /**
     * 根据合伙人id查询所下级邀请人（分页+条件）
     * @param partnerDetailsDto
     * @return
     */
    @RequestMapping(value = "/schoolDetailsByPartner",method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<SchoolPartnerVo>> getUserByPartner(@RequestBody PartnerDetailsDto partnerDetailsDto){

        String startTime="";
        String endTime="";
        if (StrUtils.isNotEmpty(partnerDetailsDto.getJoinTime())) { //分割时间段条件
            String time = partnerDetailsDto.getJoinTime();
            startTime = time.split("/")[0];
            endTime = time.split("/")[1];
            partnerDetailsDto.setStartTime(startTime);
            partnerDetailsDto.setEndTime(endTime);
        }

        Result<PageInfo<SchoolPartnerVo>> result=new Result<>();
        try {
            result.setObj(partnerService.selectUserInfoByPartner(partnerDetailsDto));
            result.setResultCode(Result.SUCCESS);
        }catch (Exception e){
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setMsg("获取失败");
        }
        return result;
    }

    /**
     * 查询所有待审核和未审核的校园合伙人
     * @param partner
     * @return
     */
    @RequestMapping(value="/getSchoolPartnerLook",method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<Partner>> getSchoolPartherLook(@RequestBody Partner partner){
        Result<PageInfo<Partner>> result = new Result<>();
        try {
            result.setObj(partnerService.selectPartnerListLook(partner));
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("POST请求 校园合伙人列表失败",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 查询所有待审核和未审核的社团合伙人
     * @param partner
     * @return
     */
    @RequestMapping(value="/getGroupPartnerLook",method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<GroupPartnerVo>> getGroupPartnerLook(@RequestBody Partner partner){
        Result<PageInfo<GroupPartnerVo>> result = new Result<>();
        try {
            result.setObj(partnerService.selectGroupPartnerListLook(partner));
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("POST请求 社团合伙人列表失败",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 查询所有通过审核的校园合伙人
     * @param partner
     * @return
     */
    @RequestMapping(value="/getSchoolPartner",method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<SchoolPartherPassVo>> getSchoolParther(@RequestBody Partner partner){
        Result<PageInfo<SchoolPartherPassVo>> result = new Result<>();
        try {
            result.setObj(partnerService.selectPartnerList(partner));
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("POST请求 校园合伙人列表失败",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 查询所有通过审核的社团合伙人
     * @param partner
     * @return
     */
    @RequestMapping(value="/getGroupPartner",method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<GroupPartnerVo>> getGroupPartner(@RequestBody Partner partner){
        Result<PageInfo<GroupPartnerVo>> result = new Result<>();
        try {
            result.setObj(partnerService.selectGroupPartnerList(partner));
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("POST请求 校园合伙人列表失败",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 校园合伙人审核
     * @author:flint
     * @param partner
     * @return
     */
    @RequestMapping(value = "/checkSchoolPass", method = RequestMethod.POST)
    @ResponseBody
    public Result checkSchoolPass(@RequestBody Partner partner) {
        Result result = new Result();
        try {
            return partnerService.updateSchoolPartnerForPass(partner);
        } catch (Exception e) {
            log.error("校园合伙人审核失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 社团合伙人审核
     * @author:flint
     * @param partner
     * @return
     */
    @RequestMapping(value = "/checkGroupPass", method = RequestMethod.POST)
    @ResponseBody
    public Result checkGroupPass(@RequestBody Partner partner) {
        Result result = new Result();
        try {
            return partnerService.updateGroupPartnerForPass(partner);
        } catch (Exception e) {
            log.error("校园合伙人审核失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 驳回(社团+校园)
     * @param partner
     * @return
     */
    @RequestMapping(value = "/checkRefused", method = RequestMethod.POST)
    @ResponseBody
    public Result checkRefused(@RequestBody Partner partner) {
        Result result = new Result();
        try {
            return partnerService.updatePartnerForRefuse(partner);
        } catch (Exception e) {
            log.error("驳回失败",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 重新审核(社团+校园)
     * @param partner
     * @return
     */
    @RequestMapping(value = "/checkAgain", method = RequestMethod.POST)
    @ResponseBody
    public Result checkAgain(@RequestBody Partner partner) {
        Result result = new Result();
        try {
            return partnerService.updatePartnerForAgain(partner);
        } catch (Exception e) {
            log.error("重新审核失败",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 生成邀请码
     * @return code
     */
    @RequestMapping("/code")
    @ResponseBody
    public Result addCode(){
        Result result = new Result();
        try {
            String code=invitationCodeService.getInvitationCode();
            result.setMsg(code);
            result.setResultCode(Result.SUCCESS);
        }catch (Exception e){
            log.error("获取code失败",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 合伙人修改备注信息
     * @param partner
     * @return
     */
    @RequestMapping(value = "/updateRemarks",method = RequestMethod.POST)
    @ResponseBody
    public Result updateGroupPartnerRemark(@RequestBody Partner partner){
        Result result=new Result();
        try {
           return partnerService.updatePartner(partner);
        }catch (Exception e){
            log.error("修改备注失败",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return  result;
    }

    /**
     * 合伙人添加邀请码
     * @param invitationCode
     * @return
     */
    @RequestMapping(value = "/addCode",method = RequestMethod.POST)
    @ResponseBody
    public Result updateGroupPartnerRemark(@RequestBody InvitationCode invitationCode){
        Result result=new Result();
        try {
            return invitationCodeService.addCode(invitationCode);
        }catch (Exception e){
            log.error("添加邀请码失败",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return  result;
    }












}
