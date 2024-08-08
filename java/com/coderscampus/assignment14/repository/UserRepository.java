package com.coderscampus.assignment14.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.coderscampus.assignment14.domain.User;

@Repository
public class UserRepository {
	Map<Long, User> users = new HashMap<>();

	public User save(User user) {

		user.setName(user.getName());
		user.setId(user.getId());
		users.put(user.getId(), user);
		return user;
	}

	public User findById(Long id) {
		return users.get(id);
	}

	public User findByName(String name) {
		return users.values().stream().filter(user -> user.getName().equals(name)).findFirst().orElse(null);
	}
}
