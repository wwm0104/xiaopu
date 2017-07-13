package com.chinaxiaopu.xiaopuMobi.controller.admin.quartz;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.dto.admin.quartz.QuartzDto;
import com.chinaxiaopu.xiaopuMobi.quartz.QuartzUtils;
import com.chinaxiaopu.xiaopuMobi.service.admin.quartz.QuartzService;
import com.chinaxiaopu.xiaopuMobi.service.topic.EventRankService;
import com.chinaxiaopu.xiaopuMobi.vo.admin.quartz.QuartzVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

/**
 * 姓名 ：武宁
 * 日期 ：2016/11/15
 * 备注 ： 定时器管理Controller
 */
@Slf4j
@Controller
@RequestMapping("/admin/quartz")
public class QuartzController {
    @Autowired
    private QuartzService quartzService;
    @Autowired
    private EventRankService eventRankService;
    @Autowired
    private QuartzUtils quartzUtils;

    private static final String TOLIST = "admin/quartz/quartzList";//跳转定时器管理页面

    /**
     * 武宁
     * 跳转到定时器管理页面
     *
     * @return
     */
    @RequestMapping(value = "/toList", method = RequestMethod.GET)
    public String toList(Model model) {
        //boolean isStart = quartzUtils.isStart();
        //boolean isShut = quartzUtils.isShutDown();
        //model.addAttribute("isStart",isStart);
        //model.addAttribute("isShut",isShut);
        return TOLIST;
    }

    /**
     * 武宁
     * 查询定时器列表
     *
     * @param quartzDto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<QuartzVo>> list(@RequestBody QuartzDto quartzDto) {
        Result<PageInfo<QuartzVo>> result = new Result<>();

        try {
            result.setObj(quartzService.list(quartzDto));
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error(" 查询 查询定时器列表 失败", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }


    /**
     * 武宁
     * 手动运行定时器
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/quartz", method = RequestMethod.POST)
    public Result Quartz(@RequestBody QuartzDto quartzDto) {
        String[] params = quartzDto.getParameter().split(",");
        Result result = new Result<>();
        try {
            if (Arrays.asList(params).contains("1")) {
                eventRankService.eventDay();
            }
            if (Arrays.asList(params).contains("2")) {
                eventRankService.eventWeek();
            }
            if (Arrays.asList(params).contains("3")) {
                eventRankService.eventMonth();

            }
            if (Arrays.asList(params).contains("4")) {
                eventRankService.votesDay();

            }
            if (Arrays.asList(params).contains("5")) {
                eventRankService.votesWeek();

            }
            if (Arrays.asList(params).contains("6")) {
               eventRankService.votesMonth();

            }
            if (Arrays.asList(params).contains("7")) {
                eventRankService.userBufu();

            }
            if (Arrays.asList(params).contains("8")) {
                eventRankService.userDaren();
            }
            if (Arrays.asList(params).contains("9")) {
                eventRankService.VotesPool();
            }
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("手动运行定时器 失败", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 武宁
     * 删除定时任务
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/removeJob", method = RequestMethod.POST)
    public Result removeJob(@RequestBody QuartzVo quartzVo) {
        Result result = new Result<>();
        try {
           quartzUtils.new_removeJob(quartzVo.getJobName().trim(),quartzVo.getTriggerGroupName().trim(),quartzVo.getGroupName().trim());
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("删除 定时任务 失败", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 武宁
     * 暂停定时器任务
     *
     * @param quartzVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pauseJob", method = RequestMethod.POST)
    public Result pauseJob(@RequestBody QuartzVo quartzVo) {
        Result result = new Result<>();
        try {
            quartzUtils.new_pauseJob(quartzVo.getJobName().trim(),quartzVo.getGroupName().trim());
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("暂停  定时器任务 失败", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 武宁
     * 恢复定时器任务
     *
     * @param quartzVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resumeJob", method = RequestMethod.POST)
    public Result resumeJob(@RequestBody QuartzVo quartzVo) {
        Result result = new Result<>();
        try {
            quartzUtils.new_resumeJob(quartzVo.getJobName().trim(),quartzVo.getGroupName().trim());
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("恢复  定时器任务 失败", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 武宁
     * 修改任务触发时间
     *
     * @param quartzVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modifyTime", method = RequestMethod.POST)
    public Result modifyTime(@RequestBody QuartzVo quartzVo) {
        Result result = new Result<>();
        try {
            quartzUtils.new_modifyTime(quartzVo.getJobName().trim(), quartzVo.getCronExpression().trim(),quartzVo.getTriggerGroupName().trim());
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("修改任务触发时间 失败", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 开始调度
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public Result start() {
        Result result = new Result<>();
        try {
            quartzUtils.start();
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("开始调度 失败", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 停止调度
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/shutdowns", method = RequestMethod.POST)
    public Result shutdown() {
        Result result = new Result<>();
        try {
            quartzUtils.shutdown();
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("开始调度  失败", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }


}
