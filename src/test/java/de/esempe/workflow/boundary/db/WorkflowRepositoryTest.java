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

import de.esempe.workflow.boundary.db.WorkflowRepository;
import de.esempe.workflow.boundary.db.WorkflowStateRepository;
import de.esempe.workflow.boundary.db.WorkflowTransitionRepository;
import de.esempe.workflow.domain.Workflow;
import de.esempe.workflow.domain.WorkflowRule;
import de.esempe.workflow.domain.WorkflowState;
import de.esempe.workflow.domain.WorkflowTransition;
import de.esempe.workflow.domain.WorkflowTransition.TransistionType;

@DataMongoTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Integrationstext WorkflowRepository/Mongo-DB")
@Tag("integration-test")
public class WorkflowRepositoryTest
{
	@Autowired
	WorkflowRepository objUnderTest;

	@Autowired
	WorkflowStateRepository repositoryStates;
	@Autowired
	WorkflowTransitionRepository repositoryTransitions;

	@BeforeAll
	void setup()
	{
		// clean database
		this.repositoryTransitions.deleteAll();
		this.repositoryStates.deleteAll();
	}

	@Test
	@Order(1)
	@Rollback(false)
	@DisplayName("Delete all data from table")
	void deleteAllTest()
	{
		this.objUnderTest.deleteAll();

		final List<Workflow> allWorfkflows = this.objUnderTest.findAll();
		assertThat(allWorfkflows).isEmpty();
	}

	@Test
	@Order(2)
	@DisplayName("Load data from empty table")
	void findAllEmptyDbTest()
	{
		final List<Workflow> allWorfkflows = this.objUnderTest.findAll();
		assertThat(allWorfkflows).isEmpty();
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
		final WorkflowState stateAblehnen = WorkflowState.create("Ablehnen");
		final WorkflowRule rule = WorkflowRule.create("Empty Rule", "");

		final WorkflowTransition transitionBearbeiten = WorkflowTransition.create("bearbeiten", stateStart, stateBearbeiten);
		transitionBearbeiten.setType(TransistionType.USER);
		transitionBearbeiten.setRule(rule);

		final WorkflowTransition transitionAblehnen = WorkflowTransition.create("ablehnen", stateStart, stateAblehnen);
		transitionAblehnen.setType(TransistionType.USER);
		transitionAblehnen.setRule(rule);

		this.repositoryStates.save(stateStart);
		this.repositoryStates.save(stateBearbeiten);
		this.repositoryStates.save(stateAblehnen);
		this.repositoryTransitions.save(transitionBearbeiten);
		this.repositoryTransitions.save(transitionAblehnen);

		final Workflow workflow = Workflow.create("demo");
		workflow.addTransition(transitionBearbeiten);
		workflow.addTransition(transitionAblehnen);

		// act
		final Workflow savedWorkflow = this.objUnderTest.save(workflow);
		// verify
		assertThat(savedWorkflow).isNotNull();

	}

}
