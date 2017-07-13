package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuwei
 * date: 16/9/29
 */
@Slf4j
@Controller
public class IndexController extends ContextController{

    @RequestMapping("/")
    @ApiIgnore
    public String index() {
        return "forward:/user/login";
    }

    @RequestMapping("/admin/index")
    @ApiIgnore
    public String adminIndex() {
        return "admin/index";
    }



    @RequestMapping("/testInput")
    @ApiIgnore
    public String testInput() {
        return "testInput";
    }

    @RequestMapping(value = "/unauthor")
    public String unauthor(HttpServletRequest request){
        String xmlHttpRequest = request.getHeader("X-Requested-With");
        if ( xmlHttpRequest != null && xmlHttpRequest.equalsIgnoreCase("XMLHttpRequest")) {//是ajax请求
            return "redirect:/user/ajaxUnLogin";
        }
        return "unauthor";
    }

    @ApiIgnore
    @ResponseBody
    @RequestMapping(value = "/ajaxUnauthor", method = RequestMethod.GET)
    public Result ajaxUnLogin(HttpServletRequest request) {
        Result result = new Result();
        result.setResultCode(Constants.UNAUTHORIZED);
        result.setMsg(Result.NO_JURISDICTION);
        return result;
    }

}
