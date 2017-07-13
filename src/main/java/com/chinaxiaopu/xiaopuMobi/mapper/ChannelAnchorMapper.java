package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.admin.channel.ChannelRecommend;
import com.chinaxiaopu.xiaopuMobi.dto.audio.ChannelDto;
import com.chinaxiaopu.xiaopuMobi.model.ChannelAnchor;
import com.chinaxiaopu.xiaopuMobi.model.ChannelAnchorExample;

import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.audio.AnchorVo;
import org.apache.ibatis.annotations.Param;

public interface ChannelAnchorMapper {
    long countByExample(ChannelAnchorExample example);

    int deleteByExample(ChannelAnchorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ChannelAnchor record);

    int insertSelective(ChannelAnchor record);

    List<ChannelAnchor> selectByExample(ChannelAnchorExample example);

    ChannelAnchor selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ChannelAnchor record, @Param("example") ChannelAnchorExample example);

    int updateByExample(@Param("record") ChannelAnchor record, @Param("example") ChannelAnchorExample example);

    int updateByPrimaryKeySelective(ChannelAnchor record);

    int updateByPrimaryKey(ChannelAnchor record);

    int selectCountByInfo(ChannelAnchor record);

    /**
     * 频道下的主播列表
     *
     * @param channelDto
     * @return
     * @author Wang
     */
    List<AnchorVo> getChannelAnchorList(ChannelDto channelDto);
}