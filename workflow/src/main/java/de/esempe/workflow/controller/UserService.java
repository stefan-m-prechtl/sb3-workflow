package de.esempe.workflow.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.esempe.workflow.boundary.db.UserRepositoryImpl;
import de.esempe.workflow.domain.User;

@Service
public class UserService
{
	@Autowired
	UserRepositoryImpl repository;

	public Optional<User> loadUserByUsername(final String username)
	{
		final List<User> users = this.repository.findByUsername(username);
		if (users.isEmpty())
		{
			return Optional.empty();
		}

		final var result = Optional.of(users.getFirst());
		return result;

	}

}
