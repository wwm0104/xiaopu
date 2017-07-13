package com.chinaxiaopu.xiaopuMobi.interceptor;

/**
 * Created by Steven@chinaxiaopu.com on 9/29/16.
 */

import com.chinaxiaopu.xiaopuMobi.service.LoginService;
import com.chinaxiaopu.xiaopuMobi.util.DateTimeUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ExplorerInterceptor extends WebMvcConfigurerAdapter {
    /**
     * 配置拦截器
     * @author steven
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new EncodeHandlerInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
    }
}

@Slf4j
class ExplorerHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    private void onLoginFail(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        map.put("msg","login error");
        response.getWriter().write(gson.toJson(map));
    }


    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}

@Slf4j
class EncodeHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}

/**
 * 日志拦截器
 * Created by Administrator on 2016/11/29.
 */
@Slf4j
class LogInterceptor implements HandlerInterceptor {
    private static final ThreadLocal<Long> startTimeThreadLocal =new NamedThreadLocal<Long>("ThreadLocal StartTime");
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (log.isDebugEnabled()){
            long beginTime = System.currentTimeMillis();//1、开始时间
            startTimeThreadLocal.set(beginTime);		//线程绑定变量（该数据只有当前请求的线程可见）
            log.info("开始计时: {}  URI: {}", new SimpleDateFormat("hh:mm:ss.SSS") .format(beginTime), request.getRequestURI());
       }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null){
            log.info("ViewName: " + modelAndView.getViewName());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 打印JVM信息。
       if (log.isDebugEnabled()){
            long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
            long endTime = System.currentTimeMillis(); 	//2、结束时间
            log.info("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
                    new SimpleDateFormat("hh:mm:ss.SSS").format(endTime), DateTimeUtil.formatDateTime(endTime - beginTime),
                    request.getRequestURI(), Runtime.getRuntime().maxMemory()/1024/1024, Runtime.getRuntime().totalMemory()/1024/1024, Runtime.getRuntime().freeMemory()/1024/1024,
                    (Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1024/1024);
        }
    }
}