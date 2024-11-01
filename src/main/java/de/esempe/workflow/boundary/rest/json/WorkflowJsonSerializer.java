package de.esempe.workflow.boundary.rest.json;

import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflow.FIELD_ID;
import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflow.FIELD_NAME;
import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflow.FIELD_TRANSITIONS;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import de.esempe.workflow.domain.Workflow;
import de.esempe.workflow.domain.WorkflowTransition;

@JsonComponent
public class WorkflowJsonSerializer extends JsonSerializer<Workflow>
{
	private ConfigJsonSerialization serializationConfig;
	private WorkflowTransitionJsonSerializer serializerTransition;

	public WorkflowJsonSerializer(final ConfigJsonSerialization serializationConfig)
	{
		this.serializationConfig = serializationConfig;
		this.serializerTransition = new WorkflowTransitionJsonSerializer(serializationConfig);
	}

	@Override
	public void serialize(final Workflow workflow, final JsonGenerator generator, final SerializerProvider serializers) throws IOException
	{
		generator.writeStartObject();
		generator.writeStringField(FIELD_ID, workflow.getObjId().toString());
		generator.writeStringField(FIELD_NAME, workflow.getName());

		generator.writeFieldName(FIELD_TRANSITIONS);
		generator.writeStartArray();
		for (final WorkflowTransition transition : workflow.getTransitions())
		{
			if (this.serializationConfig.withFullObjReference())
			{
				this.serializerTransition.serialize(transition, generator, serializers);
			}
			else
			{
				generator.writeStartObject();
				generator.writeStringField(FIELD_ID, transition.getObjId().toString());
				generator.writeEndObject();
			}
		}
		generator.writeEndArray();
		generator.writeEndObject();
	}
}
