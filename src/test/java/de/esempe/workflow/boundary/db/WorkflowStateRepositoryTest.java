package de.esempe.workflow.boundary.db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import de.esempe.workflow.DatabaseConfig;
import de.esempe.workflow.boundary.db.listener.WorkflowStateRepositoryListener;
import de.esempe.workflow.domain.WorkflowState;

@DataMongoTest
@ActiveProfiles("test")
@Import({WorkflowStateRepositoryListener.class, DatabaseConfig.class})
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
		entity.setScriptEnter("println 'Entered state START'");
		entity.setScriptLeave("println 'Left state START'");

		final WorkflowState savedEntity = this.objUnderTest.save(entity);
		assertThat(savedEntity).isNotNull();
		assertThat(savedEntity.getDbId()).isNotNull();
		assertThat(savedEntity.getObjId()).isNotNull();
		assertThat(savedEntity.getName()).isEqualTo("Start");

	}

	@Test
	@Order(4)
	@DisplayName("Load all data from not empty table")
	void findAllNotEmptyDbTest()
	{
		final List<WorkflowState> allStates = this.objUnderTest.findAll();
		assertThat(allStates).isNotEmpty();
		assertThat(allStates).hasSize(1);
	}

	@Test
	@Order(4)
	@DisplayName("Load one by name from not empty table")
	void findByNameDbTest()
	{
		final WorkflowState state = this.objUnderTest.findByName("Start");
		assertThat(state).isNotNull();
		assertThat(state.getName()).isEqualTo("Start");
	}

	@Test
	@Order(10)
	@Rollback(false)
	@DisplayName("Insert same data into table")
	void saveAgainInsertTest()
	{
		final WorkflowState entity = WorkflowState.create("Start");

		final DuplicateKeyException thrown = assertThrows(DuplicateKeyException.class, () -> {
			this.objUnderTest.save(entity);
		});
		assertThat(thrown.getMessage()).contains("workflowdb.states index: name dup key: { name: \"Start\" }'");

	}

}
