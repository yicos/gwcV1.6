package com.request;

import java.io.Serializable;

public class GetTaxiAndTypeReq implements Serializable {
	private String PlatformID;

	public String getPlatformID() {
		return PlatformID;
	}

	public void setPlatformID(String platformID) {
		PlatformID = platformID;
	}

	public GetTaxiAndTypeReq(String platformID) {
		super();
		PlatformID = platformID;
	}

	public GetTaxiAndTypeReq() {
		super();
	}
	
	
	
	
}
