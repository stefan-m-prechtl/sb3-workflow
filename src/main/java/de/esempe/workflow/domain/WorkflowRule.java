package de.esempe.workflow.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.MoreObjects;

@Document(collection = "rules")
public class WorkflowRule extends MongoDbObject
{
	private String name;
	private String script;

	WorkflowRule()
	{
	}

	public WorkflowRule(final String name, final String script)
	{
		this.name = name;
		this.script = script;
	}

	public String getScript()
	{
		return this.script;
	}

	public String getName()
	{
		return this.name;
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
