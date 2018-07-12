package com.request;

import java.io.Serializable;

public class DriverTaskReq implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String CertNo;
	private String OrgID;
	private String PlatformID;
	private String Name;
	private String Phone;
	private String IDCard;
	private String ExpiryDate;
	private String Authority;
	private String Address;
	private String HomePhone;
	private String Sex;
	/** 驾驶员编制属性（1 在编,2 合同制) */
	private String CompilingAttributes = "1";// TODO 先设置默认为1
	private String LaborContractExp;
	private String LicenseType;
	private String LicenseNumber;
	private String Flag;
	public String getCertNo() {
		return CertNo;
	}
	public void setCertNo(String certNo) {
		CertNo = certNo;
	}
	public String getOrgID() {
		return OrgID;
	}
	public void setOrgID(String orgID) {
		OrgID = orgID;
	}
	public String getPlatformID() {
		return PlatformID;
	}
	public void setPlatformID(String platformID) {
		PlatformID = platformID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getIDCard() {
		return IDCard;
	}
	public void setIDCard(String iDCard) {
		IDCard = iDCard;
	}
	public String getExpiryDate() {
		return ExpiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		ExpiryDate = expiryDate;
	}
	public String getAuthority() {
		return Authority;
	}
	public void setAuthority(String authority) {
		Authority = authority;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getHomePhone() {
		return HomePhone;
	}
	public void setHomePhone(String homePhone) {
		HomePhone = homePhone;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public String getCompilingAttributes() {
		return CompilingAttributes;
	}
	public void setCompilingAttributes(String compilingAttributes) {
		CompilingAttributes = compilingAttributes;
	}
	public String getLaborContractExp() {
		return LaborContractExp;
	}
	public void setLaborContractExp(String laborContractExp) {
		LaborContractExp = laborContractExp;
	}
	public String getLicenseType() {
		return LicenseType;
	}
	public void setLicenseType(String licenseType) {
		LicenseType = licenseType;
	}
	public String getLicenseNumber() {
		return LicenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		LicenseNumber = licenseNumber;
	}
	public String getFlag() {
		return Flag;
	}
	public void setFlag(String flag) {
		Flag = flag;
	}
	@Override
	public String toString() {
		return "DriverTaskReq [CertNo=" + CertNo + ", OrgID=" + OrgID + ", PlatformID=" + PlatformID + ", Name=" + Name
				+ ", Phone=" + Phone + ", IDCard=" + IDCard + ", ExpiryDate=" + ExpiryDate + ", Authority=" + Authority
				+ ", Address=" + Address + ", HomePhone=" + HomePhone + ", Sex=" + Sex + ", CompilingAttributes="
				+ CompilingAttributes + ", LaborContractExp=" + LaborContractExp + ", LicenseType=" + LicenseType
				+ ", LicenseNumber=" + LicenseNumber + ", Flag=" + Flag + "]";
	}
	
	

}
