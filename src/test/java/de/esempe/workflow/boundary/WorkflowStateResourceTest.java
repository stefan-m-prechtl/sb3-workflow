package de.esempe.workflow.boundary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.esempe.workflow.domain.WorkflowState;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Integrationstext WorkflowStateResource")
@Tag("integration-test")
public class WorkflowStateResourceTest
{
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper; // Jackson ObjectMapper
	@Autowired
	private MongoTemplate mongoTemplate;

	@BeforeAll
	void setup()
	{
		// Collection für Workflowstates löschen
		this.mongoTemplate.dropCollection(WorkflowState.class);
	}

	@Test
	@Order(1)
	@WithMockUser
	void getAll() throws Exception
	{
		final ResultActions resultActions = this.mockMvc//
				.perform(get("/state").accept(MediaType.APPLICATION_JSON)) //
				.andExpectAll( //
						status().isOk(), //
						content().contentType(MediaType.APPLICATION_JSON) //
				);

		// Ergebnis aus Response ermitteln
		final MockHttpServletResponse response = resultActions.andReturn().getResponse();
		final String responseBody = response.getContentAsString();
		final List<WorkflowState> states = this.convertBody(responseBody);

		// Ergebnis prüfen
		assertThat(states).isNotNull();
		assertThat(states).isEmpty();

	}

	@Test
	@Order(2)
	@WithMockUser
	void create() throws Exception
	{
		final WorkflowState state = WorkflowState.create("Befreiung");
		final String stateJson = this.objectMapper.writeValueAsString(state);

		final ResultActions resultActions = this.mockMvc//
				.perform(post("/state").contentType(MediaType.APPLICATION_JSON).content(stateJson)) //
				.andExpectAll( //
						status().isCreated(), //
						content().contentType(MediaType.APPLICATION_JSON) //
				);

		// Ergebnis aus Response ermitteln
		final MockHttpServletResponse response = resultActions.andReturn().getResponse();
		System.out.println("Post");
	}

	@Test
	@Order(3)
	@WithMockUser
	void createAgain() throws Exception
	{
		final WorkflowState state = WorkflowState.create("Befreiung");
		final String stateJson = this.objectMapper.writeValueAsString(state);

		final ResultActions resultActions = this.mockMvc//
				.perform(post("/state").contentType(MediaType.APPLICATION_JSON).content(stateJson)) //
				.andExpectAll( //
						status().isConflict(), //
						content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN) //
				);

		// Ergebnis aus Response ermitteln
		final MockHttpServletResponse response = resultActions.andReturn().getResponse();
		System.out.println("Post");
	}

	private List<WorkflowState> convertBody(final String responseBody) throws JsonProcessingException, JsonMappingException
	{
		return this.objectMapper.readValue(responseBody, this.objectMapper.getTypeFactory().constructCollectionType(List.class, WorkflowState.class));
	}

}
