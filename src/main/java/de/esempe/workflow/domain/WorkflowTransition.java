package de.esempe.workflow.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.google.common.base.MoreObjects;

@Document(collection = "transitions")
public class WorkflowTransition extends MongoDbObject
{
	public enum TransistionType
	{
		USER, SYSTEM;
	}

	private String name;
	@DocumentReference
	private WorkflowState fromState;
	@DocumentReference
	private WorkflowState toState;

	// Eingegebette Enitität
	private WorkflowRule rule;

	private TransistionType type;

	private WorkflowTransition()
	{
		super();
		this.name = "";
		this.type = TransistionType.USER;
	}

	private WorkflowTransition(final String name, final WorkflowState fromState, final WorkflowState toState)
	{
		this();
		this.name = name;
		this.fromState = fromState;
		this.toState = toState;
	}

	public static WorkflowTransition create(final String name, final WorkflowState fromState, final WorkflowState toState)
	{
		final var transition = new WorkflowTransition(name, fromState, toState);
		return transition;
	}

	public String getName()
	{
		return this.name;
	}

	public void setType(final TransistionType type)
	{
		this.type = type;
	}

	public TransistionType getType()
	{
		return this.type;
	}

	public void setRule(final WorkflowRule rule)
	{
		this.rule = rule;
	}

	public WorkflowRule getRule()
	{
		return this.rule;
	}

	public WorkflowState getFromState()
	{
		return this.fromState;
	}

	public WorkflowState getToState()
	{
		return this.toState;
	}

	@Override
	public String toString()
	{
		final var result = MoreObjects.toStringHelper(this) //
				.add("name", this.name) //
				.add("fromState", this.fromState.toString()) //
				.add("toState", this.toState.toString()) //
				.add("type", this.type.toString()) //
				.toString();
		return result;
	}

}
