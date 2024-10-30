package de.esempe.workflow.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.bson.json.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.esempe.workflow.domain.Workflow;
import de.esempe.workflow.domain.WorkflowRule;
import de.esempe.workflow.domain.WorkflowState;
import de.esempe.workflow.domain.WorkflowTask;
import de.esempe.workflow.domain.WorkflowTransition;
import de.esempe.workflow.domain.WorkflowTransition.TransistionType;

@JsonTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit-test")
class TaskControllerTest
{
	private Workflow workflow;

	private TaskController objUnderTest;

	@BeforeAll
	void setup() throws JsonProcessingException
	{
		// Create complette workflow
		final WorkflowState start = WorkflowState.create("Start");
		final WorkflowState genehmigt = WorkflowState.create("Genehmigt");
		final WorkflowState abgelehnt = WorkflowState.create("Abgelehnt");
		final WorkflowState pruefen = WorkflowState.create("In Prüfung");
		final WorkflowTransition automatischeGenehmigung = WorkflowTransition.create("Automatisch genehmigen", start, genehmigt);
		final WorkflowTransition manuellePruefung = WorkflowTransition.create("prüfen", start, pruefen);
		final WorkflowTransition manuelleGenehmigung = WorkflowTransition.create("genehmigen", pruefen, genehmigt);
		final WorkflowTransition manuelleAblehnung = WorkflowTransition.create("ablehnen", pruefen, abgelehnt);

		final var script = """
				import groovy.json.JsonSlurper
				def jsonSlurper = new JsonSlurper()
				def map = jsonSlurper.parseText(data)
				map.dauer == 4;
				""";
		final WorkflowRule ruleMinDauer = WorkflowRule.create("minDauer", script);
		automatischeGenehmigung.setType(TransistionType.SYSTEM);
		automatischeGenehmigung.setRule(ruleMinDauer);

		this.workflow = Workflow.create("Demo");
		this.workflow.addTransition(automatischeGenehmigung);
		this.workflow.addTransition(manuellePruefung);
		this.workflow.addTransition(manuelleGenehmigung);
		this.workflow.addTransition(manuelleAblehnung);

	}

	@Test
	@Order(10)
	void createDauer4()
	{
		final WorkflowTask task = WorkflowTask.create(this.workflow.getObjId(), "SW-Genehmigung");
		// Daten als JSON erzeugen
		final JsonObject data = BsonJsonBuilder.create() //
				.add("pc", "R9575") //
				.add("dauer", 4) //
				.add("begründung", "SW-Installation")//
				.build();
		task.setData(data);

		this.objUnderTest = TaskController.create(this.workflow, task);
		assertThat(this.objUnderTest).isNotNull();
		assertThat(this.objUnderTest.getCurrentState()).isEmpty();
		assertThat(this.objUnderTest.getPossibleTransitions()).isEmpty();
		assertThat(this.objUnderTest.getTask().isFinished()).isFalse();
		assertThat(this.objUnderTest.getTask().isRunning()).isFalse();
	}

	@Test
	@Order(11)
	void startTaskDauer4()
	{
		this.objUnderTest.startTask();

		assertThat(this.objUnderTest.getCurrentState()).isPresent();
		assertThat(this.objUnderTest.getPossibleTransitions()).isEmpty();
		assertThat(this.objUnderTest.getTask().isFinished()).isTrue();
		assertThat(this.objUnderTest.getTask().isRunning()).isFalse();

	}

	@Test
	@Order(20)
	void createDauer60()
	{
		final WorkflowTask task = WorkflowTask.create(this.workflow.getObjId(), "SW-Genehmigung");
		// Daten als JSON erzeugen
		final JsonObject data = BsonJsonBuilder.create() //
				.add("pc", "R9575") //
				.add("dauer", 60) //
				.add("begründung", "SW-Installation")//
				.build();
		task.setData(data);

		this.objUnderTest = TaskController.create(this.workflow, task);
		assertThat(this.objUnderTest).isNotNull();
		assertThat(this.objUnderTest.getCurrentState()).isEmpty();
		assertThat(this.objUnderTest.getPossibleTransitions()).isEmpty();
		assertThat(this.objUnderTest.getTask().isFinished()).isFalse();
		assertThat(this.objUnderTest.getTask().isRunning()).isFalse();
	}

	@Test
	@Order(21)
	void startTaskDauer60()
	{
		this.objUnderTest.startTask();

		assertThat(this.objUnderTest.getCurrentState()).isPresent();
		assertThat(this.objUnderTest.getPossibleTransitions()).isNotEmpty();
		assertThat(this.objUnderTest.getPossibleTransitions().stream().filter(t -> t.getName().equals("prüfen")).findAny()).isPresent();
		assertThat(this.objUnderTest.getTask().isFinished()).isFalse();
		assertThat(this.objUnderTest.getTask().isRunning()).isTrue();
	}

	@Test
	@Order(22)
	void fireTransistion()
	{
		final WorkflowTransition transition = this.objUnderTest.getPossibleTransitions().stream().filter(t -> t.getName().equals("prüfen")).findFirst().get();
		this.objUnderTest.fireTransition(transition);
		assertThat(this.objUnderTest.getCurrentState()).isPresent();
		assertThat(this.objUnderTest.getCurrentState().get().getName()).isEqualTo("In Prüfung");
		assertThat(this.objUnderTest.getPossibleTransitions()).isNotEmpty();

	}

}
