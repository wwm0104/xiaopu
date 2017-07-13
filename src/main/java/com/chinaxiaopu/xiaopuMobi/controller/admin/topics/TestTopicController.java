package com.chinaxiaopu.xiaopuMobi.controller.admin.topics;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.model.TopicPk;
import com.chinaxiaopu.xiaopuMobi.quartz.QuartzUtils;
import com.chinaxiaopu.xiaopuMobi.service.topic.PkFinishPrizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ycy on 2016/11/14.
 */
@Slf4j
@Controller
@RequestMapping("/admin/prize")
public class TestTopicController {

    @Autowired
    private QuartzUtils quartzUtils;
    @Autowired
    private PkFinishPrizeService pkFinishPrizeService;

    @RequestMapping(value = "/finish", method = RequestMethod.POST)
    @ResponseBody
    public Result takePrizeTest(@RequestBody TopicPk topicPk){
        Result result = new Result();
        try {
            pkFinishPrizeService.updatePkFinish(topicPk.getId());
            result.setResultCode(Result.SUCCESS);
            quartzUtils.new_removeJob("计算获奖结果->"+topicPk.getId(),"EXTJWEB_TRIGGERGROUP_NAME","EXTJWEB_JOBGROUP_NAME");
        }catch (Exception e){
            log.error("手动结束PK出错:",e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }
}
