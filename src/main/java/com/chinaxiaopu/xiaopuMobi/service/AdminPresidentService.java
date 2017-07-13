package com.chinaxiaopu.xiaopuMobi.service;

import java.util.ArrayList;
import java.util.List;

import com.chinaxiaopu.xiaopuMobi.dto.GroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinaxiaopu.xiaopuMobi.mapper.PresidentMapper;
import com.chinaxiaopu.xiaopuMobi.mapper.ReviewedGroupMapper;
import com.chinaxiaopu.xiaopuMobi.model.President;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class AdminPresidentService {

	@Autowired
	private PresidentMapper presidentMapper;

	@Autowired
	private ReviewedGroupMapper reviewedGroupMapper;

	/**
	 * 查询社长列表
	 * ycy
	 * @param president
	 * @return
	 * @throws Exception
	 */
	public PageInfo<President> presidentList(President president) throws Exception{
		if (president.getPage() != null && president.getRows() != null) {
			PageHelper.startPage(president.getPage(), president.getRows());
		}
		List<President> list = presidentMapper.selectPresidentBySchool(president);
		for(President p : list){
			GroupDto g =new GroupDto();
			g.setPresidentId(p.getUserId());

			List<GroupDto> groupList = reviewedGroupMapper.selectReviewedGroupList(g);
			List<String> nameList = new ArrayList<String>();
			String str ="";
			for(GroupDto gr : groupList){
				nameList.add(gr.getName());
				str+="【"+gr.getName()+"】";
			}
			p.setGroupNameList(nameList);
			p.setAllGroup(str);
		}
		PageInfo<President> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
}
