package com.chinaxiaopu.xiaopuMobi.controller.admin.rcomment;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.dto.admin.channel.ChannelRecommend;
import com.chinaxiaopu.xiaopuMobi.model.Recommend;
import com.chinaxiaopu.xiaopuMobi.service.admin.recomment.RecommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 推荐或置顶Controller
 * Created by 武宁 on 2016/12/7.
 */
@Slf4j
@Controller
@RequestMapping("/admin/recomment")
public class RecommentController {
    @Autowired
    private RecommentService recommentService;

    /**
     * 推荐 或  置顶
     *
     * @param recommend
     * @return
     */
    @RequestMapping(value = "/top", method = RequestMethod.POST)
    @ResponseBody
    public Result recommend(@RequestBody Recommend recommend) {
        Result result = new Result<>();
        try {
            int row = recommentService.recommend(recommend);
            if (row > 1) {
                result.setResultCode(Result.SUCCESS);
                result.setMsg("修改成功");
            } else {
                result.setResultCode(Result.FAILURE);
                result.setMsg("修改失败 FAIL");
            }
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("修改失败 FAIL ", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;

    }
}
