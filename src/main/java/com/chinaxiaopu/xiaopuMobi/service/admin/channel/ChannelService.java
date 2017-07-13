package com.chinaxiaopu.xiaopuMobi.service.admin.channel;

import com.chinaxiaopu.xiaopuMobi.dto.admin.channel.ChannelListDto;
import com.chinaxiaopu.xiaopuMobi.mapper.ChannelAssociatedMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.ChannelMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.PkChannelMapper;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.chinaxiaopu.xiaopuMobi.security.realm.ShiroUser;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.admin.channel.ChannelListVo;
import com.chinaxiaopu.xiaopuMobi.vo.admin.channel.ChannelsVo;
import com.chinaxiaopu.xiaopuMobi.vo.admin.channel.SortVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 频道字典管理
 * Created by 武宁 on 2016/12/1.
 */
@Slf4j
@Service
public class ChannelService {
    @Autowired
    private ChannelAssociatedMapper channelAssociatedMapper;
    @Autowired
    private ChannelMapper channelMapper;
    @Autowired
    private PkChannelMapper pkChannelMapper;

    /**
     * 查询频道字典列表
     *
     * @param channelListDto
     * @return
     */
    public PageInfo<ChannelListVo> list(ChannelListDto channelListDto) {
        if (channelListDto.getPage() != null && channelListDto.getRows() != null) {
            PageHelper.startPage(channelListDto.getPage(), channelListDto.getRows());
        }
        List<ChannelListVo> list = channelMapper.findList(channelListDto);
        PageInfo<ChannelListVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 更新 主表排序
     *
     * @param sortVo
     * @return
     */
    public int updateSort(SortVo sortVo) {
        int row =1;
        String[] channelIds=sortVo.getChannId().split(",");
        if(null !=channelIds && channelIds.length>0){
            for(int i=0;i<channelIds.length;i++){
                Channel e= new Channel();
                e.setId(Integer.parseInt(channelIds[i]));
                e.setSort(channelIds.length-i);
                channelMapper.updateByPrimaryKeySelective(e);
            }
        }
       /* return channelMapper.updateByPrimaryKeySelective(channel);*/
       return row;
    }

    /**
     * 删除  频道主表  和中间表信息
     *
     * @param channel
     * @return
     */
    public int deleteChl(Channel channel) {
        int row = channelMapper.deleteByPrimaryKey(channel.getId());
        int flag = 0;
        if (row > 0) {
            ChannelAssociatedExample example = new ChannelAssociatedExample();
            example.createCriteria().andPChannelIdEqualTo(channel.getId());
            flag = channelAssociatedMapper.deleteByExample(example);
        }
        return flag;
    }

    /**
     * 查询  主表一条数据
     *
     * @param channel
     * @return
     */
    public Channel selectOneById(Channel channel) {
        return channelMapper.selectChannelOneById(channel.getId());
    }

    /**
     * 查询频道中间表数据
     *
     * @param channel
     * @return
     */
    public List<ChannelAssociated> selectChildList(Channel channel) {
        ChannelAssociatedExample example = new ChannelAssociatedExample();
        example.createCriteria().andPChannelIdEqualTo(channel.getId());
        return channelAssociatedMapper.selectByExample(example);
    }

    /**
     * 查询频道列表
     *
     * @return
     */
    public List<PkChannel> selectPkChannelList() {
        return pkChannelMapper.selectAllPkChannel();
    }

    /**
     * 删除  中间表信息
     *
     * @param channelAssociated
     * @return
     */
    public int deleteChildChl(ChannelAssociated channelAssociated) {
        ChannelAssociatedExample example = new ChannelAssociatedExample();
        example.createCriteria().andPChannelIdEqualTo(channelAssociated.getpChannelId()).andChannelIdEqualTo(channelAssociated.getChannelId());
        return channelAssociatedMapper.deleteByExample(example);
    }

    /**
     * 添加 或修改
     *
     * @param channel
     */
    public void save(Channel channel, String channelId, String channelSort, String removeCid) {
        String[] channelIds = channelId.split(",");
        String[] channelSorts = channelSort.split(",");
        String[] removeCids = removeCid.split(",");
        channel.setSlogan(channel.getName());
        if (StrUtils.isNotEmpty(channelSorts)) {
            for (int i = 0; i < channelSorts.length; i++) {
                if ("n".equals(channelSorts[i])) {
                    channelSorts[i] = "0";
                }
            }
        }
        if (StrUtils.isEmpty(channel.getId())) {   //添加
            int row = channelMapper.insertSelective(channel);
            if (row > 0) {
                if (null != channelIds && channelIds.length > 0) {
                    for (int i = 0; i < channelIds.length; i++) {
                        ChannelAssociated e = new ChannelAssociated();
                        e.setChannelId(Integer.parseInt(channelIds[i]));
                        e.setSort(Integer.parseInt(channelSorts[i]));
                        e.setpChannelId(channel.getId());
                        channelAssociatedMapper.insertSelective(e);
                    }
                }
            }
        } else { //更新
            int row = channelMapper.updateByPrimaryKeySelective(channel);
            if (row > 0) {
                if (null != channelIds && channelIds.length > 0) {
                    for (int i = 0; i < channelIds.length; i++) {
                        ChannelAssociated e = new ChannelAssociated();
                        ChannelAssociatedExample exampleCount = new ChannelAssociatedExample();
                        exampleCount.createCriteria().andChannelIdEqualTo(Integer.parseInt(channelIds[i])).andPChannelIdEqualTo(channel.getId());
                        long count = channelAssociatedMapper.countByExample(exampleCount);
                        if (count > 0) {//更新
                            e.setSort(Integer.parseInt(channelSorts[i]));
                            ChannelAssociatedExample e1 = new ChannelAssociatedExample();
                            e1.createCriteria().andChannelIdEqualTo(Integer.parseInt(channelIds[i])).andPChannelIdEqualTo(channel.getId());
                            channelAssociatedMapper.updateByExampleSelective(e, e1);
                        } else {//添加
                            e.setChannelId(Integer.parseInt(channelIds[i]));
                            e.setSort(Integer.parseInt(channelSorts[i]));
                            e.setpChannelId(channel.getId());
                            channelAssociatedMapper.insertSelective(e);
                        }
                    }
                }
            }
            if (null != removeCids && removeCids.length > 0) {
                for (String id : removeCids) {
                    if (StrUtils.isEmpty(id)) {
                        continue;
                    }
                    ChannelAssociatedExample deleteExample = new ChannelAssociatedExample();
                    deleteExample.createCriteria().andChannelIdEqualTo(Integer.parseInt(id)).andPChannelIdEqualTo(channel.getId());
                    channelAssociatedMapper.deleteByExample(deleteExample);
                }
            }


        }


        /*if (StrUtils.isNotEmpty(channel.getId())) {//更新
            channel.setSlogan(channel.getName());
            int row = channelMapper.updateByPrimaryKeySelective(channel);
            if (StrUtils.isNotEmpty(str) && row > 0) {
                Gson gson = new Gson();
                List<Map<String, Object>> strList = gson.fromJson(str, List.class);
                for (Map<String, Object> map : strList) {
                    ChannelAssociated e = new ChannelAssociated();
                    ChannelAssociatedExample example = new ChannelAssociatedExample();
                    example.createCriteria().andChannelIdEqualTo(Integer.parseInt(map.get("channelId").toString())).andPChannelIdEqualTo(Integer.parseInt(map.get("pChannelId").toString()));
                    long count = channelAssociatedMapper.countByExample(example);
                    if (count > 0) {//更新
                        e.setSort(Integer.parseInt(map.get("sort").toString()));
                        ChannelAssociatedExample e1 = new ChannelAssociatedExample();
                        e1.createCriteria().andChannelIdEqualTo(Integer.parseInt(map.get("channelId").toString())).andPChannelIdEqualTo(Integer.parseInt(map.get("pChannelId").toString()));
                        channelAssociatedMapper.updateByExampleSelective(e, e1);
                    } else {//添加
                        e.setChannelId(Integer.parseInt(map.get("channelId").toString()));
                        e.setSort(Integer.parseInt(map.get("sort").toString()));
                        e.setpChannelId(Integer.parseInt(map.get("pChannelId").toString()));
                        channelAssociatedMapper.insertSelective(e);
                    }
                }
            }

        } else {//添加
            channel.setSlogan(channel.getName());
            int row = channelMapper.insertSelective(channel);
            if (StrUtils.isNotEmpty(str) && row > 0) {
                Gson gson = new Gson();
                List<Map<String, Object>> strList = gson.fromJson(str, List.class);
                for (Map<String, Object> map : strList) {
                    ChannelAssociated e = new ChannelAssociated();
                    e.setChannelId(Integer.parseInt(map.get("channelId").toString()));
                    e.setSort(Integer.parseInt(map.get("sort").toString()));
                    e.setpChannelId(channel.getId());
                    channelAssociatedMapper.insertSelective(e);
                }

            }


        }
*/
    }

    /**
     * 编辑  频道信息
     *
     * @param pkChannel
     * @return
     */
    public PkChannel selectChannelById(PkChannel pkChannel) {
        return pkChannelMapper.selectByPrimaryKey(pkChannel.getId());
    }

    /**
     * 添加频道信息
     *
     * @param pkChannel
     */
    public void saveChan(PkChannel pkChannel) {
        pkChannel.setSlogan(pkChannel.getName());
        if (StrUtils.isNotEmpty(pkChannel.getId())) {//更新
            pkChannelMapper.updateByPrimaryKeySelective(pkChannel);
        } else {
            pkChannelMapper.insertSelective(pkChannel);
        }
    }

    /**
     * 查询所有的主频道
     *
     * @return
     */
    public List<Channel> selectAllPChannelList() {
        ChannelExample exampel = new ChannelExample();
        return channelMapper.selectByExample(exampel);
    }

    /**
     * 查询所有的未添加的子频道
     *
     * @param pid
     * @return
     */
    public List<PkChannel> selectAllChannelList(Integer pid) {
        return channelAssociatedMapper.selectAllChannelList(pid);
    }

    /**
     * 查询所有已添加的频道
     *
     * @param pid
     * @return
     */
    public List<PkChannel> selectUserChannelList(Integer pid) {
        return channelAssociatedMapper.selectUserChannelList(pid);
    }

    /**
     * 频道  添加子频道
     *
     * @param channelsVo
     * @return
     */
    public int saveChildChan(ChannelsVo channelsVo) {
        int row=0;
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        PkChannel pk = new PkChannel();
        pk.setDesc(channelsVo.getDesc());
        pk.setSlogan(channelsVo.getName());
        pk.setName(channelsVo.getName());
        pk.setIsOfficial(channelsVo.getIsOfficial());
        pk.setType(channelsVo.getType());
        pk.setStatus(channelsVo.getStatus());
        pk.setCreateId(shiroUser.getId());
        pk.setCreateRealname(shiroUser.getRealName());
        pk.setPosterImg(channelsVo.getPosterImg());
        row =pkChannelMapper.insertSelective(pk);
        if(row>0){
          ChannelAssociated e = new ChannelAssociated();
            e.setpChannelId(channelsVo.getChannelId());
            e.setSort(channelsVo.getSort());
            e.setChannelId(pk.getId());
            channelAssociatedMapper.insertSelective(e);
        }
        return row;
    }
}

