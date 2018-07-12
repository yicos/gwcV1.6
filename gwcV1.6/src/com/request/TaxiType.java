package com.request;

import java.io.Serializable;

public class TaxiType implements Serializable {

	private String TaxiTypeCode;
	private String TaxiTypeName;
	private String PassengerNumber;
	public String getTaxiTypeCode() {
		return TaxiTypeCode;
	}
	public void setTaxiTypeCode(String taxiTypeCode) {
		TaxiTypeCode = taxiTypeCode;
	}
	public String getTaxiTypeName() {
		return TaxiTypeName;
	}
	public void setTaxiTypeName(String taxiTypeName) {
		TaxiTypeName = taxiTypeName;
	}
	public String getPassengerNumber() {
		return PassengerNumber;
	}
	public void setPassengerNumber(String passengerNumber) {
		PassengerNumber = passengerNumber;
	}
	@Override
	public String toString() {
		return "TaxiType [TaxiTypeCode=" + TaxiTypeCode + ", TaxiTypeName=" + TaxiTypeName + ", PassengerNumber="
				+ PassengerNumber + "]";
	}
	
	
}
