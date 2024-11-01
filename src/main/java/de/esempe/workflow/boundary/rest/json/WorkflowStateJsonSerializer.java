package de.esempe.workflow.boundary.rest.json;

import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflowState.FIELD_ID;
import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflowState.FIELD_NAME;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import de.esempe.workflow.domain.WorkflowState;

public class WorkflowStateJsonSerializer extends JsonSerializer<WorkflowState>
{
	@Override
	public void serialize(final WorkflowState state, final JsonGenerator generator, final SerializerProvider serializers) throws IOException
	{
		generator.writeStartObject();
		generator.writeStringField(FIELD_ID, state.getObjId().toString());
		generator.writeStringField(FIELD_NAME, state.getName());
		generator.writeEndObject();
	}
}
