package com.chinaxiaopu.xiaopuMobi.util;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;


/**
 * Created by liuwei
 * date: 16/10/12
 */
public class HttpHelperUtil {
    /**
     * 获取请求Body
     *
     * @param request
     * @return
     */
    public static String getBodyString(ServletRequest request) {

        StringBuffer sb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
