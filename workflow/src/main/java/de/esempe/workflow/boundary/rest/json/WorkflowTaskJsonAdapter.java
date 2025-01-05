package de.esempe.workflow.boundary.rest.json;

import java.util.UUID;

import de.esempe.workflow.domain.WorkflowTask;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.bind.adapter.JsonbAdapter;

public class WorkflowTaskJsonAdapter implements JsonbAdapter<WorkflowTask, JsonObject>
{
	private static String FIELD_ID = "id";
	private static String FIELD_NAME = "name";
	private static String FIELD_DATA = "substance";
	private static String FIELD_WORKFLOW_ID = "workflowId";
	private static String FIELD_STATE_ID = "stateId";
	private static String FIELD_CREATOR_ID = "creatorId";
	private static String FIELD_DELEGATOR_ID = "delegatorId";
	private static String FIELD_RUNNING = "running";
	private static String FIELD_FINISHED = "finished";

	@Override
	public JsonObject adaptToJson(final WorkflowTask task) throws Exception
	{
		final var result = Json.createObjectBuilder() //
				.add(FIELD_ID, task.getObjId().toString()) //
				.add(FIELD_NAME, task.getName())//
				.add(FIELD_DATA, task.getData())//
				.add(FIELD_WORKFLOW_ID, task.getWorkflowObjId().toString())//
				.add(FIELD_STATE_ID, task.getCurrentStateObjId().toString())//
				.add(FIELD_CREATOR_ID, task.getUserIdCreator())//
				.add(FIELD_DELEGATOR_ID, task.getUserIdDelegator())//
				.add(FIELD_RUNNING, task.isRunning())//
				.add(FIELD_FINISHED, task.isFinished())//
				.build();

		return result;
	}

	@Override
	public WorkflowTask adaptFromJson(final JsonObject jsonObj) throws Exception
	{
		WorkflowTask result = null;

		final String name = jsonObj.getString(FIELD_NAME);
		if (jsonObj.containsKey(FIELD_ID))
		{
			final UUID objId = UUID.fromString(jsonObj.getString(FIELD_ID));
			result = WorkflowTask.create(objId, name);
		}
		else
		{
			result = WorkflowTask.create(name);
		}

		final String data = jsonObj.getString(FIELD_DATA);
		result.setData(data);

		final UUID workflowObjId = UUID.fromString(jsonObj.getString(FIELD_WORKFLOW_ID));
		result.setWorkflowObjId(workflowObjId);

		if (jsonObj.containsKey(FIELD_STATE_ID))
		{
			final UUID stateObjId = UUID.fromString(jsonObj.getString(FIELD_STATE_ID));
			result.setCurrentStateObjId(stateObjId);
		}

		if (jsonObj.containsKey(FIELD_CREATOR_ID))
		{
			final Long creatorId = jsonObj.getJsonNumber(FIELD_CREATOR_ID).longValue();
			result.setUserIdCreator(creatorId);
		}

		if (jsonObj.containsKey(FIELD_DELEGATOR_ID))
		{
			final Long delegatorId = jsonObj.getJsonNumber(FIELD_DELEGATOR_ID).longValue();
			result.setUserIdDelegator(delegatorId);
		}

		final Boolean finished = jsonObj.getBoolean(FIELD_FINISHED);
		result.setFinished(finished);

		return result;
	}

}
