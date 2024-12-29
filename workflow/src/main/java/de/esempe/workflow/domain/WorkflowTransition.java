package de.esempe.workflow.domain;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@Document(collection = "transitions")
public class WorkflowTransition extends MongoDbObject
{
	public enum TransistionType
	{
		USER, SYSTEM;
	}

	@DocumentReference
	private WorkflowState fromState;
	@DocumentReference
	private WorkflowState toState;

	// Eingebettete Enitität
	private WorkflowRule rule;

	private TransistionType type;

	private WorkflowTransition()
	{
		super();
		this.name = "";
		this.type = TransistionType.USER;
	}

	public static WorkflowTransition create(final UUID objId, final String name, final WorkflowState fromState, final WorkflowState toState)
	{
		final var result = new WorkflowTransition();
		result.objId = objId;
		result.name = name;
		result.fromState = fromState;
		result.toState = toState;
		return result;
	}

	public static WorkflowTransition create(final String name, final WorkflowState fromState, final WorkflowState toState)
	{
		final var result = new WorkflowTransition();
		result.name = name;
		result.fromState = fromState;
		result.toState = toState;
		return result;
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

	public void setFromState(final WorkflowState state)
	{
		this.fromState = state;
	}

	public WorkflowState getFromState()
	{
		return this.fromState;
	}

	public void setToState(final WorkflowState state)
	{
		this.toState = state;
	}

	public WorkflowState getToState()
	{
		return this.toState;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (obj instanceof WorkflowTransition)
		{
			final var other = (WorkflowTransition) obj;
			return Objects.equal(this.fromState, other.fromState) //
					&& Objects.equal(this.toState, other.toState);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		final var values = Arrays.asList(this.fromState, this.toState);
		return Objects.hashCode(values);
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
