package com.request;

import java.io.Serializable;

public class DeptInfoTaskReq implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String PlatformID;
	private String OrgID;
	private String Name;
	private String ShortName;
	private String Phone;
	private String Memo;
	private String AreaCode;
	private String AreaName;
	private String AreaLevel;
	private String Flag;
	public String getPlatformID() {
		return PlatformID;
	}
	public void setPlatformID(String platformID) {
		PlatformID = platformID;
	}
	public String getOrgID() {
		return OrgID;
	}
	public void setOrgID(String orgID) {
		OrgID = orgID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getShortName() {
		return ShortName;
	}
	public void setShortName(String shortName) {
		ShortName = shortName;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getMemo() {
		return Memo;
	}
	public void setMemo(String memo) {
		Memo = memo;
	}
	public String getAreaCode() {
		return AreaCode;
	}
	public void setAreaCode(String areaCode) {
		AreaCode = areaCode;
	}
	public String getAreaName() {
		return AreaName;
	}
	public void setAreaName(String areaName) {
		AreaName = areaName;
	}
	public String getAreaLevel() {
		return AreaLevel;
	}
	public void setAreaLevel(String areaLevel) {
		AreaLevel = areaLevel;
	}
	public String getFlag() {
		return Flag;
	}
	public void setFlag(String flag) {
		Flag = flag;
	}
	@Override
	public String toString() {
		return "DeptInfoTaskReq [PlatformID=" + PlatformID + ", OrgID=" + OrgID + ", Name=" + Name + ", ShortName="
				+ ShortName + ", Phone=" + Phone + ", Memo=" + Memo + ", AreaCode=" + AreaCode + ", AreaName="
				+ AreaName + ", AreaLevel=" + AreaLevel + ", Flag=" + Flag + "]";
	}
	
	
	
}
