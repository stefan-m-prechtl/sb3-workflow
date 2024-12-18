package de.esempe.workflow.boundary.db;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import de.esempe.workflow.domain.WorkflowRule;
import de.esempe.workflow.domain.WorkflowState;
import de.esempe.workflow.domain.WorkflowTransition;
import de.esempe.workflow.domain.WorkflowTransition.TransistionType;

@DataMongoTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Integrationstext WorkflowTransitionRepository/Mongo-DB")
@Tag("integration-test")
public class WorkflowTransitionRepositoryTest
{
	@Autowired
	WorkflowTransitionRepository objUnderTest;

	@Autowired
	WorkflowStateRepository repositoryStates;

	@BeforeAll
	void setup()
	{
		// clean database
		this.repositoryStates.deleteAll();
	}

	@Test
	@Order(1)
	@Rollback(false)
	@DisplayName("Delete all data from table")
	void deleteAllTest()
	{
		this.objUnderTest.deleteAll();

		final List<WorkflowTransition> allTransitions = this.objUnderTest.findAll();
		assertThat(allTransitions).isEmpty();
	}

	@Test
	@Order(2)
	@DisplayName("Load data from empty table")
	void findAllEmptyDbTest()
	{
		final List<WorkflowTransition> allTransitions = this.objUnderTest.findAll();
		assertThat(allTransitions).isEmpty();
	}

	@Test
	@Order(3)
	@Rollback(false)
	@DisplayName("Insert data into empty table")
	void saveInsertTest()
	{
		// prepare
		final WorkflowState stateStart = WorkflowState.create("Start");
		final WorkflowState stateBearbeiten = WorkflowState.create("Bearbeiten");
		final WorkflowRule rule = WorkflowRule.create("Empty Rule", "");

		this.repositoryStates.save(stateStart);
		this.repositoryStates.save(stateBearbeiten);

		final WorkflowTransition transition = WorkflowTransition.create("bearbeiten", stateStart, stateBearbeiten);
		transition.setType(TransistionType.USER);
		transition.setRule(rule);

		// act
		final WorkflowTransition savedTransition = this.objUnderTest.save(transition);
		// verify
		assertThat(savedTransition).isNotNull();

	}

}
