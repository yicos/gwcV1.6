package com.model;

import java.io.Serializable;

import com.request.DeptInfoTaskReq;

public class DeptInfoTask extends DeptInfoTaskReq implements Serializable {
	private int uploadDegree;
	private String exceptionMsg;
	private String successFlag;
	private String id;
	private String deptRelationId;
	private String deptLevel;
	private String parentDeptId;
	private String deptName;
	
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptRelationId() {
		return deptRelationId;
	}
	public void setDeptRelationId(String deptRelationId) {
		this.deptRelationId = deptRelationId;
	}
	public String getDeptLevel() {
		return deptLevel;
	}
	public void setDeptLevel(String deptLevel) {
		this.deptLevel = deptLevel;
	}
	public String getParentDeptId() {
		return parentDeptId;
	}
	public void setParentDeptId(String parentDeptId) {
		this.parentDeptId = parentDeptId;
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
