package de.esempe.workflow.boundary.db;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import de.esempe.workflow.domain.GlobalRole;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Integrationstext GlobalRoleRepository/Postgres-DB")
@Tag("integration-test")
public class GlobalRoleRespositoryTest
{
	@Autowired
	GlobalRoleRespository objUnderTest;

	private final String rolename = "test-role";
	private final String description = "test-role description";

	private int id = -1;

	@Test
	@Order(1)
	@Rollback(false)
	@DisplayName("Delete all data from table")
	void deleteAllTest()
	{
		this.objUnderTest.deleteAll();

		final List<GlobalRole> allRoles = this.objUnderTest.findAll();
		assertThat(allRoles).isEmpty();
	}

	@Test
	@Order(2)
	@DisplayName("Load data from empty table")
	void findAllEmptyDbTest()
	{
		final List<GlobalRole> allUsers = this.objUnderTest.findAll();
		assertThat(allUsers).isEmpty();
	}

	@Test
	@Order(3)
	@Rollback(false)
	@DisplayName("Insert data into empty table")
	void saveInsertTest()
	{
		final GlobalRole entity = GlobalRole.create(this.rolename);
		entity.setDescription(this.description);

		final GlobalRole savedEntity = this.objUnderTest.save(entity);
		assertThat(savedEntity).isNotNull();
		assertThat(savedEntity.getId()).isGreaterThan(0);
		assertThat(savedEntity.getDescription()).isEqualTo(this.description);

		this.id = savedEntity.getId();
	}

	@Test
	@Order(4)
	@DisplayName("Load data from not empty table")
	void findAllNotEmptyDbTest()
	{
		final List<GlobalRole> allUsers = this.objUnderTest.findAll();
		assertThat(allUsers).isNotEmpty();
		assertThat(allUsers).hasSize(1);
	}

	@Test
	@Order(5)
	@DisplayName("Load data by id from not empty table")
	void findByIdTest()
	{
		final Optional<GlobalRole> result = this.objUnderTest.findById(this.id);
		assertThat(result).isPresent();
		assertThat(result.get().getId()).isEqualTo(this.id);
	}

	@Test
	@Order(6)
	@DisplayName("Load data by name from not empty table")
	void findByLastnameTest()
	{
		final List<GlobalRole> result = this.objUnderTest.findByRoleName(this.rolename);
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
		final Optional<GlobalRole> result = this.objUnderTest.findById(this.id);
		final GlobalRole loadedEntity = result.get();
		loadedEntity.setDescription("Updated description");

		final GlobalRole updateEntity = this.objUnderTest.save(loadedEntity);
		assertThat(updateEntity).isNotNull();
		assertThat(updateEntity.getId()).isGreaterThan(0);
		assertThat(updateEntity.getRoleName()).isEqualTo(loadedEntity.getRoleName());
		assertThat(updateEntity.getDescription()).isEqualTo(loadedEntity.getDescription());
	}

}
