package com.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.constant.AppConstant;
import com.listener.LogListener;
import com.mapper.DeptInfoTaskMapper;
import com.model.DeptInfoTask;
import com.request.DeptInfoTaskReq;
import com.utils.CommonRsp;
import com.utils.GBAreaCode;
import com.utils.HttpUtils;
import com.utils.ResponseResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 3.2.4组织机构信息
 * 
 * @author zhi.yi
 */
public class OrganizationInfoRunable implements Runnable {
	private static Logger log = Logger.getLogger(OrganizationInfoRunable.class);
	private SqlSessionFactory sessionFactory = null;

	public OrganizationInfoRunable(SqlSessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void run() {
		SqlSession session = null;
		List<DeptInfoTask> deptInfotasks = new ArrayList<DeptInfoTask>();
		DeptInfoTaskMapper deptInfoTaskMapper = null;
		try {
			//LogListener.debug(this.getClass(), AppConstant.NAME_CMD_ORGANIZATIONINFO, "开始任务");
			// 获取要发送的消息队列
			session = sessionFactory.openSession();
			deptInfoTaskMapper = session.getMapper(DeptInfoTaskMapper.class);
			deptInfotasks = deptInfoTaskMapper.selectEntityList();
			if (null != deptInfotasks && deptInfotasks.size() > 0) {
				//LogListener.debug(this.getClass(), AppConstant.NAME_CMD_ORGANIZATIONINFO, "要发送记录集合=" + deptInfotasks);
				excuteTask(deptInfotasks, session, deptInfoTaskMapper);
			} else {
				//LogListener.debug(this.getClass(), AppConstant.NAME_CMD_ORGANIZATIONINFO, "当前没有任务");
			}
		} catch (Exception e) {
			LogListener.error(this.getClass(), AppConstant.NAME_CMD_ORGANIZATIONINFO, e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != session) {
				session.close();
				//LogListener.debug(this.getClass(), AppConstant.NAME_CMD_ORGANIZATIONINFO, "结束任务");
			}
		}
	}

	private void excuteTask(List<DeptInfoTask> deptInfotasks, SqlSession session,
			DeptInfoTaskMapper deptInfoTaskMapper) {
		DeptInfoTask entity = new DeptInfoTask();
		for (int i = 0; i < deptInfotasks.size(); i++) {
			try {
				entity = deptInfotasks.get(i);

				// 获取区域编码《中华人民共和国行政区划代码》（GB/T2260-2007)和《县以下行政区划代码编制规则》（GB/T10114-2003)
				entity = setGBAreaCode(entity, deptInfoTaskMapper);

				DeptInfoTaskReq taskReq = new DeptInfoTaskReq();
				BeanUtils.copyProperties(taskReq, entity);

				// 组装消息格式
				CommonRsp commonRsp = new CommonRsp(AppConstant.CMD_ORGANIZATIONINFO, taskReq);
				// 发送消息判断是否成功
				LogListener.info(this.getClass(), AppConstant.NAME_CMD_ORGANIZATIONINFO,
						"开始发送=" +  JSONArray.fromObject(commonRsp.getArgs()));
				String result = HttpUtils.jsonPost(AppConstant.POSTURL_BASIC, commonRsp);
				LogListener.info(this.getClass(), AppConstant.NAME_CMD_ORGANIZATIONINFO, "返回结果=" + result);

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
					List<String> ids = new ArrayList<>();
					ids.add(entity.getId());
					deptInfoTaskMapper.deleteEntityByIds(ids);
				} else {
					throw new Exception(result);
				}
				session.commit();
			} catch (Exception e) {
				entity.setExceptionMsg(e.getMessage());
				deptInfoTaskMapper.updateEntityByEntity(entity);
				session.commit();
				LogListener.error(this.getClass(), AppConstant.NAME_CMD_ORGANIZATIONINFO, "操作失败:" + e.getMessage());
			}
		}
	}

	private DeptInfoTask setGBAreaCode(DeptInfoTask entity, DeptInfoTaskMapper deptInfoTaskMapper) {
		Map<String, String> map = GBAreaCode.showMap;

		// 本身为顶级父类
		if ("0".equals(entity.getParentDeptId()) || null == entity.getParentDeptId()) {
			if (map.containsKey(entity.getDeptName())) {
				entity.setAreaName(entity.getDeptName());
				entity.setAreaCode(map.get(entity.getDeptName()));
				entity.setAreaLevel("3");
			} else {
				entity.setAreaName(GBAreaCode.shixiaqu.getName());
				entity.setAreaCode(GBAreaCode.shixiaqu.getCode());
				entity.setAreaLevel("3");

			}
			return entity;
		}

		// 第一次查询父类机构
		DeptInfoTask deptInfoTask = deptInfoTaskMapper.selectParentDeptInfoByParentDeptId(entity.getParentDeptId());
		if (null == deptInfoTask) {
			entity.setAreaName(GBAreaCode.shixiaqu.getName());
			entity.setAreaCode(GBAreaCode.shixiaqu.getCode());
			entity.setAreaLevel("3");
			return entity;
		}

		if ("0".equals(deptInfoTask.getParentDeptId()) || null == entity.getParentDeptId()) {
			if (map.containsKey(deptInfoTask.getDeptName())) {
				entity.setAreaName(deptInfoTask.getDeptName());
				entity.setAreaCode(map.get(deptInfoTask.getDeptName()));
				entity.setAreaLevel("3");
			} else {
				entity.setAreaName(GBAreaCode.shixiaqu.getName());
				entity.setAreaCode(GBAreaCode.shixiaqu.getCode());
				entity.setAreaLevel("3");
			}
			return entity;
		}

		// 第一次查询父类机构
		deptInfoTask = deptInfoTaskMapper.selectParentDeptInfoByParentDeptId(deptInfoTask.getParentDeptId());
		if (null == deptInfoTask) {
			entity.setAreaName(GBAreaCode.shixiaqu.getName());
			entity.setAreaCode(GBAreaCode.shixiaqu.getCode());
			entity.setAreaLevel("3");
			return entity;
		}
		if ("0".equals(deptInfoTask.getParentDeptId()) || null == entity.getParentDeptId()) {
			if (map.containsKey(deptInfoTask.getDeptName())) {
				entity.setAreaName(deptInfoTask.getDeptName());
				entity.setAreaCode(map.get(deptInfoTask.getDeptName()));
				entity.setAreaLevel("3");
			} else {
				entity.setAreaName(GBAreaCode.shixiaqu.getName());
				entity.setAreaCode(GBAreaCode.shixiaqu.getCode());
				entity.setAreaLevel("3");

			}
			return entity;
		} else {
			entity.setAreaName(GBAreaCode.shixiaqu.getName());
			entity.setAreaCode(GBAreaCode.shixiaqu.getCode());
			entity.setAreaLevel("3");

		}
		return entity;
	}

}
