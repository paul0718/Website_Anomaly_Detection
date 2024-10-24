package com.paul.sre.detect.vo;

public class Response {
	
	public Response(int status, Object result) {
		super();
		this.status = status;
		this.result = result;
	}
	
	private int status;
	private Object result;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
	
}
