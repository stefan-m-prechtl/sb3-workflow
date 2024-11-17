package de.esempe.workflow.boundary.rest.json;

import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflow.FIELD_ID;
import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflow.FIELD_NAME;
import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflow.FIELD_TRANSITIONS;

import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import de.esempe.workflow.domain.Workflow;
import de.esempe.workflow.domain.WorkflowTransition;

@JsonComponent
public class WorkflowJsonDeserializer extends JsonDeserializer<Workflow>
{
	private WorkflowTransitionJsonDeserializer transitionDeserializer;

	WorkflowJsonDeserializer()
	{
		this.transitionDeserializer = new WorkflowTransitionJsonDeserializer();
	}

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

		final JsonNode nodeTransitions = node.get(FIELD_TRANSITIONS);
		if ((nodeTransitions != null) && nodeTransitions.isArray())
		{
			final Iterator<JsonNode> iterator = nodeTransitions.elements();

			while (iterator.hasNext())
			{
				final JsonNode transitionNode = iterator.next();
				final WorkflowTransition transistion = this.transitionDeserializer.deserialize(transitionNode.traverse(parser.getCodec()), ctxt);
				workflow.addTransition(transistion);
			}
		}
		return workflow;
	}
}
