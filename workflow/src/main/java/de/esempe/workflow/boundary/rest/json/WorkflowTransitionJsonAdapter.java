package de.esempe.workflow.boundary.rest.json;

import java.util.UUID;

import de.esempe.workflow.domain.WorkflowState;
import de.esempe.workflow.domain.WorkflowTransition;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.bind.adapter.JsonbAdapter;

public class WorkflowTransitionJsonAdapter implements JsonbAdapter<WorkflowTransition, JsonObject>
{
	private static String FIELD_ID = "id";
	private static String FIELD_NAME = "name";
	private static String FIELD_FROM_STATE = "fromState";
	private static String FIELD_TO_STATE = "toState";

	@Override
	public JsonObject adaptToJson(WorkflowTransition transition) throws Exception
	{
		final WorkflowStateJsonAdapter stateAdpater = new WorkflowStateJsonAdapter();

		final var result = Json.createObjectBuilder() //
				.add(FIELD_ID, transition.getObjId().toString()) //
				.add(FIELD_NAME, transition.getName())//
				.add(FIELD_FROM_STATE, stateAdpater.adaptToJson(transition.getFromState()))//
				.add(FIELD_TO_STATE, stateAdpater.adaptToJson(transition.getToState()))//
				.build();

		return result;
	}

	@Override
	public WorkflowTransition adaptFromJson(JsonObject jsonObj) throws Exception
	{
		WorkflowTransition result = null;
		final WorkflowStateJsonAdapter stateAdpater = new WorkflowStateJsonAdapter();

		final String name = jsonObj.getString(FIELD_NAME);

		final WorkflowState fromState = stateAdpater.adaptFromJson((JsonObject) jsonObj.get(FIELD_FROM_STATE));
		final WorkflowState toState = stateAdpater.adaptFromJson((JsonObject) jsonObj.get(FIELD_TO_STATE));

		if (jsonObj.containsKey(FIELD_ID))
		{
			final UUID objId = UUID.fromString(jsonObj.getString(FIELD_ID));
			result = WorkflowTransition.create(objId, name, fromState, toState);
		}
		else
		{
			result = WorkflowTransition.create(name, fromState, toState);
		}

		return result;
	}

}
