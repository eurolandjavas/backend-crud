package com.arved.service;

import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arved.model.User;

@Service
public class UserService {

	@Resource(name = "userList")
	private List<User> users;

	public List<User> getUserList() {
		return users;
	}

	public List<User> addNewUser(User user) {
		int maxId = users.stream().max(Comparator.comparing(User::getId)).map(m -> m.getId()).orElse(users.size() + 1);
		users.add(new User(maxId + 1, user));
		return users;
	}

	public List<User> deleteUserById(int id) {
		User del = users.stream().filter(item -> item.getId() == id).findFirst().orElse(null);
		users.remove(del);
		return users;
	}

	public List<User> updateUser(User user) {
		users.stream().filter(item -> item.getId() == user.getId()).forEach(a -> {
			a.setAge(user.getAge());
			a.setCity(user.getCity());
			a.setFirstName(user.getFirstName());
			a.setLastName(user.getLastName());
		});
		return users;
	}
}
