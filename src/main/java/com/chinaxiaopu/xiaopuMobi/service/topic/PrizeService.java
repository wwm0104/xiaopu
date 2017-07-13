package com.chinaxiaopu.xiaopuMobi.service.topic;

import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.UserInfoListDto;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.PrizesDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.PrizeAvailableDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.PrizeDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.PrizeListDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.PrizeViewDto;
import com.chinaxiaopu.xiaopuMobi.mapper.PkPrizeResultMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.PrizeMapper;
import com.chinaxiaopu.xiaopuMobi.model.Prize;
import com.chinaxiaopu.xiaopuMobi.model.Topic;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.OSSClientService;
import com.chinaxiaopu.xiaopuMobi.util.QrCodeUtil;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.admin.topics.PrizesVo;
import com.chinaxiaopu.xiaopuMobi.vo.admin.topics.TopicIndexVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.PrizeListVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.PrizeViewVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.PrizeVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.PrizesListVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.PrizesRecommVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wang on 2016/11/2.
 */
@Service
public class PrizeService {

    @Autowired
    private PkPrizeResultMapper pkPrizeResultMapper;
    @Autowired
    private PrizeMapper prizeMapper;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private OSSClientService ossClientService;

    public PrizeViewVo myPrizeView(PrizeViewDto prizeViewDto, UserInfo userInfo) throws Exception {
        PrizeViewVo prizeViewVo = pkPrizeResultMapper.selectByPrizeIdAndUserId(prizeViewDto.getPrizeId(), userInfo.getUserId(), prizeViewDto.getTopicId());
        if (!StringUtils.isEmpty(prizeViewVo) && prizeViewVo.getHasTake() == 1) {
            prizeViewVo.setCode(null);
        } else if (!StringUtils.isEmpty(prizeViewVo) && prizeViewVo.getHasTake() == 0) {
            //生成二维码
            if (StringUtils.isEmpty(prizeViewVo.getCode())) {
                prizeViewVo.setRewardUserId(userInfo.getUserId());
                genQrCode(prizeViewVo);
                prizeViewVo.setCode(getEventQrCodePath(prizeViewVo.getTopicId()));
                //更新二维码
                pkPrizeResultMapper.updateCodeByPkId(prizeViewVo.getPkId(), prizeViewVo.getCode());

            }

        }
        return prizeViewVo;
    }

    public PrizeListVo prizeList(UserInfo userInfo) {
        PrizeListVo prizeListVo = new PrizeListVo();

        List<PrizeViewVo> cashList = pkPrizeResultMapper.selectByUserId(userInfo.getUserId(), 1);
        prizeListVo.setCashList(cashList);

        List<PrizeViewVo> physicalList = pkPrizeResultMapper.selectByUserId(userInfo.getUserId(), 2);
        prizeListVo.setPhysicalList(physicalList);

        List<PrizeViewVo> virtualList = pkPrizeResultMapper.selectByUserId(userInfo.getUserId(), 3);
        prizeListVo.setVirtualList(virtualList);

        List<PrizeViewVo> redPacketList = pkPrizeResultMapper.selectByUserId(userInfo.getUserId(), 4);
        prizeListVo.setRedPacketList(redPacketList);

        Integer cashNum = 0;
        for (PrizeViewVo prizeViewVo : cashList) {
            cashNum += ((Double) prizeViewVo.getPrizeMap().get("money")).intValue();
        }
        prizeListVo.setCashNum(cashNum);

        return prizeListVo;
    }

    public PageInfo<PrizeVo> prizeList(PrizeDto prizeDto) {
        if (!StringUtils.isEmpty(prizeDto.getPage()) && !StringUtils.isEmpty(prizeDto.getRows())) {
            PageHelper.startPage(prizeDto.getPage(), prizeDto.getRows());
        }
        List<PrizeVo> prizeList = prizeMapper.selectPrizeList(prizeDto.getType(), prizeDto.getStatus(), prizeDto.getIsPublic(), prizeDto.getSort());
        PageInfo<PrizeVo> pageInfo = new PageInfo<>(prizeList);

        return pageInfo;
    }

    public Boolean create(Prize prize) {
        Boolean result = false;
        Integer count = prizeMapper.insertSelective(prize);
        if (count == 1) {
            result = true;
        }
        return result;
    }
    //后台获取奖品详情
    public PrizeVo prizeDetail(int id) {
        return prizeMapper.selectByPrizeId(id);
    }
    //app根据奖品id获取奖品详情
    public PrizeVo getPrizeDetailById(int id) {
        Gson gson = new Gson();
        PrizeVo pvo = prizeMapper.selectByPrizeId(id);
        if(StringUtils.isEmpty(pvo)){
            return pvo;
        }
        pvo.setDesc(pvo.getPrizeMap().get("desc").toString());
        pvo.setImgs(pvo.getPrizeMap().get("imgs").toString().split(","));
        String rule = pvo.getPrizeMap().get("rewardRule").toString();
        Map<String,Object> ruleMap =gson.fromJson(rule,Map.class);
        double voteCnt = (double)ruleMap.get("voteCnt");
        double challengeCnt =(double)ruleMap.get("challengeCnt");
        pvo.setVoteCnt((int)voteCnt);
        pvo.setChallengeCnt((int)challengeCnt);
        return pvo;
    }

    public Boolean enableOrDisable(PrizeAvailableDto prizeAvailableDto) {
        Boolean result = false;
        Prize prize = prizeMapper.selectPrizeById(prizeAvailableDto.getPrizeId());
        if (prize.getAvailable() == prizeAvailableDto.getAvailable()) {
            return result;
        }
        prize.setAvailable(prizeAvailableDto.getAvailable());
        int count = prizeMapper.updateByPrimaryKeySelective(prize);
        if (count == 1) {
            result = true;
        }
        return result;
    }


    private String genQrCode(PrizeViewVo prizeViewVo) throws Exception {
        String filePath = systemConfig.getImgBasePath() + "prize/prize_qr_" + prizeViewVo.getTopicId();
        Gson gson = new Gson();
        Map<String, Integer> map = new HashMap<>();
        map.put("pkId", prizeViewVo.getPkId());
        map.put("rewardUserId", prizeViewVo.getRewardUserId());
        map.put("type", Constants.QR_CODE_TYPE_PRIZE);
        String qrcodeContext = gson.toJson(map);
        String qrCodeStr = QrCodeUtil.getLogoQRCode(qrcodeContext, "", filePath);
        //二维码图片上传至OSS服务器
//        getEventQrCodePath(prizeViewVo.getTopicId());
        ossClientService.putOSSByFilePath(getEventQrCodePath(prizeViewVo.getTopicId()), filePath + ".png");
        return qrCodeStr;
    }

    private String getEventQrCodePath(Integer topicId) {
        return "prize_qr_" + topicId + ".png";
    }

    @Transactional
    public void prizeScan() {
        //扫描所有失效的奖品
        List<Prize> prizeList = prizeMapper.prizeScan();
        for (Prize prize : prizeList) {
            prize.setAvailable(0);
            //修改 奖品的状态为0，即停用
            prizeMapper.updateByPrimaryKeySelective(prize);
        }
    }

    /**
     * 武宁
     * 首页奖品推荐
     *
     * @return
     */
    public List<PrizesRecommVo> recommentPrizes() {
        List<PrizesRecommVo> list = prizeMapper.recommentPrizes();
        for (PrizesRecommVo vo : list) {
            vo.setPrizeMap(vo.getPrize());
            vo.setPrize("");
        }
        return list;
    }


    public PageInfo<PrizesListVo> selectPrizeList(PrizeListDto prizeListDto) {
        if (!StringUtils.isEmpty(prizeListDto.getPage()) && !StringUtils.isEmpty(prizeListDto.getRows())) {
            PageHelper.startPage(prizeListDto.getPage(), prizeListDto.getRows());
        }
        List<PrizesListVo> prizeList = prizeMapper.selectAllPrizeAndPkCount(prizeListDto);
        PageInfo<PrizesListVo> pageInfo =new PageInfo<PrizesListVo>(prizeList);
        pageInfo.setList(prizeList);
        for(PrizesListVo prizesListVo:pageInfo.getList()){
            List<UserInfoListDto> list = prizeMapper.selectRwardUserByPrizeId(prizesListVo.getId());
            prizesListVo.setRewardUser(list);
        }
        return pageInfo;
    }
}
