package de.esempe.workflow.boundary.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import de.esempe.workflow.domain.User;

@JsonTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit-test")
public class UserJsonAdapterTest
{
	@Autowired
	private JacksonTester<User> jacksonTester;

	private String json = "";

	@Test
	@Order(1)
	void convertToJson() throws IOException
	{
		final User user = new User();
		user.setFirstname("Stefan");
		user.setLastname("Precthl");

		final JsonContent<User> resultWrite = this.jacksonTester.write(user);
		final String jsonUser = resultWrite.getJson();

		assertThat(jsonUser).contains("first");

		this.json = jsonUser;

	}

	@Test()
	@Order(2)
	void convertFromJson() throws IOException
	{
		final User user = this.jacksonTester.parseObject(this.json);
		assertThat(user.getFirstname()).isEqualTo("Stefan");
	}
}
