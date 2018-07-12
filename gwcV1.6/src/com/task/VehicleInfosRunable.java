package com.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.constant.AppConstant;
import com.listener.LogListener;
import com.mapper.CarInfoTaskMapper;
import com.model.CarInfoTask;
import com.request.CarInfoTaskReq;
import com.request.TaxiModel;
import com.request.TaxiType;
import com.utils.CommonRsp;
import com.utils.HttpUtils;
import com.utils.ResponseResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 3.2.6车辆信息(批量)
 * 
 * @author zhi.yi
 */
public class VehicleInfosRunable implements Runnable {
	private static Logger log = Logger.getLogger(VehicleInfosRunable.class);
	private SqlSessionFactory sessionFactory = null;

	public VehicleInfosRunable(SqlSessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void run() {
		SqlSession session = null;
		List<CarInfoTask> carInfoTasks = new ArrayList<CarInfoTask>();
		CarInfoTaskMapper carInfoTaskMapper = null;
		try {
			//LogListener.debug(this.getClass(), AppConstant.NAME_CMD_VEHICLEINFOS, "开始任务");

			// 获取要发送的消息队列
			session = sessionFactory.openSession();
			carInfoTaskMapper = session.getMapper(CarInfoTaskMapper.class);
			carInfoTasks = carInfoTaskMapper.selectCarInfoTaskList();

			if (null != carInfoTasks && carInfoTasks.size() > 0) {
				//LogListener.debug(this.getClass(), AppConstant.NAME_CMD_VEHICLEINFOS, "要发送记录集合=" + carInfoTasks);
				if (AppConstant.VEHICLEINFOS_BATCH) {
					excuteTask(carInfoTasks, session, carInfoTaskMapper);
				} else {
					excuteTaskNOBatch(carInfoTasks, session, carInfoTaskMapper);
				}

			} else {
				//LogListener.debug(this.getClass(), AppConstant.NAME_CMD_VEHICLEINFOS, "当前没有任务");
			}
		} catch (Exception e) {
			LogListener.error(this.getClass(), AppConstant.NAME_CMD_VEHICLEINFOS, e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != session) {
				session.close();
				//LogListener.debug(this.getClass(), AppConstant.NAME_CMD_VEHICLEINFOS, "结束任务");
			}
		}
	}

	private void excuteTaskNOBatch(List<CarInfoTask> carInfoTasks, SqlSession session,
			CarInfoTaskMapper carInfoTaskMapper) {

		List<CarInfoTaskReq> carInfoTaskReqs = new ArrayList<CarInfoTaskReq>();
		List<CarInfoTask> tempCarInfoTasks = new ArrayList<CarInfoTask>();
		CarInfoTask entity = new CarInfoTask();
		List<String> ids = new ArrayList<>();

		Map<String, TaxiType> taxiTypeMap = AppConstant.taxiTypeMap;
		Map<String, TaxiModel> taxiModelMap = AppConstant.taxiModelMap;

		for (int i = 0; i < carInfoTasks.size(); i++) {
			try {
				entity = carInfoTasks.get(i);
				// 20条一个包
				ids.add(entity.getId());
				CarInfoTaskReq taskReq = new CarInfoTaskReq();

				// 包含该车型 设置订单车型 默认为第一个
				TaxiType taxiType = taxiTypeMap.get(taxiTypeMap.keySet().toArray()[0]);
				if (taxiTypeMap.containsKey(entity.getCarType())) {
					taxiType = taxiTypeMap.get(entity.getCarType());
				}
				entity.setTaxiTypeCode(
						StringUtils.isBlank(taxiType.getTaxiTypeCode()) ? "0" : taxiType.getTaxiTypeCode() + "");// 订单车型编码
				entity.setPassengerNumber(
						StringUtils.isBlank(taxiType.getPassengerNumber()) ? "0" : taxiType.getPassengerNumber() + "");

				// 包含该品牌 设置车辆品牌 默认为未知品牌
				TaxiModel taxiModel = taxiModelMap.get("未知品牌") == null ? new TaxiModel() : taxiModelMap.get("未知品牌");
				if (taxiModelMap.containsKey(entity.getCarBrand())) {
					taxiModel = taxiModelMap.get(entity.getCarBrand());
				}
				entity.setTaxiModelId(
						StringUtils.isBlank(taxiModel.getTaxiModelId()) ? "0" : taxiModel.getTaxiModelId() + "");// TaxiModelId
				entity.setMaxPassengerNumber(StringUtils.isBlank(taxiModel.getMaxPassengerNumber()) ? "0"
						: taxiModel.getMaxPassengerNumber() + "");// 核载人数

				BeanUtils.copyProperties(taskReq, entity);
				carInfoTaskReqs.add(taskReq);
				tempCarInfoTasks.add(entity);
				
				// 组装消息格式
				CommonRsp commonRsp = new CommonRsp(AppConstant.CMD_VEHICLEINFOS, carInfoTaskReqs);

				LogListener.info(this.getClass(), AppConstant.NAME_CMD_VEHICLEINFOS,
						"开始发送=" +  JSONArray.fromObject(commonRsp.getArgs()));
				String result = HttpUtils.jsonPost(AppConstant.POSTURL_BASIC, commonRsp);
				LogListener.info(this.getClass(), AppConstant.NAME_CMD_VEHICLEINFOS, "返回结果=" + result);

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
					carInfoTaskMapper.deleteCarInfoTaskByIds(ids);
				} else {
					throw new Exception(result);
				}
				session.commit();
				// 清空集合
				ids.clear();
				tempCarInfoTasks.clear();
				carInfoTaskReqs.clear();
			} catch (Exception e) {
				try {
					for (CarInfoTask temp : tempCarInfoTasks) {
						temp.setExceptionMsg(e.getMessage());
						temp.setUploadDegree(temp.getUploadDegree() + 1);
						carInfoTaskMapper.updateEntityByEntity(temp);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				// 清空集合
				ids.clear();
				tempCarInfoTasks.clear();
				carInfoTaskReqs.clear();
				session.commit();
				LogListener.error(this.getClass(), AppConstant.NAME_CMD_VEHICLEINFOS, "操作失败:" + e.getMessage());
			}
		}

	}

	private void excuteTask(List<CarInfoTask> carInfoTasks, SqlSession session, CarInfoTaskMapper carInfoTaskMapper) {
		List<CarInfoTaskReq> carInfoTaskReqs = new ArrayList<CarInfoTaskReq>();
		List<CarInfoTask> tempCarInfoTasks = new ArrayList<CarInfoTask>();
		CarInfoTask entity = new CarInfoTask();
		List<String> ids = new ArrayList<>();

		Map<String, TaxiType> taxiTypeMap = AppConstant.taxiTypeMap;
		Map<String, TaxiModel> taxiModelMap = AppConstant.taxiModelMap;

		for (int i = 0; i < carInfoTasks.size(); i++) {
			try {
				entity = carInfoTasks.get(i);
				// 20条一个包
				if (i == 0 || i % 20 != 0) {
					ids.add(entity.getId());
					CarInfoTaskReq taskReq = new CarInfoTaskReq();

					// 包含该车型 设置订单车型 默认为第一个
					TaxiType taxiType = taxiTypeMap.get(taxiTypeMap.keySet().toArray()[0]);
					if (taxiTypeMap.containsKey(entity.getCarType())) {
						taxiType = taxiTypeMap.get(entity.getCarType());
					}
					entity.setTaxiTypeCode(
							StringUtils.isBlank(taxiType.getTaxiTypeCode()) ? "0" : taxiType.getTaxiTypeCode() + "");// 订单车型编码
					entity.setPassengerNumber(StringUtils.isBlank(taxiType.getPassengerNumber()) ? "0"
							: taxiType.getPassengerNumber() + "");

					// 包含该品牌 设置车辆品牌 默认为未知品牌
					TaxiModel taxiModel = taxiModelMap.get("未知品牌") == null ? new TaxiModel() : taxiModelMap.get("未知品牌");
					if (taxiModelMap.containsKey(entity.getCarBrand())) {
						taxiModel = taxiModelMap.get(entity.getCarBrand());
					}
					entity.setTaxiModelId(
							StringUtils.isBlank(taxiModel.getTaxiModelId()) ? "0" : taxiModel.getTaxiModelId() + "");// TaxiModelId
					entity.setMaxPassengerNumber(StringUtils.isBlank(taxiModel.getMaxPassengerNumber()) ? "0"
							: taxiModel.getMaxPassengerNumber() + "");// 核载人数

					BeanUtils.copyProperties(taskReq, entity);
					carInfoTaskReqs.add(taskReq);
					tempCarInfoTasks.add(entity);
					if ((1 + i) % carInfoTasks.size() != 0) {
						continue;
					}
				}
				// 组装消息格式
				CommonRsp commonRsp = new CommonRsp(AppConstant.CMD_VEHICLEINFOS, carInfoTaskReqs);

				LogListener.info(this.getClass(), AppConstant.NAME_CMD_VEHICLEINFOS,
						"开始发送=" + JSONArray.fromObject(commonRsp.getArgs()));
				String result = HttpUtils.jsonPost(AppConstant.POSTURL_BASIC, commonRsp);
				LogListener.info(this.getClass(), AppConstant.NAME_CMD_VEHICLEINFOS, "返回结果=" + result);

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
					carInfoTaskMapper.deleteCarInfoTaskByIds(ids);
				} else {
					throw new Exception(result);
				}
				session.commit();
				// 清空集合
				ids.clear();
				tempCarInfoTasks.clear();
				carInfoTaskReqs.clear();
			} catch (Exception e) {
				try {
					for (CarInfoTask temp : tempCarInfoTasks) {
						temp.setExceptionMsg(e.getMessage());
						temp.setUploadDegree(temp.getUploadDegree() + 1);
						carInfoTaskMapper.updateEntityByEntity(temp);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				// 清空集合
				ids.clear();
				tempCarInfoTasks.clear();
				carInfoTaskReqs.clear();
				session.commit();
				LogListener.error(this.getClass(), AppConstant.NAME_CMD_VEHICLEINFOS, "操作失败:" + e.getMessage());
			}
		}

	}

}
