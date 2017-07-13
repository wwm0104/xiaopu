package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import com.chinaxiaopu.xiaopuMobi.dto.eventLottery.EventLotteryLoadInfoDto;
import com.chinaxiaopu.xiaopuMobi.service.EventLotteryService;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.eventLottery.EventLotteryLoadInfoVo;
import com.chinaxiaopu.xiaopuMobi.vo.eventLottery.EventLotteryVo;
import com.chinaxiaopu.xiaopuMobi.vo.eventLottery.LotteryListVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 活动抽奖
 * Created by hao on 2016/12/10.
 */
@Slf4j
@Controller
@RequestMapping("/eventLottery")
public class EventLotteryController extends ContextController{
    @Autowired
    private EventLotteryService eventLotteryService;

    @ApiOperation(value = "抽奖页面初始化数据--武宁", notes = "抽奖页面初始化数据")
    @RequestMapping(value = "/lottery/loadInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result<EventLotteryLoadInfoVo> loadInfo(@RequestBody EventLotteryLoadInfoDto eventLotteryLoadInfoDto) {
        Result<EventLotteryLoadInfoVo> result = new Result<>();
        try {
            log.debug("POST请求 抽奖页面初始化数据");
            //判断是否存在活动ID
            if(StrUtils.isEmpty(eventLotteryLoadInfoDto.getEventId())){
                result.setMsg("活动ID不能为空");
                result.setResultCode(Result.FAILURE);
                return result;
            }
            //判断抽奖是否结束
            int row =eventLotteryService.selectEventIsOver(eventLotteryLoadInfoDto);

            if(row == Result.FAILURE){ //系统出错
                result.setMsg(Result.SERVER_EXCEPTION);
                result.setResultCode(Result.FAILURE);
                return result;
            }else if(row==Constants.EVENTlOTTERY_101){ //正常抽奖
                EventLotteryLoadInfoVo eventLotteryLoadInfoVo = eventLotteryService.loadInfo(eventLotteryLoadInfoDto);
                result.setObj(eventLotteryLoadInfoVo);
                result.setResultCode(Result.SUCCESS);
            }else if(row==Constants.EVENTlOTTERY_102){
                result.setMsg(Constants.EVENTlOTTERY_102_MSG);
                result.setResultCode(Constants.EVENTlOTTERY_102);  //抽奖结束
                return result;
            }else if(row == -1){ //不存在当前数据
                result.setMsg(Constants.EVENTlOTTERY_1_MSG);
                result.setResultCode(Result.FAILURE);
                return result;
            }
        } catch (Exception e) {
            log.error("POST请求  抽奖页面初始化数据", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "根据活动id查询获奖轮次和用户列表-王运豪", notes = "根据活动id查询获奖轮次和用户列表\n" +
            "接收值：eventId(必填)\t"+"返回值：0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/getUserListByEventId/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Result<LotteryListVo> getUserListByEventId(@PathVariable("id") final int id){
        Result<LotteryListVo> result=new Result<>();
        try {
            log.debug("POST请求 获奖用户列表");
            if(StringUtils.isEmpty(id)){
                result.setResultCode(Result.FAILURE);
                result.setMsg("活动id不能为空");
                return result;
            }
            LotteryListVo vo=eventLotteryService.getUserListByEventId(id);
            if(StringUtils.isEmpty(vo.getEventId())){
                result.setResultCode(Constants.DATA_NO);
                return result;
            }else{
                result.setObj(vo);
                result.setResultCode(Result.SUCCESS);
                return result;
            }
        }catch (Exception e) {
            log.error("POST请求  获奖用户列表", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "抽奖 - 武宁", notes = "抽奖")
    @RequestMapping(value = "/lottery/lotteryPrize", method = RequestMethod.POST)
    @ResponseBody
    public Result<EventLotteryLoadInfoVo> lotteryPrize(@RequestBody EventLotteryLoadInfoDto eventLotteryLoadInfoDto) {
        Result<EventLotteryLoadInfoVo> result = new Result<>();
        try {
            log.debug("POST请求 抽奖");
            //判断是否存在活动ID
            if(StrUtils.isEmpty(eventLotteryLoadInfoDto.getEventId())){
                result.setMsg("活动ID不能为空");
                result.setResultCode(Result.FAILURE);
                return result;
            }
            if(StrUtils.isEmpty(eventLotteryLoadInfoDto.getRound())){
                result.setMsg("轮次不能为空");
                result.setResultCode(Result.FAILURE);
                return result;
            }
            //判断抽奖是否结束
            int row =eventLotteryService.selectEventIsOver(eventLotteryLoadInfoDto);
            if(row == Result.FAILURE){ //系统出错
                result.setMsg(Result.SERVER_EXCEPTION);
                result.setResultCode(Result.FAILURE);
                return result;
            }else if(row==Constants.EVENTlOTTERY_101){ //正常抽奖
                EventLotteryLoadInfoVo eventLotteryLoadInfoVo = eventLotteryService.lotteryPrize(eventLotteryLoadInfoDto);
                result.setObj(eventLotteryLoadInfoVo);
                if(StrUtils.isEmpty(eventLotteryLoadInfoVo.getWinnersList())){
                    result.setResultCode(Constants.EVENTlOTTERY_100);
                    result.setMsg(Constants.EVENTlOTTERY_100_MSG);
                }else if(StrUtils.isEmpty(eventLotteryLoadInfoVo.getUserList())){
                    result.setResultCode(Constants.EVENTlOTTERY_101);
                    result.setMsg(Constants.EVENTlOTTERY_101_MSG);
                }else{
                    result.setResultCode(Result.SUCCESS);
                }
            }else if(row==Constants.EVENTlOTTERY_102){
                result.setMsg(Constants.EVENTlOTTERY_102_MSG);
                result.setResultCode(Constants.EVENTlOTTERY_102);  //抽奖结束
                return result;
            }
        } catch (Exception e) {
            log.error("POST请求  抽奖", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "结束抽奖 - 武宁", notes = "结束抽奖")
    @RequestMapping(value = "/lottery/lotteryOver", method = RequestMethod.POST)
    @ResponseBody
    public Result<EventLotteryLoadInfoVo> lotteryOver(@RequestBody EventLotteryLoadInfoDto eventLotteryLoadInfoDto) {
        Result<EventLotteryLoadInfoVo> result = new Result<>();
        try {
            log.debug("POST请求 结束抽奖");
            //判断是否存在活动ID
            if(StrUtils.isEmpty(eventLotteryLoadInfoDto.getEventId())){
                result.setMsg("活动ID不能为空");
                result.setResultCode(Result.FAILURE);
                return result;
            }
            //判断抽奖是否结束
            int row = eventLotteryService.lotteryOver(eventLotteryLoadInfoDto);
            result.setResultCode(Result.SUCCESS);
            result.setMsg("抽奖已结束");
        } catch (Exception e) {
            log.error("POST请求  结束抽奖", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @RequestMapping(value = "/event/listUI", method = RequestMethod.GET)
    public String eventLotteryList(){
        return "event/eventLotteryList";
    }

    @ApiOperation(value = "查出所有抽奖活动列表-Wang", notes = "查出所有抽奖活动列表\n" +
            "返回值：0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/event/list",method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<EventLotteryVo>> getEventLotteryList(@RequestBody ContextDto contextDto){
        Result<PageInfo<EventLotteryVo>> result = new Result<>();
        try {
            log.debug("POST请求 查出所有抽奖活动列表");
            PageInfo<EventLotteryVo> pageInfo = eventLotteryService.getEventLotteryList(contextDto);
            result.setObj(pageInfo);
            result.setResultCode(Result.SUCCESS);
        }catch (Exception e) {
            log.error("POST请求  查出所有抽奖活动列表 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

}
