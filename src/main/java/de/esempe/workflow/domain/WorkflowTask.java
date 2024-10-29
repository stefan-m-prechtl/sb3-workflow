package de.esempe.workflow.domain;

import java.util.Optional;
import java.util.UUID;

import org.bson.json.JsonObject;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "workflows")
public class WorkflowTask extends MongoDbObject
{
	private String name;
	private UUID workflowObjId;
	private UUID currentStateObjId;

	@Transient
	private Optional<JsonObject> jsondata;
	private String data;

	private WorkflowTask()
	{
		this.name = "";
		this.data = "";
		this.jsondata = Optional.empty();
	}

	public static WorkflowTask create(final UUID workflowObjId, final String name)
	{
		final var result = new WorkflowTask();
		result.workflowObjId = workflowObjId;
		result.name = name;

		return result;
	}

	public UUID getWorkflowObjId()
	{
		return this.workflowObjId;
	}

	public Optional<UUID> getCurrentStateObjId()
	{
		return Optional.ofNullable(this.currentStateObjId);
	}

	public void setCurrentStateObjId(final UUID currentStateObjId)
	{
		this.currentStateObjId = currentStateObjId;
	}

	public void setCurrentState(final WorkflowState startState)
	{
		this.currentStateObjId = startState.getObjId();

	}

}
