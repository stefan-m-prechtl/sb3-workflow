package de.esempe.workflow.domain;

import java.util.Optional;
import java.util.UUID;

import org.bson.json.JsonObject;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.MoreObjects;

@Document(collection = "workflows")
public class WorkflowTask extends MongoDbObject
{
	private String name;
	private UUID workflowObjId;
	private UUID currentStateObjId;

	private boolean running;
	private boolean finished;

	@Transient
	private Optional<JsonObject> jsondata;
	private String data;

	private WorkflowTask()
	{
		this.name = "";
		this.data = "";
		this.jsondata = Optional.empty();
		this.running = false;
		this.finished = false;
	}

	public static WorkflowTask create(final UUID workflowObjId, final String name)
	{
		final var result = new WorkflowTask();
		result.workflowObjId = workflowObjId;
		result.name = name;

		return result;
	}

	public void setData(final JsonObject data)
	{
		this.jsondata = Optional.ofNullable(data);
	}

	public JsonObject getData()
	{
		return this.jsondata.get();
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
		this.running = true;
	}

	public void setCurrentState(final WorkflowState startState)
	{
		this.currentStateObjId = startState.getObjId();
		this.running = true;
	}

	public boolean isRunning()
	{
		return this.running;
	}

	public boolean isFinished()
	{
		return this.finished;
	}

	public void setFinished(final boolean finished)
	{
		this.running = false;
		this.finished = finished;
	}

	@Override
	public String toString()
	{
		final var result = MoreObjects.toStringHelper(this) //
				.add("objid", this.getObjId().toString()) //
				.add("name", this.name) //
				.toString();
		return result;
	}

}
