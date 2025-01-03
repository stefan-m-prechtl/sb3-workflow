package de.esempe.workflow.boundary.rest.json;

import java.util.Collection;
import java.util.UUID;

import de.esempe.workflow.domain.Workflow;
import de.esempe.workflow.domain.WorkflowTransition;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.json.bind.adapter.JsonbAdapter;

public class WorkflowJsonAdapter implements JsonbAdapter<Workflow, JsonObject>
{

	private static String FIELD_ID = "id";
	private static String FIELD_NAME = "name";
	private static String FIELD_TRANSITIONS = "transitions";

	@Override
	public JsonObject adaptToJson(final Workflow workflow) throws Exception
	{
		final var result = Json.createObjectBuilder() //
				.add(FIELD_ID, workflow.getObjId().toString()) //
				.add(FIELD_NAME, workflow.getName())//
				.add(FIELD_TRANSITIONS, this.adaptTransitionsToJson(workflow.getTransitions()))//
				.build();

		return result;
	}

	private JsonArrayBuilder adaptTransitionsToJson(final Collection<WorkflowTransition> transitions) throws Exception
	{
		final JsonArrayBuilder result = Json.createArrayBuilder();
		final WorkflowTransitionJsonAdapter adapter = new WorkflowTransitionJsonAdapter();

		for (final WorkflowTransition transition : transitions)
		{
			result.add(adapter.adaptToJson(transition));
		}

		return result;
	}

	@Override
	public Workflow adaptFromJson(final JsonObject jsonObj) throws Exception
	{
		Workflow result = null;
		final WorkflowTransitionJsonAdapter adapter = new WorkflowTransitionJsonAdapter();
		final String name = jsonObj.getString(FIELD_NAME);
		final JsonArray jsonTransitionsValue = jsonObj.getJsonArray(FIELD_TRANSITIONS);

		if (jsonObj.containsKey(FIELD_ID))
		{
			final UUID objid = UUID.fromString(jsonObj.getString(FIELD_ID));
			result = Workflow.create(objid, name);
		}
		else
		{
			result = Workflow.create(name);
		}

		for (final JsonValue jsonValue : jsonTransitionsValue)
		{
			final WorkflowTransition transition = adapter.adaptFromJson((JsonObject) jsonValue);
			result.addTransition(transition);
		}

		return result;
	}

}
