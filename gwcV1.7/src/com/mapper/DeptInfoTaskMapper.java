package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.DeptInfoTask;

public interface DeptInfoTaskMapper {
	List<DeptInfoTask> selectEntityList();
	void deleteEntityByIds(@Param("ids")List<String> ids);
	void updateEntityByEntity(@Param("entity")DeptInfoTask taskEntity);
	DeptInfoTask selectParentDeptInfoByParentDeptId(@Param("parentDeptId")String parentDeptId);
}	
