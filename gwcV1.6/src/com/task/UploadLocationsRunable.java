package com.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.constant.AppConstant;
import com.listener.LogListener;
import com.mapper.CarRuntimeInfoTaskMapper;
import com.model.CarRuntimeInfoTask;
import com.request.CarRuntimeInfoTaskReq;
import com.utils.CommonRsp;
import com.utils.HttpUtils;
import com.utils.ResponseResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 3.3.2位置数据接入(批量)
 * 
 * @author zhi.yi
 */
public class UploadLocationsRunable implements Runnable {
	private static Logger log = Logger.getLogger(UploadLocationsRunable.class);
	private SqlSessionFactory sessionFactory = null;
	private String msgThreadType = "0";

	public UploadLocationsRunable(SqlSessionFactory sessionFactory, String msgThreadType) {
		super();
		this.msgThreadType = msgThreadType;
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void run() {
		SqlSession session = null;
		List<CarRuntimeInfoTask> carRuntimeInfoTasks = new ArrayList<CarRuntimeInfoTask>();
		CarRuntimeInfoTaskMapper carRuntimeInfoTaskMapper = null;
		try {
			//LogListener.debug(this.getClass(), AppConstant.NAME_CMD_UPLOADLOCATIONS + msgThreadType, "开始任务"); // 获取要发送的消息队列
			session = sessionFactory.openSession();
			carRuntimeInfoTaskMapper = session.getMapper(CarRuntimeInfoTaskMapper.class);

			Map<String, String> map = new HashMap<String, String>();
			map.put("msgThreadType", msgThreadType);
			carRuntimeInfoTasks = carRuntimeInfoTaskMapper.selectEntityList(map);

			if (null != carRuntimeInfoTasks && carRuntimeInfoTasks.size() > 0) {
				excuteTask(carRuntimeInfoTasks, session, carRuntimeInfoTaskMapper);
			} else {
				//LogListener.debug(this.getClass(), AppConstant.NAME_CMD_UPLOADLOCATIONS + msgThreadType, "当前没有任务");
			}
		} catch (Exception e) {
			LogListener.error(this.getClass(), AppConstant.NAME_CMD_UPLOADLOCATIONS + msgThreadType, e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != session) {
				session.close();
				//LogListener.debug(this.getClass(), AppConstant.NAME_CMD_UPLOADLOCATIONS + msgThreadType, "结束任务");
			}
		}
	}

	private void excuteTask(List<CarRuntimeInfoTask> carRuntimeInfoTasks, SqlSession session,
			CarRuntimeInfoTaskMapper carRuntimeInfoTaskMapper) {
		List<String> ids = new ArrayList<>();
		List<CarRuntimeInfoTaskReq> carRuntimeInfoTaskReqs = new ArrayList<CarRuntimeInfoTaskReq>();
		List<CarRuntimeInfoTask> tempCarRuntimeInfoTasks = new ArrayList<CarRuntimeInfoTask>();
		CarRuntimeInfoTask entity = new CarRuntimeInfoTask();

		for (int i = 0; i < carRuntimeInfoTasks.size(); i++) {
			try {
				entity = carRuntimeInfoTasks.get(i);
				// 20条一个包
				if (i == 0 || i % 20 != 0) {
					ids.add(entity.getId());
					CarRuntimeInfoTaskReq taskReq = new CarRuntimeInfoTaskReq();
					BeanUtils.copyProperties(taskReq, entity);
					carRuntimeInfoTaskReqs.add(taskReq);
					tempCarRuntimeInfoTasks.add(entity);
					if ((i + 1) % carRuntimeInfoTasks.size() != 0) {
						continue;
					}
				}
				// 组装消息格式
				CommonRsp commonRsp = new CommonRsp(AppConstant.CMD_UPLOADLOCATIONS, carRuntimeInfoTaskReqs);

				// 发送消息判断是否成功
				log.info(this.getClass()+AppConstant.NAME_CMD_UPLOADLOCATIONS+ msgThreadType+
						"开始发送=" +  JSONArray.fromObject(commonRsp.getArgs()));
				String result = HttpUtils.jsonPost(AppConstant.POSTURL_LOCATION, commonRsp);

				log.info( AppConstant.NAME_CMD_UPLOADLOCATIONS + msgThreadType+
						"返回结果=" + result);

				// 如果成功，清除消息队列 如果失败记录失败原因
				JSONObject resultJson =null;
				try {
					resultJson = JSONObject.fromObject(result);
				} catch (Exception e) {
					throw new Exception("返回格式错误="+result);
				}
				ResponseResult responseResult = new ResponseResult();
				responseResult.setCmd(resultJson.getString("Cmd"));
				responseResult.setData(resultJson.getString("Data"));
				responseResult.setRemark(resultJson.getString("Remark"));
				responseResult.setResult(resultJson.getString("Result"));
				Integer code = Integer.parseInt(responseResult.getResult());

				// 操作成功
				if (null != responseResult && code > 0) {
					carRuntimeInfoTaskMapper.deleteEntityByIds(ids);
				} else {
					throw new Exception(result);
				}
				session.commit();
				// 清空集合
				ids.clear();
				carRuntimeInfoTaskReqs.clear();
				tempCarRuntimeInfoTasks.clear();
				
			} catch (Exception e) {
				try {
					for (CarRuntimeInfoTask carRuntimeInfoTask : tempCarRuntimeInfoTasks) {
						carRuntimeInfoTask.setExceptionMsg(e.getMessage());
						carRuntimeInfoTask.setUploadDegree(carRuntimeInfoTask.getUploadDegree() + 1);
						carRuntimeInfoTaskMapper.updateEntityByEntity(carRuntimeInfoTask);
					}

				} catch (Exception e2) {
					e2.printStackTrace();
				}
				// 清空集合
				ids.clear();
				carRuntimeInfoTaskReqs.clear();
				tempCarRuntimeInfoTasks.clear();
				session.commit();
				LogListener.error(this.getClass(), AppConstant.NAME_CMD_UPLOADLOCATIONS + msgThreadType,
						"操作失败:" + e.getMessage());
			}
		}
	}

}
