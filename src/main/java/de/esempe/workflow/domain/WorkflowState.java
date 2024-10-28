package de.esempe.workflow.domain;

import java.util.Optional;
import java.util.UUID;

import org.bson.json.JsonObject;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.MoreObjects;

@Document(collection = "states")
public class WorkflowState extends MongoDbObject
{
	private String name;
	private String data;
	@Transient
	private Optional<JsonObject> jsondata;

	public static WorkflowState create(final String name)
	{
		final var state = new WorkflowState(name);
		return state;
	}

	WorkflowState()
	{
		this.name = "";
		this.data = "";
		this.jsondata = Optional.empty();
	}

	private WorkflowState(final UUID objId, final String name)
	{
		super(objId);
		this.name = name;
		this.data = "";
		this.jsondata = Optional.empty();
	}

	private WorkflowState(final String name)
	{
		this();
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getData()
	{
		return this.data;
	}

	public void setData(final String data)
	{
		this.data = data;
	}

	public Optional<JsonObject> getJsonData()
	{
		return this.jsondata;
	}

	public void setJsondData(final JsonObject data)
	{
		this.jsondata = Optional.ofNullable(data);
	}

	@Override
	public String toString()
	{
		final var result = MoreObjects.toStringHelper(this) //
				.add("id", this.getDbId().toString()) //
				.add("name", this.name) //
				.toString();
		return result;
	}

}
