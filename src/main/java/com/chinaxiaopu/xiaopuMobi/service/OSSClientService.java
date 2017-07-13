package com.chinaxiaopu.xiaopuMobi.service;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * Created by liuwei
 * date: 16/10/14
 */
@Slf4j
@Service
public class OSSClientService {

    public void putOSSByByte(String key,byte[] content) throws Exception{
        OSSClientConfig ossClientConfig = OSSClientFactory.getInstance(SystemConfig.getInstance().getAlyunoss());
        ClientConfiguration conf = new ClientConfiguration();
        // 开启支持CNAME选项
        conf.setSupportCname(true);
        OSSClient ossClient = new OSSClient(ossClientConfig.getUrl(), ossClientConfig.getAppkey(), ossClientConfig.getSecret());
        ossClient.putObject(ossClientConfig.getBucketName(), key, new ByteArrayInputStream(content));
        // 关闭client
        ossClient.shutdown();
    }
    public void putOSSByFilePath(String key, String filePath) throws Exception{
        OSSClientConfig ossClientConfig = OSSClientFactory.getInstance(SystemConfig.getInstance().getAlyunoss());
        ClientConfiguration conf = new ClientConfiguration();
        // 开启支持CNAME选项
        conf.setSupportCname(true);
        OSSClient ossClient = new OSSClient(ossClientConfig.getUrl(), ossClientConfig.getAppkey(), ossClientConfig.getSecret());

        ossClient.putObject(ossClientConfig.getBucketName(), key, new File(filePath));
        // 关闭client
        ossClient.shutdown();
    }
}

interface OSSClientConfig extends IService {
    public String getAppkey();
    public String getSecret();
    public String getUrl();
    public String getBucketName();
}

class OSSClientFactory{
    public static final String ALIYUNOSS="ALIYUNOSS";
    public static final String ALIYUNOSS_TEST="ALIYUNOSS_TEST";
    private static  OSSClientConfig aliyunOssConfig;
    public static OSSClientConfig getInstance(String whichSmsProvider){
        assert StringUtils.isNoneEmpty(whichSmsProvider);
        if(whichSmsProvider.equals(ALIYUNOSS)){
            if (aliyunOssConfig == null)
                aliyunOssConfig = new AliyunOssConfig();
        } else if(whichSmsProvider.equals(ALIYUNOSS_TEST)){
            if (aliyunOssConfig == null)
                aliyunOssConfig = new AliyunOssTestConfig();
        }
        return aliyunOssConfig;
    }
    static final class AliyunOssConfig implements OSSClientConfig{
        @Getter
        private String Appkey = "LTAIa3jW8zHOZkRL";
        @Getter
        private String secret = "n5KOXJeEIrryfoF1frqQRfx3HA8x4C";
        @Getter
        private String url = "http://imgs1.chinaxiaopu.com";
        @Getter
        private String bucketName = "chinaxiaopu";
    }

    static final class AliyunOssTestConfig implements OSSClientConfig{
        @Getter
        private String Appkey = "LTAI7pIK4ILKNdgT";
        @Getter
        private String secret = "wIob9EF8ARSR5tbFPCYFDJhRivdYzj";
        @Getter
        private String url = "http://oss.chinaxiaopu.top";
        @Getter
        private String bucketName = "test-chinaxiaopu";
    }
}