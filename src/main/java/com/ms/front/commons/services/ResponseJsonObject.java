package com.ms.front.commons.services;

public class ResponseJsonObject {
	
	private int status;
	private String payload;

	public ResponseJsonObject(int status, String payload) {
		super();
		this.status = status;
		this.payload = payload;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", payload=" + payload + "]";
	}
	

}
