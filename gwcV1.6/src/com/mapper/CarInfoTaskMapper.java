package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.CarInfoTask;
import com.model.TerminalInfoTask;

public interface CarInfoTaskMapper {
	List<CarInfoTask> selectCarInfoTaskList();
	void deleteCarInfoTaskByIds(@Param("ids")List<String> ids);
	List<TerminalInfoTask> selectTerminalInfoTaskList();
	void updateEntityByEntity(@Param("entity")CarInfoTask carInfoTask);
	void updateTerminalInfoTask(@Param("entity")TerminalInfoTask taskEntity);
}	
