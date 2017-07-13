package com.chinaxiaopu.xiaopuMobi.service.domain;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Steven@chinaxiaopu.com on 10/13/16.
 */
public class SmsBaseSendRequest extends SmsRequest implements ISmsRequest{

    private String smsParam;

    @Override
    public void setRecNum(String recNum) {
        super.setRecNum(recNum);
    }

    @Override
    public String getExtend() {
        return null;
    }

    @Override
    public String getRecNum() {
        assert StringUtils.isNoneEmpty(super.getRecNum());
        return super.getRecNum();
    }

    @Override
    public String getSmsFreeSignName() {
        return "校谱，有我更精彩";
    }

    @Override
    public String getSmsParam() {
        assert this.smsParam !=null;
        return smsParam;
    }

    public void setSmsParam(String smsParam) {
        this.smsParam =smsParam;
    }

    @Override
    public String getSmsTemplateCode() {
//        return "SMS_26185188";
        return "SMS_25781057";
    }
}
