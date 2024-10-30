package de.esempe.workflow.domain;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@Document(collection = "states")
public class WorkflowState extends MongoDbObject
{
	@Indexed(unique = true)
	private String name;
	private String scriptEnter;
	private String scriptLeave;

	private WorkflowState()
	{
		this.name = "";
		this.scriptEnter = "";
		this.scriptLeave = "";
	}

	private WorkflowState(final UUID objId, final String name)
	{
		super(objId);
		this.name = name;
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

	public String getScriptEnter()
	{
		return this.scriptEnter;
	}

	public void setScriptEnter(final String scriptEnter)
	{
		this.scriptEnter = scriptEnter;
	}

	public String getScriptLeave()
	{
		return this.scriptLeave;
	}

	public void setScriptLeave(final String scriptLeave)
	{
		this.scriptLeave = scriptLeave;
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
