package com.paul.sre.detect.service;

import com.paul.sre.detect.vo.AccountValidationResult;

public interface IAccountService {

	public AccountValidationResult validateAccountName(String userName);
}
