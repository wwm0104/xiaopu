package com.chinaxiaopu.xiaopuMobi.service.shiro;

import com.chinaxiaopu.xiaopuMobi.service.authorization.ResouceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by Ellie on 2016/11/18.
 */
@Slf4j
public abstract class AbstractFilterChainDefinitionsService implements FilterChainDefinitionsService {

    private String definitions = "";

    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Autowired
    private ResouceService resourceservice;

//    @PostConstruct
    public void intiPermission() {
        shiroFilterFactoryBean.setFilterChainDefinitionMap(obtainPermission());
        log.debug("initialize resource permission success...");

        AbstractShiroFilter shiroFilter = null;

        try {
            shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter// 获取过滤管理器
                .getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

        Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();//重构生成新的filterChainDefinitionMap

        for (Map.Entry<String, String> entry : chains.entrySet()) {
            String url = entry.getKey();
            String chainDefinition = entry.getValue().trim().replace(" ", "");
            manager.createChain(url, chainDefinition);
        }

    }


    public void updatePermission() {

        synchronized (shiroFilterFactoryBean) {

            AbstractShiroFilter shiroFilter = null;

            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                log.error(e.getMessage());
            }

            // 获取过滤管理器
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                    .getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

            // 清空初始权限配置
            manager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();

            // 重新构建生成
//            shiroFilterFactoryBean.setFilterChainDefinitions(definitions);
            shiroFilterFactoryBean.setFilterChainDefinitionMap(initOtherPermission());
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();

            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                manager.createChain(url, chainDefinition);
            }

            log.debug("update resource permission success...");
        }
    }

    @Override
    public abstract Map<String, String> initOtherPermission();

    /**
     * 读取初始化资源文件
     */
    private Map<String, String> obtainPermission() {
        Map<String, String> filterChains = initOtherPermission();
        return filterChains;
    }


    public String getDefinitions() {
        return definitions;
    }

    public void setDefinitions(String definitions) {
        this.definitions = definitions;
    }
}
