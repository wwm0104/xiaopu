package com.chinaxiaopu.xiaopuMobi.service.admin.topics;

import com.chinaxiaopu.xiaopuMobi.dto.PrizeTakeLogDto;
import com.chinaxiaopu.xiaopuMobi.mapper.*;
import com.chinaxiaopu.xiaopuMobi.model.Group;
import com.chinaxiaopu.xiaopuMobi.model.PkResult;
import com.chinaxiaopu.xiaopuMobi.model.TopicTag;
import com.chinaxiaopu.xiaopuMobi.vo.PrizeTakeLogVo;
import com.chinaxiaopu.xiaopuMobi.vo.admin.topics.PkTopicVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/8.
 */
@Slf4j
@Service
public class PrizeTakeLogService {
    @Autowired
    private PkPrizeTakeLogMapper pkPrizeTakeLogMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private PkResultMapper pkResultMapper;
    @Autowired
    private TopicTagMapper topicTagMapper;

    @Autowired
    private TopicPkMapper topicPkMapper;

    /**
     * 查询奖励记录
     *
     * @param prizeTakeLogDto
     * @return
     */
    public PageInfo<PrizeTakeLogVo> getPrizeTakeList(PrizeTakeLogDto prizeTakeLogDto) {
        if (prizeTakeLogDto.getPage() != null && prizeTakeLogDto.getRows() != null) {
            PageHelper.startPage(prizeTakeLogDto.getPage(), prizeTakeLogDto.getRows());
        }
        List<PrizeTakeLogVo> list = pkPrizeTakeLogMapper.selectPkPrizeTakeLog(prizeTakeLogDto);
        //注入所有社团列表
        for (PrizeTakeLogVo prizeTakeLogVo : list) {
            if(!StringUtils.isEmpty(prizeTakeLogVo) && !StringUtils.isEmpty(prizeTakeLogVo.getUserId())){
                List<String> groupList = groupMapper.selectByUserId(prizeTakeLogVo.getUserId());
                prizeTakeLogVo.setGroups(org.apache.commons.lang3.StringUtils.join(groupList,"、"));
            }
        }
        PageInfo<PrizeTakeLogVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PrizeTakeLogVo selectDetails(Integer id) {
        PrizeTakeLogVo prizeTakeLogVo = pkPrizeTakeLogMapper.selectDetails(id);
        List<String> groupList = groupMapper.selectGroupNameyPrizeLogId(id); //获取所有社团列表
        prizeTakeLogVo.setGroups(org.apache.commons.lang3.StringUtils.join(groupList,"、"));  //设置社团列
        prizeTakeLogVo.setContentMap(prizeTakeLogVo.getContent()); //设置图文内容map
        Map<String,Object> cntMap = pkResultMapper.selectByPrizeLogId(id);
        prizeTakeLogVo.setTakeVoteCnt(Integer.parseInt(cntMap.get("sumCnt").toString()));  //设置所有投票数
        prizeTakeLogVo.setTakeTopicCnt(Integer.parseInt(cntMap.get("countCnt").toString())); //设置总参与者
        TopicTag topicTag = topicTagMapper.selectTargetNameByPrizeLogId(id);
        if(!StringUtils.isEmpty(topicTag) && !StringUtils.isEmpty(topicTag.getTargetName())){
            prizeTakeLogVo.setTags(topicTag.getTargetName()); //设置标签列
        }
        return prizeTakeLogVo;
    }

    /**
     * 获取 奖品记录 列表导出数据
     *
     * @param prizeTakeLogDto
     * @return
     */
    public List<List<Object>> getPrizeTakeExportInfo(PrizeTakeLogDto prizeTakeLogDto) {
        String typeName1 = "图片";
        String typeName2 = "视频";
        String typeName3 = "领取";
        String typeName4 = "未领";
        List<List<Object>> list = new ArrayList<>();
        List<PrizeTakeLogVo> _list = pkPrizeTakeLogMapper.selectPkPrizeTakeLog(prizeTakeLogDto);
        for (PrizeTakeLogVo o : _list) {
            if(!StringUtils.isEmpty(o) && !StringUtils.isEmpty(o.getUserId())) {
                List<String> groupList = groupMapper.selectByUserId(o.getUserId());
                o.setGroups(org.apache.commons.lang3.StringUtils.join(groupList,"、"));
            }
        }
        for(PrizeTakeLogVo vo : _list){
            List<Object> voList = new ArrayList<>();
            voList.add(vo.getPkId());
            voList.add(vo.getRewardNickName());
            voList.add(vo.getRewardRealName());
            voList.add(vo.getSchoolName());
            voList.add(vo.getGroups());
            if(vo.getTopicType()==1){
                voList.add(typeName1);
            }else{
                voList.add(typeName2);
            }
            voList.add(vo.getSlogan());
            voList.add(vo.getExpireTime());
            voList.add(1);
            voList.add(vo.getPrizeName());
            if(vo.getHasTake()==1){
                voList.add(typeName3);
            }else{
                voList.add(typeName4);
            }
            voList.add(vo.getAwardName());
            voList.add(vo.getTakeTime());
            list.add(voList);
        }
        return list;
    }

    public List<PkTopicVo> selecAllPkId(){
        return topicPkMapper.selecAllPkId();
    }
}
