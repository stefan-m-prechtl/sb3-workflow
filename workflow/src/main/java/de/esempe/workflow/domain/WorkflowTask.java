package de.esempe.workflow.domain;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.google.common.base.MoreObjects;

@Document(collection = "tasks")
public class WorkflowTask extends MongoDbObject
{
	public final static String FIELD_NAME_JSONDATA = "jsondata";

	private UUID workflowObjId;
	private UUID currentStateObjId;

	private long userIdCreator;
	private long userIdDelegator;

	private boolean running;
	private boolean finished;

	@Field(name = FIELD_NAME_JSONDATA)
	private String jsonDataString;

	private WorkflowTask()
	{
		this.name = "";
		this.jsonDataString = "";
		this.running = false;
		this.finished = false;
		this.userIdCreator = -1L;
		this.userIdDelegator = -1L;
	}

	public static WorkflowTask create(final UUID workflowObjId, final String name)
	{
		final var result = new WorkflowTask();
		result.workflowObjId = workflowObjId;
		result.name = name;

		return result;
	}

	public void setData(final String data)
	{
		this.jsonDataString = data;
	}

	public String getData()
	{
		return this.jsonDataString;
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

	public long getUserIdCreator()
	{
		return this.userIdCreator;
	}

	public void setUserIdCreator(long userIdCreator)
	{
		this.userIdCreator = userIdCreator;
	}

	public long getUserIdDelegator()
	{
		return this.userIdDelegator;
	}

	public void setUserIdDelegator(long userIdDelegator)
	{
		this.userIdDelegator = userIdDelegator;
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
