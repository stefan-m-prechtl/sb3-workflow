package de.esempe.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import de.esempe.demo.boundary.PingResource;
import de.esempe.demo.domain.PingResultRecord;

@SpringBootTest
class DemoApplicationTests
{
	@Autowired
	PingResource pingResource;

	@Test
	void contextLoads()
	{

	}

	@Test
	void testGetPing()
	{
		final ResponseEntity<PingResultRecord> resultPing = this.pingResource.getPing();

		assertThat(resultPing).isNotNull();
		assertThat(resultPing.getStatusCode().value()).isEqualTo(200);
		assertThat(resultPing.getBody()).isNotNull();
		assertThat(resultPing.getBody().msg()).isEqualTo("Ping vom Server");

	}

}
