package de.esempe.workflow.boundary;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.annotation.Rollback;

import de.esempe.workflow.domain.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Integrationstext UserRepository/Postgres-DB")
public class UserRepositoryTest
{
	private int id = -1;
	private String firstname = "Stefan";
	private String lastName = "Prechtl";

	@Autowired
	UserRepository objUnderTest;

	@Test
	@Order(1)
	@Rollback(false)
	@DisplayName("Delete all data from table")
	void deleteAllTest()
	{
		this.objUnderTest.deleteAll();

		final List<User> allUsers = this.objUnderTest.findAll();
		assertThat(allUsers).isEmpty();
	}

	@Test
	@Order(2)
	@DisplayName("Load data from empty table")
	void findAllEmptyDbTest()
	{
		final List<User> allUsers = this.objUnderTest.findAll();
		assertThat(allUsers).isEmpty();
	}

	@Test
	@Order(3)
	@Rollback(false)
	@DisplayName("Insert data into empty table")
	void saveInsertTest()
	{
		final User entity = new User();
		entity.setFirstname(this.firstname);
		entity.setLastname(this.lastName);

		final User savedEntity = this.objUnderTest.save(entity);
		assertThat(savedEntity).isNotNull();
		assertThat(savedEntity.getId()).isGreaterThan(0);
		assertThat(savedEntity.getFirstname()).isEqualTo(this.firstname);
		assertThat(savedEntity.getLastname()).isEqualTo(this.lastName);

		this.id = savedEntity.getId();
	}

	@Test
	@Order(4)
	@DisplayName("Load data from not empty table")
	void findAllNotEmptyDbTest()
	{
		final List<User> allUsers = this.objUnderTest.findAll();
		assertThat(allUsers).isNotEmpty();
		assertThat(allUsers).hasSize(1);
	}

	@Test
	@Order(5)
	@DisplayName("Load data by id from not empty table")
	void findByIdTest()
	{
		final Optional<User> result = this.objUnderTest.findById(this.id);
		assertThat(result).isPresent();
		assertThat(result.get().getId()).isEqualTo(this.id);
	}

	@Test
	@Order(6)
	@DisplayName("Load data by lastname from not empty table")
	void findByLastnameTest()
	{
		final List<User> result = this.objUnderTest.findByLastname(this.lastName);
		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();
		assertThat(result).hasSize(1);
	}

	@Test
	@Order(7)
	@DisplayName("Load data by example from not empty table")
	void findByExample()
	{
		final User exampleUser = new User();
		exampleUser.setFirstname(this.firstname);

		// Create matcher which ignores null fields and attribute "id"
		final ExampleMatcher matcher = ExampleMatcher.matching() //
				.withIgnoreNullValues() //
				.withIgnorePaths("id");

		// Wrap the user object as an Example
		final Example<User> example = Example.of(exampleUser, matcher);

		// Use the built-in `findAll(Example)` method of the UserRepository
		final List<User> result = this.objUnderTest.findAll(example);

		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();
		assertThat(result).hasSize(1);
	}

	@Test
	@Order(10)
	@Rollback(false)
	@DisplayName("Update data")
	void saveUpdateTest()
	{
		final Optional<User> result = this.objUnderTest.findById(this.id);
		final User loadedEntity = result.get();
		loadedEntity.setLastname("Mustermann-Schmitt");

		final User updateEntity = this.objUnderTest.save(loadedEntity);
		assertThat(updateEntity).isNotNull();
		assertThat(updateEntity.getId()).isGreaterThan(0);
		assertThat(updateEntity.getFirstname()).isEqualTo(loadedEntity.getFirstname());
		assertThat(updateEntity.getLastname()).isEqualTo(loadedEntity.getLastname());
	}

}
