package de.esempe.workflow.boundary.rest.json;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit-test")
public class WorkflowJsonAdapterTest
{
//	@Autowired
//	private JacksonTester<Workflow> jacksonTester;
//
//	private String json = "";
//
//	@Test
//	@Order(1)
//	void convertToJson() throws IOException
//	{
//		final Workflow workflow = Workflow.create("testworkflow");
//
//		final JsonContent<Workflow> resultWrite = this.jacksonTester.write(workflow);
//		final String jsonUser = resultWrite.getJson();
//
//		assertThat(jsonUser).contains("name");
//
//		this.json = jsonUser;
//
//	}
//
//	@Test()
//	@Order(2)
//	void convertFromJson() throws IOException
//	{
//		final Workflow workflow = this.jacksonTester.parseObject(this.json);
//		assertThat(workflow.getName()).isEqualTo("testworkflow");
//	}
//
}
