package com.chinaxiaopu.xiaopuMobi.controller.admin;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.model.Partner;
import com.chinaxiaopu.xiaopuMobi.service.PartnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * Created by liuwei
 * date: 2016/10/21
 */
@Slf4j
@Controller
@RequestMapping("/admin/reviewpartner")
public class ReviewPartnerController {

    @Autowired
    PartnerService partnerService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "partner/list";
    }



//    /**
//     * 获取合伙人列表（分页+模糊查询姓名+状态查询+类型查询）
//     * @param partner
//     * @return
//     */
//    @RequestMapping(value = "/list", method = RequestMethod.POST)
//    @ResponseBody
//    public Result<PageInfo<Partner>> list(@RequestBody Partner partner){
//        Result<PageInfo<Partner>> result = new Result<>();
//        try {
//            log.debug("POST请求 合伙人列表");
//            result.setObj(partnerService.selectPartnerList(partner));
//            result.setResultCode(Result.SUCCESS);
//        } catch (Exception e) {
//            log.error("POST请求 合伙人列表失败",e);
//            result.setResultCode(Result.FAILURE);
//        }
//        return result;
//    }


    /**
     * 合伙人审核
     * @author:flint
     * @param partner
     * @return
     */
    @RequestMapping(value = "/checkPass", method = RequestMethod.POST)
    @ResponseBody
    public Result checkPass(@RequestBody Partner partner) {
        Result result = new Result();
        try {
            return partnerService.updateSchoolPartnerForPass(partner);
        } catch (Exception e) {
            log.error("合伙人审核失败", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

    /**
     * 合伙人驳回
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
            log.error("合伙人驳回失败",e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

}
