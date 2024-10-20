package de.esempe.demo.boundary;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.esempe.demo.domain.PingResultRecord;
import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/ping")
public class PingResource
{
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@PermitAll
	public ResponseEntity<PingResultRecord> getPing()
	{
		final LocalDateTime now = LocalDateTime.now();
		final var body = new PingResultRecord(now, "Ping vom Server");
		final var status = HttpStatus.OK;

		// final var result = ResponseEntity.ok(body);
		final var result = ResponseEntity.status(status).body(body);
		return result;
	}

}
