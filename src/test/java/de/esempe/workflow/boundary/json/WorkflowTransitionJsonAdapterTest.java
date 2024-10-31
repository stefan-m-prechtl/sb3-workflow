package de.esempe.workflow.boundary.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.context.annotation.Import;

import de.esempe.workflow.domain.WorkflowState;
import de.esempe.workflow.domain.WorkflowTransition;

@JsonTest
@Import(ConfigJsonSerialization.class)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit-test")
public class WorkflowTransitionJsonAdapterTest
{
	@Autowired
	private JacksonTester<WorkflowTransition> jacksonTester;

	private String json = "";

	@Test
	@Order(1)
	void convertToJson() throws IOException
	{
		final WorkflowState fromState = WorkflowState.create("In Prüfung");
		final WorkflowState toState = WorkflowState.create("Abgelehnt");
		final WorkflowTransition transition = WorkflowTransition.create("ablehnen", toState, fromState);

		final JsonContent<WorkflowTransition> resultWrite = this.jacksonTester.write(transition);
		final String jsonWorkflowTransition = resultWrite.getJson();

		assertThat(jsonWorkflowTransition).contains("Abgelehnt");
		this.json = jsonWorkflowTransition;
		System.out.println(this.json);

	}

	@Test()
	@Order(2)
	void convertFromJson() throws IOException
	{
		final WorkflowTransition transition = this.jacksonTester.parseObject(this.json);
		assertThat(transition.getName()).isEqualTo("ablehnen");
	}

}
