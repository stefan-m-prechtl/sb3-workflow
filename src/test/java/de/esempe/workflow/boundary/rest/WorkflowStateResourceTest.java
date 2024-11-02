package de.esempe.workflow.boundary.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

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

	private String objId = UUID.randomUUID().toString();
	private WorkflowState state;

	@BeforeAll
	void setup()
	{
		// Collection für Workflowstates löschen
		this.mongoTemplate.dropCollection(WorkflowState.class);
	}

	@Test
	@Order(1)
	@WithMockUser
	@DisplayName("GET all - empty database")
	void getAllEmptyDatabase() throws Exception
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
		final List<WorkflowState> states = this.convertListBody(responseBody);

		// Ergebnis prüfen
		assertThat(states).isNotNull();
		assertThat(states).isEmpty();

	}

	@Test
	@Order(1)
	@WithMockUser
	@DisplayName("GET one by ObjId - empty database")
	void getOneByObjEmptyDatabase() throws Exception
	{
		final String url = "/state/" + this.objId;

		this.mockMvc //
				.perform(get(url).accept(MediaType.APPLICATION_JSON)) //
				.andExpectAll( //
						status().isNotFound());
	}

	@Test
	@Order(2)
	@WithMockUser
	@DisplayName("POST one - empty database")
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
		final String locationValue = (String) response.getHeaderValue("Location");
		assertThat(locationValue).startsWith("http://localhost/state");

		final String[] parts = locationValue.split("/");
		this.objId = parts[parts.length - 1];
	}

	@Test
	@Order(3)
	@WithMockUser
	@DisplayName("POST same again - not empty database")
	void createAgain() throws Exception
	{
		final WorkflowState state = WorkflowState.create("Befreiung");
		final String stateJson = this.objectMapper.writeValueAsString(state);

		this.mockMvc//
				.perform(post("/state").contentType(MediaType.APPLICATION_JSON).content(stateJson)) //
				.andExpectAll( //
						status().isConflict(), //
						content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN) //
				);
	}

	@Test
	@Order(4)
	@WithMockUser
	@DisplayName("GET all - not empty database")
	void getAllNotEmptyDatabase() throws Exception
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
		final List<WorkflowState> states = this.convertListBody(responseBody);

		// Ergebnis prüfen
		assertThat(states).isNotNull();
		assertThat(states).isNotEmpty();
	}

	@Test
	@Order(5)
	@WithMockUser
	@DisplayName("GET one by ObjId - not empty database")
	void getOneByObjNotEmptyDatabase() throws Exception
	{
		final String url = "/state/" + this.objId;

		final ResultActions resultActions = this.mockMvc//
				.perform(get(url).accept(MediaType.APPLICATION_JSON)) //
				.andExpectAll( //
						status().isOk(), //
						content().contentType(MediaType.APPLICATION_JSON) //
				);

		// Ergebnis aus Response ermitteln
		final MockHttpServletResponse response = resultActions.andReturn().getResponse();
		final String responseBody = response.getContentAsString();
		this.state = this.convertObjectBody(responseBody);

		// Ergebnis prüfen
		assertThat(this.state).isNotNull();
		assertThat(this.state.getName()).isEqualTo("Befreiung");
	}

	@Test
	@Order(6)
	@WithMockUser
	@DisplayName("PUT one by ObjId - not empty database")
	void updateOneByObjNotEmptyDatabase() throws Exception
	{
		final String url = "/state/" + this.objId;
		this.state.setName(this.state.getName().toUpperCase());

		// Convert the Customer object to JSON
		final String jsonContent = this.objectMapper.writeValueAsString(this.state);

		// Perform the PUT request
		final ResultActions resultActions = this.mockMvc //
				.perform(put(url).contentType(MediaType.APPLICATION_JSON) //
						.content(jsonContent) //
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		// Ergebnis aus Response ermitteln
		final MockHttpServletResponse response = resultActions.andReturn().getResponse();
		final String responseBody = response.getContentAsString();
		final WorkflowState state = this.convertObjectBody(responseBody);

		// Ergebnis prüfen
		assertThat(state).isNotNull();
		assertThat(state.getName()).isEqualTo("Befreiung".toUpperCase());

	}

	@Test
	@Order(7)
	@WithMockUser
	@DisplayName("DELETE one by ObjId - not empty database")
	void deleteOneByObjNotEmptyDatabase() throws Exception
	{
		final String url = "/state/" + this.objId;

		this.mockMvc//
				.perform(delete(url)) //
				.andExpectAll( //
						status().isNoContent() //
				);
	}

	private List<WorkflowState> convertListBody(final String responseBody) throws JsonProcessingException, JsonMappingException
	{
		return this.objectMapper.readValue(responseBody, this.objectMapper.getTypeFactory().constructCollectionType(List.class, WorkflowState.class));
	}

	private WorkflowState convertObjectBody(final String responseBody) throws JsonProcessingException, JsonMappingException
	{
		return this.objectMapper.readValue(responseBody, this.objectMapper.getTypeFactory().constructType(WorkflowState.class));
	}

}
