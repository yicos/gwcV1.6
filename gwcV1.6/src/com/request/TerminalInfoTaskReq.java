package com.request;

import java.io.Serializable;

public class TerminalInfoTaskReq implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String  TerminalId;
	private String  TypeModel;
	private String  platformId;
	private String  SimNo;
	private int  SimType;
	private int  factory;
	private String  flag;
	
	public String getTerminalId() {
		return TerminalId;
	}
	
	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public void setTerminalId(String terminalId) {
		TerminalId = terminalId;
	}
	public String getTypeModel() {
		return TypeModel;
	}
	public void setTypeModel(String typeModel) {
		TypeModel = typeModel;
	}
	public String getSimNo() {
		return SimNo;
	}
	
	public int getSimType() {
		return SimType;
	}
	public void setSimType(int simType) {
		SimType = simType;
	}
	public int getFactory() {
		return factory;
	}
	public void setFactory(int factory) {
		this.factory = factory;
	}
	public void setSimNo(String simNo) {
		SimNo = simNo;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "TerminalInfoTaskReq [TerminalId=" + TerminalId + ", TypeModel=" + TypeModel + ", SimNo=" + SimNo
				+ ", SimType=" + SimType + ", factory=" + factory + ", flag=" + flag + "]";
	}
	
}
