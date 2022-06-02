package com.luv2code.springsecurity.demo.service;


import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.repository.UserRepository;
import com.luv2code.springsecurity.demo.user.CrmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;



	@Transactional
	public User findByUsername(String username) {
		Optional<User> user = userRepository.findByUsername(username);

		if(!user.isPresent()) {

			throw new UsernameNotFoundException("User Not Found");
		}
		// passwordEncoder.matches("123", user.get().getPassword());
		User user1 = user.get();

		return user1;
	}



	@Transactional
	public void save(CrmUser crmUser) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		Date date = new Date();

		User user = new User();
		 // assign user details to the user object
		user.setUsername(crmUser.getUsername());
		user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
		user.setName(crmUser.getName());
		user.setEmail(crmUser.getEmail());
		user.setRegistrationDate(dateFormat.format(date));
		user.setLastBeenDate(dateFormat.format(date));
		user.setEnabled(true);
		user.setAccountNonLocked(true);
		user.setStatus("ACTIVE");

		 // save user in the database
		userRepository.save(user);
	}

	@Transactional
	public void saveUser(User user) {

		// save user in the database
		userRepository.save(user);
	}

	@Transactional
	public void delete(int id) {
		userRepository.deleteById(id);
	}


	@Transactional
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}


	@Override
	//@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);

		if(!user.isPresent()) {

			throw new UsernameNotFoundException("User Not Found");
		}
		// passwordEncoder.matches("123", user.get().getPassword());
		User user1 = user.get();
		return user1;
	}


}


