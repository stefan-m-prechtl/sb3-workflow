package de.esempe.workflow.boundary;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.bson.json.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import de.esempe.workflow.domain.WorkflowState;

@DataMongoTest
@ActiveProfiles("test")
@Import(WorkflowStateRepositoryListener.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Integrationstext WorkflowStateRepository/Mongo-DB")
@Tag("integration-test")
public class WorkflowStateRepositoryTest
{
	@Autowired
	WorkflowStateRepository objUnderTest;

	@Test
	@Order(1)
	@Rollback(false)
	@DisplayName("Delete all data from table")
	void deleteAllTest()
	{
		this.objUnderTest.deleteAll();

		final List<WorkflowState> allStates = this.objUnderTest.findAll();
		assertThat(allStates).isEmpty();
	}

	@Test
	@Order(2)
	@DisplayName("Load data from empty table")
	void findAllEmptyDbTest()
	{
		final List<WorkflowState> allStates = this.objUnderTest.findAll();
		assertThat(allStates).isEmpty();
	}

	@Test
	@Order(3)
	@Rollback(false)
	@DisplayName("Insert data into empty table")
	void saveInsertTest()
	{
		final WorkflowState entity = WorkflowState.create("Start");
		entity.setJsondData(new JsonObject("{ \"created\" : \"29.10.2024\"}"));

		final WorkflowState savedEntity = this.objUnderTest.save(entity);
		assertThat(savedEntity).isNotNull();
		assertThat(savedEntity.getDbId()).isNotNull();
		assertThat(savedEntity.getObjId()).isNotNull();
		assertThat(savedEntity.getName()).isEqualTo("Start");

	}

	@Test
	@Order(4)
	@DisplayName("Load data from not empty table")
	void findAllNotEmptyDbTest()
	{
		final List<WorkflowState> allStates = this.objUnderTest.findAll();
		assertThat(allStates).isNotEmpty();
		assertThat(allStates).hasSize(1);
	}

}
