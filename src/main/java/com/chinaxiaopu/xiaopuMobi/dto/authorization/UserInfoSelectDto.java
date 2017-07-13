package com.chinaxiaopu.xiaopuMobi.dto.authorization;

import com.chinaxiaopu.xiaopuMobi.dto.ContextDto;
import lombok.Data;

/**
 * Created by Ellie on 2016/12/5.
 */
@Data
public class UserInfoSelectDto extends ContextDto {
    private static final long serialVersionUID = 1L;
    private Integer roleId;
    private Integer userId;
    private Integer schoolId;
    private String schoolName;
    private String studentNo;
    private Long mobile;
    private String realName;
    private String qq;
    private Integer userSex;
    private String keyWord;
}
