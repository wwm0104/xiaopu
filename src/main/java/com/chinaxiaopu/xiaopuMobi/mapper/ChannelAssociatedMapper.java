package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.model.ChannelAssociated;
import com.chinaxiaopu.xiaopuMobi.model.ChannelAssociatedExample;

import java.util.List;

import com.chinaxiaopu.xiaopuMobi.model.PkChannel;
import org.apache.ibatis.annotations.Param;

public interface ChannelAssociatedMapper {
    long countByExample(ChannelAssociatedExample example);

    int deleteByExample(ChannelAssociatedExample example);

    int insert(ChannelAssociated record);

    int insertSelective(ChannelAssociated record);

    List<ChannelAssociated> selectByExample(ChannelAssociatedExample example);

    int updateByExampleSelective(@Param("record") ChannelAssociated record, @Param("example") ChannelAssociatedExample example);

    int updateByExample(@Param("record") ChannelAssociated record, @Param("example") ChannelAssociatedExample example);

    /**
     * 查询所有的未添加的子频道
     *
     * @param pid
     * @return
     */
    List<PkChannel> selectAllChannelList(@Param("pid") Integer pid);

    /**
     * 查询所有已添加的频道
     *
     * @param pid
     * @return
     */
    List<PkChannel> selectUserChannelList(@Param("pid") Integer pid);
}