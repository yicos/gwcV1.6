package com.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.model.TaskSelectEntity;

public interface TaskSelectEntityMapper {
	
	List<TaskSelectEntity> TaskSelectMapper(@Param("map")Map<String, String> map);
}	
