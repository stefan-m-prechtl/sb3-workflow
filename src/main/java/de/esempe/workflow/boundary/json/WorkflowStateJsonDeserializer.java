package de.esempe.workflow.boundary.json;

import static de.esempe.workflow.boundary.json.JsonFieldsWorkflowState.FIELD_ID;
import static de.esempe.workflow.boundary.json.JsonFieldsWorkflowState.FIELD_NAME;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import de.esempe.workflow.domain.WorkflowState;

public class WorkflowStateJsonDeserializer extends JsonDeserializer<WorkflowState>
{
	@Override
	public WorkflowState deserialize(final JsonParser parser, final DeserializationContext ctxt) throws IOException, JacksonException
	{
		// Json-String in Json-Node umwandeln
		final JsonNode node = parser.getCodec().readTree(parser);
		// Musswerte
		final UUID objId = UUID.fromString(node.get(FIELD_ID).asText());
		final String name = node.get(FIELD_NAME).asText();
		// Java-Objekt erzeugen
		final WorkflowState result = WorkflowState.create(objId, name);
		return result;
	}
}
