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

import de.esempe.workflow.domain.WorkflowState;

@JsonTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit-test")
public class WorkflowStateJsonAdapterTest
{
	@Autowired
	private JacksonTester<WorkflowState> jacksonTester;

	private String json = "";

	@Test
	@Order(1)
	void convertToJson() throws IOException
	{
		final WorkflowState state = WorkflowState.create("Ablehnen");

		final JsonContent<WorkflowState> resultWrite = this.jacksonTester.write(state);
		final String jsonWorkflowState = resultWrite.getJson();

		assertThat(jsonWorkflowState).contains("Ablehnen");

		this.json = jsonWorkflowState;

	}

	@Test()
	@Order(2)
	void convertFromJson() throws IOException
	{
		final WorkflowState state = this.jacksonTester.parseObject(this.json);
		assertThat(state.getName()).isEqualTo("Ablehnen");
	}

}
