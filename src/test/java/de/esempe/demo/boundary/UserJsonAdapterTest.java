package de.esempe.demo.boundary;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserJsonAdapterTest
{
//	@Autowired
//	private JacksonTester<User> jacksonTester;
//
//	private String json = "";
//
//	@Test
//	@Order(1)
//	void convertToJson() throws IOException
//	{
//		final User user = new User();
//		user.setFirstname("Stefan");
//		user.setLastname("Precthl");
//
//		final JsonContent<User> resultWrite = this.jacksonTester.write(user);
//		final String jsonUser = resultWrite.getJson();
//
//		assertThat(jsonUser).contains("first");
//
//		this.json = jsonUser;
//
//	}
//
//	@Test()
//	@Order(2)
//	void convertFromJson() throws IOException
//	{
//		final User user = this.jacksonTester.parseObject(this.json);
//		assertThat(user.getFirstname()).isEqualTo("Stefan");
//	}
}
