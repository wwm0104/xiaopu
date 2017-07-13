package com.chinaxiaopu.xiaopuMobi.controller.admin;

import com.chinaxiaopu.xiaopuMobi.dto.GroupAndEventNum;
import com.chinaxiaopu.xiaopuMobi.service.BackStageManagementEventService;
import com.chinaxiaopu.xiaopuMobi.service.PartnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ycy on 2016/10/19.
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class BackStageIndex {

    @Autowired
    private BackStageManagementEventService backStageManagementEventService;

    @Autowired
    private PartnerService partnerService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)

    public String goBackStageIndex(Model model, HttpServletRequest request, HttpServletResponse response){
        GroupAndEventNum groupAndEventNum = null;
        int schoolNo=0;
        int groupNo=0;
        try{
             groupAndEventNum = backStageManagementEventService.selectGroupAndEventNum();
            //校园待审核人数
            schoolNo=partnerService.selectCountBySchool();
            //社团待审核人数
            groupNo=partnerService.selectCountByGroup();
        }catch (Exception e){
            log.error("获取未审核社团及活动次数失败",e);
        }
        model.addAttribute("groupAndEventNum",groupAndEventNum);
        model.addAttribute("schoolNo",schoolNo);
        model.addAttribute("groupNo",groupNo);
        return "admin/index";
    }
}
