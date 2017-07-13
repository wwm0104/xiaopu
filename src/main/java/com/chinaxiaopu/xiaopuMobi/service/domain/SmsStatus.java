package com.chinaxiaopu.xiaopuMobi.service.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Steven@chinaxiaopu.com on 10/13/16.
 */
@Data
public class SmsStatus implements Serializable {
    public SmsStatus(String mobile) {
        this.mobile = mobile;
    }

    public SmsStatus() {

    }

    private String mobile;
    private String lastRandom;
    private long last = 0;
    private int todayCnt = 0;
}