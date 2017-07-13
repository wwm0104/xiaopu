package com.chinaxiaopu.xiaopuMobi.controller.admin.audio;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.TopicConfirmDto;
import com.chinaxiaopu.xiaopuMobi.dto.audio.AudioConfirmDto;
import com.chinaxiaopu.xiaopuMobi.dto.audio.CreatePersonnelDto;
import com.chinaxiaopu.xiaopuMobi.dto.audio.PersonnelDto;
import com.chinaxiaopu.xiaopuMobi.dto.audio.PositionDto;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.chinaxiaopu.xiaopuMobi.service.audio.AdminAudioService;
import com.chinaxiaopu.xiaopuMobi.vo.audio.AudioDetailVo;
import com.chinaxiaopu.xiaopuMobi.vo.audio.PersonnelVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * Created by ycy on 2016/12/6.
 */
@Slf4j
@RequestMapping("/adminAudio")
@Controller
public class AdminAudioController {

    @Autowired
    private AdminAudioService adminAudioService;




    /**
     * 跳转音频列表
     *
     * @return
     */
    @RequestMapping(value = "/toAudioList", method = RequestMethod.GET)
    public String toAudioList(Model model) {
        try {
            model.addAttribute("imgsDomain", SystemConfig.getInstance().getImgsDomain());
        } catch (Exception e) {
            log.error("跳转失败", e);
        }
        return "admin/audio/audioList";
    }

    /**
     * 职位列表
     * @return
     */
    @RequestMapping(value = "/toPositionList",method = RequestMethod.GET)
    public String positionList(){
        return "admin/audio/positionList";
    }



    /**
     * 人员列表
     * @return
     */
    @RequestMapping(value = "/toPersonnelList",method = RequestMethod.GET)
    public String personnelList(){
        return "admin/audio/personnelList";
    }



    /**
     * 查询音频列表（管理员）
     *
     * @param audioConfirmDto
     * @return
     */
    @RequestMapping(value = "/audioCheckList", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<AudioDetailVo>> getAudioCheckList(@RequestBody AudioConfirmDto audioConfirmDto) {
        Result<PageInfo<AudioDetailVo>> result = new Result<>();
        try {
            PageInfo<AudioDetailVo> pageInfo = adminAudioService.getAudioList1(audioConfirmDto);
            result.setObj(pageInfo);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 跳转音频详情(管理员)
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/toAudioDetail/{id}", method = RequestMethod.GET)
    public String toAudioDetail(@PathVariable("id") final int id, Model model) {
        AudioDetailVo audioDetailVo = adminAudioService.getAudioDetail(id);
        audioDetailVo.setOnlineTime(audioDetailVo.getExpireTime());
        model.addAttribute("audio", audioDetailVo);
        try {
            model.addAttribute("imgsDomain", SystemConfig.getInstance().getImgsDomain());
        } catch (Exception e) {
            log.error("跳转失败", e);
        }
        return "admin/audio/audioDetail";
    }

    /**
     * 通过or驳回
     *
     * @param topicConfirmDto
     */
    @RequestMapping("/lookAudio")
    @ResponseBody
    public Result lookAudio(@RequestBody TopicConfirmDto topicConfirmDto) {
        Result result = new Result();
        try {
            boolean ishas = adminAudioService.updateStatus(topicConfirmDto);
            if (ishas) {
                result.setMsg("成功1");
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setMsg("失败");
                result.setResultCode(Result.FAILURE);
            }
        } catch (Exception e) {
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

    /**
     * 获取职位列表
     * @param positionDto
     * @return
     */
    @RequestMapping(value = "/getPositionList", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<Position>> getPositionList(@RequestBody PositionDto positionDto) {
        Result<PageInfo<Position>> result = new Result<>();
        try {
            PageInfo<Position> pageInfo = adminAudioService.getPositionList(positionDto);
            result.setObj(pageInfo);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 人员列表
     * @param personnelDto
     * @return
     */
    @RequestMapping(value = "/getPersonnelList", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<PersonnelVo>> getPersonnelList(@RequestBody PersonnelDto personnelDto) {
        Result<PageInfo<PersonnelVo>> result = new Result<>();
        try {
            PageInfo<PersonnelVo> pageInfo = adminAudioService.getPersonnelList(personnelDto);
            result.setObj(pageInfo);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 验证职位
     * @param map
     * @return
     */
    @RequestMapping(value = "/checkPosition",method = RequestMethod.POST)
    @ResponseBody
    public Result checkPosition(@RequestBody Map<String,Object> map){
        Result result=new Result();
        try {
            boolean ishas=adminAudioService.checkPositionName(map.get("positionName").toString());
            if(ishas) {
                result.setResultCode(Result.SUCCESS);
            }else {
                result.setResultCode(Result.FAILURE);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setResultCode(Result.FAILURE);
            result.setMsg("验证失败");
        }
        return result;
    }
    /**
     * 添加职位
     * @param position
     * @return
     */
    @RequestMapping(value = "/addPosition",method = RequestMethod.POST)
    @ResponseBody
    public Result addPosition(@RequestBody Position position) {
        Result result = new Result();
        try {
            boolean ishas=adminAudioService.addPosition(position);
            if(ishas) {
                result.setResultCode(Result.SUCCESS);
                result.setMsg("添加成功");
            }else {
                result.setResultCode(Result.FAILURE);
                result.setMsg("添加失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setResultCode(Result.FAILURE);
            result.setMsg("添加失败");
        }
        return result;
    }

    /**
     * 员工赋职位
     * @param createPersonnelDto
     * @return
     */
    @RequestMapping(value = "/addPersonnel",method = RequestMethod.POST)
    @ResponseBody
    public Result addPersonnel(@RequestBody CreatePersonnelDto createPersonnelDto) {
        Result result = new Result();
        try {
            boolean ishas=adminAudioService.addPersonnel(createPersonnelDto);
            if(ishas) {
                result.setResultCode(Result.SUCCESS);
                result.setMsg("添加成功");
            }else {
                result.setResultCode(Result.FAILURE);
                result.setMsg("添加失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setResultCode(Result.FAILURE);
            result.setMsg("添加失败");
        }
        return result;
    }

    /**
     * 删除职位（逻辑删除）
     * @param id
     * @return
     */
    @RequestMapping(value = "/delPosition",method = RequestMethod.POST)
    @ResponseBody
    public Result delPosition(Integer id) {
        Result result = new Result();
        try {
            boolean ishas=adminAudioService.delPosition(id);
            if(ishas) {
                result.setResultCode(Result.SUCCESS);
                result.setMsg("删除成功");
            }else {
                result.setResultCode(Result.FAILURE);
                result.setMsg("删除失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setResultCode(Result.FAILURE);
            result.setMsg("添加失败");
        }
        return result;
    }

    /**
     * 删除权限
     * @param id
     * @return
     */
    @RequestMapping(value = "/delPersonnel",method = RequestMethod.POST)
    @ResponseBody
    public Result delPersonnel(Integer id) {
        Result result = new Result();
        try {
            boolean ishas=adminAudioService.delPersonnel(id);
            if(ishas) {
                result.setResultCode(Result.SUCCESS);
                result.setMsg("删除成功");
            }else {
                result.setResultCode(Result.FAILURE);
                result.setMsg("删除失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setResultCode(Result.FAILURE);
            result.setMsg("添加失败");
        }
        return result;
    }

}



