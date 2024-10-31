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

import de.esempe.workflow.domain.Workflow;

@JsonComponent
public class WorkflowJsonAdapter
{
	private static String FIELD_ID = "id";
	private static String FIELD_NAME = "name";

	public static class WorkflowJsonSerializer extends JsonSerializer<Workflow>
	{
		@Override
		public void serialize(final Workflow workflow, final JsonGenerator generator, final SerializerProvider serializers) throws IOException
		{
			generator.writeStartObject();
			generator.writeStringField(FIELD_ID, workflow.getObjId().toString());
			generator.writeStringField(FIELD_NAME, workflow.getName());
			generator.writeEndObject();
		}
	}

	public static class WorkflowJsonDeserializer extends JsonDeserializer<Workflow>
	{
		@Override
		public Workflow deserialize(final JsonParser parser, final DeserializationContext ctxt) throws IOException, JacksonException
		{
			// Json-String in Json-Node umwandeln
			final JsonNode node = parser.getCodec().readTree(parser);
			// Musswerte
			final UUID objId = UUID.fromString(node.get(FIELD_ID).asText());
			final String name = node.get(FIELD_NAME).asText();
			// Java-Objekt erzeugen
			final Workflow workflow = Workflow.create(objId, name);
			return workflow;
		}
	}

}
