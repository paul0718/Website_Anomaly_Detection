package com.paul.sre.detect.vo;

public class AccountValidationResult {
	
	public AccountValidationResult(int code) {
		super();
		this.code = code;
	}

	public AccountValidationResult(int code, String errMsg) {
		super();
		this.code = code;
		this.errMsg = errMsg;
	}

	private int code;
	private String errMsg;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	public static int CODE_VALID = 0;
	public static int CODE_INVALID_NAME = 1;
}
