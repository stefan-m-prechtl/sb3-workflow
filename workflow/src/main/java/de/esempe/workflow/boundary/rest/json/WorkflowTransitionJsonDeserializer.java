package de.esempe.workflow.boundary.rest.json;

import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflowTransition.FIELD_FROM_STATE;
import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflowTransition.FIELD_ID;
import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflowTransition.FIELD_NAME;
import static de.esempe.workflow.boundary.rest.json.JsonFieldsWorkflowTransition.FIELD_TO_STATE;

import java.io.IOException;
import java.util.UUID;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import de.esempe.workflow.domain.WorkflowState;
import de.esempe.workflow.domain.WorkflowTransition;

@JsonComponent
public class WorkflowTransitionJsonDeserializer extends JsonDeserializer<WorkflowTransition>
{
	private WorkflowStateJsonDeserializer stateDeserializer;
	// private WorkflowRuleJsonDeserializer ruleDeserializer;

	WorkflowTransitionJsonDeserializer()
	{
		this.stateDeserializer = new WorkflowStateJsonDeserializer();
	}

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