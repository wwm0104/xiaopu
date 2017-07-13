package com.chinaxiaopu.xiaopuMobi.service.topic;

import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.dto.topic.PKResultDetailDto;
import com.chinaxiaopu.xiaopuMobi.mapper.PkResultMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.TopicMapper;
import com.chinaxiaopu.xiaopuMobi.model.Topic;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.topic.PKResultDetailVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.PKResultListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
@Slf4j
@Service
public class VoteResultDetailService {
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private PkResultMapper pkResultMapper;

    /**
     * 获取PK结果基础数据
     *
     * @param pKResultDto
     * @return
     */
    public List<PKResultDetailVo> getPkDetail(PKResultDetailDto pKResultDto) {
        String filePath = null;
        try {
            filePath = SystemConfig.getInstance().getImgsDomain();//图片路径
        } catch (Exception e) {
            e.printStackTrace();
        }
        Topic topic =  topicMapper.selectByPrimaryKey(pKResultDto.getTopicId());
        List<PKResultDetailVo> _list=null;
        if(topic != null){
            if(StrUtils.isNotEmpty(topic.getChallengeTopicId())){
                pKResultDto.setTopicId(topic.getChallengeTopicId());
                _list = topicMapper.getPkDetail(pKResultDto);
                for (PKResultDetailVo vo : _list){
                    vo.setPath(filePath);
                    vo.setContentMap(vo.getContent());
                }
            }
        }
        return _list;
    }

    /**
     * 获取Pk结果list数据
     *
     * @param pKResultDto
     * @return
     */
    public List<PKResultListVo> getPkResultList(PKResultDetailDto pKResultDto) {
        List<PKResultListVo> _list = pkResultMapper.getPkResultList(pKResultDto);
        String filePath = null;
        try {
            filePath = SystemConfig.getInstance().getImgsDomain();//图片路径
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (PKResultListVo vo : _list) {
            vo.setPath(filePath);
            vo.setContentMap(vo.getContent().toString());
        }
        return _list;
    }
}
