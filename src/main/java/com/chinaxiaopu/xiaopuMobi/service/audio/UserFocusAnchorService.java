package com.chinaxiaopu.xiaopuMobi.service.audio;

import com.chinaxiaopu.xiaopuMobi.dto.UserFocusAnchorDto;
import com.chinaxiaopu.xiaopuMobi.mapper.UserFocusAnchorMapper;
import com.chinaxiaopu.xiaopuMobi.model.User;
import com.chinaxiaopu.xiaopuMobi.model.UserFocusAnchor;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.vo.UserAduioVo;
import com.chinaxiaopu.xiaopuMobi.vo.UserFocusVo;
import com.github.pagehelper.PageHelper;
import org.apache.maven.wagon.providers.ssh.jsch.interactive.UserInfoUIKeyboardInteractiveProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ellie on 2016/12/5.
 */
@Service
public class UserFocusAnchorService {

    @Autowired
    private UserFocusAnchorMapper userFocusAnchorMapper;

    /**
     * 获取用户关注主播列表
     *
     * @param userFocusAnchorDto
     * @return
     */
    public List<UserFocusVo> getUseraFocusAnchor(UserFocusAnchorDto userFocusAnchorDto) {

        UserFocusAnchor userFocusAnchor = new UserFocusAnchor();

        if (!StringUtils.isEmpty(userFocusAnchorDto.getUserId())) {
            userFocusAnchor.setUserId(userFocusAnchorDto.getUserId());
        }

        if (!StringUtils.isEmpty(userFocusAnchorDto.getPage()) && !StringUtils.isEmpty(userFocusAnchorDto.getRows())) {
            PageHelper.startPage(userFocusAnchorDto.getPage(), userFocusAnchorDto.getRows());
        }

        List<UserFocusVo> userFocusVoList = userFocusAnchorMapper.selectByUserId(userFocusAnchor.getUserId());
        if(userFocusVoList!=null && userFocusVoList.size()>0){
            for(UserFocusVo userFocusVo:userFocusVoList){
                userFocusVo.setIsFocus(1);
                userFocusVo.setContentMap(userFocusVo.getContent());
            }
        }
        return userFocusVoList;
    }

    /**
     * 根据传入的参数决定关注or取消关注 (1表示关注，0表示取消关注)
     *
     * @param userFocusAnchorDto
     * @return
     */
    public int focusOrCancelFocus(UserFocusAnchorDto userFocusAnchorDto) {
        int code = 0;
        UserFocusAnchor userFocusAnchor = userFocusAnchorMapper.selectByUserIdAndFocusUserId(userFocusAnchorDto.getUserId(), userFocusAnchorDto.getFocusUserId());
        if (userFocusAnchor != null) {
            int count = userFocusAnchorMapper.deleteByPrimaryKey(userFocusAnchor.getId());
            if (count > 0) {
                code = 103;//取消关注成功
            } else {
                code = 0;
            }
        } else {
            UserFocusAnchor userFocusAnchor1 = new UserFocusAnchor();
            userFocusAnchor1.setUserId(userFocusAnchorDto.getUserId());
            userFocusAnchor1.setFocusUserId(userFocusAnchorDto.getFocusUserId());
            userFocusAnchor1.setFocusTime(new Date());
            int count = userFocusAnchorMapper.insert(userFocusAnchor1);
            if (count > 0) {
                code = 104;//关注成功
            } else {
                code = 0;
            }
        }
        return code;
    }


    /**
     * 判断是否已关注该主播true（已关注）false（未关注）
     *
     * @param userId
     * @param focusUserId
     * @return
     */
    public boolean isFocus(Integer userId, Integer focusUserId) {
        UserFocusAnchor userFocusAnchor = userFocusAnchorMapper.selectByUserIdAndFocusUserId(userId, focusUserId);
        if (StringUtils.isEmpty(userFocusAnchor)) {
            return false;
        } else {
            return true;
        }
    }

}
