package com.model;

import java.io.Serializable;

import com.request.CarInfoTaskReq;

public class CarInfoTask extends CarInfoTaskReq implements Serializable {
	private int uploadDegree;
	private String exceptionMsg;
	private String successFlag;
	private String id;
	
	private String carType;
	private String carBrand;
	
	
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getCarBrand() {
		return carBrand;
	}
	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}
	public int getUploadDegree() {
		return uploadDegree;
	}
	public void setUploadDegree(int uploadDegree) {
		this.uploadDegree = uploadDegree;
	}
	public String getExceptionMsg() {
		return exceptionMsg;
	}
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
	public String getSuccessFlag() {
		return successFlag;
	}
	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
