package com.paul.sre.detect.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.paul.sre.detect.service.IAccountService;
import com.paul.sre.detect.vo.AccountValidationResult;

@Service
public class AccountServiceImpl implements IAccountService {
	
	private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);
	
	public static final Pattern ACCOUNT_NAME_PATTERN = Pattern.compile("[a-zA-Z][\\w]{4,9}");
	
	public AccountValidationResult validateAccountName(String userName) {
		if (ObjectUtils.isEmpty(userName)) {
			return new AccountValidationResult(AccountValidationResult.CODE_INVALID_NAME, "User name can't be empty!");
		}
		
		Matcher matcher = ACCOUNT_NAME_PATTERN.matcher(userName);
		if (!matcher.matches()) {
			return new AccountValidationResult(AccountValidationResult.CODE_INVALID_NAME, "Invalid user name!");
		} else {
			return new AccountValidationResult(AccountValidationResult.CODE_VALID);
		}
	}
}
