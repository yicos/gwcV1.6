package com.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.constant.AppConstant;
import com.listener.LogListener;
import com.mapper.CarInfoTaskMapper;
import com.model.TerminalInfoTask;
import com.request.TerminalInfoTaskReq;
import com.utils.CommonReq;
import com.utils.HttpUtils;
import com.utils.ResponseResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 3.2.3位置终端信息(批量)
 * 
 * @author zhi.yi
 */
public class TerminalInfosRunable implements Runnable {
	private static Logger log = Logger.getLogger(TerminalInfosRunable.class);
	private SqlSessionFactory sessionFactory = null;

	public TerminalInfosRunable(SqlSessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void run() {
		SqlSession session = null;
		List<TerminalInfoTask> terminalInfoTasks = new ArrayList<TerminalInfoTask>();
		CarInfoTaskMapper carInfoTaskMapper = null;
		try {
			//LogListener.debug(this.getClass(), AppConstant.NAME_CMD_TERMINALINFOS, "开始任务");
			// 获取要发送的消息队列
			session = sessionFactory.openSession();
			carInfoTaskMapper = session.getMapper(CarInfoTaskMapper.class);
			terminalInfoTasks = carInfoTaskMapper.selectTerminalInfoTaskList();

			if (null != terminalInfoTasks && terminalInfoTasks.size() > 0) {
				//LogListener.debug(this.getClass(), AppConstant.NAME_CMD_TERMINALINFOS, "要发送记录集合=" + terminalInfoTasks);
				excuteTask(terminalInfoTasks, session, carInfoTaskMapper);
			} else {
				//LogListener.debug(this.getClass(), AppConstant.NAME_CMD_TERMINALINFOS, "当前没有任务");
			}
		} catch (Exception e) {
			LogListener.error(this.getClass(), AppConstant.NAME_CMD_TERMINALINFOS, e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != session) {
				session.close();
				//LogListener.debug(this.getClass(), AppConstant.NAME_CMD_TERMINALINFOS, "结束任务");
			}
		}
	}

	private void excuteTask(List<TerminalInfoTask> terminalInfoTasks, SqlSession session,
			CarInfoTaskMapper carInfoTaskMapper) {
		List<TerminalInfoTask> tempTasks = new ArrayList<TerminalInfoTask>();
		List<TerminalInfoTaskReq> terminalInfoTaskReqs = new ArrayList<TerminalInfoTaskReq>();
		List<String> ids = new ArrayList<>();
		TerminalInfoTask entity = new TerminalInfoTask();
		for (int i = 0; i < terminalInfoTasks.size(); i++) {
			try {
				entity = terminalInfoTasks.get(i);
				// 20条一个包
				if (i == 0 || i % 20 != 0) {
					ids.add(entity.getId());
					TerminalInfoTaskReq taskReq = new TerminalInfoTaskReq();
					BeanUtils.copyProperties(taskReq, entity);
					terminalInfoTaskReqs.add(taskReq);
					tempTasks.add(entity);
					if ((1+i) % terminalInfoTasks.size() != 0) {
						continue;
					}
				}
				// 组装消息格式
				CommonReq commonRsp = new CommonReq(AppConstant.CMD_TERMINALINFOS, terminalInfoTaskReqs);

				// 发送消息判断是否成功
				LogListener.info(this.getClass(), AppConstant.NAME_CMD_TERMINALINFOS,
						"开始发送=" + JSONArray.fromObject(commonRsp.getArgs()));
				String result = HttpUtils.jsonPost(AppConstant.POSTURL_BASIC, commonRsp);
				LogListener.info(this.getClass(), AppConstant.NAME_CMD_TERMINALINFOS, "返回结果=" + result);

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
					carInfoTaskMapper.deleteCarInfoTaskByIds(ids);
				} else {
					throw new Exception(result);
				}
				session.commit();
				// 清空集合
				ids.clear();
				terminalInfoTaskReqs.clear();
				tempTasks.clear();
			} catch (Exception e) {
				// 清空集合
				try {
					for (TerminalInfoTask terminalInfoTask : tempTasks) {
						terminalInfoTask.setExceptionMsg(e.getMessage());
						carInfoTaskMapper.updateTerminalInfoTask(terminalInfoTask);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				ids.clear();
				terminalInfoTaskReqs.clear();
				tempTasks.clear();
				session.commit();
				LogListener.error(this.getClass(), AppConstant.NAME_CMD_TERMINALINFOS, "操作失败:" + e.getMessage());
			}
		}
	}

}
