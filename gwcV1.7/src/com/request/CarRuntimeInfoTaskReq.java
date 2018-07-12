package com.request;

import java.io.Serializable;

public class CarRuntimeInfoTaskReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String TerminalId;
	private String PlateNo;
	private double Longitude;
	private double Latitude;
	private String LocTime;
	private String isPosition;
	private Float Speed;
	private Float Direction;
	private double SubMileage;

	public String getIsPosition() {
		return isPosition;
	}

	public void setIsPosition(String isPosition) {
		this.isPosition = isPosition;
	}

	public String getTerminalId() {
		return TerminalId;
	}

	public void setTerminalId(String terminalId) {
		TerminalId = terminalId;
	}

	public String getPlateNo() {
		return PlateNo;
	}

	public void setPlateNo(String plateNo) {
		PlateNo = plateNo;
	}

	public double getLongitude() {
		return Longitude;
	}

	public void setLongitude(double longitude) {
		Longitude = longitude;
	}

	public double getLatitude() {
		return Latitude;
	}

	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public String getLocTime() {
		return LocTime;
	}

	public void setLocTime(String locTime) {
		LocTime = locTime;
	}

	public Float getSpeed() {
		return Speed;
	}

	public void setSpeed(Float speed) {
		Speed = speed;
	}

	public Float getDirection() {
		return Direction;
	}

	public void setDirection(Float direction) {
		Direction = direction;
	}

	public double getSubMileage() {
		return SubMileage;
	}

	public void setSubMileage(double subMileage) {
		SubMileage = subMileage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CarRuntimeInfoTaskReq [TerminalId=" + TerminalId + ", PlateNo=" + PlateNo + ", Longitude=" + Longitude
				+ ", Latitude=" + Latitude + ", LocTime=" + LocTime + ", Speed=" + Speed + ", Direction=" + Direction
				+ ", SubMileage=" + SubMileage + "]";
	}
}
