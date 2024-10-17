package de.esempe.demo.boundary;

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
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getSysAdmin()
	{
		final User sysadm = new User();
		sysadm.setFirstname("sysadm");
		sysadm.setLastname("sysadm");

		final var result = ResponseEntity.status(HttpStatus.OK).body(sysadm);
		return result;
	}
}
