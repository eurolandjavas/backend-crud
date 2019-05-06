package com.arved.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arved.model.User;
import com.arved.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@CrossOrigin(origins = "${allow.server}")
	@GetMapping(value = "/getusers")
	public List<User> getAll() {
		return userService.getUserList();
	}

	@CrossOrigin(origins = "${allow.server}")
	@PostMapping(value = "/adduser")
	public List<User> addUser(@RequestBody User user) {
		return userService.addNewUser(user);
	}

	@CrossOrigin(origins = "${allow.server}")
	@DeleteMapping(value = "/deleteuser")
	public List<User> deleteUser(@RequestParam int id) {
		return userService.deleteUserById(id);
	}

	@CrossOrigin(origins = "${allow.server}")
	@PutMapping(value = "/updateuser")
	public List<User> update(@RequestBody User user) {
		return userService.updateUser(user);
	}
}
