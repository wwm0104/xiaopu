package com.chinaxiaopu.xiaopuMobi.controller.admin.topics;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.controller.ContextController;
import com.chinaxiaopu.xiaopuMobi.dto.PrizeTakeLogDto;
import com.chinaxiaopu.xiaopuMobi.model.AwardPresen;
import com.chinaxiaopu.xiaopuMobi.service.admin.topics.PrizeTakeLogService;
import com.chinaxiaopu.xiaopuMobi.service.topic.AawardPresenService;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.PrizeTakeLogVo;
import com.chinaxiaopu.xiaopuMobi.vo.admin.topics.PkTopicVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/8.
 */
@Slf4j
@Controller
@RequestMapping("/admin/PrizeTake")
public class PrizeTakeConttroller extends ContextController {
    @Autowired
    private PrizeTakeLogService prizeTakeLogService;

    @Autowired
    private AawardPresenService awardPresenService;

    @RequestMapping("/list")
    public String index() {
        return "topics/prizeTakeList";
    }

    @RequestMapping("/gopresenList")
    public String presenList() {
        return "topics/awardPerson";
    }

    /**
     * 获取
     *
     * @param prizeTakeLogDto
     * @return
     */
    @RequestMapping(value = "/getPrizeTakeList", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<PrizeTakeLogVo>> getPrizeTakeList(@RequestBody PrizeTakeLogDto prizeTakeLogDto) {
        String startTime = "";
        String endTime = "";
        String takeStartTime = "";
        String takeEndTime = "";
        if (StrUtils.isNotEmpty(prizeTakeLogDto.getTime())) {
            String time = prizeTakeLogDto.getTime();
            startTime = time.split("/")[0];
            endTime = time.split("/")[1];
            prizeTakeLogDto.setStartTime(startTime);
            prizeTakeLogDto.setEndTime(endTime);
        }
        if (StrUtils.isNotEmpty(prizeTakeLogDto.getTakeTime())) {
            String time = prizeTakeLogDto.getTakeTime();
            takeStartTime = time.split("/")[0];
            takeEndTime = time.split("/")[1];
            prizeTakeLogDto.setTakeStartTime(takeStartTime);
            prizeTakeLogDto.setTakeEndTime(takeEndTime);
        }

        Result<PageInfo<PrizeTakeLogVo>> result = new Result<>();
        try {
            result.setObj(prizeTakeLogService.getPrizeTakeList(prizeTakeLogDto));
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("获取发奖记录失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @RequestMapping(value = "/getTakeDetails/{id}", method = RequestMethod.GET)
    public String selectDetails(@PathVariable("id") final int id, Model model) {
        PrizeTakeLogVo prizeTakeLogVo = prizeTakeLogService.selectDetails(id);
        prizeTakeLogVo.setImgUrl(imagesPath());
        model.addAttribute("takeDetail", prizeTakeLogVo);
        model.addAttribute("url1", "/admin/topics/goAllChanllengeList/" + prizeTakeLogVo.getTopicId());
        return "topics/prizeTakeDetail";
    }


    @RequestMapping(value = "/getAwardPresen", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<AwardPresen>> selectAllAwardPresen(@RequestBody AwardPresen awardPresen) {
        Result<PageInfo<AwardPresen>> result = new Result<>();
        try {
            PageInfo<AwardPresen> pageInfo = awardPresenService.selectAllAwardPresen(awardPresen);
            result.setObj(pageInfo);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
            log.error("获取发奖记录详情失败");
        }
        return result;
    }

    @RequestMapping(value = "/checkUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result<Map<String, Object>> checkUserInfo(@RequestBody AwardPresen awardPresen) {
        Result<Map<String, Object>> result = new Result();
        try {
            int count = awardPresenService.checkPhone(awardPresen.getRealName(), awardPresen.getMobile());
//            Map<String,Object> map = new HashMap<String,Object>();
//            map.put("num",count);
//            result.setObj(map);
            result.setResultCode(count);
        } catch (Exception e) {
            log.error("检查用户信息失败");
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @RequestMapping(value = "/createPresen", method = RequestMethod.POST)
    @ResponseBody
    public Result createPresen(@RequestBody AwardPresen awardPresen) {
        Result result = new Result();
        try {
            awardPresen.setUserId(awardPresenService.checkUserInfo(awardPresen.getRealName(), awardPresen.getMobile()));
            awardPresenService.createPresen(awardPresen);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("创建发奖人失败");
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @RequestMapping(value = "/updatePresen", method = RequestMethod.POST)
    @ResponseBody
    public Result updatePresen(@RequestBody AwardPresen awardPresen) {
        Result result = new Result();
        try {
            awardPresenService.updatePresen(awardPresen);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("修改状态失败");
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @RequestMapping(value = "/toAwardList/{userId}", method = RequestMethod.GET)
    public String toAwardList(@PathVariable("userId") final int userId, Model model) {
        /**
         * 查询发奖人信息
         */
        List<AwardPresen> AwardPreseList = awardPresenService.selectAwardPerson(userId);
        model.addAttribute("AwardPreseList", AwardPreseList);
        model.addAttribute("userId", userId);
        return "topics/prizeTakeList";
    }

    @RequestMapping(value = "/pkLinShiFinsh", method = RequestMethod.POST)
    @ResponseBody
    public Result<List<PkTopicVo>> selecAllPkId(Model model) {
        Result<List<PkTopicVo>> result = new Result();
        try {
            List<PkTopicVo> pkList = prizeTakeLogService.selecAllPkId();
            result.setObj(pkList);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("修改状态失败");
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @RequestMapping(value = "/topkLinShiFinsh", method = RequestMethod.GET)
    public String toselecAllPkId(Model model) {
        return "admin/topic/linShiPk";
    }

}
