package com.model;

import java.io.Serializable;

import com.request.DriverTaskReq;

public class DriverTask extends DriverTaskReq implements Serializable {
	private int uploadDegree;
	private String exceptionMsg;
	private String successFlag;
	private String id;
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
