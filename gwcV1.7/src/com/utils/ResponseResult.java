package com.utils;

public class ResponseResult{
	String Cmd;
	String Result="0";
	String Remark;
	String Data;
	public String getCmd() {
		return Cmd;
	}
	public void setCmd(String cmd) {
		Cmd = cmd;
	}
	public String getResult() {
		return Result;
	}
	public void setResult(String result) {
		Result = result;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getData() {
		return Data;
	}
	public void setData(String data) {
		Data = data;
	}
	@Override
	public String toString() {
		return "ResponseResult [Cmd=" + Cmd + ", Result=" + Result + ", Remark=" + Remark + ", Data=" + Data + "]";
	}
	
	
	
}
