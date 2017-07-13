package com.chinaxiaopu.xiaopuMobi.controller.anchor;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.dto.CreatTopicDto;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.TopicConfirmDto;
import com.chinaxiaopu.xiaopuMobi.model.PkChannel;
import com.chinaxiaopu.xiaopuMobi.model.Topic;
import com.chinaxiaopu.xiaopuMobi.security.realm.ShiroUser;
import com.chinaxiaopu.xiaopuMobi.service.audio.AdminAudioService;
import com.chinaxiaopu.xiaopuMobi.service.topic.TopicsCreateService;
import com.chinaxiaopu.xiaopuMobi.vo.audio.AudioDetailVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ycy on 2016/12/6.
 */
@Slf4j
@RequestMapping("/anchorManage")
@Controller
public class AnthorAudioController {

    @Autowired
    private AdminAudioService adminAudioService;

    @Autowired
    private TopicsCreateService topicsCreateService;

    /**
     * topicCreate
     */
    @RequestMapping(value = "/toCreate", method = RequestMethod.GET)
    public String toCreate(Model model) {
        try {
            List<PkChannel> channelsList = topicsCreateService.selectOfficalAudioPkChannel();
            model.addAttribute("channelsList", channelsList);
        } catch (Exception e) {
            log.error("查询頻道列表失败", e);
        }
        return "admin/topic/createAudioTopick";
    }


    /**
     * 跳转音频列表
     * @return
     */
    @RequestMapping(value = "/toAnchorAudioList",method = RequestMethod.GET)
    public String toAnchorAudioList(Model model){
        try {
            model.addAttribute("imgsDomain", SystemConfig.getInstance().getImgsDomain());
        } catch (Exception e) {
            log.error("跳转失败",e);
        }
        return "admin/anchor/anchorAudioList";
    }

    /**
     * 跳转音频详情（主播）
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/toAnchorAudioDetail/{id}",method = RequestMethod.GET)
    public String toAnchorAudioDetail(@PathVariable("id") final int id,Model model){
        AudioDetailVo audioDetailVo = adminAudioService.getAudioDetail(id);
        audioDetailVo.setOnlineTime(audioDetailVo.getExpireTime());
        model.addAttribute("audio",audioDetailVo);
        try {
            model.addAttribute("imgsDomain", SystemConfig.getInstance().getImgsDomain());
        } catch (Exception e) {
            log.error("跳转详情失败",e);
        }
        return "admin/anchor/anchorAudioDetials";  //页面待完善
    }


    /**
     * 查询音频列表(主播)
     * @param topic
     * @return
     */
    @RequestMapping(value = "/audioList", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<Topic>> getAudioList(@RequestBody  Topic topic){
        Result<PageInfo<Topic>> result = new Result<>();
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Integer userId = shiroUser.getId();
        topic.setCreatorId(userId);
        try {
            PageInfo<Topic> pageInfo = adminAudioService.getAudioList(topic);
            result.setResultCode(Result.SUCCESS);
            result.setObj(pageInfo);
        }catch (Exception e){
            log.error("查询音频列表失败",e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

    /**
     * 创建音频
     * @param topic
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Result audioCreate(@RequestBody CreatTopicDto topic){
        Result<PageInfo<Topic>> result = new Result<>();
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Integer userId = shiroUser.getId();
        topic.setCreatorId(userId);
        try {
            adminAudioService.createAudioTopic(topic);
            result.setResultCode(Result.SUCCESS);
        }catch (Exception e){
            log.error("创建音频失败",e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);

        }
        return result;
    }

}
