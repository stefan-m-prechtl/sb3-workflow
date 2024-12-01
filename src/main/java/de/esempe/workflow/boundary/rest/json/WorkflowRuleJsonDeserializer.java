package de.esempe.workflow.boundary.rest.json;

import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflowRule.FIELD_NAME;
import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflowRule.FIELD_SCRIPT;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import de.esempe.workflow.domain.WorkflowRule;

@JsonComponent
public class WorkflowRuleJsonDeserializer extends JsonDeserializer<WorkflowRule>
{
	@Override
	public WorkflowRule deserialize(final JsonParser parser, final DeserializationContext ctxt) throws IOException, JacksonException
	{
		// Json-String in Json-Node umwandeln
		final JsonNode node = parser.getCodec().readTree(parser);
		// Musswerte
		final String name = node.get(FIELD_NAME).asText();
		final String script = node.get(FIELD_SCRIPT).asText();
		// Java-Objekt erzeugen
		final WorkflowRule result = WorkflowRule.create(name, script);
		return result;
	}
}
