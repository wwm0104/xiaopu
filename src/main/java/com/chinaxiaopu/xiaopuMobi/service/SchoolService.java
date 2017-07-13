package com.chinaxiaopu.xiaopuMobi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.mapper.SchoolMapper;
import com.chinaxiaopu.xiaopuMobi.model.School;
import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SchoolService extends AbstractService{
	
	@Autowired
	private SchoolMapper schoolMapper;

	/**
	 * myGroupList
     *
	 * @return
	 */
	public List<School> getSchoolList() {

		School school = new School();
		if (school.getPage() != null && school.getRows() != null) {
			PageHelper.startPage(school.getPage(), school.getRows());
		}
		List<School> schoolList = schoolMapper.selectAll();
		return schoolList;
	}

	public Result<School> selectByPrimaryKey(Integer id) {
		Result<School> result = new Result<School>();
		School school = schoolMapper.selectByPrimaryKey(id);
		if (school == null) {
			result.setResultCode(Result.FAILURE);
		} else {
			result.setResultCode(Result.SUCCESS);
			result.setObj(school);
		}

		return result;
	}
}
