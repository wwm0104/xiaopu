package com.chinaxiaopu.xiaopuMobi.service;

import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import com.chinaxiaopu.xiaopuMobi.dto.TicketInfoDto;
import com.chinaxiaopu.xiaopuMobi.mapper.TicketMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.UserTicketMapper;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.model.UserTicket;
import com.chinaxiaopu.xiaopuMobi.util.QrCodeUtil;
import com.chinaxiaopu.xiaopuMobi.vo.TicketVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by Wang on 2016/12/01.
 */
@Slf4j
@Service
public class TicketService extends AbstractService{

    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
	private UserTicketMapper userTicketMapper;
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	private OSSClientService ossClientService;

    /**
     * getRemainingTicket
     * @param businessId,businessType
     * @return
     */
	public Integer getRemainingTicket(Integer businessId,Integer businessType) {
		Integer remainingTicketCnt = ticketMapper.getRemainingTicket(businessId,businessType);
		return remainingTicketCnt;
	}

	public PageInfo<TicketVo> ticketList(ContextDto contextDto, UserInfo userInfo) {
		if (!StringUtils.isEmpty(contextDto.getPage()) && !StringUtils.isEmpty(contextDto.getRows())) {
			PageHelper.startPage(contextDto.getPage(), contextDto.getRows());
		}
		List<TicketVo> ticketList = ticketMapper.ticketList(userInfo.getUserId());
		PageInfo<TicketVo> pageInfo = new PageInfo<>(ticketList);

		return pageInfo;
	}

	public TicketVo ticketInfo(TicketInfoDto ticketInfoDto, UserInfo userInfo) throws Exception {
		TicketVo ticketVo = ticketMapper.ticketInfo(ticketInfoDto.getTicketId(),userInfo.getUserId());
		//已扫码、已失效都将二维码置为null
		if ( (!StringUtils.isEmpty(ticketVo) && ticketVo.getTicketStatus() == 1) ||  (!StringUtils.isEmpty(ticketVo) && ticketVo.getTicketStatus() == 2)) {
			ticketVo.setTicketQrcode(null);
		} else if (!StringUtils.isEmpty(ticketVo) && ticketVo.getTicketStatus() == 0) {
			UserTicket userTicket = userTicketMapper.selectByPrimaryKey(ticketVo.getId());
			if(ticketVo.getEndTime().getTime()<new Date().getTime()){
				userTicket.setStatus(2);
				userTicketMapper.updateByPrimaryKeySelective(userTicket);
				ticketVo.setTicketStatus(2);
				ticketVo.setTicketQrcode(null);
				return ticketVo;
			}
			//生成二维码
			if (StringUtils.isEmpty(ticketVo.getTicketQrcode())) {
				getTicketQrCodePath(ticketVo.getId(),userInfo.getUserId(),ticketVo.getTicketId(),ticketVo.getEventId(),ticketVo.getBusinessType());
				userTicket.setQrcode(getTicketQrCodePath(ticketVo.getId()));
				ticketVo.setTicketQrcode(getTicketQrCodePath(ticketVo.getId()));
				//更新二维码
				userTicketMapper.updateByPrimaryKeySelective(userTicket);
			}
		}
		return ticketVo;
	}

	private String getTicketQrCodePath(Integer id,Integer userId,Integer ticketId,Integer businessId,Integer businessType) throws Exception {
		Map<String,Object> map = new HashMap<>();
		map.put("ticketUserId",userId);
		map.put("ticketId",ticketId);
		map.put("businessId",businessId);
		map.put("businessType",businessType);
		map.put("type", Constants.QR_CODE_TYPE_TICKET);

		String ticketQrcodeJson =  new Gson().toJson(map);

		String filePath = systemConfig.getImgBasePath() + "ticket/ticket_qr_" + id;
		String qrCodeStr = QrCodeUtil.getLogoQRCode(ticketQrcodeJson, "", filePath);
		//二维码图片上传至OSS服务器
		ossClientService.putOSSByFilePath(getTicketQrCodePath(id), filePath + ".png");
		return qrCodeStr;
	}

	private String getTicketQrCodePath(Integer id) {
		return "ticket_qr_" + id + ".png";
	}

    public Integer isNeedTicket(Integer businessId, Integer businessType) {
		return ticketMapper.isNeedTicket(businessId,businessType);
    }

	public Integer getSignCnt(Integer businessId, Integer businessType) {
		return ticketMapper.getSignCnt(businessId,businessType);
	}
}
