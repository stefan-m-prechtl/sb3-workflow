package de.esempe.workflow.domain;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.MoreObjects;

@Document(collection = "rules")
public class WorkflowRule extends MongoDbObject
{
	private String script;

	private WorkflowRule()
	{
	}

	public static WorkflowRule create(final UUID objId, final String name, final String script)
	{
		final var result = new WorkflowRule();
		result.objId = objId;
		result.name = name;
		result.script = script;
		return result;
	}

	public static WorkflowRule create(final String name, final String script)
	{
		final var result = new WorkflowRule();
		result.name = name;
		result.script = script;
		return result;
	}

	public String getScript()
	{
		return this.script;
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
