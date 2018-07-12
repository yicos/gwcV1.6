package com.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.constant.AppConstant;
import com.listener.LogListener;
import com.mapper.DriverTaskMapper;
import com.model.DriverTask;
import com.request.DriverTaskReq;
import com.utils.CommonReq;
import com.utils.HttpUtils;
import com.utils.ResponseResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 3.2.8司机信息(批量)
 * 
 * @author Administrator
 */
public class DriverInfoRunable implements Runnable {
	private static Logger log = Logger.getLogger(DriverInfoRunable.class);
	private SqlSessionFactory sessionFactory = null;

	public DriverInfoRunable(SqlSessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void run() {
		SqlSession session = null;
		List<DriverTask> driverTasks = new ArrayList<DriverTask>();
		DriverTaskMapper driverTaskMapper = null;
		try {
			//LogListener.debug(this.getClass(), AppConstant.NAME_CMD_DRIVERINFOS, "开始任务");
			session = sessionFactory.openSession();
			driverTaskMapper = session.getMapper(DriverTaskMapper.class);
			driverTasks = driverTaskMapper.selectEntityList();// 获取要发送的消息队列
			if (null != driverTasks && driverTasks.size() > 0) {
				excuteTask(driverTasks, session, driverTaskMapper);
			} else {
				//LogListener.debug(this.getClass(), AppConstant.NAME_CMD_DRIVERINFOS, "当前没有任务");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogListener.error(this.getClass(), AppConstant.NAME_CMD_DRIVERINFOS, e.getMessage());
		} finally {
			if (null != session) {
				session.close();
				//LogListener.debug(this.getClass(), AppConstant.NAME_CMD_DRIVERINFOS, "结束任务");
			}
		}
	}

	private void excuteTask(List<DriverTask> driverTasks, SqlSession session, DriverTaskMapper driverTaskMapper) {
		List<String> ids = new ArrayList<>();
		List<DriverTaskReq> driverTaskReqs = new ArrayList<DriverTaskReq>();
		List<DriverTask> tempDriverTasks = new ArrayList<DriverTask>();
		DriverTask entity = new DriverTask();
		for (int i = 0; i < driverTasks.size(); i++) {
			try {
				entity = driverTasks.get(i);
				// 20条一个包
				if (i == 0 || i % 20 != 0) {
					ids.add(entity.getId());
					DriverTaskReq taskReq = new DriverTaskReq();
					BeanUtils.copyProperties(taskReq, entity);
					driverTaskReqs.add(taskReq);
					tempDriverTasks.add(entity);
					if ((i+1) % driverTasks.size() != 0) {
						continue;
					}
				}
				// 组装消息格式
				CommonReq commonRsp = new CommonReq(AppConstant.CMD_DRIVERINFOS, driverTaskReqs);
				// 发送消息-判断是否成功
				LogListener.info(this.getClass(), AppConstant.NAME_CMD_DRIVERINFOS,
						"开始发送=" +  JSONArray.fromObject(commonRsp.getArgs()));
				String result = HttpUtils.jsonPost(AppConstant.POSTURL_BASIC, commonRsp);
				LogListener.info(this.getClass(), AppConstant.NAME_CMD_DRIVERINFOS, "返回结果=" + result);
				// 如果成功，清除消息队列 如果失败记录失败原因
				JSONObject resultJson = JSONObject.fromObject(result);
				ResponseResult responseResult = new ResponseResult();
				responseResult.setCmd(resultJson.getString("Cmd"));
				responseResult.setData(resultJson.getString("Data"));
				responseResult.setRemark(resultJson.getString("Remark"));
				responseResult.setResult(resultJson.getString("Result"));
				Integer code = Integer.parseInt(responseResult.getResult());
				// 操作成功
				if (null != responseResult && code > 0) {
					driverTaskMapper.deleteEntityByIds(ids);
				} else {
					throw new Exception(result);
				}
				// 提交
				session.commit();
				// 清空集合
				ids.clear();
				driverTaskReqs.clear();
				tempDriverTasks.clear();
			} catch (Exception e) {
				try {
					for (DriverTask driverTask : tempDriverTasks) {
						driverTask.setExceptionMsg(e.getMessage());// 记录异常
						driverTask.setUploadDegree(driverTask.getUploadDegree() + 1);
						driverTaskMapper.updateEntityByEntity(driverTask);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				// 清空集合
				ids.clear();
				driverTaskReqs.clear();
				tempDriverTasks.clear();
				session.commit();
				e.printStackTrace();
				LogListener.error(this.getClass(), AppConstant.NAME_CMD_DRIVERINFOS, "操作失败:" + e.getMessage());
			}
		}
	}

}
