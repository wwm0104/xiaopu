package com.chinaxiaopu.xiaopuMobi.service;

import com.chinaxiaopu.xiaopuMobi.dto.AppVersionDto;
import com.chinaxiaopu.xiaopuMobi.mapper.AppVersionMapper;
import com.chinaxiaopu.xiaopuMobi.model.AppVersion;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by liuwei
 * date: 16/10/17
 */
@Service
public class AppVersionService extends AbstractService {

    @Autowired
    private AppVersionMapper appVersionMapper;

    public AppVersion selectLastVersionByAppId(final String appId) {
        return appVersionMapper.selectLastVersionByAppId(appId);
    }

    /**
     * 获取appversion列表:分页
     * @return
     */
    public PageInfo<AppVersion> getList(AppVersionDto appVersionDto) {

        AppVersion appVersion=new AppVersion();
        //查询条件
        if(!StringUtils.isEmpty(appVersionDto.getDescription())) {
            appVersion.setDescription(appVersionDto.getDescription());//注入更新详情模糊查询条件
        }
        if(!StringUtils.isEmpty(appVersionDto.getAppId())) {
            appVersion.setAppId(appVersionDto.getAppId());//注入平台id查询条件
        }

        //分页
        if(!StringUtils.isEmpty(appVersionDto.getPage())){
            appVersion.setPage(appVersionDto.getPage());
        }
        if(!StringUtils.isEmpty(appVersionDto.getRows())){
            appVersion.setRows(appVersionDto.getRows());
        }

        if (appVersion.getPage()!=null && appVersion.getRows()!=null) {
            PageHelper.startPage(appVersion.getPage(),appVersion.getRows());//设置起始位置
        }
        List<AppVersion> versionList=appVersionMapper.selectByAppNameOrAppIdGetList(appVersion);
        PageInfo<AppVersion> pageInfo=new PageInfo<>(versionList);

        return  pageInfo;
    }

    /**
     * 根据id查询appversion
     * @param id
     * @return
     */
    public AppVersion getById(Integer id) {
        return appVersionMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改appversion
     * @param appVersion
     * @return
     */
    public boolean update(AppVersion appVersion) {
        int a=appVersionMapper.updateByPrimaryKey(appVersion);
        if(a>0) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 添加appversion
     * @param appVersion
     * @return
     */
    public boolean add(AppVersion appVersion) {
        int a=appVersionMapper.insert(appVersion);
        if(a>0) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 删除appversion
     * @param id
     * @return
     */
    public boolean del(Integer id) {
        int a=appVersionMapper.deleteByPrimaryKey(id);
        if(a>0) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * 根据平台id和代理id设置对应平台name和代理name
     * @param appVersion
     * @return
     */
    public AppVersion byAppIdorUseragent(AppVersion appVersion) {
        if(appVersion.getAppId()!=null) {
            switch (appVersion.getAppId()) {
                case "1":
                    appVersion.setAppName("android");
                    break;
                case "2":
                    appVersion.setAppName("ios");
                    break;
            }
        }
        return appVersion;
    }
}
