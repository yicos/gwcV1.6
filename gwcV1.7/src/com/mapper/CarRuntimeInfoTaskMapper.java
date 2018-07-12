package com.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.model.CarRuntimeInfoTask;

public interface CarRuntimeInfoTaskMapper {
	List<CarRuntimeInfoTask> selectEntityList(@Param("map")Map<String, String> map);
	void deleteEntityByIds(@Param("ids")List<String> ids);
	void updateEntityByEntity(@Param("entity")CarRuntimeInfoTask taskEntity);
}	
