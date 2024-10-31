package de.esempe.workflow.boundary.json;

import java.io.IOException;
import java.util.UUID;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import de.esempe.workflow.boundary.json.WorkflowStateJsonAdapter.WorkflowStateJsonSerializer;
import de.esempe.workflow.boundary.json.WorkflowStateJsonAdapter.WorkflowStateJsonSerializer.WorkflowStateJsonDeserializer;
import de.esempe.workflow.domain.WorkflowState;
import de.esempe.workflow.domain.WorkflowTransition;

@JsonComponent
public class WorkflowTransitionJsonAdapter
{
	private static String FIELD_ID = "id";
	private static String FIELD_NAME = "name";
	private static String FIELD_FROM_STATE = "fromstate";
	private static String FIELD_TO_STATE = "tostate";

	public static class WorkflowTransitionJsonSerializer extends JsonSerializer<WorkflowTransition>
	{
		WorkflowStateJsonSerializer stateSerializer = new WorkflowStateJsonSerializer();

		@Override
		public void serialize(final WorkflowTransition transition, final JsonGenerator generator, final SerializerProvider serializers) throws IOException
		{
			generator.writeStartObject();
			generator.writeStringField(FIELD_ID, transition.getObjId().toString());
			generator.writeStringField(FIELD_NAME, transition.getName());

			generator.writeFieldName("fromstate");
			this.stateSerializer.serialize(transition.getFromState(), generator, serializers);

			generator.writeFieldName("tostate");
			this.stateSerializer.serialize(transition.getToState(), generator, serializers);

			generator.writeEndObject();
		}

		public static class WorkflowTransitionJsonDeserializer extends JsonDeserializer<WorkflowTransition>
		{
			WorkflowStateJsonDeserializer stateDeserializer = new WorkflowStateJsonDeserializer();

			@Override
			public WorkflowTransition deserialize(final JsonParser parser, final DeserializationContext ctxt) throws IOException, JacksonException
			{
				// Json-String in Json-Node umwandeln
				final JsonNode node = parser.getCodec().readTree(parser);
				// Musswerte
				final UUID objId = UUID.fromString(node.get(FIELD_ID).asText());
				final String name = node.get(FIELD_NAME).asText();
				final JsonNode nodeFromState = node.get(FIELD_FROM_STATE);
				final WorkflowState fromState = this.stateDeserializer.deserialize(nodeFromState.traverse(parser.getCodec()), ctxt);
				final JsonNode nodeToState = node.get(FIELD_TO_STATE);
				final WorkflowState toState = this.stateDeserializer.deserialize(nodeToState.traverse(parser.getCodec()), ctxt);

				// Java-Objekt erzeugen
				final WorkflowTransition result = WorkflowTransition.create(objId, name, fromState, toState);
				return result;
			}
		}
	}

}
