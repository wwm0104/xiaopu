package com.chinaxiaopu.xiaopuMobi.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liuwei
 * date: 2016/10/18
 */
@Slf4j
@Controller
@RequestMapping("/admin/event")
public class EventComtroller {

    @RequestMapping(value = "/createTest", method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("groupName","校谱官方");
        return "admin/create";
    }
}
