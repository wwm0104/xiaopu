package com.chinaxiaopu.xiaopuMobi.service.topic;

import com.chinaxiaopu.xiaopuMobi.mapper.AwardPresenMapper;
import com.chinaxiaopu.xiaopuMobi.model.AwardPresen;
import com.chinaxiaopu.xiaopuMobi.model.AwardPresenExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by ycy on 2016/11/9.
 */
@Service
public class AawardPresenService {

    @Autowired
    private AwardPresenMapper awardPresenMapper;

    public PageInfo<AwardPresen> selectAllAwardPresen(AwardPresen awardPresen) throws Exception {
        if (!StringUtils.isEmpty(awardPresen.getPage()) && !StringUtils.isEmpty(awardPresen.getRows())) {
            PageHelper.startPage(awardPresen.getPage(), awardPresen.getRows());
        }
        List<AwardPresen> list = awardPresenMapper.selectAllPerson(awardPresen);
        PageInfo<AwardPresen> pageInfo = new PageInfo<AwardPresen>(list);
        return pageInfo;
    }

    public int checkPhone(String realName, Long phone) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("realName", realName);
        map.put("mobile", phone);
        return awardPresenMapper.checkPhone(map);
    }


    public int checkUserInfo(String realName, Long phone) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("realName", realName);
        map.put("mobile", phone);
        return awardPresenMapper.checkUserInfo(map);
    }

    public int createPresen(AwardPresen awardPresen) throws Exception {
        awardPresen.setCreateTime(new Date());
        awardPresen.setAwardCnt(0);
        awardPresen.setAvailable(1);
        return awardPresenMapper.insert(awardPresen);
    }

    public int updatePresen(AwardPresen awardPresen) throws Exception {
        return awardPresenMapper.updatePresen(awardPresen);
    }

    /**
     * 查询发奖人信息
     *
     * @param userId
     * @return
     */
    public List<AwardPresen> selectAwardPerson(Integer userId) {
        AwardPresenExample example = new AwardPresenExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return awardPresenMapper.selectByExample(example);
    }

    /**
     * 查询发奖人信息
     *
     * @return
     */
    public List<List<Object>> awardPersonExport() {
        String text1="启用";
        String text2="停用";

        AwardPresen e =new AwardPresen();
        List<List<Object>> list = new ArrayList<>();
        List<AwardPresen> _list = awardPresenMapper.selectAllPerson(e);
        for(AwardPresen a : _list){
            List<Object> voList = new ArrayList<>();
            voList.add(a.getId());
            voList.add(a.getRealName());
            voList.add(a.getMobile());
            voList.add(a.getOfficialName());
            voList.add(a.getOfficialMobile());
            voList.add(a.getRemarks());
            voList.add(a.getAwardCnt());
            if(a.getAvailable() == 1){
                voList.add(text1);
            }else{
                voList.add(text2);
            }
            list.add(voList);
        }
        return list;
    }

    public int checkIsAwardPerson(Integer userId){
        return  awardPresenMapper.checkIsAwardPerson(userId);
    }
}
