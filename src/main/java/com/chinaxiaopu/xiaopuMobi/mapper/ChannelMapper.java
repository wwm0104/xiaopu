package com.chinaxiaopu.xiaopuMobi.mapper;

import com.chinaxiaopu.xiaopuMobi.dto.admin.channel.ChannelListDto;
import com.chinaxiaopu.xiaopuMobi.model.Channel;
import com.chinaxiaopu.xiaopuMobi.model.ChannelExample;

import java.util.List;

import com.chinaxiaopu.xiaopuMobi.vo.admin.channel.ChannelListVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

public interface ChannelMapper {
    long countByExample(ChannelExample example);

    int deleteByExample(ChannelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Channel record);

    int insertSelective(Channel record);

    List<Channel> selectByExample(ChannelExample example);

    Channel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Channel record, @Param("example") ChannelExample example);

    int updateByExample(@Param("record") Channel record, @Param("example") ChannelExample example);

    int updateByPrimaryKeySelective(Channel record);

    int updateByPrimaryKey(Channel record);


    /**
     * 查询频道字典列表
     *
     * @param channelListDto
     * @return
     */
    List<ChannelListVo> findList(ChannelListDto channelListDto);

    /**
     * 查询一个
     * @param id
     * @return
     */
    Channel selectChannelOneById(Integer id);

}