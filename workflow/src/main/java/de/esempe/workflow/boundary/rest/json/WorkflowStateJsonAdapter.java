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
	private static String FIELD_SCRIPT_ENTER = "scriptEnter";
	private static String FIELD_SCRIPT_LEAVE = "scriptLeave";

	@Override
	public JsonObject adaptToJson(WorkflowState state) throws Exception
	{
		final var result = Json.createObjectBuilder() //
				.add(FIELD_ID, state.getObjId().toString()) //
				.add(FIELD_NAME, state.getName())//
				.add(FIELD_SCRIPT_ENTER, state.getScriptEnter())//
				.add(FIELD_SCRIPT_LEAVE, state.getScriptLeave())//
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

		final String scriptEnter = jsonObj.getString(FIELD_SCRIPT_ENTER);
		final String scriptLeave = jsonObj.getString(FIELD_SCRIPT_LEAVE);
		result.setScriptEnter(scriptEnter);
		result.setScriptLeave(scriptLeave);

		return result;
	}

}
