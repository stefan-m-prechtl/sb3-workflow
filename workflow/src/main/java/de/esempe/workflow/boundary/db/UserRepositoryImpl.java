package de.esempe.workflow.boundary.db;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import de.esempe.workflow.domain.User;

@Repository
public class UserRepositoryImpl
{
	private final UserRepository repository;

	@Lazy
	public UserRepositoryImpl(final UserRepository repository)
	{
		this.repository = repository;
	}

	public List<User> findAll()
	{
		final List<User> result = this.repository.findAll();
		return result;
	}

	public Optional<User> findById(final long id)
	{
		final Optional<User> result = this.repository.findById(id);
		return result;
	}

	public boolean existsById(final long id)
	{
		final boolean result = this.repository.existsById(id);
		return result;
	}

	public void deleteById(final long id)
	{
		this.repository.deleteById(id);
	}

	public User save(final User user)
	{
		final User result = this.repository.save(user);
		return result;
	}

	public List<User> findByUsername(final String username)
	{
		final List<User> result = this.repository.findByUsername(username);
		return result;
	}

	public void deleteAll()
	{
		this.repository.deleteAll();

	}

	public List<User> findAll(final Example<User> example)
	{
		final List<User> result = this.repository.findAll(example);
		return result;
	}

	public List<User> findByLastname(final String lastName)
	{
		final List<User> result = this.repository.findByLastname(lastName);
		return result;
	}

}
