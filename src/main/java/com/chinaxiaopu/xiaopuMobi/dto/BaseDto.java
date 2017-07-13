package com.chinaxiaopu.xiaopuMobi.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;

/**
 * Created by Steven@chinaxiaopu.com on 10/3/16.
 */
@Data
abstract class BaseDto implements Serializable {
    private String accessToken;
    private String clientDigest;
    private String version ;
    @Id
    private @Getter
    @Setter
    Integer id;

    @Transient
    private @Getter @Setter Integer page = 1;

    @Transient
    private @Getter @Setter Integer rows = 10;
}
