package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.DriverTask;

public interface DriverTaskMapper {
	List<DriverTask> selectEntityList();
	void deleteEntityByIds(@Param("ids")List<String> ids);
	void updateEntityByEntity(@Param("entity")DriverTask entity);
}	
