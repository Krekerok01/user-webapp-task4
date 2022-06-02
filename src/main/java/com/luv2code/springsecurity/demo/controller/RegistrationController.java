package com.luv2code.springsecurity.demo.controller;

import java.util.logging.Logger;

import com.luv2code.springsecurity.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luv2code.springsecurity.demo.user.CrmUser;
import com.luv2code.springsecurity.demo.entity.User;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
    @Autowired
    private UserServiceImpl userServiceImpl;
	
    private Logger logger = Logger.getLogger(getClass().getName());

	
	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model theModel) {
		theModel.addAttribute("crmUser", new CrmUser());
		return "registration-form";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(@Valid @ModelAttribute("crmUser") CrmUser theCrmUser, Model theModel,  BindingResult result) {

		//???
		if (result.hasErrors()){
			System.out.println("ERR");
			return "registration-form";
		}

		String userName = theCrmUser.getUsername();
		logger.info("Processing registration form for: " + userName);

		logger.info("theCrmUser: " + theCrmUser);


        // create user account        						
        userServiceImpl.save(theCrmUser);

        
        logger.info("Successfully created user: " + userName);
        
        return "registration-confirmation";		
	}
}
