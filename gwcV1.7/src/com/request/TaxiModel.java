package com.request;

import java.io.Serializable;

public class TaxiModel implements Serializable {
	private String TaxiModelId;
	private String TaxiModelName;
	private String TaxiBrand;
	private String MaxPassengerNumber;
	@Override
	public String toString() {
		return "TaxiModel [TaxiModelId=" + TaxiModelId + ", TaxiModelName=" + TaxiModelName + ", TaxiBrand=" + TaxiBrand
				+ ", MaxPassengerNumber=" + MaxPassengerNumber + "]";
	}
	public String getTaxiModelId() {
		return TaxiModelId;
	}
	public void setTaxiModelId(String taxiModelId) {
		TaxiModelId = taxiModelId;
	}
	public String getTaxiModelName() {
		return TaxiModelName;
	}
	public void setTaxiModelName(String taxiModelName) {
		TaxiModelName = taxiModelName;
	}
	public String getTaxiBrand() {
		return TaxiBrand;
	}
	public void setTaxiBrand(String taxiBrand) {
		TaxiBrand = taxiBrand;
	}
	public String getMaxPassengerNumber() {
		return MaxPassengerNumber;
	}
	public void setMaxPassengerNumber(String maxPassengerNumber) {
		MaxPassengerNumber = maxPassengerNumber;
	}
	
	
}
