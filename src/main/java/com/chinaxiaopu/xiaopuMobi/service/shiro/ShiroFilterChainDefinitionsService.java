package com.chinaxiaopu.xiaopuMobi.service.shiro;

import com.chinaxiaopu.xiaopuMobi.model.Resource;
import com.chinaxiaopu.xiaopuMobi.service.authorization.ResouceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ellie on 2016/11/18.
 */
@Slf4j
@Service
public class ShiroFilterChainDefinitionsService extends AbstractFilterChainDefinitionsService {


    @Autowired
    private ResouceService resourceservice;


    @Override
    public Map<String, String> initOtherPermission() {
        final LinkedHashMap<String, String> filterChains = new LinkedHashMap<>();
        filterChains.put("/css/**", "anon");
        filterChains.put("/js/**", "anon");
        filterChains.put("/img/**", "anon");
        filterChains.put("/fonts/**", "anon");
        filterChains.put("/plugins/**", "anon");

        filterChains.put("/admin/**", "roles[admin]");
        filterChains.put("/president/**", "roles[president]");
        filterChains.put("/druid/**", "user");
        filterChains.put("/logout/**", "logout");
        filterChains.put("/site/**", "user");
        List<Resource> _resources = resourceservice.findAll();
        try {
            for (Resource resource : _resources) {
                if (!("".equals(resource.getPermission())) && resource.getPermission() != null) {
                    filterChains.put(resource.getUrl(), "perms[" + resource.getPermission() + "]");
                }
            }
        } catch (Exception e) {
            System.out.println("资源属性不完整");
            e.printStackTrace();
        }
        return filterChains;
    }
}
