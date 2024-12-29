package de.esempe.workflow.boundary.db;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

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

import de.esempe.workflow.boundary.db.listener.WorkflowTaskRepositoryListener;
import de.esempe.workflow.domain.WorkflowTask;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@DataMongoTest
@Import({WorkflowTaskRepositoryListener.class, DatabaseConfig.class})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Integrationstext WorkflowTask/Mongo-DB")
@Tag("integration-test")
public class WorkflowTaskRepositoryTest
{
	@Autowired
	WorkflowTaskRepository objUnderTest;

	@Test
	@Order(1)
	@Rollback(false)
	@DisplayName("Delete all data from table")
	void deleteAllTest()
	{
		this.objUnderTest.deleteAll();

		final List<WorkflowTask> allTasks = this.objUnderTest.findAll();
		assertThat(allTasks).isEmpty();
	}

	@Test
	@Order(2)
	@DisplayName("Load data from empty table")
	void findAllEmptyDbTest()
	{
		final List<WorkflowTask> allTasks = this.objUnderTest.findAll();
		assertThat(allTasks).isEmpty();
	}

	@Test
	@Order(3)
	@Rollback(false)
	@DisplayName("Insert data into empty table")
	void saveInsertTest()
	{
		final UUID wfObjId = UUID.randomUUID();
		final UUID stateObjId = UUID.randomUUID();
		final JsonObject data = Json.createObjectBuilder() //
				.add("pc", "R9575") //
				.add("dauer", 4) //
				.add("begründung", "SW-Installation")//
				.build();

		final WorkflowTask entity = WorkflowTask.create(wfObjId, "Admin-Rechte");
		entity.setCurrentStateObjId(stateObjId);
		entity.setData(data.toString());

		final WorkflowTask savedEntity = this.objUnderTest.save(entity);
		assertThat(savedEntity).isNotNull();
		assertThat(savedEntity.getDbId()).isNotNull();
	}

	@Test
	@Order(4)
	@DisplayName("Load all data from not empty table")
	void findAllNotEmptyDbTest()
	{
		final List<WorkflowTask> allTasks = this.objUnderTest.findAll();
		assertThat(allTasks).isNotEmpty();
		assertThat(allTasks).hasSize(1);
	}

}
