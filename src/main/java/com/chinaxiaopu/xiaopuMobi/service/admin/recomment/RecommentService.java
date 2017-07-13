package com.chinaxiaopu.xiaopuMobi.service.admin.recomment;

import com.chinaxiaopu.xiaopuMobi.dto.admin.channel.ChannelRecommend;
import com.chinaxiaopu.xiaopuMobi.mapper.RecommendMapper;
import com.chinaxiaopu.xiaopuMobi.model.Recommend;
import com.chinaxiaopu.xiaopuMobi.model.RecommendExample;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 推荐或置顶service
 * Created by wuning on 2016/12/7.
 */
@Service
public class RecommentService {
    @Autowired
    private RecommendMapper recommendMapper;

    /**
     * 推荐 或  置顶
     *
     * @param recommend
     * @return
     */
    public int recommend(Recommend recommend) {
        int row = 0;
        RecommendExample selectExample = new RecommendExample();
        selectExample.createCriteria().andParentTypeEqualTo(recommend.getParentType());
        List<Recommend> list = recommendMapper.selectByExample(selectExample);

        Recommend currRecomment = null;
        Recommend oldRecomment = null;
        if (StrUtils.isNotEmpty(list)) {
            for (Recommend vo : list) {
                if (vo.getPid().equals(recommend.getPid())) {
                    currRecomment = new Recommend();
                    currRecomment.setId(vo.getId());
                    currRecomment.setPid(vo.getPid());
                    currRecomment.setSort(vo.getSort());
                    currRecomment.setParentType(vo.getParentType());
                }
                if (vo.getSort().equals(recommend.getSort())) {
                    oldRecomment = new Recommend();
                    oldRecomment.setId(vo.getId());
                    oldRecomment.setPid(vo.getPid());
                    oldRecomment.setSort(vo.getSort());
                    oldRecomment.setParentType(vo.getParentType());
                }
            }

            if (StrUtils.isNotEmpty(currRecomment) && StrUtils.isNotEmpty(oldRecomment)) {  //交换
                oldRecomment.setSort(currRecomment.getSort());
                currRecomment.setSort(recommend.getSort());
                int flag = recommendMapper.updateByPrimaryKeySelective(currRecomment);
                if (flag > 0) {
                    row = recommendMapper.updateByPrimaryKeySelective(oldRecomment);
                }
            } else if (StrUtils.isEmpty(currRecomment) && StrUtils.isNotEmpty(oldRecomment)) {
                oldRecomment.setPid(recommend.getPid());
                row = recommendMapper.updateByPrimaryKeySelective(oldRecomment);
            } else if (StrUtils.isNotEmpty(currRecomment) && StrUtils.isEmpty(oldRecomment)) {
                currRecomment.setSort(recommend.getSort());
                row = recommendMapper.updateByPrimaryKeySelective(currRecomment);
            } else {
                row = recommendMapper.insertSelective(recommend);
            }
        } else { //添加 推荐或置顶
            row = recommendMapper.insertSelective(recommend);
        }
        return row;
    }
}
