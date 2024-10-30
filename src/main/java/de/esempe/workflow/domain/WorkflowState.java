package de.esempe.workflow.domain;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.bson.json.JsonObject;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@Document(collection = "states")
public class WorkflowState extends MongoDbObject
{
	private String name;
	private String data;
	@Transient
	private Optional<JsonObject> jsondata;

	private WorkflowState()
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

	public static WorkflowState create(final String name)
	{
		final var result = new WorkflowState();
		result.name = name;
		return result;
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
	public boolean equals(final Object obj)
	{
		if (obj instanceof WorkflowState)
		{
			final var other = (WorkflowState) obj;
			return Objects.equal(this.name, other.name);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		final var values = Arrays.asList(this.name);
		return Objects.hashCode(values);
	}

	@Override
	public String toString()
	{
		final var result = MoreObjects.toStringHelper(this) //
				// .add("objid", this.getObjId().toString()) //
				.add("name", this.name) //
				.toString();
		return result;
	}

}
