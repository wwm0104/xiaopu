package com.chinaxiaopu.xiaopuMobi.controller.admin.topics;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.PrizesDto;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.TopicConfirmDto;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.TopicIndexDto;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.TopicListDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.PrizeAvailableDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.PrizeCreateDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.PrizeDto;
import com.chinaxiaopu.xiaopuMobi.model.Prize;
import com.chinaxiaopu.xiaopuMobi.model.Topic;
import com.chinaxiaopu.xiaopuMobi.security.realm.ShiroUser;
import com.chinaxiaopu.xiaopuMobi.service.topic.PrizeService;
import com.chinaxiaopu.xiaopuMobi.service.topic.TopicService;
import com.chinaxiaopu.xiaopuMobi.vo.admin.topics.TopicDetailVo;
import com.chinaxiaopu.xiaopuMobi.vo.admin.topics.TopicListVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.PrizeVo;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Wang on 2016/11/07.
 */
@Slf4j
@Controller
@RequestMapping("/admin/fight")
public class FightController {

    @Autowired
    private PrizeService prizeService;
    @Autowired
    private TopicService topicService;

    @RequestMapping(value = "/prizeList", method = RequestMethod.GET)
    public String prizeList(){
        return "admin/topic/prizeList";
    }
    @RequestMapping(value = "/prizeCreate", method = RequestMethod.GET)
    public String prizeCreate(){
        return "admin/topic/prizeCreate";
    }
    @RequestMapping(value = "/prizeDetail/{id}", method = RequestMethod.GET)
    public String prizeDetail(@PathVariable("id") final int id, Model model){
        PrizeVo prizeVo = prizeService.prizeDetail(id);
        model.addAttribute("prize",prizeVo);

        return "admin/topic/prizeDetail";
    }

    @RequestMapping(value = "/prize/list", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<PrizeVo>> list(@RequestBody PrizeDto prizeDto){
        Result<PageInfo<PrizeVo>> result =new Result<>();
        try{
            log.debug("获取奖品列表");
            PageInfo<PrizeVo> pageInfo = prizeService.prizeList(prizeDto);

            result.setObj(pageInfo);
            result.setResultCode(Result.SUCCESS);
        }catch (Exception e){
            log.error("获取奖品列表失败",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }

        return result;
    }

    @RequestMapping(value = "/prize/create", method = RequestMethod.POST)
    @ResponseBody
    public Result create(@RequestBody PrizeCreateDto prizeCreateDto){
        Result result =new Result();
        try{
            log.debug("新增奖品");
            Prize prize = new Prize();
            Gson gson = new Gson();
            prizeCreateDto.setPrize(gson.toJson(prizeCreateDto.getPrizeMap()));
            BeanUtils.copyProperties(prizeCreateDto,prize);
            prize.setStockOut(0);
            prize.setCreaterId(0);
            prize.setCreaterRealname("校谱官方");
            prize.setHasLimit(1);
            Boolean flag = prizeService.create(prize);
            if(flag){
                result.setResultCode(Result.SUCCESS);
            }else{
                result.setResultCode(Result.FAILURE);
            }
        }catch (Exception e){
            log.error("新增奖品 失败",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @RequestMapping(value = "/prize/enableOrDisable", method = RequestMethod.POST)
    @ResponseBody
    public Result enableOrDisable(@RequestBody PrizeAvailableDto prizeAvailableDto){
        Result result =new Result();
        try{
            log.debug("启用或停用奖品");
            Boolean flag = prizeService.enableOrDisable(prizeAvailableDto);
            if(flag){
                result.setResultCode(Result.SUCCESS);
            }else{
                result.setResultCode(Result.FAILURE);
            }
        }catch (Exception e){
            log.error("启用或停用奖品 失败",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }



    @RequestMapping(value = "/topicList", method = RequestMethod.GET)
    public String topicList(){
        return "admin/topic/topicList";
    }

    @RequestMapping(value = "/topic/list", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<TopicListVo>> topics(@RequestBody TopicListDto topicListDto){
        Result<PageInfo<TopicListVo>> result =new Result<>();
        try{
            log.debug("审核主题列表");
            PageInfo<TopicListVo> pageInfo = topicService.topicList(topicListDto);

            result.setObj(pageInfo);
            result.setResultCode(Result.SUCCESS);
        }catch (Exception e){
            log.error("获取审核主题列表 失败",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @RequestMapping(value = "/topicDetail/{id}", method = RequestMethod.GET)
    public String topicDetail(@PathVariable("id") final int id, Model model){
        TopicDetailVo topicDetailVo = topicService.topicDetail(id);
        model.addAttribute("topic",topicDetailVo);

        return "admin/topic/topicDetail";
    }

    @RequestMapping(value = "/topic/confirm", method = RequestMethod.POST)
    @ResponseBody
    public Result topicConfirm(@RequestBody TopicConfirmDto topicConfirmDto){
        Result result =new Result<>();
        try{
            log.debug("审核主题");
            //获取当前登录用户
            ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
            Integer userId = shiroUser.getId();
            topicConfirmDto.setUserId(userId);
            Boolean flag = topicService.topicConfirm(topicConfirmDto);
            if(flag){
                result.setResultCode(Result.SUCCESS);
            }else{
                result.setResultCode(Result.FAILURE);
            }
        }catch (Exception e){
            log.error("审核主题 失败",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @RequestMapping(value = "/goreviewTopicList", method = RequestMethod.GET)
    public String goreviewList(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("status", 0);
        return "admin/topic/topicList";
    }

}
