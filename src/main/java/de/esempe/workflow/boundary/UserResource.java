package de.esempe.workflow.boundary;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.esempe.workflow.domain.User;

@RestController
@RequestMapping("user")
public class UserResource
{
	@Autowired
	private UserRepository repository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> getUsers()
	{
		final List<User> all = this.repository.findAll();
		final var result = ResponseEntity.status(HttpStatus.OK).body(all);
		return result;
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUser(@PathVariable final int id)
	{
		final Optional<User> dbResult = this.repository.findById(id);
		if (dbResult.isEmpty())
		{
			final ResponseEntity<?> result = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			return result;
		}

		final var result = ResponseEntity.status(HttpStatus.OK).body(dbResult.get());
		return result;
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable final int id)
	{
		final boolean exists = this.repository.existsById(id);
		if (!exists)
		{
			final ResponseEntity<?> result = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			return result;
		}

		this.repository.deleteById(id);
		final var result = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		return result;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody final User user)
	{
		final User savedUser = this.repository.save(user);

		final URI resourceURI = ServletUriComponentsBuilder//
				.fromCurrentRequest()//
				.path("/{id}")//
				.buildAndExpand(savedUser.getId())//
				.toUri();

		final ResponseEntity<?> result = ResponseEntity.status(HttpStatus.CREATED).location(resourceURI).build();
		return result;
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUser(@PathVariable final int id, @RequestBody final User user)
	{
		if (id != user.getId())
		{
			final ResponseEntity<?> result = ResponseEntity.status(HttpStatus.CONFLICT).body("IDs stimmen nicht überein");
			return result;
		}
		final Optional<User> resultDb = this.repository.findById(user.getId());
		if (resultDb.isEmpty())
		{
			final ResponseEntity<?> result = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			return result;
		}

		final User savedUser = this.repository.save(user);

		final var result = ResponseEntity.status(HttpStatus.OK).body(savedUser);
		return result;
	}
}
