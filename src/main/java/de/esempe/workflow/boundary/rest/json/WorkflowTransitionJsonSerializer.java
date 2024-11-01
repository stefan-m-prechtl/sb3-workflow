package de.esempe.workflow.boundary.rest.json;

import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflowTransition.FIELD_FROM_STATE;
import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflowTransition.FIELD_ID;
import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflowTransition.FIELD_NAME;
import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflowTransition.FIELD_TO_STATE;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import de.esempe.workflow.domain.WorkflowTransition;

@JsonComponent
public class WorkflowTransitionJsonSerializer extends JsonSerializer<WorkflowTransition>
{
	private WorkflowStateJsonSerializer stateSerializer = new WorkflowStateJsonSerializer();
	private ConfigJsonSerialization serializationConfig;

	public WorkflowTransitionJsonSerializer(final ConfigJsonSerialization serializationConfig)
	{
		this.serializationConfig = serializationConfig;
	}

	@Override
	public void serialize(final WorkflowTransition transition, final JsonGenerator generator, final SerializerProvider serializers) throws IOException
	{
		generator.writeStartObject();
		generator.writeStringField(FIELD_ID, transition.getObjId().toString());
		generator.writeStringField(FIELD_NAME, transition.getName());

		generator.writeFieldName(FIELD_FROM_STATE);
		this.stateSerializer.serialize(transition.getFromState(), generator, serializers);

		generator.writeFieldName(FIELD_TO_STATE);
		this.stateSerializer.serialize(transition.getToState(), generator, serializers);

		generator.writeEndObject();
	}
}