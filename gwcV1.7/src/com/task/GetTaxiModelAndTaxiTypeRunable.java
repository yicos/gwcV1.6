package com.task;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.constant.AppConstant;
import com.listener.LogListener;
import com.request.GetTaxiAndTypeReq;
import com.request.TaxiModel;
import com.request.TaxiType;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.utils.CommonReq;
import com.utils.HttpUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 获取车型和车辆品牌
 * 
 * @author zhi.yi
 */
public class GetTaxiModelAndTaxiTypeRunable implements Runnable {
	private static Logger log = Logger.getLogger(GetTaxiModelAndTaxiTypeRunable.class);

	@Override
	public void run() {
		try {
			AppConstant.taxiModelMap = getTaxiModel();
			AppConstant.taxiTypeMap = getTaxiType();
		} catch (Exception e) {
			LogListener.error(this.getClass(), AppConstant.NAME_CMD_VEHICLEINFOS, "操作失败:" + e.getMessage());
			return;
		}
	}
	
	/** 获取车辆品牌接口 */
	private Map<String, TaxiModel> getTaxiModel() throws Exception {
		// 获取车辆品牌接口
		Map<String, TaxiModel> map = new HashMap<>();
		String GetTaxiModel = null;
		CommonReq GetTaxiModelCommonRsp = new CommonReq(AppConstant.GetTaxiModel, new GetTaxiAndTypeReq(AppConstant.PLATFORMID));
		LogListener.info(this.getClass(), "【获取车辆品牌接口】", "开始发送=" + GetTaxiModelCommonRsp.toUrlPostParams());
		GetTaxiModel = HttpUtils.jsonPost(AppConstant.POSTURL_BASIC, GetTaxiModelCommonRsp.toUrlPostParams());
		JSONObject GetTaxiModelJson = JSONObject.fromObject(GetTaxiModel);
		LogListener.info(this.getClass(), "【获取车辆品牌接口】", "返回结果=" + GetTaxiModel);

		if (GetTaxiModelJson.getInt("Result") != 1) {
			throw new Exception("获取失败:" + GetTaxiModel);
		}
		JSONArray dataArr = JSONArray.fromObject(GetTaxiModelJson.get("Data"));
		for (int i = 0; i < dataArr.size(); i++) {
			TaxiModel taxiModel = new TaxiModel();
			taxiModel.setMaxPassengerNumber(dataArr.getJSONObject(i).get("MaxPassengerNumber") + "");
			taxiModel.setTaxiBrand(dataArr.getJSONObject(i).get("TaxiBrand") + "");
			taxiModel.setTaxiModelId(dataArr.getJSONObject(i).get("TaxiModelId") + "");
			taxiModel.setTaxiModelName(dataArr.getJSONObject(i).get("TaxiModelName") + "");
			map.put(taxiModel.getTaxiBrand(), taxiModel);
		}
		return map;

	}

	/** 获取订单车型接口 */
	private Map<String, TaxiType> getTaxiType() throws Exception {
		// 获取订单车型接口
		Map<String, TaxiType> map = new HashMap<>();
		String getTaxiType = null;
		CommonReq getTaxiTypeCommonRsp = new CommonReq(AppConstant.GetTaxiType, new GetTaxiAndTypeReq(AppConstant.PLATFORMID));
		LogListener.info(this.getClass(), "【获取订单车型接口】", "开始发送=" + getTaxiTypeCommonRsp.toUrlPostParams());
		getTaxiType = HttpUtils.jsonPost(AppConstant.POSTURL_BASIC, getTaxiTypeCommonRsp.toUrlPostParams());
		JSONObject getTaxiTypeJson = JSONObject.fromObject(getTaxiType);
		LogListener.info(this.getClass(), "【获取订单车型接口】", "返回结果=" + getTaxiType);

		if (getTaxiTypeJson.getInt("Result") != 1) {
			throw new Exception("获取失败:" + getTaxiType);
		}
		JSONArray dataArr = JSONArray.fromObject(getTaxiTypeJson.get("Data"));
		for (int i = 0; i < dataArr.size(); i++) {
			TaxiType taxiType = new TaxiType();
			taxiType.setPassengerNumber(dataArr.getJSONObject(i).get("PassengerNumber") + "");
			taxiType.setTaxiTypeCode(dataArr.getJSONObject(i).get("TaxiTypeCode") + "");
			taxiType.setTaxiTypeName(dataArr.getJSONObject(i).get("TaxiTypeName") + "");
			map.put(taxiType.getTaxiTypeName(), taxiType);
		}
		return map;
	}
	
}
