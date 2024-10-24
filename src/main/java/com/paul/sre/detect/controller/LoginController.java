package com.paul.sre.detect.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paul.sre.detect.service.IAccountService;
import com.paul.sre.detect.vo.AccountValidationResult;
import com.paul.sre.detect.vo.LoginForm;
import com.paul.sre.detect.vo.Response;

/**
 *  MVC
 *  M: model
 *  V: View
 *  C: controller
 *  
 *  GET /login?userName=abc&password=afddas http1.1
 *  
 *  
 *  
 *  
 * @author win
 *
 */
@Controller
public class LoginController {

	private static final Logger logger = LogManager.getLogger(LoginController.class);
	
	@Autowired
	private IAccountService accountService;
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public String login(LoginForm form) {
		logger.info("get login POST request");
		logger.info("user name: {}, password: {}", form.getUserName(), form.getPassword());
		//TODO ...
		return "redirect:/index.html";
	}
	
	@RequestMapping("validateUserName")
	public @ResponseBody Response validateUserName(String userName) {
		logger.info("get validateUserName GET request");
		logger.info("validate user name: {}", userName);
		AccountValidationResult result = this.accountService.validateAccountName(userName);
		logger.error("validate user name: {}", userName);
		return new Response(0, result);
	}
}
