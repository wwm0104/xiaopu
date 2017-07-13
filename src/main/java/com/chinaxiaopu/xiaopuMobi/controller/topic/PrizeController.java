package com.chinaxiaopu.xiaopuMobi.controller.topic;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.controller.ContextController;
import com.chinaxiaopu.xiaopuMobi.dto.topic.PrizeListDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.PrizeTakeDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.PrizeViewDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.TicketTakeDto;
import com.chinaxiaopu.xiaopuMobi.exception.UnknownLoginException;
import com.chinaxiaopu.xiaopuMobi.mapper.EventMapper;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.chinaxiaopu.xiaopuMobi.service.EventService;
import com.chinaxiaopu.xiaopuMobi.service.topic.AawardPresenService;
import com.chinaxiaopu.xiaopuMobi.service.topic.PrizeService;
import com.chinaxiaopu.xiaopuMobi.service.topic.PrizeTakeService;
import com.chinaxiaopu.xiaopuMobi.service.topic.TopicsCreateService;
import com.chinaxiaopu.xiaopuMobi.vo.topic.PrizeViewVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.PrizeVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.PrizesListVo;
import com.github.pagehelper.PageInfo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.PrizesRecommVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.TopicIndexVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Wang on 2016/11/2.
 */

@Slf4j
@RestController
@RequestMapping("/prize")
public class PrizeController extends ContextController{

    @Autowired
    private TopicsCreateService topicsCreateService;
    @Autowired
    private PrizeService prizeService;

    @Autowired
    private PrizeTakeService prizeTakeService;

    @Autowired
    private AawardPresenService aawardPresenService;

    @Autowired
    private EventService eventService;


    @ApiOperation(value = "根据类型获取该类型下奖励列表-乐传阳", notes = "根据类型获取该类型下奖励列表\n" +
            "（传入参数Type即可）" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/listByType", method = RequestMethod.POST)
    public Result listByType(@RequestBody Prize prize) {
        Result<List<PrizeVo>> result = new Result<List<PrizeVo>>();
        List<PrizeVo> prizeNameList = null;
        try {
            prizeNameList = topicsCreateService.getPrizesListByType(prize);
            result.setObj(prizeNameList);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
            log.error("获取奖励列表失败", e);
        }
        return result;
    }


    @ApiOperation(value = "获取首页奖励列表-乐传阳", notes = "获取首页奖励列表\n" +
            "（orderType：1：来战倒序；2：来战正序，3：投票倒序，4：投票正序；isHaveChance：1:有机会  2：错过;prizeName:模糊的信息）" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/getPrizeList", method = RequestMethod.POST)
    public Result getPrizeList(@RequestBody PrizeListDto prizeListDto) {
        Result<List<PrizesListVo>> result = new Result<List<PrizesListVo>>();
        List<PrizesListVo> prizeList = null;
        try {
            PageInfo<PrizesListVo> pageInfo = prizeService.selectPrizeList(prizeListDto);
            if(pageInfo.getList() == null || pageInfo.getList().size() == 0 || prizeListDto.getPage()>pageInfo.getPages() ){
                result.setMsg(Result.NO_DATA);
                result.setResultCode(Constants.DATA_NO);
                return result;
            }
            result.setObj(pageInfo.getList());
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
            log.error("获取奖励列表失败", e);
        }
        return result;
    }

//    @RequestMapping(value = "/view/{id}", method = RequestMethod.POST)
//    public Result view(@PathVariable("id") final int id) {
//        Prize prize = new Prize();
//        Result<Prize> result = new Result<Prize>();
//        result.setObj(prize);
//        return result;
//    }

    @ApiOperation(value = "奖励详情--Wang", notes = "奖励详情，用于查看我的奖励中的奖励详情\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/authApi/myPrize/view", method = RequestMethod.POST)
    public Result<PrizeViewVo> myPrizeView(@RequestBody PrizeViewDto prizeViewDto) {
        Result<PrizeViewVo> result = new Result<>();
        try {
            log.debug("奖励详情");
            UserInfo userInfo = getCurrentUser(prizeViewDto);
            PrizeViewVo prizeViewVo = prizeService.myPrizeView(prizeViewDto,userInfo);
            result.setObj(prizeViewVo);
            result.setResultCode(Result.SUCCESS);
        } catch (UnknownLoginException ue) {
            log.error("确认用户能否参加活动->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("奖励详情 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }


    @ApiOperation(value = "根据奖品id查询奖品详情--王运豪", notes = "奖品详情，根据奖品id查询奖品详情\n" +
            "接收值：prizeId（必填） 返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/getPrizeDetail/{id}", method = RequestMethod.POST)
    public Result<PrizeVo> getPrizeById(@PathVariable("id") final int id){
        Result<PrizeVo> result = new Result<>();
        try {
            log.debug("获取奖品详情");
            if(StringUtils.isEmpty(id)){
                result.setMsg("奖品id不能为空！");
                result.setResultCode(Result.FAILURE);
                return result;
            }
            PrizeVo prizeVo = prizeService.getPrizeDetailById(id);
            result.setObj(prizeVo);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("获取奖品详情 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }




    @ApiOperation(value = "扫码接口--乐传阳", notes = "（type = 1 领取图文pk奖励，入参包括登录验证，pkId，获奖人id/topicId\n）" +
            "（type = 2  验证电子门票 入参包括：登录验证、ticketUserId：持码人id、  ticketId：门票id、   businessId：业务id、  businessType：业务类型）" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/authApi/myPrize/takePrize", method = RequestMethod.POST)
    public Result takePrize(@RequestBody Map<String,Object> map) {
        Result result = new Result<>();
        if(map.get("type") == null || (int)map.get("type") == 1) {
            //领取图文pk奖励
            result = takePrizeByCode(map);

        }
        if((int)map.get("type") == 2) {
            //验证电子门票
            result = takeTicketByCode(map);
        }
        return result;
    }


    /**
     * 检验电子门票
     * @param map
     * @return
     */
    public Result takeTicketByCode(Map<String,Object> map){
        Result result = new Result<>();
        try {
            //校验扫码人是否是社长
            TicketTakeDto ticketTakeDto = new TicketTakeDto();
            ticketTakeDto.setBusinessId((int)map.get("businessId"));
            ticketTakeDto.setBusinessType((int)map.get("businessType"));
            ticketTakeDto.setTicketId((int)map.get("ticketId"));
            ticketTakeDto.setTicketUserId((int)map.get("ticketUserId"));
            ticketTakeDto.setAccessToken((String) map.get("accessToken"));
            ticketTakeDto.setClientDigest((String) map.get("clientDigest"));
            UserInfo userInfo = getCurrentUser(ticketTakeDto);
            ticketTakeDto.setUserId(userInfo.getUserId());
            if(ticketTakeDto.getBusinessType() == 2) {
                Event event = eventService.getEventInfoByEvent(ticketTakeDto.getBusinessId());
                if(event.getStartTime().getTime()>(new Date().getTime()+2*60*60*1000)){
                    result.setMsg("活动尚未开始");
                    result.setResultCode(Result.FAILURE);
                    return result;
                }
                if(event.getEndTime().getTime()<new Date().getTime()){
                    result.setMsg("活动已结束");
                    result.setResultCode(Result.FAILURE);
                    return result;
                }
                //官方活动
                if (event.getCreatorId() == 0) {
                    if (aawardPresenService.checkIsAwardPerson(userInfo.getUserId()) == 0) {
                        result.setMsg("用户不是发奖人");
                        result.setResultCode(Result.FAILURE);
                        return result;
                    }
                } else {
                    if (!(userInfo.getUserId()).equals(event.getCreatorId())) {
                        result.setMsg("用户不是本次活动的发起人");
                        result.setResultCode(Result.FAILURE);
                        return result;
                    }
                }

                //检测用户门票是否正确
                UserTicket userTicket = prizeTakeService.selectUserTicket(ticketTakeDto);
                if (userTicket == null) {
                    result.setMsg("不是本次活动的二维码");
                    result.setResultCode(Result.FAILURE);
                    return result;
                }
                if (userTicket.getStatus() == 1) {
                    result.setMsg("用户二维码已使用");
                    result.setResultCode(Result.FAILURE);
                    return result;
                }
                if (userTicket.getStatus() == 0) {
                    prizeTakeService.updateStatus(ticketTakeDto);
                    result.setResultCode(Result.SUCCESS);
                    result.setMsg("签到成功");
                }
            }
        }catch (Exception e){
            log.error("检验电子门票失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }


    /**
     * //领取图文pk奖励
     * @param map
     * @return
     */
    public Result takePrizeByCode(Map<String,Object> map){
        Result result = new Result<>();
        try {
            log.debug("奖励详情");
            PrizeTakeDto prizeTakeDto = new PrizeTakeDto();
            prizeTakeDto.setRewardUserId((int)map.get("rewardUserId"));
            prizeTakeDto.setPkId((int)map.get("pkId"));
            //prizeTakeDto.setTopicId((int)map.get("topicId"));
            prizeTakeDto.setAccessToken((String)map.get("accessToken"));
            prizeTakeDto.setClientDigest((String)map.get("clientDigest"));
            UserInfo userInfo = getCurrentUser(prizeTakeDto);
            prizeTakeDto.setAwardUserId(userInfo.getUserId());
            if (aawardPresenService.checkIsAwardPerson(userInfo.getUserId()) == 0) {
                result.setMsg("用户不是发奖人");
                result.setResultCode(Result.FAILURE);
                return result;
            }
            if (prizeTakeService.checkIsTake(prizeTakeDto) == 1) {
                result.setMsg("用户已经领取过奖品");
                result.setResultCode(Result.FAILURE);
                return result;
            }
            prizeTakeDto.setAwardUserId(userInfo.getUserId());
            prizeTakeService.takePrize(prizeTakeDto);
            result.setResultCode(Result.SUCCESS);
            result.setMsg("领奖成功");
        } catch (Exception e) {
            log.error("领奖失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }


    @ApiOperation(value = "首页奖品推荐--武宁", notes = "首页奖品推荐\n" +
            "返回值：0代表失败，1代表成功,41代表没有数据")
    @RequestMapping(value = "/recommentPrizes", method = RequestMethod.POST)
    public Result recommentPrizes(){
        Result<List<PrizesRecommVo>> result = new Result<>();
        try {
            log.debug("POST请求 获取首页奖品推荐");
            List<PrizesRecommVo> list = prizeService.recommentPrizes();
            result.setObj(list);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("POST请求  获取首页奖品推荐 失败", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

}