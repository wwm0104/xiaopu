package com.chinaxiaopu.xiaopuMobi.util;

/**
 * 数据类型转换
 * Created by Administrator on 2016/10/25.
 */
public class DataTypeChange {
    /**
     * 以逗号分割组成的字符串转化为数组
     *
     * @param str 需要处理的字符串
     * @return 数组
     */
    public static String[] strToArray(String str) {
        String[] strArray = null;
        strArray = str.split(","); //拆分字符为"," ,然后把结果交给数组strArray
        return strArray;
    }
}
