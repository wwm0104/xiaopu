package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.TicketInfoDto;
import com.chinaxiaopu.xiaopuMobi.exception.UnknownLoginException;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.TicketService;
import com.chinaxiaopu.xiaopuMobi.vo.TicketVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Wang
 * date: 16/12/01
 */

@Slf4j
@RestController
@RequestMapping("/ticket")
public class TicketController extends ContextController{
	
	@Autowired
	private TicketService ticketService;

    @ApiOperation(value = "门票详情", notes = "门票详情\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value="/authApi/info", method = RequestMethod.POST)
    public Result<TicketVo> ticketInfo(@RequestBody TicketInfoDto ticketInfoDto) throws Exception {
        Result<TicketVo> result= new Result<>();
        try {
            log.debug("POST请求 门票详情");
            UserInfo userInfo = getCurrentUser(ticketInfoDto);
            TicketVo ticketVo = ticketService.ticketInfo(ticketInfoDto,userInfo);
            result.setObj(ticketVo);
            result.setResultCode(Result.SUCCESS);
        } catch (UnknownLoginException ue) {
            log.error("门票详情->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
        	log.error("POST请求 门票详情失败",e);
            result.setMsg(Result.SERVER_EXCEPTION);
        	result.setResultCode(Result.FAILURE);
        }

        return result;
    }
}
