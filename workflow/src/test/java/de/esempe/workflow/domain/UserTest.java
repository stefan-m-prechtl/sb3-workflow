package de.esempe.workflow.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Integrationstests User-Entity/Postgres-Datenbank")
@Tag("integration-test")
public class UserTest extends AbstractEntityTest<User>
{
	@BeforeAll
	void setUp() throws Exception
	{
		final List<String> initialQueries = new ArrayList<>();
		initialQueries.add("DELETE FROM restdemo.t_users");
		super.setUp(initialQueries, User.class);
	}

	@Override
	protected User createObjUnderTest()
	{
		final var result = User.create("prs");
		result.setFirstname("Stefan");
		result.setLastname("Prechtl");

		return result;
	}

	@Override
	protected User updateObjUnderTest(final User entity)
	{
		entity.setLastname(entity.getLastname().toUpperCase());
		return entity;
	}

}
