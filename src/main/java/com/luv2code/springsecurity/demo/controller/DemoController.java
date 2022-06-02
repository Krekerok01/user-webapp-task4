package com.luv2code.springsecurity.demo.controller;

import com.luv2code.springsecurity.demo.config.CustomAuthenticationSuccessHandler;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.luv2code.springsecurity.demo.config.CustomAuthenticationSuccessHandler.authenticationUserId;
import static com.luv2code.springsecurity.demo.config.CustomAuthenticationSuccessHandler.authenticationUserName;

@Controller
public class DemoController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@GetMapping("/")
	public String showHome(Model model) {

		List<User> users = userServiceImpl.getAllUsers();
		model.addAttribute("users", users);

		return "home";
	}

	@GetMapping("/deleteUser")
	public String deleteUser(@RequestParam("userId") int id){


		// delete
		userServiceImpl.delete(id);

		if (id == authenticationUserId) {
			return "redirect:/showMyLoginPage";
		} else {
			return "redirect:/";
		}
	}


	@GetMapping("/blockUser")
	public String blockUser(@RequestParam("username") String username){

		User user = userServiceImpl.findByUsername(username);

		user.setAccountNonLocked(false);

		userServiceImpl.saveUser(user);

		if (username.equals(authenticationUserName)){
			return "redirect:/showMyLoginPage";
		} else {
			return "redirect:/";
		}
	}

	@GetMapping("/unblockUser")
	public String unblockUser(@RequestParam("username") String username){

		User user = userServiceImpl.findByUsername(username);

		user.setAccountNonLocked(true);

		userServiceImpl.saveUser(user);
		return "redirect:/";
	}


}










