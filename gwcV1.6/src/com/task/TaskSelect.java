package com.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.constant.AppConstant;
import com.mapper.TaskSelectEntityMapper;
import com.model.TaskSelectEntity;

public class TaskSelect {
	public static Log log = LogFactory.getLog(TaskSelect.class);

	public Vector queryTask(String exceptionMsg, String cmd) {
		SqlSession session = null;
		Vector dataList = new Vector();
		try {
			// 获取要发送的消息队列
			session = AppConstant.sessionFactory.openSession();
			TaskSelectEntityMapper taskSelectMapper = session.getMapper(TaskSelectEntityMapper.class);
			Map<String, String> map =new HashMap<String, String>();
			if(!StringUtils.isBlank(exceptionMsg)) {
				map.put("exceptionMsg", exceptionMsg);
			}
			if(!StringUtils.isBlank(cmd)) {
				map.put("cmd", cmd);
			}
			List<TaskSelectEntity> taskselectEntities = taskSelectMapper.TaskSelectMapper(map);
			int index = 0;
			for (TaskSelectEntity task : taskselectEntities) {
				Vector<Comparable> temp = new Vector();
				temp.add(task.getSeq());
				temp.add(task.getFlagShow());
				temp.add(task.getCmd());
				temp.add(task.getUploadDegree());
				temp.add(task.getExceptionMsg());
				dataList.add(temp);
				index++;
			}
		} catch (Exception e) {
			//log.info(e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != session) {
				session.close();
			}
		}
		return dataList;
	}

	public static Vector<String> queryTaskNames() {
		Vector<String> dataList = new Vector<String>();
		dataList.add(AppConstant.NAME_CMD_DRIVERINFOS);
		dataList.add(AppConstant.NAME_CMD_ORGANIZATIONINFO);
		dataList.add(AppConstant.NAME_CMD_TERMINALINFOS);
		dataList.add(AppConstant.NAME_CMD_UPLOADLOCATIONS);
		dataList.add(AppConstant.NAME_CMD_VEHICLEINFOS);
		return dataList;
	}

	public static void stopTask(Vector stopData) {
		// TODO Auto-generated method stub

	}

	public static void stopTask(Vector stopData, List<Map<Object, Object>> mapList, Object flag) {
		// TODO Auto-generated method stub

	}
}
