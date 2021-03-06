package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserSevice implements UserDetailsService {
	
	@Value("${hostname}")
	private String hostname;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private MailSender mailSender;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found!");
		}
		return user;
	}

	public String addUser(User user) {
		User userByUsername = userRepo.findByUsername(user.getUsername());
		User userByEmail = userRepo.findByEmail(user.getEmail());
		if (userByUsername != null) {
			return "usernameError";
		}
		if(userByEmail != null) {
			return "emailError";
		}
		user.setActive(true);
		user.setRoles(Collections.singleton(Role.USER));
		user.setActivationCode(UUID.randomUUID().toString());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
		sendMessageToActivate(user);
		return "";
	}

	private void sendMessageToActivate(User user) {
		if (!StringUtils.isEmpty(user.getEmail())) {
			String message = String.format(
					"Hello, %s! \n" + "Welcome to Sweater. Please, visit next link: http://%s/activate/%s",
					user.getUsername(), hostname, user.getActivationCode());
			mailSender.send(user.getEmail(), "Activation code", message);
		}
	}
	
	public void sendMessageToChange(User user, String target) {
		String code = UUID.randomUUID().toString();
		if(target.equals("email")) {
			user.setActivationCode(code);
		}
		if(target.equals("password")) {
			user.setPasswordResetCode(code);
		}
		String message = String.format(
				"Hello, %s! \n" + "this is your key to change " + target + ": %s",
				user.getUsername(), 
				target.equals("email") ? user.getActivationCode() : user.getPasswordResetCode());
		mailSender.send(user.getEmail(), "Change email", message);
	}

	public boolean activateUser(String code) {
		User user = userRepo.findByActivationCode(code);
		if (user == null) {
			return false;
		}
		user.setActivationCode(null);
		userRepo.save(user);
		return true;
	}
	
	public boolean activatePassword(String code) {
		User user = userRepo.findByPasswordResetCode(code);
		if (user == null) {
			return false;
		}
		user.setPasswordResetCode(null);
		userRepo.save(user);
		return true;
	}

	public List<User> findAll() {
		return userRepo.findAll();
	}

	public void saveUser(User user, String username, Map<String, String> form) {
		user.setUsername(username);
		Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

		user.getRoles().clear();
		for (String key : form.keySet()) {
			if (roles.contains(key)) {
				user.getRoles().add(Role.valueOf(key));
			}
		}
		userRepo.save(user);
	}
	
	public void updateProfilePassword(User user, String password) {
		if (!StringUtils.isEmpty(password)) {
			user.setPassword(passwordEncoder.encode(password));
		}
		userRepo.save(user);
	}
	
	public void updateProfileEmail(User user, String email) {
		user.setActivationCode(null);
		user.setEmail(email);
		user.setActivationCode(UUID.randomUUID().toString());
		userRepo.save(user);
		sendMessageToActivate(user);
	}

	public void subscribe(User currentUser, User user) {
		user.getSubscribers().add(currentUser);
		userRepo.save(user);
	}

	public void unSubscribe(User currentUser, User user) {
		user.getSubscribers().remove(currentUser);
		userRepo.save(user);
	}
	
}
