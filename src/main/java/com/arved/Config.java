package com.arved;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.arved.model.User;

@Configuration
public class Config {

	@Bean
	public ArrayList<User> userList() {
		return userlist();

	}

	private ArrayList<User> userlist() {
		return new ArrayList<>(Arrays.asList(new User(1, "John", "Lennon", "London", 41),
				new User(2, "Georg", "Malvius", "Stockholm", 23), new User(3, "Greg", "David", "New York", 16),
				new User(4, "Rutger", "Kahn", "Barcelona", 60), new User(5, "Lisa", "Kahn", "Barcelona", 59),
				new User(6, "Mari", "Merri", "Vienna", 24)));
	}
}
