package com.chinaxiaopu.xiaopuMobi.service.domain;


import java.io.Serializable;

/**
 * Created by Steven@chinaxiaopu.com on 10/13/16.
 */
public interface ISmsRequest extends Serializable{

    public void setRecNum(String recNum);

    public void setSmsParam(String random);

    public String getExtend();

    public String getRecNum();

    public String getSmsFreeSignName();

    public String getSmsParam();

    public String getSmsTemplateCode();

    public String getSmsType();
}
