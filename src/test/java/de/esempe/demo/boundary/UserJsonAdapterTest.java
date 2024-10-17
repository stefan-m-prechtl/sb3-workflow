package de.esempe.demo.boundary;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import de.esempe.demo.domain.User;

@JsonTest
public class UserJsonAdapterTest
{
	@Autowired
	private JacksonTester<User> jacksonTester;

	@Test
	void convertToJson() throws IOException
	{
		final User user = new User();
		user.setFirstname("Stefan");
		user.setLastname("Precthl");

		final JsonContent<User> resultWrite = this.jacksonTester.write(user);
		final String jsonUser = resultWrite.getJson();

		assertThat(jsonUser).contains("first");

	}
}
