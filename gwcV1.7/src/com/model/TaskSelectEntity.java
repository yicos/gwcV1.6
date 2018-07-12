package com.model;

import java.io.Serializable;

public class TaskSelectEntity  implements Serializable {
	private String seq;
	private String flag;
	private String flagShow;
	private String cmd;
	private int  uploadDegree;
	private String exceptionMsg;
	
	
	public String getFlagShow() {
		if("1".equals(this.flag)) {
			flagShow="新增";
		}else if("2".equals(this.flag)) {
			flagShow="修改";
		}else if("3".equals(this.flag)) {
			flagShow="删除";
		}else {
			flagShow="实时位置";
		}
		return flagShow;
	}

	public void setFlagShow(String flagShow) {
		this.flagShow = flagShow;
	}

	public String getFlag() {
		return flag;
	}
	
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
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
	
	
}
