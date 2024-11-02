package de.esempe.workflow.boundary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import de.esempe.workflow.boundary.rest.PingResource;

@WebMvcTest(controllers = { PingResource.class })
public class PingResourceTest
{
	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser
	void pingTest() throws Exception
	{
		this.mockMvc.perform(get("/ping")) //
				.andExpectAll( //
						status().isOk(), //
						content().contentType(MediaType.APPLICATION_JSON), //
						jsonPath("$.msg").value("Ping vom Server")//
				);
	}
}
