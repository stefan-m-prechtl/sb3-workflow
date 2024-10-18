package de.esempe.demo.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.esempe.demo.domain.User;

@RestController
@RequestMapping("user")
public class UserResource
{
	@Autowired
	private UserRepository repository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<User>> getUsers()
	{
		final Iterable<User> all = this.repository.findAll();

		final var result = ResponseEntity.status(HttpStatus.OK).body(all);
		return result;
	}
}
