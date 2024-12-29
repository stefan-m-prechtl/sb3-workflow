package de.esempe.workflow.boundary.rest.json;

import java.util.UUID;

import de.esempe.workflow.domain.WorkflowState;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.bind.adapter.JsonbAdapter;

public class WorkflowStateJsonAdapter implements JsonbAdapter<WorkflowState, JsonObject>
{
	private static String FIELD_ID = "id";
	private static String FIELD_NAME = "name";

	@Override
	public JsonObject adaptToJson(WorkflowState state) throws Exception
	{
		final var result = Json.createObjectBuilder() //
				.add(FIELD_ID, state.getObjId().toString()) //
				.add(FIELD_NAME, state.getName())//
				.build();

		return result;
	}

	@Override
	public WorkflowState adaptFromJson(JsonObject jsonObj) throws Exception
	{
		WorkflowState result = null;

		final String name = jsonObj.getString(FIELD_NAME);

		if (jsonObj.containsKey(FIELD_ID))
		{
			final UUID objId = UUID.fromString(jsonObj.getString(FIELD_ID));
			result = WorkflowState.create(objId, name);
		}
		else
		{
			result = WorkflowState.create(name);
		}

		return result;
	}

}
